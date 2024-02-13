import { localAxios } from '@/util/http-commons'

// http 통신 라이브러리 : axios
// 응답제어(예외처리): 성공 시 then(), 실패 시 catch()

// async/await 사용법
// function 앞에 async를 붙이고 객체 앞에 await 붙이기
// async 해당 함수는 자동으로 프로미스(객체)를 반환
// 비동기 처리 패턴

const local = localAxios()

// const url = "/api/v1/";
// const url = `/api/v1/parties/`;
const url = '/api/v1/parties'

async function getCreator(param, success, fail) {
  // console.log("params:", param);
  await local.get(`${url}/${param}`).then(success).catch(fail)
}

async function getOrderList(param, success, fail) {
  await local.get(`${url}/${param}/order-menus`).then(success).catch(fail)
}

function getParticipants(code, success, fail) {
  local.get(`${url}/${code}/participants`).then(success).catch(fail)
}

function modifyParticipants(code, id, data, success, fail) {
  local.put(`${url}/${code}/participants/${id}`, data).then(success).catch(fail)
}

export { getCreator, getOrderList, getParticipants, modifyParticipants }
