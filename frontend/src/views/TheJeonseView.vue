<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { debounce } from "lodash";
import { searchByKeyword, getDetail } from "@/api/house";
import { getMapMarker } from "@/api/mapMarker";
import { getJjim, postJjim, deleteJjim } from "@/api/jjim";
import { findById2 } from "@/api/user";
import apartmentMarkerImage from "@/assets/apartment.png";
import subwayMarkerImage from "@/assets/facilities/subway.png";
import schoolMarkerImage from "@/assets/facilities/school.png";
import hospitalMarkerImage from "@/assets/facilities/hospital.png";
import seniorMarkerImage from "@/assets/facilities/senior.png";
import childMarkerImage from "@/assets/facilities/child.png";
import impairmentMarkerImage from "@/assets/facilities/impairment.png";
import homelessMarkerImage from "@/assets/facilities/homeless.png";
import "bootstrap-icons/font/bootstrap-icons.css";

const map = ref(null);
const markers = ref([]);
const searchQuery = ref("");
const searchResults = ref([]);
const selectedApartment = ref(null);
const selectedApartmentDetails = ref([]);
const showSearchResults = ref(true);
const selectedArea = ref("전체");
const userId = ref("");
const showJjimList = ref(false);

const fetchUserId = async () => {
  try {
    await findById2(
      (response) => {
        userId.value = response.data.userInfo.id;
      },
      (error) => {}
    );
    fetchJjimList();
  } catch (error) {}
};

// 찜 목록
const jjimList = ref([]);

// 모달 상태와 선택된 아파트 정보
const isModalOpen = ref(false);
const selectedDetail = ref(null);

// 편의시설 리스트
const subwayList = ref([]);
const schoolList = ref([]);
const hospitalList = ref([]);
const seniorList = ref([]);
const childList = ref([]);
const impairmentList = ref([]);
const homelessList = ref([]);

// 편의시설 마커 리스트
const subwayMarkers = ref([]);
const schoolMarkers = ref([]);
const hospitalMarkers = ref([]);
const seniorMarkers = ref([]);
const childMarkers = ref([]);
const impairmentMarkers = ref([]);
const homelessMarkers = ref([]);

// 전세/월세 토글 상태
const dealType = ref("전세");

// 지도 중심
const center = ref({ lat: 37.5665, lng: 126.978 });
const roadViewContainer = ref(null);

