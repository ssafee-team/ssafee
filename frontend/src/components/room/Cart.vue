<template>
  <!-- 탭 형태로 My Cart와 Our Cart를 표시 -->
  <div class="tabs">
    <div class="tab" :class="{ active: activeTab === 'My Cart' }" @click="activeTab = 'My Cart'">
      My Cart
    </div>
    <div class="tab" :class="{ active: activeTab === 'Our Cart' }" @click="activeTab = 'Our Cart'">
      Our Cart
    </div>
  </div>

  <!-- My Cart의 내용 -->
  <div v-if="activeTab === 'My Cart'" class="content">
    <div class="order-list">
      <div v-for="(order, index) in orders" :key="index" class="order">
        <!-- 선택한 메뉴와 가격 -->
        <div class="item">
          {{ order.name }} - {{ order.price }}원
          <span class="remove" @click="removeOrder(index)">X</span>
        </div>
        <!-- 선택한 옵션과 가격 -->
        <div v-for="(option, idx) in order.option_categories" :key="idx" class="option">
          <template v-if="option.option_names && option.option_names.length > 0">
            ㄴ{{ option.option_names.join(", ") }} - {{ option.option_prices.join(", ") }}원
          </template>
        </div>
      </div>
    </div>
    <!-- 총 주문 금액 -->
    <div class="footer">
      <div class="total">
        <div>총 주문금액</div>
        <div class="total-price">{{ calculateTotalPrice() }}원</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    orders: {
      type: Array,
      required: true,
    },
  },

  data() {
    return {
      activeTab: "My Cart", // 기본적으로 My Cart가 선택됨
    };
  },

  methods: {
    removeOrder(index) {
      // 선택한 주문을 삭제
      this.orders.splice(index, 1);
    },
    calculateTotalPrice() {
      // 주문 내역의 총 주문 금액을 계산
      let total = 0;
      for (const order of this.orders) {
        total += parseFloat(order.price);
      }
      return total.toFixed(0);
    },
  },
};
</script>

<style scoped>
/* 탭 스타일 */
.tabs {
  display: flex;
  height: 40px;
  justify-content: center;
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

.tab.active {
  background-color: #343844;
  border-radius: 3px 3px 0 0;
  /* border-radius: 10px; */
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
  cursor: pointer;
  color: red;
}

.option {
  /* color: #FFFFFF; */
  margin-left: 20px;
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

.footer {
  /* width: 100%; */
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
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
</style>
