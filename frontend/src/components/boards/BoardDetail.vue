<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { findById2 } from "@/api/user";

const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();

const { articleno } = route.params;

const article = ref({});
const isAdmin = ref(false);
const isLoggedIn = ref(false);

const fetchUserId = async () => {
  try {
    await findById2(
      (response) => {
        console.log("User Info Retrieved");
        console.log("response.data.userInfo.id............", response.data.userInfo.id);
        console.log("response.data.userInfo.role............", response.data.userInfo.role);
        isLoggedIn.value = true; // 로그인 상태로 설정
        if (response.data.userInfo.role === "admin") isAdmin.value = true;
      },
      (error) => {}
    );
  } catch (error) {}
};

onMounted(() => {
  fetchUserId();
  getArticle();
});

const getArticle = () => {
  detailArticle(
    articleno,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  deleteArticle(
    articleno,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.error(error);
    }
  );
}
</script>

<template>
  <div class="cont">
    <div class="inner">
      <div class="title">
        <h2>게시글</h2>
      </div>
      <div class="text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{ article.userName }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime }} 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="divider mb-3"></div>
          <div class="text-secondary article-content">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="move-list-btn" @click="moveList">글목록</button>
            <template v-if="isLoggedIn && isAdmin">
              <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
                글수정
              </button>
              <button
                type="button"
                class="btn btn-outline-danger mb-3 ms-1"
                @click="onDeleteArticle"
              >
                글삭제
              </button>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cont {
  display: flex;
  justify-content: center;
}
.cont .inner {
  width: 50vw;
}

.title {
  height: 10vh;
  align-content: center;
  margin-top: 50px;
  margin-bottom: 15px;
  padding-top: 3px;
  padding-bottom: 3px;
  background-color: #7468b6;
  text-align: center;
  color: white;
  border-radius: 10px; /* 모서리를 둥글게 */
}

.article-content {
  padding: 20px;
  background-color: #f9f9f9; /* 배경색 변경 */
  border-radius: 10px; /* 모서리를 둥글게 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
}

.divider {
  width: 100%;
  height: 1px;
  background-color: #ccc; /* 구분선 색상 */
  margin: 10px 0;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.btn {
  margin-left: 10px;
}

button.btn-outline-primary,
button.btn-outline-success,
button.btn-outline-danger {
  border-radius: 10px; /* 모서리를 둥글게 */
}

.move-list-btn {
  padding: 10px 20px;
  border: none;
  background-color: #ad88c6;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}
</style>
