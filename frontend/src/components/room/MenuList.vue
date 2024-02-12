<template lang="">
  <!-- 메뉴 카테고리 -->
  <div class="menu-categories">
    <div
      v-for="(category, index) in categories"
      :key="index"
      @click="selectCategory(index)"
      :class="{ selected: selectedCategory === index }"
    >
      {{ category.name }}
    </div>
  </div>

  <!-- 메뉴판 -->
  <div class="menu-content">
    <div class="menu-items" v-show="!showOptions">
      <div
        v-for="(drink, index) in selectedDrinks"
        :key="index"
        class="drink-item"
        :style="{ width: drinkItemWidth }"
        @click="toggleOptions(index)"
      >
        <img :src="drink.image" :alt="drink.name" />
        <div class="drink-name">{{ drink.name }}</div>
        <div class="price">{{ drink.price }}원</div>
      </div>
    </div>
    <!-- 선택한 음료의 옵션 화면 -->
    <div v-show="showOptions" class="options-container">
      <div v-if="selectedDrinkIndex !== null" class="options-content">
        <div class="options-title">
          <div>{{ selectedDrink.name }}</div>
          <div class="menu-price">{{ selectedDrink.price }}원</div>
          <button class="close-btn" @click="closeOptions">X</button>
        </div>
        <hr />
        <div
          v-for="optionCategory in optionCategories"
          :key="optionCategory.id"
          class="options-info"
        >
          <p>{{ optionCategory.name }}</p>
          <div class="choice">
            <div class="row" v-for="option in optionCategory.options" :key="option.id">
              <label>
                <input type="checkbox" :value="option.id" v-model="selectedOptions" />
                {{ option.name }}
              </label>
              <div class="options-price">+ {{ option.price }}원</div>
            </div>
          </div>
        </div>
      </div>
      <div class="options-footer">
        <div class="total-info">
          <div>총 금액</div>
          <div class="total-price">{{ calculateTotalPrice() }}</div>
        </div>
        <button class="add" @click="addOrder">주문하기</button>
      </div>
    </div>
  </div>
  <!-- <order-summary :order-list="orderList" :code="code"></order-summary> -->
</template>
<script>
import { getMenuCategories, getMenusByCategory, getOptionCategory } from "@/api/shop";
import OrderSummary from "./OrderSummary.vue";
import { createOrder } from "@/api/party";

