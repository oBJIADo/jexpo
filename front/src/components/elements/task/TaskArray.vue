<template>
    <div class="component__wrapper">
        <p class="title"><span>{{title}}: </span></p>
        <p class="content" v-for="elem in array"><span>{{elem}}</span></p>
    </div>
</template>

<script>
    export default {
        name: "TaskArray",
        props: ["title", "array"]
    }
</script>

<style scoped>

</style>
