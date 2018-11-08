<template>
    <div class='searching__line noselect__wrapper'>
        <form>
            <input type="text"
                   v-bind:placeholder="getSearchString()"
                   v-model="searchParam"
                   @input="$emit('input', searchParam)">
            <button @click="$emit('submit', searchParam)"></button>
        </form>
    </div>
</template>

<script>
    export default {
        name: "SearchingLine",
        props: [
            "placeHolder", "defaultValue"
        ],
        data: function () {
            return {
                searchParam: (this.defaultValue === undefined) ? '' : this.defaultValue
            }
        },
        methods: {
            getSearchString: function () {
                return ('Search ' + (this.placeHolder === undefined ? '' : this.placeHolder) + '...');
            }
        }
    }
</script>

<style scoped>

</style>