export default {
  props: {
    shopId: {
      required: true,
    },
    code: {
      type: String,
      required: true,
    },
  },

  components: {
    OrderSummary,
  },

  data() {
    return {
      categories: [],
      drinks: [],
      optionCategories: [], // 옵션 카테고리를 담을 변수 추가
      options: [], // 선택된 옵션을 담을 변수 추가
      optionCategoriesMap: {},
      selectedOptions: [],
      selectedCategory: 0,
      drinkItemWidth: "20%", //각 음료 항목의 너비
      selectedDrinkIndex: null, //선택한 음료의 인덱스를 기억하는 데이터 추가
      orderList: [], //주문 내역을 담을 배열 추가
      showOptions: false, //옵션화면 상태변수
    };
  },
  mounted() {
    //shopId를 기반 메뉴 카테고리 데이터 가져오기
    getMenuCategories(this.shopId, this.handleSuccess, this.handleFail);

    //페이지 렌더링 시 첫 번째 카테고리를 선택하지 않고 기본적으로 보여줌
    this.selectCategory(0);
    console.log("22", this.code);
  },

  computed: {
    selectedDrinks() {
      // console.log(this.categories[this.selectedCategory], "선택");
      return this.drinks[this.selectedCategory] || [];
    },
    selectedDrink() {
      // console.log(this.selectedDrinks[this.selectedDrinkIndex].name, "음료선택");
      return this.selectedDrinks[this.selectedDrinkIndex] || {};
    },
  },
  methods: {
    toggleOptions(index) {
      if (event.target.closest(".drink-item")) {
        this.setSelectedDrinkIndex(index);
      }
      // 옵션을 토글
      this.showOptions = !this.showOptions;
    },
    closeOptions() {
      this.selectedOptions = []; //선택한 옵션 초기화
      this.optionCategories = []; //메뉴의 옵션카테고리 초기화
      this.showOptions = !this.showOptions;
      console.log(this.showOptions, "닫");
    },

    handleSuccess(response) {
      //데이터를 비동기적으로 불러올 경우, response 받아서 response.data로 세팅하기
      //프록시 객체의 data 속성을 이용해 접근
      //받은 데이터를 categories에 세팅
      this.categories = response.data;
      // console.log("카테고리 출력", this.categories);
    },
    handleFail(error) {
      console.error(error);
    },

    selectCategory(index) {
      // 다른 카테고리가 선택될 때 optionCategoriesMap을 초기화
      this.optionCategoriesMap = {};

      this.showOptions = false;
      console.log(this.showOptions, "zk");
      // this.selectedDrinkIndex = null;
      this.selectedOptions = [];
      //카테고리 선택시 실행
      this.selectedCategory = index;
      // shopId와 mcId를 기반으로 카테고리 선택 시 메뉴 데이터 가져오기
      getMenusByCategory(this.shopId, index + 1, this.handleMenuSuccess, this.handleFail);
    },

    handleMenuSuccess(response) {
      const menuData = response.data;
      // 각 메뉴의 옵션 카테고리 데이터 저장
      menuData.forEach((menu) => {
        menu.option_categories.forEach((optionCategory) => {
          // 옵션 카테고리를 저장할 때 메뉴의 id를 key로 사용
          if (!this.optionCategoriesMap[menu.id]) {
            this.optionCategoriesMap[menu.id] = [];
          }
          this.optionCategoriesMap[menu.id].push(optionCategory);
        });
      });

      this.drinks[this.selectedCategory] = menuData;
      // console.log("메뉴 가져왔니?", this.drinks);
      // 현재 선택된 메뉴의 옵션 카테고리와 옵션 초기화
      if (this.selectedDrinkIndex !== null) {
        this.optionCategories = [];
        this.options = [];
      }
    },

    setSelectedDrinkIndex(index) {
      this.selectedDrinkIndex = index;

      const selectedDrink = this.selectedDrinks[index];
      console.log(selectedDrink.name, selectedDrink.price);
      const menuId = selectedDrink.id;
      console.log("선택한메뉴아이디확인", menuId);
      if (this.optionCategoriesMap[menuId]) {
        this.optionCategories = this.optionCategoriesMap[menuId];
        console.log(this.optionCategories, "dd");
      } else {
        // 저장된 데이터가 없을 경우 API를 통해 불러옴
        selectedDrink.option_categories.forEach((optionCategory) => {
          this.loadOptionCategories(optionCategory.id);
        });
      }
    },

    loadOptionCategories(optionCategoryId) {
      // 음료의 id를 기반으로 옵션 카테고리 데이터 가져오기
      getOptionCategory(
        this.shopId,
        optionCategoryId, //옵션 카테고리 id 전달
        this.handleOptionCategorySuccess, //성공 콜백
        this.handleFail //실패 콜백
      );
    },

    handleOptionCategorySuccess(response) {
      // 모달이 열릴 때마다 옵션 카테고리 데이터 업데이트
      this.optionCategories = response.data;
      console.log("옵션가져올게요", this.optionCategories);
      if (this.optionCategories) {
        this.options = this.optionCategories[0].options;
      }
    },

    calculateTotalPrice() {
      let total = parseFloat(this.selectedDrink.price);

      // 선택한 옵션들의 가격을 합산
      for (const optionCategory of this.optionCategories) {
        for (const option of optionCategory.options) {
          if (this.selectedOptions.includes(option.id)) {
            total += option.price;
          }
        }
      }

      return total.toFixed(0);
    },

    addOrder() {
      console.log("담기클릭");
      const selectedDrink = this.selectedDrinks[this.selectedDrinkIndex];
      const selectedDrinkId = selectedDrink.id; // 선택한 음료의 ID 가져오기

      // 선택한 옵션 카테고리와 그에 해당하는 옵션들의 ID를 추출
      const selectedOptionCategories = this.optionCategories.map((optionCategory) => {
        return {
          option_category_id: optionCategory.id,
          option_ids: optionCategory.options
            .filter((option) => this.selectedOptions.includes(option.id))
            .map((option) => option.id),
          option_names: optionCategory.options
            .filter((option) => this.selectedOptions.includes(option.id))
            .map((option) => option.name),
          option_prices: optionCategory.options
            .filter((option) => this.selectedOptions.includes(option.id))
            .map((option) => option.price),
        };
      });

      //주문 정보 정리(Cart에 보내는 용도)
      const order = {
        name: this.selectedDrink.name,
        price: this.calculateTotalPrice(),

        option_names: selectedOptionCategories.reduce((acc, category) => {
          return acc.concat(category.option_names);
        }, []), //선택한 옵션 명
        option_prices: selectedOptionCategories.reduce((acc, category) => {
          return acc.concat(category.option_prices);
        }, []), //선택한 옵션 가격
        menuId: selectedDrinkId, //선택한 메뉴 ID
        option_categories: selectedOptionCategories, // 선택한 옵션 카테고리와 그에 해당하는 옵션들의 ID
      };

      // console.log(order.option_names);
      // console.log("주문하기 버튼 클릭!");

      // 주문 정보를 서버로 보내기 위해 데이터 형식 맞춰주기 (백단에 보내는 용도)
      const orderData = {
        menu_id: order.menuId,
        participant_name: "전상", // 주문자 이름
        option_categories: order.option_categories.map((category) => {
          return {
            option_category_id: category.option_category_id,
            option_ids: category.option_ids,
          };
        }),
      };

      // createOrder 함수를 호출하여 서버로 주문 정보를 보냄
      // createOrder(this.code, orderData, this.handleOrderSuccess, this.handleOrderFail);
      createOrder(this.code, orderData, this.handleOrderSuccess, this.handleOrderFail);

      console.log(order);
      // console.log("전체 주문 목록", this.orderList);

      //주문 정보를 orderList에 추가
      // this.orderList.push(order);
      //부모 컴포넌트에 이벤트 발생시켜 주문 정보를 전달
      // this.$emit("order-added", this.orderList);
      window.location.reload();
    },
  },
};
</script>
<style lang="scss" scoped>
.menu-categories {
  display: flex;
  flex-wrap: wrap;
  text-align: center;
  //color: #ffffff;
  max-height: 180px;
  border: 3px solid #1e293b;
  border-radius: 10px;
  box-sizing: inherit;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.menu-categories > div {
  flex: 1 0 20%; /* 확장 가능, 축소 불가능, 최대 너비 20% */
  cursor: pointer;
  display: flex;
  justify-content: center;
  height: 40px;
  padding: 5px;
  margin: 5px;
  box-sizing: inherit;
  /* box-sizing: border-box; */
  font-size: 18px;
  font-weight: bold;
}

.menu-categories > div.selected {
  background-color: #343844;
  width: auto;
  // box-sizing: inherit;
  border-radius: 10px;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.menu-content {
  display: flex;
  flex-direction: column;
  height: 465px;
  border: 3px solid #1e293b;
  margin-top: 20px;
  border-radius: 10px;
  // box-sizing: border-box;
  // box-sizing: inherit;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.menu-items {
  display: flex;
  font-weight: bold;
  flex-wrap: wrap;
  overflow-y: auto;
}

.menu-items::-webkit-scrollbar {
  display: none;
}

.options-container {
  //color: #ffffff;
  // display: flex;
  font-weight: bold;
  // flex-wrap: wrap;
  // overflow-y: auto;
  box-sizing: inherit;
}

.options-title {
  display: flex;
  justify-content: space-between;
  margin: 20px;
  font-size: 20px;
}

.options-content {
  display: flex;
  flex-direction: column;
  // text-align: right;
  margin: 10px;
  height: 390px;
  max-height: 390px;
  overflow-y: auto;
  box-sizing: inherit;
}

.options-content::-webkit-scrollbar {
  display: none;
}

.options-info {
  justify-content: center;
}

.options-footer {
  display: flex;
  width: 100%;
  // height: auto;
  justify-content: space-between;
  border-radius: 5px;
  // font-weight: bold;

  font-size: 20px;
  // background-color: #343844;
  position: sticky;
  box-sizing: inherit;
}

hr {
  width: 100%;
  // background-color: #1e293b
}

.menu-price,
.options-price,
.total-price {
  color: #00a5e7;
}

.total-info {
  width: 70%;
  display: flex;
  gap: 20px;
  margin-left: 20px;
  font-weight: bold;
  // padding: 10px;
  align-items: center;
}

input[type="checkbox"] {
  accent-color: #00a5e7;
}

.add {
  cursor: pointer;
  background-color: #00a5e7;
  // background-color: #020817;
  border: 0px;
  font-weight: bold;
  color: #ffffff;
  font-size: 20px;
  margin: 10px;
  border-radius: 10px;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.close-btn {
  cursor: pointer;
  // background-color: #020817;
  background-color: #ffffff;
  border: 0px;
  font-size: 20px;
  // padding: 10px;
  // margin: 10px;
  font-weight: bold;
  //color: #ffffff;
}

.drink-item {
  text-align: center;
  height: 227px;
  box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}

.drink-item:hover {
  background-color: #343844; /* 호버 시 배경색 변경 */
  border-radius: 5px;
}

.drink-item img {
  /* border: 1px solid #344a53; */
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  border-radius: 15px;
  cursor: pointer;
  width: 100px;
  height: 100px;
  margin-bottom: 10px;
}

.drink-name {
  display: flex;
  justify-content: center;
  padding: 5px;
  height: 40px;
  //color: #ffffff;
}

.price {
  margin-top: 10px;
  color: #00a7d0;
}

.addOrder {
  width: 120px;
  cursor: pointer;
  border: none;

  background: #00a5e7;
  font-weight: bold;
  border-radius: 5px;
  padding: 10px 15px;
  font-weight: bold;
  font-size: 18px;
}

p {
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  margin-left: 20px;
  margin-top: 20px;
}

.choice {
  display: flex;
  flex-direction: column;
  padding: 10px;
  margin-left: 40px;
}
.row {
  font-size: 18px;
  display: flex;
  margin-bottom: 20px;
  justify-content: space-between;
}

@media screen and (max-width: 768px) {
  /* 모바일일 경우 스타일 조정 */
  .drink-item img {
    width: 80px; /*작은 화면에 맞게 이미지 너비 조정  */
    height: 80px; /* 이미지 높이 조정  */
  }

  .drink-item {
    font-size: 12px;
  }

  .menu-categories > div {
    font-size: 14px; /* 작은 화면에 맞게 메뉴 카테고리 텍스트 크기 조정 */
    font-weight: bold;
  }

  .close-btn,
  .addOrder {
    width: 100px; /* 작은 화면에 맞게 모달 버튼 너비 조정 */
    font-size: 14px; /* 작은 화면에 맞게 모달 버튼 텍스트 크기 조정 */
  }
}
</style>
