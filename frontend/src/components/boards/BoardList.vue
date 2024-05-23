<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";

import BoardListItem from "@/components/boards/item/BoardListItem.vue";
import { useMemberStore } from "@/stores/member";
import { findById2 } from "@/api/user";

const isAdmin = ref(false);
const isLoggedIn = ref(false);

// 현재 사용자의 관리자 유무를 변수에 저장
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
      (error) => {
        // 에러가 발생하는 것의 의미 - 아직 로그인 하지 않음
        // console.error(error);
        // alert("사용자 정보를 가져오는 데 실패했습니다.");
      }
    );
  } catch (error) {
    // console.error(error);
    // alert("사용자 정보를 가져오는 데 실패했습니다.");
  }
};

// 사용자 정보 가져오기
onMounted(() => {
  fetchUserId();
  getArticleList();
});

const router = useRouter();

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

// 게시글 목록 가져오기
const getArticleList = () => {
  console.log("getArticle called................................");

  // 글 제목으로만 검색
  param.value.key = "subject";

  listArticle(
    param.value,
    ({ data }) => {
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.error(error);
    }
  );
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};
</script>

<template>
  <div class="cont">
    <div class="inner">
      <div class="title-container title">
        <h2>공지사항</h2>
      </div>
      <div>
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button
              v-if="isLoggedIn && isAdmin"
              type="button"
              class="detail-btn"
              @click="moveWrite"
            >
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex" @submit.prevent="getArticleList">
              <div class="input-group input-group-sm ms-1">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="제목 검색"
                />
                <button class="detail-btn" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="(article, index) in articles"
              :key="article.articleNo"
              :article="article"
              :number="articles.length - index"
            ></BoardListItem>
          </tbody>
        </table>
      </div>
      <VPageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></VPageNavigation>
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
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  background-color: #7468b6;
  text-align: center;
  color: white;
  border-radius: 10px; /* 모서리를 둥글게 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
}

.detail-btn {
  margin: 0 5px;
  padding: 10px 20px;
  border: none;
  background-color: #ad88c6;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}
</style>
