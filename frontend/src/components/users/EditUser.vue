<template>
  <div>
    <form v-if="user" @submit.prevent="updateUserInfo">
      <div>
        <label for="id">id:</label>
        <input type="text" id="id" v-model="user.id" readonly>
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
        <input type="password" id="password" v-model="user.password">
      </div>
      <div>
        <label for="confirmPassword">비밀번호 확인:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword">
      </div>
      <button type="submit">수정</button>
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
/* 필요에 따라 스타일을 추가하세요 */
</style>
