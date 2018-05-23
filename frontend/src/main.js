// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import http from './http'
import router from './router'
import icon from './icon'
import head from './head'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  http,
  head,
  icon,
  components: { App },
  template: '<App/>'
})
