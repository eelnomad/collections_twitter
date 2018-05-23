<template>
  <div id="catalog-card" @click="$emit('selection', collection._id)">
    <h1> {{collection.desc}} </h1>
    <!-- <icon name="spinner" pulse v-if="active" scale=2></icon> -->
    <ul>
      <li v-for="keyword in collection.keywords" :key="keyword">{{keyword}}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'catalog-card',
  props: ['collection'],
  data () {
    return {
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    toggleFocus: function () {
      this.selected = !this.selected
    }
  },
  computed: {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#catalog-card {
  margin: 10px 5px 10px 5px;
  padding: 0px 30px 20px 30px;
  box-sizing: border-box;
  border-radius: 5px;
  border: 1px solid #DDDDDD;
  background-color: #EEEEEE;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  outline: none;
  z-index: 99;
}

#catalog-card.in-focus {
  background-color: pink;
}

h1, h2 {
  font-weight: normal;
  width: 200px;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin: 10px 0;
}
a {
  color: #42b983;
}
button {
  height: 100%;
  width: 100%;
}
</style>
