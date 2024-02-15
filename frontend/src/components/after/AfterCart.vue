<script>
import { modifyParticipants } from '@/api/after'

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
      dropdownOpen: false, // 드롭다운 상태변수
      sortMethod: 'default', // 현재 선택된 정렬 방식
      // orders: [], //주문내역 저장할 배열
    }
  },

  computed: {
    groupedOrders() {
      const grouped = {}
      for (const order of this.sortedOrders) {
        const participantName = order.participant_name
        if (!grouped[participantName])
          grouped[participantName] = []

        grouped[participantName].push(order)
      }

      return grouped
    },

    // 선택된 정렬 방식에 따라 정렬된 주문 목록 반환
    sortedOrders() {
      const orders = [...this.orders] // 주문 목록을 복사하여 정렬
      
      // 정렬 방식에 따라 주문 목록을 정렬
      if (this.sortMethod === 'userName')
        orders.sort((a, b) => a.participant_name.localeCompare(b.participant_name))

      // else if (this.sortMethod === "menuName") {
      //   orders.sort((a, b) => a.menu.name.localeCompare(b.menu.name));
      // }
      // console.log("정렬",orders)
      return orders
    },
  },

  mounted() {
    // 페이지가 로드될 때 getOrderList 함수를 호출하여 주문내역을 가져옴
    // console.log("33", this.code);
    // this.fetchOrderList();
  },

  methods: {
    toggleCheck(participantOrders) {
      const participantId = participantOrders[0].participant_id
      const paidValue = participantOrders[0].paid

      modifyParticipants(
        this.code,
        participantId,
        { paid: paidValue },
        () => {
          for (const order of participantOrders) {
            order.paid = paidValue
            order.checked = !order.checked
          }
        },
        (error) => {
          console.error(error)

          for (const order of participantOrders)
            order.checked = !order.checked
        },
      )
    },
    // 드롭다운
    toggleDropdown() {
      this.dropdownOpen = !this.dropdownOpen
    },
    // 주문자 이름별 정렬
    sortByUserName() {
      this.sortMethod = 'userName'
      this.toggleDropdown()
    },
    // 메뉴별 정렬
    sortByMenuName() {
      this.sortMethod = 'menuName'
      this.toggleDropdown()
    },

    calculateTotalPrice() {
      let total = 0

      for (const order of this.orders) {
        let orderTotal = Number.parseFloat(order.menu.price) // 메뉴의 가격을 먼저 더함

        for (const optionCategory of order.option_categories) {
          for (const option of optionCategory.options)
            orderTotal += Number.parseFloat(option.price) // 각 옵션의 가격을 더함
        }

        total += orderTotal // 각 주문 항목의 총 가격을 더함
      }

      return total.toFixed(0) // 소수점 이하 자리를 버리고 정수로 반환
    },
  },
}
</script>

<template>
  <!-- 탭 형태로 My Cart와 Our Cart를 표시 -->
  <div class="tabs">
    <div class="tab">
      주문내역
    </div>
    <select v-model="sortMethod" @change="sortOrders">
      <option value="default">
        기본
      </option>
      <option value="userName">
        이름순
      </option>
      <!-- <option value="menuName">메뉴순</option> -->
    </select>
  </div>

  <!-- 주문내역 -->
  <div class="content">
    <div class="order-list">
      <div
        v-for="(participantOrders, participantName) in groupedOrders"
        :key="participantName"
        class="participant"
      >
        <hr>
        <div class="participant-name">
          <input
            v-model="participantOrders[0].paid"
            :checked="participantOrders[0].paid"
            type="checkbox"
            @change="toggleCheck(participantOrders)"
          >
          {{ participantName }}
        </div>

        <hr>
        <div
          v-for="(order, index) in participantOrders"
          :key="index"
          class="order"
          :class="{ checked: participantOrders[0].paid }"
        >
          <div class="item" :class="{ checked: participantOrders[0].paid }">
            <div class="menu-name">
              {{ order.menu.name }}
            </div>
            <div class="menu-price">
              {{ order.menu.price }}원
            </div>
          </div>
          <div
            v-for="optionCategory in order.option_categories"
            :key="optionCategory.id"
            class="option"
          >
            <div
              v-for="option in optionCategory.options"
              :key="option.id"
              class="menu-option"
              :class="{ checked: participantOrders[0].paid }"
            >
              ㄴ
              <div class="option-name">
                {{ option.name }}
              </div>
              <div class="option-price">
                + {{ option.price }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 총 주문 금액 -->
    <div class="footer">
      <div class="total">
        <div>총 주문금액</div>
        <div class="total-price">
          {{ calculateTotalPrice() }}원
        </div>
      </div>
    </div>
  </div>
</template>

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
  /* width: 190px; */
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

hr {
  border: 0;
  height: 3px;
  background-color: #1e293b;
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
  margin-left: 30px;
  cursor: pointer;
  color: red;
}

.menu-name {
  width: 260px;
}

.participant-name {
  margin-left: 10px;
  margin-top: 10px;
  /* margin-right: 10px; */
  font-weight: bold;
  font-size: 18px;
}

input {
  margin-right: 10px;
}

.menu-price {
  color: #00a5e7;
  margin-left: 10px;
  width: 30%;
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

input[type="checkbox"] {
  accent-color: #00a5e7;
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
@media screen and (max-width: 768px) {
  .participant-name{
    font-size: 16px;
  }

  .item{
    font-size: 16px;
    width: 100%;
    justify-content: space-between;
  }
  .menu-option{
    font-size: 14px;
  }
  .total, .tab{
    font-size: 16px;
  }

  .footer{
    margin-top: 5px;
  }
}
</style>
