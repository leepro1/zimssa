<template>
  <div>
    <form @submit.prevent="submitForm">
      <div>
        <label for="rentGrntAmt">임차보증금액:</label>
        <input type="text" id="rentGrntAmt" v-model="formData.rentGrntAmt" />
      </div>
      <div>
        <label for="age">법정동 코드:</label>

        <select id="trgtLwdgCd" v-model="formData.trgtLwdgCd">
    <option value="">선택하세요</option>
    <option value="1100000000">서울특별시</option>
    <option value="2100000000">부산광역시</option>
    <option value="2200000000">대구광역시</option>
    <option value="2300000000">인천광역시</option>
    <option value="2400000000">광주광역시</option>
    <option value="2500000000">대전광역시</option>
    <option value="2600000000">울산광역시</option>
    <option value="2900000000">세종특별자치시</option>
    <option value="3100000000">경기도</option>
    <option value="3200000000">강원도</option>
    <option value="3300000000">충청북도</option>
    <option value="3400000000">충청남도</option>
    <option value="3500000000">전라북도</option>
    <option value="3600000000">전라남도</option>
    <option value="3700000000">경상북도</option>
    <option value="3800000000">경상남도</option>
    <option value="3900000000">제주특별자치도</option>
  </select>
      </div>
      <div>
        <label for="age">만 나이:</label>
        <input type="text" id="age" v-model="formData.age" />
      </div>
      <div>
        <label for="weddStcd">결혼 구분:</label>
  <select id="weddStcd" v-model="formData.weddStcd">
    <option value="">선택하세요</option>
    <option value="1">미혼</option>
    <option value="2">기혼</option>
    <option value="3">신혼</option>
    <option value="4">결혼예정</option>
  </select>      </div>
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
  <select id="grntPrmeActnDvcdCont" v-model="formData.grntPrmeActnDvcdCont">
    <option value="">선택하세요</option>
    <option value="01">2자녀이상</option>
    <option value="02">다문화가정</option>
    <option value="03">한부모가구</option>
    <option value="04">장애인</option>
    <option value="05">국가유공자</option>
    <option value="06">의사상자</option>
    <option value="07">신용회복지원자</option>
    <option value="08">사회적배려대상자</option>
    <option value="09">정책서민금융 이용자</option>
    <option value="10">영세자영업자</option>
    <option value="11">임대주택 입주자</option>
    <option value="12">분할상환 예정자</option>
    <option value="13">주택도시기금 전세대출 기존 이용자</option>
    <option value="14">제2금융권 전세대출 기존 이용자</option>
    <option value="15">서울시 갱신만료 임차인</option>
    <option value="16">부산시 공공임대주택(럭키7하우스) 임차인</option>
    <option value="17">고정금리 전세대출 신청자</option>
    <option value="18">전세사기피해자 등</option>
    <option value="19">영구임대주택 입주자</option>
  </select>
      </div>
      <button type="submit">제출</button>
    </form>

    <div v-if="responseData">
      <p>추천 상품 이름: {{ responseData.rcmdProdNm }}</p>

      <p>임차 보증금액 최대 한도율: {{ responseData.rentGrntMaxLoanLmtRate }}</p>
      <p>요청 대상 내용: {{ responseData.reqTrgtCont }}</p>

      <p>이자 지원 내용: {{ responseData.intSprtCont }}</p>
      <p>최대 대출 한도 금액: {{ responseData.maxLoanLmtAmt }}</p>
    
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { submitFormData, getJunseInfo } from '@/api/junseproduct';
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
    // 사용자 아이디를 가져옵니다.
    await fetchUserId();
    
    // 폼 데이터 출력
    console.log("Form Data:");
    console.log(formData.value);
    
    // 폼 데이터를 서버에 제출합니다.
    await submitFormData(
      formData.value.id, // 사용자 아이디
      formData.value,    // 폼 데이터
      // 성공 시 실행될 함수
      (response) => {
        console.log("Form data submitted successfully.");
        // 제출 후, 서버에서 반환된 데이터를 사용하여 추가 작업을 수행합니다.
        console.log("Response from submitFormData:");
        console.log(response.data);
        
        // 추가 작업을 위해 반환된 데이터를 responseData에 할당합니다.
        responseData.value = response.data;
        
        // 사용자 정보를 기반으로 추가 작업을 수행하기 위해 getJunseInfo 함수 호출
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
      },
      // 실패 시 실행될 함수
      (error) => {
        console.error("Failed to submit form data.");
        console.error(error);
        // 오류 처리
        // ex) 사용자에게 오류 메시지 표시
      }
    );
  } catch (error) {
    console.error("An error occurred:", error);
    // 오류 처리
    // ex) 사용자에게 오류 메시지 표시
  }
};
</script>

<style scoped>
/* 필요한 스타일을 추가하세요 */
</style>
