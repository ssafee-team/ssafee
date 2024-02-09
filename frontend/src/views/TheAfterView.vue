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
            <div class="center-title">
                <div>{{ partyInfo.name }}</div>
            </div>
            <div class="timeline">
                <div class="time">잔여시간</div>
                <div style="color: red" class="time">{{ remainingTime }}</div>
            </div>
        </header>
        <body>
            <Info :creator="creator" />
            <!-- Body 화면 6:4 비율로 분할 -->
            <div class="body-container">
                <div class="left-panel">
                    <OrderList
                        :orders="orders"
                        :name-set="nameSet"
                        :orders-menu-sorted="ordersMenuSorted"
                        :carriers="carriers"
                        :participants="participants"
                        :carriers-arr="carriersArr"
                        :payers="payers"
                    />
                    <!-- <div>메뉴가 들어갈 부분</div> -->
                    <!-- <MenuList/> -->
                    <!-- 여기에 내 담당을 추가한다 -->
                    <!-- 왼쪽 컨텐츠 (6:4 중 6 부분) -->
                    <!-- 추가적인 내용이 들어갈 수 있습니다. -->
                </div>
                <div class="right-panel">
                    <!-- <div>채팅창</div> -->
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
import Info from "@/components/after/Info.vue";
import OrderList from "@/components/after/OrderList.vue";
import { useRoute } from "vue-router";
import { getCreator, getOrderList, getParticipants } from "@/api/after.js";
import { getParty } from "@/api/party";

const route = useRoute();
const access_code = ref("");
// const deadLine = ref("11:30"); //마감시간 백에서 받아오고 임의 설정
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
});
const orders = ref([]);
const ordersMenuSorted = ref([]);
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

