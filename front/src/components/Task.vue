<template>
    <div class="task__wrapper">
        <h2><span class="noselect__wrapper">Task: </span><span class="head_title">{{task.keys}}</span></h2>
        <h2><span class="noselect__wrapper">Summary: </span><span class="head_title">{{task.summary}}</span></h2>
        <div class="main_details__wrapper">

            <div class="big_pair__wrapper">
                <div class="details__wrapper">
                    <p class="title">Description: </p>
                    <text-component :text="task.description"></text-component>
                </div>
                <files-list :title="'Attachments'"
                            :files-names="filesNames"
                            @click="download($event)"></files-list>
                <div class="details__wrapper" v-if="!isEmpty(task.comments)">
                    <p class="title noselect__wrapper">Comments: </p>
                    <comments-list :comments="task.comments"></comments-list>
                </div>
            </div>

            <div class="small_pair__wrapper">
                <div class="details__wrapper">
                    <p class="title noselect__wrapper">Details</p>
                    <task-element :title="'Issue type'" :input="task.issueType"></task-element>
                    <task-element :title="'Status'" :input="task.status"></task-element>
                    <task-element :title="'Priority'" :input="task.priority" v-if="task.priority"></task-element>
                    <task-element :title="'Resolution'" :input="task.resolution" v-if="task.resolution"></task-element>
                    <task-element v-if="!isEmpty(task.subTasks)"
                                  :title="'Subtasks'"
                                  :input="task.subTasks"
                                  :is-clickable="true"
                                  @click="goOn($event)"/>
                    <task-element v-if="!isEmpty(task.parentTasks)"
                                  :title="'Parent tasks'"
                                  :input="task.parentTasks"
                                  :is-clickable="true"
                                  @click="goOn($event)"/>
                    <task-element v-if="!isEmpty(task.duplicateTasks)"
                                  :title="'Duplicate'"
                                  :input="task.duplicateTasks"
                                  :is-clickable="true"
                                  @click="goOn($event)"/>
                    <task-element v-if="!isEmpty(task.relationTasks)"
                                  :title="'Related to CRs'"
                                  :input="task.relationTasks"
                                  :is-clickable="true"
                                  @click="goOn($event)"/>
                </div>

                <div class="details__wrapper">
                    <p class="title noselect__wrapper">Versions</p>
                    <task-element :title="'Affects versions'" :input="task.affectsVersions"
                                  v-if="!isEmpty(task.affectsVersions)"></task-element>
                    <task-element :title="'Fix versions'" :input="task.fixVersions"
                                  v-if="!isEmpty(task.fixVersions)"></task-element>
                    <task-element :title="'Components'" :input="task.components"
                                  v-if="!isEmpty(task.components)"></task-element>
                    <task-element :title="'Delivered version'" :input="task.deliveredVersion"
                                  v-if="task.deliveredVersion"></task-element>
                    <task-element :title="'DRC number'" :input="task.drcNumber" v-if="task.drcNumber"></task-element>
                    <task-element :title="'Order number'" :input="task.orderNumber"
                                  v-if="task.orderNumber"></task-element>
                    <task-element :title="'Sprint'" :input="task.sprint" v-if="task.sprint"></task-element>
                </div>

                <div class="details__wrapper">
                    <p class="title noselect__wrapper">People</p>
                    <task-person :title="'Assignee'" :employee="task.assignee"></task-person>
                    <task-person :title="'Reporter'" :employee="task.reporter"></task-person>
                    <task-person :title="'Creator'" :employee="task.creater"></task-person>
                </div>
            </div>


            <div class="small_pair__wrapper">
                <div class="details__wrapper">
                    <p class="title noselect__wrapper">Dates</p>
                    <task-element :title="'Created'" :input="task.created" v-if="task.created"></task-element>
                    <task-element :title="'Updated'" :input="task.updated" v-if="task.updated"></task-element>
                    <task-element :title="'Resolved'" :input="task.resolved" v-if="task.resolved"></task-element>
                    <task-element :title="'Due date'" :input="task.dueDate" v-if="task.dueDate"></task-element>
                </div>

                <div class="details__wrapper">
                    <p class="title noselect__wrapper">Amounts</p>
                    <task-element :title="'Σ progress'" :input="task.sumProgress + '%'"
                                  v-if="task.sumProgress"></task-element>
                    <task-element :title="'Σ time spent, hour'" :input="getHours(task.sumTimeSpant)"
                                  v-if="task.sumTimeSpant"></task-element>
                    <task-element :title="'Σ remaining estimate, hour'" :input="getHours(task.sumRemainingEstimate)"
                                  v-if="task.sumRemainingEstimate"></task-element>
                    <task-element :title="'Σ original estimate, hour'" :input="getHours(task.sumOriginalEstimate)"
                                  v-if="task.sumOriginalEstimate"></task-element>
                </div>

                <div class="details__wrapper">
                    <p class="title noselect__wrapper">Characteristics</p>
                    <task-element :title="'Original estimate, hour'" :input="getHours(task.originalEstimate)"
                                  v-if="task.originalEstimate"></task-element>
                    <task-element :title="'Remaining estimate, hour'" :input="getHours(task.remainingEstimate)"
                                  v-if="task.remainingEstimate"></task-element>
                    <task-element :title="'Time spent, hour'" :input="getHours(task.timeSpent)"
                                  v-if="task.timeSpent"></task-element>
                    <task-element :title="'Work ratio'" :input="task.workRation + '%'"
                                  v-if="task.workRation"></task-element>
                    <task-element :title="'Progress'" :input="task.progress + '%'" v-if="task.progress"></task-element>
                </div>

                <div class="details__wrapper">
                    <p class="title noselect__wrapper">Epic</p>
                    <task-element :title="'Epic Name'" :input="task.epicName" v-if="task.epicName"></task-element>
                    <task-element :title="'Epic color'" :input="task.epicColor" v-if="task.epicColor"></task-element>
                    <task-element :title="'Epic link'" :input="task.epicLink" v-if="task.epicLink"></task-element>
                    <task-element :title="'Epic status'" :input="task.epicStatus" v-if="task.epicStatus"></task-element>
                </div>

                <div class="details__wrapper">
                    <p class="title noselect__wrapper">Another</p>
                    <task-element :title="'Labels'" :input="task.labels" v-if="!isEmpty(task.labels)"></task-element>
                    <task-element :title="'VSE team'" :input="task.teams" v-if="!isEmpty(task.teams)"></task-element>
                    <task-element :title="'Keyword'" :input="task.keyword" v-if="task.keyword"></task-element>
                    <task-element :title="'Fix priority'" :input="task.fixPriority"
                                  v-if="task.fixPriority"></task-element>
                </div>
            </div>
        </div>


    </div>
