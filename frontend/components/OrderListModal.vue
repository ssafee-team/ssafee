<script setup lang="ts">
// Accessing props with toRefs for reactivity
const props = defineProps<{
  orderList: Array<Order>
  code: string
}>()

// Emit events
const emit = defineEmits(['close'])

const { orderList, code } = toRefs(props)

// Computed property to calculate total order price
const calculateTotalOrderPrice = computed(() => {
  return orderList.value.reduce((total, order) => total + calculateTotalPrice(order), 0)
})

// Methods
function closeModal() {
  emit('close')
}

function calculateTotalPrice(order) {
  let totalPrice = order.menu.price
  for (const category of order.optionCategories) {
    for (const option of category.options)
      totalPrice += option.price
  }
  return totalPrice
}

function deleteOrder(orderId) {
  const index = orderList.value.findIndex(order => order.id === orderId)
  if (index !== -1) {
    deleteOrderMenu(
      code.value,
      orderId,
      () => {
        orderList.value.splice(index, 1)
      },
      (error) => {
        console.error('주문 삭제 실패:', error)
      },
    )
  }
}
</script>

<template>
  <div class="modal">
    <div class="modal-content">
      <div class="modal-title">
        <div>전체 주문 현황</div>
        <button class="close" @click="closeModal">
          X
        </button>
      </div>
      <hr>
      <div class="order-list">
        <div v-for="order in orderList" :key="order.id" class="row">
          <div class="name">
            {{ order.participantName }}
          </div>
          <div class="menus">
            <div class="menu-info">
              <div class="menu-name">
                {{ order.menu.name }}
              </div>

              <div v-for="optionCategory in order.optionCategories" :key="optionCategory.id">
                <div v-for="option in optionCategory.options" :key="option.id" class="menu-option">
                  {{ option.name }}
                </div>
              </div>
            </div>
          </div>
          <div class="price">
            {{ calculateTotalPrice(order) }}원
          </div>
          <button class="delete" @click="deleteOrder(order.id)">
            X
          </button>
        </div>
      </div>
      <div class="modal-footer">
        <div>총</div>
        <div style="color: #00a7d0">
          {{ calculateTotalOrderPrice }}원
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
}

.modal-content {
  width: 40%;
  height: 500px;
  background: #344a53;
  border-radius: 5px;
  padding: 20px;
  margin-top: 20px;
  border: 1px solid black;
  text-align: center;
  color: white;
  border: none;
}

.modal-title {
  padding: 10px;
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  justify-content: space-between;
  display: flex;
  position: sticky;
}

.order-content {
  /* max-height: 380px; */
}

.order-content::-webkit-scrollbar {
  display: none;
}

.order-list {
  display: flex;
  flex-direction: column;
  padding: 10px;
  /* margin-left: 70px; */
  height: 360px;
  max-height: 360px;
  bottom: 0;
  padding: 20px;
  gap: 20px;
  /* justify-content: space-between; */
  overflow-y: auto;
}

.order-list::-webkit-scrollbar {
  display: none;
}

.name {
  width: 100px;
  font-size: 18px;
  font-weight: bold;
}

.menus {
  align-items: center;
  display: flex;
  flex-direction: column;
  font-size: 14px;
  width: 60%;
}

.price {
  width: 80px;
  /* 고정된 너비 부여 */
  color: #00a7d0;
  text-align: center;
}

.menu-info {
  margin-bottom: 15px;
  width: 100%;
  text-align: center;
}

.menu-name {
  width: 100%;
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}

.menu-option {
  font-size: 12px;
}

.row {
  width: 100%;
  font-size: 18px;
  display: flex;
  margin-bottom: 20px;
  /* align-items: center; */
  justify-content: space-between;
}

.modal-footer {
  gap: 10px;
  justify-content: right;
  display: flex;
  font-size: 18px;
  font-weight: bold;
  margin-top: 10px;
  position: sticky;
}

.close {
  cursor: pointer;
  border: none;
  background: #eb4e5a;
  color: white;
  font-weight: bold;
  border-radius: 5px;

  font-weight: bold;
  font-size: 18px;
}

.delete {
  height: 30px;
  cursor: pointer;
  border: none;
  background: #344a53;
  color: white;
  font-weight: bold;
  border-radius: 5px;

  font-weight: bold;
  font-size: 18px;
}

@media screen and (max-width: 768px) {
  .modal-content {
    width: 80%;
    /* 작은 화면에 맞게 모달 너비 조정 */
    margin: 50px auto;
    /* 모달 위치 조정 */
  }

  .modal-title {
    font-size: 16px;
  }

  .name,
  .menu-name,
  .price {
    font-size: 14px;
  }

  .modal-footer {
    font-size: 16px;
  }
}
</style>
