![header](https://capsule-render.vercel.app/api?type=waving&color=gradient&height=180&section=header&text=%E2%98%95%EF%B8%8F%20SSAFEE%20&fontSize=32&animation=fadeIn&fontAlignY=36&fontColor=ffffff)

<h2 align="center">
쉽고 편리한 커피 주문
</h2>
<br>
<p align="center">
<a href="https://ssafy.coffee">Visit Our Site</a>
</p>

### 개발 기간

| 2024.01.03 ~ 2024.02.16 |
| ----------------------- |

### 개요

SSAFEE는 사업장 내에서 커피주문 및 수령에 어려움을 겪는 SSAFY인들의 불편을 해소하고, 나아가 자주 주문하는 카페와 배달 플랫폼을 배제하고 직접 주문을 연결하여 소비자와 판매자가 모두 이익을 보는, **편의성**과 **지역 상생**을 도모한 프로젝트 입니다. 

### 팀 소개

**"☕ 불편한 문제를 모두에게 편리하도록 풀어내는 서비스"**

| 팀원    | 역할      |
| ------- | --------- |
| 👼박희찬 | 팀장, BE  |
| 👶고영훈 | BE, INFRA |
| 😁양희승 | BE        |
| 👩전상혁 | FE        |
| 😄주홍찬 | FE        |
| 👦강찬우 | FE        |

## 🔨 기술 스택 🔨

**Backend**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring%20boot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

**Frontend**

![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)
![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![PNPM](https://img.shields.io/badge/pnpm-%234a4a4a.svg?style=for-the-badge&logo=pnpm&logoColor=f69220)
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![Nuxtjs](https://img.shields.io/badge/Nuxt-002E3B?style=for-the-badge&logo=nuxtdotjs&logoColor=#00DC82)
![UnoCSS](https://img.shields.io/badge/unocss-333333.svg?style=for-the-badge&logo=unocss&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)

**Database**

![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![PlanetScale](https://img.shields.io/badge/planetscale-%23000000.svg?style=for-the-badge&logo=planetscale&logoColor=white)


**Deploy**

![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Cloudflare](https://img.shields.io/badge/Cloudflare-F38020?style=for-the-badge&logo=Cloudflare&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)


**Collaboration**

![GitLab](https://img.shields.io/badge/gitlab-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white)
![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)

---

## 파일 구조

<details>
<summary>
<b>Frontend</b>
</summary>

```log
frontend
├── app.vue
├── components
|  ├── AfterCart.vue
|  ├── CarrierList.vue
|  ├── Cart.vue
|  ├── Chat.vue
|  ├── Info.vue
|  ├── InputEntry.vue
|  ├── MenuCategoryList.vue
|  ├── MenuList.vue
|  ├── OrderList.vue
|  ├── OrderListModal.vue
|  ├── OrderModal.vue
|  └── OrderSummary.vue
├── composables
|  ├── creator.ts
|  ├── shop.ts
|  └── user.ts
├── constants
|  └── index.ts
├── Dockerfile
├── eslint.config.ts
├── layouts
|  ├── default.vue
|  ├── manager.vue
|  └── README.md
├── LICENSE
├── netlify.toml
├── nuxt.config.ts
├── package.json
├── pages
|  ├── create.vue
|  ├── index.vue
|  ├── login
|  |  └── oauth2
|  |     └── redirect
|  |        └── [registration].vue
|  ├── manager
|  |  ├── index.vue
|  |  ├── login.vue
|  |  └── order.vue
|  ├── room
|  |  └── [id].vue
|  └── [...all].vue
├── pnpm-lock.yaml
├── public
|  ├── favicon.ico
|  ├── img
|  |  ├── logo.png
|  |  └── send.png
|  ├── music.mp3
|  └── robots.txt
├── README.md
├── tsconfig.json
├── types
|  └── index.d.ts
└── uno.config.ts
```
</details>

<br>

<details>
<summary>
<b>Backend</b>
</summary>

```log
backend
├── build.gradle
├── Dockerfile
├── gradle
|  └── wrapper
|     ├── gradle-wrapper.jar
|     └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
   ├── main
   |  ├── java
   |  |  └── coffee
   |  |     └── ssafy
   |  |        └── ssafee
   |  |           ├── common
   |  |           |  ├── BaseTimeEntity.java
   |  |           |  ├── CookieUtils.java
   |  |           |  ├── DateUtils.java
   |  |           |  ├── ExceptionHandler.java
   |  |           |  ├── OriginProps.java
   |  |           |  └── S3Props.java
   |  |           ├── config
   |  |           |  ├── JpaAuditConfig.java
   |  |           |  ├── ProblemConfig.java
   |  |           |  ├── S3Config.java
   |  |           |  ├── SecurityConfig.java
   |  |           |  ├── StompWebSocketConfig.java
   |  |           |  └── SwaggerConfig.java
   |  |           ├── domain
   |  |           |  ├── chat
   |  |           |  |  ├── controller
   |  |           |  |  |  └── ChatController.java
   |  |           |  |  ├── dto
   |  |           |  |  |  ├── ChatInfo.java
   |  |           |  |  |  ├── request
   |  |           |  |  |  └── response
   |  |           |  |  ├── entity
   |  |           |  |  |  └── Chat.java
   |  |           |  |  ├── mapper
   |  |           |  |  |  └── ChatMapper.java
   |  |           |  |  ├── repository
   |  |           |  |  |  └── ChatRepository.java
   |  |           |  |  └── service
   |  |           |  |     └── ChatService.java
   |  |           |  ├── order
   |  |           |  |  ├── controller
   |  |           |  |  |  ├── RoomOrderController.java
   |  |           |  |  |  └── ShopOrderController.java
   |  |           |  |  ├── dto
   |  |           |  |  |  ├── OrderInfo.java
   |  |           |  |  |  └── response
   |  |           |  |  ├── entity
   |  |           |  |  |  └── Order.java
   |  |           |  |  ├── exception
   |  |           |  |  |  ├── OrderErrorCode.java
   |  |           |  |  |  └── OrderException.java
   |  |           |  |  ├── mapper
   |  |           |  |  |  └── OrderMapper.java
   |  |           |  |  ├── repository
   |  |           |  |  |  └── OrderRepository.java
   |  |           |  |  └── service
   |  |           |  |     ├── RoomOrderService.java
   |  |           |  |     └── ShopOrderService.java
   |  |           |  ├── room
   |  |           |  |  ├── controller
   |  |           |  |  |  ├── ChoiceMenuController.java
   |  |           |  |  |  ├── CreatorController.java
   |  |           |  |  |  ├── ParticipantController.java
   |  |           |  |  |  └── RoomController.java
   |  |           |  |  ├── dto
   |  |           |  |  |  ├── request
   |  |           |  |  |  └── response
   |  |           |  |  ├── entity
   |  |           |  |  |  ├── ChoiceMenu.java
   |  |           |  |  |  ├── ChoiceOption.java
   |  |           |  |  |  ├── ChoiceOptionCategory.java
   |  |           |  |  |  ├── Creator.java
   |  |           |  |  |  ├── Participant.java
   |  |           |  |  |  └── Room.java
   |  |           |  |  ├── exception
   |  |           |  |  |  ├── RoomErrorCode.java
   |  |           |  |  |  └── RoomException.java
   |  |           |  |  ├── mapper
   |  |           |  |  |  ├── ChoiceMenuMapper.java
   |  |           |  |  |  ├── ParticipantMapper.java
   |  |           |  |  |  └── RoomMapper.java
   |  |           |  |  ├── repository
   |  |           |  |  |  ├── ChoiceMenuRepository.java
   |  |           |  |  |  ├── ChoiceOptionCategoryRepository.java
   |  |           |  |  |  ├── ChoiceOptionRepository.java
   |  |           |  |  |  ├── CreatorRepository.java
   |  |           |  |  |  ├── ParticipantRepository.java
   |  |           |  |  |  └── RoomRepository.java
   |  |           |  |  └── service
   |  |           |  |     ├── ChoiceMenuService.java
   |  |           |  |     ├── CreatorService.java
   |  |           |  |     ├── MattermostService.java
   |  |           |  |     ├── ParticipantService.java
   |  |           |  |     └── RoomService.java
   |  |           |  ├── shop
   |  |           |  |  ├── controller
   |  |           |  |  |  ├── MenuCategoryController.java
   |  |           |  |  |  ├── MenuController.java
   |  |           |  |  |  ├── OptionCategoryController.java
   |  |           |  |  |  ├── OptionController.java
   |  |           |  |  |  └── ShopController.java
   |  |           |  |  ├── dto
   |  |           |  |  |  ├── request
   |  |           |  |  |  └── response
   |  |           |  |  ├── entity
   |  |           |  |  |  ├── Menu.java
   |  |           |  |  |  ├── MenuCategory.java
   |  |           |  |  |  ├── Option.java
   |  |           |  |  |  ├── OptionCategory.java
   |  |           |  |  |  └── Shop.java
   |  |           |  |  ├── exception
   |  |           |  |  |  ├── ShopErrorCode.java
   |  |           |  |  |  └── ShopException.java
   |  |           |  |  ├── mapper
   |  |           |  |  |  ├── MenuCategoryMapper.java
   |  |           |  |  |  ├── MenuMapper.java
   |  |           |  |  |  ├── OptionCategoryMapper.java
   |  |           |  |  |  ├── OptionMapper.java
   |  |           |  |  |  └── ShopMapper.java
   |  |           |  |  ├── repository
   |  |           |  |  |  ├── MenuCategoryRepository.java
   |  |           |  |  |  ├── MenuRepository.java
   |  |           |  |  |  ├── OptionCategoryRepository.java
   |  |           |  |  |  ├── OptionRepository.java
   |  |           |  |  |  └── ShopRepository.java
   |  |           |  |  └── service
   |  |           |  |     ├── MenuCategoryService.java
   |  |           |  |     ├── MenuService.java
   |  |           |  |     ├── OptionCategoryService.java
   |  |           |  |     ├── OptionService.java
   |  |           |  |     ├── S3Service.java
   |  |           |  |     └── ShopService.java
   |  |           |  └── user
   |  |           |     ├── controller
   |  |           |     |  └── ManagerController.java
   |  |           |     ├── dto
   |  |           |     |  ├── request
   |  |           |     |  └── response
   |  |           |     ├── entity
   |  |           |     |  ├── User.java
   |  |           |     |  └── UserAuthority.java
   |  |           |     ├── exception
   |  |           |     |  ├── UserErrorCode.java
   |  |           |     |  └── UserException.java
   |  |           |     ├── mapper
   |  |           |     |  └── UserMapper.java
   |  |           |     ├── repository
   |  |           |     |  └── UserRepository.java
   |  |           |     └── service
   |  |           |        └── ManagerService.java
   |  |           ├── jwt
   |  |           |  ├── dto
   |  |           |  |  └── JwtPrincipalInfo.java
   |  |           |  ├── exception
   |  |           |  |  ├── JwtTokenErrorCode.java
   |  |           |  |  └── JwtTokenException.java
   |  |           |  ├── JwtAuthenticationFilter.java
   |  |           |  ├── JwtProps.java
   |  |           |  ├── JwtTokenProvider.java
   |  |           |  └── repository
   |  |           |     └── RefreshTokenRepository.java
   |  |           ├── oauth
   |  |           |  ├── HttpCookieOAuth2RequestRepository.java
   |  |           |  ├── OAuth2RequestResolver.java
   |  |           |  ├── Oauth2Service.java
   |  |           |  ├── OAuth2SuccessHandler.java
   |  |           |  └── OAuth2UserImpl.java
   |  |           └── SsafeeApplication.java
   |  └── resources
   |     ├── application.yml
   |     ├── data.sql
   |     └── schema.sql
   └── test
      └── java
         └── coffee
            └── ssafy
               └── ssafee
                  └── SsafeeApplicationTests.java
```
</details>

## 시스템 아키텍처

![시스템아키텍처](https://github.com/ssafee-team/ssafee/assets/20319256/ab1df058-b1ad-4bcf-973f-9ffeb18c5796)

## 화면 정의서

### 1. WireFrame

![화면정의서 와이어프레임](https://github.com/ssafee-team/ssafee/assets/20319256/29bc3141-8a3d-492d-a2c7-a44ea619ba03)

### 2. Prototype

![화면정의서 프로토타입](https://github.com/ssafee-team/ssafee/assets/20319256/5be57724-3d6b-4e18-82bc-8008a2379e16)

### 3.1. Deploy User

![화면정의서 정식 사용자](https://github.com/ssafee-team/ssafee/assets/20319256/c3c3a5ec-5fd4-4f7f-a111-8b5e984deff2)

### 3.2. Deploy Cafe

![화면정의서 정식 카페](https://github.com/ssafee-team/ssafee/assets/20319256/c3461f56-7580-40bf-ae60-a4abd513f4bb)

## ERD

![ERD](https://github.com/ssafee-team/ssafee/assets/20319256/5eea8027-9609-435c-aa46-fc5f1e18e850)

## 서비스 소개

### 1. 사용자

- 파티 생성
  - 소셜 로그인(Google)
  - 파티 생성
  - 생성을 위한 정보 입력
  - 주문 플랫폼 선택
- 주문
- 메뉴 선택
  - 실시간 익명 채팅
  - 주문 요청(파티 생성자)
  - 배달 알림(파티 생성자)
  - 송금 요청(파티 생성자)

### 2. 카페

- 로그인
  - 최초 이용 시 회원가입
- 메인 화면
  - 주문 대기
  - 주문 요청
    - Media Alarm 재생
- 주문 접수 화면
  - 주문 상세 정보
      - 제조 완료 알림 전송
      - 배달 출발 알림 전송
      - 제조된 메뉴 상태 변경(UX)

---

- 메인 화면 (현재 모집 중인 파티를 확인)
  ![메인화면](https://github.com/ssafee-team/ssafee/assets/20319256/c24aa7c9-2be3-480e-b4b7-0457f5f83747)

- 방 생성

  ![방 생성](https://github.com/ssafee-team/ssafee/assets/20319256/1b25d063-28a7-4d4b-9f83-27a26e479156)

- 파티
  ![방 입장 1 기본](https://github.com/ssafee-team/ssafee/assets/20319256/a6b992a3-50ae-432f-8086-9d66a3b793c5)

  ![방 입장 2 메뉴 옵션 선택 1](https://github.com/ssafee-team/ssafee/assets/20319256/99442286-cc03-4ccb-a003-4997e942a1a2)

  ![방 입장 2 메뉴 옵션 선택 2](https://github.com/ssafee-team/ssafee/assets/20319256/e6fb699a-f481-4226-a621-a45ed70d32da)

  ![방 입장 3 메뉴카테고리 채팅](https://github.com/ssafee-team/ssafee/assets/20319256/83ac8ea6-3327-4e21-94b8-a01fda773496)

  ![방 입장 4 주문자입력](https://github.com/ssafee-team/ssafee/assets/20319256/6461a067-7039-4e4a-a894-19647c8890b7)

  ![방 입장 4 주문자입력 2](https://github.com/ssafee-team/ssafee/assets/20319256/836dc229-07ef-4014-8fac-87599135618d)

  ![방 입장 5 주문신청 AFTER](https://github.com/ssafee-team/ssafee/assets/20319256/58203574-1c8c-4ff5-b163-77ca6fc3445e)

  ![방 입장 6 입금 체크](https://github.com/ssafee-team/ssafee/assets/20319256/3d8b0fb8-7a42-4f05-b3eb-ccdc77622b3d)

### 3. Manager

![매니저 메인](https://github.com/ssafee-team/ssafee/assets/20319256/cc02d05a-a967-4dca-b838-0246523aa138)

![매니저 2 주문대기](https://github.com/ssafee-team/ssafee/assets/20319256/d5ad5855-85bb-4084-91f9-7208a67d7b07)

![매니저 3 주문 받음](https://github.com/ssafee-team/ssafee/assets/20319256/bc33ae6b-cf3a-424c-9bf1-29d1f9d4b087)

![매니저 4 1 주문 접수](https://github.com/ssafee-team/ssafee/assets/20319256/9665dfb2-9ab5-4ed7-92e5-98150e5e9d3b)

### 4. Mattermost

- 3-1. 파티 생성 시

  ![MM 1 방생성](https://github.com/ssafee-team/ssafee/assets/20319256/5b0ceeac-2a8c-4fb3-a452-bcf96f27e95f)

- 3-2. 주문 마감 시

  ![MM 2 배달원](https://github.com/ssafee-team/ssafee/assets/20319256/860413c9-aecd-40f7-b3f2-25b685514450)

- 3-3. 미정산 송금 요청 시

  ![MM 3 송금요청](https://github.com/ssafee-team/ssafee/assets/20319256/2b2cc09e-4379-44ac-942c-c155613b2423)

- 3-4. 배달 도착 시

  ![MM 4 배달 도착 알림](https://github.com/ssafee-team/ssafee/assets/20319256/6b60a6de-8d8f-437e-8a3a-22cf8175ac9f)
