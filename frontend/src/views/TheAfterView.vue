<template>
  <main>
    <!-- <header :style="{ height: headerHeight }"> -->
    <header :style="{ height: 72 }">
      <div class="timeline">
        <div class="time">마감시간</div>
        <!-- last_order_time -->
        <!-- <div>11:30</div> -->
        <div class="time">{{ partyInfo.last_order_time }}</div>
      </div>
      <div class="center-content">
        <div>{{ partyInfo.name }}</div>
      </div>
      <div class="timeline">
        <div class="time">잔여시간</div>
        <!-- <div style="color: red">32:18</div> -->
        <div style="color: red" class="time">{{ remainingTime }}</div>
      </div>
    </header>
    <body>
      <!-- <div class="center-content" style="margin-top: 25px">
        <button @click="checkOrderStatus">현재 주문현황 확인하기</button>
      </div> -->
      <Info 
        :creator="creator"
      />
      <!-- Body 화면 6:4 비율로 분할 -->
      <div class="body-container">
        <div class="left-panel">
          <OrderList
            :orders="orders"
            :name-set="nameSet"
            :orders-menu-sorted="ordersMenuSorted"
          />
          <!-- <div>메뉴가 들어갈 부분</div> -->
          <!-- <MenuList/> -->
          <!-- 여기에 내 담당을 추가한다 -->
          <!-- 왼쪽 컨텐츠 (6:4 중 6 부분) -->
          <!-- 추가적인 내용이 들어갈 수 있습니다. -->
        </div>
        <div class="right-panel">
          <!-- <div>채팅창</div> -->
          <chat />
          <!-- 오른쪽 컨텐츠 (6:4 중 4 부분) -->
          <!-- 추가적인 내용이 들어갈 수 있습니다. -->
        </div>
      </div>
    </body>
  </main>
</template>