const getMapMarkerFilter = async () => {
  subwayList.value = await getMapMarker("subway");
  schoolList.value = await getMapMarker("school");
  hospitalList.value = await getMapMarker("hospital");
  seniorList.value = await getMapMarker("senior");
  childList.value = await getMapMarker("child");
  impairmentList.value = await getMapMarker("impairment");
  homelessList.value = await getMapMarker("homeless");
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
    updateMarkers();
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

const updateMarkers = () => {
  const level = map.value.getLevel();

  if (level > 5) {
    markers.value.forEach((marker) => marker.setMap(null));
    subwayMarkers.value.forEach((marker) => marker.setMap(null));
    schoolMarkers.value.forEach((marker) => marker.setMap(null));
    hospitalMarkers.value.forEach((marker) => marker.setMap(null));
    seniorMarkers.value.forEach((marker) => marker.setMap(null));
    childMarkers.value.forEach((marker) => marker.setMap(null));
    impairmentMarkers.value.forEach((marker) => marker.setMap(null));
    homelessMarkers.value.forEach((marker) => marker.setMap(null));
    return;
  }

  markers.value.forEach((marker) => {
    marker.setMap(null);
  });
  markers.value = [];

  displayMarkers(selectedApartmentDetails.value);

  displaySubwayMarkers();
  displaySchoolMarkers();
  displayHospitalMarkers();
  displaySeniorMarkers();
  displayChildMarkers();
  displayImpairmentMarkers();
  displayHomelessMarkers();
};

const subwayMarkersVisible = ref(false);
const schoolMarkersVisible = ref(false);
const hospitalMarkersVisible = ref(false);
const seniorMarkersVisible = ref(false);
const childMarkersVisible = ref(false);
const impairmentMarkersVisible = ref(false);
const homelessMarkersVisible = ref(false);

const toggleSubwayMarkers = () => {
  subwayMarkersVisible.value = !subwayMarkersVisible.value;
  subwayMarkers.value.forEach((marker) =>
    marker.setMap(subwayMarkersVisible.value ? map.value : null)
  );
};

const toggleSchoolMarkers = () => {
  schoolMarkersVisible.value = !schoolMarkersVisible.value;
  schoolMarkers.value.forEach((marker) =>
    marker.setMap(schoolMarkersVisible.value ? map.value : null)
  );
};

const toggleHospitalMarkers = () => {
  hospitalMarkersVisible.value = !hospitalMarkersVisible.value;
  hospitalMarkers.value.forEach((marker) =>
    marker.setMap(hospitalMarkersVisible.value ? map.value : null)
  );
};

const toggleSeniorMarkers = () => {
  seniorMarkersVisible.value = !seniorMarkersVisible.value;
  seniorMarkers.value.forEach((marker) =>
    marker.setMap(seniorMarkersVisible.value ? map.value : null)
  );
};

const toggleChildMarkers = () => {
  childMarkersVisible.value = !childMarkersVisible.value;
  childMarkers.value.forEach((marker) =>
    marker.setMap(childMarkersVisible.value ? map.value : null)
  );
};

const toggleImpairmentMarkers = () => {
  impairmentMarkersVisible.value = !impairmentMarkersVisible.value;
  impairmentMarkers.value.forEach((marker) =>
    marker.setMap(impairmentMarkersVisible.value ? map.value : null)
  );
};

const toggleHomelessMarkers = () => {
  homelessMarkersVisible.value = !homelessMarkersVisible.value;
  homelessMarkers.value.forEach((marker) =>
    marker.setMap(homelessMarkersVisible.value ? map.value : null)
  );
};

const displaySubwayMarkers = () => {
  if (!subwayMarkersVisible.value) return;

  subwayMarkers.value.forEach((marker) => marker.setMap(null));
  subwayMarkers.value = [];

  const bounds = map.value.getBounds();

  subwayList.value.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    if (bounds.contain(position)) {
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        title: data.name,
        image: new kakao.maps.MarkerImage(subwayMarkerImage, new kakao.maps.Size(24, 24)),
      });
      subwayMarkers.value.push(marker);
    }
  });
};

const displaySchoolMarkers = () => {
  if (!schoolMarkersVisible.value) return;

  schoolMarkers.value.forEach((marker) => marker.setMap(null));
  schoolMarkers.value = [];

  const bounds = map.value.getBounds();

  schoolList.value.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    if (bounds.contain(position)) {
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        title: data.name,
        image: new kakao.maps.MarkerImage(schoolMarkerImage, new kakao.maps.Size(24, 24)),
      });
      schoolMarkers.value.push(marker);
    }
  });
};

const displayHospitalMarkers = () => {
  if (!hospitalMarkersVisible.value) return;

  hospitalMarkers.value.forEach((marker) => marker.setMap(null));
  hospitalMarkers.value = [];

  const bounds = map.value.getBounds();

  hospitalList.value.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    if (bounds.contain(position)) {
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        title: data.name,
        image: new kakao.maps.MarkerImage(hospitalMarkerImage, new kakao.maps.Size(24, 24)),
      });
      hospitalMarkers.value.push(marker);
    }
  });
};

const displaySeniorMarkers = () => {
  if (!seniorMarkersVisible.value) return;

  seniorMarkers.value.forEach((marker) => marker.setMap(null));
  seniorMarkers.value = [];

  const bounds = map.value.getBounds();

  seniorList.value.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    if (bounds.contain(position)) {
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        title: data.name,
        image: new kakao.maps.MarkerImage(seniorMarkerImage, new kakao.maps.Size(24, 24)),
      });
      seniorMarkers.value.push(marker);
    }
  });
};

const displayChildMarkers = () => {
  if (!childMarkersVisible.value) return;

  childMarkers.value.forEach((marker) => marker.setMap(null));
  childMarkers.value = [];

  const bounds = map.value.getBounds();

  childList.value.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    if (bounds.contain(position)) {
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        title: data.name,
        image: new kakao.maps.MarkerImage(childMarkerImage, new kakao.maps.Size(24, 24)),
      });
      childMarkers.value.push(marker);
    }
  });
};

