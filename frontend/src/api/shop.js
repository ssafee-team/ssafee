import { localAxios } from '@/util/http-commons'

const local = localAxios()

const url = '/api/v1/shops'
// const url = "/server";

function getShops(param, success, fail) {
  local.get(`${url}`, { param }).then(success).catch(fail)
}

// 메뉴 카테고리 조회
function getMenuCategories(id, success, fail) {
  local.get(`${url}/${id}/menu-categories`).then(success).catch(fail)
}

// 메뉴 카테고리 선택 > 메뉴 조회
function getMenusByCategory(shop_id, mc_id, success, fail) {
  local.get(`${url}/${shop_id}/menu-categories/${mc_id}/menus`).then(success).catch(fail)
}

// 메뉴 선택 > 옵션 카테고리 조회
function getOptionCategory(shop_id, option_category_id, success, fail) {
  local.get(`${url}/${shop_id}/option-categories/${option_category_id}`).then(success).fail(fail)
}

export { getShops, getMenuCategories, getMenusByCategory, getOptionCategory }
