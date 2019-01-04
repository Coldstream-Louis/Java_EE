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
            <div class="row">
                <input v-model="code" type="text" name="code" id="code" value="" placeholder="Enter the code you received on mobile" />
                <button v-on:click="sendCode()" class="button fit" id="send_code">Get your code</button>
            </div>
            <button v-on:click="create()" class="button fit primary" id="create">Create Your Account</button>
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
            contact: '',
            code: '',
            code_verify: '',
        }
    },
    methods: {
        create: function () {
            var self = this;
            if (this.code == "") {
                alert("You have to input the code you received! ")
                return;
            }
            else if(this.code != this.code_verify) {
                alert("The code you input is incorrect! ")
                return;
            }
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/create_user',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"user_name": self.user_name, "password": self.password, "id_number": self.id_number, "contact": self.contact}),
                success: function (res) {
                    alert("You have create your account successfully! Please remember your User ID: "+ res.user_id);
                    window.open("index.html", "_self");
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
        sendCode: function () {
            var self = this;
            if(this.contact == '') {
                alert("You have to enter your mobile first! ")
                return;
            }
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/send_MessageCode',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"contact": self.contact}),
                success: function (res) {
                    alert("Your verified code have been sent! ")
                    self.code_verify = res.code;
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url(../assets/css/style.css);

    h1{
        margin-top: 50px;
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


    #user_name, #password, #id_number, #contact, #code{
        width: 50%;
        margin: 20px auto;
    }

    #code, #send_code{
        width: 25%;
        display: inline
    }

    #create{
        width: 55%;
        margin-top: 40px;
    }
    button:hover{cursor: pointer;}
</style>
