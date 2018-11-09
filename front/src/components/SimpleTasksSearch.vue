<template>
    <div class="tasks__wrapper">
        <searching-line :placeHolder="'tasks'"
                        v-model="searchText"
                        :default-value="searchParam"
                        @submit="pageTasksChangeAction()"/>
        <tasks-table :tasks="tasks" v-model="keys" @click="clickToTask(keys)"/>
        <navigation-buttons v-if="pageNum>0"
                            :lastPage="lastPage"
                            :num="pageNum"
                            @change="pageTasksChangeAction($event)"/>
    </div>
</template>

<script>
    import TasksTable from './elements/task/TasksTable'
    import SearchingLine from './elements/functional/SearchingLine';
    import NavigationButtons from './elements/functional/NavigationButtons';
    import AxiosFunctions from './js/axiosFunctions';

    export default {
        name: "SimpleTasksSearch",
        components: {
            tasksTable: TasksTable,
            searchingLine: SearchingLine,
            navigationButtons: NavigationButtons
        },
        props: ["page", "searchParam"],
        data: function () {
            return {
                keys: '',
                tasks: [],
                pageNum: undefined,
                lastPage: undefined,
                searchText: undefined,
            }
        },

        methods: {
            clickToTask: function (keys) {
                this.$router.push({name: 'task', params: {key: keys}})
            },

            getTasks: function () {
                const axConf = AxiosFunctions.tasksRqConfig(this.pageNum, this.searchText, null, null);
                this.$http.get(axConf.address, axConf.rqConf)
                    .then(response => {
                        this.tasks = response.data;
                    })
            },
            getPages: function () {
                const axConf = AxiosFunctions.pagesRqConfig(this.searchText, null, null);
                this.$http.get(axConf.address, axConf.rqConf)
                    .then(response => {
                            //todo: to func!
                            this.lastPage = response.data;
                            if (this.lastPage === 0) {
                                this.pageNum = 0;
                            } else {
                                this.pageNum = (this.pageNum < this.lastPage) ? this.pageNum : 1;
                                if (this.isPageNotRight(+this.pageNum)) {
                                    this.$router.push({name: "error"});
                                }
                            }
                        }
                    )
            },

            pageTasksChangeAction: function (newPage) {
                const query = (this.searchText !== undefined && this.searchText.length > 0) ?
                    {page: this.pageNum, searchParam: this.searchText} :
                    {page: +newPage};
                const pushParams = {
                    name: "tasks",
                    query: query
                };
                this.$router.push(pushParams)
            }
            ,

            isPageNotRight: function (page) {
                return typeof(page) !== 'number' || isNaN(page) || page < 1 || page > this.lastPage
            }
        },
        watch: {
            '$route'(to, from) {
                this.pageNum = +to.query.page;
                this.searchText = to.query.searchParam;
                this.getTasks();
                if (from.query.searchParam !== this.searchText) {
                    this.getPages();
                }
            }
        }
        ,

        created: function () {
            this.searchText = this.searchParam;
            this.pageNum = (this.page === undefined) ? 1 : this.page;
            this.getPages();
            this.getTasks();
        }
        ,
    }
</script>

<style scoped>
    .tasks__wrapper {
        text-align: center;
    }
</style>