<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useLocalStorage } from '@vueuse/core'
import { useRouter } from 'vue-router'
import { createParty } from '@/api/party'
import CreateHeader from '@/components/common/CreateHeader.vue'
import { getShops } from '@/api/shop'

const router = useRouter()
const headerHeight = ref('72px') // 예시로 100px를 기본값으로 설정
const token = useLocalStorage('user-token', null)
if (token.value === null)
  await router.push('/')

const form = ref({
  roomTitle: '',
  shop_id: '',
  name: '',
  class: '',
  batch: '',
  deadline: '',
  bankName: '',
  accountNumber: '',
  phoneNumber: '',
})

const formErrors = ref({
  roomTitle: false,
  name: false,
  class: false,
  batch: false,
  deadline: false,
  bankName: false,
  accountNumber: false,
  phoneNumber: false,
})

const shops = ref([])

onMounted(() => {
  console.log('시작')
  getShopList()
})

function getShopList(param) {
  // API 호출
  getShops(
    param,

    ({ data }) => {
      console.log('가져온 데이터: ', data)
      shops.value.push(data)
    },
    (error) => {
      console.log(error)
    },
  )
}

// shop_id에 대한 watch만 따로 처리
watch(
  () => form.value.shop_id,
  (newValue) => {
    // newValue가 존재하고 빈 문자열이 아닌 경우에만 처리
    if (newValue !== undefined && newValue !== '') {
      // 정수로 변환하여 저장
      form.value.shop_id = Number.parseInt(newValue)
    }
  },
)

// shop_id를 제외한 나머지 필드들에 대한 watch 생성
Object.keys(form.value).forEach((key) => {
  // shop_id는 이미 위에서 처리되었으므로 여기서는 처리하지 않음
  if (key !== 'shop_id') {
    watch(
      () => form.value[key],
      (newValue) => {
        // 여기서는 단순히 값이 비어있지 않은지 확인합니다.
        // 필요에 따라 더 복잡한 검증 로직을 추가할 수 있습니다.
        formErrors.value[key] = !newValue.trim()
      },
    )
  }
})

// 기존에 정의된 함수들...

function submitForm() {
  const requiredFields = ['name', 'generation', 'shop_id', 'classroom', 'last_order_time', 'creator']
  const isAnyFieldEmpty = requiredFields.some(
    field =>
      (typeof partyData.value[field] === 'string' && partyData.value[field].trim() === '')
      || (typeof partyData.value[field] === 'object'
      && Object.values(partyData.value[field]).some(val => val.trim() === '')),
  )

  if (isAnyFieldEmpty) {
    // 필드가 비어있으면 경고 메시지 띄우기
    EmptyModal.value = true
  }
  else {
    // 필드가 모두 채워져 있으면 모달 열기 및 데이터 전송
    modalOpen()
    createParty(token.value, partyData.value, onSuccess, onFailure)
  }

  let isValid = true
  for (const [key, value] of Object.entries(form.value)) {
    if (!value) {
      formErrors.value[key] = true
      isValid = false
    }
    else {
      formErrors.value[key] = false
    }
  }

  if (!isValid) {
    // alert('빈 항목을 채워주세요.');

  }

  // 유효한 경우, 폼 제출 로직을 여기에 추가합니다.
  // 예: createParty(...)
}

const PickPlatform = ref('')
const Pickdelivery = ref('')

const isGongchaSelected = computed(() => PickPlatform.value === '공차')
const isComposedSelected = computed(() => PickPlatform.value === '컴포즈드')
const isBaekSelected = computed(() => PickPlatform.value === '백다방')

const isBaeminSelected = ref(false)
const isSsafySelected = ref(false)

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
  if (PickPlatform.value === itemName)
    PickPlatform.value = ''
  else if (PickPlatform.value === '')
    PickPlatform.value = itemName
}

function addDelivery(itemName) {
  if (Pickdelivery.value === itemName)
    Pickdelivery.value = ''
  else if (Pickdelivery.value === '')
    Pickdelivery.value = itemName
}

const platformValue = computed(() => {
  return PickPlatform.value === '컴포즈드' ? 1 : 0
})

const partyData = computed(() => ({
  name: form.value.roomTitle,
  generation: form.value.batch,
  classroom: form.value.class,
  last_order_time: form.value.deadline,
  shop_id: form.value.shop_id,
  creator: {
    name: form.value.name,
    email: 'skip',
    bank: form.value.bankName,
    account: form.value.accountNumber,
    webhook_url: 'skip',
  },
}))

