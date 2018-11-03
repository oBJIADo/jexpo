<template>
    <div>
        <div class="custom_cb__wrapper" @click="$emit('click', value)">
            <!--<span v-show="isCheked">Hello</span>-->
            <span>{{checked}}</span>
        </div>
        <p @click="logout()">BIGBUTTON</p>
    </div>
</template>

<script>
    export default {
        name: "CustomCheckBox",
        props: [
            'checked',
            'title',
            'value'
        ],

        data: function () {
            return {
                isCheked: this.checked
            }
        },


        methods: {
            logout: function () {
                console.log(this.checked);
            }
        }

        // computed: {
        //     getChecked: function () {
        //         return this.checked
        //     }
        // }
    }
</script>

<style scoped>

</style>