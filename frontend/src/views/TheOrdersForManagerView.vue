<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCreator, getOrderList } from '@/api/after.js'

const route = useRoute()
const router = useRouter()

const shopname = ref('컴포즈커피_광주장덕수완점')
const isFold = ref(false)

const tri = computed(() => {
  return isFold.value ? '▲' : '▼'
})

const access_code = ref(null)
const arrayTF = ref(null)
const orders = ref([])
const ordersMenuSorted = ref([])
const menuSet = new Set()
const nameSet = new Set()
const partyPrice = ref(0)
const creator = ref({
  id: '',
  name: '',
  bank: '',
  account: '',
})

function toggleShop() {
  console.log('toggle')
  isFold.value = !isFold.value
}

const state = reactive({
  arr: [{ menuName: '아이스아메리카노', menuCount: 12, menuCombi: [{ combiCount: 10, options: ['1 샷추가'] }, { combiCount: 10, options: ['시럽 1펌프', '2샷 추가'] }] }],
  phoneNumber: '010-4120-6929',
  address: '광주광역시 하남산단 삼성전자 C5주차장 자전거 거치대 앞',

})

function orderClick(index) {
  // console.log("눌렀다",index)
  arrayTF.value[index] = !arrayTF.value[index]
  // console.log(arrayTF.value)
}
onMounted(() => {
  access_code.value = route.params.access_code
  // console.log(access_code.value)
  getCreator(access_code.value, (res) => {
    creator.value.id = res.data.creator.id
    creator.value.name = res.data.creator.name
    creator.value.bank = res.data.creator.bank
    creator.value.account = res.data.creator.account
  }, (error) => {
    console.log(error)
  })

  getOrderList(access_code.value, (res) => {
    res.data.forEach((elem) => {
      nameSet.add(elem.participant_name)
      menuSet.add(elem.chosen_menu.name)

      let optionPrice = 0
      const options = []
      elem.chosen_option_categories.forEach((option_categories) => {
        option_categories.chosen_options.forEach((option) => {
          options.push({ id: option.id, name: option.name, price: option.price })
          optionPrice += option.price
        })
      })

      options.sort((a, b) => a.id - b.id)
      let itCat = ''
      options.forEach((opt) => { itCat += (`${opt.id}_`) })
      itCat = itCat === '' ? '0' : itCat.substring(0, itCat.length - 1)
      // console.log(itCat)
      options.unshift(1)
      options.unshift(itCat)

      orders.value.push({
        classNo: 2,
        studentName: elem.participant_name,
        menuName: elem.chosen_menu.name,
        menuPrice: elem.chosen_menu.price + optionPrice,
        menuPriceNoOptions: elem.chosen_menu.price,
        menuOptions: options,
      })
    })
    //   orders.value = orders.value.filter((order) => carriersArr.value.includes(order.studentName)).concat(orders.value.filter(order=> !carriersArr.value.includes(order.studentName)));
    menuSet.forEach((menu) => {
      // console.log(menu)
      // 개별메뉴 숫자
      const optionsTemp = []
      const sortedByMenu = orders.value.filter(order => order.menuName === menu)
      // sortedByMenu 에서 options 순회
      // option이 tempOptions에 없으면 추가, 있으면 수량만 +1
      const optionsSet = new Set()
      let sumMenuPrice = 0
      sortedByMenu.forEach((order) => {
        sumMenuPrice += order.menuPrice
        // order.menuOptions[0] 번이 없으면 추가 있으면 위치를 찾아 count += 1
        if (!optionsSet.has(order.menuOptions[0])) {
          optionsSet.add(order.menuOptions[0])
          optionsTemp.push(order.menuOptions)
          // console.log(optionsSet)
        }
        else {
          const idx = optionsTemp.findIndex(elem => elem[0] === order.menuOptions[0])
          optionsTemp[idx][1] += 1
        }
      })
      optionsTemp.sort((a, b) => a[0].localeCompare(b[0]))
      // console.log('adfasdzcxv',optionsTemp[0])

      ordersMenuSorted.value.push(
        {
          sumMenuPrice,
          menuName: menu,
          menuCount: sortedByMenu.length,
          options: optionsTemp,
        },
      )
    })
    console.log(ordersMenuSorted.value)

    ordersMenuSorted.value.forEach(a => partyPrice.value += a.sumMenuPrice)
    //   console.log('배열의크기', menuSet.size)
    arrayTF.value = Array.from({ length: menuSet.size }).fill(false)
  }, (error) => {
    console.log(error)
  })
})
</script>

