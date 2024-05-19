<template>
    <form @submit.prevent="submitForm">
      <div>
        <label for="rentGrntAmt">임차보증금액:</label>
        <input type="text" id="rentGrntAmt" v-model="formData.rentGrntAmt" />
      </div>
      <div>
        <label for="trgtLwdgCd">법정동코드:</label>
        <input type="text" id="trgtLwdgCd" v-model="formData.trgtLwdgCd" />
      </div>
      <div>
        <label for="age">만 나이:</label>
        <input type="text" id="age" v-model="formData.age" />
      </div>
      <div>
        <label for="weddStcd">결혼 구분:</label>
        <input type="text" id="weddStcd" v-model="formData.weddStcd" />
      </div>
      <div>
        <label for="myIncmAmt">연 소득금액:</label>
        <input type="text" id="myIncmAmt" v-model="formData.myIncmAmt" />
      </div>
      <div>
        <label for="myTotDebtAmt">총 부채금액:</label>
        <input type="text" id="myTotDebtAmt" v-model="formData.myTotDebtAmt" />
      </div>
      <div>
        <label for="ownHsCnt">주택보유수:</label>
        <input type="text" id="ownHsCnt" v-model="formData.ownHsCnt" />
      </div>
      <div>
        <label for="grntPrmeActnDvcdCont">보증 우대 조치 구분:</label>
        <input type="text" id="grntPrmeActnDvcdCont" v-model="formData.grntPrmeActnDvcdCont" />
      </div>
      <button type="submit">제출</button>
    </form>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { submitFormData } from '@/api/junseproduct';
  import { findById2 } from "@/api/user";
  
  const formData = ref({
    rentGrntAmt: '200000000',
  trgtLwdgCd: '2629000000',
  age: '22', 
  weddStcd: '1',
  myIncmAmt: '40000000',
  myTotDebtAmt: '10000000',
  ownHsCnt: '1',
  grntPrmeActnDvcdCont: '01',
    id: '', // 사용자 아이디 필드 추가
  });
  
  const fetchUserId = async () => {
  try {
    const response = await findById2(
      (response) => {
        console.log("junse User Info Retrieved");
        console.log(response.data.userInfo);
        formData.id = response.data.userInfo.id; // 사용자 아이디 값을 formData에 할당
      },
      (error) => {
        console.error(error);
        alert('사용자 정보를 가져오는 데 실패했습니다.');
      }
    );
  } catch (error) {
    console.error(error);
    alert('사용자 정보를 가져오는 데 실패했습니다.');
  }
};
  const submitForm = async () => {
    try {
        await fetchUserId(); // 사용자 아이디를 미리 가져옵니다.
        console.log(formData.id);
        console.log(formData)
        const response = await submitFormData(formData.id, formData.value);

      console.log(response);
      // 성공 처리
    } catch(error) {
      console.error(error);
      // 오류 처리
    }
  };
  </script>
  
  <style scoped>
  /* 필요한 스타일을 추가하세요 */
  </style>
  