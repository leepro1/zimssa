<script setup>
import { ref, onMounted, watch } from "vue";
import { searchByKeyword, getDetail } from "@/api/house"; // 서버 API 함수들

const map = ref(null);
const markers = ref([]);
const searchQuery = ref("");
const searchResults = ref([]);
const selectedApartment = ref(null);
const selectedApartmentDetails = ref([]);

const center = ref({ lat: 37.5665, lng: 126.978 }); // 초기 지도 중심 (서울시청 기준)
const roadViewContainer = ref(null);

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

const initRoadView = (lat, lng) => {
  if (roadViewContainer.value) {
    const roadview = new kakao.maps.Roadview(roadViewContainer.value);
    const roadviewClient = new kakao.maps.RoadviewClient();
    const position = new kakao.maps.LatLng(lat, lng);

    roadviewClient.getNearestPanoId(position, 50, function (panoId) {
      roadview.setPanoId(panoId, position);
    });
  }
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
  if (selectedApartment.value) {
    const data = await getDetail(
      "maemae_info",
      selectedApartment.value.dongName,
      selectedApartment.value.aptName
    );
    displayMarkers(data);
  }
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
      // handle marker click if needed
    });

    markers.value.push(marker);
  });
};

const handleSearch = async () => {
  if (searchQuery.value.trim() !== "") {
    try {
      searchResults.value = await searchByKeyword("maemae_info", searchQuery.value);
      console.log(searchResults.value);
    } catch (error) {
      console.error("Error fetching search results:", error);
    }
  } else {
    searchResults.value = [];
  }
};

const handleSelectApartment = async (apartment) => {
  selectedApartment.value = apartment;
  try {
    const detail = await getDetail("maemae_info", apartment.dongName, apartment.aptName);
    if (detail && detail.length > 0) {
      selectedApartmentDetails.value = detail;
      const apartmentDetail = detail[0];
      if (map.value) {
        const position = new kakao.maps.LatLng(apartmentDetail.lat, apartmentDetail.lng);
        map.value.setCenter(position);
        map.value.setLevel(3);
        displayMarkers([apartmentDetail]);
        initRoadView(apartmentDetail.lat, apartmentDetail.lng);
      }
    } else {
      console.error("No details found for the selected apartment.");
    }
  } catch (error) {
    console.error("Error fetching apartment details:", error);
  }
};

watch(searchQuery, handleSearch);

onMounted(() => {
  initMap();
});
</script>

<template>
  <div style="position: relative; height: 100vh">
    <div style="position: absolute; top: 10px; left: 10px; z-index: 10">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="아파트 이름 검색"
        style="padding: 8px; font-size: 16px"
      />
      <div
        v-if="searchResults.length"
        style="background: white; border: 1px solid #ccc; max-height: 200px; overflow-y: auto"
      >
        <ul>
          <li
            v-for="apartment in searchResults"
            :key="apartment.aptName"
            @click="handleSelectApartment(apartment)"
            style="padding: 8px; cursor: pointer"
          >
            {{ apartment.dongName }} - {{ apartment.aptName }}
          </li>
        </ul>
      </div>
    </div>
    <div id="map" style="width: 70%; height: 100%; float: left"></div>
    <div
      v-if="selectedApartmentDetails.length"
      style="
        width: 30%;
        height: 100%;
        float: left;
        padding: 16px;
        box-sizing: border-box;
        overflow-y: auto;
      "
    >
      <h2>{{ selectedApartment.aptName }}</h2>
      <p>
        <strong>{{ selectedApartment.dongName }}</strong>
      </p>
      <div ref="roadViewContainer" style="width: 100%; height: 300px; margin-bottom: 16px"></div>
      <ul>
        <li>
          <span><strong>면적</strong></span>
          <span><strong>거래 날짜</strong></span>
          <span><strong>거래 가격</strong></span>
          <span><strong>층</strong></span>
        </li>
        <li
          v-for="detail in selectedApartmentDetails"
          :key="detail.dealDate"
          style="
            display: flex;
            justify-content: space-between;
            padding: 4px 0;
            border-bottom: 1px solid #ccc;
          "
        >
          <span
            ><strong>{{ detail.area }} ㎡</strong></span
          >
          <span
            ><strong>{{ detail.dealDate }}</strong></span
          >
          <span
            ><strong>{{ detail.dealPrice }} 만원</strong></span
          >
          <span
            ><strong>{{ detail.floor }}층</strong></span
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped></style>
