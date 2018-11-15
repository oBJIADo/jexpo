import Vue from 'vue'
import Router from 'vue-router'

import SimpleTasksSearch from '../components/SimpleTasksSearch'
import Task from '../components/Task'
import Home from '../components/Home'
import Employees from '../components/Employees'
import Search from '../components/Search'
import Error from '../components/Error'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/tasks',
            name: 'tasks',
            props: (route) => ({
                page: route.query.page,
                searchParam: route.query.searchParam
            }),
            component: SimpleTasksSearch
        },
        {
            path: '/task/:key',
            name: 'task',
            component: Task,
        },
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/employees',
            name: 'employees',
            component: Employees
        },
        {
            path: '/search',
            name: 'search',
            props: (route) => ({
                page: route.query.page,
                searchParam: route.query.searchParam,
                queryIndexes: route.query.queryIndexes,
                caseIgnore: route.query.caseIgnore
            }),
            component: Search,
        },
        {
            path: "/error",
            name: "error",
            component: Error
        }
    ],
    scrollBehavior(to, from, savedPosition) {
        if (to.name === 'task')
            return {x: 0, y: 0};
        return savedPosition;
    },
})