// 유효성 검사
function checkExistData(value, dataName) {
  if (value === '') {
    alert(`${dataName} 입력해주세요!`)
    return false
  }
  return true
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

function classValidation() {
  // 숫자만 남기고 모든 문자 제거
  form.value.class = form.value.class.replace(/[^0-9]/g, '')
  // 2자리 숫자를 초과하는 입력 제거
  if (form.value.class.length > 1)
    form.value.class = form.value.class.slice(0, 1)

  const numericValue = Number.parseInt(form.value.class, 10)

  if (numericValue < 1 || numericValue > 7) {
    console.log(numericValue)
    // 범위를 벗어난 경우 경고 표시 및 입력값 초기화
    window.alert('입력값은 확인')
    form.value.class = ''
  }
}

function batchValidation() {
  // 숫자만 남기고 모든 문자 제거
  form.value.batch = form.value.batch.replace(/[^0-9]/g, '')
  // 2자리 숫자를 초과하는 입력 제거
  if (form.value.batch.length > 2)
    form.value.batch = form.value.batch.slice(0, 2)

  const numericValue = Number.parseInt(form.value.class, 10)

  // 숫자가 1부터 30 사이가 아닐 경우
  if (numericValue < 1 || numericValue > 30) {
    window.alert('입력값은 1부터 30 사이여야 합니다.')
    form.value.class = ''
  }
}

function timeValidation(event) {
  // 숫자와 콜론만 허용
  const validCharacters = /[\d:]/
  const isCharacterValid = validCharacters.test(event.key) || event.key === 'Backspace'

  if (!isCharacterValid)
    event.preventDefault() // 유효하지 않은 문자 입력 차단

  // 5자리를 초과하는 입력 차단
  if (form.value.deadline.length >= 5 && event.key !== 'Backspace' && event.key !== 'Delete')
    event.preventDefault()
}
const TimeModal = ref(false)
// const formatTime = () => {
//   // 시간 형식 검증
//   form.value.deadline = form.value.deadline.replace(/[^\d:]/g, '');

//   if (form.value.deadline.length === 5) {
//     const [hours, minutes] = form.value.deadline.split(':');
//     if (parseInt(hours) > 23 || parseInt(minutes) > 59) {
//       TimeModal.value = true
//       form.value.deadline = '';
//     }
//   }
// };

function checkName(name) {
  if (!checkExistData(name, '이름을'))
    return false
  const nameRegExp = /^[가-힣]{2,4}$/
  if (!nameRegExp.test(name)) {
    alert('이름이 올바르지 않습니다.')
    return false
  }
  return true
}

// 이하는 모달
const modalCheck = ref(false)
const EmptyModal = ref(false)
function modalClose() {
  TimeModal.value = false
  EmptyModal.value = false
}
function modalOpen() {
  modalCheck.value = !modalCheck.value
}
// console.log(modalCheck.value);
// console.log(form.value);

//   function submitForm() {
//   // 필요한 필드들이 공백인지 확인
//   const requiredFields = ['name', 'generation', 'classroom', 'last_order_time', 'creator'];
//   const isAnyFieldEmpty = requiredFields.some(field =>
//     (typeof partyData.value[field] === 'string' && partyData.value[field].trim() === '') ||
//     (typeof partyData.value[field] === 'object' && Object.values(partyData.value[field]).some(val => val.trim() === ''))
//   );

//   if (isAnyFieldEmpty) {
//     // 필드가 비어있으면 경고 메시지 띄우기
//     EmptyModal.value = true
//   } else {
//     // 필드가 모두 채워져 있으면 모달 열기 및 데이터 전송
//     modalOpen();
//     createParty(partyData.value, onSuccess, onFailure);
//   }
// }

// 성공 콜백 함수를 정의합니다.
function onSuccess(response) {
  console.log('성공:', response)
  console.log(response.headers.location)
  // const responseObject = response.data.headers.location
  const locationPath = response.headers.location
  const parts = locationPath.split('/') // '/'를 기준으로 문자열을 분할
  const roomcode = parts[parts.length - 1]
  window.location.href = `/room/${roomcode}`
}

// 실패 콜백 함수를 정의합니다.
function onFailure(error) {
  console.error('실패:', error)
}

// createParty 함수를 호출합니다.

const deadlineError = ref(false) // 마감시간 에러 상태

// 기존 함수와 변수들...

function formatTime() {
  // 시간 형식 검증 및 현재 시간 이후인지 확인
  form.value.deadline = form.value.deadline.replace(/[^\d:]/g, '')
  if (form.value.deadline.length === 5) {
    const [hours, minutes] = form.value.deadline.split(':')
    const deadlineDate = new Date()
    deadlineDate.setHours(hours, minutes)
    const now = new Date()
    if (deadlineDate <= now) {
      deadlineError.value = true
      form.value.deadline = ''
    }
    else {
      deadlineError.value = false
    }
  }
}
</script>

<template>
  <div v-show="modalCheck" class="modal-wrap">
    <div class="modal-container">
      <p class="p">
        방이 생성되었습니다
      </p>

      <div style="text-align: right">
        <RouterLink to="/">
          <button class="button-modal" @click="modalOpen">
            확인
          </button>
        </RouterLink>
      </div>
    </div>
  </div>

  <div v-if="EmptyModal" class="modal-wrap">
    <div class="modal-container">
      <p class="p">
        빈 항목을 채워주세요.
        <br>
      </p>
      <div style="text-align: right">
        <button class="button-modal" @click="modalClose">
          확인
        </button>
      </div>
    </div>
  </div>

  <div v-if="TimeModal" class="modal-wrap">
    <div class="modal-container">
      <p class="p">
        입력형식을 확인해주세요(HH:MM)
      </p>
      <div>
        <button class="button-modal" @click="modalClose">
          확인
        </button>
      </div>
    </div>
  </div>

  <CreateHeader />
  <div id="empty" />

  <div class="parent">
    <div class="child2">
      <main class="form-container">
        <div class="form-field">
          <label for="roomTitle">파티명</label>

          <div class="input-with-error">
            <input id="roomTitle" v-model="form.roomTitle" type="text" maxlength="32" placeholder="방 제목을 입력해주세요">
            <span v-if="formErrors.roomTitle" class="error-message">방 제목을 입력해주세요.</span>
          </div>
        </div>

        <div class="form-field">
          <label for="cafe">카페</label>
          <div class="input-with-error">
            <select id="cafe" v-model="form.shop_id" class="input-style">
              <option disabled value="">
                카페를 선택하세요
              </option>
              <!-- 수정된 부분: v-for 디렉티브에서 각 카페의 이름을 가져와서 표시 -->
              <option v-for="shop in shops[0]" :key="shop.id" :value="shop.id">
                {{ shop.name }}
              </option>
            </select>
            <span v-if="formErrors.shop_id" class="error-message">카페를 선택하세요.</span>
          </div>
        </div>

        <div class="form-field">
          <label for="name">이름</label>
          <div class="input-with-error">
            <input
              id="name" v-model="form.name" type="text" :class="{ 'error-input': formErrors.name }" maxlength="8"
              placeholder="김싸피"
            >
            <span v-if="formErrors.name" class="error-message">이름을 입력해주세요.</span>
          </div>
        </div>
        <!-- <div class="form-field">
          <label for="name">이름</label>
          <div class="input-with-error">
            <input type="text" id="name" v-model="form.name" maxlength="8" placeholder="김싸피">
            <span v-if="formErrors.name" class="error-message">이름을 입력해주세요.</span>
          </div>
        </div> -->

        <div class="form-field">
          <label for="class">반</label>
          <div class="input-with-error">
            <input id="class" v-model="form.class" type="text" placeholder="2" @input="classValidation">
            <span v-if="formErrors.class" class="error-message">반을 입력해주세요.</span>
          </div>
        </div>

        <div class="form-field">
          <label for="batch">기수</label>
          <div class="input-with-error">
            <input id="batch" v-model="form.batch" type="text" placeholder="10" @input="batchValidation">
            <span v-if="formErrors.batch" class="error-message">기수를 입력해주세요.</span>
          </div>
        </div>

        <div class="form-field">
          <label for="deadline">마감시간</label>
          <div class="input-with-error">
            <input
              id="deadline" v-model="form.deadline" type="text" placeholder="13:00" @keydown="timeValidation"
              @input="formatTime"
            >
            <!-- 현재 시간 이후만 입력 가능 에러 메시지 -->

            <span v-if="deadlineError" class="error-message">시간은 현재 시각 이후만 입력 가능합니다.</span>
            <span v-if="formErrors.deadline" class="error-message">마감 시간을 입력해주세요.</span>
          </div>
        </div>

        <div class="form-field">
          <label for="bankName">은행명</label>
          <div class="input-with-error">
            <input id="bankName" v-model="form.bankName" type="text" maxlength="32" placeholder="은행명을 입력해주세요">
            <span v-if="formErrors.bankName" class="error-message">은행명을 입력해주세요.</span>
          </div>
        </div>

        <div class="form-field">
          <label for="accountNumber">계좌번호</label>
          <div class="input-with-error">
            <input id="accountNumber" v-model="form.accountNumber" type="text" placeholder="계좌번호를 입력해주세요">
            <span v-if="formErrors.accountNumber" class="error-message">계좌번호를 입력해주세요.</span>
          </div>
        </div>

        <div class="form-field">
          <label for="hook">WebHook_URL</label>
          <div class="input-with-error">
            <input id="hook" v-model="form.hook" type="text">
          </div>
        </div>
        <!-- <div class="form-field">
      <label for="phoneNumber">전화번호</label>
      <input type="text" id="phoneNumber" v-model="form.phoneNumber" maxlength="15"
      placeholder="010-1234-5678">
    </div> -->
        <div class="button-container">
          <button class="button-style" @click="submitForm">
            생성하기
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<style>
#empty {
  height: 50px;
}

