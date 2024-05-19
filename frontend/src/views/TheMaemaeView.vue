<script setup>
import { ref, onMounted, watch } from "vue";
import { debounce } from "lodash";
import { searchKeyword, getDongData } from "@/api/maemae"; // 서버 API 함수들

const map = ref(null);
const markers = ref([]);
const searchQuery = ref("");
const searchResults = ref([]);
const selectedDong = ref(null);

const center = ref({ lat: 37.5665, lng: 126.978 }); // 초기 지도 중심 (서울시청 기준)

// 환경 변수에서 카카오 맵 API 키를 가져옵니다
const KAKAO_MAP_SERVICE_KEY = import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY;

const loadKakaoMapScript = () => {
  return new Promise((resolve, reject) => {
    if (window.kakao && window.kakao.maps) {
      resolve();
      return;
    }
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${KAKAO_MAP_SERVICE_KEY}&libraries=services`;
    script.onload = () => resolve();
    script.onerror = () => reject(new Error("Kakao Map script loading error"));
    document.head.appendChild(script);
  });
};

const initMap = () => {
  const mapContainer = document.getElementById("map");
  const mapOption = {
    center: new kakao.maps.LatLng(center.value.lat, center.value.lng),
    level: 3,
  };
  map.value = new kakao.maps.Map(mapContainer, mapOption);

  kakao.maps.event.addListener(map.value, "tilesloaded", () => {
    addEventListeners();
  });
};

const addEventListeners = () => {
  kakao.maps.event.addListener(map.value, "dragend", () => {
    const mapCenter = map.value.getCenter();
    center.value.lat = mapCenter.getLat();
    center.value.lng = mapCenter.getLng();
    updateMarkers();
  });

  kakao.maps.event.addListener(map.value, "zoom_changed", () => {
    updateMarkers();
  });
};

const updateMarkers = async () => {
  clearMarkers();
  // 가짜 데이터로 대체
  const markersData = [{ lat: 37.5665, lng: 126.978, name: "Sample Dong" }];
  displayMarkers(markersData);
};

const clearMarkers = () => {
  markers.value.forEach((marker) => marker.setMap(null));
  markers.value = [];
};

const displayMarkers = (markersData) => {
  markersData.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    const marker = new kakao.maps.Marker({
      position,
      map: map.value,
    });

    kakao.maps.event.addListener(marker, "click", () => {
      handleSelectDong(data);
    });

    markers.value.push(marker);
  });
};

const handleSearch = debounce(async () => {
  if (searchQuery.value.trim() !== "") {
    try {
      searchResults.value = await searchKeyword(searchQuery.value);
    } catch (error) {
      console.error("Error fetching search results:", error);
    }
  } else {
    searchResults.value = [];
  }
}, 300);

const handleSelectDong = async (dong) => {
  try {
    const data = await getDongData(dong.name);
    selectedDong.value = data;

    if (map.value && data.latitude && data.longitude) {
      const position = new kakao.maps.LatLng(data.latitude, data.longitude);
      map.value.setCenter(position);
      map.value.setLevel(3);

      clearMarkers();
      updateMarkers();
    }
  } catch (error) {
    console.error("Error fetching dong data:", error);
  }
};

watch(searchQuery, handleSearch);

onMounted(async () => {
  try {
    await loadKakaoMapScript();
    initMap();
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div style="position: relative; height: 100vh">
    <div style="position: absolute; top: 10px; left: 10px; z-index: 10">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="동 이름 검색"
        style="padding: 8px; font-size: 16px"
      />
      <div
        v-if="searchResults.length"
        style="background: white; border: 1px solid #ccc; max-height: 200px; overflow-y: auto"
      >
        <ul>
          <li
            v-for="dong in searchResults"
            :key="dong.id"
            @click="handleSelectDong(dong)"
            style="padding: 8px; cursor: pointer"
          >
            {{ dong.name }}
          </li>
        </ul>
      </div>
    </div>
    <div id="map" style="width: 100%; height: 100%"></div>
  </div>
</template>

<style scoped></style>
