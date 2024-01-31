<template lang="">
  <!-- 모달 -->
  <div class="black-bg" v-if="openModal == true" @click="close($event)">
    <div class="white-bg" :style="{ maxHeight: modalMaxHeight }">
      <div class="modal-title">
        <div>옵션</div>
        <div style="color: #00a7d0">{{ calculateTotalPrice() }}원</div>
      </div>
      <hr />
      <div class="modal-content">
        <p>휘핑선택</p>
        <div class="choice">
          <div class="row" v-for="option in whippingOptions" :key="option.value">
            <label>
              <input type="checkbox" :value="option.label" v-model="selectedOptions" />
              {{ option.label }}
            </label>
            <div>+ {{ option.price }}원</div>
          </div>
        </div>
        <p>추가선택</p>
        <div class="choice">
          <div class="row" v-for="option in additionalOptions" :key="option.value">
            <label>
              <input type="checkbox" :value="option.label" v-model="selectedOptions" />
              {{ option.label }}
            </label>
            <div>+ {{ option.price }}원</div>
          </div>
        </div>
      </div>
      <div class="modal-btn">
        <button class="close" @click="close">취소</button>
        <button class="addOrder" @click="addOrder">주문하기</button>
      </div>
    </div>
  </div>
  <!-- 메뉴 카테고리 -->
  <div class="menu-categories">
    <div
      v-for="(category, index) in categories"
      :key="index"
      @click="selectCategory(index)"
      :class="{ selected: selectedCategory === index }"
    >
      {{ category }}
    </div>
  </div>
  <!-- 메뉴판 -->
  <div class="menu-items">
    <div
      v-for="(drink, index) in selectedDrinks"
      :key="index"
      class="drink-item"
      :style="{ width: drinkItemWidth }"
    >
      <!-- 이미지 들어가는거 다시 다뤄야 함-->
      <img
        src="../../assets/img/blueberry.jpg"
        :alt="drink.name"
        @click="
          openModal = true;
          setSelectedDrinkIndex(index);
        "
      />
      <div>{{ drink.name }}</div>
      <div class="price">{{ drink.price }}</div>
    </div>
  </div>
  <order-summary
    :order-list="orderList"
    :order-summary-visible="orderSummaryVisible"
    @toggle-order-summary="toggleOrderSummary"
  ></order-summary>
