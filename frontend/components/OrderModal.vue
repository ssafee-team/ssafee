<script setup lang="ts">
// Define props and emits
const props = defineProps<{
  orders: Array<Order>
  code: string
}>()

const emit = defineEmits(['close'])

// Reactive state
const nameInput = ref('')

// Methods
function closeModal() {
  emit('close')
}

function confirmOrder() {
  if (!nameInput.value.trim()) {
    alert('이름을 입력하세요.')
    return
  }

  // Loop through each order and create it
  props.orders.forEach((order) => {
    const orderData = {
      menuId: order.menuId,
      participantName: nameInput.value,
      optionCategories: order.optionCategories,
    }

    createOrder(props.code, orderData, (response) => {
      // Order creation success logic here
      window.location.reload()
    }, (error) => {
      // Order creation failure logic here
      console.error('주문 실패:', error)
    })
  })

  // Close modal after order creation
  closeModal()
}
</script>

<template>
  <div class="modal">
    <div class="modal-content">
      <div class="modal-title">
        주문하기
      </div>
      <hr>
      <input v-model="nameInput" type="text" placeholder="이름을 입력하세요">
      <div class="modal-buttons">
        <button class="close" @click="closeModal">
          취소
        </button>
        <button class="confirm" @click="confirmOrder">
          확인
        </button>
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
  width: 30%;
  height: 200px;
  background: #344a53;
  border-radius: 5px;
  padding: 20px;
  margin-top: 20px;
  /* border: 1px solid black; */
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

.modal-buttons {
  margin-top: 40px;
  /* position: sticky; */
}

input {
  width: 100%;
  height: 30px;
  font-weight: bold;
  font-size: 16px;
  margin-top: 10px;
}

.close {
  width: 120px;
  cursor: pointer;
  border: none;
  background: #eb4e5a;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  padding: 10px 15px;
  font-weight: bold;
  font-size: 18px;
  margin-right: 10px;
}

.confirm {
  width: 120px;
  cursor: pointer;
  border: none;
  background: #00a7d0;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  padding: 10px 15px;
  font-size: 18px;
}

@media screen and (max-width: 768px) {
  .modal-content {
    width: 80%;
  }

  .modal-title {
    font-size: 16px;
  }

  input {
    font-size: 14px;
  }

  .close,
  .confirm {
    width: 100px;
    font-size: 14px;
  }
}
</style>
