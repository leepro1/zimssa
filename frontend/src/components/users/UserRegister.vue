<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { check, deleteUser } from "@/api/user"; // deleteUser 메서드 import

const name = ref('');
const id = ref('');
const password = ref('');
const pwdcheck = ref('');
const email_id = ref('');
const email_domain = ref('선택');
const custom_email_domain = ref('');
const idCheckMessage = ref('');
const idCheckStatus = ref(''); // success, error, or empty

const router = useRouter();

const join = async () => {
  if (password.value !== pwdcheck.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  const finalEmailDomain = email_domain.value === 'custom' ? custom_email_domain.value : email_domain.value;

  const userData = {
    name: name.value,
    id: id.value,
    password: password.value,
    emailId: email_id.value,
    emailDomain: finalEmailDomain
  };
  console.log('User Data:', userData); // 데이터 확인을 위한 로그

  try {
    const response = await fetch(`${import.meta.env.VITE_VUE_API_URL}/user/join`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    });

    if (response.ok) {
      alert('회원가입이 성공적으로 완료되었습니다.');
      // 회원가입 성공 후 메인 화면으로 리디렉션
      router.push('/main'); // 메인 화면 경로에 맞게 조정하세요
    } else {
      const result = await response.json();
      alert(`회원가입 실패: ${result.message}`);
    }
  } catch (error) {
    console.error('회원가입 중 오류 발생:', error);
    alert('회원가입 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
  }
};

const resetForm = () => {
  name.value = '';
  id.value = '';
  password.value = '';
  pwdcheck.value = '';
  email_id.value = '';
  email_domain.value = '선택'; // 여기서 email_domain을 초기화합니다.
  custom_email_domain.value = '';
  idCheckMessage.value = '';
  idCheckStatus.value = '';
};

const isCustomDomain = computed(() => email_domain.value === 'custom');
const checkId = async () => {
  try {
    idCheckMessage.value = "사용가능합니다";
      idCheckStatus.value = 'success';
    const result = await check(id.value);
    console.log("result",result)
    if (result === '사용 가능한 아이디입니다.') {
      alert('사용 가능한 아이디입니다.');
      idCheckMessage.value = "사용가능합니다";
      idCheckStatus.value = 'success';
    } else {
      idCheckMessage.value = result;
      idCheckStatus.value = 'error';
    }
  } catch (error) {
    console.error('아이디 중복 검사 중 오류 발생:', error);
    idCheckMessage.value = '중복된 아이디입니다.';
    idCheckStatus.value = 'error';
  }
}

// ID must be 3-20 characters long, alphanumeric
const isIdValid = computed(() => /^[a-zA-Z0-9]{3,20}$/.test(id.value));
// Password must be 8-20 characters long, include letters and numbers, and may include special characters
const isPasswordValid = computed(() => /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*?&]{8,20}$/.test(password.value));
</script>

<template>
  <div class="container">
    <div class="signup-box">
      <h2 class="title">회원가입</h2>
      <form @submit.prevent="join">
        <input type="text" id="name" v-model="name" placeholder="이름" />
        <div class="input-group">
          <input type="text" id="id" v-model="id" @blur="checkId" placeholder="아이디" />
          <button type="button" @click="checkId">아이디 중복확인</button>
        </div>
        <small v-show="idCheckStatus === 'success'" class="text-success">{{ idCheckMessage }}</small>
        <small v-show="idCheckStatus === 'error'" class="text-danger">{{ idCheckMessage }}</small>
        <small v-show="!isIdValid && idCheckStatus === ''" class="text-danger"></small>
        <input type="password" id="password" v-model="password" placeholder="비밀번호 비밀번호는 8자 이상 20자 이내로 입력하세요." />
        <small v-if="isPasswordValid" class="text-success">사용 가능한 비밀번호입니다.</small>
        <small v-else class="text-danger"></small>
        <input type="password" id="pwdcheck" v-model="pwdcheck" placeholder="비밀번호 확인" />
        <small v-show="password === pwdcheck && pwdcheck !== ''" class="text-success">비밀번호가 일치합니다.</small>
        <small v-show="password !== pwdcheck && pwdcheck !== ''" class="text-danger">비밀번호가 일치하지 않습니다.</small>
        <div class="input-group">
          <input type="text" v-model="email_id" placeholder="이메일" />
          <span>@</span>
          <input v-if="isCustomDomain" type="text" v-model="custom_email_domain" placeholder="직접 입력..." />
          <select v-else v-model="email_domain">
            <option value="선택">선택</option>
            <option value="ssafy.com">ssafy.com</option>
            <option value="google.com">google.com</option>
            <option value="naver.com">naver.com</option>
            <option value="kakao.com">kakao.com</option>
            <option value="custom">직접 입력</option>
          </select>
        </div>
        <div class="buttons">
          <button type="submit" class="btn btn-primary">회원가입</button>
        </div>
      </form>
      <div v-if="!isIdValid || !isPasswordValid" class="validation-message">
        회원가입 조건을 만족하지 않습니다.     <br>
입력 내용을 확인해 주세요.
      </div>
    </div>
  </div>
</template>

<style scoped>
body, html {
  overflow: hidden; /* Hide scrollbar */
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 84vh;
  background-color: #f8f9fa;
}

.signup-box {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.title {
  margin-bottom: 50px;
  text-align: center;
  color: #7468B6;
}

input, select, button, span {
  width: 100%;
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.input-group {
  display: flex;
  align-items: center;
}

.input-group input {
  flex: 1;
}

.input-group span {
  flex: 0 0 30px;
  background-color: #ddd;
  border-radius: 0 5px 5px 0;
  text-align: center;
  line-height: 30px;
  margin-left: -1px; /* Removing gap */
}

.input-group button {
  flex: 0 0 auto;
  padding: 10px 20px;
  background-color: #7468B6;
  color: #fff;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  margin-left: 10px;
}

.input-group input:last-child {
  border-radius: 0 5px 5px 0;
}

.input-group select {
  border-radius: 0 5px 5px 0;
}

.buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

.btn-primary {
  background-color: #7468B6;
  color: #fff;
}

.btn-secondary {
  background-color: #AD88C6;
  color: #fff;
}

.btn-warning {
  background-color: #E1AFD1;
  color: #fff;
}

.btn-primary:hover,
.btn-secondary:hover,
.btn-warning:hover {
  opacity: 0.9;
}

.text-success {
  color: green;
  margin-bottom: 20px; /* 텍스트 성공 클래스에 마진 추가 */
}

.text-danger {
  color: red;
  
  margin-bottom: 20px; /* 텍스트 성공 클래스에 마진 추가 */

}

.validation-message {
  text-align: center;
  color: red;
  margin-top: 10px;
}
</style>
