<template>
    <div id="app">
        <nav>
            <ul class="menu_wrapper">
                <!--<li><router-link class="link_wrapper"-->
                <!--:to="{name:'home'}">Home</router-link></li>-->
                <li>
                    <router-link class="link_wrapper"
                                 :to="{name:'tasks', query: {page: 1}}">Tasks
                    </router-link>
                </li>
                <li>
                    <router-link class="link_wrapper"
                                 :to="{name:'employees'}">Employees
                    </router-link>
                </li>
                <li>
                    <router-link class="link_wrapper"
                                 :to="{name:'search', query: {page: 1}}">Search
                    </router-link>
                </li>
                <!--<li><a class="link_wrapper" href="#">JPA query</a></li>-->
            </ul>
        </nav>
        <router-view/>
    </div>
</template>

<script>
    export default {
        name: 'App'
    }
</script>

<style>

</style>
