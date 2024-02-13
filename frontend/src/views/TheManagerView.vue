<script setup>
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const shopname = ref('컴포즈커피_광주장덕수완점')
const isFold = ref(false)
const isOpening = ref(true)
const isParty = ref(true)
const isOrderTime = ref(true)
const isAccepted = ref(false)
const isRejected = ref(false)

const creator = ref('박희찬')
const partyInfo = ref('(ICE)아메리카노 外 총 71잔')

const tri = computed(() => {
  return isFold.value ? '▲' : '▼'
})

const msg1 = computed(() => {
  if (!isOpening.value) {
    return '😥 지금은 운영시간이 아니에요 ㅠㅠ 😥'
  }
  else {
    if (!isParty.value)
      return '😂 아직은 G5에서 파티가 생성되지 않았어요 😂'
    else
      return '😍 G5가 단체주문방을 만들었어요 😍'
  }
})

const msg2 = computed(() => {
  if (!isOpening.value) {
    return '운영시간을 변경하시려면 상단 “설정” 을 이용해 주세요'
  }
  else {
    if (!isParty.value)
      return 'SSAFEE 를 위한 “프로모션”을 진행하면 주문빈도가 증가할지도..?'
    else
      return '[마감시간] 에 주문 내역이 도착하니 기대해주세요 !'
  }
})

function toggleShop() {
  console.log('toggle')
  isFold.value = !isFold.value
}

function acceptOrder() {
  console.log('주문접수')
  // 주문접수 창으로 이동한다.
  isAccepted.value = true
  isRejected.value = false
  router.push({ name: 'OrdersForManager', params: { manager_id: '1', access_code: 'ACODE123' } })
}
function rejectOrder() {
  console.log('주문거절')
  // 해당 주문을 삭제하고 모달을 닫는다.
  isAccepted.value = false
  isRejected.value = true
}

function goEditor() {
  router.push({ name: 'ManagerEditor', params: { manager_id: '1' } })
}

// 운영시간이 아니면 운영시간 아님 메세지 출력
// 운영시간일 때 파티가 없으면 파티없음 메세지, 파티있으면 알림
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
          <div class="edit-info" @click="goEditor">
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
        <div v-if="isOrderTime && !isRejected" class="black-bg">
          <div class="white-bg">
            <div style="font-size: 30px; font-weight: bold;">
              🔔 알림 🔔
            </div>
            <div>
              <div class="party-info">
                주문자명 | {{ creator }}
              </div>
              <div class="party-info">
                음료정보 | {{ partyInfo }}
              </div>
            </div>
            <div class="button-container">
              <input
                class="button accept" :class="{ cliked: isAccepted }" type="button" value="주문접수"
                @click="acceptOrder"
              >
              <input
                class="button reject" :class="{ cliked: isRejected }" type="button" value="주문거절"
                @click="rejectOrder"
              >
            </div>
          </div>
        </div>
        <div class="msg-container">
          <div class="msg-1">
            {{ msg1 }}
          </div>
          <div class="msg-2">
            {{ msg2 }}
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
  background-color: white;
  // border: 1px solid black;
}

body {
  margin-top: 0px;
}

.body-container {
  // background-color: pink;
  width: 100%;
  height: 88vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
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
}

.button {
  font-size: 20px;
  font-weight: bold;
  border: 0px;
  padding: 10px;
  margin-left: 10px;
  border-radius: 40px;
  width: 120px;
  height: 50px;
  // line-height: ;
  color: white;
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
</style>
