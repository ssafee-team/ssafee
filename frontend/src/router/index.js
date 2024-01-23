import { createRouter, createWebHistory } from "vue-router";
// import { storeToRefs } from "pinia";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: () => import("@/views/TheMainPageView.vue"),
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
      path: '/room',
      name: 'room',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/TheRoomView.vue')
    },
    {
      path: '/CreateRoomView',
      name: 'CreateRoomView',
      component: () => import('../views/TheCreateRoomView.vue')
    },
    {
      path: '/After',
      name: 'After',
      component: () => import('../views/TheAfterView.vue')
    },
  ],
});

export default router;
