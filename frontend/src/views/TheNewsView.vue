<script setup>
import NewsCategory from "@/components/news/NewsCategory.vue";
import { getNewsList } from "@/api/news.js";
import { ref, onMounted } from "vue";

const officeList = ref([]); // 사무실
const houseList = ref([]); // 주택
const retailList = ref([]); // 상가
const stockList = ref([]); // 주식
const trendList = ref([]); // 부동산트렌드

const loading = ref(true);

const getNews = async () => {
  try {
    officeList.value = await getNewsList("%EC%82%AC%EB%AC%B4%EC%8B%A4");
    houseList.value = await getNewsList("%EC%A3%BC%ED%83%9D");
    retailList.value = await getNewsList("%EC%83%81%EA%B0%80");
    stockList.value = await getNewsList("%EC%A3%BC");
    trendList.value = await getNewsList("%EB%B6%80%EB%8F%99%EC%82%B0%ED%8A%B8%EB%A0%8C%EB%93%9C");

    console.log("officeList.value........................:", officeList.value);
    console.log("houseList.value........................:", houseList.value);
    console.log("retailList.value........................:", retailList.value);
    console.log("stockList.value........................:", stockList.value);
    console.log("trendList.value........................:", trendList.value);
  } catch (error) {
    console.error("Error fetching news:", error);
  } finally {
    loading.value = false;
  }
};

getNews();
</script>

<template>
  <div class="container">
    <header class="header">
      <h1>오늘의 간추린 뉴스</h1>
      <h2>VOL. 1409</h2>
      <p>2024.05.21</p>
    </header>
    <section class="news-section">
      <NewsCategory v-if="officeList.length" title="Office (사무실)" :news-items="officeList" />
      <NewsCategory v-if="houseList.length" title="Housing (주택)" :news-items="houseList" />
      <NewsCategory v-if="retailList.length" title="Retail (상가)" :news-items="retailList" />
      <NewsCategory
        v-if="stockList.length"
        title="Development, Reits, Fund (부동산개발 및 리츠, 펀드)"
        :news-items="stockList"
      />
      <NewsCategory
        v-if="trendList.length"
        title="Real Estate Trend (부동산트렌드)"
        :news-items="trendList"
      />
      <div v-if="loading">Loading...</div>
    </section>
  </div>
</template>

<style>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f4f9;
}

.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  background-color: #00aaff;
  color: white;
  text-align: center;
  padding: 20px 0;
  border-radius: 10px;
}

.header h1 {
  margin: 0;
  font-size: 2em;
}

.header h2 {
  margin: 10px 0;
  font-size: 1.5em;
}

.header p {
  margin: 0;
  font-size: 1em;
}

.news-section {
  margin-top: 20px;
}
</style>
