<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerTime: "",
});

if (props.type === "modify") {
  let { articleno } = route.params;
  getModifyArticle(
    articleno,
    ({ data }) => {
      article.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.error(error);
    }
  );
  isUseId.value = true;
} else {
  // 추가하는 경우에는 글 작성자 아이디를 현재 로그인 사용자로 설정
  article.value.userId = memberStore.userInfo.id;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="id" class="form-label">작성자 ID : </label>
      <input
        v-if="type === 'regist'"
        type="text"
        class="form-control"
        :value="memberStore.userInfo.id"
        readonly
      />
      <input
        v-else
        type="text"
        class="form-control"
        v-model="article.userId"
        :disabled="isUseId"
        readonly
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="button" class="detail-btn basic-btn" @click="moveList">목록</button>
      <button type="submit" class="detail-btn highlight-btn" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="detail-btn highlight-btn" v-else>글수정</button>
    </div>
  </form>
</template>

<style scoped>
.detail-btn {
  margin: 0 5px;
  padding: 10px 20px;
  border: none;
  background-color: #ad88c6;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.highlight-btn {
  background-color: #534787;
}

.basic-btn {
  background-color: #7468b6;
}
</style>
