<template>
    <ul class="dropdown noselect__wrapper">
        <li class="checkbox__header">
            <label v-if="title">{{title}}
                <input type="button"
                       @click="$emit('click', structureIndex)">
            </label>
        </li>
        <li v-for="(elem, index) in elements">
            <c-box :elem="elem" @input="$emit('change', {el: structureIndex, ind: index})"></c-box>
        </li>
    </ul>
</template>

<script>
    import CBox from './CBox'

    export default {
        name: "CheckBox",
        components: {CBox},
        comments: {cBox: CBox},
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

    .dropdown {
        display: inline-block;
        font-size: 16px;
        width: 260px;
        /*background: #d94f5c;*/
        padding: 0 0 0 15px;
        margin: 15px 0;
    }

    .dropdown li {
        list-style-type: none;
        background-color: #333333;
        color: #4FB9A7;
        cursor: pointer;
    }

    .checkbox__header label:hover {
        background-color: #231a1a;
    }

    /* Customize the label (the container) */

    .checkbox__header {
        text-align: left;
    }

    .checkbox__header label {
        padding: 10px 100px 10px 10px;
        display: block;
    }

    .checkbox__header input {
        display: none;
    }

    .checkbox__header li {
        list-style-type: none;
        padding: 15px;
        background-color: #333333;
        color: #4FB9A7;
        margin-bottom: 1px;
        cursor: pointer;
    }
</style>