const displayImpairmentMarkers = () => {
  if (!impairmentMarkersVisible.value) return;

  impairmentMarkers.value.forEach((marker) => marker.setMap(null));
  impairmentMarkers.value = [];

  const bounds = map.value.getBounds();

  impairmentList.value.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    if (bounds.contain(position)) {
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        title: data.name,
        image: new kakao.maps.MarkerImage(impairmentMarkerImage, new kakao.maps.Size(24, 24)),
      });
      impairmentMarkers.value.push(marker);
    }
  });
};

const displayHomelessMarkers = () => {
  if (!homelessMarkersVisible.value) return;

  homelessMarkers.value.forEach((marker) => marker.setMap(null));
  homelessMarkers.value = [];

  const bounds = map.value.getBounds();

  homelessList.value.forEach((data) => {
    const position = new kakao.maps.LatLng(data.lat, data.lng);
    if (bounds.contain(position)) {
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        title: data.name,
        image: new kakao.maps.MarkerImage(homelessMarkerImage, new kakao.maps.Size(24, 24)),
      });
      homelessMarkers.value.push(marker);
    }
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

const displayMarkers = (markersData) => {
  if (markersData.length === 0) return;

  const data = markersData[0];
  const position = new kakao.maps.LatLng(data.lat, data.lng);

  const markerImage = new kakao.maps.MarkerImage(apartmentMarkerImage, new kakao.maps.Size(40, 40));

  const marker = new kakao.maps.Marker({
    position,
    map: map.value,
    image: markerImage,
  });

  const content = `
    <div style="background-color: #ad88c6; text-align: center; padding: 10px; white-space: nowrap; border-radius: 8px;">
      <div style="font-weight: bold; font-size: 14px;">${selectedApartment.value.aptName}</div>
      <div style="font-size: 12px;">${jeonsePriceStats.value.min} 억 ~ ${jeonsePriceStats.value.max} 억</div>
    </div>
  `;

  const infoWindow = new kakao.maps.InfoWindow({
    content,
    removable: false,
    disableAutoPan: false,
  });

  const contentDiv = document.createElement("div");
  contentDiv.innerHTML = content;

  setTimeout(() => {
    infoWindow.setContent(contentDiv);
    infoWindow.open(map.value, marker);
  }, 0);

  markers.value.push(marker);
};

const handleSearch = debounce(async () => {
  if (searchQuery.value.trim() !== "") {
    try {
      searchResults.value = await searchByKeyword("jeonse_info", searchQuery.value);
      showSearchResults.value = true;
      console.log(searchResults.value);
    } catch (error) {
      console.error("Error fetching search results:", error);
    }
  } else {
    searchResults.value = [];
    showSearchResults.value = false;
  }
}, 500);

const fetchJjimList = async () => {
  if (userId.value) {
    try {
      jjimList.value = await getJjim(userId.value);
    } catch (error) {
      console.error("Error fetching jjim list:", error);
    }
  }
};

const handleJjimToggle = async () => {
  if (userId.value) {
    const existingJjim = jjimList.value.find(
      (jjim) =>
        jjim.location === selectedApartment.value.dongName &&
        jjim.house_name === selectedApartment.value.aptName
    );

    try {
      if (existingJjim) {
        await deleteJjim(existingJjim.id);
      } else {
        await postJjim(
          "jeonse_info",
          userId.value,
          selectedApartment.value.dongName,
          selectedApartment.value.aptName
        );
      }
      await fetchJjimList();
    } catch (error) {
      console.error("Error toggling jjim:", error);
    }
  }
};

const isJjimmed = computed(() => {
  return jjimList.value.some(
    (jjim) =>
      jjim.location === selectedApartment.value.dongName &&
      jjim.house_name === selectedApartment.value.aptName
  );
});

const handleJjimApartment = (jjim) => {
  const jjimApartment = {
    dongName: jjim.location,
    aptName: jjim.house_name,
  };
  handleSelectApartment(jjimApartment);
};

const handleSelectApartment = async (apartment) => {
  selectedApartment.value = apartment;
  showSearchResults.value = false;
  selectedArea.value = "전체";
  try {
    const detail = await getDetail("jeonse_info", apartment.dongName, apartment.aptName);
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
      await fetchJjimList();
    } else {
      console.error("No details found for the selected apartment.");
    }
  } catch (error) {
    console.error("Error fetching apartment details:", error);
  }
};

