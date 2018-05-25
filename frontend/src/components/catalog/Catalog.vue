<template>
  <div id="catalog">
    <div id='header'>
      <h1>{{ $route.name }}</h1>
    </div>
    <div id="header-block"></div>
    <div id="columns">
      <div id="column" v-for='(column, index) in columns' :key='index'>
        <transition-group name="list-complete">
          <catalog-card v-for='card in column' :key='card._id' :collection='card' @selection="onFocus" :class="{'in-focus': card._id === selection._id, 'active': card.activeFlag}">
          </catalog-card>
        </transition-group>
      </div>
      <div id="fab-bar">
        <router-link :to="{ path: '/new' }" class="fab">
          <icon label="new">
            <icon name="circle" scale=4.2 class="fab-color-1"></icon>
            <icon name="circle" scale=4 class="fab-color-2"></icon>
            <icon name="plus" scale=2 center class="fab-color-1"></icon>
          </icon>
        </router-link>
        <div class="fab" @click="selection.activeFlag ? stopCollection() : runCollection()" v-show="selection._id !== ''">
          <icon label="run">
            <icon name="circle" scale=4.2 class="fab-color-1"></icon>
            <icon name="circle" scale=4 class="fab-color-2"></icon>
            <icon :name="selection.activeFlag ? 'stop' : 'play'" scale=2 center class="fab-color-1"></icon>
          </icon>
        </div>
        <div class="fab" @click="deleteCollection()" v-show="selection._id !== ''">
          <icon label="delete">
            <icon name="circle" scale=4.2 class="fab-color-1"></icon>
            <icon name="circle" scale=4 class="fab-color-2"></icon>
            <icon name="trash-alt" scale=2 center class="fab-color-1"></icon>
          </icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CatalogCard from './CatalogCard'

export default {
  name: 'catalog',
  data () {
    return {
      cards: [],
      sections: 0,
      selected: ''
    }
  },
  created () {
    this.sections = Math.max(Math.floor(document.documentElement.clientWidth / 300), 1)
    this.resetSelection()
    this.listCollection()
  },
  mounted () {
    window.addEventListener('resize', this.setColumns)
  },
  beforeDestroy () {
    window.removeEventListener('resize', this.setColumns)
  },
  watch: {
  },
  methods: {
    listCollection: function () {
      // this.$http.get('/api/list/collections').then(response => {
      //   this.$set(this, 'cards', response.data)
      // }).catch(error => {
      //   console.log(error)
      // })

      this.cards = [
        {
          '_id': '586c21aac0c8fa262cbc7524',
          'keywords': [
            'Hot chocolate',
            'Magi',
            'Milky way',
            'Passionate chocolate',
            'Rebbeca',
            'Sinbad',
            'Trollhunter'
          ],
          'desc': 'Old Woman',
          'activeFlag': false
        },
        {
          '_id': '586edb3cc0c8fa16947e1745',
          'keywords': [
            'Cashmere',
            'Dress',
            'Jeans',
            'Sweater'
          ],
          'desc': 'Fashion',
          'activeFlag': false
        },
        {
          '_id': '586edcc2c0c8fa16947e1747',
          'keywords': [
            'Activia',
            'Chobani',
            'Dannon',
            'Light n\' fit',
            'Oikos',
            'Yogurt',
            'Yoplait'
          ],
          'desc': 'Yogurt',
          'activeFlag': true
        },
        {
          '_id': '586ee002c0c8fa16947e1748',
          'keywords': [
            'Cowboys',
            'Football',
            'Giants',
            'Nfl',
            'Pats',
            'Superbowl'
          ],
          'desc': 'Sports',
          'activeFlag': false
        },
        {
          '_id': '586ee2efc0c8fa26988d69e4',
          'keywords': [
            'Dove',
            'Garnier',
            'Loreal',
            'Maybelline',
            'Milk',
            'Nars'
          ],
          'desc': 'Beauty',
          'activeFlag': false
        },
        {
          '_id': '586ee4a5c0c8fa26988d69e5',
          'keywords': [
            'Absolut',
            'Bruchladder',
            'Cointreau',
            'Cointreau noir',
            'Hennessy',
            'Louis xiii',
            'Mount gay',
            'Remy',
            'Remy xo',
            'Skyy',
            'Vsop'
          ],
          'desc': 'Booooze',
          'activeFlag': false
        },
        {
          '_id': '5873b1bfd48fa71f9c47faed',
          'keywords': [
            'Go home',
            'Home sweet home',
            'Immigrate',
            'Move'
          ],
          'desc': 'Canada',
          'activeFlag': false
        },
        {
          '_id': '5873ec2bd48fa70fe8dbbf91',
          'keywords': [
            'Mountain bicycle',
            'Mountain bike',
            'Specialized bike',
            'Stumpjumper'
          ],
          'desc': 'Specialized Bike',
          'activeFlag': false
        },
        {
          '_id': '58740297ad251a0f5878d630',
          'keywords': [
            'Clinton',
            'Democrat',
            'Donald',
            'Hillary',
            'Republican',
            'Trump'
          ],
          'desc': '2016 Election',
          'activeFlag': false
        },
        {
          '_id': '58866b8d5fae32135c39292c',
          'keywords': [
            'Cheese',
            'Pepperoni',
            'Tomato sauce',
            'Vegan cheese'
          ],
          'desc': 'Pizza',
          'activeFlag': false
        },
        {
          '_id': '5abe8891d4b46f2ef834509e',
          'keywords': [
            'Southeast asia oil shipping'
          ],
          'desc': 'Oil Shipping',
          'activeFlag': false
        },
        {
          '_id': '5abe8f2dd4b46f2ef834509f',
          'keywords': [
            'Brownies',
            'Pizza',
            'Potato chips'
          ],
          'desc': 'Not-so Guilty Pleasures',
          'activeFlag': false
        },
        {
          '_id': '5abe9f70d4b46f269039495f',
          'keywords': [
            'Happy hour',
            'Lime',
            'Margarita',
            'Tequilla'
          ],
          'desc': 'wednesday',
          'activeFlag': false
        }
      ]
    },
    runCollection: function () {
      this.$http.get('api/run', {
        params: {
          collectionId: this.selection._id
        }
      }).then(response => {
        this.listCollection()
      }).catch(error => {
        console.log(error)
      })
    },
    stopCollection: function () {
      this.$http.get('api/stop', {
        params: {
          collectionId: this.selection._id
        }
      }).then(response => {
        this.listCollection()
      }).catch(error => {
        console.log(error)
      })
    },
    deleteCollection: function () {
      this.$http.get('api/delete', {
        params: {
          collectionId: this.selection._id
        }
      }).then(response => {
        this.listCollection()
      }).catch(error => {
        console.log(error)
      })
    },
    onFocus: function (id) {
      this.selected = this.selected === id ? '' : id
    },
    resetSelection: function () {
      this.selected = ''
    },
    setColumns: function () {
      if (this.sections !== Math.floor(document.documentElement.clientWidth / 300)) {
        this.sections = Math.max(Math.floor(document.documentElement.clientWidth / 300), 1)
      }
    }
  },
  components: {
    CatalogCard
  },
  computed: {
    columns: function () {
      var height = []
      var result = []
      for (var i = 0; i < this.sections; i++) {
        height.push(0)
        result.push([])
      }
      for (i = 0; i < this.cards.length; i++) {
        var min = height.indexOf(Math.min(...height))
        height[min] += this.cards[i].keywords.length + 5
        result[min].push(this.cards[i])
      }
      return result
    },
    selection: function () {
      for (var i = 0; i < this.cards.length; i++) {
        if (this.cards[i]._id === this.selected) {
          return this.cards[i]
        }
      }
      return {_id: ''}
    }
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
#catalog {
  width: 100%;
  height: 100%;
  position: absolute;
}

#backdrop {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
}

