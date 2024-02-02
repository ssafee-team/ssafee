<template>
  <div class="modal-wrap" v-show="modalCheck">
    <div class="modal-container">
      <p style="font-size: 33px; color: white">방이 생성되었습니다</p>

      <div>
        <RouterLink to="/">
          <button @click="modalOpen" class="button-modal">확인</button>
        </RouterLink>
      </div>
    </div>
  </div>


  <div class="modal-wrap" v-if="EmptyModal">
    <div class="modal-container">
      <p style="font-size: 26px; color: white">빈 항목이 있습니다.
        <br> 
      빈 항목을 채워주세요.</p>
      <div>
          <button @click="modalClose" class="button-modal">확인</button>
      </div>
    </div>
  </div>


  <div class="modal-wrap" v-if="TimeModal">
    <div class="modal-container">
      <p style="font-size: 30px; color: white">입력형식을 확인해주세요(HH:MM)</p>
      <div>
          <button @click="modalClose" class="button-modal">확인</button>
      </div>
    </div>
  </div>

  <header>
    <div id="coffeeshop">방 생성</div>
  </header>
  <div id="empty"></div>
  <div class="parent">
    <div class="child2">
      <main class="form-container">
        <div class="form-field">
      <label for="roomTitle">방제목</label>
      <input type="text" id="roomTitle" v-model="form.roomTitle" maxlength="32"
      placeholder="방 제목을 입력해주세요">
    </div>
    <div class="form-field">
      <label for="name">이름</label>
      <input type="text" id="name" v-model="form.name" maxlength="8"
      placeholder="김싸피">
    </div>
    <div class="form-field ">
      <label for="class">반</label>
      <input type="text" id="class" v-model="form.class" @input="classValidation"
      placeholder="2">
    </div>
    <div class="form-field">
      <label for="batch">기수</label>
      <input type="text" id="batch" v-model="form.batch" @input="batchValidation"
      placeholder="10">
    </div>
    <div class="form-field">
      <label for="deadline">마감시간</label>
      <input type="text" id="deadline" v-model="form.deadline" @keydown="timeValidation" @input = "formatTime"
      placeholder="13:00">
    </div>
    <div class="form-field">
      <label for="bankName">은행명</label>
      <input type="text" id="bankName" v-model="form.bankName" maxlength="32"
      placeholder="삼성은행">
    </div>
    <div class="form-field">
      <label for="accountNumber">계좌번호</label>
      <input type="text" id="accountNumber" v-model="form.accountNumber" @input = "bankValidation"
      placeholder="123-4567-890">
    </div>
    <!-- <div class="form-field">
      <label for="phoneNumber">전화번호</label>
      <input type="text" id="phoneNumber" v-model="form.phoneNumber" maxlength="15"
      placeholder="010-1234-5678">
    </div> -->
      </main>
    </div>

    <div class="child">
      <p><label>카페선택</label></p>
      <img
        v-on:click="addPlatform('공차')"
        src="../assets/img/coffeebrand/공차.jpg"
        alt="공차"
        :class="{ border1: true, grayscale: !isGongchaSelected }"
        width="150px"
        height="150px"
      />
      <br />

      <img
        v-on:click="addPlatform('컴포즈드')"
        src="../assets/img/coffeebrand/컴포즈드커피.jpg"
        alt=""
        :class="{ border1: true, grayscale: !isComposedSelected }"
        width="150px"
        height="150px"
      />
      <br />
      <img
        v-on:click="addPlatform('백다방')"
        src="../assets/img/coffeebrand/백다방커피.jpg"
        alt=""
        :class="{ border1: true, grayscale: !isBaekSelected }"
        width="150px"
        height="150px"
      />
    </div>

    <!-- <div class="child">
      <p><label> 플랫폼 선택</label></p>
      <img
        v-on:click="addDelivery('싸피')"
        src="../assets/img/ssaffee_로고.png"
        alt="싸"
        :class="{ border1: true, grayscale: !isSsafySelected }"
        width="150px"
        height="150px"
      />
      <p>사장님께 자동으로 주문내역을 전달합니다</p>
      <img
        v-on:click="addDelivery('배민')"
        src="../assets/img/delivery/배달플랫폼.jpg"
        alt="배달플랫폼"
        :class="{ border1: true, grayscale: !isBaeminSelected }"
        width="150px"
        height="150px"
      />
      <p>마감시간이 지난 후 타 배달 플랫폼을 활용하여 직접 주문해주셔야 합니다.</p>
    </div> -->
  </div>

  <div class="button-container">
    <button @click="submitForm" class="button-style">완료</button>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { getShops } from "@/api/shop";
import { createParty } from "@/api/party";

const form = ref({
        roomTitle: '',
        name: '',
        class: '',
        batch: '',
        deadline: '',
        bankName: '',
        accountNumber: '',
        phoneNumber: ''
})

//여기부터 상혁이가 작성--------------------
const shops = ref({
  id: "",
  name: "",
  image: "",
});

onMounted(() => {
  console.log("시작");
  getShopList();
});

