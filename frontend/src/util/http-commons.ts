import axios from 'axios'

const { VITE_VUE_API_URL } = import.meta.env

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      // 안될경우 application/json 으로 시도
      'Content-Type': 'application/json;charset=utf-8',
    },
  })
  return instance
}

export { localAxios }
