<template>
    <div class="tasks_search__wrapper">
        <div class="large_pair__wrapper">
            <searching-line :placeHolder="'tasks'" v-model="searchText" @submit="search()"/>
            <div class="settings__wrapper">
                <label class="container">Case ignore
                    <input type="checkbox"
                           :name="'settings'"
                           :value="'caseIgnore'"
                           v-model="settings"
                    />
                    <span class="checkmark"></span>
                </label>
            </div>
            <div class="tasks__wrapper">
                <tasks-table :tasks="tasks" v-model="keys" @click="clickToTask(keys)"/>
                <navigation-buttons v-if="pageNum>0"
                                    :num="pageNum"
                                    :lastPage="lastPage"
                                    v-model="pageNum"
                                    @change="pageTasksChangeAction()"/>
            </div>
        </div>
        <div class="small_pair__wrapper">
            <label class="sear__button clickable">Search
                <input type="button" @click="search()">
            </label>
            <div class="checkbox__wrapper">
                <check-box
                        v-for='(structure, index) in queryConstants.structure'
                        :key="index"
                        :structIndex="index"
                        :fields-mapping="queryConstants.queryMapping"
                        :indexes='structure.indexes'
                        :title="structure.title"
                        :bool-array='selected'
                        @click="titleCBClick($event)"
                        @change="customCBClick($event)"/>
            </div>
        </div>
    </div>
</template>

<script>
    //todo checkboxes drop to cb class
    import SearchingLine from './elements/functional/SearchingLine';
    import TasksTable from "./elements/task/TasksTable";
    import Constants from './js/Constants'
    import AxiosFunctions from './js/axiosFunctions'
    import NavigationButtons from './elements/functional/NavigationButtons';
    import CheckBox from './elements/functional/CheckBox'

    export default {
        name: "Search",

        components: {
            TasksTable,
            searchingLine: SearchingLine,
            navigationButtons: NavigationButtons,
            checkBox: CheckBox
        },

        props: {
            page: undefined,
            searchParam: undefined,
            queryIndexes: undefined,
            caseIgnore: undefined
        },

        data: function () {
            return {
                keys: '',
                tasks: [],
                queryConstants: Constants.queryConstants,
                // selected: [0, 1, 2, 8, 9, 10, 22, 31, 32, 33, 34, 35, 36, 37, 38, 42,43,44],
                selected: [],
                settings: ['caseIgnore'],
                searchText: undefined,
                pageNum: 1,
                lastPage: undefined,
                mainClickIndex: undefined
            }
        },

        methods: {
            removeAllValues: function (index) {
                this.queryConstants.structure[index].indexes.forEach((value) => this.selected[value] = false)
            },
            titleCBClick: function (index) {
                let contain = true;
                console.log(index);

                this.queryConstants.structure[index].indexes.forEach((value) => {
                    console.log(value + ":" + this.selected[value]);
                    if (!this.selected[value]) {
                        contain = false;
                        this.selected[value] = true;
                    }
                });

                if (contain) {
                    this.removeAllValues(index)
                }
            },
            customCBClick: function(index){
                console.log(index +" : "+ this.selected[index]);
                this.selected[index] = !this.selected[index];
                console.log(index +" : "+ this.selected[index]);
            },

            clickToTask: function (keys) {
                this.$router.push({name: 'task', params: {key: keys}})
            },
            getTasks: function () {
                const axConf = AxiosFunctions.tasksRqConfig(this.pageNum, this.searchText, this.makeIndexesStringMessage(), this.isCaseSensetive());
                this.$http.get(axConf.address, axConf.rqConf)
                    .then(response => {
                        this.tasks = response.data;
                    })
            },
            getPages: function () {
                const axConf = AxiosFunctions.pagesRqConfig(this.searchText, this.makeIndexesStringMessage(), this.isCaseSensetive());
                this.$http.get(axConf.address, axConf.rqConf)
                    .then(response => {
                        //todo: to func!
                        this.lastPage = response.data;
                        if (this.lastPage === 0) {
                            this.pageNum = 0;
                        } else {
                            this.pageNum = 1;
                            if (this.isPageNotRight(+this.pageNum)) {
                                this.$router.push({name: "error"});
                            }
                        }
                    })
            },

            makeIndexesStringMessage: function () {
                let message = "";
                this.selected.forEach((value, index) => {
                    if (value)
                        message += index.toString() + ","
                });
                return message.substring(0, message.length - 1);
            },

            isCaseSensetive: function () {
                return this.settings.indexOf('caseIgnore') >= 0
            },
            selectAll: function () {
                let all = [];
                this.value.forEach(elem => {
                    all.push(elem)
                });
                return all;
            },

            search: function () {
                const query = this.createQuery(1);
                this.$router.push({name: "search", query: query})
            },

            pageTasksChangeAction: function () {
                const query = this.createQuery();
                const pushParams = {
                    name: "search",
                    query: query
                };
                this.$router.push(pushParams)
            },
            createQuery: function (page) {
                const pageNum = page === undefined ? this.pageNum : page;
                const sparam = this.searchText;
                const sindexes = this.makeIndexesStringMessage();
                const caseig = this.isCaseSensetive();
                return (sparam !== undefined && sparam.length > 0) ?
                    {page: pageNum, searchParam: this.searchText, queryIndexes: sindexes, caseIgnore: caseig} :
                    {page: pageNum};


            },
            isPageNotRight: function (page) {
                return typeof(page) !== 'number' || isNaN(page) || page < 1 || page > this.lastPage
            },
            initSelected: function (indexes) {
                //todo
                let selectedArray = new Array(Constants.queryStructureLength);
                for (let i = 0; i < Constants.queryStructureLength; i++) {
                    selectedArray[i] = true;
                }
                return selectedArray;
            }

        },

        watch: {
            '$route'(to, from) {
                this.pageNum = +to.query.page;
                this.searchText = to.query.searchParam;
                this.selected = this.initSelected(to.query.queryIndexes);
                if (this.caseIgnore) this.settings = ['caseIgnore'];
                this.getTasks();
                if (from.query.searchParam !== this.searchText || from.query.queryIndexes !== this.queryIndexes) {
                    this.getPages();
                }
            }
        },

        created: function () {
            this.pageNum = this.page === undefined ? +1 : this.page;
            this.searchText = this.searchParam;
            //If query indexes undefined -> selected not a array -> we cant use checkboxes!
            //If it interesting for u, try and u see what happened lol
            this.selected = this.initSelected(this.queryIndexes); //so hot
            if (this.caseIgnore) this.settings = ['caseIgnore'];
            this.getPages();
            this.getTasks();
        },
    }
</script>

<style scoped>
    .settings__wrapper {
        width: 191px;
        margin: 0 69px 0 auto;
        color: #4fb9a7;
        background: #333333;
    }

    .tasks__wrapper {
        text-align: center;
    }
</style>
