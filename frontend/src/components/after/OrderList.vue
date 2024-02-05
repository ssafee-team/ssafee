<template>
	<div class="menulist">
		<!-- <div style="width: 100%; height: 200px; z-index: 900; background:rgba(0, 0, 0, 0.2);  top:100px;  left: 200px; position:fixed"> -->
			<!-- <div style="width:100%; height: 100px; z-index: 0; background-color: red; position:fixed">
			</div> -->
		<!-- </div> -->
		<div v-if="selectedStudentNo >= 0" class="black-bg">
				<div class="white-bg">
					<!-- <div id="aa" style="background-color: aqua;  width: 100%; height: 40px; border: 1px dashed black display: block">
						<div style="background-color: blue; width: 80%; height: 40px; box-sizing: border-box; line-height: 40px;">dddddd</div>
						<div style="background-color: red; width: 10%; height: 40px;">dd</div>
					</div> -->
					<div style="  width: 100%; height: 30px; display: flex; transform: translateY(-5px);">
						<div style="font-size: 20px; width: 80%; line-height: 30px; text-align: left; padding-left: 10px;">{{ isInit? this.orders[selectedStudentNo].studentName: this.ordersCopied[selectedStudentNo].studentName }} 님의 주문내역</div>
						<div style=" width: 20%;  text-align: right; padding-right: 10px;">
							<button @click="closeModal()" style=" background-color: #EB4E5A; color:white; width: 25px; height: 25px; border-radius: 8px; font-weight: bold; font-size: 16px; border: 0px;">X</button>
						</div>
					</div>
					<div style="margin-left: auto; margin-right: auto;">
						<hr>
					</div>
					<div id="bb" style="width: 100%; height: 300px; display: flex; flex-direction: column; align-items: center;">
					<!-- <p>{{ this.orders.filter((order) => order.studentName ===  this.orders[selectedStudentNo].studentName) }}</p>	 -->
					<div 
					v-for="(selectedOrder, index) in selectedOrders"
						:key="index"
						style="width: 95%; height: auto;  min-height: 60px; display: flex; flex-direction: row; margin: 5px 0px;">
						<div style="width: 75%;">
							<div style="font-size: 18px; height: 30px; text-align: left; line-height: 30px;">
								<!-- {{ this.orders[selectedStudentNo].menuName }} 1잔 -->
								{{ selectedOrder.menuName }} 1잔
							</div>
							<div style="height:auto;  min-height: 30px; text-align: left; line-height: 30px;">
								<!-- <div style="display: flex; flex-direction: row;" v-if="selectedOrder.menuOptions.length >=3"> -->
								<div style="padding: 4px; display: flex; flex-direction: row; flex-wrap: wrap;" v-if="selectedOrder.menuOptions.length >=3">
									<div v-for="(option, idx) in selectedOrder.menuOptions.slice(2)" :key="idx">
										{{ option.name }} &nbsp; &nbsp;
									</div>
								</div>
								<div v-if="selectedOrder.menuOptions.length <=2">
									<div style="padding: 4px;">
										기본옵션
									</div>
								</div>
							</div>
						</div>
						<div style="width: 25%; height; text-align: right; line-height: 60px; padding-right: 0px;">
							<!-- {{ this.orders[selectedStudentNo].menuPrice }} 원 -->
							{{ selectedOrder.menuPrice }} 원
						</div>
					</div>
				</div>
					<div style="width: 100%; height: 40px; padding-top: 50px; display: flex; flex-direction: column; align-items: center;">
						<div style="width: 95%; height: 100%;  display: flex;">
							<div style=" width: 80%; line-height: 100%;  color: white; line-height: 40px; text-align: right;">
								총
							</div>
							<div style=" width: 15%; line-height: 100%;  color:  #00A7D0; line-height: 40px; text-align: right; padding-right: 3px;">
								{{ selectedOrders.reduce((acc, cur)=> acc + cur.menuPrice, 0) }}
							</div>
							<div style=" width: 5%; line-height: 100%;  color: white; line-height: 40px; text-align: right; padding-right: 0px;">
								원
							</div>
						</div>
							<!-- <div style="width: 60%; color: white;">총</div>
							<div style="width: 30%; color: #00A7D0; text-align: light;">{{ selectedOrders.reduce((acc, cur)=> acc + cur.menuPrice, 0) }}&nbsp;</div>
							<div style="width: 10%; color: white; margin-right: 0px;">원</div> -->
					</div>					
				</div>
		</div>
		<div style="z-index: 1;" class="menulistbox">
			<!-- <div class="menulisttitle">
			전체 주문 내역
		</div> -->
			<div v-if="!isSortedByMenu" class="orderheader">
				<div style="width: 10%; border-box; padding-left: 0.5rem;">학급</div>
				<div id="name-click" @click="sortByName" style="width: 25%; cursor: pointer;">이름 ▼</div>
				<div @click="sortByMenu" style="width: 50%; box-sizing: border-box; padding-left: 7rem; text-align: left; cursor: pointer;">메뉴 ▼</div>
				<div id="price-click" @click="sortByPrice" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: pointer;">금액 ▼</div>
			</div>
			<!-- <div v-if="!isSortedByMenu" class="orderheader">
				<div style="width: 10%; border-box; padding-left: 0.5rem;">학급</div>
				<div id="name-click" @click="sortByName" style="width: 25%; cursor: pointer; ">이름 ▼</div>
				<div @click="sortByMenu" style="width: 50%; box-sizing: border-box; padding-left: 7rem; text-align: left; cursor: pointer;">메뉴 ▼</div>
				<div id="price-click" @click="sortByPrice" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: pointer;">금액 ▼</div>
			</div> -->

			<div v-if="isSortedByMenu" class="orderheader">
				<div style="width: 10%; border-box; padding-left: 0.5rem;"></div>
				<div @click="sortByMenu" style="width: 25%; cursor: pointer; ">메뉴 〓</div>
				<!-- <div @click="sortByName(this.isSortedByName)" style="width: 25%; cursor: grab; ">이름 ▼</div> -->
				<div @click="sortByMenu" style="width: 50%; box-sizing: border-box; padding-left: 7rem; text-align: left; cursor: pointer;">옵션</div>
				<div @click="sortByMenu" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: pointer;">금액</div>
				<!-- <div @click="sortByPrice(this.isSortedByPrice)" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: grab;">금액 ▼</div> -->
			</div>

			<div v-if="!isSortedByMenu" class="orderbox" style="font-size: 32px;">
				<div
					v-for="(order, index) in (isInit?orders:ordersCopied)"
					:key="index"
					@click="selectStudent(index)"
					:class="{ordermenu: true, isSelected: selectedStudentNo == index, isCarrier: this.carriersArr.includes( order.studentName) ?true:false}"
					>
					<!-- {{ order.classNo }} {{ order.studentName }} {{ order.menuName }} {{ order.menuPrice }}-->
					<div class="classno">{{ order.classNo }}</div>
					<div v-if="this.carriersArr.includes(order.studentName) && this.payers.includes( order.studentName)" class="studentname">{{ order.studentName }}🛵💰</div>
					<div v-if=" this.carriersArr.includes(order.studentName) && !this.payers.includes( order.studentName)" class="studentname">{{ order.studentName }} 🛵</div>
					<div v-if=" !this.carriersArr.includes(order.studentName) && this.payers.includes( order.studentName)" class="studentname">{{ order.studentName }} 💰</div>
					<div v-if=" !this.carriersArr.includes(order.studentName) && !this.payers.includes( order.studentName)" class="studentname">{{ order.studentName }}</div>
					<div class="menuname">{{ order.menuName }}</div>
					<div class="menuprice">{{ order.menuPrice }}</div>
				</div>
			</div>


			<div v-if="isSortedByMenu" class="orderbox" style="font-size:32px;">
				<div 
				class="ordermenu2"
				v-for="(order, index) in ordersMenuSorted"
				:key="index"
				>
				<div class="classno">{{ order.classNo }}</div>
				<div class="studentname">{{ order.menuName }} X {{ order.menuCount }}</div>
				<div class="menuname">
				<!-- <div class="menuname" style="background-color: bisque;"> -->
					<div class="baker" v-for="(option, indexOpt) in order.options" 
					:key="indexOpt">
					<div style="display: flex; flex-direction: row;">
						<div style="display: flex; flex-direction: column;" v-if="option.length>=3">
						<!-- <div style="background-color: mediumseagreen; display: flex; flex-direction: row;" v-if="option.length>=3"> -->
							<div class="option-list" style=" display: inline-block; height: auto;  min-height: 20px; color: #344A53;" v-for="(a,b) in option.slice(2)" 
							:key="b">
							{{ a.name }}
							</div>
					</div>

					<div style="display: flex; flex-direction: column;"  v-if="option.length<=2">
					<!-- <div style="background-color: red;"  v-if="option.length<=2"> -->
						<div class="option-list" style=" display: inline-block; height: auto;  min-height: 20px; color: #344A53;">
							기본옵션 &nbsp
						</div>
					</div>


					<div style="align-items: center; padding-left: 10px; font-weight: bold;">
						= {{ option[1] }}개
					</div>
					
				</div>	
					</div>	
				</div>
				<div class="menuprice">{{ order.sumMenuPrice }}</div>



				<!-- <div>{{ order.menuName }} X {{ order.menuCount }} {{ order.sumMenuPrice }} 원 </div> -->
				
				
				</div>
			</div>

			<div class="summary">
				<div>총 인원 {{ summaryData.totalNumStudent  }} 명</div>
				<!-- <div></div> -->
				<div>총 수량 {{ summaryData.totalNumItem }} 개</div>
				<!-- <div></div> -->
				<div>총 금액 {{ summaryData.totalPrice }} 원</div>
				<!-- <div></div> -->
			</div>
		</div>
	</div>
