<script setup>
import { onMounted, onUnmounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

// import func from '../../vue-temp/vue-editor-bridge'
import MainHeader from '@/components/common/MainHeader.vue'
import MenuList from '@/components/room/MenuList.vue'
import Chat from '@/components/room/Chat.vue'
import Cart from '@/components/room/Cart.vue'
import { getOrderList, getParty, orderRequest } from '@/api/party'

// const roomCode = ref("");

const route = useRoute()
const router = useRouter()
const code = ref('') // 파티 코드
const isLoading = ref(true) // 로딩 상태 변수

const partyInfo = ref({
  id: '',
  name: '',
  generation: '',
  classroom: '',
  last_order_time: '',
  created_time: '',
  shop_id: '',
  creator: {
    id: '',
    name: '',
    email: '',
    bank: '',
    account: '',
  },
})

const orderList = ref([])

function addToCart(order) {
  orderList.value.push(order)
}

// 파티 객체 정보의 shop_id 추출
const shopId = partyInfo.value.shop_id

const isOrderListModalOpen = ref(false)

const remainingTime = ref('') // 남은시간

// 헤더 높이를 저장하는 변수
const headerHeight = ref('')

// 화면 크기가 변경될 때마다 헤더 높이를 업데이트하는 함수
function updateHeaderHeight() {
  headerHeight.value = `${document.querySelector('header').offsetHeight}px`
}

// 스크립트 섹션 안에서
const isUserLoggedIn = ref(false)

// 컴포넌트가 마운트될 때와 언마운트될 때 이벤트 리스너 추가/제거
onMounted(() => {
  // 로컬 스토리지에서 "user-token"을 가져와서 확인
  const userToken = localStorage.getItem('user-token')
  isUserLoggedIn.value = !!userToken

  updateHeaderHeight()
  window.addEventListener('resize', updateHeaderHeight)
  updateRemainingTime() // 페이지 로드시 남은시간 계산
  // 1초마다 남은시간 갱신
  setInterval(updateRemainingTime, 1000)
  code.value = route.params.code
  // console.log("현재 방 코드: ", code.value);
  getPartyInfo()
  addToOrderList()
  setTimeout(() => {
    isLoading.value = false
  }, 500)
})

onUnmounted(() => {
  window.removeEventListener('resize', updateHeaderHeight)
})

function getPartyInfo() {
  getParty(
    code.value,

    ({ data }) => {
      // console.log(data);
      partyInfo.value.id = data.id
      partyInfo.value.name = data.name
      partyInfo.value.generation = data.generation
      partyInfo.value.classroom = data.classroom
      partyInfo.value.last_order_time = data.last_order_time
      partyInfo.value.created_time = data.created_time
      partyInfo.value.shop_id = data.shop_id
      partyInfo.value.creator = data.creator
      // console.log(partyInfo);
    },
    (error) => {
      console.log(error)
    },
  )
}

// 남은시간 갱신하는 함수 호출
function updateRemainingTime() {
  const now = new Date() // 현재시간 변수
  // const deadlineTime = new Date(partyInfo.value.last_order_time); // last_order_time을 Date 객체로 파싱

  // console.log(deadlineTime);

  const deadlineTime = new Date()
  const [hours, minutes] = partyInfo.value.last_order_time.split(':').map(Number)

  deadlineTime.setHours(hours, minutes, 0)
  // deadlineTime.setHours(11, 48, 0);

  // 마감시간에서 현재시간 차이를 저장
  const diff = deadlineTime - now
  // console.log(diff);
  // console.log(code.value)

  if (diff > 0) {
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
    const seconds = Math.floor((diff % (1000 * 60)) / 1000)

    remainingTime.value = `${hours < 10 ? `0${hours}` : hours} : ${minutes < 10 ? `0${minutes}` : minutes
      } : ${seconds < 10 ? `0${seconds}` : seconds}`
  }
  else if (diff <= 0) {
    // 마감시간 지난 경우
    // go("/after")화면으로
    remainingTime.value = '마감'
    console.log(window.location.href)
    // console.log(code.value);
    setTimeout(() => {
      window.location.href = `/after/${code.value}`
    }, 100)
  }
}

function addToOrderList() {
  // 주문 목록 조회
  getOrderList(
    code.value, // partyCode 전달
    (response) => {
      orderList.value = response.data
      // console.log("주문 현황 불러오기: ", orderList.value);
    },
    (error) => {
      console.error('주문 현황 조회 실패: ', error)
    },
  )
}

function closeOrderModal() {
  isOrderListModalOpen.value = false
}
// const checkOrderStatus = () => {
//   // 주문 현황 확인 로직을 추가할 수 있습니다.
//   console.log("주문 현황 확인하기 버튼이 클릭되었습니다.");
// };

// 마감시간 시 After 창으로 이동하는 코드

function goMain() {
  router.push({ name: 'main' })
}

function goCreate() {
  router.push({ name: 'CreateRoomView' })
}

function goOrder() {
  orderRequest(
    code.value,
    console.log(code.value),
    () => {
      console.log('주문이 요청되었습니다.')
      // 주문이 요청되면 할 일을 추가할 수 있습니다.
    },
    (error) => {
      console.error('주문 요청에 실패했습니다:', error)
      // 주문 요청 실패 시 처리할 내용을 추가할 수 있습니다.
    },
  )
}
</script>

<template>
  <div id="app">
    <div v-if="isLoading" class="loader-overlay">
      <div class="loader" />
    </div>
    <MainHeader />
    <main>
      <div class="container">
        <head>
          <div class="line">
            <div class="party-name">
              {{ partyInfo.name }}
            </div>
          </div>
          <div class="center-title">
            <div class="row">
              <div class="account">
                {{ partyInfo.creator.bank }}
              </div>
              <div class="account">
                ({{ partyInfo.creator.name }})
              </div>
            </div>
            <div class="row">
              <!-- <div class="account-logo">
                logo
              </div> -->
              <div class="account-num">
                {{ partyInfo.creator.account }}
              </div>
            </div>
          </div>
          <img src="@/assets/img/logo_compose.png" alt="">
          <div class="timeline">
            <div>잔여시간</div>
            <div style="color: red" class="time">
              {{ remainingTime }}
            </div>
          </div>
          <div class="timeline">
            <div>마감시간</div>

            <div class="time">
              {{ partyInfo.last_order_time }}
            </div>
          </div>
        </head>
        <body>
          <div v-if="isUserLoggedIn" class="btn-order">
            <button class="order-request" @click="goOrder()">
              주문요청
            </button>
          </div>
          <!-- <div class="center-content">
            <button class="btn-roomlist" @click="goMain">방목록</button>
            <button class="btn-curorder" @click="openOrderListModal">현재 주문현황 확인하기</button>
          </div> -->
          <div class="body-container">
            <div class="left-panel">
              <MenuList :shop-id="1" :code="code" @order-cart="addToCart" />
            </div>
            <div class="center-panel">
              <Cart :orders="orderList" :code="code" />
            </div>
            <div class="right-panel">
              <!-- <div>채팅창</div> -->
              <Chat />
            </div>
          </div>
        </body>

        <!-- <OrderListModal v-if="isOrderListModalOpen" @close="closeOrderListModal" /> -->
      </div>
    </main>
  </div>
</template>

<style scoped>
body {

  font-family: "Arial", sans-serif;
}

main {
  display: flex;
  flex-direction: column;
  height: auto;
  overflow-x: hidden;

  /* box-sizing: border-box; */
  justify-content: center;
  align-items: center;
  /* height: 878px; */
  overflow: hidden;
}

.container {
  width: 90%;
  height: 900px;
}

head {
  color: black;
  min-height: 70px;
  display: flex;
  font-size: 20px;
  margin-top: 30px;
  margin-bottom: 20px;
  border: 3px solid #1e293b;
  justify-content: space-between;
  align-items: center;
  border-radius: 5px;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  /* background-color: #020817; */
}

.timeline {
  width: 20%;
  display: flex;
  border: 3px solid #1e293b;
  justify-content: center;
  align-items: center;
  height: 70px;
  /* font-size: 30px; */
  /* margin: 20px; */
  padding: 5px;
  border-radius: 5px;
  font-weight: bold;
  margin: 10px;
}

.time {
  width: auto;
  margin-left: 10px;
}

.row {
  margin-top: 5px;
  display: flex;
  /* justify-content: space-between; */
  justify-content: center;
  gap: 10px;
  /* margin-top: 20px; */
}

.account {
  /* display: flex; */
  /* align-items: center; */
}

.line {
  width: 20%;
  height: 70px;
  padding: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 10px;
  border-radius: 5px;
  border: 3px solid #1e293b;
  font-weight: bold;
}

.party-name {
  justify-content: center;
  align-content: center;
  align-items: center;
}

.center-title {
  text-align: center;
  font-weight: bold;
  padding: 5px;
  margin: 10px;
  border: 3px solid #1e293b;
  width: 20%;
  height: 70px;
  border-radius: 5px;
}

.center-content {
  /* display: flex; */
  /* text-align: center; */
  /* font-size: 30px; */
  /* flex-grow: 1; */
  font-weight: bold;
  /* justify-content: center; */
}

button {
  padding: 10px;
  background-color: white;
  border: none;
  color: #00a7d0;
  font-size: 18px;
  font-weight: bold;
  /* text-decoration: underline; */
  cursor: pointer;
}

.btn-roomlist,
.btn-create {
  color: #344a53;
}

.btn-order{
  display: flex;
  justify-content: right;
  margin-bottom: 10px;
}

.order-request{
  border-radius: 10px;
  font-weight: bold;
  font-size: 16px;
  background-color: #1e293b;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  color: #ffffff;

}
.order-request:hover{
  background-color: #343844;
}

.body-container {
  display: flex;
  height: 645px;
  /* margin-top: 25px; */
  box-sizing: border-box;
}

.left-panel {
  flex: 5;
  /* 7:3 비율로 나누기 위한 설정 */
  /* height: 700px; */
  /* border: 5px solid #ccc; */
  box-sizing: inherit;
}

.center-panel {
  flex: 3;
  margin-left: 20px;
  /* margin-bottom: 20px; */
  /* height: 700px; */
  /* height: 645px; */
  border: 3px solid #1e293b;
  border-radius: 5px;
  box-sizing: inherit;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.right-panel {
  flex: 2;
  margin-left: 20px;
  /* height: 700px; */
  border: 3px solid #1e293b;
  border-radius: 5px;
  box-sizing: inherit;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {
  head {
    font-size: 18px;
    /* 화면이 작을 때 텍스트 크기 조절 */
  }

  .body-container {
    flex-direction: column;
  }

  /* .right-panel {
    margin-left: 0;
    margin-top: 20px;
  } */

  .btn-curorder,
  .btn-roomlist {
    font-size: 16px;
  }
}

.loader-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  /* 투명한 배경색 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
  /* 다른 요소 위에 표시 */
}

.loader {
  width: 35px;
  height: 80px;
  position: relative;
}

.loader:after {
  content: "";
  position: absolute;
  inset: 0;
  padding: 3px 5px;
  border-top: 1px solid #bbb6aa;
  border-bottom: 4px solid #bbb6aa;
  background:
    linear-gradient(#612329 0 0) bottom no-repeat content-box,
    #e4e0d7;
  mix-blend-mode: darken;
  animation: l1 1.5s infinite linear;
}

.loader:before {
  content: "";
  position: absolute;
  inset: -18px calc(50% - 2px) 8px;
  background: #eb6b3e;
  transform-origin: bottom;
  transform: rotate(8deg);
}

@keyframes l1 {
  0% {
    background-size: 100% 100%
  }

  100% {
    background-size: 100% 5%
  }
}
</style>
