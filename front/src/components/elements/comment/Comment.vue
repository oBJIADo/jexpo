<template>
  <li>
    <person :employee="comment.author"></person>
    <p>{{comment.commentDate}}</p>
    <text-component :text="comment.comment"/>
  </li>
</template>

<script>
  import Person from "../employees/Person"
  import TextComponent from "../functional/TextComponent"
  import DynRenderText from "../functional/DynRenderText"
  
  // <span v-html="addLinksToComment()"></span>
  export default {
    name: "Comment",
    components: {
      person: Person,
      textComponent: TextComponent,
      dynRenderText: DynRenderText
    },
    props: {
      comment: {
        author: {type: String},
        commentDate: {type: String},
        comment: {type: String}
      }
    },
    methods: {
    
    }
  }
</script>

<style scoped>

</style>