const uniqueAreas = computed(() => {
  const areas = new Set(selectedApartmentDetails.value.map((detail) => detail.area));
  return ["전체", ...Array.from(areas).sort((a, b) => parseFloat(a) - parseFloat(b))];
});

const filteredJeonseDetails = computed(() => {
  if (selectedArea.value === "전체") {
    return selectedApartmentDetails.value.filter((detail) => detail.dealType === "전세");
  }
  return selectedApartmentDetails.value.filter(
    (detail) => detail.dealType === "전세" && detail.area === selectedArea.value
  );
});

const filteredWolseDetails = computed(() => {
  if (selectedArea.value === "전체") {
    return selectedApartmentDetails.value.filter((detail) => detail.dealType === "월세");
  }
  return selectedApartmentDetails.value.filter(
    (detail) => detail.dealType === "월세" && detail.area === selectedArea.value
  );
});

const jeonsePriceStats = computed(() => {
  if (filteredJeonseDetails.value.length === 0) return { min: 0, max: 0, avg: 0 };

  const prices = filteredJeonseDetails.value.map((detail) =>
    parseInt(detail.dealPrice.replace(/,/g, ""), 10)
  );
  const min = Math.min(...prices);
  const max = Math.max(...prices);
  const avg = prices.reduce((sum, price) => sum + price, 0) / prices.length;

  return {
    min: (min / 10000).toFixed(2),
    max: (max / 10000).toFixed(2),
    avg: (avg / 10000).toFixed(2),
  };
});

const wolsePriceStats = computed(() => {
  if (filteredWolseDetails.value.length === 0) return { min: 0, max: 0, avg: 0 };

  const prices = filteredWolseDetails.value.map((detail) =>
    parseInt(detail.dealPrice.replace(/,/g, ""), 10)
  );
  const min = Math.min(...prices);
  const max = Math.max(...prices);
  const avg = prices.reduce((sum, price) => sum + price, 0) / prices.length;

  return {
    min: (min / 10000).toFixed(2),
    max: (max / 10000).toFixed(2),
    avg: (avg / 10000).toFixed(2),
  };
});

const openModal = (detail) => {
  selectedDetail.value = detail;
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
  selectedDetail.value = null;
};

watch(searchQuery, handleSearch);

onMounted(() => {
  initMap();
  getMapMarkerFilter();
  fetchUserId();
});
</script>

