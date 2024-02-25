<script setup lang="ts">
const setMessage = defineModel<(msg: string) => void>('setMessage', { required: true })
const roomId = defineModel<string>('roomId', { required: true })
const menuCategories = defineModel<Array<MenuCategory>>('menuCategories', { required: true })
const currentMenuCategoryIndex = defineModel<number>('currentMenuCategoryIndex', { required: true })

const { apiBase } = useRuntimeConfig().public

// 메뉴카테고리 & 메뉴
const currentMenuCategory = computed(() => menuCategories.value[currentMenuCategoryIndex.value])
const currentMenus = computed(() => currentMenuCategory.value.menus)
const currentMenuIndex = ref<number | null>(null)
const currentMenu = computed(() => currentMenuIndex.value !== null ? currentMenus.value[currentMenuIndex.value] ?? null : null)
watch(() => currentMenuCategoryIndex.value, closeMenuOptions)

// 옵션 & 가격
const currentOptionCategories = computed(() => currentMenu.value?.optionCategories ?? [])
const selectedOptions = ref<Array<Option>>([])
const currentPrice = computed(() => (currentMenu.value?.price ?? 0) + selectedOptions.value.reduce((sum, option) => sum + option.price, 0))

function openMenuOptions(index: number) {
  currentMenuIndex.value = index
}
function closeMenuOptions() {
  currentMenuIndex.value = null
  selectedOptions.value = []
}

// 참가자
const participantName = useSessionStorage<string>('participant-name', '')

function handleOptionChange(optionCategory: OptionCategory, option: Option) {
  // 라디오 로직
  if (optionCategory.required) {
    selectedOptions.value = selectedOptions.value.filter(selectedOption => !optionCategory.options.find(categoryOption => categoryOption.id === selectedOption.id))
    selectedOptions.value.push(option)
  }
  // 체크박스 로직
  else {
    const index = selectedOptions.value.findIndex(selectedOption => selectedOption.id === option.id)
    if (index === -1)
      selectedOptions.value.push(option)
    else
      selectedOptions.value.splice(index, 1)
  }
}

// 이름 입력 모달
const showNameModal = ref(false)

function createChoiceMenu(trimedName: string) {
  $fetch(`/v1/rooms/${roomId.value}/choice-menus`, {
    baseURL: apiBase,
    method: 'POST',
    body: {
      menuId: currentMenu.value?.id,
      participantName: trimedName, // 주문자 이름
      optionCategories: currentOptionCategories.value
        .map(optionCategory => ({
          optionCategoryId: optionCategory.id,
          optionIds: optionCategory.options
            .filter(option => selectedOptions.value.includes(option))
            .map(option => option.id),
        }))
        .filter(optionCategory => optionCategory.optionIds.length > 0),
    },
  })
}

function openNameInputModal() {
  const requiredOptionCategories = currentOptionCategories.value.filter(category => category.required) ?? []
  const hasMissingRequiredOptions = requiredOptionCategories.some(category =>
    !category.options.some(option => selectedOptions.value.map(selectedOption => selectedOption.id).includes(option.id)))
  if (hasMissingRequiredOptions)
    return setMessage.value('필수 옵션을 선택하세요.')

  const exceedingMaxCountCategories = currentOptionCategories.value.filter(category =>
    selectedOptions.value.filter(option => category.options.map(option => option.id).includes(option.id)).length > (category.maxCount ?? 1))
  if (exceedingMaxCountCategories.length > 0)
    return setMessage.value('최대 선택 가능한 옵션 개수를 초과했습니다.')

  showNameModal.value = true
}

function closeNameInputModal() {
  showNameModal.value = false
}

// 모달에서 주문하기 버튼을 누르면 호출되는 메서드
// 주문하기 버튼 클릭 시 모달을 닫고, 주문 정보를 서버로 전송
async function confirmOrder() {
  const trimedName = participantName.value.trim()
  if (trimedName.length === 0)
    return setMessage.value('이름을 입력하세요.')

  createChoiceMenu(trimedName)
  closeNameInputModal()
  closeMenuOptions()
}
</script>

