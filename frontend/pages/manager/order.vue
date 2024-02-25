<script setup lang="ts">
const route = useRoute('manager-order')
const { apiBase } = useRuntimeConfig().public

// orderList의 참조를 생성합니다.
const orders = ref<Array<Order>>([])
const shopId = ref(route.query.roomId)
// roomId를 반응형 참조로 선언
const roomId = ref(route.query.shopId)
const managerToken = useLocalStorage('manager-token', null)
const totalPrice = ref(0)

async function fetchOrderDetails() {
  if (!roomId.value)
    return

  orders.value = await $fetch<Array<Order>>(`${apiBase}/v1/shops/${shopId.value}/orders`, {
    headers: { Authorization: `Bearer ${managerToken.value}` },
  })
  // orderList.value = orders.filter(order => order.roomId === numericRoomId)
  // console.log(orderList.value)

  let price = 0
  orders.value.forEach((order) => {
    order.choiceMenus?.forEach((choiceMenu) => {
      price += choiceMenu.menu.price // 메뉴 가격 합산

      choiceMenu.optionCategories.forEach((optionCategory) => {
        optionCategory.options.forEach((option) => {
          price += option.price // 옵션 가격 합산
        })
      })
    })
    totalPrice.value = price
  })
}

function onMade() {
  $fetch(`/v1/shops/${shopId.value}/orders/${roomId.value}/made`, {
    baseURL: apiBase,
    method: 'POST',
    headers: { Authorization: `Bearer ${managerToken.value}` },
  })
}

function onStartDevlivery() {
  $fetch(`/v1/shops/${shopId.value}/orders/${roomId.value}/deliver`, {
    baseURL: apiBase,
    method: 'POST',
    headers: { Authorization: `Bearer ${managerToken.value}` },
  })
}

onMounted(() => {
  if (roomId.value)
    fetchOrderDetails()
})

function toggleHighlight(choiceMenu) {
  choiceMenu.highlighted = !choiceMenu.highlighted
}
</script>

<template>
  <div class="content">
    <div class="room-info">
      <div class="room-info1">
        <span>주문자명 | 양희승</span>
        <div style="color:#E6F4F1">
          .
        </div>
        <span>전화번호 | 010-9779-4837</span>
        <div style="color:#E6F4F1">
          .
        </div>
        <span>배달주소 | 광주광역시 하남산단 삼성전자 G5주차장 자전거 거치대 앞</span>
      </div>
      <div class="room-info2">
        <span>총 주문금액 : {{ totalPrice }}원</span>
      </div>

      <div class="status-info">
        <div class="status-accept">
          <span>주문수락 | </span>
          <span>11:40:35</span>
        </div>
        <div class="direction">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▼
        </div>
        <div class="status-made">
          <span>제조완료 | </span>
          <span>12:19:10</span>
          <button @click="onMade">
            제조 완료
          </button>
        </div>
        <div class="direction">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▼
        </div>
        <div class="status-deliveryStart">
          <span>배달출발 | </span>
          <span style="color: gray;">이전 단계를 완료하세요.</span>
          <span>12:25:54</span>
          <button @click="onStartDevlivery">
            배달 출발
          </button>
        </div>
        <div class="direction">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▼
        </div>
        <div class="delivered">
          <span>고객수령 | </span>
          <span style="color: gray;">고객 제품 수령 시, 시간이 표시됩니다.</span>
          <span>12:25:54</span>
        </div>
      </div>
    </div>

    <div id="orderList" class="order-info">
      <div v-for="(order, index) in orders" :key="index" class="order-ul">
        <div v-for="choiceMenu in order.choiceMenus" :key="choiceMenu.id" class="order-li" :class="{ highlight: choiceMenu.highlighted }" @click="toggleHighlight(choiceMenu)">
          <div class="menu-name">
            {{ choiceMenu.menu.name }}
          </div>

          <div v-for="choiceOptionCategory in choiceMenu.choiceOptionCategories" :key="choiceOptionCategory.id">
            <div class="option-name">
              <div v-for="option in choiceOptionCategory.optionCategory.options" :key="option.id">
                ㄴ {{ option.name }}
              </div>
            </div>
          </div>
          <hr>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.content {
  padding: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.room-info {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-radius: 20px;
  color: #ffffff;
  background-color: #1e293b;
  align-items: center;
  font-weight: bold;
}

.room-info1 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 15px;
  font-size: 20px;
}

.room-info2 {
  padding: 15px;
  padding-top: 0px;
  justify-content: center;
  font-size: 30px;
  border-left: 2px black solid;
  border-right: 2px black solid;
}

.status-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 20px;
  padding: 15px;
}

.order-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  margin: 10px;
  font-size: 20px;
  width: 300px;
  height: 700px;
  overflow-y: auto;
  border-radius: 5px;
  border: 3px solid #1e293b;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.menu-name {
  font-weight: bold;
}

.order-ul {
  width: 100%;
}

.order-li {
}

.order-info::-webkit-scrollbar {
  display: none;
}

.option-name {
  margin-top: 5px;
  margin-left: 10px;
  font-size: 16px;
  display: flex;
}

button {
  margin-left: 5px;
  background-color: #eb4e5a;
  color: white;
  border-radius: 25px;
  border: none;
  width: auto;
  height: 25px;
  font-weight: bold;
  font-size: 15px;
  align-items: center;
  cursor: pointer;
}

.direction {
}

.highlight {
  background-color: #ffff16;
  /* 배경색을 노란색으로 설정 */
}
</style>
