import { localAxios } from "@/util/http-commons";
//http 통신 라이브러리 : axios
//응답제어(예외처리): 성공 시 then(), 실패 시 catch()

//async/await 사용법
//function 앞에 async를 붙이고 객체 앞에 await 붙이기
// async 해당 함수는 자동으로 프로미스(객체)를 반환
// 비동기 처리 패턴

//localAxios 함수 사용하기 위해 선언
const local = localAxios();

//파티 생성 url 정의
const url = "/api/v1/parties";

//예시) 노션의 api 명세 참고
// 파티를 생성할 경우 총무의 정보를 백단에 보내주는 함수 예시
async function createParty(param, success, fail) {
  console.log("param", param);
  await local.post(`${url}`, param).then(success).catch(fail);
}

//예시2)
// 파티 목록을 조회
// function getPartiesToday(param, success, fail) {
//     local.get(`${url}`, { params }).then(success).catch(fail);
// }

export { createParty };
