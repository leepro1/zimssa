<template>
  <div class="container">
    <div class="mypage-box">
      <h2 class="title">마이페이지</h2>
      <form v-if="user" class="user-info">
        <div class="form-group">
          <label for="id">아이디:</label>
          <input type="text" id="id" :value="user.id" readonly>
        </div>
        <div class="form-group">
          <label for="name">이름:</label>
          <input type="text" id="name" :value="user.name" readonly>
        </div>
        <div class="form-group">
          <label for="email">이메일:</label>
          <input type="text" id="email" :value="user.emailId + '@' + user.emailDomain" readonly>
        </div>
        <div class="form-group">
          <label for="joinDate">가입일:</label>
          <input type="text" id="joinDate" :value="user.joinDate" readonly>
        </div>
        <div class="buttons">
          <button @click.prevent="goToEditPage" class="btn btn-primary">회원정보 수정</button>
          <button @click.prevent="deleteUser" class="btn btn-danger">회원탈퇴</button>
        </div>
      </form>
      <div v-else>
        <p>로딩 중...</p>
      </div>
    </div>
  </div>
</template>

<script>
import { findById2, deleteUser } from "@/api/user";
import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";

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
        await deleteUser(this.user.id);
        sessionStorage.removeItem("accessToken");

        userLogout();
        changeMenuState();

        this.$router.push({ name: 'main' });
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
body, html {
  overflow: hidden; /* Hide scrollbar */
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f8f9fa;
}

.mypage-box {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.title {
  margin-bottom: 20px;
  text-align: center;
  color: #7468B6;
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
  font-weight: bold;
  color: #7468B6;
  text-align: left;
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

.buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

.btn-primary {
  background-color: #7468B6;
  color: #fff;
}

.btn-danger {
  background-color: #E74C3C;
  color: #fff;
}

.btn-primary:hover {
  background-color: #5a4ea1;
}

.btn-danger:hover {
  background-color: #c0392b;
}
</style>
