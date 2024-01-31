<template>
  <div class="modal">
    <div class="modal-content">
      <div class="modal-title">
        <div>전체 주문 현황</div>
        <button @click="closeModal" class="close">X</button>
      </div>
      <hr />
      <div class="order-list">
        <div class="row" v-for="order in orderList" :key="order.id">
          <div>{{ order.name }}</div>
          <div class="menus">
            <div class="menu-info" v-for="orderMenu in order.order_menus" :key="orderMenu.id">
              <div>{{ orderMenu.menu.name }}</div>
              <div v-for="option in orderMenu.options" :key="option.id">{{ option.name }}</div>
            </div>
          </div>
          <div>{{ calculateTotalPrice(order) }}원</div>
          <button @click="deleteOrder(index)" class="delete">X</button>
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
export default {
  props: {
    // orderList: Array,
  },
  data() {
    return {
      orderList: [
        {
          id: 348,
          name: "고영훈",
          is_creator: true,
          is_carrier: false,
          is_payed: true,
          is_pay_confirmed: false,
          order_menus: [
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
          ],
        },
        {
          id: 348,
          name: "고영훈",
          is_creator: true,
          is_carrier: false,
          is_payed: true,
          is_pay_confirmed: false,
          order_menus: [
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
          ],
        },
        {
          id: 348,
          name: "고영훈",
          is_creator: true,
          is_carrier: false,
          is_payed: true,
          is_pay_confirmed: false,
          order_menus: [
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
          ],
        },
        {
          id: 348,
          name: "고영훈",
          is_creator: true,
          is_carrier: false,
          is_payed: true,
          is_pay_confirmed: false,
          order_menus: [
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
            {
              id: 99,
              menu: {
                id: 77,
                name: "아메리카노",
                price: 1500,
                image: "string",
              },
              options: [
                {
                  id: 1556,
                  name: "ICE",
                  price: 500,
                },
              ],
            },
          ],
        },
      ],
    };
  },

  methods: {
    closeModal() {
      this.$emit("close");
    },
    calculateTotalPrice(order) {
      let totalPrice = 0;
      for (const orderMenu of order.order_menus) {
        totalPrice += orderMenu.menu.price;
        for (const option of orderMenu.options) {
          totalPrice += option.price;
        }
      }
      return totalPrice;
    },
    deleteOrder(index) {
      this.orderList.splice(index, 1); // 주문 리스트에서 해당 인덱스의 주문 삭제
    },
  },
  computed: {
    calculateTotalOrderPrice() {
      let totalOrderPrice = 0;
      for (const order of this.orderList) {
        totalOrderPrice += this.calculateTotalPrice(order);
      }
      return totalOrderPrice;
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
  height: 70%;
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
  max-height: 380px;
}
.order-content::-webkit-scrollbar {
  display: none;
}

.order-list {
  display: flex;
  flex-direction: column;
  padding: 10px;
  /* margin-left: 70px; */
  height: 380px;
  max-height: 380px;
  padding: 20px;
  gap: 20px;
  justify-content: space-between;
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
  display: flex;
  flex-direction: column;
  font-size: 14px;
}

.menu-info {
  margin-bottom: 15px;
}

.row {
  width: 100%;
  font-size: 18px;
  display: flex;
  margin-bottom: 20px;
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
</style>
