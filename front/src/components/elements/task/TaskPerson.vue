<template>
  <div class="person_component__wrapper">
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
  .person_component__wrapper {
    border-top: solid 1px #3d4551;
    padding: 3px 3px;
    margin: 3px 0;
  }

  .person_component__wrapper :last-child {
    text-align: right;
  }

  .person_component__wrapper .title {
    margin: 0;
    font-size: 21px;
  }

  .person_component__wrapper .content {
    display: block;
    text-align: right;
    margin: 5px;
    font-size: 16px;
    padding: 6px 10px;
    color: #e5e5e5;
    font-style: italic;
  }

  .person_component__wrapper .content span {
    padding: 3px 6px;
    border-radius: 8px;
    border: 0;
  }
</style>
