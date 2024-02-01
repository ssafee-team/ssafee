import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/api/v1/shops";
// const url = "/server";

function getShops(param, success, fail) {
  local.get(`${url}`, { param }).then(success).catch(fail);
  console.log("가져올게욧");
}

// function getShops(param, success, fail) {
//   local.get(`${url}/api/v1/shops`, { param }).then(success).catch(fail);
//   console.log("가져올게욧");
// }

//메뉴 카테고리 조회
function getMenuCategories(id, success, fail) {
  local.get(`${url}/${id}/menu-categories`).then(success).catch(fail);
}

//메뉴 카테고리 선택 > 메뉴 조회
function getMenusByCategory(shop_id, mc_id, success, fail) {
  local.get(`${url}/${shop_id}/menu-categories/${mc_id}/menus`).then(success).catch(fail);
}

export { getShops, getMenuCategories, getMenusByCategory };
