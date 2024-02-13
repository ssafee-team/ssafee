<script setup>
import { onMounted, onUnmounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import MainHeader from '@/components/common/MainHeader.vue'
import Chat from '@/components/room/Chat.vue'
import AfterCart from '@/components/after/AfterCart.vue'
import { getOrderList, getParty } from '@/api/party'
import CarrierList from '@/components/after/CarrierList.vue'
import { getParticipants } from '@/api/after'

// const roomCode = ref("");

const route = useRoute()
const router = useRouter()
const code = ref('') // 파티 코드

const partyInfo = ref({
  id: '',
  name: '',
  generation: '',
  classroom: '',
  last_order_time: '24:00',
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

const carrierParticipants = ref([])

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

// 컴포넌트가 마운트될 때와 언마운트될 때 이벤트 리스너 추가/제거
onMounted(() => {
  updateHeaderHeight()
  window.addEventListener('resize', updateHeaderHeight)
  updateRemainingTime() // 페이지 로드시 남은시간 계산
  // 1초마다 남은시간 갱신
  setInterval(updateRemainingTime, 1000)
  code.value = route.params.code
  // console.log("현재 방 코드: ", code.value);
  getPartyInfo()
  getCarrierList()
  addToOrderList()
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
      // partyInfo.value.last_order_time = data.last_order_time;
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
  //   console.log(diff);
  //   console.log(code.value);

  if (diff > 0) {
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
    const seconds = Math.floor((diff % (1000 * 60)) / 1000)

    remainingTime.value = `${hours < 10 ? `0${hours}` : hours} : ${minutes < 10 ? `0${minutes}` : minutes
      } : ${seconds < 10 ? `0${seconds}` : seconds}`
  }
  else if (diff <= 0) {
    // 마감시간 지난 경우
    remainingTime.value = '마감'
  }
}

function addToOrderList() {
  // 주문 목록 조회
  getParticipants(
    code.value,
    (response) => {
      const participants = response.data
      getOrderList(
        code.value,
        (response) => {
          orderList.value = response.data.map((order) => {
            const participant = participants.find(participant => participant.name === order.participant_name)

            return {
              ...order,
              participant_id: participant ? participant.id : null, // Add participant_id or null if participant not found
            }
          })
          // console.log('주문 현황 불러오기: ', orderList.value)
        },
        (error) => {
          console.error('주문 현황 조회 실패: ', error)
        },
      )
    },
    (error) => {
      console.error(error)
    },
  )
}

function getCarrierList() {
  getParticipants(
    code.value,
    (response) => {
      carrierParticipants.value = response.data.filter(participant => participant.is_carrier)
    },
    (error) => {
      console.error(error)
    },
  )
}
</script>

<template>
  <div id="app">
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
              <div class="account-logo">
                logo
              </div>
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
          <div class="body-container">
            <div class="left-panel">
              <!-- <div>메뉴가 들어갈 부분</div> -->
              <!-- <MenuList :shopId="1" :code="code" @order-cart="addToCart" /> -->
              <CarrierList :carriers="carrierParticipants" />
              <!-- 왼쪽 컨텐츠 (6:4 중 6 부분) -->
              <!-- 추가적인 내용이 들어갈 수 있습니다. -->
            </div>
            <div class="center-panel">
              <AfterCart :orders="orderList" :code="code" />
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
  margin-bottom: 30px;
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
  text-decoration: underline;
  cursor: pointer;
}

.btn-roomlist,
.btn-create {
  color: #344a53;
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
  border: 3px solid #1e293b;
  border-radius: 5px;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.center-panel {
  flex: 2.5;
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
  flex: 2.5;
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
</style>
