<template>
    <div class="container">
      <div v-if="responseData" class="mypage-box">
        <h2 class="title">맞춤형 추천상품</h2>
        <form class="user-info">
          <div class="form-group">
            <label><span class="purple-text">추천 상품</span></label>
            <input type="text" :value="responseData.rcmdProdNm" readonly>
          </div>
          <div class="form-group">
            <label><span class="purple-text">임차 보증금액 최대 한도율</span></label>
            <input type="text" :value="responseData.rentGrntMaxLoanLmtRate" readonly>
          </div>
          <div class="form-group">
            <label><span class="purple-text">상품 설명</span></label>
            <input type="text" :value="responseData.reqTrgtCont" readonly>
          </div>
          <div class="form-group">
            <label><span class="purple-text">이자 지원</span></label>
            <input type="text" :value="responseData.intSprtCont" readonly>
          </div>
          <div class="form-group">
            <label><span class="purple-text">최대 대출 한도 금액</span></label>
            <input type="text" :value="responseData.maxLoanLmtAmt" readonly>
          </div>
        </form>
      </div>
      <div v-else>
        <p>로딩 중...</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { getJunseInfo } from '@/api/junseproduct';
  import { findById2 } from "@/api/user";
  
  const responseData = ref(null);
  const formData = ref({
    rentGrntAmt: '',
    trgtLwdgCd: '',
    age: '', 
    weddStcd: '',
    myIncmAmt: '',
    myTotDebtAmt: '',
    ownHsCnt: '',
    grntPrmeActnDvcdCont: '',
    id: '', // 사용자 아이디 필드 추가
  });
  
  const fetchUserId = async () => {
    try {
      await findById2(
        (response) => {
          console.log("junse User Info Retrieved");
          console.log(response.data.userInfo);
          formData.value.id = response.data.userInfo.id; // 사용자 아이디 값을 formData에 할당
          submitForm();
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
    getJunseInfo(formData.value.id, 
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
  
  // 페이지가 실행될 때 사용자 정보를 가져오고 데이터를 제출합니다.
  import { onMounted } from 'vue';
  onMounted(fetchUserId);
  </script>
  
  <style scoped>
  .mypage-box {
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  
  .title {
    margin-bottom: 20px;
    text-align: center;
    color: #7468B6; /* 타이틀 색상 */
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
    color: #7468B6; /* 보라색 텍스트 */
    font-weight: bold; /* 굵은 글꼴 */
  
}
  
  p {
    
    text-align: center;
    margin-top: 20px;
  }
  </style>
  