<template>
    <ul class="dropdown">
        <li class="checkbox__header">
            <label v-if="title">{{title}}
                <input type="button"
                       @click="$emit('click', structIndex)">
            </label>
        </li>
        <li v-for="value in indexes">
            <!--<label class="container">{{fieldsMapping[value]}}-->
            <!--<input type="checkbox"-->
            <!--:name="fieldsMapping[value]"-->
            <!--:checked="boolArray[value]"-->
            <!--@input="boolArray[value]=$event.target.checked"-->
            <!--/>-->
            <!--<span class="checkmark"></span>-->
            <!--</label>-->
            <div class="container">
                <custom-check-box
                        :key="value"
                        :checked="boolArray[value]"
                        :title="fieldsMapping[value]"
                        :value="value"
                        @click="$emit('change',$event)"
                />
            </div>
        </li>
    </ul>
</template>

<script>
    import CustomCheckBox from './CustomCheckBox'

    export default {
        name: "CheckBox",
        components: {CustomCheckBox},
        comments: {
            customCheckBox: CustomCheckBox
        },

        props: [
            'structIndex',
            'title',
            'fieldsMapping',
            'indexes',
            'boolArray',
        ],

        // created: function () {
        //     console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //     console.log("structIndex");
        //     console.log(this.structIndex);
        //     console.log("title");
        //     console.log(this.title);
        //     console.log("fieldsMapping");
        //     console.log(this.fieldsMapping);
        //     console.log("indexes");
        //     console.log(this.indexes);
        //     console.log("boolArray");
        //     console.log(this.boolArray);
        //     console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // }
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
