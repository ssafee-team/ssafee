<script>
export default {
  name: 'ModalComponent',
  props: {
    partyId: {
      type: [Number, String],
      default: null,
    },
  },
  emits: ['close'],
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
            <button class="modal-default-button" @click="$emit('close')">
              닫기
            </button>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-wrapper {
  overflow: auto;
}

.modal-container {
  width: 300px;
  margin: 0 20px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease-in-out;
}

.modal-header {
  margin-bottom: 20px;
}

.modal-body {
  margin-bottom: 20px;
}

.modal-footer {
  text-align: right;
}

.modal-default-button {
  padding: 10px 20px;
  background-color: #eee;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
