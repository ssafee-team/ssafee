<template>
  <div class="modal" @click="closeModal">
    
    <div class="modal-content">
      <div class="modal-title">주문하기</div>
      <hr />
      <input type="text" v-model="nameInput" placeholder="이름을 입력하세요" />
      <div class="modal-buttons">
        <button @click="closeModal" class="close">취소</button>
        <button @click="confirmOrder" class="confirm">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
import { createOrder } from "@/api/party";

export default {
  props: {
    orders: Array,
    code: {
      required: true,
    },
  },
  data() {
    return {
      nameInput: "",
      // partyCode: "Gqe3GwHFoK",
    };
  },
  methods: {
    closeModal() {
      this.$emit("close");
    },
    confirmOrder() {
      if (!this.nameInput.trim()) {
        alert("이름을 입력하세요.");
        return;
      }

      // 주문 정보를 저장할 배열 초기화
      const ordersData = [];

      // 모든 주문에 대한 정보 추출
      this.orders.forEach((order) => {
        const orderData = {
          menu_id: order.menuId,
          participant_name: this.nameInput,
          option_categories: order.option_categories,
        };

        // 주문 정보를 배열에 추가
        // ordersData.push(orderData);
        createOrder(this.code, orderData, this.handleOrderSuccess, this.handleOrderFail);
      });

      // 최종 주문 정보 객체 생성
      // const orderInfo = {
      //   orders: ordersData,
      // };
      // const orderInfo = ordersData[0]; //현재 메뉴 한개만 들어감

      // console.log("주문 정보:", orderInfo);
      // console.log(this.partyCode);

      // createOrder(this.code, orderInfo, this.handleOrderSuccess, this.handleOrderFail);

      // 모달 닫기
      this.closeModal();
    },
    handleOrderSuccess(response) {
      // console.log("주문 성공: ", response);
      window.location.reload();
    },
    handleOrderFail(error) {
      console.error("주문 실패:", error);
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
  font-weight: bold;
  font-size: 18px;
}

@media screen and (max-width: 768px) {

  .modal-content{
    width: 80%;
  }

  .modal-title{
    font-size: 16px;
  }
  input{
    font-size: 14px;
  }
  .close, .confirm{
    width: 100px;
    font-size: 14px;
  }

}
</style>
