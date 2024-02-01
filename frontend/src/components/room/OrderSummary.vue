<template lang="">
  <div class="order">
    <div class="title">
      <div style="display: flex">
        <p style="margin-left: 20px">전체주문금액 :</p>
        <p class="total-price" style="margin-left: 20px; color: #00a7d0">{{ totalPrice }}원</p>
        <button class="btn-order" @click="openOrderModal">주문하기</button>
      </div>
      <button class="btn-toggle" @click="toggleOrderSummary">
        {{ orderSummaryVisible ? "▼" : "▲" }}
      </button>
    </div>
    <hr />
    <ul v-show="orderList.length > 0" class="list">
      <li v-for="(order, index) in limitedOrderList" :key="index" class="order-list">
        <div class="order-name">{{ order.name }}</div>
        <div class="order-options">옵션: {{ getOrderOptions(order.options) }}</div>
        <div class="order-price">{{ order.price }}</div>
        <button class="btn-delete" @click="deleteOrder(index)">취소</button>
      </li>
    </ul>
    <OrderModal v-if="isOrderModalOpen" @close="closeOrderModal" :items="limitedOrderList" />
  </div>
</template>
<script>
import OrderModal from "./modal/OrderModal.vue";

export default {
  components: {
    OrderModal,
  },

  props: {
    orderList: Array,
    orderSummaryVisible: Boolean,
  },
  data() {
    return {
      isOrderModalOpen: false,
    };
  },
  computed: {
    limitedOrderList() {
      // 최대 2개까지의 주문만 보여주기
      return this.orderList.slice(0, 2);
    },
    totalPrice() {
      return this.orderList
        .reduce((total, order) => {
          return total + parseFloat(order.price.replace("원", "").replace(",", ""));
        }, 0)
        .toFixed(0);
    },
  },
  methods: {
    openOrderModal() {
      this.isOrderModalOpen = true;
    },
    closeOrderModal() {
      this.isOrderModalOpen = false;
    },
    toggleOrderSummary() {
      console.log("주문내역확인하자~");
      console.log("받은 데이터", this.orderList.value);
      this.$emit("toggle-order-summary");
    },
    getOrderOptions(options) {
      // options 배열에는 각 옵션의 문자열이 들어있음
      // 여기서 각 옵션의 value 값을 가져와서 출력할 수 있음
      return options.join(", ");
    },

    deleteOrder(index) {
      this.orderList.splice(index, 1); //해당 인덱스 요소를 제거
    },
  },
};
</script>
<style scoped>
.order {
  background-color: #344a53;
  color: white;
  border-radius: 10px 10px 0px 0px;
  height: 120px;
  max-height: 120px;
  /* width: 60%; */
  margin: 10px 0px 0px 20px;
  /* position: fixed; */
  /* bottom: 0; */
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
  margin-left: 20px;
  background-color: #00a5e7;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  margin-top: 10px;
  font-size: 14px;
  border: none;
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
}
.order-list {
  display: flex;
  justify-content: space-evenly;
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
  font-size: 16px;
}
.order-price {
  color: #00a7d0;
  font-size: 18px;
}
.btn-delete {
  width: 80px;
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
</style>
