<script setup>
import { ref } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"
import { useMenuStore } from "@/stores/menu"
import { findPassword as findPasswordAPI } from "@/api/user"

const router = useRouter()

const memberStore = useMemberStore()

const { isLogin, isLoginError } = storeToRefs(memberStore)
const { userLogin, getUserInfo } = memberStore
const { changeMenuState } = useMenuStore()

const loginUser = ref({
  id: "",
  password: "",
})

const isFindPasswordModalOpen = ref(false)
const findPasswordUserId = ref("")
const findPasswordMessage = ref("")
const isPasswordMessageError = ref(false)

const login = async () => {
  await userLogin(loginUser.value)
  let token = sessionStorage.getItem("accessToken")
  console.log(token)
  console.log("isLogin: " + isLogin.value)
  if (isLogin.value) {
    getUserInfo(token)
    changeMenuState()
    router.replace("/")
  }
}

const openFindPasswordModal = () => {
  isFindPasswordModalOpen.value = true
}

const closeFindPasswordModal = () => {
  isFindPasswordModalOpen.value = false
  findPasswordUserId.value = ""
  findPasswordMessage.value = ""
}

const findPassword = async () => {
  try {
    await findPasswordAPI(findPasswordUserId.value, 
      response => {
        findPasswordMessage.value = response.data
        isPasswordMessageError.value = false
      }, 
      () => {
        findPasswordMessage.value = "아이디를 확인해주세요."
        isPasswordMessageError.value = true
      }
    )
  } catch (error) {
    findPasswordMessage.value = "아이디를 확인해주세요."
    isPasswordMessageError.value = true
  }
}

const goToSignUp = () => {
  router.push({ name: "user-join" })
}
</script>

<template>
  <div class="container">
    <div class="login-box">
      <h2 class="title">LOGIN</h2>
      <form>
        <div class="form-group">
          <input
            type="text"
            class="form-input"
            v-model="loginUser.id"
            placeholder="아이디"
          />
        </div>
        <div class="form-group">
          <input
            type="password"
            class="form-input"
            v-model="loginUser.password"
            @keyup.enter="login"
            placeholder="비밀번호"
          />
        </div>
        <div class="form-group" v-if="isLoginError === true">
          <div class="alert alert-danger" role="alert">아이디 또는 비밀번호 확인해 주세요</div>
        </div>
        <div class="buttons">
          <button type="button" class="btn btn-primary btn-block" @click="login">로그인</button>
        </div>
        <div class="buttons-secondary">
          <button type="button" class="btn btn-secondary" @click="goToSignUp">회원가입</button>
          <button type="button" class="btn btn-warning" @click="openFindPasswordModal">비밀번호 찾기</button>
        </div>
      </form>
    </div>

    <div v-if="isFindPasswordModalOpen" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeFindPasswordModal">&times;</span>
        <h2>비밀번호 찾기</h2>
        <div class="form-group">
          <label for="findPasswordId" class="form-label">아이디를 입력하세요. 등록된 이메일로 임시비밀번호가 전송됩니다.</label>
          <input type="text" class="form-input" v-model="findPasswordUserId" placeholder="아이디" />
        </div>
        <div class="form-group" v-if="findPasswordMessage">
          <div :class="['warning-message', { 'error-message': isPasswordMessageError, 'info-message': !isPasswordMessageError }]" role="alert">{{ findPasswordMessage }}</div>
        </div>
        <div class="buttons">
          <button type="button" class="btn btn-primary" @click="findPassword">비밀번호 찾기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
body, html {
  overflow: hidden; /* Hide scrollbar */
  margin: 0;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 90vh;
  background-color: #f8f9fa;
}

.login-box {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.title {
  margin-bottom: 50px;
  text-align: center;
  color: #7468B6;
}

.form-group {
  margin-bottom: 20px; /* Increase spacing between form fields */
}

.form-label {
  display: block;
  color: #333;
  margin-bottom: 40px;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.buttons {
  margin-top: 20px;
}

.buttons .btn-block {
  width: 100%;
  margin-bottom: 10px;
}

.buttons-secondary {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

.buttons-secondary .btn {
  flex: 1;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

.btn-primary {
  background-color: #534787;
  color: #fff;
}

.btn-secondary {
  background-color: #7468B6;
  color: #fff;
}

.btn-warning {
  background-color: #7468B6;
  color: #fff;
}

.btn-primary:hover,
.btn-secondary:hover,
.btn-warning:hover {
  opacity: 0.9;
}

/* Modal Styles */
.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fff;
  margin: 10% auto;
  padding: 20px;
  border: 1px solid #888;
  border-radius: 10px;
  width: 80%;
  max-width: 500px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.alert {
  padding: 10px;
  margin-bottom: 15px;
  border-radius: 5px;
}

.alert-danger {
  background-color: #ffe6e6;
  border-color: #e1afd1;
  color: #ad88c6;
}

.alert-info {
  background-color: #e7f3fe;
  border-color: #b3d7ff;
  color: #31708f;
}

.warning-message {
  text-align: left;
  border-radius: 5px;
  padding: 0px;
}

.error-message {
  color: red;
}

.info-message {
  color: blue;
}
</style>
