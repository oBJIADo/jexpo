<template>
  <p @click="$emit('click')" v-if="show === undefined || show">{{showEmployee()}}</p>
</template>

<script>
  export default {
    name: "Person",
    props: {
      employee: {},
      show: true
    },
    methods: {
      showEmployee: function () {
        if (this.employee === undefined || this.employee == null) {
          // this.show = false;
          return 'nobody';
        }
        else if(this.employee.firstname === this.employee.secondname)
          return this.employee.firstname;
        else return this.employee.firstname + ', ' + this.employee.secondname;
      },
    }
  }
</script>

<style scoped>

</style>
