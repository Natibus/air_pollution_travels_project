import Vue from 'vue'
import Router from 'vue-router'
import Map from './views/Map.vue'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Simulation',
      component: Map
    },
    {
      path: '/scope',
      name: 'Scope',
      component: () => import(/* webpackChunkName: "about" */ './views/Scope.vue')
    },
    {
      path: '/events',
      name: 'Events',
      component: () => import(/* webpackChunkName: "about" */ './views/Events.vue')
    },
  ]
})
