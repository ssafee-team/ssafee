<script setup lang="ts">
import { defineEmits, onMounted, ref } from 'vue'
import { useLocalStorage } from '@vueuse/core'
import axios from 'axios'
import router from '@/router'

import music from '@/assets/music.mp3'

const props = defineProps({
  partyId: {
    type: [Number, String],
    default: null,
  },
})
const emit = defineEmits(['close'])

const shopId = 1 // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const partyId = ref<number>() // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const managerToken = useLocalStorage('manager-token', null)
const audio = new Audio(music)

// TODO: Chrome 정책 상 오디오 자동재생이 사용자경험에 악영향을 끼친다고해서 갑자기 에러터질 때가 있음. 사용자에게 "허용하시겠습니까?" 영구동의 받으면 에러 안생김
// TODO: 나중에 구현해야할 듯. 관련 에러는 다음과 같음 (Uncaught (in promise) DOMException: play() failed because the user didn't interact with the document first)

onMounted(() => {
  // audio.muted: true;
  audio.play()

  getOrders(shopId)
})

interface OrderResponse {
  party_id: number // Type should be lowercase
  name: string // Type should be lowercase
  shop_id: number // Type should be lowercase
}

const response = ref<OrderResponse[]>([]) // Should be an array of OrderResponse if expecting multiple orders
const totalPrice = ref(0)
const menuCount = ref(0)

// API 호출 함수 정의
async function getOrders(shopId: number) {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  try {
    const result = await axios.get(`/api/v1/shops/${shopId}/orders`, config) // URL should be a template literal
    // API에서 받은 데이터를 특정 타입으로 캐스팅
    response.value = result.data as OrderResponse[]
    // 부모 컴포넌트에서 받은 partyId prop와 일치하는 주문만 필터링
    // const filteredOrders = response.value.filter((order: { party_id: any }) => order.party_id === props.partyId)

    totalPrice.value = 0
    menuCount.value = 0

    // filteredOrders.forEach((order) => {
    //   order.choice_menus.forEach((choiceMenu) => {
    //     totalPrice.value += choiceMenu.menu.price // 메뉴 가격 합산
    //     menuCount.value++
    //     choiceMenu.option_categories.forEach((optionCategory) => {
    //       optionCategory.options.forEach((option) => {
    //         totalPrice.value += option.price // 옵션 가격 합산
    //       })
    //     })
    //   })
    // })
    // console.log(totalPrice)
    // console.log(menuCount)

    // await confirmOrder(shopId, partyId.value)
  }
  catch (error) {
    console.error('API 호출 중 오류 발생:', error)
  }
}

async function onConfirm() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  await axios.post(`/api/v1/shops/${shopId}/orders/${props.partyId}/confirm`, null, config)
  audio.pause()
  // router.push('/m-order-list')
  console.log(props.partyId.toString())
  router.push({ path: '/m-order-list', query: { partyId: props.partyId.toString() } })
}

async function onReject() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  await axios.post(`/api/v1/shops/${shopId}/orders/${props.partyId}/reject`, null, config)
  audio.pause()
  emit('close')
}
</script>

<template>
  <div class="modal">
    <div class="modal-mask">
      <slot name="header">
        <span style="font-size: 25px; font-weight: bold;">🔔 알 림 🔔</span>
        <!-- <p>{{ response.party_id }}</p> -->
      </slot>
    </div>

    <slot class="modal-body">
      <slot name="body">
        <span style="font-size: 30px; font-weight: bold;">주문금액 : {{ totalPrice }} 원</span>
        <br>
        <span style="font-size: 30px;">총 {{ menuCount }} 잔</span>
      </slot>
    </slot>

    <div class="modal-footer">
      <slot name="footer">
        <button style="background-color: #00A5E7;" @click="onConfirm">
          접수
        </button>
        <button class="modal-default-button" style="background-color: #EB4E5A;" @click="onReject">
          거절
        </button>
      </slot>
    </div>
  </div>
</template>

<style scoped>
/* 여기에 모달 컴포넌트의 스타일을 추가하세요. */
.modal {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 500px;
  height: 300px;
  background-color: #020817;
  color: white;
  border-radius: 25px;
}

button {
  margin: 10px;
  border-radius: 15px;
  font-size: 28px;
  font-weight: bold;
  border: none;
  padding: 5px;
  cursor: pointer;
  color: white;
}
</style>