</template>

<script>
import { computed, onMounted, ref, registerRuntimeCompiler } from 'vue'

export default {
	props: {
        orders: Array,
		ordersMenuSorted: Array,
		nameSet: Set,
		carriers: Array,
		participants: Array,
		carriersArr: Array,
		payers: Array,
      },

	data () {
		return {
			// orders: [
			// 	{classNo: "2", studentName: "전상혁", menuName: "에스프레소", menuPrice: 1000},
			// 	{classNo: "2", studentName: "고영훈", menuName: "ICE 카페라떼", menuPrice: 2000},
			// 	{classNo: "2", studentName: "양희승", menuName: "HOT 바닐라라떼", menuPrice: 1300},
			// 	{classNo: "2", studentName: "강찬우", menuName: "ICE 로즈마리", menuPrice: 2500},
			// 	{classNo: "2", studentName: "강찬우", menuName: "공군짜장", menuPrice: 15000},
			// 	{classNo: "2", studentName: "박희찬", menuName: "HOT 페퍼민트", menuPrice: 2400},
			// 	{classNo: "2", studentName: "주홍찬", menuName: "HOT 페퍼민트", menuPrice: 2400},
			// 	{classNo: "2", studentName: "주홍찬", menuName: "해병 팥빙수", menuPrice: 100},
			// 	{classNo: "2", studentName: "주홍찬", menuName: "해병 팥빙수", menuPrice: 100},
			// 	{classNo: "2", studentName: "강민지", menuName: "ICE 자몽티", menuPrice: 3000},
			// 	{classNo: "2", studentName: "김우태", menuName: "캔디소다 밀크쉐이크", menuPrice: 6000},
			// 	{classNo: "2", studentName: "유병욱", menuName: "ICE 고구마라떼", menuPrice: 300},
			// 	{classNo: "2", studentName: "이도훈", menuName: "ICE 고구마라떼", menuPrice: 300},
			// 	{classNo: "2", studentName: "김요한", menuName: "키위주스", menuPrice: 12345},
			// 	{classNo: "2", studentName: "이유빈", menuName: "오렌지당근주스", menuPrice: 12345},
			// 		],
			ordersCopied : [],
			selectedStudentNo: -1,
			selectedOrders: [],
			isSortedByName: false,
			isSortedByPrice: false,
			isInit: true,
			
			// isSortedByMenu: true,
			isSortedByMenu: false,

		}
	},

	computed: {
		
		summaryData() {
			// let totalPrice = ref(0)
			// this.orders.forEach(order => {
			// 	totalPrice.value += order.menuPrice
			// 	console.log(order.menuPrice)
			// });
			// console.log('ssafy')
			// this.orders.forEach((order) => {
			// 	this.orders.reduce((acc, cur) => {order}, 0)
			// })
			// return {totalNum: this.orders.length, totalPrice: totalPrice}
			return { totalNumStudent: this.nameSet.size, totalNumItem: this.orders.length, totalPrice: this.orders.reduce((acc, cur)=> acc + cur.menuPrice, 0)}
			// return { totalNumStudent: this.orders.length, totalNumItem: this.orders.length, totalPrice: this.orders.reduce((acc, cur)=> acc + cur.menuPrice, 0)}
		},
	},
	methods: {
		selectStudent(index) {
			// this.selectedStudentNo = this.selectedStudentNo === index? -99: index;
			// let menuNames = []
			console.log(index)
			if (this.selectedStudentNo === index) {
				this.selectedStudentNo = -99;
				this.selectedOrders = ref([]);
			}
			else {
				this.selectedStudentNo = index;
				// this.selectedOrders = this.orders.filter((order) => order.studentName ===  this.orders[this.selectedStudentNo].studentName);
				// this.selectedOrders = this.orders.filter((order) => order.studentName ===  this.orders[this.selectedStudentNo].studentName);
				if (this.isInit) {
					this.selectedOrders = this.orders.filter((order) => order.studentName ===  this.orders[this.selectedStudentNo].studentName);
				} else {
					this.selectedOrders = this.ordersCopied.filter((order) => order.studentName ===  this.ordersCopied[this.selectedStudentNo].studentName)
				}
				
			// 	this.selectedOrders.forEach((order,index) => {
			// 		if (!menuNames.includes(order.menuName)) {
			// 			menuNames.push(order.menutName)
			// 		} else {
			// 			let idx = this.selectedOrders.findIndex(x => x.menuName === order.menuName)
			// 			this.selectedOrders[idx].
			// 			this.selectedOrders.splice(index,1)
						
			// 		}
			// 	}) 

			}
			// console.log(this.selectedStudentNo);
			// console.log(this.orders[this.selectedStudentNo].studentName);
			// console.log(this.selectedOrders);
		},
		closeModal() {
			this.selectedStudentNo = -1;
		},

		sortByName(event) {
			console.log('참여자:',this.participants)
			console.log('배달부:',this.carriers)
			document.querySelector("#price-click").innerText = "금액 ▼"
			// console.log(this.isInit)
			this.isInit = false
			// console.log(this.isInit)
			if (!this.isSortedByName) {
				this.isSortedByName = true
				event.target.innerText = "이름 〓"
				
				console.log("이름기준 정렬데이터를 호출합니다")
				// this.orders.sort((a, b) => a.studentName.localeCompare(b.studentName));
				this.ordersCopied = this.orders.toSorted((a, b) => a.studentName.localeCompare(b.studentName));
				this.ordersCopied = this.ordersCopied.filter((order) => this.carriersArr.includes(order.studentName)).concat(this.ordersCopied.filter(order=> !this.carriersArr.includes(order.studentName)));
				// this.ordersCopied = this.orders.toSorted((a, b) => a.studentName.localeCompare(b.studentName));
			} else {
				this.isSortedByName = false
				event.target.innerText = "이름 ▼"
				this.ordersCopied = JSON.parse(JSON.stringify(this.orders))
				
			}
			
			// console.log(this.orders)
		},
		sortByMenu() {
			// const nameClick= document.querySelector('#name-click');
			// console.log(nameClick.innerText)
			if (this.isSortedByMenu) {
				console.log('정렬됨')
				this.isSortedByMenu = false
				console.log(this.isSortedByMenu)
			} else {
				console.log('정렬안됨')
				this.isSortedByMenu = true
				console.log(this.isSortedByMenu)
			}
		},

		sortByPrice(event) {
			document.querySelector("#name-click").innerText = "이름 ▼"
			this.isInit = false
			if (!this.isSortedByPrice) {
				this.isSortedByPrice = true
				event.target.innerText = "금액 〓"
				console.log("가격기준 정렬데이터를 호출합니다")
				// this.orders.sort((a, b) => b.menuPrice - a.menuPrice);
				this.ordersCopied = this.orders.toSorted((a, b) => b.menuPrice - a.menuPrice);
				console.log("ss:", this.carriersArr)
				this.ordersCopied = this.ordersCopied.filter((order) => this.carriersArr.includes(order.studentName)).concat(this.ordersCopied.filter(order=> !this.carriersArr.includes(order.studentName)));
			} else {
				this.isSortedByPrice = false
				event.target.innerText = "금액 ▼"
				this.ordersCopied = JSON.parse(JSON.stringify(this.orders))
				
			}
			
			// console.log(this.orders)
			// this.orders.sort((b, a) => a.studentName.localeCompare(b.studentName));
		},
	},
}
</script>

