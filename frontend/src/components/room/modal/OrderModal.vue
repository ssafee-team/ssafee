<template>
  <div class="modal">
    <div class="modal-content">
      <div class="modal-title">주문하기</div>
      <hr />
      <input type="text" v-model="nameInput" placeholder="이름을 입력하세요" />
      <div class="modal-buttons">
        <button @click="closeModal" class="close">취소</button>
        <button @click="confirmOrder" class="confirm">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    items: Array,
  },
  data() {
    return {
      nameInput: "",
    };
  },
  methods: {
    closeModal() {
      this.$emit("close");
    },
    confirmOrder() {
      if (!this.nameInput.trim()) {
        alert("이름을 입력하세요.");
        return;
      }

      // 백에 입력한 이름 및 주문 정보 전달하기 위해 저장
      const orderInfo = {
        participant_name: this.nameInput,
        items: this.items, // items는 부모 컴포넌트에서 props로 받아온 데이터
      };
      console.log("주문 정보:", orderInfo);

      // 모달 닫기
      this.closeModal();
    },
  },
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
}

.modal-content {
  width: 40%;
  height: 200px;
  background: #344a53;
  border-radius: 5px;
  padding: 20px;
  margin-top: 20px;
  border: 1px solid black;
  text-align: center;
  color: white;
  border: none;
}

.modal-title {
  padding: 10px;
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  justify-content: space-between;
  display: flex;
  position: sticky;
}

.modal-buttons {
  margin-top: 40px;
  /* position: sticky; */
}

input {
  width: 100%;
  height: 30px;
  font-weight: bold;
  font-size: 16px;
  margin-top: 10px;
}

.close {
  width: 120px;
  cursor: pointer;
  border: none;
  background: #eb4e5a;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  padding: 10px 15px;
  font-weight: bold;
  font-size: 18px;
  margin-right: 10px;
}

.confirm {
  width: 120px;
  cursor: pointer;
  border: none;
  background: #00a7d0;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  padding: 10px 15px;
  font-weight: bold;
  font-size: 18px;
}
</style>
