<template>
    <div class="task__wrapper">
        <h2>Task: <span class="head_title">{{task.keys}}</span></h2>
        <h2>Summary: <span class="head_title">{{task.summary}}</span></h2>
        <div class="main_details__wrapper">

            <div class="big_pair__wrapper">
                <div class="grey__wrapper">
                    <p class="title">Description: </p>
                    <text-component class="content" :text="task.description"></text-component>
                </div>
                <div class="grey__wrapper" v-if="!isEmpty(filesNames)">
                    <p class="title">Attachements</p>
                    <p
                            class="content clickable"
                            v-for="name in filesNames"
                            @click="download(name)">{{name}}</p>
                </div>
                <div class="grey__wrapper" v-if="!isEmpty(task.comments)">
                    <p class="title">Comments: </p>
                    <comments-list :comments="task.comments"></comments-list>
                </div>
            </div>

            <div class="small_pair__wrapper">
                <div class="details__wrapper">
                    <h3>Details</h3>
                    <task-element :title="'Issue type'" :field="task.issueType"></task-element>
                    <task-element :title="'Status'" :field="task.status"></task-element>
                    <task-element :title="'Priority'" :field="task.priority" v-if="task.priority"></task-element>
                    <task-element :title="'Resolution'" :field="task.resolution" v-if="task.resolution"></task-element>
                    <div class="component__wrapper" v-if="!isEmpty(task.subTasks)">
                        <p class="title"><span>Subtasks: </span></p>
                        <p class="content clickable" v-for="sub in task.subTasks" v-on:click="goOn(sub)">
                            <span>{{sub}}</span>
                        </p>
                    </div>
                    <div class="component__wrapper" v-if="!isEmpty(task.parentTasks)">
                        <p class="title"><span>Parent tasks: </span></p>
                        <p class="content clickable" v-for="sub in task.parentTasks" v-on:click="goOn(sub)">
                            <span>{{sub}}</span>
                        </p>
                    </div>
                    <div class="component__wrapper" v-if="!isEmpty(task.duplicateTasks)">
                        <p class="title"><span>Duplicate: </span></p>
                        <p class="content clickable" v-for="sub in task.duplicateTasks" v-on:click="goOn(sub)">
                            <span>{{sub}}</span>
                        </p>
                    </div>
                    <div class="component__wrapper" v-if="!isEmpty(task.relationTasks)">
                        <p class="title"><span>Related to CRs: </span></p>
                        <p class="content clickable" v-for="sub in task.relationTasks" v-on:click="goOn(sub)">
                            <span>{{sub}}</span>
                        </p>
                    </div>
                </div>

                <div class="details__wrapper">
                    <h3>Versions</h3>
                    <task-array :title="'Affects versions'" :array="task.affectsVersions"
                                v-if="!isEmpty(task.affectsVersions)"></task-array>
                    <task-array :title="'Fix versions'" :array="task.fixVersions"
                                v-if="!isEmpty(task.fixVersions)"></task-array>
                    <task-array :title="'Components'" :array="task.components"
                                v-if="!isEmpty(task.components)"></task-array>
                    <task-element :title="'Delivered version'" :field="task.deliveredVersion"
                                  v-if="task.deliveredVersion"></task-element>
                    <task-element :title="'DRC number'" :field="task.drcNumber" v-if="task.drcNumber"></task-element>
                    <task-element :title="'Order number'" :field="task.orderNumber"
                                  v-if="task.orderNumber"></task-element>
                    <task-element :title="'Sprint'" :field="task.sprint" v-if="task.sprint"></task-element>
                </div>

                <div class="details__wrapper">
                    <h3>People</h3>
                    <task-person :title="'Assignee'" :employee="task.assignee"></task-person>
                    <task-person :title="'Reporter'" :employee="task.reporter"></task-person>
                    <task-person :title="'Creator'" :employee="task.creater"></task-person>
                </div>
            </div>


            <div class="small_pair__wrapper">
                <div class="details__wrapper">
                    <h3>Dates</h3>
                    <task-element :title="'Created'" :field="task.created" v-if="task.created"></task-element>
                    <task-element :title="'Updated'" :field="task.updated" v-if="task.updated"></task-element>
                    <task-element :title="'Resolved'" :field="task.resolved" v-if="task.resolved"></task-element>
                    <task-element :title="'Due date'" :field="task.dueDate" v-if="task.dueDate"></task-element>
                </div>

                <div class="details__wrapper">
                    <h3>Amounts</h3>
                    <task-element :title="'Σ progress'" :field="task.sumProgress + '%'"
                                  v-if="task.sumProgress"></task-element>
                    <task-element :title="'Σ time spent, hour'" :field="task.sumTimeSpant/60"
                                  v-if="task.sumTimeSpant"></task-element>
                    <task-element :title="'Σ remaining estimate, hour'" :field="task.sumRemainingEstimate/60"
                                  v-if="task.sumRemainingEstimate"></task-element>
                    <task-element :title="'Σ original estimate, hour'" :field="task.sumOriginalEstimate/60"
                                  v-if="task.sumOriginalEstimate"></task-element>
                </div>

                <div class="details__wrapper">
                    <h3>Characteristics</h3>
                    <task-element :title="'Original estimate, hour'" :field="task.originalEstimate/60"
                                  v-if="task.originalEstimate"></task-element>
                    <task-element :title="'Remaining estimate, hour'" :field="task.remainingEstimate/60"
                                  v-if="task.remainingEstimate"></task-element>
                    <task-element :title="'Time spent, hour'" :field="task.timeSpent/60"
                                  v-if="task.timeSpent"></task-element>
                    <task-element :title="'Work ratio'" :field="task.workRation + '%'"
                                  v-if="task.workRation"></task-element>
                    <task-element :title="'Progress'" :field="task.progress + '%'" v-if="task.progress"></task-element>
                </div>

                <div class="details__wrapper">
                    <h3>Epic</h3>
                    <task-element :title="'Epic Name'" :field="task.epicName" v-if="task.epicName"></task-element>
                    <task-element :title="'Epic color'" :field="task.epicColor" v-if="task.epicColor"></task-element>
                    <task-element :title="'Epic link'" :field="task.epicLink" v-if="task.epicLink"></task-element>
                    <task-element :title="'Epic status'" :field="task.epicStatus" v-if="task.epicStatus"></task-element>
                </div>

                <div class="details__wrapper">
                    <h3>Another</h3>
                    <task-array :title="'Labels'" :array="task.labels" v-if="!isEmpty(task.labels)"></task-array>
                    <task-array :title="'VSE team'" :array="task.teams" v-if="!isEmpty(task.teams)"></task-array>
                    <task-element :title="'Keyword'" :field="task.keyword" v-if="task.keyword"></task-element>
                    <task-element :title="'Fix priority'" :field="task.fixPriority"
                                  v-if="task.fixPriority"></task-element>
                </div>
            </div>
        </div>


    </div>
