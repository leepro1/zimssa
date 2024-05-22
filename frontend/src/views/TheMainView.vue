<script setup>
import SwiperSlide from "@/components/main/SwiperSlide.vue";
import { getNewsList } from "@/api/news.js";
import { ref, onMounted } from "vue";
import { formatDistanceToNow, parse } from "date-fns";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";

const houseList = ref([]); // 주택
const isNewsLoading = ref(true);
const isBoardLoading = ref(true);
const router = useRouter();

// 현재 날짜를 가져오기
const currentDate = ref(
  new Date().toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  })
);
const getHouseNews = async () => {
  try {
    houseList.value = (await getNewsList("%EC%A3%BC%ED%83%9D")).slice(0, 3);
    console.log("houseList.value........................:", houseList.value);
  } catch (error) {
    console.error("Error fetching news:", error);
  } finally {
    isNewsLoading.value = false;
  }
};

const articles = ref([]);

// 게시글 목록 가져오기
const getArticleList = () => {
  console.log("getArticleList called................................");

  const param = ref({
    pgno: 1, // 0번째 부터
    spp: 2, // 2개 출력
    key: "",
    word: "",
  });

  listArticle(
    param.value,
    ({ data }) => {
      articles.value = data.articles;
      console.log("articles.value...................", articles.value);
      // currentPage.value = data.currentPage;
      // totalPage.value = data.totalPageCount;
      isBoardLoading.value = false;
    },
    (error) => {
      console.error(error);
    }
  );
};

function formatRelativeTime(pubDate) {
  // 'EEE, dd MMM yyyy HH:mm:ss xxxx' 형식으로 파싱
  const date = parse(pubDate, "EEE, dd MMM yyyy HH:mm:ss xxxx", new Date());
  return formatDistanceToNow(date, { addSuffix: true });
}

onMounted(() => {
  console.log("onMounted............");
  getHouseNews();
  getArticleList();
});

const goToNews = () => {
  router.push({ name: "news" });
};

const goToBoard = () => {
  router.push({ name: "board" });
};
</script>

<template>
  <div>
    <swiper-slide></swiper-slide>
    <div class="content">
      <div class="left">fasafdga</div>
      <div class="right">
        <div class="product"></div>
        <div class="news-board">
          <div class="news">
            <div class="rounded-shadow-box" @click="goToNews">
              <h4>오늘의 뉴스</h4>
              <hr />
              <div v-if="isNewsLoading" class="loading">Loading...</div>
              <ul v-if="!isNewsLoading" class="announcement-list">
                <li
                  v-for="(item, index) in houseList.slice(0, 2)"
                  :key="index"
                  class="announcement-item"
                >
                  <span v-html="item.title" class="announcement-text"></span>
                  <span class="announcement-date">{{ formatRelativeTime(item.pubDate) }}</span>
                </li>
              </ul>
            </div>
          </div>

          <div class="board">
            <div class="rounded-shadow-box" @click="goToBoard">
              <h4>공지사항</h4>
              <hr />
              <div v-if="isBoardLoading" class="loading">Loading...</div>
              <ul v-if="!isBoardLoading" class="announcement-list">
                <li
                  v-for="(item, index) in articles.slice(0, 2)"
                  :key="index"
                  class="announcement-item"
                >
                  <span v-html="item.subject" class="announcement-text"></span>
                  <span class="announcement-date">{{ item.registerTime }}</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.content {
  display: flex;
  background-color: rgb(185, 189, 164);
  width: 100%;
  height: 70vh;
}

.left {
  display: flex;
  background-color: rgb(72, 200, 113);
  width: 50vw;
  height: 70vh;
}

.right {
  display: flex;
  background-color: rgb(3, 165, 206);
  width: 50vw;
  height: 70vh;
}

.product {
  display: flex;
  background-color: rgb(255, 116, 46);
  width: 25vw;
  height: 70vh;
}

.news-board {
  display: flex;
  flex-direction: column;
  background-color: rgb(132, 72, 79);
  width: 25vw;
  height: 70vh;
}

.news {
  display: flex;
  background-color: rgb(63, 201, 187);
  width: 25vw;
  height: 35vh;
}

.board {
  display: flex;
  background-color: rgb(56, 73, 71);
  width: 25vw;
  height: 35vh;
}

.rounded-shadow-box {
  background-color: #f0f0f0; /* 원하는 배경색으로 변경 */
  border-radius: 10px; /* 모서리를 둥글게 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  padding: 20px; /* 내부 여백 */
  width: 300px; /* 원하는 너비로 설정 */
  margin: 20px auto; /* 가운데 정렬 */
  text-align: center; /* 텍스트 가운데 정렬 */
}

/* 뉴스 정보 */

.announcement-list {
  list-style: none; /* 기본 리스트 스타일 제거 */
  padding: 0;
  margin: 0;
}

.announcement-item {
  padding: 5px 0;
  border-bottom: 1px solid #ccc; /* 항목 간 구분선 */
}

.announcement-item:last-child {
  border-bottom: none; /* 마지막 항목은 구분선 제거 */
}

.announcement-text {
  display: block;
  font-size: 16px;
  text-align: left;
}

.announcement-date {
  display: block;
  font-size: 14px;
  color: #888; /* 날짜 색상 */
  margin-top: 3px;
  text-align: right;
}
</style>
