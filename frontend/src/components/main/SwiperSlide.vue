<script>
// import Swiper core and required modules
import { Navigation, Pagination, Autoplay } from "swiper/modules";

// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue";

// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

export default {
  components: {
    Swiper,
    SwiperSlide,
  },
  setup() {
    const onSwiper = (swiper) => {
      console.log(swiper);
    };
    const onSlideChange = () => {
      console.log("slide change");
    };
    return {
      onSwiper,
      onSlideChange,
      modules: [Navigation, Pagination, Autoplay],
    };
  },
};
</script>

<template>
  <swiper
    :modules="modules"
    :slides-per-view="1"
    :space-between="0"
    navigation
    :pagination="{ clickable: true }"
    :autoplay="{ delay: 4000 }"
    @swiper="onSwiper"
    @slideChange="onSlideChange"
    class="swiper-container swiper"
  >
    <swiper-slide>
      <div class="slide-content">
        <div class="slide-text">
          <h2>서울의 집 값을 한눈에!</h2>
          <p>실거래가 조회하기</p>
        </div>
      </div>
    </swiper-slide>
    <swiper-slide>
      <div class="slide-content">
        <div class="slide-text">
          <h2>내 한도는 어디까지?</h2>
          <p>전세 대출 알아보기</p>
        </div>
      </div>
    </swiper-slide>
    <swiper-slide>
      <div class="slide-content">
        <div class="slide-text">
          <h2>사회적 약자를 위한 지원</h2>
          <p>복지시설 정보 제공</p>
        </div>
      </div>
    </swiper-slide>
    <swiper-slide>
      <div class="slide-content">
        <div class="slide-text">
          <h2>궁금증을 챗봇에게 바로 물어봐!</h2>
          <p>ChatBot</p>
        </div>
      </div>
    </swiper-slide>
  </swiper>
</template>
<style>
@import "swiper/css";
@import "swiper/css/navigation";
@import "swiper/css/pagination";

.swiper {
  border-bottom: 1px solid rgb(165, 165, 165);
  background-image: url("src/assets/main_apt.jpg"); /* 배경 이미지 추가 */
  background-size: cover; /* 배경 이미지가 컨테이너를 넘지 않도록 설정 */
  background-position: center; /* 배경 이미지가 가운데에 위치하도록 설정 */
  background-repeat: no-repeat; /* 배경 이미지 반복 방지 */
}
.swiper-container {
  width: 100%;
  height: 20vh; /* 전체 화면 높이 */
}

.swiper-slide {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.slide-content {
  width: 100%;
  height: 100%;
  position: relative;
}

.slide-text {
  position: absolute;
  z-index: 2;
  color: black;
  text-align: center;
  width: 100%;
  top: 50%;
  transform: translateY(-50%);
}

h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

p {
  font-size: 18px;
}
.swiper-button-next,
.swiper-button-prev {
  color: #ad88c6;
}

.swiper-pagination-bullet {
  background-color: #e1afd1;
}

.swiper-pagination-bullet-active {
  background-color: #ad88c6;
}
</style>
