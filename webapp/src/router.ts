import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/mine',
      name: 'mine',
      component: () => import( './views/Mine.vue')
    },
    {
      path: '/challenge/:id?',
      name: 'challenge',
      props: true,
      component: () => import( './views/Challenge.vue')
    },
    {
      path: '/my',
      name: 'my',
      component: () => import( './views/My.vue')
    },{
      path: '/profile',
      name: 'profile',
      component: () => import( './views/Profile.vue')
    },
    {
      path: '/info',
      name: 'info',
      component: () => import( './views/Info.vue')
    },
    {
      path: '/redirect',
      name: 'redirect',
      component: () => import( './views/Redirect.vue')
    }
  ]
})
