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
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); 
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(id, success, fail) {
  await local.get(`/user/logout/${id}`).then(success).catch(fail);
}
async function updateUser(userId, user, success, fail) {
  await local.put(`/user/update/${userId}`, user).then(success).catch(fail);
}
async function deleteUser(userId,success,fail) {

  await local.delete(`/user/delete/${userId}`).then(success).catch(fail);
  
}

async function check(userId, success, fail) {
  console.log("check")
  console.log(userId)
  await local.get(`/user/check/${userId}`).then(success).catch(fail);
}
async function findPassword(userId, success, fail) {
  await local.post(`/user/findpassword/${userId}`).then(success).catch(fail);
}
export { findPassword,check,userConfirm, findById, tokenRegeneration, logout,findById2 ,updateUser,deleteUser};
