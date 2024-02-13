<script>
import { ref } from 'vue'

export default {
  props: {
    orders: Array,
    ordersMenuSorted: Array,
    nameSet: Set,
    carriers: Array,
    participants: Array,
    carriersArr: Array,
    payers: Array,
  },

  data() {
    return {
      ordersCopied: [],
      selectedStudentNo: -1,
      selectedOrders: [],
      isSortedByName: false,
      isSortedByPrice: false,
      isInit: true,

      // isSortedByMenu: true,
      isSortedByMenu: false,
    }
  },

  computed: {
    summaryData() {
      // let totalPrice = ref(0)
      // this.orders.forEach(order => {
      //   totalPrice.value += order.menuPrice
      //   console.log(order.menuPrice)
      // });
      // console.log('ssafy')
      // this.orders.forEach((order) => {
      //   this.orders.reduce((acc, cur) => {order}, 0)
      // })
      // return {totalNum: this.orders.length, totalPrice: totalPrice}
      return {
        totalNumStudent: this.nameSet.size,
        totalNumItem: this.orders.length,
        totalPrice: this.orders.reduce((acc, cur) => acc + cur.menuPrice, 0),
      }
      // return { totalNumStudent: this.orders.length, totalNumItem: this.orders.length, totalPrice: this.orders.reduce((acc, cur)=> acc + cur.menuPrice, 0)}
    },
  },
  methods: {
    selectStudent(index) {
      // this.selectedStudentNo = this.selectedStudentNo === index? -99: index;
      // let menuNames = []
      // console.log(index);
      if (this.selectedStudentNo === index) {
        this.selectedStudentNo = -99
        this.selectedOrders = ref([])
      }
      else {
        this.selectedStudentNo = index
        // this.selectedOrders = this.orders.filter((order) => order.studentName ===  this.orders[this.selectedStudentNo].studentName);
        // this.selectedOrders = this.orders.filter((order) => order.studentName ===  this.orders[this.selectedStudentNo].studentName);
        if (this.isInit) {
          this.selectedOrders = this.orders.filter(
            order => order.studentName === this.orders[this.selectedStudentNo].studentName,
          )
        }
        else {
          this.selectedOrders = this.ordersCopied.filter(
            order => order.studentName === this.ordersCopied[this.selectedStudentNo].studentName,
          )
        }

        //   this.selectedOrders.forEach((order,index) => {
        //     if (!menuNames.includes(order.menuName)) {
        //       menuNames.push(order.menutName)
        //     } else {
        //       let idx = this.selectedOrders.findIndex(x => x.menuName === order.menuName)
        //       this.selectedOrders[idx].
        //       this.selectedOrders.splice(index,1)

        //     }
        //   })
      }
      // console.log(this.selectedStudentNo);
      // console.log(this.orders[this.selectedStudentNo].studentName);
      // console.log(this.selectedOrders);
    },
    closeModal() {
      this.selectedStudentNo = -1
    },

    sortByName(event) {
      // console.log("참여자:", this.participants);
      // console.log("배달부:", this.carriers);
      document.querySelector('#price-click').innerText = '금액 ▼'
      // console.log(this.isInit)
      this.isInit = false
      // console.log(this.isInit)
      if (!this.isSortedByName) {
        this.isSortedByName = true
        event.target.innerText = '이름 〓'

        // console.log("이름기준 정렬데이터를 호출합니다");
        // this.orders.sort((a, b) => a.studentName.localeCompare(b.studentName));
        this.ordersCopied = this.orders.toSorted((a, b) =>
          a.studentName.localeCompare(b.studentName),
        )
        this.ordersCopied = this.ordersCopied
          .filter(order => this.carriersArr.includes(order.studentName))
          .concat(
            this.ordersCopied.filter(order => !this.carriersArr.includes(order.studentName)),
          )
        // this.ordersCopied = this.orders.toSorted((a, b) => a.studentName.localeCompare(b.studentName));
      }
      else {
        this.isSortedByName = false
        event.target.innerText = '이름 ▼'
        this.ordersCopied = JSON.parse(JSON.stringify(this.orders))
      }

      // console.log(this.orders)
    },
    sortByMenu() {
      // const nameClick= document.querySelector('#name-click');
      // console.log(nameClick.innerText)
      if (this.isSortedByMenu) {
        // console.log("정렬됨");
        this.isSortedByMenu = false
        // console.log(this.isSortedByMenu);
      }
      else {
        // console.log("정렬안됨");
        this.isSortedByMenu = true
        // console.log(this.isSortedByMenu);
      }
    },

    sortByPrice(event) {
      document.querySelector('#name-click').innerText = '이름 ▼'
      this.isInit = false
      if (!this.isSortedByPrice) {
        this.isSortedByPrice = true
        event.target.innerText = '금액 〓'
        // console.log("가격기준 정렬데이터를 호출합니다");
        // this.orders.sort((a, b) => b.menuPrice - a.menuPrice);
        this.ordersCopied = this.orders.toSorted((a, b) => b.menuPrice - a.menuPrice)
        // console.log("ss:", this.carriersArr);
        this.ordersCopied = this.ordersCopied
          .filter(order => this.carriersArr.includes(order.studentName))
          .concat(
            this.ordersCopied.filter(order => !this.carriersArr.includes(order.studentName)),
          )
      }
      else {
        this.isSortedByPrice = false
        event.target.innerText = '금액 ▼'
        this.ordersCopied = JSON.parse(JSON.stringify(this.orders))
      }

      // console.log(this.orders)
      // this.orders.sort((b, a) => a.studentName.localeCompare(b.studentName));
    },
  },
}
</script>

