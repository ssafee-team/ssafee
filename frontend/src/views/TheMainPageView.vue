<script setup>
import { useRouter } from 'vue-router'
import { useBrowserLocation, useFetch, useLocalStorage } from '@vueuse/core'
import { jwtDecode } from 'jwt-decode'

const location = useBrowserLocation()
const router = useRouter()
const token = useLocalStorage('user-token', null)
const { data: parties } = await useFetch('/api/v1/parties').get().json()
const { data: shops } = await useFetch('/api/v1/shops').get().json()
const shopRecord = shops.value.reduce((acc, shop) => ({ ...acc, [shop.id]: shop }), {})

function isPartyOpened(party) {
  const [hours, minutes] = party.last_order_time.split(':')
  const lastOrderTime = new Date()
  lastOrderTime.setHours(hours)
  lastOrderTime.setMinutes(minutes)
  return new Date() < lastOrderTime
}

function handleAuth() {
  const navigateToCreate = () => router.push('/CreateRoomView')
  if (token.value !== null) {
    const decoded = jwtDecode(token.value)
    if (Date.now() < decoded.exp * 1000)
      return navigateToCreate()
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
    navigateToCreate()
  })
}
</script>

<template>
  <div id="app">
    <header>
      <p class="bannarname">
        현재 개설된 방
      </p>
    </header>
    <div class="link-container">
      <div>
        <button v-for="party in parties" :key="party.id" class="party-button">
          <div> {{ shopRecord[party.shop_id].name }} </div>
          <div> {{ party.name }} </div>
          <div> {{ isPartyOpened(party) ? '주문 중' : '주문 마감' }} </div>
        </button>
      </div>
    </div>

    <div class="link-container">
      <button class="plusbutton plusbutton:hover dis" @click="handleAuth">
        파티 생성
      </button>
    </div>
  </div>
</template>

<style scoped>
header {
  /* background-color: #344a53;
    color: #e9fcff;
    padding: 10px; */
  height: 72px;
  /* display: flex;
    justify-content: space-between;
    align-items: center; */
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
  justify-content: center;
  width: 100%;
  /* 전체 너비를 차지하도록 설정 */
}

button {
  font-size: 20px;
  /* 폰트 크기 설정 */
  background-color: #f5f5f5;
  /* 배경색 설정 */
  color: #344a53;
  /* 글자색 설정 */
  padding: 10px 20px;
  /* 상하, 좌우 패딩 설정 */
  border: none;
  /* 테두리 제거 */
  border-radius: 5px;
  /* 테두리 둥글게 */
  width: 800px;
  display: flex;
  margin-top: 20px;
  justify-content: center;
  font-weight: bold;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  border: 1px solid #f5f5f5;
}

.party-button {
  display: flex;
  flex-direction: column;
}

.plusbutton {
  font-size: 20px;
  /* 폰트 크기 설정 */
  background-color: #f5f5f5;
  /* 배경색 설정 */
  color: #344a53;
  /* 글자색 설정 */
  padding: 10px 20px;
  /* 상하, 좌우 패딩 설정 */
  border: none;
  /* 테두리 제거 */
  border-radius: 5px;
  /* 테두리 둥글게 */
  cursor: pointer;
  /* 커서 모양을 손가락 모양으로 */
  width: 800px;
  display: flex;
  margin-top: 20px;
  justify-content: center;
}

.plusbutton:hover {
  background-color: #344a53;
  /* 버튼에 마우스를 올렸을 때 배경색 변경 */
  color: white;
}

.link-container a {
  text-decoration: none;
  /* 밑줄 제거 */
}

header {
  background-color: #344a53;
  color: #e9fcff;
  min-height: 70px;
  display: flex;
  font-size: 24px;
  justify-content: space-between;
  align-items: center;
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