<template>
  <div class="container">
    <div class="sidebar">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="아파트 이름 검색"
        class="search-input"
      />
      <div v-if="showSearchResults && searchResults.length" class="search-results">
        <ul class="search-results-list">
          <li
            v-for="apartment in searchResults"
            :key="apartment.aptName"
            @click="handleSelectApartment(apartment)"
            class="search-result-item"
          >
            {{ apartment.dongName }} - {{ apartment.aptName }}
          </li>
        </ul>
      </div>
      <div v-if="selectedApartmentDetails.length">
        <div style="display: flex; justify-content: space-around">
          <div>
            <h2>{{ selectedApartment.aptName }}</h2>
            <p>
              <strong>{{ selectedApartment.dongName }}</strong>
            </p>
          </div>
          <div>
            <button
              v-if="userId"
              @click="handleJjimToggle"
              :class="{ active: isJjimmed }"
              class="jjim-button"
            >
              <i :class="isJjimmed ? 'bi bi-heart-fill' : 'bi bi-heart'"></i>
            </button>
          </div>
        </div>
        <div ref="roadViewContainer" class="road-view-container"></div>
        <div style="margin: 20px">
          <button class="jwBtn" @click="dealType = '전세'" :class="{ active: dealType === '전세' }">
            전세
          </button>
          <button class="jwBtn" @click="dealType = '월세'" :class="{ active: dealType === '월세' }">
            월세
          </button>
        </div>
        <div v-if="dealType === '전세'">
          <div>
            <div class="price-stats">
              <div class="price-range">
                <div>{{ jeonsePriceStats.min }}억원 ~ {{ jeonsePriceStats.max }}억원</div>
                <div>(평균 {{ jeonsePriceStats.avg }}억원)</div>
              </div>
              <div>
                <select v-model="selectedArea" class="area-select">
                  <option v-for="area in uniqueAreas" :key="area" :value="area">{{ area }}</option>
                </select>
              </div>
            </div>
            <ul class="apartment-details-list">
              <li class="apartment-details-header">
                <span>거래 날짜</span>
                <span>면적</span>
                <span>보증금</span>
              </li>
              <li
                v-for="detail in filteredJeonseDetails"
                :key="detail.dealDate + detail.area"
                class="apartment-detail-item"
                @click="openModal(detail)"
              >
                <span>{{ detail.dealDate }}</span>
                <span>{{ detail.area }} ㎡</span>
                <span
                  >{{
                    (parseInt(detail.dealPrice.replace(/,/g, ""), 10) / 10000).toFixed(2)
                  }}
                  억원</span
                >
              </li>
            </ul>
          </div>
        </div>
        <div v-if="dealType === '월세'">
          <div>
            <div class="price-stats">
              <div class="price-range">
                <div>{{ wolsePriceStats.min }}억원 ~ {{ wolsePriceStats.max }}억원</div>
                <div>(평균 {{ wolsePriceStats.avg }}억원)</div>
              </div>
              <div>
                <select v-model="selectedArea" class="area-select">
                  <option v-for="area in uniqueAreas" :key="area" :value="area">{{ area }}</option>
                </select>
              </div>
            </div>
            <ul class="apartment-details-list">
              <li class="apartment-details-header">
                <span>거래 날짜</span>
                <span>면적</span>
                <span>보증금</span>
              </li>
              <li
                v-for="detail in filteredWolseDetails"
                :key="detail.dealDate + detail.area"
                class="apartment-detail-item"
                @click="openModal(detail)"
              >
                <span>{{ detail.dealDate }}</span>
                <span>{{ detail.area }} ㎡</span>
                <span
                  >{{
                    (parseInt(detail.dealPrice.replace(/,/g, ""), 10) / 10000).toFixed(2)
                  }}
                  억원</span
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <div id="map" class="map-container">
      <div class="jjim-list-toggle">
        <button @click="showJjimList = !showJjimList" class="jjim-list-button">
          <i class="bi bi-heart"></i>
        </button>
        <div v-if="showJjimList" class="jjim-list">
          <ul>
            <li v-for="jjim in jjimList" :key="jjim.id" @click="handleJjimApartment(jjim)">
              {{ jjim.location }} - {{ jjim.house_name }}
            </li>
          </ul>
        </div>
      </div>
      <div class="map-buttons">
        <button @click="toggleSubwayMarkers" class="map-button">
          <div><img src="@/assets/facilities/subway.png" width="24px" /></div>
          <div class="button-label">지하철</div>
        </button>
        <button @click="toggleSchoolMarkers" class="map-button">
          <div><img src="@/assets/facilities/school.png" width="24px" /></div>
          <div class="button-label">학교</div>
        </button>
        <button @click="toggleHospitalMarkers" class="map-button">
          <div><img src="@/assets/facilities/hospital.png" width="24px" /></div>
          <div class="button-label">병원</div>
        </button>
        <button @click="toggleSeniorMarkers" class="map-button">
          <div><img src="@/assets/facilities/senior.png" width="24px" /></div>
          <div class="button-label">노인</div>
        </button>
        <button @click="toggleChildMarkers" class="map-button">
          <div><img src="@/assets/facilities/child.png" width="24px" /></div>
          <div class="button-label">아동</div>
        </button>
        <button @click="toggleImpairmentMarkers" class="map-button">
          <div><img src="@/assets/facilities/impairment.png" width="24px" /></div>
          <div class="button-label">장애인</div>
        </button>
        <button @click="toggleHomelessMarkers" class="map-button">
          <div><img src="@/assets/facilities/homeless.png" width="24px" /></div>
          <div class="button-label">노숙인</div>
        </button>
      </div>
    </div>

    <div v-if="isModalOpen" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2 style="margin-bottom: 30px">{{ selectedApartment.aptName }}</h2>
        <p>
          <strong>거래일:</strong> {{ selectedDetail.dealDate.substring(0, 4) }}년
          {{ selectedDetail.dealDate.substring(4, 6) }}월
          {{ selectedDetail.dealDate.substring(6, 8) }}일
        </p>
        <p><strong>거래 타입:</strong> {{ selectedDetail.dealType }}</p>
        <p><strong>매물 타입:</strong> {{ selectedDetail.houseType }}</p>
        <p><strong>면적:</strong> {{ selectedDetail.area }} ㎡</p>
        <p><strong>건축년도:</strong> {{ selectedDetail.buildYear }}년</p>
        <p><strong>층:</strong> {{ selectedDetail.floor }}층</p>
        <p>
          <strong>보증금:</strong>
          {{ (parseInt(selectedDetail.dealPrice.replace(/,/g, ""), 10) / 10000).toFixed(2) }} 억원
        </p>
        <p v-if="selectedDetail.dealType === '월세'">
          <strong>월세:</strong> {{ selectedDetail.rentPrice }} 만원
        </p>
        <button @click="closeModal">닫기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
