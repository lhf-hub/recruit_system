import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

import AuthLayout from '../layouts/AuthLayout.vue'
import AppLayout from '../layouts/AppLayout.vue'
import FrontLayout from '../layouts/FrontLayout.vue'
import { useUserStore } from '../stores/user-store'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/:pathMatch(.*)*',
    redirect: { name: 'front' },
  },
  {
    name: 'super-admin',
    path: '/super-admin',
    component: AppLayout,
    redirect: { name: 'super-admin-statistic' },
    children: [
      {
        name: 'super-admin-statistic',
        path: 'statistic',
        component: () => import('../pages/statistic/Statistic.vue'),
      },
      {
        name: 'user-manage',
        path: 'user-manage',
        component: () => import('../pages/user-manage/UserManagePage.vue'),
      },
      {
        name: 'post-manage',
        path: 'post-manage',
        component: () => import('../pages/post-manage/PostManagePage.vue'),
      },
      {
        name: 'news-manage',
        path: 'news-manage',
        component: () => import('../pages/news-manage/NewsManage.vue'),
      }
    ],
  },
  {
    name: 'admin',
    path: '/admin',
    component: AppLayout,
    redirect: { name: 'admin-statistic' },
    children: [
      {
        name: 'admin-statistic',
        path: 'statistic',
        component: () => import('../pages/statistic/Statistic.vue'),
      },
      {
        name: 'resume-manage',
        path: 'resume-manage',
        component: () => import('../pages/resume-manage/ResumeManagePage.vue'),
      },
      {
        name: 'interview-manage',
        path: '/interview-manage',
        component: () => import('../pages/interview-manage/InterviewManagePage.vue'),
      },
      {
        name: 'written-manage',
        path: '/written-manage',
        component: () => import('../pages/written-manage/WrittenManagePage.vue'),
      },
    ],
  },

  {
    path: '/auth',
    component: AuthLayout,
    redirect: { name: 'login' },
    children: [
      {
        name: 'login',
        path: 'login',
        component: () => import('../pages/auth/Login.vue'),
      },
      {
        name: 'signup',
        path: 'signup',
        component: () => import('../pages/auth/Signup.vue'),
      },
      {
        name: 'recover-password',
        path: 'recover-password',
        component: () => import('../pages/auth/RecoverPassword.vue'),
      },
      {
        path: '',
        redirect: { name: 'login' },
      },
    ],
  },
  {
    name: 'front',
    path: '/front',
    component: FrontLayout,
    redirect: { name: 'home' },
    children: [
      {
        name: 'home',
        path: 'home',
        component: () => import('../pages/front/HomePage.vue'),
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    // For some reason using documentation example doesn't scroll on page navigation.
    if (to.hash) {
      return { el: to.hash, behavior: 'smooth' }
    } else {
      window.scrollTo(0, 0)
    }
  },
  routes,
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const store = useUserStore()
  // 未登录时，除去 /auth/login 和 /auth/signup 全部重定向到 /front
  if (!store.isLogin) {
    if (to.name == 'front' || to.name == 'home') {
      next()
      return
    }
    if (to.name !== 'login' && to.name !== 'signup') {
      next({ name: 'front' })
    } else {
      next()
    }
  } else {
    // 登录后，只有 role 为 user 可以访问 /front
    if ((to.name === 'front' || to.name === 'home') && store.role !== 'user') {
      next({ name: store.role }) // 重定向到对应的角色路由
    } else if (
      (to.name === 'admin' && store.role !== 'admin') ||
      (to.name === 'super-admin' && store.role !== 'super-admin')
    ) {
      // super-admin 和 admin 只能访问 /super-admin/... 或 /admin/...
      next({ name: store.role }) // 重定向到对应的角色路由
    } else {
      next() // 允许访问其他路由
    }
  }
})
export default router
