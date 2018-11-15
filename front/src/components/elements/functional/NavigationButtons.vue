<template>
    <div class="paginator noselect__wrapper" id="pageButtons">
        <a class="prev" v-bind:class="prevNext[0]" v-on:click="setNeiboPage(1, prevNext[0])"></a>
        <a v-for="page in pages" v-bind:class="page.classes" v-on:click="setCurPage(page)">
            {{page.count}}
        </a>
        <a class="next" v-bind:class="prevNext[1]" v-on:click="setNeiboPage(-1, prevNext[1])"></a>
    </div>
</template>

<script>
    export default {
        name: "NavigationButtons",

        props: ["num", "lastPage"],

        // model: {
        //     pageNum: undefined,
        //     event: 'change'
        // },

        data: function () {
            return {
                buttonsCount: 2,
                pages: [],
                prevNext: ['inactive', 'inactive'],
            }
        },
        watch: {
            'num'(to) {
                this.setPage(+to);
            },
            'lastPage'() {
                this.setPage(+this.num)
            }
        },
        methods: {
            getActiveButton: function (count) {
                return {
                    count: count,
                    classes: "current"
                }
            },
            getButton: function (count) {
                return {
                    count: count,
                    classes: "active"
                }
            },
            getDots: function () {
                return {
                    count: '...',
                    classes: "dots"
                }
            },
            setPrevNextButtons: function (paramPrev, paramNext) {
                this.prevNext[0] = paramPrev;
                this.prevNext[1] = paramNext;
            },
            setFirstPage: function () {
                let pages = [];
                let max = this.lastPage;
                pages.push(this.getActiveButton(1));

                if (max < 6) {
                    for (let i = 1; i < max; i++) {
                        pages.push(this.getButton(i + 1));
                    }
                } else {
                    for (let i = 1; i < 4; i++) {
                        pages.push(this.getButton(i + 1));
                    }
                    pages.push(this.getDots());
                    pages.push(this.getButton(max));
                }

                if (max > 1) {
                    this.setPrevNextButtons('inactive', 'active');
                } else {
                    this.setPrevNextButtons('inactive', 'inactive');
                }
                this.pages = pages;
            },
            setLastPage: function () {
                let pages = [];
                let max = this.lastPage;
                if (max < 6) {
                    for (let i = 1; i < max; i++) {
                        pages.push(this.getButton(i))
                    }
                } else {
                    pages.push(this.getButton(1));
                    pages.push(this.getDots());
                    for (let i = max - this.buttonsCount - 1; i < max; i++) {
                        pages.push(this.getButton(i));
                    }
                }
                pages.push(this.getActiveButton(max));
                this.pages = pages;

                if (max != 1) {
                    this.setPrevNextButtons('active', 'inactive');
                } else {
                    this.setPrevNextButtons('inactive', 'inactive');
                }
            },
            setLeft: function (current) {
                let pages = [];
                if (current - this.buttonsCount > 2) {
                    pages.push(this.getButton(1));
                    pages.push(this.getDots());
                    for (let i = current - this.buttonsCount; i < current; i++) {
                        pages.push(this.getButton(i));
                    }
                } else {
                    for (let i = 1; i < current; i++) {
                        pages.push(this.getButton(i));
                    }
                }
                pages.push(this.getActiveButton(current));

                return pages;
            },
            setRight: function (current) {
                let pages = [];
                if (current + this.buttonsCount < this.lastPage - 1) {
                    for (let i = current + 1; i <= current + this.buttonsCount; i++) {
                        pages.push(this.getButton(i))
                    }
                    pages.push(this.getDots());
                    pages.push(this.getButton(this.lastPage))
                } else {
                    for (let i = current + 1; i <= this.lastPage; i++) {
                        pages.push(this.getButton(i))
                    }
                }
                return pages;
            },

            setCurPage: function (page) {
                if (page.count == '...')
                    return;
                else if (page.classes == 'current')
                    return;
                this.changePage(+page.count);
            },

            changePage: function (current) {
                this.$emit('change', current);
            },

            setPage: function (current) {
                if (current == 1)
                    this.setFirstPage();
                else if (current == this.lastPage)
                    this.setLastPage();
                else {
                    this.pages = this.setLeft(current);
                    Array.prototype.push.apply(this.pages, this.setRight(current));
                    this.setPrevNextButtons('active', 'active');
                }
            },
            setNeiboPage: function (param, status) {
                if (status == 'inactive')
                    return;

                let count;
                this.pages.forEach(element => {
                    if (element.classes == 'current') {
                        count = element.count;
                        return;
                    }
                });

                count = param > 0 ? count - 1 : count + 1;
                this.changePage(+count);
            },
        },
    }
</script>

<style scoped>
    .paginator {
        display: inline-block;
        padding: 10px;
        margin: 30px auto;
        white-space: nowrap;
        background: #333333;
        border-radius: 10px;
    }

    .paginator a {
        display: inline-block;
        min-width: 20px;
        height: 40px;
        padding: 0 10px;
        line-height: 40px;
        text-decoration: none;
        font-weight: bold;
        text-align: center;
        vertical-align: middle;
        background: #3d4551;
        color: #aaa;
    }

    .paginator .prev {
        margin-right: 20px;
        background-image: url("/img/arrows.png");
        background-repeat: no-repeat;
        background-position: 0px 0px;
    }

    .paginator .next {
        margin-left: 20px;
        background-image: url("/img/arrows.png");
        background-repeat: no-repeat;
        background-position: 0px -40px;
    }

    .paginator .dots {
        background: #333;
    }

    .paginator .current {
        background: #314236;
    }

    .paginator .current:hover {
        cursor: default;
    }

    .paginator .inactive {
        background: #333333;
    }

    .paginator .active:hover {
        background: #4fb9a7;
        color: #1c1d22;
        cursor: pointer;
    }
</style>
