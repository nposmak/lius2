import Vue from 'vue'
import VueRouter from 'vue-router'

import TitlePage from '../components/TitlePage.vue'
import LogIn from '../components/LogIn.vue'
import RegUser from '../components/RegUser.vue'
import CreateProp from '../components/CreateProp.vue'
import MyProps from '../components/MyProps.vue'

Vue.use(VueRouter)

const routes = [
  {path: '/', component: TitlePage},
  {path: '/login', component: LogIn },
  {path: '/register', component: RegUser},
  {path:'/prop/new', component: CreateProp},
  {path:'/prop/myprops', component: MyProps}

  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