<script setup>
//   import MenuList from '@/components/room/MenuList.vue';
//   const checkOrderStatus = () => {
//     // 주문 현황 확인 로직을 추가할 수 있습니다.
//     console.log("주문 현황 확인하기 버튼이 클릭되었습니다.");
//   };
  import { ref, onMounted, onUnmounted } from "vue";
  import Info from '@/components/after/Info.vue';
  import OrderList from '@/components/after/OrderList.vue';
  import Chat from "@/components/room/chat/Chat.vue";
  import { useRoute } from 'vue-router';
  import { getCreator, getOrderList } from '@/api/after.js'
  import { getParty } from "@/api/party";

  const route = useRoute();
 
  const deadLine = ref("11:30"); //마감시간 백에서 받아오고 임의 설정
  const remainingTime = ref(""); //남은시간

  // 헤더 높이를 저장하는 변수
  const headerHeight = ref("");

  // 화면 크기가 변경될 때마다 헤더 높이를 업데이트하는 함수
  const updateHeaderHeight = () => {
    headerHeight.value = `${document.querySelector("header").offsetHeight}px`;
  };


  const creator = ref({
    id: "",
    name: "",
    bank: "",
    account: "",
  })
  const orders = ref([])
  const ordersMenuSorted = ref([])
  const menuSet = new Set();
  const nameSet = new Set();
  
  const partyInfo = ref({
  id: "",
  name: "",
  generation: "",
  classroom: "",
  last_order_time: "",
  created_time: "",
  shop_id: "",
  creator: {
    id: "",
    name: "",
    email: "",
    bank: "",
    account: "",
  },
});
  // 컴포넌트가 마운트될 때와 언마운트될 때 이벤트 리스너 추가/제거
  onMounted(() => {
    // console.log(route.params.access_code);
    getCreator(route.params.access_code, 
    (res) => {
      creator.value.id = res.data.creator.id;
      creator.value.name = res.data.creator.name;
      creator.value.bank = res.data.creator.bank;
      creator.value.account = res.data.creator.account;
    }, 
    (error)=>{
      console.log(error)
    });
    

    getOrderList(route.params.access_code, 
    (res) => {
      // console.log(res.data)
      
      // console.log(res.data)
      res.data.forEach(elem => {
        // console.log('주문자:', elem.participant_name)        
        nameSet.add(elem.participant_name);
        menuSet.add(elem.chosen_menu.name);

        let optionPrice = 0;
        let options = [];
        elem.chosen_option_categories.forEach(option_categories => {
          option_categories.chosen_options.forEach(option => {
            options.push({id:option.id, name:option.name, price:option.price})
            optionPrice += option.price
          })      
        })

        // console.log("정렬 전 옵션",options)
        // console.log("정렬된옵션:", options)
        options.sort((a,b) => a.id - b.id)
        // console.log("정렬 전 옵션", options)
        let itCat = ''
        options.forEach((opt) => {itCat += (opt.id + '_')})
        itCat = itCat === "" ? "0" : itCat.substring(0,itCat.length-1)
        // console.log(itCat)
        options.unshift(1)
        options.unshift(itCat)

        orders.value.push({
          classNo: 2,
          studentName: elem.participant_name,
          menuName: elem.chosen_menu.name,
          menuPrice: elem.chosen_menu.price + optionPrice,
          menuPriceNoOptions: elem.chosen_menu.price,
          menuOptions: options,
        })

        
      });

      // 반복문 이후
      // console.log("개발시작")
      menuSet.forEach(menu => {
        // console.log(menu)
        // 개별메뉴 숫자
        let optionsTemp = [];
        let sortedByMenu = orders.value.filter(order => order.menuName === menu);
        // sortedByMenu 에서 options 순회
        // option이 tempOptions에 없으면 추가, 있으면 수량만 +1
        let optionsSet = new Set();
        let sumMenuPrice = 0;
        sortedByMenu.forEach((order)=>{
          sumMenuPrice += order.menuPrice
          // console.log(order.menuPrice)
          // console.log('우우웅:',order.menuOptions[0])
          // console.log
          // order.menuOptions[0] 번이 없으면 추가 있으면 위치를 찾아 count += 1
          if (!optionsSet.has(order.menuOptions[0])) {
            optionsSet.add(order.menuOptions[0])
            optionsTemp.push(order.menuOptions)
            // console.log(optionsSet)
          } else {
            let idx = optionsTemp.findIndex(elem => elem[0] === order.menuOptions[0])
            // console.log("idx:",idx)
            optionsTemp[idx][1] += 1
          }
          
          // order.menuOptions.forEach((option)=>{
          //   console.log("옵션이름:", option.name)
          //   if (!temp1.includes(option.name)) {
          //     temp1.push(option.name)
          //     optionsTemp.push(option)
          //   } else {
          //     console.log('부힛 부히힛:',temp1.indexOf(option.name))
          //   }
          //   optionsTemp.push(option)
          // })

        })
        optionsTemp.sort((a,b)=>a[0].localeCompare(b[0]))
        // console.log('adfasdzcxv',optionsTemp[0])


        ordersMenuSorted.value.push(
          {
            sumMenuPrice: sumMenuPrice,
            menuName: menu,
            menuCount: sortedByMenu.length,
            options: optionsTemp,
          }
        )

      });
      // console.log("orders:", orders.value)
      // console.log("메뉴기준정렬",ordersMenuSorted.value)

      // console.log(orders.value)
      // console.log(nameSet);
      // console.log(menuSet);
      // console.log(optionsSet);
    }, 
    (error) => {
      console.log(error)
    });




    updateHeaderHeight();
    window.addEventListener("resize", updateHeaderHeight);
    updateRemainingTime(); //페이지 로드시 남은시간 계산
    // 1초마다 남은시간 갱신
    setInterval(updateRemainingTime, 1000);
    getPartyInfo();
  });

  onUnmounted(() => {
    window.removeEventListener("resize", updateHeaderHeight);
    
  });


  const getPartyInfo = () => {
    // console.log('파티피플')
  getParty(
    route.params.access_code,

    ({ data }) => {
      console.log(data);
      partyInfo.value.id = data.id;
      partyInfo.value.name = data.name;
      partyInfo.value.generation = data.generation;
      partyInfo.value.classroom = data.classroom;
      partyInfo.value.last_order_time = data.last_order_time;
      partyInfo.value.created_time = data.created_time;
      partyInfo.value.shop_id = data.shop_id;
      // console.log('파티정보:',partyInfo);
    },
    (error) => {
      console.log(error);
    }
  );
};
// 남은시간 갱신하는 함수 호출
  const updateRemainingTime = () => {
    const now = new Date(); //현재시간 변수
    const deadlineTimne = new Date();
    const [hours, minutes] = deadLine.value.split(":").map(Number);

    deadlineTimne.setHours(hours, minutes, 0);

    //마감시간에서 현재시간 차이를 저장
    const diff = deadlineTimne - now;

    if (diff > 0) {
      const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((diff % (1000 * 60)) / 1000);

      remainingTime.value = `${hours} : ${minutes} : ${seconds}`;
    } else {
      remainingTime.value = "마감";
    }
  };
</script>



<style scoped>
body {
  /* margin: 20px; */
  /* padding: 20px; */
  font-family: "Arial", sans-serif;
}
main {
  display: flex;
  flex-direction: column;
  /* height: 100vh; */
  height: auto;
  overflow-x: hidden;
}

header {
  background-color: #344a53;
  color: #e9fcff;
  /* padding: 10px; */
  min-height: 70px;
  display: flex;
  font-size: 24px;
  justify-content: space-between;
  align-items: center;
}
.timeline {
  display: flex;
  font-size: 24px;
  margin: 20px;
  font-weight: bold;
}
.time {
  margin-right: 10px;
}
.center-content {
  text-align: center;
  /* font-size: 40px; */
  font-size: 24px;
  flex-grow: 1;
  font-weight: bold;
}
.order-status {
  display: flex;
  align-items: center;
  text-align: center;
  color: #00a7d0;
  font-size: 26px;
}
button {
  padding: 10px;
  background-color: white;
  border: none;
  color: #00a7d0;
  font-size: 22px;
  font-weight: bold;
  text-decoration: underline;
  cursor: pointer;
}
.body-container {
  display: flex;
  /* flex: 6; */
  /* margin-top: 25px; */
}
.left-panel {
  flex: 7; /* 6:4 비율로 나누기 위한 설정 */
  /* height: 75vh; */
  height: auto;
  
  /* border: 5px solid #ccc; */
}
.right-panel {
  flex: 3;
  margin-left: 20px; /* 왼쪽과 오른쪽 패널 간격 설정 */
  /* border: 5px solid #ccc; */
  height: auto;
}
</style>
