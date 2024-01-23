<template lang="">
  <div class="black-bg" v-if="openModal == true" @click="close($event)">
    <div class="white-bg">
      <div class="modal-title">
        <div>옵션</div>
        <div style="color: #00a7d0">5,500원</div>
      </div>
      <hr />
      <p>휘핑선택</p>
      <p>추가선택</p>
      <button class="close">닫기</button>
    </div>
  </div>
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
  <div class="menu-items">
    <div
      v-for="(drink, index) in selectedDrinks"
      :key="index"
      class="drink-item"
      :style="{ width: drinkItemWidth }"
    >
      <!-- 이미지 들어가는거 다시 다뤄야 함-->
      <img src="../../assets/img/blueberry.jpg" :alt="drink.name" @click="openModal = true" />
      <div>{{ drink.name }}</div>
      <div class="price">{{ drink.price }}</div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      openModal: false, //모달 기본적으로 안보이게 설정

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
      selectedCategory: 0,
      drinkItemWidth: "20%", //각 음료 항목의 너비
    };
  },
  computed: {
    selectedDrinks() {
      return this.drinks[this.selectedCategory] || [];
    },
  },
  methods: {
    selectCategory(index) {
      this.selectedCategory = index;
    },
    close(event) {
      if (event.target.classList.contains("black-bg") || event.target.classList.contains("close")) {
        this.openModal = false;
      } else if (event.target.classList.contains("white-bg")) {
        this.openModal = true;
      }
    },
  },
};
</script>
<style>
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
  overflow-y: scroll;
  height: 500px;
}

.drink-item {
  text-align: center;
  box-sizing: border-box;
  padding: 10px;
}

.drink-item img {
  border: 3px solid black;
  border-radius: 15px;
  cursor: pointer;
  width: 160px;
  height: 160px;
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
}
.white-bg {
  width: 40%;
  height: 300px;
  margin: 80px auto;
  background: #344a53;
  border-radius: 5px;
  padding: 10px 0;
  border: 2px solid black;
  text-align: center;

  color: white;
}
.close {
  width: 100px;
  cursor: pointer;
  border: none;
  background: #6a7793;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  padding: 5px 15px;
}
.close:hover {
  /* color: white; */
  /* font-weight: bold; */
  /* transform: scale(1.1); */
  /* transition: all 0.5s; */
}
.modal-title {
  /* 상우좌하 */
  margin: 0px 10px 10px 5px;
  padding: 5px;
  /* margin-left: 10px; */
  /* margin-bottom: 5px; */
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  justify-content: space-between;
  display: flex;
}
p {
  font-size: 20px;
  font-weight: bold;
}
</style>
