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
  id: "", // 사용자 아이디 필드 추가
});

const fetchUserId = async () => {
  await findById2(
    (response) => {
      console.log("junse User Info Retrieved");
      console.log(response.data.userInfo);
      formData.value.id = response.data.userInfo.id; // 사용자 아이디 값을 formData에 할당
      submitForm();
    },
    (error) => {
      console.log("makeInactive.called............");
      // console.error(error);
    }
  );
};

const submitForm = async () => {
  getJunseInfo(
    formData.value.id,
    // 성공 시 실행될 함수
    (response1) => {
      console.log("JunseInfo retrieved successfully.");
      // 서버에서 반환된 JunseInfo 데이터를 responseData에 추가합니다.
      console.log("Response from getJunseInfo:");
      console.log(response1.data);
      responseData.value = response1.data;
    },
    // 실패 시 실행될 함수
    (error) => {
      console.error("Failed to retrieve JunseInfo.");
      console.error(error);
      // 오류 처리
      // ex) 사용자에게 오류 메시지 표시
    }
  );
};

// 안전한 responseData를 제공하는 computed 속성
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
    console.log("memberStore.isLogin 변수 변경 => ", newVal);
  }
);

const goToLogin = () => {
  router.push({ name: "user-login" });
};

// 페이지가 실행될 때 사용자 정보를 가져오고 데이터를 제출합니다.
onMounted(fetchUserId);
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f8f9fa; /* 배경색 */
  position: relative;
}

.mypage-box {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.title {
  margin-bottom: 20px;
  text-align: center;
  color: #7468b6; /* 타이틀 색상 */
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
  color: #555; /* 라벨 텍스트 색상 */
}

input[type="text"] {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  outline: none;
}

input[type="text"]:read-only {
  background-color: #eee; /* 읽기 전용 input 배경색 */
}

.purple-text {
  color: #7468b6; /* 보라색 텍스트 */
  font-weight: bold; /* 굵은 글꼴 */
}

p {
  text-align: center;
  margin-top: 20px;
}

/* 회색빛 불투명 효과 및 상태창 */
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
  border-radius: 10px; /* 부모 요소의 border-radius와 맞춤 */
  color: rgb(0, 0, 0);
  font-size: 20px;
  font-weight: bold;
}

.toLoginBtn {
  border-color: 7468B6;
  color: #fff;
  background-image: linear-gradient(45deg, #ad88c6 50%, transparent 50%);
  background-position: 100%;
  background-size: 400%;
  transition: background 300ms ease-in-out;
  border-radius: 12px;
  width: 200px;
  height: 35px;
  color: black;
  margin: 0 20px; /* 좌우 간격을 균등하게 유지 */
}

.toLoginBtn:hover {
  background-position: 0;
}
</style>
