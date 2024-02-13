import { localAxios } from '@/util/http-commons'

const local = localAxios()

const url = '/api/v1/managers'

async function managerLogin(param, success, fail) {
  await local.post(`${url}/login`, param).then(success).catch(fail)
}

async function managerModify(param, success, fail) {
  await local.put(`${url}/me`).then(success).catch(fail)
}

async function managerDetail(param, success, fail) {
  await local.get(`${url}/me`).then(success).catch(fail)
}

export { managerLogin, managerModify, managerDetail }
