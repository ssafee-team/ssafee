<script setup lang="ts">
import { computed, ref } from 'vue'
import { deleteOrderMenu } from '@/api/party'

const code = defineModel<string>('code', { required: true })
const choiceMenus = defineModel<ChoiceMenu[]>('choiceMenus', { required: true })

interface ChoiceMenu {
  id: number
  participant_name: string
  menu: {
    id: number
    name: string
    price: number
    image: string
    soldout: boolean
  }
  option_categories: {
    id: number
    name: string
    required: boolean
    max_count: number
    options: {
      id: number
      name: string
      price: number
    }[]
  }[]
}

// 현재 선택된 정렬 방식
const sortMethod = ref('default')

// 선택된 정렬 방식에 따라 정렬된 주문 목록
const sortedOrders = computed(() => {
  const ordersCopy = [...choiceMenus.value]
  if (sortMethod.value === 'userName')
    ordersCopy.sort((a, b) => a.participant_name.localeCompare(b.participant_name))

  else if (sortMethod.value === 'menuName')
    ordersCopy.sort((a, b) => a.menu.name.localeCompare(b.menu.name))
  return ordersCopy
})

// 주문 삭제
function removeOrder(choiceMenuId: number) {
  const index = choiceMenus.value.findIndex(choiceMenu => choiceMenu.id === choiceMenuId)
  if (index !== -1) {
    deleteOrderMenu(
      code.value,
      choiceMenuId,
      () => { choiceMenus.value.splice(index, 1) },
      null,
    )
  }
}

const totalPrice = computed(() => {
  let price = 0
  for (const choiceMenu of choiceMenus.value) {
    price += choiceMenu.menu.price
    for (const optionCategory of choiceMenu.option_categories) {
      for (const option of optionCategory.options)
        price += option.price
    }
  }
  return price
})
</script>

<template>
  <!-- 탭 형태로 My Cart와 Our Cart를 표시 -->
  <div class="tabs">
    <div class="tab">
      주문내역
    </div>
    <select v-model="sortMethod">
      <option value="default">
        기본
      </option>
      <option value="userName">
        이름순
      </option>
      <option value="menuName">
        메뉴순
      </option>
    </select>
  </div>

  <!-- 주문내역 -->
  <div class="content">
    <div class="order-list">
      <div v-for="(order, index) in sortedOrders" :key="index" class="order">
        <!-- 선택한 메뉴와 가격 -->
        <div class="item">
          <div class="participant-name">
            {{ order.participant_name }}
          </div>
          <div class="menu-name">
            {{ order.menu.name }}
          </div>
          <div class="menu-price">
            {{ order.menu.price }}
          </div>
          <span class="remove" @click="removeOrder(order.id)">X</span>
        </div>
        <!-- 선택한 옵션과 가격 -->
        <div v-for="optionCategory in order.option_categories" :key="optionCategory.id" class="option">
          <div v-for="option in optionCategory.options" :key="option.id" class="menu-option">
            <div class="option-name">
              ㄴ{{ option.name }}
            </div>
            <div class="option-price">
              +{{ option.price }}
            </div>
          </div>
        </div>
        <!-- <div v-for="(option, idx) in order.option_categories" :key="idx" class="option"> -->
        <!-- <template v-if="option.name && option.option_names.length > 0">
            ㄴ{{ option.options.map((opt) => opt.name + " - " + opt.price + "원").join(", ") }}
          </template> -->
        <!-- </div> -->
      </div>
    </div>
    <!-- 총 주문 금액 -->
    <div class="footer">
      <div class="total">
        <div>총 주문금액</div>
        <div class="total-price">
          {{ totalPrice }}원
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
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
  font-size: 17px;
  font-weight: bold;
  text-align: center;
}

/* 주문 내역 스타일 */
.order {
  margin-top: 10px;
  box-sizing: inherit;
}

.item {
  /* color: #FFFFFF; */
  margin-bottom: 5px;
  padding: 15px;
  padding-bottom: 0px;
  font-size: 15px;
  font-weight: bold;
  display: flex;
  box-sizing: border-box;
  /* justify-content: space-between; */
}

.remove {
  margin-left: 30px;
  cursor: pointer;
  color: red;
}

.participant-name{
  width: 100px;
}

.menu-name {
  width: 190px;
}

.menu-price {
  font-size: 17px;
  width: 50px;
  color: #00a5e7;
  margin-left: 10px;
}

.option {
  /* color: #FFFFFF; */
  margin-left: 20px;
}

.menu-option {
  display: flex;
  justify-content: space-between;
  margin-right: 50px;
  box-sizing: inherit;
}

.option-name {
  width: 170px;
  margin-left: 70px;
}

.option-price {
  color: #00a5e7;
  margin-left: 10px;
  display: flex;
  align-items: center;
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
  overflow-y: auto;
  /* 주문 목록이 넘칠 경우 스크롤 생성 */
  height: 550px;
}

.order-list::-webkit-scrollbar {
  display: none;
}

.footer {
  /* width: 100%; */
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #ffffff;
  background-color: #1e293b;
  /* bottom: 0; */
  /* position: sticky; */
  border-radius: 1px;
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
  .item{
    font-size: 16px;
    width: 100%;
    justify-content: space-between;
  }

  .menu-option{
    font-size: 14px;
  }

  .footer{
    margin-top: 5px;
  }
  .total, .tab{
    font-size: 16px;
  }

}
</style>
