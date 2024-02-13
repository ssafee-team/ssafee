import { localAxios } from '@/util/http-commons'

const local = localAxios()

const url = '/api/v1/shops/'

async function startDelivery(shopId, partyId, success, fail) {
  await local.post(`${url}/${shopId}/orders/${partyId}/start-delivery`).then(success).catch(fail)
}

async function reject(shopId, partyId, success, fail) {
  await local.post(`${url}/${shopId}/orders/${partyId}/reject`).then(success).catch(fail)
}

async function made(shopId, partyId, success, fail) {
  await local.post(`${url}/${shopId}/orders/${partyId}/made`).then(success).catch(fail)
}

async function confirm(shopId, partyId, success, fail) {
  await local.post(`${url}/${shopId}/orders/${partyId}/confirm`).then(success).catch(fail)
}

async function getOrderList(shopId, success, fail) {
  await local.get(`${url}/${shopId}/orders`).then(success).catch(fail)
}

async function getOrderListDetail(shopId, partyId, success, fail) {
  await local.get(`${url}/${shopId}/orders/${partyId}/confirm`).then(success).catch(fail)
}

export { startDelivery, reject, made, confirm, getOrderList, getOrderListDetail }
