<template>
    <div class="hello">
        <!-- Header -->
        <header id="header">
            <a class="logo" href="#banner">ITeam - {{enter_user_name}} (Guest Mode)</a>
            <nav>
                <a href="newPage.html">Home</a>
            </nav>
        </header>

        <div id="bg">
            <!-- Banner -->
            <section id="banner">
                <div class="inner">
                    <h1>Welcome to ITeam</h1>
                    <p>The Best Online Crowdsourcing Platform<br />
                        designed by ITeam and released under the Creative Commons License.</p>
                </div>
            </section>

            <section id="info">
                <div class="inner" id="info_box">
                    <h2>Personal Information</h2>
                    <h2>The User Id of {{enter_user_name}} is {{enter_user_id}}</h2>
                    <div class="row">
                        <h3>User Name: </h3>
                        <input v-model="enter_user_name" type="text" name="user_name" id="user_name" value="" placeholder="User Name" disabled/>
                        <h3>Real Name:</h3>
                        <input v-model="enter_real_name" type="text" name="real_name" id="real_name" value="" placeholder="Password" disabled/>
                        <h3>Contact:</h3>
                        <input v-model="enter_contact" type="text" name="contact" id="contact" value="" placeholder="Mobile" disabled/>
                    </div>
                    <div class="row">
                        <h3>Personal Information: </h3>
                        <textarea name="personal info" id="personal_info" placeholder="Add your personal information here" rows="6" v-model="enter_personal_info" disabled></textarea>
                        <h3>Work Information: </h3>
                        <textarea name="personal info" id="work_info" placeholder="Add your work information here" rows="6" v-model="enter_work_info" disabled></textarea>
                    </div>
                </div>
            </section>

            <section id="cert">
                <div class="inner" id="cert_box">
                    <h2>{{enter_user_name}}'s Certification</h2>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Certification ID</th>
                                <th>Certification Content</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="certification in certification_list">
                                <td>{{certification.certification_id}}</td>
                                <td>{{certification.certification}}</td>
                                <td>{{certification.status}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

            <section id="my_project">
                <div class="inner" id="myProject_box">
                    <h2>{{enter_user_name}}'s Project</h2>
                    <hr>
                    <h2>{{enter_user_name}}'s Responsible Project</h2>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Project Name</th>
                                <th>Introduction</th>
                                <th>team_name</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="project in my_responsible" v-on:click="enterProject(project.project_id, 'guest')">
                                <td>{{project.project_id}}</td>
                                <td>{{project.project_name}}</td>
                                <td>{{project.introduction}}</td>
                                <td>{{project.team_name}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <h2>{{enter_user_name}}'s Joined Project</h2>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Project Name</th>
                                <th>My Position</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="project in my_joined" v-on:click="enterProject(project.project_id, 'guest')">
                                <td>{{project.project_id}}</td>
                                <td>{{project.project_id}}</td>
                                <td>{{project.position}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

            <!-- Footer -->
            <footer id="footer">
                <div class="inner">
                    <div class="content">
                        <section style="margin: auto;">
                            <h3>Accumsan montes viverra</h3>
                            <p>Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat eu sed ante lacinia sapien lorem accumsan varius montes viverra nibh in adipiscing. Lorem ipsum dolor vestibulum ante ipsum primis in faucibus vestibulum. Blandit adipiscing eu felis iaculis volutpat ac adipiscing sed feugiat eu faucibus. Integer ac sed amet praesent. Nunc lacinia ante nunc ac gravida.</p>
                        </section>
                        <section style="margin: auto;">
                            <h4>Magna sed ipsum</h4>
                            <ul class="plain">
                                <li><a href="#"><i class="icon fa-twitter">&nbsp;</i>Twitter</a></li>
                                <li><a href="#"><i class="icon fa-facebook">&nbsp;</i>Facebook</a></li>
                                <li><a href="#"><i class="icon fa-instagram">&nbsp;</i>Instagram</a></li>
                                <li><a href="#"><i class="icon fa-github">&nbsp;</i>Github</a></li>
                            </ul>
                        </section>
                    </div>
                </div>
            </footer>

        </div>

    </div>
</template>

<script>

export default {
  name: 'user',
  data () {
    return {
        user_id: '',
        enter_user_id: '',
        enter_user_name: '',
        enter_real_name: '',
        enter_contact: '',
        enter_personal_info: '',
        enter_work_info: '',
        certification_list: [],
        my_responsible: [],
        my_joined: []
    }
  },
    mounted: function() {
        this.user_id = localStorage.getItem('user_id');
        this.enter_user_id = localStorage.getItem('enter_user_id');
        var self = this;
        $.ajax({
            type: 'post',
            async: true,
            crossDomain: true,
            url: 'http://localhost:8088/bbs/api/show_user',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-type', 'application/json')
            },
            data: JSON.stringify({"user_id": self.enter_user_id}),
            success: function (res) {
                self.enter_user_name = res.user_name;
                self.enter_real_name = res.real_name;
                self.enter_contact = res.contact;
                self.enter_personal_info = res.personal_info;
                self.enter_work_info = res.work_info;
            },
            error: function (res) {
                console.log('A Something wrong!');
            }
        })
        $.ajax({
            type: 'post',
            async: true,
            crossDomain: true,
            url: 'http://localhost:8088/bbs/api/show_user_certifications',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-type', 'application/json')
            },
            data: JSON.stringify({"user_id": self.user_id}),
            success: function (res) {
                self.certification_list = res;
            },
            error: function (res) {
                console.log('A Something wrong!');
            }
        })
        $.ajax({
            type: 'post',
            async: true,
            crossDomain: true,
            url: 'http://localhost:8088/bbs/api/my_project',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-type', 'application/json')
            },
            data: JSON.stringify({"user_id": self.enter_user_id}),
            success: function (res) {
                self.my_responsible = res.my_responsible;
                self.my_joined = res.my_joined;
            },
            error: function (res) {
                console.log('A Something wrong!');
            }
        })
    },
    methods: {
        print: function () {
            alert("You have changed your information successfully! ");
        },
        show_category: function () {
            alert(this.category+" "+this.search_key);
        },
        enterProject: function (project_id, reviewer) {
            localStorage.setItem("project_id", project_id);
            localStorage.setItem("reviewer", reviewer);
            window.open("project.html", "_self");
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import url(../assets/css/style.css);
    @import url(../assets/css/font-awesome.min.css);

    #bg{
        background-image: url("../assets/img/bg.jpg");
        background-position: center;
        background-blend-mode: lighten;
        background-color: lightgrey;
        background-repeat: repeat-y;
        top:0;
        width:100%;
        min-height: 100%;
        background-attachment: fixed;

        text-align: center;
        color:white;
    }
    #info_box, #cert_box, #search_box, #myProject_box{
        margin: 50px auto;
        color: darkslategray;
        width: 80rem;
        max-width: calc(100% - 10rem);
        background: white;
        padding: 40px;
        child-align: middle;
        border-radius: 6px;
    }

    #info_box h2 {
        margin-top: 20px;
    }
    #info_box h3 {
        display: inline;
    }
    #myProject_box h3 {
        display: inline;
    }

    #myProject_box input {
        width: 30%;
        margin: 20px 30px;
    }

    input {
        display: inline;
    }
    textarea{
        width: 70%;
        margin: 20px auto;
    }

    #user_name, #password, #real_name{
        width: 16%;
        margin: 20px 20px;
    }

    #id_number, #contact{
        width: 30%;
        margin: 20px 20px;
    }

    #certification {
        width: 50%;
        margin: 20px 20px;
    }
    #send_cert {
        width: 20%;
    }

    #key_box {
        width: 50%;
        margin: 20px 20px;
    }

    #change, #submit{
        width: 30%;
        margin-top: 40px;
    }
    #category {
        display: inline;
        width: 20%;
        margin: 20px 20px;
    }
    button:hover{cursor: pointer;}
</style>
