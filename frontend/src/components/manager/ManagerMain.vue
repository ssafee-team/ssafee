<script setup>
import { onMounted, onUnmounted, ref } from 'vue'
import { useBrowserLocation } from '@vueuse/core'
import { Client } from '@stomp/stompjs'
import ManagerHeader from '@/components/common/ManagerHeader.vue'
import ManagerModal from '@/components/manager/ManagerModal.vue'

const location = useBrowserLocation()
const wsProtocol = location.value.protocol === 'https:' ? 'wss:' : 'ws:'
const wsEndpoint = '/ws'
const wsUrl = `${wsProtocol}//${location.value.host}${wsEndpoint}`

const partyId = ref(null)
const showModal = ref(false)
const shopId = '1' // 임시로 1로 설정

const client = new Client({
  brokerURL: wsUrl,
  onConnect: () => {
    client.subscribe(`/sub/shop/${shopId}/order`, (message) => {
      partyId.value = JSON.parse(message.body).party_id
      showModal.value = true
    })
  },
})

onMounted(() => {
  client.activate()
})

onUnmounted(() => {
  client.deactivate()
})
</script>

<template>
  <ManagerHeader />
  <div class="main-container">
    <div class="main-message">
      <h1>😥 주문대기 중이에요 😥</h1>
      <h3>주문이 도착하면 알려드릴게요 !</h3>
    </div>
    <!-- 모달 컴포넌트를 조건부로 렌더링합니다. -->
    <ManagerModal v-if="showModal" :party-id="partyId" @close="showModal = false" />
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
