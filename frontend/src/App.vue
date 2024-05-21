<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import Chatbot from "@/components/chatbot/Chatbot.vue"; // Chatbot 컴포넌트를 가져옵니다

</script>

<template>
  <div>
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
    <Chatbot /> 
  </div>
</template>

<style scoped>
/* 필요한 스타일을 추가하세요 */
</style>
