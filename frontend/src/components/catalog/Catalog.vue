<template>
  <div id='catalog'>
    <div id='catalog-header'>
      <h1>This will be a page with cards for each collection available.</h1>
    </div>
    <div id='catalog-cards'>
      <catalog-card v-for='card in cards' :key='card._id' :collection='card'>
      </catalog-card>
    </div>
  </div>
</template>

<script>
import CatalogCard from './CatalogCard'

export default {
  name: 'catalog',
  data () {
    return {
      cards: []
    }
  },
  created () {
    // this.$http.get('/api/list/collections').then(response => {
    //   console.log(response)
    //   this.cards = response.data
    // }).catch(error => {
    //   console.log(error)
    // })
    this.cards = [
      {
        '_id': '58646d4762f1c546d4d9973b',
        'keywords': [
          'Clinton',
          'Democrat',
          'Donald',
          'Hillary',
          'Republican',
          'Trump'
        ],
        'desc': '2016 Election',
        'activeFlag': true
      },
      {
        '_id': '5af77e1c5df0a30b38445f74',
        'keywords': [
          'Ice',
          'Lol',
          'Me',
          'You'
        ],
        'desc': 'test',
        'activeFlag': false
      },
      {
        '_id': '5af77f4b5df0a30b38445f75',
        'keywords': [
          'Beef',
          'Chicken',
          'Fish',
          'Lamb'
        ],
        'desc': 'test2',
        'activeFlag': false
      }
    ]
  },
  methods: {
  },
  components: {
    CatalogCard
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
#catalog {
  margin-bottom: 120px;
}
#catalog-header {

}
#catalog-cards {
  display: flex;
  flex-wrap: wrap;
  padding: 0 50px;
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