#columns {
  display: flex;
  box-sizing: border-box;
  padding: 0 3%;
  margin-bottom: 120px;
}

#column {
  flex: 1 0 auto;
}

#fab-bar {
  margin: auto;
  position: fixed;
  display: flex;
  flex-direction: row-reverse;
  bottom: 5%;
  right: 5%;
  z-index: 3;
}

.fab {
  cursor: pointer;
}

.fab-color-1 {
  color: white;
}
.fab-color-2 {
  color: #2c3e50;
}
.fab-color-2:hover {
  color: linear-gradient(143deg, #ed6282, #edab62, #c6ed62, #62ed9e);
}

.in-focus {
  animation: pulse 2s infinite;
  background-color: pink;
}

.active {
  background: linear-gradient(143deg, #ed6282, #edab62, #c6ed62, #62ed9e);
  background-size: 800% 800%;

  -webkit-animation: gradient 18s ease infinite;
  -moz-animation: gradient 18s ease infinite;
  animation: gradient 18s ease infinite;
}

.in-focus.active {
  animation: pulse 2s infinite, gradient 18s ease infinite;
}

@-webkit-keyframes pulse {
  0% {-webkit-box-shadow: 0 0 0 0 rgba(204,169,44, 0.4)}
  70%{-webkit-box-shadow: 0 0 0 10px rgba(204,169,44, 0)}
  100%{-webkit-box-shadow: 0 0 0 0 rgba(204,169,44, 0)}
}
@keyframes pulse {
  0% {
    -moz-box-shadow: 0 0 0 0 rgba(204,169,44, 0.4);
    box-shadow: 0 0 0 0 rgba(204,169,44, 0.4);
  }
  70% {
      -moz-box-shadow: 0 0 0 10px rgba(204,169,44, 0);
      box-shadow: 0 0 0 10px rgba(204,169,44, 0);
  }
  100% {
      -moz-box-shadow: 0 0 0 0 rgba(204,169,44, 0);
      box-shadow: 0 0 0 0 rgba(204,169,44, 0);
  }
}

@-webkit-keyframes gradient {
    0%{background-position:31% 0%}
    50%{background-position:70% 100%}
    100%{background-position:31% 0%}
}
@-moz-keyframes gradient {
    0%{background-position:31% 0%}
    50%{background-position:70% 100%}
    100%{background-position:31% 0%}
}
@keyframes gradient {
    0%{background-position:31% 0%}
    50%{background-position:70% 100%}
    100%{background-position:31% 0%}
}

h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
