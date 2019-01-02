<template>
    <div id="bg">
        <h1>Welcome to ITeam</h1>
        <h2>The Best Online Crowdsourcing Platform</h2>
        <div class="inner">
            <h2>Please Create Your Account</h2>
            <input v-model="user_name" type="text" name="user_name" id="user_name" value="" placeholder="User Name" />
            <input v-model="password" type="password" name="password" id="password" value="" placeholder="Password" />
            <input v-model="id_number" type="text" name="id_number" id="id_number" value="" placeholder="Identification Number" />
            <input v-model="contact" type="text" name="contact" id="contact" value="" placeholder="Mobile" />
            <button v-on:click="print()" class="button fit primary" id="create">Create Your Account</button>
        </div>
    </div>
</template>

<script>
export default {
  name: 'cell',
    name: 'app',
    data () {
        return {
            user_name: '',
            password: '',
            id_number: '',
            contact: ''
        }
    },
    methods: {
        print: function () {
            alert(this.user_name+" "+this.password+" "+this.id_number+" "+this.contact);
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url(../assets/css/style.css);

    h1{
        margin-top: 100px;
        font-size: 60px;
        font-weight: 100;
        margin-bottom: 5px;
    }
    h2{
        margin-top: 0;
        font-weight: 200;
        font-size: 28px;
    }

    #bg{
        background-image: url("../assets/img/banner.jpg");
        background-position: center;
        background-blend-mode: darken;
        background-color: #6c757d;
        background-repeat: repeat-y;
        top:0;
        width:100%;
        min-height: 100%;
        background-attachment: fixed;
        position: fixed;
        text-align: center;
        color:white;
    }
    .inner {
        color: darkslategray;
        margin: 0 auto;
        width: 40rem;
        max-width: calc(100% - 12rem);
        background: white;
        padding: 40px;
        child-align: middle;
    }
    .inner h2 {
        margin-top: 20px;
    }


    #user_name, #password, #id_number, #contact{
        width: 50%;
        margin: 20px auto;
    }

    #create{
        width: 55%;
        margin-top: 40px;
    }
    button:hover{cursor: pointer;}
</style>
