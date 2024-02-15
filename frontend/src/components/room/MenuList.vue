<script setup lang="ts">
import { computed, ref } from 'vue'
import { useFetch } from '@vueuse/core'
import axios from 'axios'

const shopId = defineModel<number>('shopId', { required: true })
const code = defineModel<string>('code', { required: true })

interface Option {
  id: number
  name: string
  price: number
}

interface OptionCategory {
  id: number
  name: string
  required: boolean
  max_count: number
  options: Option[]
}

interface Menu {
  id: number
  name: string
  price: number
  image: string
  soldout: boolean
  option_categories: OptionCategory[]
}

interface MenuCategory {
  id: number
  name: string
  menus: Menu[]
}

const participantName = ref('')
const { data: menuCategories } = await useFetch(`/api/v1/shops/${shopId.value}/menu-categories`).get().json<MenuCategory[]>()

// 메뉴카테고리 선택
const selectedMenuCategoryIndex = ref<number | null>(menuCategories !== null ? 0 : null)
const selectedMenuCategory = computed(() => selectedMenuCategoryIndex.value !== null
  ? menuCategories.value?.[selectedMenuCategoryIndex.value] ?? null
  : null)
const currentMenus = computed(() => selectedMenuCategory.value?.menus ?? [])

// 메뉴 선택 및 옵션 선택
const selectedMenuIndex = ref<number | null>(null)
const selectedMenu = computed(() => selectedMenuIndex.value !== null
  ? currentMenus.value[selectedMenuIndex.value] ?? null
  : null)
const currentOptionCategories = computed(() => selectedMenu.value?.option_categories ?? [])

const selectedOptions = ref<Option[]>([])
const totalPrice = computed(() => (selectedMenu.value?.price ?? 0) + selectedOptions.value.reduce((sum, option) => sum + option.price, 0))

function openOptions(index: number) {
  selectedMenuIndex.value = index
}

function closeOptions() {
  selectedMenuIndex.value = null
  selectedOptions.value = []
}

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

function openNameModal() {
  const requiredOptionCategories = currentOptionCategories.value.filter(category => category.required) ?? []
  const hasMissingRequiredOptions = requiredOptionCategories.some(category =>
    !category.options.some(option => selectedOptions.value.map(selectedOption => selectedOption.id).includes(option.id)))
  if (hasMissingRequiredOptions) {
    alert('필수 옵션을 선택하세요.')
    return
  }

  const exceedingMaxCountCategories = currentOptionCategories.value.filter(category =>
    selectedOptions.value.filter(option => category.options.map(option => option.id).includes(option.id)).length > (category.max_count ?? 1))
  if (exceedingMaxCountCategories.length > 0) {
    alert('최대 선택 가능한 옵션 개수를 초과했습니다.')
    return
  }

  showNameModal.value = true
}

function closeNameModal() {
  showNameModal.value = false
}

// 모달에서 주문하기 버튼을 누르면 호출되는 메서드
// 주문하기 버튼 클릭 시 모달을 닫고, 주문 정보를 서버로 전송
async function confirmOrder() {
  const data = {
    menu_id: selectedMenu.value?.id,
    participant_name: participantName.value, // 주문자 이름
    option_categories: currentOptionCategories.value.map(optionCategory => ({
      option_category_id: optionCategory.id,
      option_ids: optionCategory.options
        .filter(option => selectedOptions.value.includes(option))
        .map(option => option.id),
    })),
  }

  await axios.post(`/api/v1/parties/${code.value}/order-menus`, data)
  closeNameModal()
}
</script>

<template>
  <!-- 메뉴 카테고리 -->
  <div class="menu-categories">
    <div
      v-for="(category, index) in menuCategories" :key="index"
      :class="{ selected: selectedMenuCategoryIndex === index }" @click="selectedMenuCategoryIndex = index"
    >
      {{ category.name }}
    </div>
  </div>

  <!-- 메뉴판 -->
  <div class="menu-content">
    <div v-show="selectedMenuIndex === null" class="menu-items">
      <div
        v-for="(menu, index) in selectedMenuCategory?.menus" :key="index" class="drink-item"
        @click="openOptions(index)"
      >
        <img :src="menu.image" :alt="menu.name">
        <div class="drink-name">
          {{ menu.name }}
        </div>
        <div class="price">
          {{ menu.price }}원
        </div>
      </div>
    </div>
    <!-- 선택한 음료의 옵션 화면 -->
    <div v-show="selectedMenu !== null" class="options-container">
      <div v-if="selectedMenu !== null" class="options-content">
        <div class="options-title">
          <div>{{ selectedMenu.name }}</div>
          <div class="menu-price">
            {{ selectedMenu.price }}원
          </div>
          <button class="close-btn" @click="closeOptions">
            X
          </button>
        </div>
        <hr>
        <div v-for="optionCategory in selectedMenu.option_categories" :key="optionCategory.id" class="options-info">
          <div class="option-category">
            <p>{{ optionCategory.name }} </p>
            <div v-if="optionCategory.required">
              *
            </div>
            <div v-else-if="optionCategory.max_count < optionCategory.options.length">
              (최대 {{ optionCategory.max_count }})
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
            {{ totalPrice }}
          </div>
        </div>
        <button class="add" @click="openNameModal">
          주문하기
        </button>
      </div>
    </div>
  </div>
  <!-- 모달 창 -->
  <div v-if="showNameModal" class="modal">
    <div class="modal-content" @keyup.enter="confirmOrder">
      <span class="close" @click="closeNameModal">&times;</span>

      <input v-model="participantName" class="modal-input" type="text" placeholder="이름을 입력하세요">
      <button class="confirmOrder" @click="confirmOrder">
        주문하기
      </button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
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
  // justify-content: center;
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

