<template>
    <div class="comment__wrapper">
        <person :employee="comment.author"></person>
        <p>{{comment.commentDate}}</p>
        <text-component :text="comment.comment"/>
    </div>
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
        methods: {}
    }
</script>

<style scoped>
    .comment__wrapper {
        /*padding: 10px 20px;*/
        color: #e5e5e5;
        border-top: solid 2px #3d4551;
    }

    .comment__wrapper {
        padding: 2px 5px;
    }
</style>

