<template>
  <div>
    <h1>마이페이지</h1>
    <div v-if="user">
      <p><strong>아이디:</strong> {{ user.id }}</p>
      <p><strong>이름:</strong> {{ user.name }}</p>
      <p><strong>이메일:</strong> {{ user.emailId }}@{{ user.emailDomain }}</p>
      <p><strong>가입일:</strong> {{ user.joinDate }}</p>
      <button @click="goToEditPage">회원정보 수정</button>
      <button @click="deleteUser">회원탈퇴</button> <!-- Added button for deletion -->
    </div>
    <div v-else>
      <p>로딩 중...</p>
    </div>
  </div>
</template>



<script>
import { findById2, updateUser, deleteUser } from "@/api/user"; // Import the new deleteUser method

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
    async deleteUser() {
      try {
        await deleteUser(this.user.id); // Call the deleteUser method with the user ID
        sessionStorage.removeItem("accessToken");
        this.$router.push({ name: 'main' }); // 메인 페이지로 이동
        alert('회원 탈퇴가 완료되었습니다.');
        // Redirect or perform any other action after deletion
      } catch (error) {
     //   console.error(error);
        alert('회원 탈퇴를 실패했습니다.');
      }
    },
  },
};
</script>

<style scoped>
/* 필요에 따라 스타일을 추가하세요 */
</style>
