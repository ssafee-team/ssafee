import { createRouter, createWebHistory } from 'vue-router'

// Manager
import ManagerLogin from '@/components/manager/ManagerLogin.vue'
import ManagerMain from '@/components/manager/ManagerMain.vue'
import ManagerShop from '@/components/manager/ManagerShop.vue'
import ManagerInfo from '@/components/manager/ManagerInfo.vue'
import ManagerOrderList from '@/components/manager/ManagerOrderList.vue'

// import { storeToRefs } from "pinia";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: () => import('@/views/TheMainPageView.vue'),
    },
    {
      path: '/login/oauth2/redirect/:registration',
      name: 'LoginOAuth2',
      component: () => import('@/views/TheLoginOAuth2View.vue'),
    },
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // },
    {
      path: '/room/:code',
      name: 'room',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/TheRoomView.vue'),
      // props: true, //route props로 코드 전달
    },
    {
      path: '/CreateRoomView',
      name: 'CreateRoomView',
      component: () => import('../views/TheCreateRoomView.vue'),
    },
    {
      path: '/after/:code',
      name: 'after',
      component: () => import('../views/TheAfterView.vue'),
      props: true,
    },
    {
      path: '/m-login',
      name: 'm-login',
      component: ManagerLogin,

    },
    {
      path: '/m-main',
      name: '/m-main',
      component: ManagerMain,
    },
    {
      path: '/m-shop',
      name: '/m-shop',
      component: ManagerShop,
    },
    {
      path: '/m-info',
      name: '/m-info',
      component: ManagerInfo,
    },
    {
      path: '/m-order-list',
      name: '/m-order-list',
      component: ManagerOrderList,
    },
  ],
})

export default router
