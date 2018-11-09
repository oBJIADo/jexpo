<template>
    <div v-if="" class="array_component__wrapper">
        <div class="title"><span>{{title}}: </span></div>
        <div class="content"
             v-if="!Array.isArray(input)"
             @click="clickIfClickable(input)">
            <span :class="isClickable ? 'clickable' : ''">{{input}}</span>
        </div>
        <div v-else v-for="elem in input"
             class="content"
             @click="clickIfClickable(elem)">
            <span :class="isClickable ? 'clickable' : ''">{{elem}}</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "TaskElement",
        props: ["title", "input", "isClickable"],
        methods: {
            clickIfClickable: function (elem) {
                if (this.isClickable) {
                    this.$emit('click', elem)
                }
            }
        },
    }
</script>

<style scoped>
    .array_component__wrapper {
        border-top: solid 1px #3d4551;
        padding: 3px 3px;
        margin: 3px 0;
    }

    .array_component__wrapper :last-child {
        text-align: right;
    }

    .array_component__wrapper .title {
        margin: 0;
        font-size: 20px;
        color: #777777;
    }

    .array_component__wrapper .content {
        display: block;
        text-align: right;
        margin: 5px;
        font-size: 16px;
        padding: 6px 10px;
        color: #e5e5e5;
        font-style: italic;
    }

    .array_component__wrapper .content span {
        padding: 3px 6px;
        border-radius: 8px;
        border: 0;
    }
</style>
