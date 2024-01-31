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
function getMenuCategories(id, param, success, fail) {
  local.get(`${url}/${id}/menu-categories`, { param }).then(success).catch(fail);
}

export { getShops, getMenuCategories };
