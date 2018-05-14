<template>
  <div id="catalog-card" :class="classes" :tabindex=0 @focus="toggleFocus()" @blur="toggleFocus()" v-on:click="animateScroll()">
    <h1> {{collection.desc}} </h1>
    <ul>
      <li v-for="keyword in keywords" :key="keyword">{{keyword}}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'catalog-card',
  props: ['collection'],
  data () {
    return {
      mongo_id: this.collection._id,
      desc: this.collection.desc,
      active: this.collection.activeFlag,
      selected: false,
      keywords: this.collection.keywords
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    toggleFocus: function () {
      this.selected = !this.selected
    },
    animateScroll: function () {
      console.log(this.$el)
    }
  },
  computed: {
    classes: function () {
      return {
        'in-focus': this.selected,
        'no-focus': !this.active && !this.selected,
        active: this.active && !this.selected
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#catalog-card {
  height: 200px;
  flex: 1 0 300px;
  margin: 5px;
  padding: 0px 30px;
  border-radius: 10px;
  box-shadow: 0 1px 1px #777777;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  outline: none;
}

.in-focus {
  box-shadow: 0 0 0 3px #fff, 0 0 0 5px #ddd, 0 0 0 10px #fff, 0 0 2px 10px #eee;
  background-color: #FFEAEE;
}
.no-focus {
  background-color: #EEEEEE;
}

.active {
  background-color: #FFDAEE;
}

h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
  height: 100px;
  overflow-y: hidden;
  background-color: gray;
}
li {
}
a {
  color: #42b983;
}
button {
  height: 100%;
  width: 100%;
}
</style>