.border1 {
  /* margin: 20px auto;
  margin-left: 50px; */
  display: inline-block;
  /* 이미지를 인라인 블록 요소로 설정 */
  margin-bottom: 10px;
  /* 이미지 사이의 여백 */
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
  border: 3px solid #1E293B;
  border-radius: 15px;
  max-width: 800px;

  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;

}

.form-field {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

label {
  font-size: 20px;
  margin-right: 10px;
  width: 180px;
  font-weight: bold;
  color: #020817;
}

input {
  flex-grow: 1;
  height: 40px;
  padding: 8px;
  font-size: 18px;
  border: 3px solid #1E293B;
  border-radius: 15px;
  box-sizing: border-box;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}
.input-style {
  flex-grow: 1;
  height: 40px;
  padding: 8px;
  font-size: 18px;
  border: 3px solid #1E293B;
  border-radius: 15px;
  box-sizing: border-box;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}
/* header {
  font-size: 2rem;
  width: 100%;
  background-color: #344a53;
  justify-content: space-between;
} */

#coffeeshop {
  color: #e9fcff;
  display: flex;
  justify-content: center;
}

.button-style {
  background-color: #020817;
  /* 녹색 배경 */
  color: white;
  /* 흰색 텍스트 */
  padding: 15px 32px;
  /* 상하, 좌우 패딩 */
  text-align: center;
  /* 텍스트 가운데 정렬 */
  text-decoration: none;
  /* 텍스트 밑줄 제거 */
  display: inline-block;
  /* 인라인 블록 요소로 표시 */
  font-size: 16px;
  /* 글꼴 크기 */
  margin: 4px 0px;
  /* 마진 */
  cursor: pointer;
  /* 마우스 커서를 포인터로 변경 */
  border: none;
  /* 테두리 없애기 */
  font-weight: bold;
  border-radius: 5px;
  /* 테두리 둥글게 */
}

