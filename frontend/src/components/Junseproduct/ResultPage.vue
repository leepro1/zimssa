<template>
  <div class="container" :class="{ overlay: !memberStore.isLogin }">
    <div class="mypage-box">
      <h2 class="title">맞춤형 추천상품</h2>
      <form class="user-info">
        <div class="form-group">
          <label><span class="purple-text">추천 상품:</span></label>
          <input type="text" :value="safeResponseData.rcmdProdNm" readonly />
        </div>
        <div class="form-group">
          <label><span class="purple-text">임차 보증금액 최대 한도율:</span></label>
          <input type="text" :value="safeResponseData.rentGrntMaxLoanLmtRate" readonly />
        </div>
        <div class="form-group">
          <label><span class="purple-text">상품 설명:</span></label>
          <input type="text" :value="safeResponseData.reqTrgtCont" readonly />
        </div>
        <div class="form-group">
          <label><span class="purple-text">이자 지원:</span></label>
          <input type="text" :value="safeResponseData.intSprtCont" readonly />
        </div>
        <div class="form-group">
          <label><span class="purple-text">최대 대출 한도 금액:</span></label>
          <input type="text" :value="safeResponseData.maxLoanLmtAmt" readonly />
        </div>
      </form>
    </div>
    <div v-if="!memberStore.isLogin" class="login-overlay">
      <span>로그인 후에 이용 가능합니다</span>
      <button class="toLoginBtn" @click="goToLogin">로그인으로 이동</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { getJunseInfo } from "@/api/junseproduct";
import { findById2 } from "@/api/user";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const router = useRouter();
const memberStore = useMemberStore();
const responseData = ref(null);
const formData = ref({
  rentGrntAmt: "",
  trgtLwdgCd: "",
  age: "",
  weddStcd: "",
  myIncmAmt: "",
  myTotDebtAmt: "",
  ownHsCnt: "",
  grntPrmeActnDvcdCont: "",
  id: "",
});

const fetchUserId = async () => {
  await findById2(
    (response) => {
      formData.value.id = response.data.userInfo.id;
      submitForm();
    },
    (error) => {}
  );
};

const submitForm = async () => {
  getJunseInfo(
    formData.value.id,
    (response1) => {
      responseData.value = response1.data;
    },
    (error) => {}
  );
};

const safeResponseData = computed(() => {
  return (
    responseData.value || {
      rcmdProdNm: "",
      rentGrntMaxLoanLmtRate: "",
      reqTrgtCont: "",
      intSprtCont: "",
      maxLoanLmtAmt: "",
    }
  );
});

watch(
  () => memberStore.isLogin,
  (newVal) => {
    if (!newVal) {
      Object.keys(formData.value).forEach((key) => {
        formData.value[key] = "";
        responseData.value = null;
      });
    }
  }
);

const goToLogin = () => {
  router.push({ name: "user-login" });
};

onMounted(fetchUserId);
</script>
<style scoped>
.container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f8f9fa;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

.mypage-box {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 600px;
}

.title {
  margin-bottom: 20px;
  text-align: center;
  color: #7468b6;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  color: #555;
}

input[type="text"] {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  outline: none;
}

input[type="text"]:read-only {
  background-color: #eee;
}

.purple-text {
  color: #7468b6;
  font-weight: bold;
}

p {
  text-align: center;
  margin-top: 20px;
}

.overlay {
  opacity: 1;
}

.login-overlay {
  flex-direction: column;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
  border-radius: 10px;
  color: rgb(0, 0, 0);
  font-size: 20px;
  font-weight: bold;
}

.toLoginBtn {
  border-color: #7468b6;
  color: #fff;
  background-image: linear-gradient(45deg, #ad88c6 50%, transparent 50%);
  background-position: 100%;
  background-size: 400%;
  transition: background 300ms ease-in-out;
  border-radius: 12px;
  width: 200px;
  height: 35px;
  color: black;
  margin: 0 20px;
}

.toLoginBtn:hover {
  background-color: #ad88c6; /* 호버 시 배경색을 #ad88c6로 변경 */
  color: #ffffff; /* 호버 시 텍스트 색상을 흰색으로 변경 */
}
</style>
