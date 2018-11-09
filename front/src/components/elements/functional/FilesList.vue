<template>
    <div class="details__wrapper" v-if="!isEmpty(filesNames)">
        <p class="title">{{title}}</p>
        <p
                class="content clickable"
                v-for="name in filesNames"
                @click="$emit('click',name)">{{name}}</p>
    </div>
</template>

<script>
    export default {
        name: "FilesList",
        props: ["title", "filesNames"]
    }
</script>

<style scoped>
    .content {
        margin: 5px;
        font-size: 15px;
        padding: 6px 10px;
        background: #39403C;
        border-radius: 10px;
    }
    .details__wrapper {
        background: #333333;
        margin: 10px 0;
        padding: 1px 5px;
        border-radius: 8px;
    }

    .details__wrapper h3 {
        border-top: 1px solid #333;
    }
</style>