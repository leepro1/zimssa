<script setup>
import { ref } from "vue"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"
import { useMemberStore } from "@/stores/member"
import { useMenuStore } from "@/stores/menu"

const router = useRouter()

const memberStore = useMemberStore()

const { isLogin, isLoginError } = storeToRefs(memberStore)
const { userLogin, getUserInfo } = memberStore
const { changeMenuState } = useMenuStore()

const loginUser = ref({
  id: "",
  password: "",
})

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
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">로그인</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <form>
          <div class="form-check mb-3 float-end">
            <input class="form-check-input" type="checkbox" />
            <label class="form-check-label" for="saveid"> 아이디저장 </label>
          </div>
          <div class="mb-3 text-start">
            <label for="id" class="form-label">아이디 : </label>
            <input
              type="text"
              class="form-control"
              v-model="loginUser.id"
              placeholder="아이디..."
            />
          </div>
          <div class="mb-3 text-start">
            <label for="password" class="form-label">비밀번호 : </label>
            <input
              type="password"
              class="form-control"
              v-model="loginUser.password"
              @keyup.enter="login"
              placeholder="비밀번호..."
            />
          </div>
          <div class="mb-3 text-start" v-if="isLoginError === true">
            <div class="alert alert-danger" role="alert">아이디 또는 비밀번호 확인해 주세요</div>
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="login">
              로그인
            </button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3">회원가입</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 아래는 CSS 스타일입니다 */
.container {
  margin-top: 50px;
}

.orange {
  background-color: #7468B6;
  padding: 0 5px;
  color: white;
}

.mb-3 {
  margin-bottom: 1rem;
}

.float-end {
  float: right;
}

.text-start {
  text-align: left;
}

.alert-danger {
  background-color: #FFE6E6;
  border-color: #E1AFD1;
  color: #AD88C6;
}

.btn-outline-primary {
  color: #7468B6;
  border-color: #7468B6;
}

.btn-outline-primary:hover {
  background-color: #7468B6;
  color: white;
}

.btn-outline-success {
  color: #AD88C6;
  border-color: #AD88C6;
}

.btn-outline-success:hover {
  background-color: #AD88C6;
  color: white;
}
</style>
