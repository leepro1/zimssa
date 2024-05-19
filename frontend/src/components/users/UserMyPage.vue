<template>
  <div>
    <h1>마이페이지</h1>
    <div v-if="user">
      <p><strong>아이디:</strong> {{ user.id }}</p>
      <p><strong>이름:</strong> {{ user.name }}</p>
      <p><strong>이메일:</strong> {{ user.emailId }}@{{ user.emailDomain }}</p>
      <p><strong>가입일:</strong> {{ user.joinDate }}</p>
      <button @click="goToEditPage">회원정보 수정</button>
      <button @click="deleteUser">회원탈퇴</button> <!-- 회원탈퇴 버튼 추가 -->
    </div>
    <div v-else>
      <p>로딩 중...</p>
    </div>
  </div>
</template>

<script>
import { findById2, deleteUser } from "@/api/user"; // deleteUser 메서드 import
import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

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
      const menuStore = useMenuStore();
      const memberStore = useMemberStore();

      const { userLogout } = memberStore;
      const { changeMenuState } = menuStore;

      try {
        await deleteUser(this.user.id); // deleteUser 메서드를 호출하여 사용자 삭제
        sessionStorage.removeItem("accessToken");

        // 로그아웃 함수 호출
        userLogout();
        changeMenuState();

        this.$router.push({ name: 'main' }); // 메인 페이지로 이동
        alert('회원 탈퇴가 완료되었습니다.');
      } catch (error) {
        console.error(error);
        alert('회원 탈퇴를 실패했습니다.');
      }
    },
  },
};
</script>

<style scoped>
/* 필요에 따라 스타일을 추가하세요 */
</style>
