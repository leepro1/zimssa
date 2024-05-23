<script setup>
import { getNewsList } from "@/api/news.js";
import { ref, onMounted } from "vue";
import { formatDistanceToNow, parse } from "date-fns";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";
import SwiperSlide from "@/components/main/SwiperSlide.vue";
import ResultPage from "@/components/Junseproduct/ResultPage.vue";

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

const goToMaemae = () => {
  router.push({ name: "maemae" });
};

const goToJeonse = () => {
  router.push({ name: "jeonse" });
};
</script>

<template>
  <div>
    <swiper-slide></swiper-slide>
    <div class="outer">
      <div class="content">
        <div class="left rounded-shadow-box-content">
          <div class="inner">
            <div class="introduce">
              <h1 style="margin-bottom: 20px">𝔃𝓲𝓶𝓼𝓼𝓪</h1>
              <p>"짐 싸! 좋은 집으로 이사 가자"</p>
              <h5>나에게 꼭 맞는 전세 보증 상품을 알아보자!</h5>
              <h5>요즘 부동산 가격은? 뉴스를 통해!</h5>
            </div>
            <div class="linkBtns">
              <button @click="goToMaemae">매매 매물 보러가기 ></button>
              <button @click="goToJeonse">전월세 매물 보러가기 ></button>
            </div>
          </div>
        </div>
        <div class="right">
          <div class="product">
            <result-page></result-page>
          </div>
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
  </div>
</template>

<style scoped>
.outer {
  display: flex;
  justify-content: center;
  width: 100%; /* 전체 너비로 설정 */
  height: 100%; /* 전체 높이로 설정 */
  position: relative; /* 내부 요소를 위치시키기 위해 상대 위치 설정 */
}

.content-linkbtns {
  align-items: center;
  align-content: center;
}

.content {
  display: flex;
  width: 80vw;
  height: 70vh;
  padding-bottom: 0px;
}

.left {
  display: flex;
  flex-direction: column;
  width: 50%;
  justify-content: center;
  align-items: center;
}

.left .inner {
  display: flex;
  flex-direction: column;
  border-radius: 10px; /* 모서리를 둥글게 */
}

.right {
  display: flex;
  width: 50%;
  height: 70vh;
}

.product {
  display: flex;
  padding-top: 20px;
  padding-bottom: 20px;
  width: 100%;
  height: 70vh;
  padding: 15px;
}

.news-board {
  display: flex;
  padding-left: 10px;
  padding-right: 20px;
  padding-top: 20px;
  padding-bottom: 20px;
  flex-direction: column;
  width: 50%;
  height: 70vh;
}

.news {
  display: flex;
  width: 100%;
  height: 50%;
}

.board {
  display: flex;
  width: 100%;
  height: 50%;
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

.rounded-shadow-box-content {
  background-color: white; /* 원하는 배경색으로 변경 */
  border-radius: 10px; /* 모서리를 둥글게 */
  width: 50%; /* 원하는 너비로 설정 */
  margin: 20px auto; /* 가운데 정렬 */
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

/* 소개 글*/
.introduce {
  align-items: center;
  align-content: center;
  padding: 20px;
  width: 100%;
  height: 100%;
}

.introduce h1 {
  font-size: 60px;
  color: #ad88c6; /* 날짜 색상 */
}

.introduce p {
  font-size: 30px;
  color: black;
  word-wrap: break-word;
}

.linkBtns {
  display: flex;
  flex-direction: row;
  padding: 20px 0;
}

.linkBtns button {
  background-color: #ffffff; /* 흰색 배경 */
  border: 2px solid #ad88c6; /* 테두리를 #ad88c6 색상으로 설정 */
  color: #ad88c6; /* 텍스트 색상을 #ad88c6으로 설정 */
  border-radius: 12px;
  width: 250px;
  height: 70px;
  margin: 0 20px; /* 좌우 간격을 균등하게 유지 */
  transition: background 300ms ease-in-out, color 300ms ease-in-out;
}

.linkBtns button:hover {
  background-color: #ad88c6; /* 호버 시 배경색을 #ad88c6로 변경 */
  color: #ffffff; /* 호버 시 텍스트 색상을 흰색으로 변경 */
}
</style>
