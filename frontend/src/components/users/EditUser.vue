<template>
    <div>
      <h1>회원정보 수정</h1>
      <div v-if="user">
      <p><strong>아이디:</strong> {{ user.id }}</p>
      <p><strong>이름:</strong> {{ user.name }}</p>
      <p><strong>이메일:</strong> {{ user.emailId }}@{{ user.emailDomain }}</p>
      <p><strong>가입일:</strong> {{ user.joinDate }}</p>
      <p><strong>가입일:</strong> {{ user.password }}</p>
      <button @click="goToEditPage">회원정보 수정</button>

    </div>
    <form v-if="user" @submit.prevent="updateUserInfo">
        <div>
        <label for="id">id:</label>
        <input type="text" id="id" v-model="user.id">
      </div>
      <div>
        <label for="name">이름:</label>
        <input type="text" id="name" v-model="user.name">
      </div>
      <div>
        <label for="emailId">이메일 아이디:</label>
        <input type="text" id="emailId" v-model="user.emailId">
      </div>
      <div>
        <label for="emailDomain">이메일 도메인:</label>
        <input type="text" id="emailDomain" v-model="user.emailDomain">
      </div>
      <div>
        <label for="password">비밀번호:</label>
        <input type="text" id="password" v-model="user.password">
      </div>
      <!-- <div>
        <label for="confirmPassword">비밀번호 확인:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword">
      </div> -->
      <button type="submit">저장</button>
    </form>
    </div>
  </template>
  
  <script>
  import { findById2,updateUser } from "@/api/user";
  
  export default {
    name: "EditUser",
    data() {
      return {
        user: {
              name: '',
              id: '',
              password:'',
              emailId: '',
          emailDomain: ''
        }
      };
    },
    created() {
     // const userId = sessionStorage.getItem('id');
     this.fetchUserInfo();

    },
    methods: {
      async fetchUserInfo() {
        try {
            const response = await findById2(
          (response) => {
            console.log("User Info Retrieved");
            console.log(response.data.userInfo);
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
          const response = await updateUser(this.user);
          alert('회원 정보가 수정되었습니다.');
          this.$router.push({ name: 'MyPage' });
        } catch (error) {
          console.error(error);
          alert('회원 정보를 수정하는 데 실패했습니다.');
        }
      }
    }
  };
  </script>
  
  <style scoped>
  /* 필요에 따라 스타일을 추가하세요 */
  </style>
  