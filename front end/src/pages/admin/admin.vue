<template>
    <div id="app">
       <admin></admin>
    </div>
</template>

<script>
import admin from 'components/admin.vue'
export default {
  name: 'app',
  components: { admin }
}
</script>

<style>
#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin: 40px auto;
    margin-bottom: 0;
    wwidth:100%;
}
</style>