</template>

<script>
    //todo set correct time
    import CommentsList from "./elements/comment/CommentsList"
    import TaskPerson from "./elements/task/TaskPerson"
    import TaskElement from "./elements/task/TaskElement"
    import TaskArray from "./elements/task/TaskArray"
    import TextComponent from "./elements/functional/TextComponent"
    import Constants from "./js/Constants";

    export default {
        name: "Task",
        components: {
            commentsList: CommentsList,
            taskPerson: TaskPerson,
            taskElement: TaskElement,
            taskArray: TaskArray,
            textComponent: TextComponent
        },
        data: function () {
            return {
                selectedFileName: undefined,
                keys: undefined,
                task: {},
                filesNames: undefined,
                imgFormats: ['jpg', 'png'],
            }
        },
        methods: {
            goOn(keys) {
                this.$router.push({name: 'task', params: {key: keys}})
            },
            getTask() {
                this.$http
                    .get(Constants.addresses.tasksUrl + '/' + this.keys)
                    .then(response => {
                            this.task = response.data;
                        }
                    );
            },
            download: function (fileName) {
                this.$http({
                    url: Constants.addresses.filesUrl + '/' + this.keys + '/' + Constants.addresses.fileDownloadUrl,
                    method: 'GET',
                    responseType: 'blob', // important
                    params: {
                        fileName: fileName
                    }

                }).then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', fileName); //or any other extension
                    document.body.appendChild(link);
                    link.click();
                });
            },
            isEmpty(array) {
                if (array === undefined)
                    return true;
                else return array.length <= 0
            },
            getFilesNames: function (keys) {
                this.$http.get(Constants.addresses.filesUrl + '/' + this.keys)
                    .then((response) => {
                        this.filesNames = response.data;
                    });


            },
        },


        watch: {
            '$route'(to) {
                this.keys = to.params.key;
                this.getTask();
            }
        },

        created: function () {
            this.keys = this.$route.params.key;
            this.getTask(this.keys);
            this.getFilesNames(this.keys);
        }
    }
</script>

<style scoped>

</style>

