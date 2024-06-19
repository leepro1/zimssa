# 🏡 짐싸(zimssa)


## Team Members 
|박정민|이태현|이희주|
|:--------------------:|:--------------------:|:--------------------:|
|<img src = "https://avatars.githubusercontent.com/u/90558247?v=4" width = "100" height = "100">|<img src = "https://avatars.githubusercontent.com/u/107914598?s=400&v=4" width = "100" height = "100">|<img src = "https://avatars.githubusercontent.com/u/107914598?s=400&v=4" width = "100" height = "100">
|[@inmyownway](https://github.com/inmyownway)|[@taehyeoon](https://github.com/taehyeoon)|[Huiju Lee](https://github.com/leepro1)|
## 💜 배경 및 개요

서울 공공데이터 아파트 실거래가 데이터를 바탕으로 매매, 전월세 거래 정보를 제공하는 서비스입니다.
개인 맞춤형 "전세보증대출상품" 추천 서비스를 제공합니다. 사용자에게 가장 적합한 대출 상품을 추천하므로써 사용자가 부동산을 구매할 때, 고려할 수 있는 기준을 추가적으로 제공합니다.
부동산 취약 계층을 고려하여 AI 챗봇과 복지 시설 기능을 제공합니다.
지하철, 학교 뿐만아니라, 사회복지시설, 아동복지시설, 노인의료시설등 다양한 복지 시설의 위치 정보를 제공합니다.

### 프로젝트 진행 기간

2024.5.7(화) ~ 2024.05.24(금)

### DataBase 설계
<img width="509" alt="image" src="https://github.com/TeamZimssa/zimssa/assets/90558247/dfb34ccc-77ca-466b-9c0c-88bed41f7082">

## 💜 주요 기능

### 주택 실거래가 (매매, 전월세)

- 국토교통부 실거래가 정보를 활용하여 데이터 가공 후 유저의 검색 정보에 따라 검색 결과를 제공합니다.
- 아파트 이름으로 검색을 지원합니다.
- 로그인을 한 경우, 찜 기능을 통해 관심있는 매물을 저장할 수 있습니다.
  
![매물검색2](https://github.com/ksh103/HappyHouse/assets/90558247/7aac0099-e1b6-428c-9e1d-efddd4e5a02c)

### 편의 시설 정보

- 공공 데이터를 활용하여 복지 시설 정보(사회 복지시설, 노인의료 복지시설, 아동보육시설, 장애인 복지시설, 노숙인 쉼터, 학교, 지하철, 병원)를 제공합니다.
![편의시설](https://github.com/ksh103/HappyHouse/assets/90558247/c7e1569c-e0e0-4de7-b181-332fbd390c48)


### 회원 관리

- 로그인, 로그아웃, 마이페이지 조회, 수정, 비밀번호 찾기 기능을 제공합니다.
- 일반 유저, 관리자 로그인 구분합니다.
- 비밀번호 찾기 기능은 회원가입시 저장한 이메일을 통해 임시 비밀번호를 발급하여 진행할 수 있습니다.
  ![회원가입](https://github.com/ksh103/HappyHouse/assets/90558247/8ae0aa57-03eb-436d-8deb-db57c654f9c5)


### 개인 맞춤형 전세 대출 정보 제공

- 사용자의 거주지역, 나이, 연소득, 부채 금액, 결혼 유무, 보유 주택 수, 보증 우대구분을 통해 "개인 맞춤형 전세 대출 정보"를 제공합니다.

### 챗봇

- chatGPT API를 이용하여 부동산 관련 질의에 답변해주는 챗봇 기능을 제공합니다.

![대출](https://github.com/ksh103/HappyHouse/assets/90558247/8770cee2-3da9-4f0d-9ce6-d44bac3288ce)



### 뉴스 정보

- 네이버 API를 활용하여 부동산에 관련된 4가지 키워드에 대한 뉴스 정보를 제공합니다
- 키워드 : 전세, 월세, 부동산, 재건축

  ![뉴스](https://github.com/ksh103/HappyHouse/assets/90558247/cbf97161-38ef-4773-8991-dbaeb2ddb4ab)


### 공지사항

- 일반 유저와 관리자에 따라서 사용할 수 있는 기능이 달라집니다.
- 일반 유저의 경우, 게시된 공지사항을 확인만 할 수 있습니다.
- 관리자의 경우, 공지사항을 추가, 삭제, 수정할 수 있습니다.

![공지사항](https://github.com/ksh103/HappyHouse/assets/90558247/1a82154c-1919-42b1-a142-8e58b54e98ad)




---

## 💻 주요 기술

### Backend

- JDK 17
- Springboot 3.2.4
- Maven 4.0.0
- Mybatis 3.0.3
- Lombok
- MySQL, Workbench

### Frontend

- VSCode
- Vue 3.4.27
- bootstrap 5.3.3
- pinia 2.1.7
- Node LTS
