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
    .table__wrapper table {
        color: #aaa;
        text-align: center;
        border-spacing: 0 7px;
        background: #333333;
        border: 16px solid #333333;
        border-radius: 20px;
        margin: 0 auto;
    }

    .table__wrapper th {
        font-size: 15px;
        padding: 2px 11px;
        border-radius: 5px;
    }

    .table__wrapper tr {
        margin: 5px 0;
    }

    .table__wrapper td {
        padding: 3px 11px;
        font-size: 15px;
        height: 35px; /* Dirty hack? */
    }

    .table__wrapper td:first-child {
        border-bottom-left-radius: 5px;
        border-top-left-radius: 5px;
    }

    .table__wrapper td:last-child {
        border-bottom-right-radius: 5px;
        border-top-right-radius: 5px;
    }

    .table__wrapper h1 {
        text-align: center;
    }
</style>
