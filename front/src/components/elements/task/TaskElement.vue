<template>
    <div class="component__wrapper">
        <p class="title"><span>{{title}}: </span></p>
        <p class="content"><span>{{field}}</span></p>
    </div>
</template>

<script>
    export default {
        name: "TaskElement",
        props: ["title", "field"]

    }
</script>

<style scoped>

</style>
