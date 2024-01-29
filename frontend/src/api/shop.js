import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/api/v1/shops";

function getShops(param, success, fail) {
  local.get(`${url}`, { param }).then(success).catch(fail);
  console.log("가져올게욧");
}

export { getShops };