<template>
  <!-- 메뉴 카테고리 -->
  <div card card-shadow>
    <!-- 메뉴판 -->
    <div
      v-if="currentMenu === null"
      flex flex-wrap
    >
      <div
        v-for="(menu, index) in currentMenuCategory?.menus" :key="index"
        w="1/5" flex flex-col cursor-pointer items-center rounded-1 p-y-2 font-bold hover:bg-gray-4
        @click="openMenuOptions(index)"
      >
        <NuxtImg
          :src="menu.imageUrl" :alt="menu.name"
          h-25 w-25 b-2 b-color rounded-4
        />
        <div>
          {{ menu.name }}
        </div>
        <div text="#0096bb">
          {{ menu.price }}원
        </div>
      </div>
    </div>
    <!-- 선택한 음료의 옵션 화면 -->
    <div
      v-else
      class="options-container"
    >
      <div class="options-content">
        <div class="options-title">
          <div>{{ currentMenu.name }}</div>
          <div class="menu-price">
            {{ currentMenu.price }}원
          </div>
          <button class="close-btn" @click="closeMenuOptions">
            X
          </button>
        </div>
        <hr>
        <div v-for="optionCategory in currentMenu.optionCategories" :key="optionCategory.id" class="options-info">
          <div class="option-category">
            <p>{{ optionCategory.name }} </p>
            <div v-if="optionCategory.required">
              *
            </div>
            <div v-else-if="optionCategory.maxCount < optionCategory.options.length">
              (최대 {{ optionCategory.maxCount }})
            </div>
          </div>
          <div class="choice">
            <div v-for="option in optionCategory.options" :key="option.id" class="row">
              <label>
                <input
                  :type="optionCategory.required ? 'radio' : 'checkbox'"
                  :name="optionCategory.name"
                  :value="option"
                  @change="handleOptionChange(optionCategory, option)"
                >
                {{ option.name }}
              </label>
              <div class="options-price">
                + {{ option.price }}원
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="options-footer">
        <div class="total-info">
          <div>총 금액</div>
          <div class="total-price">
            {{ currentPrice }}
          </div>
        </div>
        <button class="add" @click="openNameInputModal">
          주문하기
        </button>
      </div>
    </div>
    <!-- 모달 창 -->
    <div v-if="showNameModal" class="modal">
      <div
        class="modal-content"
        @keyup.enter="confirmOrder"
      >
        <span
          class="close"
          @click="closeNameInputModal"
        >&times;</span>

        <input
          v-model="participantName"
          type="text" placeholder="이름을 입력하세요"
          rounded-2 text-center
        >
        <button
          class="confirmOrder"
          @click="confirmOrder"
        >
          주문하기
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.confirmOrder {
  cursor: pointer;
  background-color: #00a5e7;
  border: 0px;
  font-weight: bold;
  color: #ffffff;
  font-size: 20px;
  margin: 10px;
  border-radius: 10px;
}

/* 닫기 버튼 스타일 */
.close {
  width: 100%;
  text-align: right;
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.options-container {
  font-weight: bold;
  box-sizing: inherit;
}

.options-title {
  display: flex;
  justify-content: space-between;
  margin: 20px;
  font-size: 20px;
}

.options-content {
  display: flex;
  flex-direction: column;
  margin: 10px;
  height: 390px;
  max-height: 390px;
  overflow-y: auto;
  box-sizing: inherit;
}

.options-content::-webkit-scrollbar {
  display: none;
}

.options-info {
  justify-content: center;
}

.option-category {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
}

.option-category div {
  font-size: 24px;
  font-weight: bold;
  color: red;
}

.options-footer {
  display: flex;
  width: 100%;
  justify-content: space-between;
  border-radius: 5px;

  font-size: 20px;
  position: sticky;
  box-sizing: inherit;
}

hr {
  width: 100%;
  height: 3px;
  background-color: #1e293b;
}

.menu-price,
.options-price,
.total-price {
  color: #00a5e7;
}

.total-info {
  width: 70%;
  display: flex;
  gap: 20px;
  margin-left: 20px;
  font-weight: bold;
  align-items: center;
}

input[type='checkbox'] {
  accent-color: #00a5e7;
}

.add {
  cursor: pointer;
  background-color: #00a5e7;
  border: 0px;
  font-weight: bold;
  color: #ffffff;
  font-size: 20px;
  margin: 10px;
  border-radius: 10px;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.close-btn {
  cursor: pointer;
  background-color: #ffffff;
  border: 0px;
  font-size: 20px;
  font-weight: bold;
}

.addOrder {
  width: 120px;
  cursor: pointer;
  border: none;

  background: #00a5e7;
  font-weight: bold;
  border-radius: 5px;
  padding: 10px 15px;
  font-weight: bold;
  font-size: 18px;
}

p {
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  margin-left: 20px;
  margin-top: 20px;
}

.choice {
  display: flex;
  flex-direction: column;
  padding: 10px;
  margin-left: 40px;
}

.row {
  font-size: 18px;
  display: flex;
  margin-bottom: 20px;
  justify-content: space-between;
}

/* 모달 스타일 */
.modal {
  position: fixed;
  z-index: 1;
  /* 모달을 다른 요소들 위에 표시 */
  left: 0;
  top: 0;
  width: 100%;
  /* 화면 전체 너비 */
  height: 100%;
  /* 화면 전체 높이 */
  overflow: auto;
  /* 스크롤이 필요한 경우 스크롤바 표시 */
  background-color: rgb(0, 0, 0);
  /* 반투명 검은 배경 */
  background-color: rgba(0, 0, 0, 0.4);
  /* 반투명 검은 배경 (투명도 조절) */
}

/* 모달 내용 스타일 */
.modal-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  background-color: #343844; /* 모달 내용 배경색 */
  margin: 15% auto; /* 중앙 정렬 */
  padding: 20px;

  border-radius: 15px;
  width: 30%; /* 모달 내용 너비 */
  height: 20%;
}

.modal-content input {
  font-weight: bold;
  font-size: 25px;
  height: 50px;
  margin-bottom: 10px;
  margin: 10px;
}

.modal-content button {
  margin: 10px;
  padding: 5px;
  font-size: 25px;
  margin-bottom: 25px;
}
</style>
