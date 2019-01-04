package web;

import neo4j.Neo4jDriver;
import org.neo4j.ogm.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.GetMessageCode;

@RestController
public class IndexRestController {
    @Autowired
    private Neo4jDriver neo4jDriver;
    @RequestMapping(value = "/api/log_in" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> getOneNodeData(@RequestBody Map<String, Object> map) {
        String user_id = (String)map.get("user_id");
        String password = (String)map.get("password");
        return neo4jDriver.log_in(user_id, password);
    }
    @RequestMapping(value = "/api/create_user" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> create_user(@RequestBody Map<String, Object> map) {
        String user_name = (String)map.get("user_name");
        String password = (String)map.get("password");
        String id_number = (String)map.get("id_number");
        String contact = (String)map.get("contact");
        return neo4jDriver.create_user(user_name, password,id_number,contact);
    }
    @RequestMapping(value = "/api/edit_user" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> edit_user(@RequestBody Map<String, Object> map) {
        String user_id = (String)map.get("user_id");
        String user_name = (String)map.get("user_name");
        String personal_information = (String)map.get("personal_information");
        String work_information = (String)map.get("work_information");
        String real_name = (String)map.get("real_name");
        String contact = (String)map.get("contact");
        return neo4jDriver.edit_user(user_id, user_name, personal_information, work_information, contact, real_name);
    }

    @RequestMapping(value = "/api/create_report" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> create_report(@RequestBody Map<String, Object> map) {
        String sender_id = (String)map.get("sender_id");
        String target_id = (String)map.get("target_id");
        String reason = (String)map.get("reason");

        return neo4jDriver.create_report(sender_id,target_id,reason);
    }

    @RequestMapping(value = "/api/send_certification" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> send_certification(@RequestBody Map<String, Object> map) {
        String user_id = (String)map.get("user_id");
        String certification = (String)map.get("certification");
        return neo4jDriver.send_certification(user_id,certification);
    }

    @RequestMapping(value = "/api/review_report" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> review_report(@RequestBody Map<String, Object> map) {
        String report_id = (String)map.get("report_id");
        return neo4jDriver.review_report(report_id);
    }

    @RequestMapping(value = "/api/show_reports" , method = RequestMethod.GET ,produces = "application/json")
    public List<HashMap<String, Object>> show_reports() {
        return neo4jDriver.show_reports();
    }

    @RequestMapping(value = "/api/show_certifications" , method = RequestMethod.GET ,produces = "application/json")
    public List<HashMap<String, Object>> show_certifications() {
        return neo4jDriver.show_certifications();
    }

    @RequestMapping(value = "/api/certificate" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> certificate(@RequestBody Map<String, Object> map) {
        String certification_id=(String)map.get("certification_id");
        return neo4jDriver.certificate(certification_id,"success");
    }
    @RequestMapping(value = "/api/ban_user" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> ban_user(@RequestBody Map<String, Object> map) {
        String user_id=(String)map.get("user_id");
        String report_id=(String)map.get("report_id");
        return neo4jDriver.ban_user(user_id,report_id);
    }

    @RequestMapping(value = "/api/show_user" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> show_user(@RequestBody Map<String, Object> map) {
        String user_id=(String)map.get("user_id");
        return neo4jDriver.show_user(user_id);
    }

    @RequestMapping(value = "/api/create_position" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> create_position(@RequestBody Map<String, Object> map) {
        String position_name=(String)map.get("position_name");
        String description=(String)map.get("description");
        String position_status=(String)map.get("position_status");
        String project_id=(String)map.get("project_id");
        return neo4jDriver.create_position(position_name,description,position_status,project_id);
    }

    @RequestMapping(value = "/api/send_request" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> send_request(@RequestBody Map<String, Object> map) {
        String user_id=(String)map.get("user_id");
        String position_id=(String)map.get("position_id");
        String project_id=(String)map.get("project_id");
        return neo4jDriver.send_request(user_id,project_id,position_id);
    }

    @RequestMapping(value = "/api/edit_position" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> edit_position(@RequestBody Map<String, Object> map) {
        String position_id=(String)map.get("position_id");
        String position_name=(String)map.get("position_name");
        String description=(String)map.get("description");
        String position_status=(String)map.get("position_status");
        return neo4jDriver.edit_position(position_id,position_name,description,position_status);
    }

    @RequestMapping(value = "/api/accept_request" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> accept_request(@RequestBody Map<String, Object> map) {
        String position_id=(String)map.get("position_id");
        String user_id=(String)map.get("user_id");
        String project_id=(String)map.get("project_id");
        return neo4jDriver.accept_request(user_id,position_id,project_id);
    }

    @RequestMapping(value = "/api/reject_request" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> reject_request(@RequestBody Map<String, Object> map) {
        String position_id=(String)map.get("position_id");
        String user_id=(String)map.get("user_id");
        String project_id=(String)map.get("project_id");
        return neo4jDriver.reject_request(user_id,position_id,project_id);
    }

    @RequestMapping(value = "/api/kick_member" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> kick_member(@RequestBody Map<String, Object> map) {
        String position_id=(String)map.get("position_id");
        String user_id=(String)map.get("user_id");
        String project_id=(String)map.get("project_id");
        return neo4jDriver.kick_member(user_id,position_id,project_id);
    }

    @RequestMapping(value = "/api/search_users" , method = RequestMethod.POST ,produces = "application/json")
    public List<HashMap<String, Object>> search_users(@RequestBody Map<String, Object> map) {
        String name_match=(String)map.get("name_match");
        return neo4jDriver.search_users(name_match);
    }

    @RequestMapping(value = "/api/show_requests" , method = RequestMethod.POST ,produces = "application/json")
    public List<HashMap<String, Object>> show_requests(@RequestBody Map<String, Object> map) {
        String project_id=(String)map.get("project_id");
        return neo4jDriver.show_requests(project_id);
    }

    @RequestMapping(value = "/api/search_projects" , method = RequestMethod.POST ,produces = "application/json")
    public List<HashMap<String, Object>> search_projects(@RequestBody Map<String, Object> map) {
        String name_match=(String)map.get("name_match");
        return neo4jDriver.search_projects(name_match);
    }

    @RequestMapping(value = "/api/create_project" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> create_project(@RequestBody Map<String, Object> map) {
        String name=(String)map.get("name");
        String introduction=(String)map.get("introduction");
        String responsible=(String)map.get("responsible");
        String team_name=(String)map.get("team_name");
        String fund=(String)map.get("fund");
        return neo4jDriver.create_project(name, introduction, responsible, team_name, fund);
    }

    @RequestMapping(value = "/api/edit_project" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> edit_project(@RequestBody Map<String, Object> map) {
        String project_id=(String)map.get("project_id");
        String name=(String)map.get("name");
        String introduction=(String)map.get("introduction");
        String responsible=(String)map.get("responsible");
        String team_name=(String)map.get("team_name");
        String fund=(String)map.get("fund");
        return neo4jDriver.edit_project(project_id, name, introduction, responsible, team_name, fund);
    }

    @RequestMapping(value = "/api/my_project" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, List<HashMap<String, Object>>> my_project(@RequestBody Map<String, Object> map) {
        String user_id=(String)map.get("user_id");
        return neo4jDriver.my_project(user_id);
    }

    @RequestMapping(value = "/api/show_project" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> show_project(@RequestBody Map<String, Object> map) {
        String project_id=(String)map.get("project_id");
        return neo4jDriver.show_project(project_id);
    }

    @RequestMapping(value = "/api/show_positions" , method = RequestMethod.POST ,produces = "application/json")
    public List<HashMap<String, Object>> show_positions(@RequestBody Map<String, Object> map) {
        String project_id=(String)map.get("project_id");
        return neo4jDriver.show_positions(project_id);
    }

    @RequestMapping(value = "/api/send_MessageCode" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> send_MessageCode(@RequestBody Map<String, Object> map) {
        HashMap<String, Object> res=new HashMap<>();
        String telephone=(String)map.get("contact");
        res.put("code",GetMessageCode.getCode(telephone));
        return res;
    }

    @RequestMapping(value = "/api/show_user_certifications" , method = RequestMethod.POST ,produces = "application/json")
    public List<HashMap<String, Object>> show_user_certification(@RequestBody Map<String, Object> map) {
        String user_id=(String)map.get("user_id");
        return neo4jDriver.show_user_certifications(user_id);
    }

    @RequestMapping(value = "/api/set_report_read" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> set_report_read(@RequestBody Map<String, Object> map) {

        String report_id=(String)map.get("report_id");
        return neo4jDriver.set_report_read(report_id);
    }

    @RequestMapping(value = "/api/reject_certification" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> reject_certification(@RequestBody Map<String, Object> map) {

        String certification_id=(String)map.get("certification_id");
        return neo4jDriver.reject_certification(certification_id);
    }

    @RequestMapping(value = "/api/project_info" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String,List<HashMap<String, Object>>> project_info(@RequestBody Map<String, Object> map) {
        String project_id=(String)map.get("project_id");
        String reviewer=(String)map.get("reviewer");
        return neo4jDriver.project_info(project_id,reviewer);
    }

}
