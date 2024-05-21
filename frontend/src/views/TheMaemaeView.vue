<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { debounce } from "lodash";
import { searchByKeyword, getDetail } from "@/api/house";
import { getMapMarker } from "@/api/mapMarker";
import apartmentMarkerImage from "@/assets/apartment.png";
import subwayMarkerImage from "@/assets/subway.png";
import schoolMarkerImage from "@/assets/school.png";
import hospitalMarkerImage from "@/assets/hospital.png";
import seniorMarkerImage from "@/assets/hospital.png";
import childMarkerImage from "@/assets/hospital.png";
import impairmentMarkerImage from "@/assets/hospital.png";
import homelessMarkerImage from "@/assets/hospital.png";

const map = ref(null);
const markers = ref([]);
const searchQuery = ref("");
const searchResults = ref([]);
const selectedApartment = ref(null);
const selectedApartmentDetails = ref([]);
const showSearchResults = ref(true);
const selectedArea = ref("전체");

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

const center = ref({ lat: 37.5665, lng: 126.978 }); // 초기 지도 중심 (서울시청 기준)
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
    <div class="info-window" style="background-color: #e1afd1; text-align: center; padding: 10px; white-space: nowrap; border-radius: 8px;">
      <div style="font-weight: bold; font-size: 14px;">${selectedApartment.value.aptName}</div>
      <div style="font-size: 12px;">${priceStats.value.min} 억 ~ ${priceStats.value.max} 억</div>
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
      searchResults.value = await searchByKeyword("maemae_info", searchQuery.value);
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

