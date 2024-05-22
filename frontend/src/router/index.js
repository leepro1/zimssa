import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "../views/TheMainView.vue";
// import TheBoardView from "../views/TheBoardView.vue";

import { storeToRefs } from "pinia";
import TheJunseProductView from "@/views/TheJunseProductView.vue"; // 새로운 뷰 추가

import { useMemberStore } from "@/stores/member";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");

  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    
    {
      path: "/maemae",
      name: "maemae",
      component: () => import("@/views/TheMaemaeView.vue"),
    },
    {
      path: "/jeonse",
      name: "jeonse",
      component: () => import("@/views/TheJeonseView.vue"),
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/users/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserRegister.vue"),
        },
        {
          path: "mypage",
          name: "user-mypage",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/users/UserMyPage.vue"),
        },
        {
          path: "mypage/edit-user", // Add this route
          name: "EditUser",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/users/EditUser.vue"),
        },
      ],
    },
    {
      path: '/result',
      name: 'ResultPage',
      component:  () => import("@/components/Junseproduct/ResultPage.vue"),
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/boards/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/boards/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/boards/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/boards/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/news",
      name: "news",
      component: () => import("@/views/TheNewsView.vue"),
    },
    {
      path: "/junseproduct",
      name: "junseproduct",
      component: TheJunseProductView,
    },
  ],
});

export default router;
