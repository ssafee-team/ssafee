<template>
	<main>
		<header>
			<img src="../assets/img/logo_ssaffe_2nd.png" alt="logo_ssaffe_2nd" style="height: 72px; padding-right: 10px;">
			<div style="margin-right: 0;">
				SSAFFE®
			</div>
			<div style="margin-right: auto; font-size: 8px; transform: translateY(-8px);">
				for Manager
			</div>
		</header>
		<body>
			<div style="background-color: black; width: 100%; height: 88vh; ">
					<div style="width: 100%; height: 400px; display: flex; flex-direction: row; justify-content: center;">
						<div style="width: 500px; height: 80%; margin-top: 50px; ">
							
							<div class="div-login">
								<div>ID</div>
								<input type="text">
							</div>

							<div class="div-login" style="border: none;">
								<div>PW</div>
								<input type="password">
							</div>
							
							<div class="div-login">
								<div></div>
								<input @click="goManager" class="button-login" type="button" value="로그인">
							</div>

							<div class="div-login">
								<div></div>
								<a href="">비밀번호 찾기</a>
							</div>
	
						</div>
					</div>
				<footer>
					<div class="footer-info">제휴문의</div>
					<div class="footer-info">Email. example@naver.com</div>
					<div class="footer-info">Tel (+82) 10-0000-0000</div>
			</footer>
			</div>
		</body>
		
	</main>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const manager_id = ref(1);

const goManager = () => {
	router.push({name: 'Manager', params: {manager_id:'1'}})
}
</script>

<style lang="scss" scoped>
	main {
		height: 100%;
	}

	header {
		display: flex;
		align-items: center;
		flex-direction: row;
		width: 100%;
		height: 72px;
		font-size: 30px;
		font-weight: bold;
		background-color: white;
	}

	footer {
		padding-left: 20px;
		height: 100px;
		position: absolute;
		bottom: 0;
		// width: 100%;
		// background-color: black;
		color: #97AFBA;
	}

	.container-login {
		background-color: aqua;
		width: 100px;
		height: 100px;
	}

	.div-login {
		display: flex; 
		justify-content: center;
	} 
	.div-login > div {
		padding-right: 10px;
		line-height: 80px;
		width: 50px;
		color: white;
		font-size: 30px;
		font-weight: bold;
		text-align: center;		
	}

	.div-login > input {
		box-sizing: content-box;
		padding-left: 10px;
		margin: 6px;
		border-radius: 16px;
		border: 0px;
		width: 400px;
		height: 80px;
		font-size: 30px;
	}

	.div-login > a {
		box-sizing: content-box;
		padding: 0;
		width: 400px;
		height: 80px;
		font-weight: bold;
		color: #00A5E7;
		text-align: center;
		text-decoration: none;
	}

	.button-login {
		background-color: #296A84;
  	color: white;
		padding: 0px;
  	cursor: pointer;
	}
</style>