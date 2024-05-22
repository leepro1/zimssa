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
    <div class="content">
      <div class="left">
        <div class="introduce">
          <h1>𝔃𝓲𝓶𝓼𝓼𝓪</h1>
          <h3>짐 싸, 좋은 집으로 이사 가자</h3>
          <h5>
            사법권은 법관으로 구성된 법원에 속한다. 지방의회의 조직·권한·의원선거와 지방자치단체의
            장의 선임방법 기타 지방자치단체의 조직과 운영에 관한 사항은 법률로 정한다. 군사재판을
            관할하기 위하여 특별법원으로서 군사법원을 둘 수 있다. 모든 국민은 건강하고 쾌적한
            환경에서 생활할 권리를 가지며, 국가와 국민은 환경보전을 위하여 노력하여야 한다.
          </h5>
        </div>
        <div class="linkBtns">
          <button @click="goToMaemae">매매 매물 보러가기 ></button>
          <button @click="goToJeonse">전월세 매물 보러가기 ></button>
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
  flex-direction: column;
  background-color: rgb(255, 255, 255);
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
  background-color: rgb(255, 255, 255);
  width: 25vw;
  height: 70vh;
  padding: 15px;
}

.news-board {
  display: flex;
  flex-direction: column;
  background-color: rgb(112, 132, 72);
  width: 25vw;
  height: 70vh;
}

.news {
  display: flex;
  background-color: rgb(255, 255, 255);
  width: 25vw;
  height: 35vh;
}

.board {
  display: flex;
  background-color: rgb(255, 255, 255);
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

/* 소개 글*/
.introduce {
  display: flex;
  flex-direction: column;
  background-color: rgb(255, 255, 255);
  margin-top: 40px;
  margin-left: 40px;
  width: 50vw;
  height: 50vh;
}

.introduce h1 {
  font-size: 60px;
  color: #ad88c6; /* 날짜 색상 */
}

.introduce h3 {
  font-size: 30px;
  color: black;
}

.linkBtns {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  background-color: white;
  width: 50vw;
  height: 20vh;
}

.linkBtns button {
  border-color: 7468B6;
  color: #fff;
  background-image: linear-gradient(45deg, #ad88c6 50%, transparent 50%);
  background-position: 100%;
  background-size: 400%;
  transition: background 300ms ease-in-out;
  border-radius: 12px;
  width: 200px;
  height: 70px;
  color: black;
  margin: 0 20px; /* 좌우 간격을 균등하게 유지 */
}

.linkBtns button:hover {
  background-position: 0;
}
</style>
