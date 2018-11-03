<template>
    <div>
        <h1>It hurts >_<</h1>
        <h1>from: {{from}}</h1>
    </div>
</template>

<script>
    export default {
        name: "Error",
        data: function () {
            return {
                from: ""
            }
        },
        watch: {
            '$route'(to, from) {
                this.from = from.name;
                console.log(from);
            }
        }
    }
</script>

<style scoped>
    h1 {
        text-align: center;
    }
</style>
