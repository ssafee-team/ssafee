<script setup lang="ts">
import { Client } from '@stomp/stompjs'

const { apiBase, wsEndpoint } = useRuntimeConfig().public

const roomId = ref(null)
const showModal = ref(false)
const shopId = ref(null)

const client = new Client({
  brokerURL: wsEndpoint,
  onConnect: () => {
    client.subscribe(`/sub/shop/${shopId.value}/order`, (message) => {
      roomId.value = JSON.parse(message.body).roomId

      showModal.value = true
    })
  },
})

function setShopId() {
  // manager-token 가져오기
  const managerToken = useLocalStorage<string>('manager-token', null)
  // console.log(managerToken.value)

  // manager-token이 존재하는 경우에만 요청 보내기
  if (managerToken.value) {
    fetch(`${apiBase}/v1/managers/me`, {
      headers: {
        Authorization: `Bearer ${managerToken.value}`,
      },
    })
      .then(response => response.json())
      .then((data) => {
        shopId.value = data.shopId

        client.activate()
      })
      .catch((error) => {
        console.error('Error fetching shopId:', error)
      })
  }
  else {
    console.error('Manager token not found.')
  }
}

onMounted(() => {
  setShopId()
})

onUnmounted(() => {
  client.deactivate()
})
</script>

<template>
  <div class="main-container">
    <div v-if="!showModal" class="main-message">
      <h1>😥 주문대기 중이에요 😥</h1>
      <h3>주문이 도착하면 알려드릴게요 !</h3>
    </div>
    <!-- 모달 컴포넌트를 조건부로 렌더링합니다. -->
    <ManagerModal v-if="showModal" :room-id="roomId" :shop-id="shopId" style="margin: 30px;" @close="showModal = false" />
    <!-- <div class="main-message">
      <h1>😂 아직은 G5에서 파티가 생성되지 않았어요 😂</h1>
      <h3>SSAFEE 를 위한 “프로모션”을 진행하면 주문빈도가 증가할지도..?</h3>
    </div>

    <div class="main-message">
      <h1>😥 지금은 운영시간이 아니에요 ㅠㅠ 😥</h1>
      <h3>운영시간을 변경하시려면 상단 “설정” 을 이용해 주세요</h3>
    </div>

    <div class="main-message">
      <h1>😍 G5가 단체주문방을 만들었어요 😍</h1>
      <h3>[마감시간] 에 주문 내역이 도착하니 기대해주세요 !</h3>
    </div> -->
  </div>
</template>

<style scoped>
body,
html {
  height: 100%;
  margin: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.header {
  position: absolute;
  top: 0;
  width: 100%;
}

.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.main-message {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 20px;
  /* 각 메시지 사이의 간격 */
  color: gray;
}
</style>
