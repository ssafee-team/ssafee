# <img src="https://github.com/ssafee-team/ssafee/assets/20319256/75482caa-83fa-4e95-a19d-b66d8510ffa3" width="50" height="50"> SSAFEE - 쉽고 편리한 커피 주문

![header](https://capsule-render.vercel.app/api?type=waving&color=gradient&height=180&section=header&text=%E2%98%95%EF%B8%8F%20Tactical%20Team%20Project&fontSize=32&animation=fadeIn&fontAlignY=36&fontColor=ffffff)

---

# 프로젝트 개요
### 개발 기간
|개발 기간|2024.01.03 ~ 2024.02.16|
|---|---|

### 프로젝트 소개
SSAFEE는 사업장 내에서 커피주문 및 수령에 어려움을 겪는 SSAFY인들의 불편을 해소하고, 나아가 자주 주문하는 카페와 배달 플랫폼을 배제하고 직접 주문을 연결하여 소비자와 판매자가 모두 이익을 보는, **편의성**과 **지역 상생**을 도모한 프로젝트 입니다. 

### 팀 소개
**"☕ 불편한 문제를 모두에게 편리하도록 풀어내는 서비스"**

|팀원|역할|
|---|---|
|👼박희찬|팀장, BE|
|👶고영훈|BE|
|😁양희승|BE|
|👩전상혁|FE|
|😄주홍찬|FE|
|👦강찬우|FE|



# 🔨 기술 스택 🔨
<div style="display:flex; flex-direction:column; align-items:flex-start;">
    <!-- Backend -->
    <p><strong>Backend</strong></p>
    <div>
        <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"> 
        <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
    </div>
    <!-- Database -->
    <p><strong>Database</strong></p>
    <div>
        <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
    </div>
    <!-- Server -->
    <p><strong>Server</strong></p>
    <div>
        <img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=amazon aws&logoColor=white"> 
    </div>
    <!-- Frontend -->
    <p><strong>Frontend</strong></p>
    <div>
    <img src="https://img.shields.io/badge/Vue-4FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white"/>
        <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
        <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
        <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
        <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
    </div>
    <!-- CI/CD -->
    <p><strong>CI/CD</strong></p>
    <div>
        <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
        <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white"/>
        <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
        <img src="https://img.shields.io/badge/nodejs-339933?style=for-the-badge&logo=nodedotjs&logoColor=white"/>
</div><br>
<!-- Others -->
    <p><strong>Others</strong></p>
    <div>
        <img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white"/>
        <img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jirasoftware&logoColor=white"/>
        
</div><br>
</div>

<hr>

# 프로젝트 파일 구조
### Frontend
```ini
frontend
 ┣ .vscode
 ┃ ┣ extensions.json
 ┃ ┗ settings.json
 ┣ public
 ┃ ┗ vite.svg
 ┣ src
 ┃ ┣ api
 ┃ ┃ ┣ after.js
 ┃ ┃ ┣ manager.js
 ┃ ┃ ┣ party.js
 ┃ ┃ ┣ realtime.js
 ┃ ┃ ┗ shop.js
 ┃ ┣ assets
 ┃ ┃ ┣ img
 ┃ ┃ ┃ ┣ coffeebrand
 ┃ ┃ ┃ ┃ ┣ 공차.jpg
 ┃ ┃ ┃ ┃ ┣ 백다방커피.jpg
 ┃ ┃ ┃ ┃ ┗ 컴포즈드커피.jpg
 ┃ ┃ ┃ ┣ delivery
 ┃ ┃ ┃ ┃ ┣ 배달플랫폼.jpg
 ┃ ┃ ┃ ┃ ┣ 배민.jpg
 ┃ ┃ ┃ ┃ ┣ 요기요.jpg
 ┃ ┃ ┃ ┃ ┗ 쿠팡이츠.jpg
 ┃ ┃ ┃ ┣ LOGO.png
 ┃ ┃ ┃ ┣ logo_compose.png
 ┃ ┃ ┃ ┣ logo_ssaffe_2nd.png
 ┃ ┃ ┃ ┗ send.png
 ┃ ┃ ┗ music.mp3
 ┃ ┣ components
 ┃ ┃ ┣ after
 ┃ ┃ ┃ ┣ AfterCart.vue
 ┃ ┃ ┃ ┣ CarrierList.vue
 ┃ ┃ ┃ ┣ Info.vue
 ┃ ┃ ┃ ┗ OrderList.vue
 ┃ ┃ ┣ common
 ┃ ┃ ┃ ┣ CreateHeader.vue
 ┃ ┃ ┃ ┣ MainHeader.vue
 ┃ ┃ ┃ ┣ ManagerHeader.vue
 ┃ ┃ ┃ ┗ ManagerSidebar.vue
 ┃ ┃ ┣ icons
 ┃ ┃ ┃ ┣ IconCommunity.vue
 ┃ ┃ ┃ ┣ IconDocumentation.vue
 ┃ ┃ ┃ ┣ IconEcosystem.vue
 ┃ ┃ ┃ ┣ IconSupport.vue
 ┃ ┃ ┃ ┗ IconTooling.vue
 ┃ ┃ ┣ manager
 ┃ ┃ ┃ ┣ ManagerInfo.vue
 ┃ ┃ ┃ ┣ ManagerLogin.vue
 ┃ ┃ ┃ ┣ ManagerMain.vue
 ┃ ┃ ┃ ┣ ManagerModal.vue
 ┃ ┃ ┃ ┣ ManagerOrderList.vue
 ┃ ┃ ┃ ┗ ManagerShop.vue
 ┃ ┃ ┣ room
 ┃ ┃ ┃ ┣ modal
 ┃ ┃ ┃ ┃ ┣ OrderListModal.vue
 ┃ ┃ ┃ ┃ ┗ OrderModal.vue
 ┃ ┃ ┃ ┣ Cart.vue
 ┃ ┃ ┃ ┣ Chat.vue
 ┃ ┃ ┃ ┣ MenuList.vue
 ┃ ┃ ┃ ┗ OrderSummary.vue
 ┃ ┃ ┗ WelcomeItem.vue
 ┃ ┣ router
 ┃ ┃ ┗ index.ts
 ┃ ┣ stores
 ┃ ┃ ┗ counter.ts
 ┃ ┣ util
 ┃ ┃ ┣ http-commons.ts
 ┃ ┃ ┗ http-status.ts
 ┃ ┣ views
 ┃ ┃ ┣ manager
 ┃ ┃ ┃ ┣ ManagerInfo.vue
 ┃ ┃ ┃ ┣ ManagerLogin.vue
 ┃ ┃ ┃ ┣ ManagerMain.vue
 ┃ ┃ ┃ ┣ ManagerMenu.vue
 ┃ ┃ ┃ ┣ ManagerMenuCategory.vue
 ┃ ┃ ┃ ┣ ManagerOption.vue
 ┃ ┃ ┃ ┣ ManagerOptionCategory.vue
 ┃ ┃ ┃ ┣ ManagerOrderList.vue
 ┃ ┃ ┃ ┣ ManagerShop.vue
 ┃ ┃ ┃ ┗ ManagerShopInfo.vue
 ┃ ┃ ┣ TheAfterView.vue
 ┃ ┃ ┣ TheCreateRoomView.vue
 ┃ ┃ ┣ TheLoginOAuth2View.vue
 ┃ ┃ ┣ TheMainPageView.vue
 ┃ ┃ ┣ TheManagerView.vue
 ┃ ┃ ┗ TheRoomView.vue
 ┃ ┣ App.vue
 ┃ ┣ main.ts
 ┃ ┗ vite-env.d.ts
 ┣ .gitignore
 ┣ Dockerfile
 ┣ eslint.config.js
 ┣ index.html
 ┣ package-lock.json
 ┣ package.json
 ┣ README.md
 ┣ tsconfig.json
 ┣ tsconfig.node.json
 ┗ vite.config.ts
```

### Backend
```ini
backend
 ┣ gradle
 ┃ ┗ wrapper
 ┃ ┃ ┣ gradle-wrapper.jar
 ┃ ┃ ┗ gradle-wrapper.properties
 ┣ src
 ┃ ┣ main
 ┃ ┃ ┣ java
 ┃ ┃ ┃ ┗ coffee
 ┃ ┃ ┃ ┃ ┗ ssafy
 ┃ ┃ ┃ ┃ ┃ ┗ ssafee
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ common
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ BaseTimeEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CookieUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ DateUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ OriginProps.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ JpaAuditConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ProblemConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ S3Config.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ StompWebSocketConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ chat
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChatController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ChatStompController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ChatRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ChatResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ Chat.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ChatMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ChatRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ChatService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ party
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyOrderController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceOptionCategoryRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreatorRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OrderRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantUpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CreatorResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyDetailResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyOrderCreateResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyStatusResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyOrderCreateInfo.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyStatusInfo.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenu.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuOption.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuOptionCategory.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Creator.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Participant.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ Party.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyErrorCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyOrderMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuOptionCategoryRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuOptionRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChoiceMenuService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MatterMostService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ParticipantService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyOrderService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ PartyService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ shop
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategoryController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategoryController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ShopController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ShopOrderController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategoryRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategoryRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ShopRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategoryDetailResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategoryResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuDetailResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategoryDetailResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategoryResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ PartyDetailForManagerResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ShopDetailResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ShopResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Menu.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategory.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Option.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategory.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ Shop.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ShopErrorCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ShopException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategoryMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategoryMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ShopMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ShopOrderMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategoryRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategoryRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ShopRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuCategoryService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ MenuService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionCategoryService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OptionService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ S3Service.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ShopOrderService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ShopService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ManagerController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ManagerLoginRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ManagerUpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ManagerResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Manager.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ManagerErrorCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ManagerException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ mapper
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ManagerMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ManagerRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ ManagerService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ jwt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ JwtPrincipalInfo.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ JwtTokenErrorCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ JwtTokenException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ RefreshTokenRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ JwtAuthenticationFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ JwtProps.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ JwtTokenProvider.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ oauth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ HttpCookieOAuth2RequestRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OAuth2RequestResolver.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OAuth2SuccessHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ OAuth2UserImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ SsafeeApplication.java
 ┃ ┃ ┗ resources
 ┃ ┃ ┃ ┗ application.yml
 ┃ ┗ test
 ┃ ┃ ┗ java
 ┃ ┃ ┃ ┗ coffee
 ┃ ┃ ┃ ┃ ┗ ssafy
 ┃ ┃ ┃ ┃ ┃ ┗ ssafee
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ SsafeeApplicationTests.java
 ┣ .gitignore
 ┣ build.gradle
 ┣ Dockerfile
 ┣ gradlew
 ┣ gradlew.bat
 ┗ settings.gradle
```

# 시스템 아키텍처
![시스템아키텍처](https://github.com/ssafee-team/ssafee/assets/20319256/ab1df058-b1ad-4bcf-973f-9ffeb18c5796)

# 화면 정의서
### 1. WireFrame
![화면정의서_와이어프레임](https://github.com/ssafee-team/ssafee/assets/20319256/29bc3141-8a3d-492d-a2c7-a44ea619ba03)

### 2. Prototype
![화면정의서_프로토타입](https://github.com/ssafee-team/ssafee/assets/20319256/5be57724-3d6b-4e18-82bc-8008a2379e16)

### 3-1. Deploy_User
![화면정의서_정식_사용자](https://github.com/ssafee-team/ssafee/assets/20319256/c3c3a5ec-5fd4-4f7f-a111-8b5e984deff2)

### 3-2. Deploy_Cafe
![화면정의서_정식_카페](https://github.com/ssafee-team/ssafee/assets/20319256/c3461f56-7580-40bf-ae60-a4abd513f4bb)

## ERD
![SSAFEE_ERD](https://github.com/ssafee-team/ssafee/assets/20319256/5eea8027-9609-435c-aa46-fc5f1e18e850)

# 서비스 소개
## 1. 사용자
### 파티 생성
- 소셜 로그인(Google)
- 파티 생성
- 생성을 위한 정보 입력
- 주문 플랫폼 선택

### 주문
- 메뉴 선택
- 실시간 익명 채팅
- 주문 요청(파티 생성자)
- 배달 알림(파티 생성자)
- 송금 요청(파티 생성자)

## 2. 카페
### 로그인
 - 최초 이용 시 회원가입

### 메인 화면
- 주문 대기
- 주문 요청
    - Media Alarm 재생
 
### 주문 접수 화면
- 주문 상세 정보
    - 제조 완료 알림 전송
    - 배달 출발 알림 전송
    - 제조된 메뉴 상태 변경(UX)

<hr>

### 메인 화면
  - 현재 모집 중인 파티를 확인.
![메인화면](https://github.com/ssafee-team/ssafee/assets/20319256/c24aa7c9-2be3-480e-b4b7-0457f5f83747)

### 방 생성
![방 생성](https://github.com/ssafee-team/ssafee/assets/20319256/1b25d063-28a7-4d4b-9f83-27a26e479156)

### 파티
![방입장_1_기본](https://github.com/ssafee-team/ssafee/assets/20319256/a6b992a3-50ae-432f-8086-9d66a3b793c5)

![방입장_2_메뉴_옵션_선택_1](https://github.com/ssafee-team/ssafee/assets/20319256/99442286-cc03-4ccb-a003-4997e942a1a2)

![방입장_2_메뉴_옵션_선택_2](https://github.com/ssafee-team/ssafee/assets/20319256/e6fb699a-f481-4226-a621-a45ed70d32da)

![방입장_3_메뉴카테고리_채팅](https://github.com/ssafee-team/ssafee/assets/20319256/83ac8ea6-3327-4e21-94b8-a01fda773496)

![방입장_4_주문자입력](https://github.com/ssafee-team/ssafee/assets/20319256/6461a067-7039-4e4a-a894-19647c8890b7)

![방입장_4_주문자입력_2](https://github.com/ssafee-team/ssafee/assets/20319256/836dc229-07ef-4014-8fac-87599135618d)

![방입장_5_주문신청_AFTER](https://github.com/ssafee-team/ssafee/assets/20319256/58203574-1c8c-4ff5-b163-77ca6fc3445e)

![방입장_6_입금_체크](https://github.com/ssafee-team/ssafee/assets/20319256/3d8b0fb8-7a42-4f05-b3eb-ccdc77622b3d)

## 2. 카페
![매니저_메인](https://github.com/ssafee-team/ssafee/assets/20319256/cc02d05a-a967-4dca-b838-0246523aa138)

![매니저_2_주문대기](https://github.com/ssafee-team/ssafee/assets/20319256/d5ad5855-85bb-4084-91f9-7208a67d7b07)

![매니저_3_주문_받음](https://github.com/ssafee-team/ssafee/assets/20319256/bc33ae6b-cf3a-424c-9bf1-29d1f9d4b087)

![매니저_4_1_주문_접수](https://github.com/ssafee-team/ssafee/assets/20319256/9665dfb2-9ab5-4ed7-92e5-98150e5e9d3b)

## 3. MatterMost Alarm
### 3-1. 파티 생성 시
![MM_1_방생성](https://github.com/ssafee-team/ssafee/assets/20319256/5b0ceeac-2a8c-4fb3-a452-bcf96f27e95f)

### 3-2. 주문 마감 시
![MM_2_배달원](https://github.com/ssafee-team/ssafee/assets/20319256/860413c9-aecd-40f7-b3f2-25b685514450)

### 3-3. 미정산 송금 요청 시
![MM_3_송금요청](https://github.com/ssafee-team/ssafee/assets/20319256/2b2cc09e-4379-44ac-942c-c155613b2423)

### 3-4. 배달 도착 시
![MM_4_배달_도착_알림](https://github.com/ssafee-team/ssafee/assets/20319256/6b60a6de-8d8f-437e-8a3a-22cf8175ac9f)

# 컨벤션
### Branch
```javascript
- 주요 브랜치 (항상 유지)
    - **main**
        - 항상 배포 가능한 브랜치
    
    - **develop**
        - 배포 브랜치(main)의 다음 버전을 준비하는 브랜치
        - 개발된 모든 코드의 통합 장소
- 보조 브랜치 (임시 생성/머지했으면 삭제)
    - **feature**
        - 특정 기능을 개발하는 브랜치
        - develop 브랜치에서 생성, 기능 개발 후 develop 브랜치로 병합
        - e.g. `feature/backend-login`, `feature/frontend-login`
    
    - **release**
        - 배포 브랜치의 다음 버전을 갱신하기 직전에 QA를 수행하는 브랜치
        - develop 브랜치에서 생성, 테스트 및 버그 수정 후 main 및 develop 브랜치로 병합
        - e.g. `release/0.0.1`, `release/1.0-beta`
    - **hotfix**
        - 배포 브랜치의 현재 버전에서 버그가 생겼을 때 긴급 수정을 위한 브랜치
        - main 브랜치에서 생성, 버그 수정 후 main 및 develop 브랜치로 병합
        - e.g. `hotfix/20240111`, `hotfix/#5`
```
### Commit
- 커밋 메시지 구조
  - 개행 2번씩.
```text
type: <subject>

[body]

[footer]
```

- type
```javascript
- feat: 기능 추가
- fix: 버그 수정
- refactor: 코드 리팩토링
- design: CSS 등 디자인
- style: 코드 스타일 수정
- test: 코드 테스트 관련
- docs: 문서 수정
- chore: 빌드 등 기타 변경사항
- rename: 파일명 수정
- remove: 파일 삭제
```

- subject
```javascript
1. 최대 50글자까지 작성
2. 특수기호 사용 금지 (마침표 등)
3. 영문 작성시 첫 단어 명령문, 첫 글자 대문자
    - e.g. `Add file`
4. 한글 작성시 개조식으로 작성
    - e.g. `파일 추가`
```

- body
```javascript
1. 생략 가능
2. '어떻게'보다는 '무엇을', '왜' 변경했는지 작성
```

- footer
```python
1. 생략 가능
2. Github 연동
3. Fixes #<이슈번호>
4. Resolves #<이슈번호>
5. Ref #<이슈번호>
6. Related to #<이슈번호>
7. e.g. `Fixes: #47`, `Related to: #32, 21`
```




