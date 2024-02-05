<template>
  <div>
    <div class="tooltip-bg-black" v-if="isOpened == true">
      <div class="tooltip-bg-white">
        <div class="tooptip-body">
          전체 주문 현황 상단은 배달 인원으로 선정된 명단입니다.<br />
          배달 인원은 6잔 당 1명씩 추가됩니다.
        </div>
        <div class="tooltip-tail"></div>
      </div>
    </div>

    <!-- <div class="black-bg" v-if="isOpened==true"> -->
    <!-- <div class="black-bg"> -->
    <!-- <div class="flexmodal"> -->
    <!-- <div class="tooptip-body">
          전체 주문 현황 상단은 배달 인원으로 선정된 명단입니다.<br>
          배달 인원은 6잔 당 1명씩 추가됩니다.
        </div> -->
    <!-- <div class="tooltip-tail"></div>
			</div> -->
    <!-- <div class="black-bg"> -->
    <!-- </div> -->
    <div id="info">
      <div class="accountinfo">
        <!-- <span>입금계좌:</span>
        <span>농협 302-0051-8244-01</span>
        <span>전상혁</span> -->
        <span>입금계좌: &nbsp; &nbsp;</span>
        <span>{{ this.creator.bank }} &nbsp; {{ this.creator.account }} &nbsp;&nbsp;</span>
        <span>{{ this.creator.name }}</span>
      </div>
      <div @mouseover="onMoloo" @mouseout="offMoloo" id="moloo">?</div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  props: {
    creator: Object,
  },
  data() {
    return {
      isOpened: false,
      isSelected: false,
    };
  },
  computed: {},
  methods: {
    onMoloo(event) {
      console.log(this.creator.id, this.creator.name, this.creator.bank, this.creator.account);
      this.isOpened = true;
      event.target.innerText = "!";
    },
    offMoloo(event) {
      this.isOpened = false;
      event.target.innerText = "?";
    },
  },
};
</script>

<style>
span {
  font-weight: bold;
  font-size: 18px;
  color: #344a53;
}

#info {
  display: flex;
  align-items: center;
  justify-content: space-between;
  overflow: visible;
}
.accountinfo {
  /* font-size: 16px; */
  margin-left: 2rem;
}
#moloo {
  line-height: 46px;
  font-size: 24px;
  height: 46px;
  width: 46px;
  color: white;
  transform: translateY(-4px);
  text-align: center;
  background-color: #344a53;
  border-radius: 50%;
  margin-right: 2rem;
  cursor: help;
}

.dv {
  box-sizing: border-box;
}
.tooltip-bg-black {
  width: 100%;
  height: 45px;
  /* background: rgba(0, 0, 0, 0.6); */
  position: absolute;
  display: flex;
  justify-content: flex-end;
}

.tooltip-bg-white {
  width: 520px;
  height: 45px;
  /* background-color: aqua; */
  display: flex;
}

.tooptip-body {
  width: 360px;
  height: 28px;
  color: #f8f8f8;
  background-color: #97afba;
  /* opacity: 0.9; */
  font-weight: bold;
  font-size: 14px;
  border-radius: 4px;
  transform: translateY(-5px);
  text-align: center;
  box-sizing: content-box;
  padding: 10px;
  line-height: 14px;
}

.tooltip-tail {
  /* padding-top: 20px; */
  width: 0;
  height: 0;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-left: 20px solid #97afba;
  /* opacity: 0.9; */
  border-right: 10px solid transparent;
  transform: translateY(8px);
}

@media screen and (max-width: 768px) {
  .accountinfo span {
    font-size: 14px;
  }
}
</style>
