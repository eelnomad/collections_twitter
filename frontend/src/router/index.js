import Vue from 'vue'
import Router from 'vue-router'
import Catalog from '@/components/catalog/Catalog'
import NewCollection from '@/components/newCollection/NewCollection'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Collections',
      component: Catalog
    },
    {
      path: '/new',
      name: 'New Collection',
      component: NewCollection
    }
  ]
})