ul {
  padding: 0;
  margin: 0;
}

li {
  list-style-type: none;
}

.container {
  position: relative;
  margin: 0 auto;
  padding: 0;
  width: 100%;
  height: 90vh;
}

.sidebar {
  width: 30%;
  height: 100%;
  float: left;
  padding: 16px;
  box-sizing: border-box;
  overflow-y: auto;
  background-color: #f8f8f8;
}

.search-input {
  padding: 8px;
  font-size: 16px;
  width: 100%;
  margin-bottom: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-results {
  background: white;
  border: 1px solid #ccc;
  max-height: 200px;
  overflow-y: auto;
  margin-bottom: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.search-results-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.search-result-item {
  padding: 8px;
  cursor: pointer;
}

.road-view-container {
  width: 100%;
  height: 300px;
  margin-bottom: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.price-stats {
  display: flex;
  justify-content: space-around;
  padding-bottom: 10px;
}

.price-range {
  display: flex;
  flex-direction: column;
  font-size: 20px;
}

.area-select {
  padding: 8px;
  margin-bottom: 16px;
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.apartment-details-list {
  padding: 0;
  list-style-type: none;
  max-height: 300px;
  overflow-y: auto;
}

.apartment-details-header {
  display: flex;
  justify-content: space-between;
  padding: 8px;
  border-bottom: 1px solid #ccc;
  background: #f0f0f0;
  font-weight: bold;
}

.apartment-detail-item {
  display: flex;
  justify-content: space-between;
  padding: 8px;
  border-bottom: 1px solid #eee;
  transition: background-color 0.2s;
  cursor: pointer;
}

.map-container {
  width: 70%;
  height: 100%;
  position: relative;
  float: right;
}

.map-buttons {
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  flex-direction: column;
}

.map-button {
  margin-bottom: 5px;
  padding: 10px;
  color: white;
  border: none;
  border-radius: 4px;
  z-index: 2;
  background-color: #ad88c6;
}
.jwBtn {
  padding: 0 10px;
  border: solid 1px;
}
.button-label {
  font-size: 10px;
}

button.active {
  background-color: #d1a7e0;
}

.jjim-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 24px;
  color: #ad88c6;
  transition: color 0.3s;
}

.jjim-button.active {
  color: #ff6b6b;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 400px;
  max-width: 90%;
}

.modal-content h3 {
  margin-top: 0;
}

.modal-content button {
  background-color: #ad88c6;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}
.jjim-list-toggle {
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 2;
}

.jjim-list-button {
  background: #ad88c6;
  border: none;
  cursor: pointer;
  font-size: 24px;
  color: white;
  transition: color 0.3s;
}

.jjim-list {
  background: white;
  border: 1px solid #ccc;
  margin-top: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 200px;
  max-height: 200px;
  overflow-y: auto;
  z-index: 1000;
}

.jjim-list ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.jjim-list li {
  padding: 8px;
  cursor: pointer;
}
</style>
