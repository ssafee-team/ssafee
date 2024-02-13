<script setup>
import { defineEmits, defineProps } from 'vue'

const props = defineProps({
  partyId: {
    type: [Number, String],
    default: null,
  },
})

const emit = defineEmits(['close'])

function closeModal() {
  emit('close')
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
            <button class="modal-default-button" @click="closeModal">
              닫기
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