</template>
<script>
import OrderSummary from "./OrderSummary.vue";
export default {
  components: {
    OrderSummary,
  },

  data() {
    return {
      openModal: false, //모달 기본적으로 안보이게 설정
      modalMaxHeight: "80%",
      checkedOptions: {},
      categories: [
        "인기메뉴",
        "시즌메뉴",
        "커피",
        "디카페인",
        "베버리지",
        "스무디/프라페",
        "밀크쉐이크",
        "에이드/주스",
        "티",
        "기타",
      ],
      drinks: {
        0: [
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry", price: "2,000원" },
        ],
        1: [
          { name: "블루베리스무디", photo: "blueberry.jpg", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry.jpg", price: "2,000원" },
          { name: "블루베리스무디", photo: "blueberry.jpg", price: "2,000원" },
        ],
      },
      whippingOptions: [
        { value: "1", label: "휘핑제공", price: 0 },
        { value: "2", label: "휘핑미제공", price: 0 },
        { value: "3", label: "휘핑적게", price: 0 },
        { value: "4", label: "휘핑많이", price: 500 },
        { value: "5", label: "휘핑많이2", price: 500 },
      ],
      additionalOptions: [
        { value: "6", label: "설탕시럽 1펌프 추가", price: 500 },
        { value: "7", label: "설탕시럽 2펌프 추가", price: 1000 },
        { value: "8", label: "샷 추가", price: 500 },
        { value: "9", label: "펄 추가", price: 500 },
        { value: "10", label: "펄 추가2", price: 1000 },
      ],
      selectedOptions: [],
      selectedCategory: 0,
      drinkItemWidth: "20%", //각 음료 항목의 너비
      selectedDrinkIndex: null, //선택한 음료의 인덱스를 기억하는 데이터 추가
      orderList: [], //주문 내역을 담을 배열 추가
    };
  },
  computed: {
    selectedDrinks() {
      console.log(this.categories[this.selectedCategory], "선택");
      return this.drinks[this.selectedCategory] || [];
    },
    selectedDrink() {
      console.log(this.selectedDrinks[this.selectedDrinkIndex].name, "음료선택");
      return this.selectedDrinks[this.selectedDrinkIndex] || {};
    },
  },
  methods: {
    selectCategory(index) {
      this.selectedCategory = index;
      // console.log(this.selectedCategory);
    },
    setSelectedDrinkIndex(index) {
      this.selectedDrinkIndex = index;
      // console.log(this.selectedDrinkIndex);
    },

    close(event) {
      if (event.target.classList.contains("black-bg") || event.target.classList.contains("close")) {
        // this.openModal = false;
        this.closeModal();
      } else if (event.target.classList.contains("white-bg")) {
        this.openModal = true;
      }
    },
    closeModal() {
      this.openModal = false;
      //모달 닫힐 시 선택한 옵션 초기화
      this.selectedOptions = [];
    },

    calculateTotalPrice() {
      let total = parseFloat(this.selectedDrink.price.replace("원", "").replace(",", ""));
      console.log("현재", total);

      //선택한 옵션들의 가격을 합산
      for (const option of this.selectedOptions) {
        const checkedOption = [...this.whippingOptions, ...this.additionalOptions].find(
          (opt) => opt.label === option
        );
        if (checkedOption) {
          total += checkedOption.price;
        }
      }
      // total += this.whippingOptions.find((opt) => opt.value === option).price;

      return total.toFixed(0);
    },

    addOrder() {
      // const selectedOptions = Object.keys(this.checkedOptions).filter(
      //   (option) => this.checkedOptions[option]
      // );
      //주문 정보 정리
      const order = {
        name: this.selectedDrink.name,
        price: this.calculateTotalPrice(),
        options: this.selectedOptions,
      };
      //주문 정보를 orderList에 추가
      this.orderList.push(order);

      console.log("주문하기 버튼 클릭!");

      console.log(order);
      console.log("전체 주문 목록", this.orderList);
      //부모 컴포넌트에 이벤트 발생시켜 주문 정보를 전달
      this.$emit("order-placed", order);

      //모달 닫기
      this.closeModal();
    },
  },
};
</script>
<style scoped>
.menu-categories {
  display: flex;
  flex-wrap: wrap;
  text-align: center;
  color: #6a7793;
  border: 1px solid rgb(209, 204, 204);
  /* shadow 수정 필요 */
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  border-radius: 10px;
}

.menu-categories > div {
  flex: 1 0 20%; /* 확장 가능, 축소 불가능, 최대 너비 20% */
  cursor: pointer;
  padding: 10px;
  box-sizing: border-box;
  font-size: 20px;
  font-weight: bold;
}

.menu-categories > div.selected {
  background-color: #97afba;
  border-radius: 10px;
}

.menu-items {
  display: flex;
  font-weight: bold;
  margin-top: 20px;
  flex-wrap: wrap;
  overflow-y: auto;
  height: 600px;
}

.menu-items::-webkit-scrollbar {
  display: none;
}

.drink-item {
  text-align: center;
  box-sizing: border-box;
  padding: 10px;
}

.drink-item img {
  border: 1px solid #344a53;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  border-radius: 15px;
  cursor: pointer;
  width: 120px;
  height: 120px;
  margin-bottom: 10px;
}
.price {
  margin-top: 10px;
  color: #00a7d0;
}
.black-bg {
  width: 100%;
  height: 100%;
  /* 컴포넌트가 분리되어 있어서 백그라운드 컬러가 나뉘어짐 */
  /* background: rgba(0, 0, 0, 0.6); */
  position: fixed;
  z-index: 9999;
  /* 모달 창을 제외한 모든 요소는 모달 창 뒤로 위치*/
}
.white-bg {
  width: 40%;
  margin: 80px auto;
  background: #344a53;
  border-radius: 5px;
  padding: 10px;
  margin-top: 20px;
  border: 1px solid black;
  text-align: center;
  /* height: 60%; */
  color: white;
  position: relative;
  z-index: 10000;
  border: none;
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
.addOrder {
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

.modal-title {
  /* 상우좌하 */
  margin: 0px 10px 10px 5px;
  padding: 10px;

  font-size: 20px;
  font-weight: bold;
  text-align: left;
  justify-content: space-between;
  display: flex;
  position: sticky;
}

.modal-content {
  max-height: 400px;
  overflow-y: auto;
}

/* Webkit 브라우저용 스크롤바 숨기기 */
.modal-content::-webkit-scrollbar {
  display: none; /* 스크롤바 숨기기 */
}

.modal-btn {
  position: sticky;
  bottom: 0;
  padding: 10px;
}

p {
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  margin-left: 70px;
  margin-top: 20px;
}

.choice {
  display: flex;
  flex-direction: column;
  padding: 10px;
  margin-left: 70px;
}
.row {
  font-size: 18px;
  display: flex;
  margin-bottom: 20px;
  justify-content: space-between;
}
</style>
