<template>
  <div>
    <h1>마이페이지</h1>
    <div v-if="user">
      <p><strong>아이디:</strong> {{ user.id }}</p>
      <p><strong>이름:</strong> {{ user.name }}</p>
      <p><strong>이메일:</strong> {{ user.emailId }}@{{ user.emailDomain }}</p>
      <p><strong>가입일:</strong> {{ user.joinDate }}</p>
      <button @click="goToEditPage">회원정보 수정</button>

    </div>
    <div v-else>
      <p>로딩 중...</p>
    </div>
  </div>
</template>

<script>
import { findById2 } from "@/api/user";

export default {
  name: "MyPage",
  data() {
    return {
      user: null,
    };
  },
  created() {
    console.log("fetchUserInfo");
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        // const token = sessionStorage.getItem('accessToken');
        // if (!token) {
        //   throw new Error('토큰이 없습니다.');
        // }
       // console.log("Token:", token);

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
    goToEditPage() {
      this.$router.push({ name: 'EditUser' });
    },
  },
};
</script>

<style scoped>
/* 필요에 따라 스타일을 추가하세요 */
</style>
