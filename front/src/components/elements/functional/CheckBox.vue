<template>
    <ul class="dropdown noselect__wrapper">
        <li class="checkbox__header">
            <label v-if="title">{{title}}
                <input type="button"
                       @click="$emit('click', structureIndex)">
            </label>
        </li>
        <li v-for="(elem, index) in elements">
            <label class="container">{{elem.name}}
            <input type="checkbox"
            :name="elem.name"
            :checked="elem.value"
            @input="$emit('change', {el: structureIndex, ind: index})"
            />
            <span class="checkmark"></span>
            </label>
        </li>
    </ul>
</template>

<script>

    export default {
        name: "CheckBox",

        // props: {
        //     structureIndex : {type: Number},
        //     title : {type: String},
        //     elements: [
        //         {
        //             name: {type: String},
        //             index: {type: Number},
        //             value: {type: Boolean}
        //         }
        //     ],
        // },
        props:["structureIndex","title","elements"],
    }
</script>

<style scoped>
    .checkbox__header li {
        list-style-type: none;
        padding: 15px;
        background-color: #333333;
        color: #4FB9A7;
        margin-bottom: 1px;
        cursor: pointer;
    }
</style>
