import { localAxios } from "@/util/http-commons";

const local = localAxios();

// const url = "/api/v1/shops";
const url = "/server";

function getShops(param, success, fail) {
  local.get(`${url}/api/v1/shops`, { param }).then(success).catch(fail);
  console.log("가져올게욧");
}

// function getShops(param, success, fail) {
//   local.get(`${url}/api/v1/shops`, { param }).then(success).catch(fail);
//   console.log("가져올게욧");
// }

export { getShops };