/* 버튼을 감싸는 컨테이너의 스타일 - 버튼을 가운데 정렬 */
.button-container {
  text-align: center;
  /* 가운데 정렬 */
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
  display: none;
  /* 마지막 child에는 선을 표시하지 않음 */
}

.grayscale {
  filter: grayscale(100%);
  /* 흑백 필터 적용 */
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
  background: #343844;
  border-radius: 5px;
  padding: 5px 10px;
  box-sizing: border-box;
}

.button-modal {
  /* position: relative; */
  /* left: 92%; */
  /* display: flex; */

  margin-bottom: 5px;
  padding: 10px 20px;
  /* 버튼 내부 여백 */
  border: none;
  /* 테두리 제거 */
  background-color: #00a5e7;
  /* 배경색 */
  color: white;
  /* 글자색 */
  /* text-align: center; 텍스트 가운데 정렬 */
  text-decoration: none;
  /* 텍스트 밑줄 제거 */
  /* display: inline-block; 인라인 블록 요소로 표시 */
  font-size: 14px;
  /* 글꼴 크기 */
  cursor: pointer;
  /* 마우스 커서를 포인터로 변경 */
  border-radius: 5px;
  /* 둥근 모서리를 만들기 위해 테두리 반경 설정 */
  font-weight: bold;
}

.error-message {
  color: red;
  /* 에러 메시지 색상 */
  font-size: 14px;
  /* 에러 메시지 글꼴 크기 */
  margin-top: 5px;
  /* 입력 필드와의 여백 */
}

.input-with-error {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.cafe-selection {
  display: block;
  /* 기본적으로는 보임 */
}

.p {
  font-size: 20px;
  text-align: center;
  color: white;
  font-weight: bold;
}

@media screen and (max-width: 768px) {
  .parent {
    flex-direction: column;
  }

  .cafe-selection {
    display: block;
    /* 화면 크기에 따라 숨김 해제 */
    order: 3;
    /* 필요한 경우 순서 조정 */
  }

  .child2,
  .child {
    width: 100%;
    /* 모바일 화면에서 자식 요소들이 전체 폭을 차지하도록 조정 */
    order: 1;
    /* child2를 먼저 나오게 하려면 순서 조정 */
  }

  /* 필요에 따라 추가적인 스타일 조정 */

  .p {
    font-size: 16px;
  }
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