const handleSelectApartment = async (apartment) => {
  selectedApartment.value = apartment;
  showSearchResults.value = false;
  selectedArea.value = "전체";
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

const uniqueAreas = computed(() => {
  const areas = new Set(selectedApartmentDetails.value.map((detail) => detail.area));
  return ["전체", ...Array.from(areas).sort((a, b) => parseFloat(a) - parseFloat(b))];
});

const filteredDetails = computed(() => {
  if (selectedArea.value === "전체") {
    return selectedApartmentDetails.value;
  }
  return selectedApartmentDetails.value.filter((detail) => detail.area === selectedArea.value);
});

const priceStats = computed(() => {
  if (filteredDetails.value.length === 0) return { min: 0, max: 0, avg: 0 };

  const prices = filteredDetails.value.map((detail) =>
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

watch(searchQuery, handleSearch);

onMounted(() => {
  initMap();
  getMapMarkerFilter();
});
</script>

<template>
  <div style="position: relative; height: 87vh">
    <div
      style="
        width: 30%;
        height: 100%;
        float: left;
        padding: 16px;
        box-sizing: border-box;
        overflow-y: auto;
        background-color: #f8f8f8;
      "
    >
      <input
        v-model="searchQuery"
        type="text"
        placeholder="아파트 이름 검색"
        style="
          padding: 8px;
          font-size: 16px;
          width: 100%;
          margin-bottom: 16px;
          border: 1px solid #ccc;
          border-radius: 4px;
        "
      />
      <div
        v-if="showSearchResults && searchResults.length"
        style="
          background: white;
          border: 1px solid #ccc;
          max-height: 200px;
          overflow-y: auto;
          margin-bottom: 16px;
          box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        "
      >
        <ul style="list-style-type: none; padding: 0; margin: 0">
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
      <div v-if="selectedApartmentDetails.length">
        <h2>{{ selectedApartment.aptName }}</h2>
        <p>
          <strong>{{ selectedApartment.dongName }}</strong>
        </p>
        <div v-if="filteredDetails.length">
          <div>
            <p>
              {{ priceStats.min }}억원 ~ {{ priceStats.max }}억원 (평균 {{ priceStats.avg }}억원)
            </p>
          </div>
        </div>
        <div
          ref="roadViewContainer"
          style="
            width: 100%;
            height: 300px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
          "
        ></div>
        <div>
          <select
            v-model="selectedArea"
            style="
              padding: 8px;
              margin-bottom: 16px;
              width: 100%;
              border: 1px solid #ccc;
              border-radius: 4px;
            "
          >
            <option v-for="area in uniqueAreas" :key="area" :value="area">{{ area }}</option>
          </select>
        </div>
        <ul style="padding: 0; list-style-type: none; max-height: 300px; overflow-y: auto">
          <li
            style="
              display: flex;
              justify-content: space-between;
              padding: 8px;
              border-bottom: 1px solid #ccc;
              background: #f0f0f0;
              font-weight: bold;
            "
          >
            <span>거래 날짜</span>
            <span>면적</span>
            <span>거래 가격</span>
            <span>층</span>
          </li>
          <li
            v-for="detail in filteredDetails"
            :key="detail.dealDate + detail.area + detail.floor"
            style="
              display: flex;
              justify-content: space-between;
              padding: 8px;
              border-bottom: 1px solid #eee;
              transition: background-color 0.2s;
              cursor: pointer;
            "
          >
            <span>{{ detail.dealDate }}</span>
            <span>{{ detail.area }} ㎡</span>
            <span
              >{{
                (parseInt(detail.dealPrice.replace(/,/g, ""), 10) / 10000).toFixed(2)
              }}
              억원</span
            >
            <span>{{ detail.floor }}층</span>
          </li>
        </ul>
      </div>
    </div>
    <div id="map" style="width: 70%; height: 100%; position: relative">
      <div
        style="position: absolute; top: 10px; right: 10px; display: flex; flex-direction: column"
      >
        <button
          @click="toggleSubwayMarkers"
          style="
            margin-bottom: 5px;
            padding: 10px;
            color: white;
            border: none;
            border-radius: 4px;
            z-index: 2;
            background-color: #4caf50;
          "
        >
          <div><img src="@/assets/subway.png" width="24px" /></div>
          <div style="font-size: 10px">지하철</div>
        </button>

        <button
          @click="toggleSchoolMarkers"
          style="
            margin-bottom: 5px;
            padding: 10px;
            color: white;
            border: none;
            border-radius: 4px;
            z-index: 2;
            background-color: #2196f3;
          "
        >
          <div><img src="@/assets/school.png" width="24px" /></div>
          <div style="font-size: 10px">학교</div>
        </button>

        <button
          @click="toggleHospitalMarkers"
          style="
            margin-bottom: 5px;
            padding: 10px;
            color: white;
            border: none;
            border-radius: 4px;
            z-index: 2;
            background-color: #f44336;
          "
        >
          <div><img src="@/assets/hospital.png" width="24px" /></div>
          <div style="font-size: 10px">병원</div>
        </button>

        <button
          @click="toggleSeniorMarkers"
          style="
            margin-bottom: 5px;
            padding: 10px;
            color: white;
            border: none;
            border-radius: 4px;
            z-index: 2;
            background-color: #f44336;
          "
        >
          <div><img src="@/assets/hospital.png" width="24px" /></div>
          <div style="font-size: 10px">노인</div>
        </button>

        <button
          @click="toggleChildMarkers"
          style="
            margin-bottom: 5px;
            padding: 10px;
            color: white;
            border: none;
            border-radius: 4px;
            z-index: 2;
            background-color: #f44336;
          "
        >
          <div><img src="@/assets/hospital.png" width="24px" /></div>
          <div style="font-size: 10px">아동</div>
        </button>

        <button
          @click="toggleImpairmentMarkers"
          style="
            margin-bottom: 5px;
            padding: 10px;
            color: white;
            border: none;
            border-radius: 4px;
            z-index: 2;
            background-color: #f44336;
          "
        >
          <div><img src="@/assets/hospital.png" width="24px" /></div>
          <div style="font-size: 10px">장애인</div>
        </button>

        <button
          @click="toggleHomelessMarkers"
          style="
            margin-bottom: 5px;
            padding: 10px;
            color: white;
            border: none;
            border-radius: 4px;
            z-index: 2;
            background-color: #f44336;
          "
        >
          <div><img src="@/assets/hospital.png" width="24px" /></div>
          <div style="font-size: 10px">노숙인</div>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow: hidden;
}

ul {
  padding: 0;
  margin: 0;
}

li {
  list-style-type: none;
}
</style>
