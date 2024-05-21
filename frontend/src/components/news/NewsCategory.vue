<template>
  <div class="news-category">
    <h3>{{ title }}</h3>
    <hr />
    <div class="news-item" v-for="(news, index) in newsItems" :key="index">
      <h2 v-html="news.title"></h2>
      <p v-html="news.description"></p>
      <span>{{ news.pubDate }}</span>
    </div>
  </div>
</template>

<script>
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
};
</script>

<style scoped>
.news-category h3 {
  margin-bottom: 10px;
}

.news-category hr {
  border: none;
  border-top: 2px solid #ccc;
  margin: 0 0 20px 0;
}

.news-item {
  background-color: #e8e8e8;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.news-item h2 {
  margin: 0 0 10px 0;
  font-size: 1.2em;
}

.news-item p {
  margin: 0 0 10px 0;
}

.news-item span {
  display: block;
  text-align: right;
  color: #888;
  font-size: 0.9em;
}
</style>