const getShopList = (param) => {
  //API 호출
  getShops(
    param,

    ({ data }) => {
      console.log("가져온 데이터: ", data);
      shops.value.id = data.id;
      shops.value.name = data.name;
      shops.value.image = data.image;
    },
    (error) => {
      console.log(error);
    }
  );
};
// const shops = ref({
//   id: "",
//   name: "",
//   address: "",
//   phone: "",
//   image: "",

// })
//여기까지 상혁이가 작성--------------------

const PickPlatform = ref("");
const Pickdelivery = ref("");

const isGongchaSelected = computed(() => PickPlatform.value === "공차");
const isComposedSelected = computed(() => PickPlatform.value === "컴포즈드");
const isBaekSelected = computed(() => PickPlatform.value === "백다방");

const isBaeminSelected = ref(false);
const isSsafySelected = ref(false);

// watch(Pickdelivery, (value) => {
//   if (value === "싸피") {
//     isBaeminSelected.value = false;
//     isSsafySelected.value = true;
//   } else if (value === "배민") {
//     isBaeminSelected.value = true;
//     isSsafySelected.value = false;
//   } else {
//     // 조건이 맞지 않을 경우의 로직
//     isBaeminSelected.value = false;
//     isSsafySelected.value = false;
//   }
// });

// console.log(form)
// console.log(addPlatform)
// function submitForm() {
// alert('제출이 완료되었습니다')
// console.log(form)
// }

function addPlatform(itemName) {
  if (PickPlatform.value === itemName) {
    PickPlatform.value = "";
  } else if (PickPlatform.value === "") {
    PickPlatform.value = itemName;
  }
}

function addDelivery(itemName) {
  if (Pickdelivery.value === itemName) {
    Pickdelivery.value = "";
  } else if (Pickdelivery.value === "") {
    Pickdelivery.value = itemName;
  }
}

const platformValue = computed(() => {
  return PickPlatform.value === "컴포즈드" ? 1 : 0;
});


const partyData = computed(() => ({
     "name": form.value.roomTitle,
     "generation": form.value.batch,
     "classroom": form.value.class,
     "last_order_time": form.value.deadline,
     "shop_id": 1,
     "creator": {
       "name": form.value.name,
       "email": "skip",
       "bank": form.value.bankName,
       "account": form.value.accountNumber
     }
   }));

// 유효성 검사
function checkExistData(value, dataName) {
  if (value == "") {
    alert(dataName + " 입력해주세요!");
      return false;}
        return true;
      }

// const NameValidation = () => {
//   // 한글 이름 2-4자를 확인하는 정규 표현식
//   form.value.name = form.value.name.replace(/^[가-힣]{2,4}$/, '')
// };

// const name = "예시"; // 사용자가 입력한 이름
// const isValid = NameValidation(name);

// if (!isValid) {
//   alert('이름은 한글로 2자에서 4자 사이여야 합니다.');
// }

const classValidation = () => {
  // 숫자만 남기고 모든 문자 제거
  form.value.class = form.value.class.replace(/[^0-9]/g, '');
  // 2자리 숫자를 초과하는 입력 제거
  if (form.value.class.length > 1) {
    form.value.class = form.value.class.slice(0, 1);
  }
  const numericValue = parseInt(form.value.class, 10);

  if (numericValue < 1 || numericValue > 7) {
    console.log(numericValue)
    // 범위를 벗어난 경우 경고 표시 및 입력값 초기화
    window.alert('입력값은 확인');
    form.value.class = '';
  }
};


const batchValidation = () => {
  // 숫자만 남기고 모든 문자 제거
  form.value.batch = form.value.batch.replace(/[^0-9]/g, '');
  // 2자리 숫자를 초과하는 입력 제거
  if (form.value.batch.length > 2) {
    form.value.batch = form.value.batch.slice(0, 2);
  }
  const numericValue = parseInt(form.value.class, 10);

  // 숫자가 1부터 30 사이가 아닐 경우
  if (numericValue < 1 || numericValue > 30) {
    window.alert('입력값은 1부터 30 사이여야 합니다.');
    form.value.class = ''; 
  }
};



const timeValidation = (event) => {
  // 숫자와 콜론만 허용
  const validCharacters = /[\d:]/;
  const isCharacterValid = validCharacters.test(event.key) || event.key === 'Backspace';

  if (!isCharacterValid) {
    event.preventDefault(); // 유효하지 않은 문자 입력 차단
  }

  // 5자리를 초과하는 입력 차단
  if (form.value.deadline.length >= 5 && event.key !== 'Backspace' && event.key !== 'Delete') {
    event.preventDefault();
  }
};
const TimeModal = ref(false)
const formatTime = () => {
  // 시간 형식 검증
  form.value.deadline = form.value.deadline.replace(/[^\d:]/g, '');

  if (form.value.deadline.length === 5) {
    const [hours, minutes] = form.value.deadline.split(':');
    if (parseInt(hours) > 23 || parseInt(minutes) > 59) {
      TimeModal.value = true
      form.value.deadline = '';
    }
  }
};


