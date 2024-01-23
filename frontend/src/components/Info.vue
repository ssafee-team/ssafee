<template>
    <div id="info">
          <div id="accountinfo">
              <span>입금계좌:</span>
              <span>농협 302-0051-8244-01</span>
              <span>전상혁</span>
          </div>
          <div id="moloo">?</div>
    </div>
  </template>
  
  <script setup>
  
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
  </style>