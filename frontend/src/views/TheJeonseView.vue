<template>
  <div>
    <p style="margin-top: -12px">
      <em class="link">
        <a href="/web/documentation/#CategoryCode" target="_blank"
          >카테고리 코드목록을 보시려면 여기를 클릭하세요!</a
        >
      </em>
    </p>
    <div class="map_wrap">
      <div id="map" style="width: 100%; height: 100%; position: relative; overflow: hidden"></div>
      <ul id="category">
        <li id="CS2" data-order="0">
          <span class="category_bg store"></span>
          편의점
        </li>
        <li id="PS3" data-order="1">
          <span class="category_bg kindergarten"></span>
          어린이집, 유치원
        </li>
        <li id="SC4" data-order="2">
          <span class="category_bg school"></span>
          학교
        </li>
        <li id="PK6" data-order="3">
          <span class="category_bg parking"></span>
          주차장
        </li>
        <li id="OL7" data-order="4">
          <span class="category_bg oil"></span>
          주유소, 충전소
        </li>
        <li id="SW8" data-order="5">
          <span class="category_bg subway"></span>
          지하철역
        </li>
        <li id="BK9" data-order="6">
          <span class="category_bg bank"></span>
          은행
        </li>
        <li id="CT1" data-order="7">
          <span class="category_bg culture"></span>
          문화시설
        </li>
        <li id="AG2" data-order="8">
          <span class="category_bg real_estate"></span>
          중개업소
        </li>
        <li id="PO3" data-order="9">
          <span class="category_bg public_office"></span>
          공공기관
        </li>
        <li id="CE7" data-order="10">
          <span class="category_bg cafe"></span>
          카페
        </li>
        <li id="HP8" data-order="11">
          <span class="category_bg hospital"></span>
          병원
        </li>
        <li id="PM9" data-order="12">
          <span class="category_bg pharmacy"></span>
          약국
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";

onMounted(() => {
  var placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 }),
    contentNode = document.createElement("div"),
    markers = [],
    currCategory = "";

  var mapContainer = document.getElementById("map"),
    mapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567),
      level: 5,
    };

  var map = new kakao.maps.Map(mapContainer, mapOption);
  var ps = new kakao.maps.services.Places(map);

  kakao.maps.event.addListener(map, "idle", searchPlaces);

  contentNode.className = "placeinfo_wrap";
  addEventHandle(contentNode, "mousedown", kakao.maps.event.preventMap);
  addEventHandle(contentNode, "touchstart", kakao.maps.event.preventMap);
  placeOverlay.setContent(contentNode);

  addCategoryClickEvent();

  function addEventHandle(target, type, callback) {
    if (target.addEventListener) {
      target.addEventListener(type, callback);
    } else {
      target.attachEvent("on" + type, callback);
    }
  }

  function searchPlaces() {
    if (!currCategory) {
      return;
    }

    placeOverlay.setMap(null);
    removeMarker();
    ps.categorySearch(currCategory, placesSearchCB, { useMapBounds: true });
  }

  function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      displayPlaces(data);
    }
  }

  function displayPlaces(places) {
    var order = document.getElementById(currCategory).getAttribute("data-order");

    for (var i = 0; i < places.length; i++) {
      var marker = addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

      (function (marker, place) {
        kakao.maps.event.addListener(marker, "click", function () {
          displayPlaceInfo(place);
        });
      })(marker, places[i]);
    }
  }

  function addMarker(position, order) {
    var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png",
      imageSize = new kakao.maps.Size(27, 28),
      imgOptions = {
        spriteSize: new kakao.maps.Size(72, 208),
        spriteOrigin: new kakao.maps.Point(46, order * 36),
        offset: new kakao.maps.Point(11, 28),
      },
      markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
      marker = new kakao.maps.Marker({
        position: position,
        image: markerImage,
      });

    marker.setMap(map);
    markers.push(marker);

    return marker;
  }

  function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    markers = [];
  }

  function displayPlaceInfo(place) {
    var content =
      '<div class="placeinfo">' +
      '   <a class="title" href="' +
      place.place_url +
      '" target="_blank" title="' +
      place.place_name +
      '">' +
      place.place_name +
      "</a>";

    if (place.road_address_name) {
      content +=
        '    <span title="' +
        place.road_address_name +
        '">' +
        place.road_address_name +
        "</span>" +
        '  <span class="jibun" title="' +
        place.address_name +
        '">(지번 : ' +
        place.address_name +
        ")</span>";
    } else {
      content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
    }

    content +=
      '<span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

    contentNode.innerHTML = content;
    placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
    placeOverlay.setMap(map);
  }

  function addCategoryClickEvent() {
    var category = document.getElementById("category"),
      children = category.children;

    for (var i = 0; i < children.length; i++) {
      children[i].onclick = onClickCategory;
    }
  }

  function onClickCategory() {
    var id = this.id,
      className = this.className;

    placeOverlay.setMap(null);

    if (className === "on") {
      currCategory = "";
      changeCategoryClass();
      removeMarker();
    } else {
      currCategory = id;
      changeCategoryClass(this);
      searchPlaces();
    }
  }

  function changeCategoryClass(el) {
    var category = document.getElementById("category"),
      children = category.children,
      i;

    for (i = 0; i < children.length; i++) {
      children[i].className = "";
    }

    if (el) {
      el.className = "on";
    }
  }
});
</script>

<style scoped>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .store {
  background-position: -10px -180px;
}
#category li .kindergarten {
  background-position: -10px -216px;
}
#category li .school {
  background-position: -10px -252px;
}
#category li .parking {
  background-position: -10px -288px;
}
#category li .oil {
  background-position: -10px -324px;
}
#category li .subway {
  background-position: -10px -360px;
}
#category li .bank {
  background-position: -10px -396px;
}
#category li .culture {
  background-position: -10px -432px;
}
#category li .real_estate {
  background-position: -10px -468px;
}
#category li .public_office {
  background-position: -10px -504px;
}
#category li .cafe {
  background-position: -10px -540px;
}
#category li .hospital {
  background-position: -10px -576px;
}
#category li .pharmacy {
  background-position: -10px -612px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
