import { createRouter, createWebHistory } from 'vue-router'

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
      path: '/login/oauth2/redirect/:registraionId',
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
      path: '/Manager',
      name: 'ManagerLogin',
      component: () => import('../views/TheManagerLoginView.vue'),
    },
    {
      path: '/manager/:manager_id',
      name: 'Manager',
      component: () => import('../views/TheManagerView.vue'),
    },
    {
      path: '/manager/:manager_id/party/:access_code',
      name: 'OrdersForManager',
      component: () => import('../views/TheOrdersForManagerView.vue'),
    },
    {
      path: '/manager/:manager_id/edit',
      name: 'ManagerEditor',
      component: () => import('../views/TheManagerEditorView.vue'),
    },
  ],
})

export default router
