<template>
    <div class="hello">
        <!-- Header -->
        <header id="header">
            <a class="logo" href="#banner">ITeam - {{user_name}}</a>
            <nav>
                <a href="#banner">Home</a>
                <a href="#info">My Info</a>
                <a href="#cert">Certification</a>
                <a href="#search">Search</a>
                <a href="#my_project">My Project</a>
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
                    <h2>Your User Id is {{user_id}}</h2>
                    <div class="row">
                        <h3>User Name: </h3>
                        <input v-model="user_name" type="text" name="user_name" id="user_name" value="" placeholder="User Name" disabled/>
                        <h3>Password: </h3>
                        <input v-model="password" type="password" name="password" id="password" value="" placeholder="Password" disabled/>
                        <h3>Real Name:</h3>
                        <input v-model="real_name" type="text" name="real_name" id="real_name" value="" placeholder="Password" disabled/>
                    </div>
                    <div class="row">
                        <h3>ID Number:</h3>
                        <input v-model="id_number" type="text" name="id_number" id="id_number" value="" placeholder="Identification Number" disabled/>
                        <h3>Contact:</h3>
                        <input v-model="contact" type="text" name="contact" id="contact" value="" placeholder="Mobile" disabled/>
                    </div>
                    <div class="row">
                        <h3>Personal Information: </h3>
                        <textarea name="personal info" id="personal_info" placeholder="Add your personal information here" rows="6" v-model="personal_info" disabled></textarea>
                        <h3>Work Information: </h3>
                        <textarea name="personal info" id="work_info" placeholder="Add your work information here" rows="6" v-model="work_info" disabled></textarea>
                    </div>
                    <button v-on:click="change()" class="button fit" id="change">Change Your Information</button>
                    <button v-on:click="submit()" class="button fit primary" id="submit" disabled>Submit Your Change</button>
                </div>
            </section>

            <section id="cert">
                <div class="inner" id="cert_box">
                    <h2>Add Your Certification Here</h2>
                    <input v-model="certification" type="text" name="user_name" id="certification" value="" placeholder="Type your certification name here" />
                    <button v-on:click="send_cert()" class="button fit primary" id="send_cert">Submit Your Certification</button>
                    <hr>
                    <h2>My Certifications</h2>
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

            <section id="search">
                <div class="inner" id="search_box">
                    <h2>Explore What You Are Interested in</h2>
                    <select name="category" id="category" v-model="category">
                        <option value="user">User</option>
                        <option value="project">Project</option>
                    </select>
                    <input v-model="search_key" type="text" name="key_box" id="key_box" value="" placeholder="Enter your key" />
                    <button v-on:click="search()" class="button primary icon fa-search">Search</button>
                    <h2>Search Result</h2>
                    <hr>
                    <h3>Project</h3>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Project Name</th>
                                <th>Introduction</th>
                                <th>Responsible</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="project in project_result" v-on:click="enterProject(project.project_id, 'guest')">
                                <td>{{project.project_id}}</td>
                                <td>{{project.project_name}}</td>
                                <td>{{project.introduction}}</td>
                                <td>{{project.responsible}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <h3>User</h3>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>User ID</th>
                                <th>User Name</th>
                                <th>Personal Information</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="user in user_result" v-on:click="enter_user(user.user_id)">
                                <td>{{user.user_id}}</td>
                                <td>{{user.user_name}}</td>
                                <td>{{user.personal_info}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

            <section id="my_project">
                <div class="inner" id="myProject_box">
                    <h2>My Project</h2>
                    <hr>
                    <h2>My Responsible Project</h2>
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
                            <tr v-for="project in my_responsible" v-on:click="enterProject(project.project_id, 'responsible')">
                                <td>{{project.project_id}}</td>
                                <td>{{project.project_name}}</td>
                                <td>{{project.introduction}}</td>
                                <td>{{project.team_name}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <h2>My Joined Project</h2>
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
                            <tr v-for="project in my_joined" v-on:click="enterProject(project.project_id, 'joined')">
                                <td>{{project.project_id}}</td>
                                <td>{{project.project_id}}</td>
                                <td>{{project.position}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <h2>My Requested Project</h2>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Project Name</th>
                                <th>Request Position</th>
                                <th>Request Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="project in my_requested" v-on:click="enterProject(project.project_id, 'guest')">
                                <td>{{project.project_id}}</td>
                                <td>{{project.project_name}}</td>
                                <td>{{project.position_name}}</td>
                                <td>{{project.status}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <h2>Create Your Project</h2>
                    <div class="row">
                        <h3>Project Name: </h3>
                        <input v-model="project_name" type="text" name="user_name" id="project_name" value="" placeholder="Project Name" />
                        <h3>Responsible: </h3>
                        <input v-model="responsible" type="text" name="responsible" id="responsible" value="" placeholder="Responsible Person's User ID" />
                    </div>
                    <div class="row">
                        <h3>Team Name: </h3>
                        <input v-model="team_name" type="text" name="real_name" id="team_name" value="" placeholder="Team Name" />
                        <h3>Fund: </h3>
                        <input v-model="fund" type="text" name="real_name" id="fund" value="" placeholder="Project's Fund" />
                    </div>
                    <div class="row">
                        <h3>Introduction: </h3>
                        <textarea name="introduction" id="introduction" placeholder="Add your project introduction here" rows="6" v-model="introduction"></textarea>
                    </div>
                    <button v-on:click="createProject()" class="button fit primary" id="create_project">Create Your Project</button>
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
  name: 'newPage',
  data () {
    return {
        user_name: 'Jack',
        user_id: '',
        real_name: 'none',
        password: '123456',
        id_number: '901283921',
        contact: '1203923846',
        personal_info: 'none',
        work_info: 'none',
        certification: '',
        certification_list: [],
        search_key: '',
        category: 'user',
        user_result: [],
        project_result: [],
        my_responsible: [],
        my_joined: [],
        my_requested: [],
        project_name: '',
        responsible: '',
        team_name: '',
        fund: '',
        introduction: ''
    }
  },
    mounted: function() {
        this.user_id = localStorage.getItem('user_id');
        this.user_name = localStorage.getItem('user_name');
        this.password = localStorage.getItem("password");
        this.real_name = localStorage.getItem("real_name");
        this.id_number = localStorage.getItem("id_number");
        this.work_info = localStorage.getItem("work_info");
        this.personal_info = localStorage.getItem("personal_info");
        this.contact = localStorage.getItem("contact");
        var self = this;
        $.ajax({
            type: 'post',
            async: true,
            crossDomain: true,
            url: 'http://localhost:8088/bbs/api/my_project',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-type', 'application/json')
            },
            data: JSON.stringify({"user_id": self.user_id}),
            success: function (res) {
                self.my_responsible = res.my_responsible;
                self.my_joined = res.my_joined;
                self.my_requested = res.my_requested;
                console.log(self.my_responsible[0].project_id);
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
    },
    methods: {
        print: function () {
            alert("You have changed your information successfully! ");
        },
        show_category: function () {
            alert(this.category+" "+this.search_key);
        },
        search: function () {
            var self = this;
            if (this.category=="user") {
                $.ajax({
                    type: 'post',
                    async: true,
                    crossDomain: true,
                    url: 'http://localhost:8088/bbs/api/search_users',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader('Content-type', 'application/json')
                    },
                    data: JSON.stringify({"name_match": self.search_key,}),
                    success: function (res) {
                        self.user_result = res;
                    },
                    error: function (res) {
                        console.log('A Something wrong!');
                    }
                })
            } else {
                $.ajax({
                    type: 'post',
                    async: true,
                    crossDomain: true,
                    url: 'http://localhost:8088/bbs/api/search_projects',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader('Content-type', 'application/json')
                    },
                    data: JSON.stringify({"name_match": self.search_key,}),
                    success: function (res) {
                        self.project_result = res;
                    },
                    error: function (res) {
                        console.log('A Something wrong!');
                    }
                })
            }
        },
        change: function () {
            document.getElementById("submit").disabled=false;
            document.getElementById("user_name").disabled=false;
            //document.getElementById("password").disabled=false;
            document.getElementById("real_name").disabled=false;
            //document.getElementById("id_number").disabled=false;
            document.getElementById("contact").disabled=false;
            document.getElementById("personal_info").disabled=false;
            document.getElementById("work_info").disabled=false;
        },
        submit: function () {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/edit_user',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"user_id": self.user_id, "user_name": self.user_name, "real_name": self.real_name,
                    "contact": self.contact, "personal_information": self.personal_info, "work_information": self.work_info}),
                success: function (res) {
                    if(res.status == "success") {
                        alert("You have changed your information successfully! ");
                    }
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
        send_cert: function () {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/send_certification',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"user_id": self.user_id, "certification": self.certification}),
                success: function (res) {
                    if(res.status == "success") {
                        alert("You send your certification request successfully! ");
                    }
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
        createProject: function () {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/create_project',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"name": self.project_name, "responsible": self.responsible, "team_name": self.team_name,
                    "fund": self.fund, "introduction": self.introduction}),
                success: function (res) {
                    alert ("You have create your project successfully, your project ID is "+res.project_id);
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
        enterProject: function (project_id, reviewer) {
            localStorage.setItem("project_id", project_id);
            localStorage.setItem("reviewer", reviewer);
            window.open("project.html", "_self");
        },
        enter_user: function (user_id){
            localStorage.setItem("enter_user_id", user_id);
            window.open("user.html", "_self");
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
