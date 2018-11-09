<template>
  <div class="comments_wrapper">
    <searching :placeHolder="'comments'" v-model="searchParam">
    </searching>
      <comment v-for="(com, index) in comments" :key="index" :comment="com" v-if="isContains(com)"></comment>
  </div>
</template>

<script>
  import Comment from "./Comment"
  import SearchingLine from "../functional/SearchingLine"
  
  export default {
    name: "CommentsList",
    components: {
      comment: Comment,
      searching: SearchingLine
    },
  
    data: function(){
      return{
        searchParam: ''
      }
    },
    
    props: {comments: undefined},
  
    methods: {
      isContains(comment) {
        if (this.searchParam != '') {
          let param = this.searchParam.toLowerCase();
          return comment.comment.toLowerCase().includes(param) ||
            comment.author.firstname.toLowerCase().includes(param) ||
            comment.author.secondname.toLowerCase().includes(param) ||
            comment.commentDate.includes(param)
        } else {
          return true;
        }
      }
    }
  }
</script>

<style scoped>
  .comments_wrapper h1 {
    text-align: center;
  }
</style>
