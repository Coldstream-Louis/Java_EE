<template>
    <div class="hello">
        <!-- Header -->
        <header id="header">
            <a class="logo" href="#banner">ITeam - {{reviewer}} Mode</a>
            <nav>
                <a href="newPage.html">Home</a>
            </nav>
        </header>

        <div id="bg">

            <section id="KG">
                <svg id="graph" width="1200" height="600"></svg>
                <button v-on:click="show()" class="button fit" style="width: 40%;">Visualization</button>
                <label style="color:black; display: block;">
                    <input style="width:240px; margin-top: 20pt;" type="range" min="0" max="0.2" step="any" value="0.1"> Link Strength
                </label>
            </section>

            <section id="search">
                <div class="inner" id="info_box">
                    <h2>Project Information</h2>
                    <h3 style="display: block;">Project ID: {{project_id}}</h3>
                    <h3>Project Name:</h3>
                    <input disabled v-model="project_name" type="text" name="project_name" id="project_name" value="" style="width: 50%"/>
                    <h3 style="display: block;">Introduction:</h3>
                    <textarea disabled name="introduction" id="introduction" rows="4" v-model="introduction"></textarea>
                    <h3>Responsible:</h3>
                    <input disabled v-model="responsible" type="text" name="responsible" id="responsible" value="" />
                    <h3>Team Name:</h3>
                    <input disabled v-model="team_name" type="text" name="team_name" id="team_name" value="" placeholder="You have no authority to see it. " />
                    <h3>Fund:</h3>
                    <input disabled v-model="fund" type="text" name="fund" id="fund" value="" placeholder="You have no authority to see it. " />
                    <button v-on:click="enable()" class="button fit" id="change" disabled>Edit Project Information</button>
                    <button v-on:click="change_info()" class="button fit primary" id="submit" disabled>Submit your change</button>
                    <hr>
                    <h3>Position List</h3>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Position ID</th>
                                <th>Position Name</th>
                                <th>Description</th>
                                <th>Position Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="position in position_list">
                                <td>{{position.position_id}}</td>
                                <td>{{position.position_name}}</td>
                                <td>{{position.description}}</td>
                                <td>{{position.position_status}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <div class="row">
                        <h3>Position Name:</h3>
                        <input disabled v-model="position_name" type="text" name="position_name" id="position_name" value="" style="width: 30%"/>
                        <h3>Position Status:</h3>
                        <input disabled v-model="position_status" type="text" name="position_status" id="position_status" value="" style="width: 30%"/>
                    </div>
                    <h3>Description:</h3>
                    <input disabled v-model="description" type="text" name="description" id="description" value="" style="width: 80%"/>
                    <button v-on:click="create_position()" class="button fit primary" id="create_position" disabled>Create A New Position</button>
                </div>
            </section>

            <section id="team">
                <div class="inner" id="team_box">
                    <h2>Send request to join us(for guest)</h2>
                    <input v-model="position_id" type="text" name="position_id" id="position_id" value="" style="width: 50%" placeholder="Enter the position id here"/>
                    <button v-on:click="send_request()" class="button fit primary" id="send_request">Send Request</button>
                    <div class="row" id="res_only">
                        <hr>
                        <h2>Manage requests received</h2>
                        <div class="table-wrapper">
                            <table>
                                <thead>
                                <tr>
                                    <th>Position ID</th>
                                    <th>Position Name</th>
                                    <th>User ID</th>
                                    <th>User Name</th>
                                    <th>Status</th>
                                    <th>Operation</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="request in request_list">
                                    <td>{{request.position_id}}</td>
                                    <td>{{request.request_position}}</td>
                                    <td>{{request.user_id}}</td>
                                    <td>{{request.user_name}}</td>
                                    <td>{{request.status}}</td>
                                    <td>
                                        <button v-on:click="accept(request.user_id, request.position_id)" class="button fit" style="display: inline; width:40%;">Accept</button>
                                        <button v-on:click="reject(request.user_id, request.position_id)" class="button fit" style="display: inline; width:40%;">Reject</button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
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
import {showData2} from "../assets/js/dataV";

export default {
  name: 'project',
  data () {
    return {
        user_id: "",
        project_id: "",
        reviewer: "",
        project_name: "",
        introduction: "",
        responsible: "",
        team_name: "",
        fund: "",
        position_list: [],
        request_list: [],
        position_name: '',
        position_status: '',
        status: '',
        position_id: '',
        nodes: [],
        links: []
    }
  },
    mounted: function() {
        this.user_id = localStorage.getItem('user_id');
        this.project_id = localStorage.getItem('project_id');
        this.reviewer = localStorage.getItem('reviewer');
        if (this.reviewer == 'responsible') {
            document.getElementById("change").disabled = false;
            document.getElementById("position_name").disabled = false;
            document.getElementById("position_status").disabled = false;
            document.getElementById("description").disabled = false;
            document.getElementById("create_position").disabled = false;
        }
        var self = this;
        $.ajax({
            type: 'post',
            async: true,
            crossDomain: true,
            url: 'http://localhost:8088/bbs/api/project_info',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-type', 'application/json')
            },
            data: JSON.stringify({"project_id": self.project_id, "reviewer":self.reviewer}),
            success: function (res) {
                self.nodes=res.nodes;
                self.links=res.links;
                console.log(self.nodes);
                console.log(self.links);
            },
            error: function (res) {
                console.log('A Something wrong!');
            }
        })
        $.ajax({
            type: 'post',
            async: true,
            crossDomain: true,
            url: 'http://localhost:8088/bbs/api/show_project',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-type', 'application/json')
            },
            data: JSON.stringify({"project_id": self.project_id}),
            success: function (res) {
                self.project_name = res.project_name;
                self.introduction = res.introduction;
                self.responsible = res.responsible;
                if (self.reviewer == "guest") {
                    return;
                } else {
                    self.fund = res.fund;
                    self.team_name = res.team_name;
                }
            },
            error: function (res) {
                console.log('A Something wrong!');
            }
        })
        $.ajax({
            type: 'post',
            async: true,
            crossDomain: true,
            url: 'http://localhost:8088/bbs/api/show_positions',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-type', 'application/json')
            },
            data: JSON.stringify({"project_id": self.project_id}),
            success: function (res) {
                self.position_list=res;
            },
            error: function (res) {
                console.log('A Something wrong!');
            }
        })
        if(this.reviewer == 'responsible') {
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/show_requests',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"project_id": self.project_id}),
                success: function (res) {
                    self.request_list=res;
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        }
    },
    methods: {
        show_category: function () {
            alert(this.category+" "+this.search_key);
        },
        show: function () {
            showData2(this.nodes, this.links);
        },
        enable: function () {
            document.getElementById("project_name").disabled = false;
            document.getElementById("introduction").disabled = false;
            document.getElementById("responsible").disabled = false;
            document.getElementById("team_name").disabled = false;
            document.getElementById("fund").disabled = false;
            document.getElementById("submit").disabled = false;
        },
        change_info: function () {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/edit_project',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"project_id": self.project_id, "name": self.project_name, "introduction": self.introduction,
                    "team_name": self.team_name, "fund": self.fund, "responsible": self.responsible}),
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
        create_position: function () {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/create_position',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"project_id": self.project_id, "position_name": self.position_name, "description": self.description,
                    "position_status": self.position_status}),
                success: function (res) {
                    if(res.status == "success") {
                        alert("You have changed your information successfully! ");
                        window.open("project.html", "_self");
                    }
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
        send_request: function () {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/send_request',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"project_id": self.project_id, "position_id": self.position_id, "user_id": self.user_id,}),
                success: function (res) {
                    if(res.status == "success") {
                        alert("You have sent your request successfully! ");
                        window.open("project.html", "_self");
                    }
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
        accept: function (user_id, position_id) {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/accept_request',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"project_id": self.project_id, "position_id": position_id, "user_id": user_id,}),
                success: function (res) {
                    if(res.status == "success") {
                        alert("Accept successfully! ");
                        window.open("project.html", "_self");
                    }
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
        reject: function (user_id, position_id) {
            var self = this;
            $.ajax({
                type: 'post',
                async: true,
                crossDomain: true,
                url: 'http://localhost:8088/bbs/api/reject_request',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-type', 'application/json')
                },
                data: JSON.stringify({"project_id": self.project_id, "position_id": position_id, "user_id": user_id,}),
                success: function (res) {
                    if(res.status == "success") {
                        alert("Reject successfully! ");
                        window.open("project.html", "_self");
                    }
                },
                error: function (res) {
                    console.log('A Something wrong!');
                }
            })
        },
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
    #info_box, #team_box {
        margin: 50px auto;
        color: darkslategray;
        width: 70rem;
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

    textarea{
        max-width: 70%;
        margin: 20px auto;
    }

    #info_box input{
        display: inline;
        width: 16%;
        margin: 20px 20px;
    }

    #id_number, #contact, #send_request{
        width: 30%;
        margin: 20px 20px;
    }

    #key_box {
        width: 50%;
        margin: 20px 20px;
    }

    #change, #submit{
        width: 30%;
        margin-top: 20px;
        margin-bottom: 20px;
    }
    #category {
        display: inline;
        width: 20%;
        margin: 20px 20px;
    }
    button:hover{cursor: pointer;}

    svg{
        background-color: white;
        max-width: 100%;
        margin-top: 40px;
        border-radius: 5px;
    }
    #send_request, #position_id{
        display: inline;
    }
</style>