<template>
  <main>
    <header>
      <img src="../assets/img/logo_ssaffe_2nd.png" alt="logo_ssaffe_2nd" style="height: 72px; padding-right: 10px;">
      <div style="margin-right: 0;">
        SSAFFE®
      </div>
      <div style="margin-right: auto; font-size: 8px; transform: translateY(-8px);">
        for Manager
      </div>

      <div class="shop-name" :class="{ isfold: isFold }" @click="toggleShop">
        {{ shopname }} {{ tri }}
        <div v-if="isFold">
          <div class="edit-info">
            <hr>기본정보 수정
          </div>
          <div class="edit-my-menu">
            <hr>내 가게 메뉴 편집
          </div>
        </div>
      </div>
    </header>
    <hr>

    <body>
      <div class="body-container">
        <!-- <div style="width:100%; height:120px; background-color: aqua; display: flex; flex-direction: row; align-items: center"> -->
        <div style="width:100%; height:120px; display: flex; flex-direction: row; align-items: center">
          <div class="creator-container">
            <div>주문자명 | {{ creator.name }}</div>
            <div>전화번호 | {{ state.phoneNumber }}</div>
            <div>배달주소 | {{ state.address }}</div>
          </div>
          <div class="total-price">
            총 주문금액: {{ partyPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }} 원
          </div>
          <input class="button" style="margin-right: 20px;" type="button" value="배달출발">
        </div>
        <hr class="hr-double">
        <!-- <div style="width: 100%; height: 80%; background-color: palegreen; display: flex; justify-content: center; align-items: center;"> -->
        <div style="width: 100%; height: 80%; display: flex; justify-content: center; align-items: center;">
          <div class="order-container" style="width: 420px; height: 96%; overflow-x: hidden; overflow-y: scroll;">
            <!-- <div class="order" v-for="(order, index) in ordersMenuSorted" :key="index" style="width: 400px; min-height: 100px; border-left:1px solid black; border-right:1px solid black; border-bottom: 1px dashed black; padding: 10px 0 10px 0;"> -->
            <div
              v-for="(order, index) in ordersMenuSorted" :key="index" :class="{ isSelected: arrayTF[index] }"
              class="order"
              style="width: 400px; min-height: 100px; border-left:1px solid black; border-right:1px solid black; border-bottom: 1px dashed black; padding: 10px 0 10px 0;"
              @click="orderClick(index)"
            >
              <div style="display: flex; font-size: 24px; font-weight: bold;">
                <div style="width: 30px; text-align: right; padding-right: 10px; ">
                  {{ order.menuCount }}
                </div>
                <div>
                  {{ order.menuName }}
                </div>
              </div>
              <div
                v-for=" (combi, idx) in order.options" :key="idx"
                style="display: flex; font-size: 16px; flex-wrap: wrap;"
              >
                <div style="width: 30px; text-align: right; padding-right: 10px; ">
                  {{ combi[1] }}
                </div>
                <div v-for="(option, idx2) in combi.slice(2)" style="display: inline-block;">
                  {{ option.name }} &nbsp;
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </body>
  </main>
</template>

<style lang="scss" scoped>
main {
  height: 100%;
}

header {
  display: flex;
  align-items: center;
  flex-direction: row;
  width: 100%;
  height: 72px;
  font-size: 30px;
  font-weight: bold;
  white-space: normal;
  background-color: white;
  // border: 1px solid black;
  // z-index: 20;
}

body {
  margin: 0px;
}

.body-container {
  // background-color: pink;
  width: 100%;
  height: 88vh;
  display: flex;
  flex-direction: column;
  // justify-content: center;
  // align-content: center;
}

.isfold {
  color: black;
  background-color: #97AFBA;
  border-radius: 10px 10px 0px 0px;
  border-top: 2px solid black;
  border-left: 2px solid black;
  border-right: 2px solid black;
}

hr {
  width: 90%;
  padding: 0px;
  margin: 0px;
  border: none;
  border-top: 2px solid black;
  width: 100%;
}

.shop-name {
  color: #296A84;
  width: 450px;
  height: 60px;
  line-height: 60px;
  text-align: center;
  margin-right: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 10px;
  cursor: pointer;
  z-index: 30;
}

.edit-info {
  width: 450px;
  height: 60px;
  line-height: 60px;
  background-color: #97AFBA;
  border-left: 2px solid black;
  border-right: 2px solid black;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.edit-my-menu {
  width: 450px;
  height: 60px;
  line-height: 60px;
  background-color: #97AFBA;
  border-left: 2px solid black;
  border-right: 2px solid black;
  border-bottom: 2px solid black;
  text-align: center;
  border-radius: 0px 0px 10px 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.msg-1 {
  text-align: center;
  font-size: 40px;
  font-weight: bold;
  color: #97AFBA;
}

.msg-2 {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #00A5E7;
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
  width: 60%;
  min-width: 450px;
  height: 500px;
  margin: 80px auto;

  background: #344a53;
  border-radius: 25px;
  padding: 10px;
  margin-top: 100px;
  border: 1px solid black;
  text-align: center;
  /* height: 60%; */
  color: white;
  position: relative;
  z-index: 10000;
  border: none;
}

.button-container {
  position: absolute;
  bottom: 0;
  right: 20px;
  margin-bottom: 20px;
  // z-index: 10;
}

.button {
  font-size: 30px;
  font-weight: bold;
  border: 0px;
  padding: 0px;
  // margin-left: 10px;
  border-radius: 20px;
  width: 80px;
  height: 100px;
  // word-break: break-all;
  // word-wrap: break-word;
  white-space: normal;
  // overflow: visible;
  word-break: break-all;
  // line-height: ;
  // color: white;
  background-color: #00A7D0;
}

.accept {
  background-color: #36BAC3;
}

.reject {
  background-color: #EB4E5A;
}

.cliked {
  filter: brightness(0.6);
}

.party-info {
  width: 100%;
  text-align: left;
  font-size: 20px;
  padding: 20px;
  // color: pink;
}

.party-info::before {
  content: ":::";
}

.party-info::after {
  content: " :::";
}

.hr-double {
  // border: none;
  border-top: 4px double black;
}

.creator-container {
  font-weight: bold;
  display: flex;
  flex-direction: column;
  justify-content: center;
  // background-color: red;
  width: auto;
  height: 100%;
  font-size: 20px;
}

.total-price {
  padding-left: 40px;
  line-height: 120px;
  font-size: 30px;
  font-weight: bold;
  // background-color: blue;
  width: auto;
  height: 100%;
  margin-right: auto;
}

.order-container::-webkit-scrollbar {
  background-color: #D9D9D9;
  height: 90%;
  width: 10px;
  border-radius: 10px;
}

.order-container::-webkit-scrollbar-thumb {
  background-color: #97AFBA;
  // height: 90%;
  width: 10px;
  border-radius: 10px;
}

.isSelected {
  background-color: yellow;
}
</style>