.confirmOrder {
  cursor: pointer;
  background-color: #00a5e7;
  // background-color: #020817;
  border: 0px;
  font-weight: bold;
  color: #ffffff;
  font-size: 20px;
  margin: 10px;
  border-radius: 10px;
  // box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
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

.menu-categories {
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-wrap: wrap;
  text-align: center;
  //color: #ffffff;
  max-height: 180px;
  border: 3px solid #1e293b;
  border-radius: 10px;
  box-sizing: inherit;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.menu-categories>div {
  flex: 1 0 20%;
  /* 확장 가능, 축소 불가능, 최대 너비 20% */
  cursor: pointer;
  display: flex;
  justify-content: center;
  text-align: center;
  height: 40px;
  padding: 5px;
  margin: 5px;
  box-sizing: inherit;
  /* box-sizing: border-box; */
  font-size: 16px;
  font-weight: bold;
}

.menu-categories>div:hover {
  background-color: #d6d6d6;
  border-radius: 10px;
}

.menu-categories>div.selected {
  background-color: #ababab;
  color: black;
  width: auto;
  // box-sizing: inherit;
  border-radius: 10px;
  /* box-shadow: 2px 2px 2px 2px rgb(227, 226, 226); */
}

.menu-content {
  display: flex;
  flex-direction: column;
  height: 465px;
  border: 3px solid #1e293b;
  margin-top: 20px;
  border-radius: 10px;
  // box-sizing: border-box;
  // box-sizing: inherit;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.menu-items {
  display: flex;
  font-weight: bold;
  flex-wrap: wrap;
  overflow-y: auto;
}

.menu-items::-webkit-scrollbar {
  display: none;
}

.options-container {
  //color: #ffffff;
  // display: flex;
  font-weight: bold;
  // flex-wrap: wrap;
  // overflow-y: auto;
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
  // text-align: right;
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
  // height: auto;
  justify-content: space-between;
  border-radius: 5px;
  // font-weight: bold;

  font-size: 20px;
  // background-color: #343844;
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
  // padding: 10px;
  align-items: center;
}

input[type="checkbox"] {
  accent-color: #00a5e7;
}

.modal-input {
  border-radius: 5px;
}

.add {
  cursor: pointer;
  background-color: #00a5e7;
  // background-color: #020817;
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
  // background-color: #020817;
  background-color: #ffffff;
  border: 0px;
  font-size: 20px;
  // padding: 10px;
  // margin: 10px;
  font-weight: bold;
  //color: #ffffff;
}

.drink-item {
  text-align: center;
  width: 20%;
  height: 227px;
  box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
}

.drink-item:hover {
  background-color: #b5b5b5;
  /* 호버 시 배경색 변경 */
  border-radius: 5px;

}

.drink-item img {
  border: 2px solid #1e293b;
  /* box-shadow: 2px 2px 2px 2px rgb(227, 226, 226); */
  border-radius: 15px;
  width: 100px;
  height: 100px;
  margin-bottom: 10px;
}

.drink-name {
  display: flex;
  justify-content: center;
  padding: 5px;
  height: 40px;
  //color: #ffffff;
}

.price {
  margin-top: 10px;
  color: #0096bb;
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

@media screen and (max-width: 768px) {

  /* 모바일일 경우 스타일 조정 */
  .drink-item img {
    width: 80px;
    /*작은 화면에 맞게 이미지 너비 조정  */
    height: 80px;
    /* 이미지 높이 조정  */
  }

  .drink-item {
    font-size: 12px;
  }

  .menu-categories>div {
    font-size: 12px;
    /* 작은 화면에 맞게 메뉴 카테고리 텍스트 크기 조정 */
    font-weight: bold;
  }

  .close-btn,
  .addOrder {
    width: 100px;
    /* 작은 화면에 맞게 모달 버튼 너비 조정 */
    font-size: 14px;
    /* 작은 화면에 맞게 모달 버튼 텍스트 크기 조정 */
  }
}
</style>
