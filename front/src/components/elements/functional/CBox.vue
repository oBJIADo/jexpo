<template>
    <label class="container">{{elem.name}}
        <input type="checkbox"
               :name="elem.name"
               :checked="elem.value"
               @input="$emit('input')"
        />
        <span class="checkmark"></span>
    </label>
</template>

<script>
    export default {
        name: "CBox",
        props: ["elem"]
    }
</script>

<style scoped>
    .container:hover{
        background-color: #231a1a;
    }

    .container {
        position: relative;
        cursor: pointer;
        padding: 10px 10px 10px 40px;
        font-size: 17px;
        display: block;
    }

    /* Hide the browser's default checkbox */

    .container input {
        position: absolute;
        opacity: 0;
        cursor: pointer;
    }

    /* Create the checkmark/indicator (hidden when not checked) */

    .checkmark:after {
        content: "";
        position: absolute;
        display: none;
    }

    /* Show the checkmark when checked */

    .container input:checked ~ .checkmark:after {
        display: block;
    }

    /* Style the checkmark/indicator */

    .container .checkmark:after {
        left: 15px;
        top: 12px;
        width: 5px;
        height: 10px;
        border: solid #d94f5c;
        border-width: 0 3px 3px 0;
        -webkit-transform: rotate(45deg);
        -ms-transform: rotate(45deg);
        transform: rotate(45deg);
    }
</style>