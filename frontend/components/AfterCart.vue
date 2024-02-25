<script setup lang="ts">
const choiceMenus = defineModel<Array<ChoiceMenu>>('choiceMenus', { required: true })
const participantRecord = defineModel<Record<number, Participant>>('participantRecord', { required: true })
const totalPrice = defineModel<number>('totalPrice', { required: true })
const roomId = defineModel<string>('roomId', { required: true })

const { apiBase } = useRuntimeConfig().public

// const sortMethod = ref('default')
// const sortedChoiceMenus = computed(() => {
//   const copied = [...choiceMenus.value]
//   if (sortMethod.value === 'userName')
//     copied.sort((a, b) => a.participantName.localeCompare(b.participantName))
//   return copied
// })

const choiceMenusByParticipantId = computed(() => {
  const grouped = {} as Record<number, Array<ChoiceMenu>>
  for (const choiceMenu of choiceMenus.value) {
    const participantId = choiceMenu.participantId
    if (grouped[participantId] === undefined)
      grouped[participantId] = []
    grouped[participantId].push(choiceMenu)
  }
  return grouped
})

async function toggleCheck(participantId: number) {
  const participant = participantRecord.value[participantId]
  participant.paid = !participant.paid

  await $fetch(`/v1/rooms/${roomId.value}/participants/${participantId}`, {
    baseURL: apiBase,
    method: 'PATCH',
    body: { paid: participant.paid },
  })
}
</script>

<template>
  <div card card-shadow>
    <div h-full flex flex-col>
      <!-- header -->
      <div h-10 flex items-center justify-around b-b-3 b-color text-xl font-bold>
        <div>
          주문내역
        </div>
        <!-- <select v-model="sortMethod">
          <option value="default">
            기본
          </option>
          <option value="userName">
            이름순
          </option>
        </select> -->
      </div>
      <!-- content -->
      <div flex-1 overflow-y-auto>
        <div
          v-for="(subChoiceMenus, participantId) in choiceMenusByParticipantId" :key="participantId"
          class="participant"
        >
          <div class="participant-name">
            <input
              type="checkbox" :checked="participantRecord[participantId].paid"
              @change="toggleCheck(participantId)"
            >
            {{ participantRecord[participantId].name }}
          </div>
          <div
            v-for="(choiceMenu, index) in subChoiceMenus" :key="index"
            class="order" :class="{ checked: participantRecord[participantId].paid }"
          >
            <div class="item" :class="{ checked: participantRecord[participantId].paid }">
              <div class="menu-name">
                {{ choiceMenu.menu.name }}
              </div>
              <div class="menu-price">
                {{ choiceMenu.menu.price }}원
              </div>
            </div>
            <div
              v-for="optionCategory in choiceMenu.optionCategories" :key="optionCategory.id"
              class="option"
            >
              <div
                v-for="option in optionCategory.options" :key="option.id"
                class="menu-option" :class="{ checked: participantRecord[participantId].paid }"
              >
                ㄴ
                <div class="option-name">
                  {{ option.name }}
                </div>
                <div class="option-price">
                  + {{ option.price }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- footer -->
      <div h-12 flex items-center justify-around bg-color text-xl font-bold>
        <div text-white>
          총 주문금액
        </div>
        <div text="#00a5e7">
          {{ totalPrice }}원
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.order {
  border-bottom: 1px solid gray;
}
.checkbox {
  margin-left: 10px;
}
.item.checked .menu-name,
.item.checked .menu-price {
  color: red;
  text-decoration: line-through; /* 취소선 적용 */
}

.menu-option.checked .option-name,
.menu-option.checked .option-price {
  color: red;
  text-decoration: line-through; /* 취소선 적용 */
}

.menu-name {
  /* width: 190px; */
  display: flex;
  align-items: center;
}

select {
  border: none;
  font-size: 16px;
  font-weight: bold;
}
option {
  font-size: 16px;
  font-weight: bold;
}

/* 탭 스타일 */
.tabs {
  display: flex;
  height: 40px;
  justify-content: space-around;
  border-bottom: 3px solid #1e293b;
  /* box-shadow: 0px 0px 10px 0px rgb(227, 226, 226); */
}

.tab {
  width: 50%;
  cursor: pointer;
  padding: 10px;
  /* color: #FFFFFF; */
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}

hr {
  border: 0;
  height: 3px;
  background-color: #1e293b;
}

/* 주문 내역 스타일 */
.order {
  /* color: #FFFFFF; */
  height: 20%;
  max-height: 20%;
  margin-top: 10px;
  /* margin-left: 10px; */
  box-sizing: border-box;
}

.item {
  /* color: #FFFFFF; */
  margin-bottom: 5px;
  padding: 10px;
  font-size: 18px;
  font-weight: bold;
  display: flex;
  justify-content: space-between;
}

.remove {
  margin-left: 30px;
  cursor: pointer;
  color: red;
}

.participant-name {
  margin-left: 10px;
  margin-top: 10px;
  /* margin-right: 10px; */
  font-weight: bold;
  font-size: 18px;
}

input {
  margin-right: 10px;
}
.menu-name {
  width: 260px;
  margin-left: 30px;
}

.menu-price {
  color: #00a5e7;
  width: 30%;
  text-align: right;
}

.option {
  /* color: #FFFFFF; */
  margin-left: 20px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.menu-option {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-left: 25px;
}

.option-name {
  margin-left: 5px;
  width: 190px;
}

.option-price {
  color: #00a5e7;
  text-align: right;
  margin-left: 18px;
}

.content {
  display: flex;
  flex-direction: column;
  height: 605px;
  /* max-height: 624px; */
  /* height: calc(100% - 60px);  */
  overflow-y: auto;
  box-sizing: inherit;
}

.order-list {
  overflow-y: auto; /* 주문 목록이 넘칠 경우 스크롤 생성 */
  height: 550px;
}

.order-list::-webkit-scrollbar {
  display: none;
}

input[type='checkbox'] {
  accent-color: #00a5e7;
}

.footer {
  /* width: 100%; */
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #ffffff;
  background-color: #343844;
  /* bottom: 0; */
  /* position: sticky; */
  border-radius: 0 0 3px 3px;
  /* box-sizing: inherit; */
}

.total {
  /* color: #FFFFFF; */
  font-size: 20px;
  font-weight: bold;
  display: flex;
  gap: 20px;
}
.total-price {
  color: #00a5e7;
}
@media screen and (max-width: 768px) {
  .participant-name {
    font-size: 16px;
  }

  .item {
    font-size: 16px;
    width: 100%;
    justify-content: space-between;
  }
  .menu-option {
    font-size: 14px;
  }
  .total,
  .tab {
    font-size: 16px;
  }

  .footer {
    margin-top: 5px;
  }
}
</style>
