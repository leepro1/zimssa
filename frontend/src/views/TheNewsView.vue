<script setup>
import NewsCategory from "@/components/news/NewsCategory.vue";
import { getNewsList } from "@/api/news.js";
import { ref, onMounted } from "vue";

const officeList = ref([]); // 사무실
const houseList = ref([]); // 주택
const retailList = ref([]); // 상가
const stockList = ref([]); // 주식

const loading = ref(true);

// 현재 날짜를 가져오기
const currentDate = ref(
  new Date().toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  })
);

const getNews = async () => {
  try {
    officeList.value = (await getNewsList("%EC%82%AC%EB%AC%B4%EC%8B%A4")).slice(0, 3);
    houseList.value = (await getNewsList("%EC%A3%BC%ED%83%9D")).slice(0, 3);
    retailList.value = (await getNewsList("%EC%83%81%EA%B0%80")).slice(0, 3);
    stockList.value = (await getNewsList("%EC%A3%BC")).slice(0, 3);

    console.log("officeList.value........................:", officeList.value);
    console.log("houseList.value........................:", houseList.value);
    console.log("retailList.value........................:", retailList.value);
    console.log("stockList.value........................:", stockList.value);
  } catch (error) {
    console.error("Error fetching news:", error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  getNews();
});
</script>

<template>
  <div class="container">
    <header class="header">
      <h1>오늘의 간추린 뉴스</h1>
      <p>{{ currentDate }}</p>
    </header>
    <section class="news-section" v-if="!loading">
      <div class="news-grid">
        <NewsCategory v-if="officeList.length" title="Office (사무실)" :news-items="officeList" />
        <NewsCategory v-if="houseList.length" title="Housing (주택)" :news-items="houseList" />
        <NewsCategory v-if="retailList.length" title="Retail (상가)" :news-items="retailList" />
        <NewsCategory v-if="stockList.length" title="Stock (주식)" :news-items="stockList" />
      </div>
    </section>
    <div v-if="loading" class="loading">Loading...</div>
  </div>
</template>

<style>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f4f9;
  overflow: hidden;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  background-color: #7468b6;
  color: white;
  text-align: center;
  padding: 10px 0; /* 패딩을 줄여 높이 감소 */
  border-radius: 10px;
}

.header h1 {
  margin: 0;
  font-size: 1.8em; /* 폰트 크기 감소 */
}

.header p {
  margin: 0;
  font-size: 0.9em; /* 폰트 크기 감소 */
}

.news-section {
  margin-top: 10px; /* 마진 감소 */
}

.news-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(2, 1fr);
  gap: 10px; /* 갭 감소 */
  height: calc(100vh - 150px); /* Adjusting to fit within viewport minus header */
}

.loading {
  text-align: center;
  font-size: 1.5em;
  margin-top: 20px;
}
</style>
