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

<style scoped></style>
