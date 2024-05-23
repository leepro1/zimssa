<template>
  <div class="news-category">
    <h3>{{ title }}</h3>
    <hr />
    <div
      class="news-item"
      v-for="(news, index) in newsItems"
      :key="index"
      @click="navigateToUrl(news.link)"
      style="cursor: pointer"
    >
      <h2 v-html="news.title"></h2>
      <!-- <p v-html="news.description" class="news-description"></p> -->
      <span class="pub-date">{{ formatRelativeTime(news.pubDate) }}</span>
    </div>
  </div>
</template>

<script>
import { formatDistanceToNow, parse } from "date-fns";

export default {
  name: "NewsCategory",
  props: {
    title: {
      type: String,
      required: true,
    },
    newsItems: {
      type: Array,
      required: true,
      default: () => [],
    },
  },
  methods: {
    formatRelativeTime(pubDate) {
      // 'EEE, dd MMM yyyy HH:mm:ss xxxx' 형식으로 파싱
      const date = parse(pubDate, "EEE, dd MMM yyyy HH:mm:ss xxxx", new Date());
      return formatDistanceToNow(date, { addSuffix: true });
    },
    navigateToUrl(url) {
      window.open(url, "_blank");
    },
  },
};
</script>

<style scoped>
.news-category {
  height: 100%; /* Full height for each category */
}

.news-category h3 {
  margin-bottom: 10px;
}

.news-category hr {
  border: none;
  border-top: 2px solid #ccc;
  margin: 0 0 10px 0;
}

.news-item {
  position: relative; /* Relative positioning for absolute child elements */
  background-color: #e8e8e8;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  height: 90px; /* Adjusting height to fit 3 items within the category */
  overflow: hidden;
}

.news-item h2 {
  margin: 0 0 5px 0;
  font-size: 1.1em;
}

.news-description {
  font-size: 0.9em; /* 폰트 크기 줄임 */
  margin: 0 0 5px 0;
}

.news-item span {
  position: absolute; /* Absolute positioning */
  bottom: 10px; /* Offset from the bottom */
  right: 10px; /* Offset from the right */
  color: #888;
  font-size: 0.9em;
}

.pub-date {
  /* Additional styles for pub-date if necessary */
}
</style>
