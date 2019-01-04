<template>
    <div class="hello">
        <!-- Header -->
        <header id="header">
            <a class="logo" href="#banner">Admin</a>
        </header>

        <div id="bg">
            <!-- Banner -->
            <section id="banner">
                <div class="inner">
                    <h1>Welcome! ITeam Admin</h1>
                    <p>Please handle <br />
                        Reports and Certifications</p>
                </div>
            </section>

            <section id="search">
                <div class="inner" id="re_ce_box">
                    <div id="h2">To handle <button class="button4"  v-on:click="showData">refresh</button></div>
                    <div id="h3"><p>Reports</p></div>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Report ID</th>
                                <th>Target ID</th>
                                <th>Reason</th>
                                <th>detail</th>
                            </tr>
                            </thead>
                            <tbody>
                             <tr v-for="reports in report_result">
                                <th>{{reports.report_id}}</th>
                                <th>{{reports.target_id}}</th>
                                <th>{{reports.reason}}</th>
                                <th>
                                    <button class="button1" v-on:click="review_report(reports.report_id)">detail</button>
                                    <button class="button1" v-on:click="ban_user(reports.target_id,reports.report_id)">ban</button>
                                    <button class="button1" v-on:click="set_report_read(reports.report_id)">read</button>
                                </th>
                             </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <div id="h3"><p>Certification</p></div>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                            <tr>
                                <th>Certification ID</th>
                                <th>User ID</th>
                                <th>Certification</th>
                                <th>Status</th>
                                <th>Operation</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="certifications in certification_result">
                                <td>{{certifications.certification_id}}</td>
                                <td>{{certifications.user_id}}</td>
                                <td>{{certifications.certification}}</td>
                                <td>{{certifications.status}}</td>
                                <td> <button class="button2" v-on:click="certificate(certifications.certification_id),showCertifications()"></button>&nbsp;&nbsp;<button class="button3" v-on:click="reject_certification(certifications.certification_id),showCertifications()"></button></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

            <section id="my_project">
                <div class="inner" id="myProject_box">
                    <h2>Thanks for your working!</h2>
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
  name: 'admin',
  data () {
    return {
        search_key: '',
        category: 'user',
        report_result:[],
        certification_result:[]
    }
  },


      methods:{
                 showData:function () {
                        this.showReports();
                        this.showCertifications();
                    },

                showReports:function () {
                                    var self=this;
                                    $.ajax({
                                      async: false ,
                                      url: 'http://localhost:8088/bbs/api/show_reports',
                                      crossDomain: true,
                                      beforeSend: function (xhr)
                                      {
                                            xhr.setRequestHeader('Content-type', 'application/json')
                                       },
                                      success: function (res) {
                                        alert('get reports success!');
                                        self.report_result=res;
                                      },
                                      error:function () {
                                        alert('get reports fail!');
                                      }
                                    })
                                    },

                showCertifications:function () {
                        var self=this;
                        $.ajax({
                              async: false ,
                              url: 'http://localhost:8088/bbs/api/show_certifications',
                              crossDomain: true,
                              beforeSend: function (xhr)
                              {
                                    xhr.setRequestHeader('Content-type', 'application/json')
                               },
                               success: function (res) {
                                     alert('get certifications success!');
                                     self.certification_result=res;
                               },
                               error:function (res) {
                                        alert('get certifications fail!');
                                      }
                                    })
                                },
                 certificate:function (certification_id) {
                     $.ajax({
                            type:'post',
                             async: false,
                             crossDomain: true,
                              url: 'http://localhost:8088/bbs/api/certificate',
                                beforeSend: function(xhr)
                                 {
                                        xhr.setRequestHeader('Content-type','application/json');
                                 },
                                 data: JSON.stringify({"certification_id":certification_id}),
                                 success: function (res) {
                                        alert('operation '+res.status);
                                  },
                                  error:function (res ) {
                                        alert('operation '+res.status);
                                  }
                               })
                 },

                 ban_user:function (user_id,report_id) {
                            $.ajax({
                            type:'post',
                            async: true ,
                            crossDomain: true,
                            url: 'http://localhost:8088/bbs/api/ban_user',
                            beforeSend: function(xhr)
                            {
                                    xhr.setRequestHeader('Content-type','application/json');
                             },
                            data: JSON.stringify({"user_id":user_id,"report_id":report_id}),
                            success: function (res) {
                                    alert('operation '+res.status);
                            },
                            error:function (res) {
                                   alert('operation '+res.status);
                            }
                          })
                 },

                 set_report_read:function (report_id) {
                            $.ajax({
                            type:'post',
                            async: true ,
                            crossDomain: true,
                            url: 'http://localhost:8088/bbs/api/set_report_read',
                            beforeSend: function(xhr)
                            {
                                   xhr.setRequestHeader('Content-type','application/json');
                             },
                             data: JSON.stringify({"report_id":report_id}),
                             success: function (res) {
                                   alert('operation '+res.status);
                             },
                             error:function (res) {
                                  alert('operation '+res.status);
                              }
                             })
                 },

                  reject_certification:function (certification_id) {
                             $.ajax({
                             type:'post',
                             async: false ,
                             crossDomain: true,
                             url: 'http://localhost:8088/bbs/api/reject_certification',
                             beforeSend: function(xhr)
                             {
                                    xhr.setRequestHeader('Content-type','application/json');
                              },
                              data: JSON.stringify({"certification_id":certification_id}),
                              success: function (res) {
                                    alert('operation '+res.status);
                              },
                              error:function (res) {
                                    alert('operation '+res.status);

                              }
                              })
                     },

                  review_report:function (report_id) {
                      $.ajax({
                          type:'post',
                          async: true ,
                          crossDomain: true,
                          url: 'http://localhost:8088/bbs/api/review_report',
                          beforeSend: function(xhr)
                          {
                              xhr.setRequestHeader('Content-type','application/json');
                          },
                          data: JSON.stringify({"report_id":report_id}),
                          success: function (res) {
                              alert('report information:  \nreport_id--'+res.report_id+' \nsender_id--'+res.sender_id+' \ntarget_id--'+res.target_id+' \nreason--'+res.reason+' \nstatus--'+res.status);
                          },
                          error:function (res) {
                              alert('operation '+res.status);

                          }
                      })
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
    #info_box, #re_ce_box, #myProject_box{
        margin: 50px auto;
        color: darkslategray;
        width: 80rem;
        max-width: calc(100% - 10rem);
        background: white;
        padding: 40px;
        child-align: middle;
        border-radius: 6px;
    }

    #h2 {
        margin-top: 20px;
        font-size:35px;
        font-weight: bolder;

    }

    #h3 {
        text-align: left;
        font-weight: bolder;
        font-size:28px;
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
