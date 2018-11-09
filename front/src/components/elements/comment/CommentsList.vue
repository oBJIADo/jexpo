<template>
  <div class="comments_wrapper">
    <searching :placeHolder="'comments'" v-model="searchParam">
    </searching>
    <ul v-for="com in comments">
      <comment :comment="com" v-if="isContains(com)"></comment>
    </ul>
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

  .comments_wrapper ul {
    /*padding: 10px 20px;*/
    background: #333333;
    border-radius: 10px;
  }

  .comments_wrapper li {
    padding: 5px 5px;
    margin: 20px 10px;
    background: #39403C;
    border-radius: 20px;
  }

  .comments_wrapper li p{
    color: #aaaaaa;
  }
</style>
