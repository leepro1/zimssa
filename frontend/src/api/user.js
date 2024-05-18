import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
  console.log("!!! param")
  console.log("param:"+param)
  await local.post(`/user/login`, param).then(success).catch(fail);
}

async function findById(id, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${id}`).then(success).catch(fail);
}


async function findById2(success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/member`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(id, success, fail) {
  await local.get(`/user/logout/${id}`).then(success).catch(fail);
}
async function updateUser(user, success, fail) {
 // const token = sessionStorage.getItem("accessToken");
  if (token) {
    local.defaults.headers["Authorization"] = `Bearer ${token.replace(/ /g, '')}`;
    await local.put(`/user/member`, user).then(success).catch(fail);
  } else {
    fail(new Error("No token found in sessionStorage"));
  }
}

export { userConfirm, findById, tokenRegeneration, logout,findById2 ,updateUser};
