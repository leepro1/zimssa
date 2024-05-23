<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="cont">
    <div class="inner">
      <div class="title">
        <h2>글수정</h2>
      </div>
      <div class="text-start">
        <BoardFormItem type="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.cont {
  display: flex;
  justify-content: center;
}
.cont .inner {
  width: 50vw;
}

.title {
  height: 10vh;
  align-content: center;
  margin-top: 50px;
  margin-bottom: 15px;
  padding-top: 3px;
  padding-bottom: 3px;
  background-color: #7468b6;
  text-align: center;
  color: white;
  border-radius: 10px; /* 모서리를 둥글게 */
}
</style>
