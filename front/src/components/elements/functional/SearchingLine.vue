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
    .searching__line {
        padding: 15px 0;
    }

    .searching__line:after {
        content: "";
        clear: both;
        display: table
    }

    .searching__line input {
        width: auto;
        float: right;
        margin-right: 30px;
    }

    .searching__line input {
        width: 250px;
        height: 42px;
        padding-left: 15px;
        border-radius: 42px;
        color: #aaa;
        border: 2px solid #d94f5c;
        background: #333333;
        outline: none;
        position: relative;
        transition: .3s linear;
    }

    .searching__line input:focus {
        width: 300px;
    }

    .searching__line button {
        width: 42px;
        height: 42px;
        background: none;
        border: none;
        position: absolute;
        top: -2px;
        right: 0;
    }

    .searching__line button:before {
        content: "\f002";
        color: #324b4e;
    }
</style>
