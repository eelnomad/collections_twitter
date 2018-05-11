<template>
  <div id="catalog-card" :class="[ collection.activeFlag ? 'active' : 'inactive' ]" :tabindex=0>
    <h1> {{collection.desc}} </h1>
    <ul>
      <li v-for="keyword in collection.keywords" :key="keyword">{{keyword}}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'catalog-card',
  props: ['collection'],
  created () {
  },
  methods: {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#catalog-card {
  height: 200px;
  list-style-type: none;
  flex: 1 0 25%;
  margin: 20px;
  padding: 0px 10px;
  border-radius: 10px;
  box-shadow: 0 1px 1px #777777;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

#catalog-card:focus {
  box-shadow: 0 0 0 3px #fff, 0 0 0 5px #ddd, 0 0 0 10px #fff, 0 0 2px 10px #eee;
  outline: none;
  background-color: #FFEAEE;
}

.active {
  background-color: #FFDAEE;
}
.inactive {
  background-color: #EEEEEE;
}

h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: inherit;
  padding: 0;
}
li {
  margin: 0 10px;
}
a {
  color: #42b983;
}
button {
  height: 100%;
  width: 100%;
}
</style>
