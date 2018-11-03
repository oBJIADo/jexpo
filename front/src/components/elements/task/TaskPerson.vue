<template>
  <div class="component__wrapper">
    <p class="title" v-if="title!==undefined"><span>{{title}}: </span></p>
    <person  :employee="employee" class="content"></person>
  </div>
</template>

<script>
  import Person from "../employees/Person"
  
  export default {
    name: "TaskPerson",
    components: {person: Person},
    props: {
      title: undefined,
      employee: undefined
    }
  }
</script>

<style scoped>

</style>
