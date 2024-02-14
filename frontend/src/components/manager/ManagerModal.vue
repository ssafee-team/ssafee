<script setup lang="ts">
// import { defineEmits, defineProps } from 'vue'
import { useLocalStorage } from '@vueuse/core'
import axios from 'axios'
import router from '@/router'

// const props = defineProps({
//   partyId: {
//     type: [Number, String],
//     default: null,
//   },
// })
// const emit = defineEmits(['close'])

const shopId = 1 // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const partyId = 10 // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const managerToken = useLocalStorage('manager-token', null)

async function onConfirm() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  await axios.post(`/api/v1/shops/${shopId}/orders/${partyId}/confirm`, null, config)
  router.push('/m-order-list')
}

async function onReject() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  await axios.post(`/api/v1/shops/${shopId}/orders/${partyId}/reject`, null, config)
  // emit('close')
}
</script>

<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">
          <slot name="header">
            기본 헤더
          </slot>
        </div>

        <div class="modal-body">
          <slot name="body">
            모달의 내용을 여기에 넣으세요.
          </slot>
        </div>

        <div class="modal-footer">
          <slot name="footer">
            <button @click="onConfirm">
              수락
            </button>
            <button class="modal-default-button" @click="onReject">
              거절
            </button>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 여기에 모달 컴포넌트의 스타일을 추가하세요. */
</style>
