<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useBrowserLocation, useFetch, useLocalStorage } from '@vueuse/core'
import { jwtDecode } from 'jwt-decode'
import { onMounted, onUnmounted, ref } from 'vue'
import moment from 'moment'
import CreateHeader from '@/components/common/CreateHeader.vue'

interface Party {
  id: number
  name: string
  generation: number
  classroom: number
  last_order_time: string
  created_time: string
  shop_id: number
  user_id: number
}

interface Shop {
  id: number
  name: string
  address: string
  phone: string
  image: string
  enabled_order: boolean
  minimum_price: number
  closed: boolean
}

const location = useBrowserLocation()
const router = useRouter()
const token = useLocalStorage('user-token', null)
const { data: parties } = await useFetch('/api/v1/parties').get().json<Party[]>()
const { data: shops } = await useFetch('/api/v1/shops').get().json<Shop[]>()
const shopRecord = shops.value?.reduce((acc, shop) => ({ ...acc, [shop.id]: shop }), {}) as Record<number, Shop>

function isPartyOpened(party: Party) {
  const now = moment()
  const deadline = moment(party.last_order_time, 'HH:mm')
  return now.isBefore(deadline)
}

function handleAuth() {
  const pushCreate = () => router.push('/CreateRoomView')
  if (token.value !== null) {
    const decoded = jwtDecode(token.value)
    if (Date.now() < decoded.exp! * 1000)
      return pushCreate()
    token.value = null
  }

  const registraion = 'google'
  const url = `/api/v1/oauth2/authorization/${registraion}?redirect_uri=${location.value.origin}/login/oauth2/redirect/${registraion}`
  const width = 600
  const height = 600
  const left = (window.innerWidth - width) / 2
  const top = (window.innerHeight - height) / 2
  window.open(url, 'OAuth2 Login', `toolbar=no, menubar=no, width=${width}, height=${height}, top=${top}, left=${left}`)
  window.addEventListener('message', (event) => {
    token.value = event.data.token
    pushCreate()
  })
}

const currentDate = ref('')
const currentDayOfWeek = ref('')
const currentTime = ref('')

function updateCurrentDateTime() {
  const now = new Date()
  currentDate.value = now.toLocaleDateString()
  currentDayOfWeek.value = '일월화수목금토'[now.getDay()]
  currentTime.value = now.toLocaleTimeString()
}

// 컴포넌트가 처음으로 렌더링될 때 현재 시간을 설정하는 함수
onMounted(() => {
  updateCurrentDateTime()
  setInterval(updateCurrentDateTime, 1000)
})
</script>

<template>
  <div id="app">
    <CreateHeader />
    <div class="content">
      <div class="time-info">
        <div>
          {{ currentDate }} ({{ currentDayOfWeek }})
        </div>
        <div>{{ currentTime }}</div>
      </div>
      <div class="link-info">
        <div class="link-container">
          <button v-for="party in parties" :key="party.id" class="party-button">
            <div class="last-order">
              <div>마감시간 {{ party.last_order_time }}</div>
            </div>
            <div> {{ shopRecord[party.shop_id].name }} </div>
            <div> {{ party.name }} </div>

            <div class="order-info">
              <div class="order-status" :class="{ blue: isPartyOpened(party), red: !isPartyOpened(party) }">
                {{ isPartyOpened(party) ? '주문 중' : '주문 마감' }}
              </div>
            </div>
          </button>
        </div>

        <div class="create-party">
          <button class="plusbutton plusbutton:hover dis" @click="handleAuth">
            파티 생성
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.content {
  display: flex;
  flex-direction: row;

}

.time-info {
  display: flex;
  width: 20%;
  border-radius: 10px;
  flex-direction: column;
  border: 3px solid #1E293B;
  margin-left: 20px;
  margin-top: 20px;
  margin-right: 40px;
  font-weight: bold;
  font-size: 20px;
  align-items: center;
  height: 200px;
  justify-content: center;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);

}

.link-info {
  display: flex;
  flex-direction: column;
  align-items: center;

}

#app>span {
  font-size: 30px;
  background-color: black;
  display: flex;
  justify-content: center;
  color: white;
  box-sizing: border-box;
  width: 100%;
}

.link-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px;
  padding: 20px;
  border: 3px solid #1E293B;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  border-radius: 10px;
  height: 400px;
  width: 900px;
  max-height: 400px;
  overflow-y: auto;
  /* 전체 너비를 차지하도록 설정 */
}

.link-container::-webkit-scrollbar {
  display: none;
}

.last-order {
  width: 100%;
  text-align: left;
  margin-left: 10px;
}

button {
  font-size: 20px;
  /* 폰트 크기 설정 */
  background-color: #ffffff;
  /* background-color: #f5f5f5; */
  /* 배경색 설정 */
  color: #344a53;
  width: 800px;
  font-weight: bold;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);

}

.party-button {
  align-items: center;
  border-radius: 10px;
  margin: 20px;
  border: 3px solid;
  display: flex;
  flex-direction: column;
}

.party-button div {
  width: 100%;
}

.create-party {
  display: flex;
}

.plusbutton {
  font-size: 20px;
  /* 폰트 크기 설정 */
  /* background-color: #f5f5f5; */
  /* 배경색 설정 */
  color: #344a53;
  /* 글자색 설정 */
  padding: 10px 20px;
  /* 상하, 좌우 패딩 설정 */
  border: 3px solid #1E293B;
  /* 테두리 제거 */
  border-radius: 25px;
  /* 테두리 둥글게 */
  cursor: pointer;
  /* 커서 모양을 손가락 모양으로 */
  width: 800px;
  display: flex;
  margin-top: 10px;
  justify-content: center;
}

.plusbutton:hover {
  background-color: #1E293B;
  /* 버튼에 마우스를 올렸을 때 배경색 변경 */
  color: white;
}

.link-container a {
  text-decoration: none;
  /* 밑줄 제거 */
}

.order-status {
  text-align: right;
  margin-right: 10px;
}

.blue {
  color: #00A7D0;
}

.red {
  color: #EB4E5A;
}

/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {
  header {
    font-size: 18px;
    /* 화면이 작을 때 텍스트 크기 조절 */
  }

  button {
    width: 600px;
  }

  .plusbutton {
    width: 600px;
  }
}

.bannarname {
  display: flex;
  /* font-size: 30px; */
  margin: 20px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  width: 100%;
}
</style>
