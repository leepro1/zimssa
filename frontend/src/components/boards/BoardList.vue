<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";

import BoardListItem from "@/components/boards/item/BoardListItem.vue";
import { useMemberStore } from "@/stores/member";
import { findById2 } from "@/api/user";

const isAdmin = ref(false);

// 현재 사용자의 관리자 유무를 변수에 저장
const fetchUserId = async () => {
  try {
    await findById2(
      (response) => {
        console.log("junse User Info Retrieved");
        console.log("response.data.userInfo.id............", response.data.userInfo.id);
        console.log("response.data.userInfo.role............", response.data.userInfo.role);
        if (response.data.userInfo.role === "admin") isAdmin.value = true;
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

onBeforeMount(() => {
  console.log("onMounted............................");
  fetchUserId();
});

const router = useRouter();
const memberStore = useMemberStore();

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

onMounted(() => {
  getArticleList();
});

// const changeKey = (val) => {
//   param.value.key = val;
// };

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
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글목록</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button
              v-if="isAdmin"
              type="button"
              class="btn btn-outline-primary btn-sm"
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
                  placeholder="검색어..."
                />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
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
              v-for="article in articles"
              :key="article.articleNo"
              :article="article"
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

<style scoped></style>
