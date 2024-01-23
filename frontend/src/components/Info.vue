<template>
    <div class="black-bg dv" v-if="isVisible==true">
        <div class="white-bg dv">
            <h4>상세페이지</h4>
            <p>상세페이지내용</p>

        </div>
    </div>

    <div id="info">
          <div id="accountinfo">
              <span>입금계좌:</span>
              <span>농협 302-0051-8244-01</span>
              <span>전상혁</span>
          </div>
          <div @mouseover="onMoloo" id="moloo">?</div>
    </div>
  </template>
  
  <script>
    import { ref } from 'vue'
    
    export default {
    setup () {
        let isVisible = ref(false)
        const onMoloo = function (event) {
            console.log('몰?루')
            console.log(event.currentTarget())
            // isVisible = ref(true)
            // console.log(isVisible)
        }
        return {
            isVisible,
            onMoloo
        }
    }
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
      }

      body {
        margin: 0;
      }
      .dv {
        box-sizing: border-box;
      }
      .black-bg {
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.5);
        position: fixed;
        padding: 20px;
      }
      .white-bg {
        width: 50%;
        background: white;
        border-radius: 8px;
        padding: 20px;
      }
  </style>