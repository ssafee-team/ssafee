<template>
	<div class="menulist">
		<div v-if="selectedStudentNo >= 0" class="black-bg">
				<div class="white-bg">
					<!-- <div id="aa" style="background-color: aqua;  width: 100%; height: 40px; border: 1px dashed black display: block">
						<div style="background-color: blue; width: 80%; height: 40px; box-sizing: border-box; line-height: 40px;">dddddd</div>
						<div style="background-color: red; width: 10%; height: 40px;">dd</div>
					</div> -->
					<div style="  width: 100%; height: 30px; display: flex; transform: translateY(-5px);">
						<div style=" width: 80%; line-height: 30px; text-align: left; padding-left: 10px;">{{ this.orders[selectedStudentNo].studentName }} 님의 주문내역</div>
						<div style=" width: 20%;  text-align: right; padding-right: 10px;">
							<button @click="closeModal()" style=" background-color: #EB4E5A; color:white; width: 25px; height: 25px; border-radius: 8px; font-weight: bold; font-size: 1rem; border: 0px;">X</button>
						</div>
					</div>
					<div style="margin-left: auto; margin-right: auto;">
						<hr style="width: 380px; margin: 0px;">
					</div>
					<div id="bb" style="width: 100%; height: 300px; display: flex; flex-direction: column; align-items: center;">
					<!-- <p>{{ this.orders.filter((order) => order.studentName ===  this.orders[selectedStudentNo].studentName) }}</p>	 -->
					<div 
					v-for="(selectedOrder, index) in selectedOrders"
						:key="index"
						style="width: 95%; height: 60px; display: flex; margin: 5px 0px;">
						<div style="width: 75%;">
							<div style="height: 30px; text-align: left; line-height: 30px;">
								<!-- {{ this.orders[selectedStudentNo].menuName }} 1잔 -->
								{{ selectedOrder.menuName }} 1잔
							</div>
							<div style="height: 30px; text-align: left; line-height: 30px;">
								옵션: 없음
							</div>
						</div>
						<div style="width: 25%; height; text-align: right; line-height: 60px; padding-right: 0px;">
							<!-- {{ this.orders[selectedStudentNo].menuPrice }} 원 -->
							{{ selectedOrder.menuPrice }} 원
						</div>
					</div>
				</div>
					<div style="width: 100%; height: 40px;  display: flex; flex-direction: column; align-items: center;">
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
		<div class="menulistbox">
			<div class="menulisttitle">
			전체 주문 내역
		</div>
			<div class="orderheader">
				<div style="width: 10%; border-box; padding-left: 0.5rem;">학급</div>
				<div @click="sortByName" style="width: 25%; cursor: pointer; ">이름 ▼</div>
				<!-- <div @click="sortByName(this.isSortedByName)" style="width: 25%; cursor: grab; ">이름 ▼</div> -->
				<div style="width: 50%; box-sizing: border-box; padding-left: 7rem; text-align: left;">메뉴</div>
				<div @click="sortByPrice" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: pointer;">금액 ▼</div>
				<!-- <div @click="sortByPrice(this.isSortedByPrice)" style="width: 15%; box-sizing: border-box; padding-right: 2rem; cursor: grab;">금액 ▼</div> -->
			</div>
			<div class="orderbox" style="font-size: 2rem;">
				<div
					v-for="(order, index) in (isInit?orders:ordersCopied)"
					:key="index"
					@click="selectStudent(index)"
					:class="{ordermenu: true, isSelected: selectedStudentNo == index}"
					>
					<!-- {{ order.classNo }} {{ order.studentName }} {{ order.menuName }} {{ order.menuPrice }} -->
					<div class="classno">{{ order.classNo }}</div>
					<div class="studentname">{{ order.studentName }}</div>
					<div class="menuname">{{ order.menuName }}</div>
					<div class="menuprice">{{ order.menuPrice }}</div>
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
import { computed, ref, registerRuntimeCompiler } from 'vue'
export default {
	data () {
		return {
			orders: [
				{classNo: "2", studentName: "전상혁", menuName: "에스프레소", menuPrice: 1000},
				{classNo: "2", studentName: "고영훈", menuName: "ICE 카페라떼", menuPrice: 2000},
				{classNo: "2", studentName: "양희승", menuName: "HOT 바닐라라떼", menuPrice: 1300},
				{classNo: "2", studentName: "강찬우", menuName: "ICE 로즈마리", menuPrice: 2500},
				{classNo: "2", studentName: "강찬우", menuName: "공군짜장", menuPrice: 15000},
				{classNo: "2", studentName: "박희찬", menuName: "HOT 페퍼민트", menuPrice: 2400},
				{classNo: "2", studentName: "주홍찬", menuName: "HOT 페퍼민트", menuPrice: 2400},
				{classNo: "2", studentName: "주홍찬", menuName: "해병 팥빙수", menuPrice: 100},
				{classNo: "2", studentName: "주홍찬", menuName: "해병 팥빙수", menuPrice: 100},
				{classNo: "2", studentName: "강민지", menuName: "ICE 자몽티", menuPrice: 3000},
				{classNo: "2", studentName: "김우태", menuName: "캔디소다 밀크쉐이크", menuPrice: 6000},
				{classNo: "2", studentName: "유병욱", menuName: "ICE 고구마라떼", menuPrice: 300},
				{classNo: "2", studentName: "이도훈", menuName: "ICE 고구마라떼", menuPrice: 300},
				{classNo: "2", studentName: "김요한", menuName: "키위주스", menuPrice: 12345},
				{classNo: "2", studentName: "이유빈", menuName: "오렌지당근주스", menuPrice: 12345},
					],
			ordersCopied : [],
			selectedStudentNo: -1,
			selectedOrders: [],
			isSortedByName: false,
			isSortedByPrice: false,
			isInit: true,

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
			return { totalNumStudent: this.orders.length, totalNumItem: this.orders.length, totalPrice: this.orders.reduce((acc, cur)=> acc + cur.menuPrice, 0)}
		},
	},
	methods: {
		selectStudent(index) {
			// this.selectedStudentNo = this.selectedStudentNo === index? -99: index;
			let menuNames = []
			if (this.selectedStudentNo === index) {
				this.selectedStudentNo = -99;
				this.selectedOrders = ref([]);
			}
			else {
				this.selectedStudentNo = index;
				this.selectedOrders = this.orders.filter((order) => order.studentName ===  this.orders[this.selectedStudentNo].studentName);
				
				
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
			this.isInit = false
			if (!this.isSortedByName) {
				this.isSortedByName = true
				event.target.innerText = "이름 〓"
				console.log("이름기준 정렬데이터를 호출합니다")
				// this.orders.sort((a, b) => a.studentName.localeCompare(b.studentName));
				this.ordersCopied = this.orders.toSorted((a, b) => a.studentName.localeCompare(b.studentName));
			} else {
				this.isSortedByName = false
				event.target.innerText = "이름 ▼"
				this.ordersCopied = JSON.parse(JSON.stringify(this.orders))
				
			}
			
			// console.log(this.orders)
		},
		sortByPrice(event) {
			this.isInit = false
			if (!this.isSortedByPrice) {
				this.isSortedByPrice = true
				event.target.innerText = "금액 〓"
				console.log("가격기준 정렬데이터를 호출합니다")
				// this.orders.sort((a, b) => b.menuPrice - a.menuPrice);
				this.ordersCopied = this.orders.toSorted((a, b) => b.menuPrice - a.menuPrice);
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

	.menulisttitle {
		
		
		width: 100%;
		height: 50px;
		line-height: 50px;
		text-align: center;
		font-size: 1.5rem;
		// background-color: pink;
		color: #344A53;
		flex-grow: 0;	
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
		font-size: 1.5rem;
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
		font-size: 1rem;
		background-color: #F5F5F5;
		margin: 0.25rem;
		position: relative;
		
	}

	.summary {
		
		// width: 100%;
		height: 50px;
		line-height: 50px;
		text-align: center;
		font-size: 1.5rem;
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
		font-size: 1.5rem;
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
		// border: 1px dashed black;
		// position: absolute;
		// left: 20%;
		// flex-grow: 3;
	}

	.menuname {
		width: 50%;
		// border: 1px dashed black;
		box-sizing: border-box;
		padding-left: 5rem;
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
		// position: absolute;
		// left: 90%;
		// flex-grow: 2;
	}

	.isSelected {
		background-color: #97AFBA;
	}

	.black-bg {
        width: 70%;
        height: 100%;
        // background: rgba(0,0,0,0.5);
        position: fixed;
        padding: 20px;
				// position: absolute;
				z-index: 3;
				transform: translateX(30%);
				margin-left: 0;
      }
	.white-bg {
				width: 400px;
				height: 300px;
				margin: 80px auto;
				margin-left: 0px;
				background: #344a53;
				border-radius: 5px;
				padding: 10px 0;
				border: 2px solid black;
				text-align: center;
				color: white;
				z-index: 4;
				// position: relative;
				// margin-right: 0;
				display: flex;
				flex-direction: column;
}		
	
</style>