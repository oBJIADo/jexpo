<template>
    <div class="table__wrapper noselect__wrapper">
        <h1>Tasks</h1>
        <table>
            <tr>
                <th>Key</th>
                <th>Summary</th>
                <th>Issue Type</th>
                <th>Resolution</th>
                <th>Created</th>
            </tr>
            <tr class="clickable" v-for="task in tasks" v-on:click="goOn(task.keys)">
                <td>{{task.keys}}</td>
                <td>{{task.summary}}</td>
                <td>{{task.issueType}}</td>
                <td>{{task.resolution}}</td>
                <td>{{task.created}}</td>
            </tr>
        </table>
    </div>
</template>

<script>
    import Person from "../employees/Person"

    export default {
        name: "TasksTable",

        components: {
            person: Person
        },

        model: {
            prop: 'keys',
            event: 'click'
        },

        props: {
            tasks: undefined,
        },

        methods: {
            goOn(keys) {
                this.$emit('click', keys);
            },

        },
    }
</script>

<style scoped>

</style>
