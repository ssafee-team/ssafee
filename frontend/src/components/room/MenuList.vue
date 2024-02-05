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
        <div v-for="optionCategory in optionCategories" :key="optionCategory.id">
          <p>{{ optionCategory.name }}</p>
          <div class="choice">
            <div class="row" v-for="option in optionCategory.options" :key="option.id">
              <label>
                <input type="checkbox" :value="option.id" v-model="selectedOptions" />
                {{ option.name }}
              </label>
              <div>+ {{ option.price }}원</div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-btn">
        <button class="close" @click="close">취소</button>
        <button class="addOrder" @click="addOrder">담기</button>
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
      {{ category.name }}
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
      <img
        :src="drink.image"
        :alt="drink.name"
        @click="
          openModal = true;
          setSelectedDrinkIndex(index);
        "
      />
      <div>{{ drink.name }}</div>
      <div class="price">{{ drink.price }}원</div>
    </div>
  </div>
  <order-summary :order-list="orderList" :code="code"></order-summary>
</template>
<script>
import { getMenuCategories, getMenusByCategory, getOptionCategory } from "@/api/shop";
import OrderSummary from "./OrderSummary.vue";

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
      openModal: false, //모달 기본적으로 안보이게 설정
      modalMaxHeight: "80%",
      checkedOptions: {},
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
    };
  },
  mounted() {
    //shopId를 기반 메뉴 카테고리 데이터 가져오기
    getMenuCategories(this.shopId, this.handleSuccess, this.handleFail);

    //페이지 렌더링 시 첫 번째 카테고리를 선택하지 않고 기본적으로 보여줌
    this.selectCategory(0);
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
    },

    setSelectedDrinkIndex(index) {
      this.selectedDrinkIndex = index;

      const selectedDrink = this.selectedDrinks[index];
      const menuId = selectedDrink.id;
      // console.log("선택한메뉴아이디확인", menuId);
      if (this.optionCategoriesMap[menuId]) {
        this.optionCategories = this.optionCategoriesMap[menuId];
        // console.log(this.optionCategories, "dd");
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
      if (this.optionCategories) {
        this.options = this.optionCategories[0].options;
      }
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
      // 모달 닫힐 때 선택한 옵션 및 카테고리 초기화
      this.selectedOptions = [];
      this.optionCategories = [];
      this.options = [];
      this.openModal = false;
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
        };
      });

      //주문 정보 정리
      const order = {
        name: this.selectedDrink.name,
        price: this.calculateTotalPrice(),
        // options: this.selectedOptions, //선택한 옵션  명
        option_names: selectedOptionCategories.reduce((acc, category) => {
          return acc.concat(category.option_names);
        }, []), //선택한 옵션 명
        menuId: selectedDrinkId, //선택한 메뉴 ID
        option_categories: selectedOptionCategories, // 선택한 옵션 카테고리와 그에 해당하는 옵션들의 ID
      };
      //주문 정보를 orderList에 추가
      this.orderList.push(order);

      // console.log(order.option_names);
      // console.log("주문하기 버튼 클릭!");

      // console.log(order);
      // console.log("전체 주문 목록", this.orderList);
      //부모 컴포넌트에 이벤트 발생시켜 주문 정보를 전달
      this.$emit("order-placed", order);

      //모달 닫기
      this.closeModal();
    },
  },
};
</script>
<style lang="scss" scoped>
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
  /* box-sizing: border-box; */
  font-size: 18px;
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
  height: 500px;
}

.menu-items::-webkit-scrollbar {
  display: none;
}

.drink-item {
  text-align: center;
  box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}

.drink-item img {
  /* border: 1px solid #344a53; */
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
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  /* 컴포넌트가 분리되어 있어서 백그라운드 컬러가 나뉘어짐 */
  /* background: rgba(0, 0, 0, 0.6); */
  background-color: rgba(0, 0, 0, 0.5);
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

  .white-bg {
    width: 80%; /* 작은 화면에 맞게 모달 너비 조정 */
    margin: 50px auto; /* 모달 위치 조정 */
  }

  .modal-title {
    font-size: 16px; /* 작은 화면에 맞게 모달 제목 텍스트 크기 조정 */
  }

  .modal-content {
    max-height: 300px; /* 작은 화면에 맞게 모달 내용 영역 최대 높이 조정 */
  }

  .modal-content p {
    font-size: 16px;
  }

  .modal-btn {
    padding: 5px; /* 작은 화면에 맞게 모달 버튼 간격 조정 */
  }

  .close,
  .addOrder {
    width: 100px; /* 작은 화면에 맞게 모달 버튼 너비 조정 */
    font-size: 14px; /* 작은 화면에 맞게 모달 버튼 텍스트 크기 조정 */
  }

  .row {
    font-size: 14px;
  }
}
</style>