<template>
  <div class="menulist">
    <!-- <div style="width: 100%; height: 200px; z-index: 900; background:rgba(0, 0, 0, 0.2);  top:100px;  left: 200px; position:fixed"> -->
    <!-- <div style="width:100%; height: 100px; z-index: 0; background-color: red; position:fixed">
      </div> -->
    <!-- </div> -->
    <div v-if="selectedStudentNo >= 0" class="black-bg">
      <div class="white-bg">
        <!-- <div id="aa" style="background-color: aqua;  width: 100%; height: 40px; border: 1px dashed black display: block">
            <div style="background-color: blue; width: 80%; height: 40px; box-sizing: border-box; line-height: 40px;">dddddd</div>
            <div style="background-color: red; width: 10%; height: 40px;">dd</div>
          </div> -->
        <div class="modal-title">
          <div>
            {{
              isInit
                ? orders[selectedStudentNo].studentName
                : ordersCopied[selectedStudentNo].studentName
            }}
            님의 주문내역
          </div>
          <div>
            <button class="close" @click="closeModal()">
              X
            </button>
          </div>
        </div>
        <div style="margin-left: auto; margin-right: auto">
          <hr>
        </div>
        <div id="bb" class="modal-content">
          <!-- <p>{{ this.orders.filter((order) => order.studentName ===  this.orders[selectedStudentNo].studentName) }}</p>   -->
          <div
            v-for="(selectedOrder, index) in selectedOrders" :key="index" style="
              width: 95%;
              height: auto;
              min-height: 60px;
              display: flex;
              flex-direction: row;
              margin: 5px 0px;
            "
          >
            <div style="width: 75%">
              <div class="menu-name">
                <!-- {{ this.orders[selectedStudentNo].menuName }} 1잔 -->
                {{ selectedOrder.menuName }} 1잔
              </div>
              <div class="options">
                <!-- <div style="display: flex; flex-direction: row;" v-if="selectedOrder.menuOptions.length >=3"> -->
                <div
                  v-if="selectedOrder.menuOptions.length >= 3"
                  style="padding: 4px; display: flex; flex-direction: row; flex-wrap: wrap; align-items: center;"
                >
                  <div v-for="(option, idx) in selectedOrder.menuOptions.slice(2)" :key="idx">
                    {{ option.name }} &nbsp; &nbsp;
                  </div>
                </div>
                <div v-if="selectedOrder.menuOptions.length <= 2">
                  <div style="padding: 4px">
                    기본옵션
                  </div>
                </div>
              </div>
            </div>
            <div
              class="menu-price"
              style="width: 25%; height; text-align: right; line-height: 60px; padding-right: 0px; font-size: 18px;"
            >
              <!-- {{ this.orders[selectedStudentNo].menuPrice }} 원 -->
              {{ selectedOrder.menuPrice }} 원
            </div>
          </div>
        </div>
        <div>
          <div class="modal-footer">
            <div
              style="
                line-height: 100%;
                color: white;
                line-height: 40px;
                text-align: right;
                font-weight: bold;
                font-size: 18px;
              "
            >
              총
            </div>
            <div
              style="
                line-height: 100%;
                color: #00a7d0;
                line-height: 40px;
                text-align: right;
                font-weight: bold;
                font-size: 18px;
                margin-left: 15px;
              "
            >
              {{ selectedOrders.reduce((acc, cur) => acc + cur.menuPrice, 0) }}
            </div>
            <div
              style="
                line-height: 40px;
                color: #00a7d0;

                font-weight: bold;
                font-size: 18px;
              "
            >
              원
            </div>
          </div>
          <!-- <div style="width: 60%; color: white;">총</div>
              <div style="width: 30%; color: #00A7D0; text-align: light;">{{ selectedOrders.reduce((acc, cur)=> acc + cur.menuPrice, 0) }}&nbsp;</div>
              <div style="width: 10%; color: white; margin-right: 0px;">원</div> -->
        </div>
      </div>
    </div>
    <div style="z-index: 1" class="menulistbox">
      <!-- <div class="menulisttitle">
      전체 주문 내역
    </div> -->
      <div v-if="!isSortedByMenu" class="orderheader">
        <div style="width: 10%; border-box; padding-left: 0.5rem;">
          학급
        </div>
        <div id="name-click" style="width: 25%; cursor: pointer" @click="sortByName">
          이름 ▼
        </div>
        <div
          style="
            width: 50%;
            box-sizing: border-box;
            /* padding-left: 7rem; */
            text-align: center;
            cursor: pointer;
          " @click="sortByMenu"
        >
          메뉴 ▼
        </div>
        <div id="price-click" style="width: 15%; box-sizing: border-box; cursor: pointer" @click="sortByPrice">
          금액 ▼
        </div>
      </div>
      <!-- <div v-if="!isSortedByMenu" class="orderheader">
        <div style="width: 10%; border-box; padding-left: 0.5rem;">학급</div>
        <div id="name-click" @click="sortByName" style="width: 25%; cursor: pointer; ">이름 ▼</div>
        <div @click="sortByMenu" style="width: 50%; box-sizing: border-box; padding-left: 7rem; text-align: left; cursor: pointer;">메뉴 ▼</div>
        <div id="price-click" @click="sortByPrice" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: pointer;">금액 ▼</div>
      </div> -->

      <div v-if="isSortedByMenu" class="orderheader">
        <div style="width: 10%; border-box; padding-left: 0.5rem;" />
        <div style="width: 25%; cursor: pointer" @click="sortByMenu">
          메뉴 〓
        </div>
        <!-- <div @click="sortByName(this.isSortedByName)" style="width: 25%; cursor: grab; ">이름 ▼</div> -->
        <div
          style="
            width: 50%;
            box-sizing: border-box;
            padding-left: 6rem;
            text-align: left;
            cursor: pointer;
          " @click="sortByMenu"
        >
          옵션
        </div>
        <div style="width: 15%; box-sizing: border-box; cursor: pointer" @click="sortByMenu">
          금액
        </div>
        <!-- <div @click="sortByPrice(this.isSortedByPrice)" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: grab;">금액 ▼</div> -->
      </div>

      <div v-if="!isSortedByMenu" class="orderbox" style="font-size: 32px">
        <div
          v-for="(order, index) in isInit ? orders : ordersCopied" :key="index" class="ordermenu" :class="{
            isSelected: selectedStudentNo === index,
            isCarrier: carriersArr.includes(order.studentName) ? true : false,
          }" @click="selectStudent(index)"
        >
          <!-- {{ order.classNo }} {{ order.studentName }} {{ order.menuName }} {{ order.menuPrice }} -->
          <div class="classno">
            {{ order.classNo }}
          </div>

          <div
            v-if="carriersArr.includes(order.studentName)
              && payers.includes(order.studentName)
            " class="studentname"
          >
            {{ order.studentName }}🛵💰
          </div>
          <div
            v-if="carriersArr.includes(order.studentName)
              && !payers.includes(order.studentName)
            " class="studentname"
          >
            {{ order.studentName }} 🛵
          </div>
          <div
            v-if="!carriersArr.includes(order.studentName)
              && payers.includes(order.studentName)
            " class="studentname"
          >
            {{ order.studentName }} 💰
          </div>
          <div
            v-if="!carriersArr.includes(order.studentName)
              && !payers.includes(order.studentName)
            " class="studentname"
          >
            {{ order.studentName }}
          </div>
          <div class="menuname">
            {{ order.menuName }}
          </div>
          <div class="menuprice">
            {{ order.menuPrice }}
          </div>
        </div>
      </div>

      <div v-if="isSortedByMenu" class="orderbox" style="font-size: 32px">
        <div v-for="(order, index) in ordersMenuSorted" :key="index" class="ordermenu2">
          <div class="classno">
            {{ order.classNo }}
          </div>
          <div class="studentname">
            {{ order.menuName }} X {{ order.menuCount }}
          </div>
          <div class="menuname">
            <!-- <div class="menuname" style="background-color: bisque;"> -->
            <div v-for="(option, indexOpt) in order.options" :key="indexOpt" class="baker">
              <div style="display: flex; flex-direction: row; margin-top: 5px;">
                <div v-if="option.length >= 3" style="display: flex; flex-direction: column">
                  <!-- <div style="background-color: mediumseagreen; display: flex; flex-direction: row;" v-if="option.length>=3"> -->
                  <div
                    v-for="(a, b) in option.slice(2)" :key="b" class="option-list"
                    style="display: inline-block; height: auto; min-height: 20px; color: #344a53"
                  >
                    {{ a.name }}
                  </div>
                </div>

                <div v-if="option.length <= 2" style="display: flex; flex-direction: column">
                  <!-- <div style="background-color: red;"  v-if="option.length<=2"> -->
                  <div class="option-list" style="display: inline-block; height: auto; min-height: 20px; color: #344a53">
                    기본옵션 &nbsp;
                  </div>
                </div>

                <div style="align-items: center; padding-left: 10px; font-weight: bold">
                  = {{ option[1] }}개
                </div>
              </div>
            </div>
          </div>
          <div class="menuprice">
            {{ order.sumMenuPrice }}
          </div>

          <!-- <div>{{ order.menuName }} X {{ order.menuCount }} {{ order.sumMenuPrice }} 원 </div> -->
        </div>
      </div>

      <div class="summary">
        <div>총 인원 {{ summaryData.totalNumStudent }} 명</div>
        <!-- <div></div> -->
        <div>총 수량 {{ summaryData.totalNumItem }} 개</div>
        <!-- <div></div> -->
        <div>총 금액 {{ summaryData.totalPrice }} 원</div>
        <!-- <div></div> -->
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.menulist {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.menulistbox {
  display: flex;
  flex-direction: column;
  // border: 1px solid black;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  border-radius: 0.5rem;
  // flex-grow: 1;
  height: 100%;
}

.orderheader {
  display: flex;
  color: white;
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
  background-color: #97afba;
  border-radius: 0.5rem 0.5rem 0 0;
  border: 2px solid #97afba;
  // flex-grow: 0;
}

.orderbox {
  // background-color: purple;
  // flex-grow: 1;
  height: 673px;
  overflow-y: auto;
  // overflow-x: hidden;
  // z-index: 2;
}

.orderbox::-webkit-scrollbar {
  display: none;
}

.ordermenu {
  display: flex;
  flex-direction: row;
  color: #344a53;
  height: 64px;
  line-height: 64px;
  text-align: center;
  font-size: 18px;
  background-color: #f5f5f5;
  margin: 0.25rem;
  position: relative;
  margin-bottom: 5px;
  border: 2px solid #f5f5f5;
  box-shadow: 1px 1px 0px 1px rgb(227, 226, 226);
}

.summary {
  // width: 100%;
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  background-color: #f5f5f5;
  border: 1px solid #f5f5f5;
  // border: 1px solid #344a53;
  border-radius: 5px;
  margin: 0.5rem;
  display: flex;
  // flex-grow: 0;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.summary>div {
  width: 33%;
  font-weight: bold;
}

.ordermenu>* {
  font-size: 18px;
  font-weight: bold;
  color: #344a53;
  // border: 1px dashed black;
  // flex-grow: 1;
  // border: 2px dashed black;
  // margin: 2px;
}

// {classNo: "2", studentName: "김요한", menuName: "키위주스", menuPrice: 12345},
.classno {
  width: 10%;
  // border: 1px dashed black;
  text-align: center;
  // position: absolute;
  // left: 0%;
  // flex-grow: 2;
}

.studentname {
  width: 25%;

  // display: flex;
  align-items: center;
  //   text-align: center;
  // border: 1px dashed black;
  // position: absolute;
  // left: 20%;
  // flex-grow: 3;
}

.menuname {
  width: 50%;
  font-size: 14px;
  // border: 1px dashed black;
  box-sizing: border-box;
  // padding-left: 64px;
  text-align: center;
  // position: absolute;
  // left: 45%;
  // flex-grow: 5;
}

.menuprice {
  width: 15%;
  // border: 1px dashed black;
  // text-align: right;
  box-sizing: border-box;
  // padding-right: 2rem;

  // display: flex;
  // justify-content: flex-end;
  align-items: center;
  // position: absolute;
  // left: 90%;
  // flex-grow: 2;
}

.isSelected {
  background-color: #97afba;
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

.modal-title {
  padding: 10px;
  margin-left: 5px;
  font-size: 20px;
  align-items: center;
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

.modal-content::-webkit-scrollbar {
  display: none;
}

.modal-footer {
  // style="width: 95%; height: 100%; display: flex; justify-content: right; gap: 10px"
  position: sticky;
  bottom: 0;
  padding: 10px;
  display: flex;
  justify-content: right;

}

.close {
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

.menu-name {
  // style="font-size: 18px; height: 30px; text-align: left; line-height: 30px"
  font-size: 18px;
  height: 30px;
  text-align: left;
  margin-top: 10px;
  margin-left: 10px;
  font-weight: bold;
}

.options {
  // style="height: auto; min-height: 30px; text-align: left; line-height: 30px"
  height: auto;
  min-height: 30px;
  text-align: left;
  margin-left: 20px;
}

.faker {
  background-color: purple;
  width: 100%;
  margin-top: 10px;
}

.baker {
  // background-color: mediumturquoise;
}

.ordermenu2 {
  display: flex;
  flex-direction: row;
  color: #344a53;
  // height: 64px;
  // line-height: 64px;
  text-align: center;
  font-size: 16px;
  background-color: #f5f5f5;
  margin: 0.25rem;
  position: relative;
  padding-top: 0.25rem;
  padding-bottom: 0.25rem;
  margin-bottom: 5px;
  border: 2px solid #f5f5f5;
  box-shadow: 1px 1px 0px 1px rgb(227, 226, 226);
}

.ordermenu2>.classno,
.studentname,
.menuprice {
  font-size: 18px;
  font-weight: bold;
  color: #344a53;
  margin-top: 5px;
  margin-bottom: 5px;
}

.option-list {
  width: 250px;
  font-size: 14px;
}

.isCarrier {
  background-color: #344a53;
}

.isCarrier * {
  // background-color: #344a53;
  color: white;
}

@media screen and (max-width: 768px) {
  .orderheader {
    font-size: 16px;
    // flex-grow: 0;
  }

  .ordermenu {
    font-size: 12px;
  }

  .summary {
    font-size: 18px;
    // flex-grow: 0;
  }

  .ordermenu>* {
    font-size: 18px;
  }

  .ordermenu2 {
    font-size: 12px;
  }

  .black-bg {

    width: 100%;
    height: 100%;
  }

  .white-bg {
    width: 80%;

  }

  .ordermenu2>.classno,
  .studentname,
  .menuprice {
    font-size: 14px;
  }

  .option-list {
    width: 180px;
  }

  .modal-title {
    font-size: 16px;
  }

  .menu-price {
    font-size: 14px;
  }

  .modal-footer {
    font-size: 12px;
  }

  .menuname,
  .menu-name,
  .menuprice {
    font-size: 14px;
  }

  .options {
    font-size: 12px;
  }

  .close {
    font-size: 12px;
  }
}
</style>
