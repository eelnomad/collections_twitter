<template>
  <div id='new-collection'>
    <div id='header'>
      <h1>{{ $route.name }}</h1>
    </div>
    <div class="button-bar">
      <div class="button" @click="newCollection()">
        <h2>CREATE</h2>
      </div>
      <router-link class="button" :to="{ path: '/' }">
        <h2>BACK</h2>
      </router-link>
    </div>
    <div id="header-block"></div>
    <form>
      <h1>Collection Description</h1>
      <input type="text" placeholder="Healthy Foods" v-model="title">
      <h2>Keywords</h2>
      <input type="text" @keyup.enter="addKeyword" v-model="word" placeholder="Pizza, Burgers, Fries, Kale, etc.">
      <ul>
        <li v-for="(keyword, index) in keywords" :key="keyword">
          <div class="keyword">{{ keyword }}</div>
          <a @click="remove(index)">
            <icon name="times" scale=1 class="icon"></icon>
          </a>
        </li>
      </ul>
    </form>
    <div id="header-block"></div>
  </div>
</template>

<script>
export default {
  name: 'new-collection',
  data () {
    return {
      title: '',
      keywords: [],
      word: ''
    }
  },
  created () {
  },
  methods: {
    newCollection: function () {
      this.$http.put('api/new', {}, {
        params: {
          keywords: this.keywords.join(','),
          desc: this.title
        }
      }).then(response => {
        this.$router.push('/')
      }).catch(error => {
        console.log(this.keywords.join(','))
        console.log(error)
      })
    },
    addKeyword: function () {
      this.word.split(',').forEach(word => {
        var w = word.trim()
        if (w === '') return alert('No empty keywords allowed')
        if (this.keywords.indexOf(w) !== -1) return alert('Keyword already exists')
        this.keywords.push(w)
      })
      this.word = ''
    },
    remove: function (index) {
      this.keywords.splice(index, 1)
    }
  },
  components: {
  },
  computed: {
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
#new-collection {
  width: 100%;
  height: 100%;
  position: absolute;
}

.button-bar {
  position: fixed;
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
  height: 110px;
  bottom: 0;
  right: 0;
  z-index: 3;
  padding: 10px 20%;
  width: 60%;
  background: -webkit-linear-gradient(bottom, white 40%, transparent);
  background: -moz-linear-gradient(bottom, white 40%, transparent);
  background: -ms-linear-gradient(bottom, white 40%, transparent);
  background: -o-linear-gradient(bottom, white 40%, transparent);
  background: linear-gradient(bottom, white 40%, transparent);
}

.button {
  margin: 0 20px 0;
  cursor: pointer;
  background-color: white;
  height: 60px;
  width: 160px;
  bottom: 10px;
  justify-content: center;
  align-items: center;
  display: flex;
  color: #2c3e50;
  border-radius: 10px;
  border-style: solid;
  border-width: 2px;
}

.button:hover {
  background-color: #2c3e50;
  color: white;
}

.icon {
  position: relative;
  top: .08em;
}

.keyword {
  margin-right: 10px;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

input {
  margin-left: 20%;
  width: 60%;
  height: 60px;
  font-size: 30px;
  padding-left: 20px;
  margin-bottom: 20px;
  border-radius: 7px;
}

input:focus {
  outline: none;
  animation: pulse 2s infinite;
}

form > h1, form > h2 {
  margin-left: 20%;
}

h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
  margin-left: 10%;
  width: 80%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
li {
  display: flex;
  justify-content: space-between;
  margin: 10px 10px;
  padding: 6px 10px 6px 15px;
  font-weight: normal;
  font-size: 20px;
  border-color: #2c3e50;
  border-width: 2px;
  border-style: solid;
  color: #2c3e50;
  border-radius: 20px;
  max-width: 80%;
}
li:hover {
  background-color: #2c3e50;
  color: white;
}
a{
    text-decoration: none;
}

@-webkit-keyframes pulse {
  0% {-webkit-box-shadow: 0 0 0 0 rgba(204,169,44, 0.4) inset}
  70%{-webkit-box-shadow: 0 0 0 10px rgba(204,169,44, 0) inset}
  100%{-webkit-box-shadow: 0 0 0 0 rgba(204,169,44, 0) inset}
}
@keyframes pulse {
  0% {
    -moz-box-shadow: 0 0 0 0 rgba(204,169,44, 0.4) inset;
    box-shadow: 0 0 0 0 rgba(204,169,44, 0.4) inset;
  }
  70% {
      -moz-box-shadow: 0 0 0 10px rgba(204,169,44, 0) inset;
      box-shadow: 0 0 0 10px rgba(204,169,44, 0) inset;
  }
  100% {
      -moz-box-shadow: 0 0 0 0 rgba(204,169,44, 0) inset;
      box-shadow: 0 0 0 0 rgba(204,169,44, 0) inset;
  }
}
</style>
