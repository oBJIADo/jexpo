<template>
    <div class="small_pair__wrapper">
    </div>
</template>

<script>
    //todo: idk how to do it, but mb in future
    import TaskElement from "./elements/task/TaskElement"

    export default {
        name: "TaskBlock",
        components: {taskElement: TaskElement},
        props: ['titles', 'values']
    }
</script>

<style scoped>

</style>