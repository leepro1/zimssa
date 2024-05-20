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
    console.log(id.value)
    
    const result = await check(id.value);
    
    // console.log(result)
  //  console.log(result.value)
   //console.log(result.data)
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
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">회원가입</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <form @submit.prevent="join">
          <div class="mb-3">
            <label for="name" class="form-label">이름 : </label>
            <input type="text" class="form-control" v-model="name" placeholder="이름..." />
          </div>
          <div class="mb-3">
            <label for="id" class="form-label">아이디 : </label>
            <div class="input-group">
              
              <input type="text" class="form-control" v-model="id" @blur="checkId" placeholder="공백없이 문자와 숫자로만 3자 이상 20자 이내로 입력하세요" />
              <button type="button" class="btn btn-outline-secondary" @click="checkId">중복확인</button>
            </div>

            <small v-show="idCheckStatus === 'success'" class="text-success">{{ idCheckMessage }}</small>
  <small v-show="idCheckStatus === 'error'" class="text-danger">{{ idCheckMessage }}</small>
  <small v-show="!isIdValid && idCheckStatus === ''" class="text-danger">아이디는 공백없이 문자와 숫자로만 3자 이상 20자 이내로 입력하세요</small>
          </div>
          <div class="mb-3">
        <label for="password" class="form-label">비밀번호 : </label>
        <input type="password" class="form-control" v-model="password" placeholder="8자 이상 20자 이내로 입력하세요. 영문자, 숫자, 특수문자 사용할 수 있으며 공백은 사용할 수 없습니다" />
        <small v-if="isPasswordValid" class="text-success">사용 가능한 비밀번호입니다.</small>
        <small v-else class="text-danger">비밀번호는 8자 이상 20자 이내로 입력하세요. 영문자와 숫자를 포함해야 하며, 특수문자는 선택적으로 사용할 수 있습니다. 공백은 사용할 수 없습니다.</small>
      </div>
      <div class="mb-3">
  <label for="pwdcheck" class="form-label">비밀번호 확인 : </label>
  <input type="password" class="form-control" v-model="pwdcheck" placeholder="비밀번호 확인..." />
  <small v-show="password === pwdcheck && pwdcheck !== ''" class="text-success">비밀번호가 일치합니다.</small>
  <small v-show="password !== pwdcheck && pwdcheck !== ''" class="text-danger">비밀번호가 일치하지 않습니다.</small>
</div>
          <div class="mb-3">
            <label for="emailid" class="form-label">이메일 : </label>
            <div class="input-group">
              <input type="text" class="form-control" v-model="email_id" placeholder="이메일아이디" />
              <span class="input-group-text">@</span>
              <input v-if="isCustomDomain" type="text" class="form-control" v-model="custom_email_domain" placeholder="직접 입력..." />
              <select v-else class="form-select" v-model="email_domain" aria-label="이메일 도메인 선택">
                <option value="선택">선택</option>
                <option value="ssafy.com">ssafy.com</option>
                <option value="google.com">google.com</option>
                <option value="naver.com">naver.com</option>
                <option value="kakao.com">kakao.com</option>
                <option value="custom">직접 입력</option>
              </select>
            </div>
          </div>
          <div class="col-auto text-center">
            <button type="submit" class="btn btn-outline-primary mb-3">회원가입</button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3" @click="resetForm">초기화</button>
          </div>
        </form>
        <div v-if="!isIdValid || !isPasswordValid" class="text-center text-danger">
          회원가입 조건을 만족하지 않습니다. 입력 내용을 확인해 주세요.
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 스타일 정의 */
</style>
