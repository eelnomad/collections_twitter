<template>
  <div id="catalog">
    <div id="catalog-header">
      <h1>This will be a page with cards for each collection available.</h1>
    </div>
    <div id="catalog-cards">
      <catalog-card v-for="card in cards" :key="card._id" :collection="card">
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
        '_id': 'pizza',
        keywords: ['nice', 'no', 'ice', 'yay'],
        activeFlag: true,
        desc: 'Testing'
      },
      {
        '_id': 'pizza1',
        keywords: ['nice', 'no', 'ice', 'yay'],
        activeFlag: false,
        desc: 'TestingTestingTestingTestingTestingTestingTesting'
      },
      {
        '_id': 'pizza2',
        keywords: ['nice', 'no', 'ice', 'yay'],
        activeFlag: false,
        desc: 'Testing'
      },
      {
        '_id': 'pizza3',
        keywords: ['nice', 'no', 'ice', 'yay'],
        activeFlag: false,
        desc: 'Testing'
      },
      {
        '_id': 'pizza4',
        keywords: ['nice', 'no', 'ice', 'yay'],
        activeFlag: false,
        desc: 'Testing'
      },
      {
        '_id': 'pizza5',
        keywords: ['nice', 'no', 'ice', 'yay'],
        activeFlag: false,
        desc: 'Testing'
      }
    ]
  },
  components: {
    CatalogCard
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#catalog {
  margin-bottom: 120px;
}
#catalog-header {

}
#catalog-cards {
  display: flex;
  flex-wrap: wrap;
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