<style lang="scss" scoped>
	.menulist {
		display: flex;
		flex-direction: column;
		height: 100%;
	}
	.menulistbox {
		display: flex;
		flex-direction: column;
		// border: 1px solid black;
		border-radius: 0.5rem;
		// flex-grow: 1;
		height: 100%;
		
	}
	.orderheader {
		display: flex;
		color: white;
		height: 50px;
		line-height: 50px;
		text-align: center;
		font-weight: bold;
		font-size: 26px;
		background-color: #97AFBA;
		border-radius: 0.5rem 0.5rem 0 0;
		// flex-grow: 0;
	}
	.orderbox {
		// background-color: purple;
		// flex-grow: 1;
		height: 100%;
		overflow-y:scroll;
		overflow-x: hidden;
		z-index: 2;
	}

	.ordermenu {
		display: flex;
		flex-direction: row;
		color: #344A53;
		height: 64px;
		line-height: 64px;
		text-align: center;
		font-size: 18px;
		background-color: #F5F5F5;
		margin: 0.25rem;
		position: relative;
		
	}

	.summary {
		
		// width: 100%;
		height: 50px;
		line-height: 50px;
		text-align: center;
		font-size: 24px;
		background-color: #F5F5F5;
		border: 1px solid #344A53;
		margin: 0.5rem;
		display: flex;
		// flex-grow: 0;
	}

	.summary > div {
		width: 33%;
	}

	.ordermenu > * {
		font-size: 24px;
		font-weight: bold;
		color: #344A53;
		// border: 1px dashed black;
		// flex-grow: 1;
		// border: 2px dashed black;
		// margin: 2px;
	}
	// {classNo: "2", studentName: "김요한", menuName: "키위주스", menuPrice: 12345},
	.classno {
		width: 10%;
		// border: 1px dashed black;
		text-align: center;
		// position: absolute;
		// left: 0%;
		// flex-grow: 2;
	}

	.studentname {
		width: 25%;
		// display: flex;
		align-items: center;
		text-align: center;
		// border: 1px dashed black;
		// position: absolute;
		// left: 20%;
		// flex-grow: 3;
	}

	.menuname {
		width: 50%;
		// border: 1px dashed black;
		box-sizing: border-box;
		padding-left: 64px;
		text-align: left;
		// position: absolute;
		// left: 45%;
		// flex-grow: 5;
	}

	.menuprice {
		width: 15%;
		// border: 1px dashed black;
		text-align: right;
		box-sizing: border-box;
		padding-right: 2rem;

		display: flex;
		justify-content: flex-end;
		align-items: center;
		// position: absolute;
		// left: 90%;
		// flex-grow: 2;
	}

	.isSelected {
		background-color: #97AFBA;
	}

	.black-bg {
        width: 40%;
        height: 100%;
        // background: rgba(0,0,0,0.5);
        position: fixed;
		left: 20%;
        // padding: 20px;
		
		z-index: 3;
		// transform: translateX(20%);
		// margin-left: 0;
      }
	.white-bg {
				// width: 650px;
				width: 130%;
				// height: 300px;
				height: auto;
				margin: 80px auto;
				background: #344a53;
				border-radius: 10px;
				padding: 10px 0;
				border: 2px solid black;
				text-align: center;
				color: white;
				z-index: 4;
				// position: relative;
				// margin-right: 0;
				display: flex;
				flex-direction: column;
				transform: translateY(-26px);
}		
	.faker {
		background-color: purple;
		width: 100%;
		margin-top: 10px;
	}

	.baker {
		// background-color: mediumturquoise;
	}

	.ordermenu2 {
		display: flex;
		flex-direction: row;
		color: #344A53;
		// height: 64px;
		// line-height: 64px;
		text-align: center;
		font-size: 16px;
		background-color: #F5F5F5;
		margin: 0.25rem;
		position: relative;
		padding-top: 0.25rem;
		padding-bottom: 0.25rem;
	}

	.ordermenu2 > .classno, .studentname, .menuprice {
		font-size: 20px;
    font-weight: bold;
    color: #344A53;
	}
	hr {
		width: 50vw; 
		margin: 0px;
	}

	.option-list{
		width: 250px;
		
	}

	.isCarrier {
		background-color: #344a53;
	}

	.isCarrier * {
		// background-color: #344a53;
		color: white;
	}
	@media screen and (max-width: 768px) {
	.orderheader {
		font-size: 20px;
		// flex-grow: 0;
	}
	.ordermenu {
		font-size: 12px;
	}

	.summary {
		font-size: 18px;
		// flex-grow: 0;
	}

	.ordermenu > * {
		font-size: 18px;
	}

	.ordermenu2 {
		font-size: 12px;
	}
	.black-bg {
		left:2%;
        width: 96%;
        height: 100%;
      }
	.white-bg {
				width: 99%;
				transform: translateY(-26px);
	}
	hr {
		width: 90vw; 
	}
	.ordermenu2 > .classno, .studentname, .menuprice {
		font-size: 16px;
	}

	.option-list{
		width: 180px;
		
	}
	}
</style>