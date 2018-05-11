import Vue from 'vue'
import Router from 'vue-router'
import Catalog from '@/components/catalog/Catalog'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Catalog',
      component: Catalog
    }
  ]
})
