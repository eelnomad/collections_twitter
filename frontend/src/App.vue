<template>
  <div id="app">
    <transition name="component-fade">
      <router-view/>
    </transition>
  </div>
</template>

<script>
import 'normalize.css/normalize.css'
export default {
  name: 'App',
  head: {
    title: {
      inner: 'Collections'
    },
    link: [
      {
        rel: 'icon',
        type: 'image/png',
        href: 'static/images/favicon.png'
      }
    ]
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
  width: 100%;
}
html {
    overflow: scroll;
    overflow-x: hidden;
}
::-webkit-scrollbar {
    width: 0px;  /* remove scrollbar space */
    background: transparent;  /* optional: just make scrollbar invisible */
}
/* optional: show position indicator in red */
::-webkit-scrollbar-thumb {
    background: #FF0000;
}
#header {
  display: flex;
  justify-content: center;
  width: 100%;
  background: -webkit-linear-gradient(top, white, transparent);
  background: -moz-linear-gradient(top, white, transparent);
  background: -ms-linear-gradient(top, white, transparent);
  background: -o-linear-gradient(top, white, transparent);
  background: linear-gradient(top, white, transparent);
  position: fixed;
  top: 0;
  font-size: 20px;
}
#header-block {
  height: 120px;
}

.component-fade-enter-active, .component-fade-leave-active {
  transition: opacity 1.2s ease-in;
}

.component-fade-enter-active {
  transition-delay: 1s;
}

.component-fade-enter, .component-fade-leave-to
/* .component-fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
