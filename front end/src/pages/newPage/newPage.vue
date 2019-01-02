<template>
    <div id="app">
       <newPage></newPage>
    </div>
</template>

<script>
import newPage from 'components/newPage.vue'
export default {
  name: 'app',
  components: { newPage }
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
