<template>
  <div class="details__wrapper">
    <h3>People</h3>
    <searching-line
      v-model="searchParam" :placeHolder="'employee'">
    </searching-line>
    <person
      class="content"
      v-for="person in people"
      :key="person.firstName"
      :employee="person"
      :show="isContains(person)"
      @click="selectUser(person)">
    </person>
  </div>
</template>

<script>
  import Person from "./elements/employees/Person"
  import SearchingLine from "./elements/functional/SearchingLine";
  import Constants from "./js/Constants";

  export default {
    name: "Employees",
    
    components: {
      SearchingLine,
      person: Person,
    },
    
    data: function () {
      return {
        searchParam: '',
        people: undefined,
      }
    },



    methods: {
      isContains: function(employee) {
        if (this.searchParam != '') {
          let param = this.searchParam.toLowerCase();
          return employee.firstname.toLowerCase().includes(param) ||
            employee.secondname.toLowerCase().includes(param)
        } else {
          return true;
        }
      },
      getAll: function () {
        this.$http
          .get(Constants.addresses.employeesUrl)
          .then(response => (this.people = response.data));
      },
      
      selectUser: function (person) {
      
      }
    },
    
    created: function () {
      this.getAll()
    }
  }
</script>

<style scoped>
  .details__wrapper {
    background: #333333;
    margin: 10px 0;
    padding: 1px 5px;
    border-radius: 8px;
  }
</style>