const participants = ref([]);
const carriers = ref([]);
const carriersArr = ref([]);
const payers = ref([]);
// 컴포넌트가 마운트될 때와 언마운트될 때 이벤트 리스너 추가/제거
onMounted(() => {
    // console.log(window.location.host)
    access_code.value = route.params.access_code;
    // console.log(access_code.value)
    getCreator(
        access_code.value,
        (res) => {
            creator.value.id = res.data.creator.id;
            creator.value.name = res.data.creator.name;
            creator.value.bank = res.data.creator.bank;
            creator.value.account = res.data.creator.account;
        },
        (error) => {
            console.log(error);
        }
    );

    getParticipants(
        access_code.value,
        (res) => {
            res.data.forEach((p) => {
                participants.value.push({
                    id: p.id,
                    name: p.name,
                    is_carrier: p.is_carrier,
                    paid: p.paid,
                });
            });
            // console.log(participants.value)
            // console.log(res.data)
            // participants.value.forEach(p=>console.log('p:',p.is_carrier))
            carriers.value = participants.value.filter((participant) => participant.is_carrier);
            carriers.value.forEach((carriers) => carriersArr.value.push(carriers.name));
            // let pp = res.data.filter((participant) => participant.is_carrier===true)
            // console.log("carriers:"", carriers)

            participants.value.filter((participant) => participant.paid).forEach((x) => payers.value.push(x.name));
            // console.log("payers:", payers.value)
        },
        (error) => {
            console.log(error);
        }
    );
    // getOrderList(route.params.access_code,
    getOrderList(
        access_code.value,
        (res) => {
            res.data.forEach((elem) => {
                nameSet.add(elem.participant_name);
                menuSet.add(elem.chosen_menu.name);

                let optionPrice = 0;
                let options = [];
                elem.chosen_option_categories.forEach((option_categories) => {
                    option_categories.chosen_options.forEach((option) => {
                        options.push({ id: option.id, name: option.name, price: option.price });
                        optionPrice += option.price;
                    });
                });

                options.sort((a, b) => a.id - b.id);
                let itCat = "";
                options.forEach((opt) => {
                    itCat += opt.id + "_";
                });
                itCat = itCat === "" ? "0" : itCat.substring(0, itCat.length - 1);
                // console.log(itCat)
                options.unshift(1);
                options.unshift(itCat);

                orders.value.push({
                    classNo: 2,
                    studentName: elem.participant_name,
                    menuName: elem.chosen_menu.name,
                    menuPrice: elem.chosen_menu.price + optionPrice,
                    menuPriceNoOptions: elem.chosen_menu.price,
                    menuOptions: options,
                });
            });

            // return arr.filter((x) => x === 1).concat(arr.filter((x) => x !== 1));
            // orders2.value = orders.value.filter((order=> carriersArr.value.includes(order)).concat(orders.value.filter((order=> !carriersArr.value.includes(order)))))
            orders.value = orders.value
                .filter((order) => carriersArr.value.includes(order.studentName))
                .concat(orders.value.filter((order) => !carriersArr.value.includes(order.studentName)));
            // console.log(orders)
            // 반복문 이후
            // console.log("개발시작")
            menuSet.forEach((menu) => {
                // console.log(menu)
                // 개별메뉴 숫자
                let optionsTemp = [];
                let sortedByMenu = orders.value.filter((order) => order.menuName === menu);
                // sortedByMenu 에서 options 순회
                // option이 tempOptions에 없으면 추가, 있으면 수량만 +1
                let optionsSet = new Set();
                let sumMenuPrice = 0;
                sortedByMenu.forEach((order) => {
                    sumMenuPrice += order.menuPrice;
                    // order.menuOptions[0] 번이 없으면 추가 있으면 위치를 찾아 count += 1
                    if (!optionsSet.has(order.menuOptions[0])) {
                        optionsSet.add(order.menuOptions[0]);
                        optionsTemp.push(order.menuOptions);
                        // console.log(optionsSet)
                    } else {
                        let idx = optionsTemp.findIndex((elem) => elem[0] === order.menuOptions[0]);
                        optionsTemp[idx][1] += 1;
                    }
                });
                optionsTemp.sort((a, b) => a[0].localeCompare(b[0]));
                // console.log('adfasdzcxv',optionsTemp[0])

                ordersMenuSorted.value.push({
                    sumMenuPrice: sumMenuPrice,
                    menuName: menu,
                    menuCount: sortedByMenu.length,
                    options: optionsTemp,
                });
            });
        },
        (error) => {
            console.log(error);
        }
    );

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
        access_code.value,

        ({ data }) => {
            // console.log(data);
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

    // const [hours, minutes] = deadLine.value.split(":").map(Number);
    const [hours, minutes] = partyInfo.value.last_order_time.split(":").map(Number);
    deadlineTimne.setHours(hours, minutes, 0);

    //마감시간에서 현재시간 차이를 저장
    const diff = deadlineTimne - now;

    if (diff > 0) {
        const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((diff % (1000 * 60)) / 1000);

        remainingTime.value = `${hours < 10 ? "0" + hours : hours} : ${minutes < 10 ? "0" + minutes : minutes} : ${
            seconds < 10 ? "0" + seconds : seconds
        }`;
    } else if (diff <= 0) {
        remainingTime.value = "마감";
    }
};
</script>

<style scoped>
body {
    font-family: "Arial", sans-serif;
}
main {
    display: flex;
    flex-direction: column;
    height: auto;
    overflow-x: hidden;
}

header {
    background-color: #344a53;
    color: #e9fcff;
    min-height: 70px;
    display: flex;
    font-size: 24px;
    justify-content: space-between;
    align-items: center;
}
.timeline {
    display: flex;
    /* font-size: 24px; */
    margin: 20px;
    font-weight: bold;
}
.time {
    width: auto;
    margin-right: 10px;
}
.center-title {
    text-align: center;
    /* font-size: 24px; */
    /* flex-grow: 1; */
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
    font-size: 18px;
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

/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {
    header {
        font-size: 18px; /* 화면이 작을 때 텍스트 크기 조절 */
    }
    .body-container {
        flex-direction: column;
    }
    .right-panel {
        margin-left: 0;
        margin-top: 20px;
    }

    .btn-curorder {
        font-size: 16px;
    }
}
</style>
