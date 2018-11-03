<template>
  <div class="searching__line">
    <form>
      <input type="text"
             v-bind:placeholder="getSearchString()"
             v-model="searchParam"
             @input="$emit('input', searchParam)">
      <button @click="$emit('submit', searchParam)"></button>
    </form>
  </div>
</template>

<script>
  export default {
    name: "SearchingLine",
    props: {
      placeHolder: '',
    },
    data: function () {
      return {
        searchParam: ''
      }
    },
    methods: {
      getSearchString: function () {
        return ('Search ' + (this.placeHolder === undefined ? '' : this.placeHolder) + '...');
      },
      
      outQuery: function () {
        //comments
        //text
        //num
        //simpleEntity
        //ManyToMany
        //Employees
      }
    }
  }
</script>

<style scoped>

</style>
