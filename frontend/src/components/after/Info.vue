<template>
	<div>
		<div class="black-bg" v-if="isOpened==true">
		<!-- <div class="black-bg"> -->
			<div class="flexmodal">
				<div class="modalbody">
          전체 주문 현황 상단은 배달 인원으로 선정된 명단입니다.<br>
          배달 인원은 6잔 당 1명씩 추가됩니다.
        </div>
				<div class="modaltail"></div>
			</div>
		<!-- <div class="black-bg"> -->
		</div>
    <div id="info">
      <div id="accountinfo">
        <span>입금계좌:</span>
        <span>농협 302-0051-8244-01</span>
        <span>전상혁</span>
      </div>
    <div @mouseover="onMoloo" @mouseout="offMoloo" id="moloo">?</div>
    <!-- <div @click="onMoloo" @mouseout="offMoloo" id="moloo">?</div> -->
    </div>
	</div>
</template>
  
  <script>
    import { ref } from 'vue'
    
    export default {
    // setup () {
    //     let isVisible = ref(false)
    //     const onMoloo = function (event) {
    //         console.log('몰?루')
    //         console.log(event.currentTarget())
    //         // isVisible = ref(true)
    //         // console.log(isVisible)
    //     }
    //     return {
    //         isVisible,
    //         onMoloo
    //     }
    // }
    data () {
			return {
				isOpened: false,
        isSelected: false,
			}
    },
    computed: {

    },
    methods: {
			onMoloo(event) {
				// console.log(event)
				// console.log("물음표에 들어왔어요")
				this.isOpened = true
        event.target.innerText = "!";
        // <img class="image-thumbnail">
        
				// console.log(this.isOpened)
				// console.log(event.currentTarget)
			},
			offMoloo(event) {
				// console.log(event)
				// console.log("물음표를 나가요")
				this.isOpened = false
        event.target.innerText = "?";
				// console.log(this.isOpened)
				// this.isOpened = false
				// console.log(event.currentTarget)
			}
    },
}
  </script>
  
  <style>
      span {
          font-weight: bold;
          font-size: 1.25rem;
          color: #344A53;
      }
  
      #info {
          margin-top: 25px;
          height: 50px;
          display: flex;
          align-items: center;
          justify-content: space-between;
      }
  
      #accountinfo {
          margin-left: 2rem;
      }	
      #moloo {
          line-height: 40px;
          font-size: 1.5rem;
          height: 40px;
          width: 40px;
          color: white;
          /* align-items: center; */
          text-align: center;
          /* margin: 32px 0px; */
          /* justify-content: center; */
          background-color:#344A53;
          border-radius: 50%;
          margin-right: 2rem;
          cursor:help;
      }

      body {
        margin: 0;
      }
      .dv {
        box-sizing: border-box;
      }
      .black-bg {
        width: 90%;
        height: 100%;
        /* background: rgba(0,0,0,0.5); */
        position: fixed;
        padding: 20px;
				text-align: right;
				/* margin-right: 0px; */
        /* display: flex;
        flex-direction: row; */
      }
      

      .white-bg {
        /* width: 50%; */
        background: white;
        /* border-radius: 8px; */
        /* padding: 20px; */
				width: 100px;
				height: 100px;
      }

			.flexmodal {
				/* width: 100%; */
        height: 100px;
				/* padding-right: 8rem; */
				display: inline-flex;
				/* text-align: right; */
        /* margin: 0px auto; */
        /* margin-right: 6rem; */
        position: absolute;
        right: -2rem;
        
			}
			.modalbody {
				width: 450px;
				height: 50px;
				background-color: hotpink;
				border-radius: 10px;
				transform: translateY(-28px);
        text-align: center;
        box-sizing: content-box;
        padding: 10px;
			}

			.modaltail {
				/* padding-top: 20px; */
				width: 0;
				height: 0;
				border-top: 10px solid transparent;
				border-bottom: 10px solid transparent;
				border-left: 20px solid hotpink;
				border-right: 10px solid transparent;
        transform: translateY(-4px);
			}
  </style>