function checkName(name)
{if(!checkExistData(name,"이름을"))
return false;
var nameRegExp=/^[가-힣]{2,4}$/;
if(!nameRegExp.test(name)){alert("이름이 올바르지 않습니다.");
return false;}
return true; 
}





// 이하는 모달
const modalCheck = ref(false);
const EmptyModal = ref(false)
function modalClose() {
  TimeModal.value = false
  EmptyModal.value= false
}
function modalOpen() {
  modalCheck.value = !modalCheck.value;
  
}
  console.log(modalCheck.value)
  console.log(form.value)

  function submitForm() {
  // 필요한 필드들이 공백인지 확인
  const requiredFields = ['name', 'generation', 'classroom', 'last_order_time', 'creator'];
  const isAnyFieldEmpty = requiredFields.some(field => 
    (typeof partyData.value[field] === 'string' && partyData.value[field].trim() === '') ||
    (typeof partyData.value[field] === 'object' && Object.values(partyData.value[field]).some(val => val.trim() === ''))
  );

  if (isAnyFieldEmpty) {
    // 필드가 비어있으면 경고 메시지 띄우기
    EmptyModal.value = true
  } else {
    // 필드가 모두 채워져 있으면 모달 열기 및 데이터 전송
    modalOpen();
    createParty(partyData.value, onSuccess, onFailure);
  }
}


// 성공 콜백 함수를 정의합니다.
function onSuccess(response) {
  console.log('성공:', response);
  console.log(response.headers.location)
  // const responseObject = response.data.headers.location
  const locationPath = response.headers.location;
  const parts = locationPath.split('/'); // '/'를 기준으로 문자열을 분할
  const roomcode = parts[parts.length - 1];
  window.location.href = 'http://localhost:8083/room/' + roomcode
  
}

// 실패 콜백 함수를 정의합니다.
function onFailure(error) {
  console.error('실패:', error);
} 

// createParty 함수를 호출합니다.


</script>

<style>
#empty {
  height: 50px;
}
.border1 {
  /* margin: 20px auto;
  margin-left: 50px; */
  display: inline-block; /* 이미지를 인라인 블록 요소로 설정 */
  margin-bottom: 10px; /* 이미지 사이의 여백 */
}
.parent {
  display: flex;
}
.child2 {
  flex: 2;
}
.child {
  flex: 1;
  text-align: center;
}
.form-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
}

.form-field {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

label {
  font-size: 20px;
  margin-right: 10px;
  width: 180px;
}

input{
  flex-grow: 1;
  height: 40px;
  padding: 8px;
  font-size: 18px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

header {
  /* height: 50px; */
  font-size: 2rem;
  width: 100%;
  background-color: #344a53;
  justify-content: space-between;
}

#coffeeshop {
  color: #e9fcff;
  display: flex;
  justify-content: center;
}

.button-style {
  background-color: #344a53; /* 녹색 배경 */
  color: white; /* 흰색 텍스트 */
  padding: 15px 32px; /* 상하, 좌우 패딩 */
  text-align: center; /* 텍스트 가운데 정렬 */
  text-decoration: none; /* 텍스트 밑줄 제거 */
  display: inline-block; /* 인라인 블록 요소로 표시 */
  font-size: 16px; /* 글꼴 크기 */
  margin: 4px 0px; /* 마진 */
  cursor: pointer; /* 마우스 커서를 포인터로 변경 */
  border: none; /* 테두리 없애기 */
  border-radius: 4px; /* 테두리 둥글게 */
}

/* 버튼을 감싸는 컨테이너의 스타일 - 버튼을 가운데 정렬 */
.button-container {
  text-align: center; /* 가운데 정렬 */
}

.child,
.child2 {
  position: relative;
}

.child::after,
.child2::after {
  content: "";
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  width: 1px;
  background: #000;
}

.child:last-child::after {
  display: none; /* 마지막 child에는 선을 표시하지 않음 */
}

.grayscale {
  filter: grayscale(100%); /* 흑백 필터 적용 */
}

/* 이하는 모달 */
/* dimmed */
.modal-wrap {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
}
/* modal or popup */
.modal-container {
  position: relative;
  top: 12%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #344a53;
  border-radius: 20px;
  padding: 5px 40px;
  box-sizing: border-box;
}

.button-modal {
  position: relative;
  left: 92%;
  padding: 10px 20px; /* 버튼 내부 여백 */
  border: none; /* 테두리 제거 */
  background-color: #4c95af; /* 배경색 */
  color: white; /* 글자색 */
  text-align: center; /* 텍스트 가운데 정렬 */
  text-decoration: none; /* 텍스트 밑줄 제거 */
  display: inline-block; /* 인라인 블록 요소로 표시 */
  font-size: 14px; /* 글꼴 크기 */
  cursor: pointer; /* 마우스 커서를 포인터로 변경 */
  border-radius: 15px; /* 둥근 모서리를 만들기 위해 테두리 반경 설정 */
}
</style>
