<template>
    <div class="hello">
        <!-- Header -->
        <header id="header">
            <a class="logo" href="#banner">ITeam - {{user_name}}</a>
            <nav>
                <a href="#banner">Home</a>
                <a href="#info">My Info</a>
                <a href="#search">Search</a>
                <a href="index.html">My Project</a>
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
                        <input v-model="user_name" type="text" name="user_name" id="user_name" value="" placeholder="User Name" />
                        <h3>Password: </h3>
                        <input v-model="password" type="password" name="password" id="password" value="" placeholder="Password" />
                        <h3>Real Name:</h3>
                        <input v-model="real_name" type="text" name="real_name" id="real_name" value="" placeholder="Password" />
                    </div>
                    <div class="row">
                        <h3>ID Number:</h3>
                        <input v-model="id_number" type="text" name="id_number" id="id_number" value="" placeholder="Identification Number" />
                        <h3>Contact:</h3>
                        <input v-model="contact" type="text" name="contact" id="contact" value="" placeholder="Mobile" />
                    </div>
                    <div class="row">
                        <h3>Personal Information: </h3>
                        <textarea name="personal info" id="personal_info" placeholder="Add your personal information here" rows="6" v-model="personal_info"></textarea>
                        <h3>Work Information: </h3>
                        <textarea name="personal info" id="work_info" placeholder="Add your work information here" rows="6" v-model="work_info"></textarea>
                    </div>
                    <button v-on:click="print()" class="button fit primary" id="change">Submit Your Change</button>
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
                    <button v-on:click="show_category()" class="button primary icon fa-search">Search</button>
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
                            <tr>
                                <td>19</td>
                                <td>Alpha</td>
                                <td>Ante turpis integer aliquet porttitor.</td>
                                <td>Jack</td>
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
                            <tr>
                                <td>7615</td>
                                <td>Shuyang</td>
                                <td>Ante turpis integer aliquet porttitor.</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

            <section id="my_project">
                <div class="inner" id="myProject_box">
                    <h2>My Project</h2>
                    <h3>My Responsible Project</h3>
                </div>
            </section>

        </div>

    </div>
</template>

<script>
export default {
  name: 'newPage',
  data () {
    return {
        user_name: 'Jack',
        user_id: '7615',
        real_name: 'none',
        password: '123456',
        id_number: '901283921',
        contact: '1203923846',
        personal_info: 'none',
        work_info: 'none',
        search_key: '',
        category: 'user',
    }
  },
    methods: {
        print: function () {
            alert("You have changed your information successfully! ");
        },
        show_category: function () {
            alert(this.category+" "+this.search_key);
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
    #info_box, #search_box, #myProject_box{
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

    input {
        display: inline;
    }
    textarea{
        width: 70%;
        margin: 20px auto;
    }

    #user_name, #password, #real_name {
        width: 16%;
        margin: 20px 20px;
    }

    #id_number, #contact{
        width: 30%;
        margin: 20px 20px;
    }

    #key_box {
        width: 50%;
        margin: 20px 20px;
    }

    #change{
        width: 40%;
        margin-top: 40px;
    }
    #category {
        display: inline;
        width: 20%;
        margin: 20px 20px;
    }
    button:hover{cursor: pointer;}
</style>
