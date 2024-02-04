<template>
  <div class="modal" @click="closeModal">
    <div class="modal-content">
      <div class="modal-title">
        <div>전체 주문 현황</div>
        <button @click="closeModal" class="close">X</button>
      </div>
      <hr />
      <div class="order-list">
        <div class="row" v-for="order in orderList" :key="order.id">
          <div class="name">{{ order.participant_name }}</div>
          <div class="menus">
            <div class="menu-info">
              <div class="menu-name">{{ order.chosen_menu.name }}</div>

              <div
                v-for="optionCategory in order.chosen_option_categories"
                :key="optionCategory.id"
              >
                <div
                  class="menu-option"
                  v-for="option in optionCategory.chosen_options"
                  :key="option.id"
                >
                  {{ option.name }}
                </div>
              </div>
            </div>
          </div>
          <div class="price">{{ calculateTotalPrice(order) }}원</div>
          <button @click="deleteOrder(order.id)" class="delete">X</button>
        </div>
      </div>
      <div class="modal-footer">
        <div>총</div>
        <div style="color: #00a7d0">{{ calculateTotalOrderPrice }}원</div>
      </div>
    </div>
  </div>
</template>

<script>
import { deleteOrderMenu } from "@/api/party";

export default {
  props: {
    orderList: Array,
    code: {
      required: true,
    },
  },

  methods: {
    closeModal() {
      this.$emit("close");
    },
    calculateTotalPrice(order) {
      let totalPrice = order.chosen_menu.price;
      for (const category of order.chosen_option_categories) {
        for (const option of category.chosen_options) {
          totalPrice += option.price;
        }
      }
      return totalPrice;
    },
    deleteOrder(orderId) {
      // this.orderList.splice(index, 1); // 주문 리스트에서 해당 인덱스의 주문 삭제

      const index = this.orderList.findIndex((order) => order.id === orderId);
      if (index !== -1) {
        // 서버에 삭제 요청 보내기
        deleteOrderMenu(
          this.code,
          orderId,
          () => {
            //성공 시 주문 리스트에서 해당 주문 삭제
            this.orderList.splice(index, 1);
          },
          (error) => {
            //실패 시
            console.error("주문 삭제 실패:", error);
          }
        );
      }
    },
  },
  computed: {
    calculateTotalOrderPrice() {
      return this.orderList.reduce((total, order) => total + this.calculateTotalPrice(order), 0);
    },
  },
};
</script>

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
  width: 80px; /* 고정된 너비 부여 */
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
  .modal-content{
    width: 80%; /* 작은 화면에 맞게 모달 너비 조정 */
    margin: 50px auto; /* 모달 위치 조정 */
  }

  .modal-title{
    font-size: 16px;
  }
  .name, .menu-name, .price{
    font-size: 14px;
  }
  .modal-footer{
    font-size: 16px;
  }
  
}
</style>
