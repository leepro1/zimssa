<template>
  <div class="edit-user-container">
    <form v-if="user" @submit.prevent="updateUserInfo" class="edit-user-form">
      <div class="form-group">
        <label for="id">ID:</label>
        <input type="text" id="id" v-model="user.id" readonly>
      </div>
      <div class="form-group">
        <label for="name">이름:</label>
        <input type="text" id="name" v-model="user.name">
      </div>
      <div class="form-group">
        <label for="emailId">이메일 아이디:</label>
        <input type="text" id="emailId" v-model="user.emailId">
      </div>
      <div class="form-group">
        <label for="emailDomain">이메일 도메인:</label>
        <input type="text" id="emailDomain" v-model="user.emailDomain">
      </div>
      <div class="form-group">
        <label for="password">비밀번호:</label>
        <input type="password" id="password" v-model="user.password">
      </div>
      <div class="form-group">
        <label for="confirmPassword">비밀번호 확인:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword">
      </div>
      <button type="submit" class="submit-button">수정</button>
    </form>
  </div>
</template>

<script>
import { findById2, updateUser } from "@/api/user";

export default {
  name: "EditUser",
  data() {
    return {
      user: {
        id: '',
        name: '',
        password: '',
        emailId: '',
        emailDomain: ''
      },
      confirmPassword: ''
    };
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        await findById2(
          (response) => {
            console.log("User Info Retrieved", response.data.userInfo);
            this.user = response.data.userInfo;
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
    },
    async updateUserInfo() {
      try {
        if (this.user.password !== this.confirmPassword) {
          alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
          return;
        }
        
        await updateUser(
          this.user.id,
          this.user,
          () => {
            alert('회원 정보가 수정되었습니다.');
            this.$router.push({ name: 'user-mypage' });
          },
          (error) => {
            console.error(error);
            alert('회원 정보를 수정하는 데 실패했습니다.');
          }
        );
      } catch (error) {
        console.error(error);
        alert('회원 정보를 수정하는 데 실패했습니다.');
      }
    }
  }
};
</script>

<style scoped>
body, html {
  overflow: hidden; /* Hide scrollbar */
}

.edit-user-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 84vh;
  background-color: #f8f9fa;
}

.edit-user-form {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 400px;
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
  font-weight: bold;
  color: #7468B6;
  text-align: left; /* Left align all labels */
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

input[readonly] {
  background-color: #E9ECEF;
}

.submit-button {
  padding: 10px;
  border: none;
  background-color: #7468B6;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

.submit-button:hover {
  background-color: #5a4ea1;
}

.text-success {
  color: green;
  margin-bottom: 20px;
}

.text-danger {
  color: red;
  margin-bottom: 20px;
}

.validation-message {
  text-align: center;
  color: red;
  margin-top: 10px;
}
</style>
