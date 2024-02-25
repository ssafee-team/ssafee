<script setup lang="ts">
const props = defineProps({
  orderList: Array<any>,
  code: { required: true },
})

const emit = defineEmits(['toggleOrderSummary'])

const isOrderModalOpen = ref(false)
const orderSummaryVisible = ref(false)

const limitedOrderList = computed(() => {
  return orderSummaryVisible.value ? props.orderList : props.orderList.slice(0, 2)
})

const totalPrice = computed(() => {
  return props.orderList
    .reduce((total, order) => total + Number.parseFloat(order.price.replace('원', '').replace(',', '')), 0)
    .toFixed(0)
})

function openOrderModal() {
  if (props.orderList.length === 0) {
    alert('담은 주문내역이 없습니다!')
    return
  }
  isOrderModalOpen.value = true
}

function closeOrderModal() {
  isOrderModalOpen.value = false
}

function toggleOrderSummary() {
  orderSummaryVisible.value = !orderSummaryVisible.value
  emit('toggleOrderSummary')
  // For class toggling, you may need to access the root element differently
  // Since $el is not available, consider using a ref on the component's root element
}

function getOrderOptions(options) {
  return options.join(', ')
}

function deleteOrder(index) {
  props.orderList.splice(index, 1) // Direct mutation of props is generally bad practice
  // Consider emitting an event to the parent to handle the deletion
}
</script>

<template lang="">
  <div class="order">
    <div class="title">
      <div style="display: flex">
        <p style="margin-left: 20px">
          전체주문금액 :
        </p>
        <p class="total-price">
          {{ totalPrice }}원
        </p>
        <button class="btn-order" @click="openOrderModal">
          주문하기
        </button>
      </div>
      <button class="btn-toggle" @click="toggleOrderSummary">
        {{ orderSummaryVisible ? "▼" : "▲" }}
      </button>
    </div>
    <hr>
    <ul v-show="orderList.length > 0" class="list">
      <li v-for="(order, index) in limitedOrderList" :key="index" class="order-list">
        <div class="order-name">
          {{ order.name }}
        </div>
        <div class="order-options">
          옵션: {{ getOrderOptions(order.optionNames) }}
        </div>
        <div class="order-price">
          {{ order.price }}
        </div>
        <button class="btn-delete" @click="deleteOrder(index)">
          취소
        </button>
      </li>
    </ul>
    <OrderModal v-if="isOrderModalOpen" :orders="orderList" :code="code" @close="closeOrderModal" />
  </div>
</template>

<style scoped>
.order {
  background-color: #344a53;
  color: white;
  border-radius: 10px 10px 0px 0px;
  height: 180px;
  max-height: 300px;
  width: 67%;
  margin: 0px 10px 0px 10px;
  /* position: relative; */
  /* position: absolute; */
  position: fixed;
  bottom: 0;

  transition: height 0.3s ease;
  /* transition 효과 추가 */
  /* overflow: hidden; 추가 */
}

.expanded {
  height: 400px;
  /* 토글 시 높이를 자동으로 조정하도록 설정 */
}

.title {
  display: flex;
  justify-content: space-between;
}

.title > p {
  font-weight: bold;
  margin-left: 20px;
  margin-bottom: 5px;
  font-size: 24px;
}

.btn-order {
  cursor: pointer;
  height: auto;
  /* margin-left: 20px; */
  background-color: #00a5e7;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  margin-top: 10px;
  font-size: 14px;
  border: none;
}

.total-price {
  margin-left: 20px;
  color: #00a7d0;
  width: 100px;
}

.btn-toggle {
  cursor: pointer;
  margin-top: 10px;
  margin-right: 10px;
  /* opacity: 0.; 배경 투명 */
  background-color: #344a53;
  border: 0;
  color: white;
  font-size: 20px;
}

.list {
  margin-top: 5px;
  height: auto;
  padding: 15px;
  max-height: 180px;
  overflow-y: auto;
}

.list::-webkit-scrollbar {
  display: none;
}

.order-list {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
  height: auto;
}

.order-list > div {
  flex: 1;
}

.order-name {
  font-weight: bold;
  font-size: 18px;
}

.order-options {
  color: #969696;
  font-size: 14px;
}

.order-price {
  color: #00a7d0;
  font-size: 18px;
  font-weight: bold;
}

.btn-delete {
  width: 60px;
  height: 30px;
  cursor: pointer;
  border: none;
  padding: 5px;
  background: #eb4e5a;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  font-size: 16px;
  margin-right: 10px;
}

p {
  font-weight: bold;
}

@media screen and (max-width: 768px) {
  .order {
    width: 100%;
    /* height: 100px; */
    max-height: 400px;
    margin: 0;
    position: relative;
  }

  .order-name,
  .order-price {
    font-size: 16px;
  }

  .order-options,
  .btn-delete {
    font-size: 14px;
  }
}
</style>
