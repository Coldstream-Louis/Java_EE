<template>
    <div id="app">
        <div id="bg">
            <h1>Welcome to ITeam</h1>
            <h2>The Best Online Crowdsourcing Platform</h2>
            <div class="inner">
                <h2>Please Log in Your Account</h2>
                <input v-model="id" type="text" name="user_id" id="user_id" value="" placeholder="User_id" />
                <input v-model="password" type="password" name="password" id="password" value="" placeholder="Password" />
                <button v-on:click="print()" class="button primary fit" id="login">Sign in</button>
                <a href="cell.html" class="button fit" id="create">Sign up</a>
            </div>
            <a href="admin.html">Enter Admin</a>
        </div>
    </div>
</template>

<script>
export default {
    name: 'app',
    data () {
        return {
            id: '',
            password: ''
        }
    },
    methods: {
        print: function () {
            // alert(this.id+" "+this.password);
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/log_in',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"user_id": self.id, "password":self.password}),
                success: function (res) {
                    if(res.status == "banned")  {
                        alert("Sorry, you account have been banned. ");
                        return;
                    }
                    alert("Sign in "+res.result);
                    localStorage.setItem("user_id", self.id);
                    localStorage.setItem("user_name", res.user_name);
                    localStorage.setItem("password", res.password);
                    localStorage.setItem("real_name", res.real_name);
                    localStorage.setItem("id_number", res.id_number);
                    localStorage.setItem("work_info", res.work_info);
                    localStorage.setItem("personal_info", res.personal_info);
                    localStorage.setItem("contact", res.contact);
                    window.open("newPage.html", "_self");
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
            //window.open("newPage.html", "_self");
        }
    }
}
</script>

<style scoped>
@import url(../../assets/css/style.css);

#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: white;
    width:100%;
    min-height: 100%;
    position: fixed;
}

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
    background-image: url("../../assets/img/bg.jpg");
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


#user_id, #password{
    width: 50%;
    margin: 20px auto;
}

#login{
    margin-top: 0;
    width: 55%;
}
#create{
    width: 46%;
    margin-top: 40px;
}
    button:hover{cursor: pointer;}

</style>
