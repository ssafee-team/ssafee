<template>
  <!-- 탭 형태로 My Cart와 Our Cart를 표시 -->
  <div class="tabs">
    <div class="tab">주문내역</div>
    <select v-model="sortMethod" @change="sortOrders">
      <option value="default">기본</option>
      <option value="userName">이름순</option>
      <option value="menuName">메뉴순</option>
    </select>
  </div>

  <!-- 주문내역 -->
  <div class="content">
    <div class="order-list">
      <div v-for="(order, index) in sortedOrders" :key="index" class="order">
        <!-- 선택한 메뉴와 가격 -->
        <div class="item" :class="{ checked: order.checked }" @click="toggleCheck(order)">
          <div class="menu-name">
            <input type="checkbox" v-model="order.checked" class="checkbox" @click.stop />
            <div>{{ order.menu.name }}</div>
          </div>
          <div class="menu-price">
            <div>{{ order.menu.price }}원</div>
          </div>
          <span class="remove" @click="removeOrder(order.id)">X</span>
        </div>
        <!-- 선택한 옵션과 가격 -->
        <div
          v-for="optionCategory in order.option_categories"
          :key="optionCategory.id"
          class="option"
        >
          <div
            class="menu-option"
            v-for="option in optionCategory.options"
            :key="option.id"
            :class="{ checked: order.checked }"
          >
            <div class="option-name">
              <div>ㄴ{{ option.name }}</div>
            </div>
            <div class="option-price">
              <div>+{{ option.price }}</div>
            </div>
          </div>
        </div>
        <!-- <div v-for="(option, idx) in order.option_categories" :key="idx" class="option"> -->
        <!-- <template v-if="option.name && option.option_names.length > 0">
              ㄴ{{ option.options.map((opt) => opt.name + " - " + opt.price + "원").join(", ") }}
            </template> -->
        <!-- </div> -->
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
import { deleteOrderMenu } from "@/api/party";

export default {
  props: {
    orders: {
      type: Array,
      required: true,
    },
    code: {
      required: true,
    },
  },

  data() {
    return {
      dropdownOpen: false, //드롭다운 상태변수
      sortMethod: "default", //현재 선택된 정렬 방식
      // orders: [], //주문내역 저장할 배열
    };
  },

  mounted() {
    // 페이지가 로드될 때 getOrderList 함수를 호출하여 주문내역을 가져옴
    console.log("33", this.code);
    // this.fetchOrderList();
  },

  computed: {
    // 선택된 정렬 방식에 따라 정렬된 주문 목록 반환
    sortedOrders() {
      const orders = [...this.orders]; // 주문 목록을 복사하여 정렬
      console.log("정렬전, ", orders);
      // 정렬 방식에 따라 주문 목록을 정렬
      if (this.sortMethod === "userName") {
        orders.sort((a, b) => a.participant_name.localeCompare(b.participant_name));
      } else if (this.sortMethod === "menuName") {
        console.log("ㅇㅇㅇㅇ");
        orders.sort((a, b) => a.menu.name.localeCompare(b.menu.name));
      }

      console.log("정렬후,", orders);
      return orders;
    },
  },

  methods: {
    toggleCheck(order) {
      order.checked = !order.checked;
      if (order.participant.is_paid) {
        updateParticipantPayment(
          this.code,
          order.participant.id,
          { is_paid: !order.participant.is_paid },
          () => {
            order.participant.is_paid = !order.participant.is_paid;
          },
          (error) => {
            console.error("Failed to update participant payment status:", error);
            order.checked = !order.checked;
          }
        );
      }
    },

    //드롭다운
    toggleDropdown() {
      this.dropdownOpen = !this.dropdownOpen;
    },
    //주문자 이름별 정렬
    sortByUserName() {
      this.sortMethod = "userName";
      this.toggleDropdown();
    },
    //메뉴별 정렬
    sortByMenuName() {
      this.sortMethod = "menuName";
      this.toggleDropdown();
    },

    removeOrder(orderId) {
      // 선택한 주문을 삭제
      const index = this.orders.findIndex((order) => order.id === orderId);
      if (index !== -1) {
        // 서버에 삭제 요청 보내기
        deleteOrderMenu(
          this.code,
          orderId,
          () => {
            // 성공 시 주문 리스트에서 해당 주문 삭제
            this.orders.splice(index, 1);
          },
          (error) => {
            // 실패 시
            console.error("주문 삭제 실패:", error);
          }
        );
      }
    },
    calculateTotalPrice() {
      let total = 0;

      for (const order of this.orders) {
        let orderTotal = parseFloat(order.menu.price); // 메뉴의 가격을 먼저 더함

        for (const optionCategory of order.option_categories) {
          for (const option of optionCategory.options) {
            orderTotal += parseFloat(option.price); // 각 옵션의 가격을 더함
          }
        }

        total += orderTotal; // 각 주문 항목의 총 가격을 더함
      }

      return total.toFixed(0); // 소수점 이하 자리를 버리고 정수로 반환
    },
  },
};
</script>

<style scoped>
.checkbox {
  margin-left: 10px;
}
.item.checked .menu-name,
.item.checked .menu-price {
  color: red;
  text-decoration: line-through; /* 취소선 적용 */
}

.menu-option.checked .option-name,
.menu-option.checked .option-price {
  color: red;
  text-decoration: line-through; /* 취소선 적용 */
}

.menu-name {
  width: 190px;
  display: flex;
  align-items: center;
}

select {
  border: none;
  font-size: 16px;
  font-weight: bold;
}
option {
  font-size: 16px;
  font-weight: bold;
}

/* 탭 스타일 */
.tabs {
  display: flex;
  height: 40px;
  justify-content: space-around;
  border-bottom: 3px solid #1e293b;
  /* box-shadow: 0px 0px 10px 0px rgb(227, 226, 226); */
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
  /* justify-content: space-between; */
}

.remove {
  margin-left: 30px;
  cursor: pointer;
  color: red;
}

.menu-name {
  width: 190px;
}
.menu-price {
  color: #00a5e7;
  margin-left: 10px;
}

.option {
  /* color: #FFFFFF; */
  margin-left: 20px;
}

.menu-option {
  display: flex;
}

.option-name {
  width: 190px;
}

.option-price {
  color: #00a5e7;
  margin-left: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
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
  color: #ffffff;
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
