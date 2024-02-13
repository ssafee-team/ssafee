import { jwtDecode } from 'jwt-decode'
import { localAxios } from '@/util/http-commons'

const local = localAxios()

const TOKEN_NAME = 'userToken'
const OAUTH2_URLS = {
  google: '/api/v1/oauth2/authorization/google',
}

async function getOAuth2Callback(registraionId, param) {
  return local.get(`/api/v1/oauth2/callback/${registraionId}`, { params: param })
}

function setLocalStorageToken(token) {
  localStorage.setItem(TOKEN_NAME, token)
}

function getLocalStorageToken() {
  const isTokenExpired = token => jwtDecode(token).exp < Date.now() / 1000
  const token = localStorage.getItem(TOKEN_NAME)
  if (token !== null && isTokenExpired(token)) {
    localStorage.removeItem(TOKEN_NAME)
    return null
  }
  return token
}

export { OAUTH2_URLS, getOAuth2Callback, setLocalStorageToken, getLocalStorageToken }
