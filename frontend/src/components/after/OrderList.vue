<template>
	<div class="menulist">
		<div class="menulistbox">
			<div class="menulisttitle">
			전체 주문 내역
		</div>
			<div class="orderheader">
				<div style="width: 10%; border-box; padding-left: 0.5rem;">학급</div>
				<div style="width: 25%;">이름</div>
				<div style="width: 50%; box-sizing: border-box; padding-left: 7rem; text-align: left;">메뉴</div>
				<div style="width: 15%; box-sizing: border-box; padding-right: 2rem;">금액</div>
			</div>
			<div class="orderbox" style="font-size: 2rem;">
				<div
					v-for="(order, index) in orders"
					:key="index"
					:class="{ordermenu: true}"
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
				{classNo: "2", studentName: "박희찬", menuName: "HOT 페퍼민트", menuPrice: 2400},
				{classNo: "2", studentName: "주홍찬", menuName: "HOT 페퍼민트", menuPrice: 2400},
				{classNo: "2", studentName: "강민지", menuName: "ICE 자몽티", menuPrice: 3000},
				{classNo: "2", studentName: "김우태", menuName: "캔디소다 밀크쉐이크", menuPrice: 6000},
				{classNo: "2", studentName: "유병욱", menuName: "ICE 고구마라떼", menuPrice: 300},
				{classNo: "2", studentName: "이도훈", menuName: "ICE 고구마라떼", menuPrice: 300},
				{classNo: "2", studentName: "김요한", menuName: "키위주스", menuPrice: 12345},
				{classNo: "2", studentName: "이유빈", menuName: "오렌지당근주스", menuPrice: 12345},
					]
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
		}
	},
	method: {

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
</style>