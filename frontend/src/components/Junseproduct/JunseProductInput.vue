<template>
  <div class="container">
    <div class="form-box">
      <h2 class="title">내 정보 입력</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="rentGrntAmt">임차보증금액</label>
          <input
            placeholder="200000000"
            type="text"
            id="rentGrntAmt"
            v-model="formData.rentGrntAmt"
          />
        </div>
        <div class="form-group">
          <label for="trgtLwdgCd">지역</label>
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
        <div class="form-group">
          <label for="age">만 나이</label>
          <input type="text" id="age" v-model="formData.age" />
        </div>
        <div class="form-group">
          <label for="weddStcd">결혼 구분</label>
          <select id="weddStcd" v-model="formData.weddStcd">
            <option value="">선택하세요</option>
            <option value="1">미혼</option>
            <option value="2">기혼</option>
            <option value="3">신혼</option>
            <option value="4">결혼예정</option>
          </select>
        </div>
        <div class="form-group">
          <label for="myIncmAmt">연 소득금액</label>
          <input placeholder="65000000" type="text" id="myIncmAmt" v-model="formData.myIncmAmt" />
        </div>
        <div class="form-group">
          <label for="myTotDebtAmt">총 부채금액</label>
          <input
            placeholder="50000000"
            type="text"
            id="myTotDebtAmt"
            v-model="formData.myTotDebtAmt"
          />
        </div>
        <div class="form-group">
          <label for="ownHsCnt">주택보유수</label>
          <input placeholder="1" type="text" id="ownHsCnt" v-model="formData.ownHsCnt" />
        </div>
        <div class="form-group">
          <label for="grntPrmeActnDvcdCont">보증 우대 구분</label>
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
        <div class="form-actions">
          <router-link :to="{ name: 'ResultPage' }">
            <button type="submit" @click="submitForm">맞춤형 추천 받기</button>
          </router-link>
        </div>
      </form>
    </div>
  </div>

  <div v-if="responseData" class="results form-box">
    <h3>결과</h3>
    <div class="result-info">
      <div class="form-group">
        <label>추천 상품 이름:</label>
        <div>{{ responseData.rcmdProdNm }}</div>
      </div>
      <div class="form-group">
        <label>임차 보증금액 최대 한도율:</label>
        <div>{{ responseData.rentGrntMaxLoanLmtRate }}</div>
      </div>
      <div class="form-group">
        <label>요청 대상 내용:</label>
        <div>{{ responseData.reqTrgtCont }}</div>
      </div>
      <div class="form-group">
        <label>이자 지원 내용:</label>
        <div>{{ responseData.intSprtCont }}</div>
      </div>
      <div class="form-group">
        <label>최대 대출 한도 금액:</label>
        <div>{{ responseData.maxLoanLmtAmt }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { submitFormData, getJunseInfo } from "@/api/junseproduct";
import { findById2 } from "@/api/user";

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
  try {
    await findById2(
      (response) => {
        formData.value.id = response.data.userInfo.id;
      },
      (error) => {
        console.error(error);
        alert("사용자 정보를 가져오는 데 실패했습니다.");
      }
    );
  } catch (error) {
    console.error(error);
    alert("사용자 정보를 가져오는 데 실패했습니다.");
  }
};

const submitForm = async () => {
  try {
    await fetchUserId();

    await submitFormData(
      formData.value.id,
      formData.value,
      (response) => {
        responseData.value = response.data;

        getJunseInfo(
          formData.value.id,
          (response1) => {
            responseData.value = response1.data;
          },

          (error) => {
            console.error(error);
          }
        );
      },

      (error) => {
        console.error("Failed to submit form data.");
      }
    );
  } catch (error) {
    alert("폼 데이터를 제출하는 데 실패했습니다.");
  }
};
</script>
<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 87vh;
}

.form-box {
  width: 100%;
  max-width: 400px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.title {
  text-align: center;
  margin-bottom: 20px;
  color: #7468b6;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #7468b6;
}

input,
select {
  width: calc(100% - 12px);
  padding: 8px;
  margin-top: 3px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 15px;
}

.form-actions {
  text-align: center;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  border-radius: 5px;
  background-color: #7468b6;
  color: white;
  cursor: pointer;
  font-size: 1rem;
}

button:hover {
  background-color: #5d52a1;
}

.results {
  margin-top: 30px;
}
</style>