</template>

<script>
    import CommentsList from "./elements/comment/CommentsList"
    import TaskPerson from "./elements/task/TaskPerson"
    import TaskElement from "./elements/task/TaskElement"
    import TextComponent from "./elements/functional/TextComponent"
    import Constants from "./js/Constants";
    import FilesList from "./elements/functional/FilesList";

    export default {
        name: "Task",
        components: {
            filesList: FilesList,
            commentsList: CommentsList,
            taskPerson: TaskPerson,
            taskElement: TaskElement,
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
            getHours(seconds) {
                return seconds / 60 / 60
            }
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
    .task__wrapper {
        color: #aaa;
        min-width: 950px;
    }

    .task__wrapper h2 {
        text-align: center;
    }

    .head_title {
        color: #4FB9A7;
    }

    .main_details__wrapper {
        display: flex;
        justify-content: space-around;
    }

    .big_pair__wrapper {
        flex-basis: 60%;
        margin: 0 5px 0 0;
        max-width: 60vw;
    }

    .small_pair__wrapper {
        flex-basis: 20%;
        margin: 0 5px;
    }

    .details__wrapper {
        background: #333333;
        margin: 10px 0;
        padding: 1px 5px;
        border-radius: 8px;
    }

    .details__wrapper h3 {
        border-top: 1px solid #333;
    }

    .title {
        margin: 0;
        font-size: 22px;
        text-align: center;
        color: #777777;
        /*font-style: bold italic;*/
    }
</style>

