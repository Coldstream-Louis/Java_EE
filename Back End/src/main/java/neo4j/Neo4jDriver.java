package neo4j;

import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import util.CommonUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;
@Component
public class Neo4jDriver {

    // 用户注册
    public HashMap<String, Object> create_user(String user_name, String password,String id_number,String contact) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("id", -1);
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                String user_id=contact.substring(contact.length()-4, contact.length());
                StatementResult result = tx.run("Match (n:user{contact: $contact}) return n.password as PW",
                        parameters("contact",contact));

                if (result.hasNext())
                    System.out.println("user_id exists");
                else
                {

                    StatementResult newID = tx.run("CREATE (n: user) "+
                                    "SET n.user_name = $user_name "+"SET n.id_number = $id_number "+"SET n.password = $password "+"SET n.contact = $contact "+
                                    "SET n.personal_information = $personal_information "+"SET n.real_name = $real_name "+"SET n.status = $status "+
                                    "SET n.user_id = $user_id "+"SET n.work_information = $work_information "+"return id(n) as ID",
                            parameters("user_name",user_name,
                                    "id_number",id_number,
                                    "password",password,
                                    "contact",contact,
                                    "personal_information","none",
                                    "real_name","none",
                                    "status","正常",
                                    "user_id",user_id,
                                    "work_information","none"
                            ));
                    Record record=newID.next();
                    res.remove("id");
                    res.put("user_id", user_id);
                    tx.success();
                }
            }
        }
        driver.close();
        return res;
    }
    // 用户登录
    public HashMap<String, Object> log_in(String user_id, String password) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:user{user_id: $user_id}) return n as user",
                        parameters("user_id",user_id));
                while(result.hasNext())
                {
                    Record record = result.next();
                    Node node = record.get("user").asNode();
                    if (node.get("password").asString().equals(password)) {
                        res.remove("status");
                        res.put("result", "success");
                        res.put("user_name", node.get("user_name").asString());
                        res.put("password", node.get("password").asString());
                        res.put("real_name", node.get("real_name").asString());
                        res.put("id_number", node.get("id_number").asString());
                        res.put("work_info", node.get("work_information").asString());
                        res.put("personal_info", node.get("personal_information").asString());
                        res.put("contact", node.get("contact").asString());
                        res.put("status", node.get("status").asString());
                    }
                }
            }
        }
        driver.close();
        return res;
    }
    //编辑用户信息
    public HashMap<String, Object> edit_user(String user_id, String user_name,String personal_information,String work_information,String contact,String real_name) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:user{user_id: $user_id}) "+"SET n.user_name = $user_name "+"SET n.contact = $contact "+
                                "SET n.personal_information = $personal_information "+"SET n.real_name = $real_name "+
                                "SET n.work_information = $work_information "+"return id(n) as ID",
                        parameters("user_id", user_id,
                                "user_name",user_name,
                                "contact",contact,
                                "personal_information",personal_information,
                                "real_name",real_name,
                                "work_information",work_information));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }

        }
        driver.close();
        return res;
    }
    //发送举报
    public HashMap<String, Object> create_report(String sender_id, String target_id,String reason) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult report_num=tx.run("Match (n:report) return count(n) as num");
                String report_id=""+(Integer.valueOf(report_num.next().get("num").toString())+1);
                StatementResult result = tx.run("Create (n:report) "+"SET n.report_id = $report_id "+"SET n.target_id = $target_id "+
                                "SET n.reason = $reason "+"SET n.status = $status ",
                        parameters(
                                "report_id",report_id,
                                "target_id",target_id,
                                "reason",reason,
                                "status","unhandled"));
                result=tx.run("MATCH (r:report),(u:user) WHERE r.report_id=$report_id AND u.user_id=$user_id CREATE (u)-[e:send_report{sender_id:u.user_id,report_id:r.report_id}]->(r)  RETURN e",
                        parameters("report_id",report_id,
                                "user_id",sender_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }
    //发送认证请求
    public HashMap<String, Object> send_certification(String user_id, String certification) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult certification_num=tx.run("Match (n:certification) return count(n) as num");
                String certification_id=""+(Integer.valueOf(certification_num.next().get("num").toString())+1);
                StatementResult result = tx.run("Create (n:certification) "+"SET n.certification_id = $certification_id "+"SET n.user_id = $user_id "+"SET n.certification = $certification "+"SET n.status = $status ",
                        parameters(
                                "certification_id",certification_id,
                                "user_id", user_id,
                                "certification",certification,
                                "status","unhandled"));
                result=tx.run("MATCH (r:certification),(u:user) WHERE r.certification_id=$certification_id AND u.user_id=$user_id CREATE (u)-[e:has_certification{user_id:u.user_id,certification_id:r.certification_id}]->(r)  RETURN e",
                        parameters("certification_id",certification_id,
                                "user_id",user_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }
    //查看用户信息
    public HashMap<String, Object> show_user(String user_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();

        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result=tx.run("Match (n:user) where n.user_id=$user_id return n as user ",parameters("user_id",user_id));
                if (result.hasNext()) {
                    Record record = result.next();
                    Node node = record.get("user").asNode();
                    res.put("user_id",node.get("user_id").asString());
                    res.put("user_name", node.get("user_name").asString());
                    res.put("real_name", node.get("real_name").asString());
                    res.put("work_info", node.get("work_information").asString());
                    res.put("personal_info", node.get("personal_information").asString());
                    res.put("contact", node.get("contact").asString());
                    res.put("status", node.get("status").asString());
                    tx.success();
                }
                else
                    System.out.println("report_id not exists.");
            }
        }
        driver.close();
        return res;
    }

    //查看举报请求
    public HashMap<String, Object> review_report(String report_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        HashMap<String, Object> res = new HashMap<>();

        try (Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("Match (u:user)-[r:send_report]->(n:report) where r.report_id=$report_id return n as report,r as send_report ", parameters("report_id", report_id));
                if (result.hasNext()) {
                    Record record = result.next();
                    Node node = record.get("report").asNode();
                    Relationship re = record.get("send_report").asRelationship();
                    res.put("sender_id", re.get("sender_id").asString());
                    res.put("report_id", node.get("report_id").asString());
                    res.put("reason", node.get("reason").asString());
                    res.put("target_id", node.get("target_id").asString());
                    res.put("status", node.get("status").asString());
                    tx.success();
                } else
                    System.out.println("report_id not exists.");
            }
        }
        driver.close();
        return res;
    }

    //展示用户举报请求
    public List<HashMap<String, Object>> show_reports() {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        List<HashMap<String, Object>> reports = new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result=tx.run("Match (n:report) return n as reports ");
                while (result.hasNext()) {
                    Record record = result.next();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> res = new HashMap<>();
                            res.put("report_id", node.get("report_id").asString());
                            res.put("reason", node.get("reason").asString());
                            res.put("target_id", node.get("target_id").asString());
                            reports.add(res);
                        }
                    }
                    tx.success();
                }

            }
        }
        driver.close();
        return reports;
    }
    //查看所有认证请求
    public List<HashMap<String, Object>> show_certifications() {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        List<HashMap<String, Object>> reports = new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result=tx.run("Match (n:certification) return n as certifications ");
                while (result.hasNext()) {
                    Record record = result.next();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> res = new HashMap<>();
                            res.put("certification_id", node.get("certification_id").asString());
                            res.put("user_id", node.get("user_id").asString());
                            res.put("certification", node.get("certification").asString());
                            res.put("status", node.get("status").asString());
                            reports.add(res);
                        }
                    }
                    tx.success();
                }

            }
        }
        driver.close();
        return reports;
    }
    //处理认证请求
    public HashMap<String, Object> certificate(String certification_id,String operation) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String,Object> res=new HashMap<>();
        res.put("status" ,"fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result=tx.run("Match (n:certification) where n.certification_id=$certification_id set n.status=$status",
                        parameters("certification_id",certification_id,"status",operation));
                res.remove("status");
                res.put("status","success");
                tx.success();
            }

        }

        driver.close();
        return res;
    }

    //封禁用户
    public HashMap<String, Object> ban_user(String user_id,String report_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String,Object> res=new HashMap<>();
        res.put("status" ,"fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result=tx.run("Match (n:user),(r:report) where n.user_id=$user_id and r.report_id=$report_id and r.target_id=$user_id set n.status=$status set r.status='banned' return n.status",
                        parameters("user_id",user_id,"report_id",report_id,"status","banned"));
                if(result.hasNext()) {
                    res.remove("status");
                    res.put("status", "success");
                    tx.success();
                }

            }

        }

        driver.close();
        return res;
    }

    //创建职位
    public HashMap<String, Object> create_position(String position_name, String description,String position_status,String project_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult position_num=tx.run("Match (n:position) return count(n) as num");
                Record record=position_num.next();
                String position_id="";
                if(record.get("num").asInt()==0)
                    position_id="1";
                else
                {
                    position_num=tx.run("Match (n:position) return max(n.position_id) as num");
                    record=position_num.next();
                    position_id=""+(Integer.valueOf(record.get("num").asString())+1);
                }
                StatementResult result = tx.run("Match (p:project) where p.project_id=$project_id Create (n:position)"+"SET n.position_id = $position_id "+"SET n.position_name = $position_name "+"SET n.description = $description "+"SET n.position_status = $position_status "
                                +"SET n.project_id = $project_id "+"SET n.project_name = p.project_name ",
                        parameters(
                                "position_id",position_id,
                                "position_name", position_name,
                                "description",description,
                                "position_status",position_status,
                                "project_id",project_id));
                result=tx.run("MATCH (n:position),(e:project) WHERE n.position_id=$position_id AND e.project_id=$project_id CREATE (n)-[r:belong_to_project]->(e)  RETURN r",
                        parameters("position_id",position_id,
                                "project_id",project_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //发送加入团队请求
    public HashMap<String, Object> send_request(String user_id, String project_id,String position_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result=tx.run("MATCH (r:project),(u:user) WHERE r.project_id=$project_id AND u.user_id=$user_id CREATE (u)-[e:request{user_id:u.user_id,project_id:r.project_id,position_id:$position_id,status:'未处理'}]->(r)  RETURN e",
                        parameters("user_id",user_id,
                                "project_id",project_id,
                                "position_id",position_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //编辑职位信息
    public HashMap<String, Object> edit_position(String position_id, String position_name,String description,String position_status) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:position{position_id: $position_id}) "+"SET n.position_name = $position_name "+"SET n.description = $description "+
                                "SET n.position_status = $position_status "+"return id(n) as ID",
                        parameters("position_id", position_id,
                                "position_name",position_name,
                                "description",description,
                                "position_status",position_status));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }
    //接受加入请求
    public HashMap<String, Object> accept_request(String user_id, String position_id,String project_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (u:user)-[e:request]->(r:project),(p:position) where e.user_id=$user_id and e.project_id=$project_id and u.user_id=$user_id and p.position_id=$position_id "
                                +"SET p.position_status = $position_status "
                                +"set e.status='已接受'"
                                + "Create (u)-[j:join_project]->(p)",
                        parameters("position_id", position_id,
                                "user_id",user_id,
                                "project_id",project_id,
                                "position_status","已招"));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //拒绝加入请求
    public HashMap<String, Object> reject_request(String user_id, String position_id,String project_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (u:user)-[e:request]->(r:project) where e.user_id=$user_id and e.project_id=$project_id and u.user_id=$user_id "
                                +"set e.status='已拒绝'",
                        parameters("position_id", position_id,
                                "user_id",user_id,
                                "project_id",project_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //剔除成员
    public HashMap<String, Object> kick_member(String user_id, String position_id,String project_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (u:user)-[e:join_project]->(p:position) where u.user_id=$user_id and p.position_id=$position_id and p.project_id=$project_id "
                                +"set p.position_status='未招' "
                                +"delete e",
                        parameters("position_id", position_id,
                                "user_id",user_id,
                                "project_id",project_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //查找成员
    public List<HashMap<String, Object>> search_users(String name_match) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        List<HashMap<String, Object>> users = new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:user) where n.user_name=~'.*"+name_match+".*' return n",
                        parameters("name_match", name_match));
                while(result.hasNext())
                {
                    Record record=result.next();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> res = new HashMap<>();
                            res.put("user_id", node.get("user_id").asString());
                            res.put("user_name", node.get("user_name").asString());
                            res.put("personal_info", node.get("personal_information").asString());
                            users.add(res);
                        }
                    }
                }
                tx.success();
            }
        }
        driver.close();
        return users;
    }

    //展示项目请求
    public List<HashMap<String, Object>> show_requests(String project_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        List<HashMap<String, Object>> users = new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (u:user)-[e:request]->(p:project),(n:position) where e.project_id=$project_id and n.position_id=e.position_id return u as u ,e as e,n as n ",
                        parameters("project_id", project_id));
                while(result.hasNext())
                {
                    Record record=result.next();
                    HashMap<String, Object> res = new HashMap<>();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            if(node.hasLabel("user")) {
                                res.put("user_id", node.get("user_id").asString());
                                res.put("user_name", node.get("user_name").asString());
                            }
                            else
                            {
                                res.put("position_id",node.get("position_id").asString());
                                res.put("request_position",node.get("position_name").asString());
                            }
                        }
                        else
                        {
                            Relationship re=value.asRelationship();
                            res.put("status",re.get("status").asString());
                        }
                    }
                    users.add(res);
                }
                tx.success();
            }
        }
        driver.close();
        return users;
    }

    //搜索项目
    public List<HashMap<String, Object>> search_projects(String name_match) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        List<HashMap<String, Object>> projects = new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:project) where n.project_name=~'.*"+name_match+".*' return n",
                        parameters("name_match", name_match));
                while(result.hasNext())
                {
                    Record record=result.next();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> res = new HashMap<>();
                            res.put("project_id", node.get("project_id").asString());
                            res.put("project_name", node.get("project_name").asString());
                            res.put("introduction", node.get("introduction").asString());
                            res.put("responsible", node.get("responsible").asString());
                            projects.add(res);
                        }
                    }
                }
                tx.success();
            }
        }
        driver.close();
        return projects;
    }

    //创建项目
    public HashMap<String, Object> create_project(String name, String introduction, String responsible, String team_name, String fund) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult project_num=tx.run("Match (n:project) return count(n) as num");
                Record record=project_num.next();
                String project_id="";
                if(record.get("num").asInt()==0)
                    project_id="1";
                else
                {
                    project_num=tx.run("Match (n:project) return max(n.project_id) as num");
                    record=project_num.next();
                    project_id=""+(Integer.valueOf(record.get("num").asString())+1);
                }
                StatementResult result = tx.run("Create (n:project) "+"SET n.project_id = $project_id "+
                                "SET n.project_name = $project_name "+"SET n.introduction = $introduction "+"SET n.responsible = $responsible "
                                +"SET n.team_name = $team_name "+"SET n.fund = $fund ",
                        parameters(
                                "project_id",project_id,
                                "project_name", name,
                                "introduction",introduction,
                                "responsible",responsible,
                                "team_name",team_name,
                                "fund", fund));
                res.put("project_id", project_id);
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //更改项目信息
    public HashMap<String, Object> edit_project(String project_id, String name, String introduction, String responsible, String team_name, String fund) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:project{project_id:$project_id}) "+ "SET n.project_name = $project_name "
                                +"SET n.introduction = $introduction "+"SET n.responsible = $responsible "+"SET n.team_name = $team_name "+"SET n.fund = $fund ",
                        parameters(
                                "project_id",project_id,
                                "project_name", name,
                                "introduction",introduction,
                                "responsible",responsible,
                                "team_name",team_name,
                                "fund", fund));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //查看与我有关的项目
    public HashMap<String, List<HashMap<String, Object>>> my_project(String user_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        HashMap<String, List<HashMap<String,Object>>> result = new HashMap<>();
        List<HashMap<String, Object>> responsible = new ArrayList<>();
        List<HashMap<String, Object>> joined = new ArrayList<>();
        List<HashMap<String, Object>> requested = new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult res_respon = tx.run("Match (n:project{responsible:$user_id}) return n",
                        parameters("user_id", user_id));
                while(res_respon.hasNext())
                {
                    Record record1=res_respon.next();
                    for(Value value:record1.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> res = new HashMap<>();
                            res.put("project_id", node.get("project_id").asString());
                            res.put("project_name", node.get("project_name").asString());
                            res.put("introduction", node.get("introduction").asString());
                            res.put("team_name", node.get("team_name").asString());
                            responsible.add(res);
                        }
                    }
                }
                StatementResult res_joined = tx.run("Match (n:user{user_id:$user_id})-[]->(m:position) return m",
                        parameters("user_id", user_id));
                while(res_joined.hasNext())
                {
                    Record record2=res_joined.next();
                    for(Value value:record2.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> res = new HashMap<>();
                            res.put("project_id", node.get("project_id").asString());
                            res.put("project_name", node.get("project_name").asString());
                            res.put("position", node.get("position_name").asString());
                            joined.add(res);
                        }
                    }
                }
                StatementResult res_request = tx.run("Match (n:user{user_id:$user_id})-[r:request]->(m:project) return r",
                        parameters("user_id", user_id));
                while(res_request.hasNext())
                {
                    Record record3 = res_request.next();
                    for(Value value:record3.values())
                    {
                        if (value.type().name().equals("RELATIONSHIP")) {
                            Relationship relationship = value.asRelationship();
                            HashMap<String,Object> rela = new HashMap<>();
                            rela.put("project_id",relationship.get("project_id").asString());
                            rela.put("status",relationship.get("status").asString());
                            StatementResult get_position = tx.run("Match (n:position{position_id:$position_id}) return n",
                                    parameters("position_id",relationship.get("position_id").asString()));
                            while(get_position.hasNext())
                            {
                                Record record4 = get_position.next();
                                for(Value value1:record4.values())
                                {
                                    if (value1.type().name().equals("NODE")) {
                                        Node node=value1.asNode();
                                        rela.put("project_name", node.get("project_name").asString());
                                        rela.put("position_name", node.get("position_name").asString());
                                    }
                                }
                            }
                            requested.add(rela);
                        }
                    }
                }
                tx.success();
                result.put("my_responsible", responsible);
                result.put("my_joined", joined);
                result.put("my_requested", requested);
            }
        }
        driver.close();
        return result;
    }

    // 显示指定项目的详情(除了职位信息)
    public HashMap<String, Object> show_project(String project_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        HashMap<String, Object> res = new HashMap<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:project{project_id:$project_id}) return n",
                        parameters("project_id", project_id));
                while(result.hasNext())
                {
                    Record record=result.next();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            res.put("project_id", node.get("project_id").asString());
                            res.put("project_name", node.get("project_name").asString());
                            res.put("introduction", node.get("introduction").asString());
                            res.put("responsible", node.get("responsible").asString());
                            res.put("team_name", node.get("team_name").asString());
                            res.put("fund", node.get("fund").asString());
                        }
                    }
                }
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    // 显示指定项目的职位信息
    public List<HashMap<String, Object>> show_positions(String project_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        List<HashMap<String, Object>> res = new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:position{project_id:$project_id}) return n",
                        parameters("project_id", project_id));
                while(result.hasNext())
                {
                    Record record=result.next();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> position = new HashMap<>();
                            position.put("position_id", node.get("position_id").asString());
                            position.put("position_name", node.get("position_name").asString());
                            position.put("description", node.get("description").asString());
                            position.put("position_status", node.get("position_status").asString());
                            res.add(position);
                        }
                    }
                }
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //展示用户申请的认证
    public List<HashMap<String, Object>> show_user_certifications(String user_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        List<HashMap<String, Object>> allcer=new ArrayList<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:certification) where n.user_id=$user_id "
                                +"return n as allcer",
                        parameters("user_id", user_id));
                while (result.hasNext()) {
                    Record record = result.next();
                    for(Value value:record.values())
                    {
                        if (value.type().name().equals("NODE")) {
                            Node node=value.asNode();
                            HashMap<String, Object> res = new HashMap<>();
                            res.put("certification_id", node.get("certification_id").asString());
                            res.put("certification", node.get("certification").asString());
                            res.put("status", node.get("status").asString());
                            allcer.add(res);
                        }
                    }
                    tx.success();
                }
            }
        }
        driver.close();
        return allcer;
    }

    //把举报设为已读
    public HashMap<String, Object> set_report_read(String report_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try (Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("Match (n:report) where n.report_id=$report_id "
                                + "set n.status='read' ",
                        parameters("report_id", report_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    //拒绝认证
    public HashMap<String, Object> reject_certification(String certification_id) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try (Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("Match (n:certification) where n.certification_id=$certification_id "
                                + "set n.status='fail' ",
                        parameters("certification_id", certification_id));
                res.remove("status");
                res.put("status", "success");
                tx.success();
            }
        }
        driver.close();
        return res;
    }

    // 可视化接口
    public HashMap<String, List<HashMap<String, Object>>> project_info(String project_id, String reviewer)
    {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j", "111111"));
        HashMap<String, List<HashMap<String, Object>>> info=new HashMap<>();
        List<HashMap<String, Object>> allnodes = new ArrayList<>();
        List<HashMap<String, Object>> allres = new ArrayList<>();
        try (Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("Match p=(n1:position)-[r1:belong_to_project]->(n2:project) where n2.project_id=$project_id return p as path ",
                        parameters("project_id", project_id));
                while (result.hasNext()) {
                    Record record = result.next();
                    Path path = record.get("path").asPath();
                    Iterable<Node> nodes = path.nodes();
                    for (Node node : nodes) {
                        HashMap<String, Object> nod = new HashMap();
                        nod.put("id", "" + node.id());
                        if (node.hasLabel("position")) {
                            nod.put("type", "position");
                            nod.put("name", node.get("position_name").asString());
                        } else if (node.hasLabel("project")) {
                            nod.put("type", "project");
                            nod.put("name", node.get("project_name").asString());
                        }
                        if (!allnodes.contains(nod))
                            allnodes.add(nod);
                    }
                    Iterable<Relationship> relations = path.relationships();
                    for (Relationship relationship : relations) {
                        HashMap<String, Object> rela = new HashMap();
                        rela.put("source", relationship.startNodeId());
                        rela.put("target", relationship.endNodeId());
                        rela.put("type", relationship.type());
                        allres.add(rela);
                    }
                }
                if(!reviewer.equals("guest")) {
                    result = tx.run("Match p=(n1:user)-[r1:join_project]->(n2:position) where n2.project_id=$project_id return p as path ",
                            parameters("project_id", project_id));
                    while (result.hasNext()) {
                        Record record = result.next();
                        Path path = record.get("path").asPath();
                        Iterable<Node> nodes = path.nodes();
                        for (Node node : nodes) {
                            HashMap<String, Object> nod = new HashMap();
                            nod.put("id", "" + node.id());
                            if (node.hasLabel("position")) {
                                nod.put("type", "position");
                                nod.put("name", node.get("position_name").asString());
                            } else if (node.hasLabel("user")) {
                                nod.put("type", "user");
                                nod.put("name", node.get("user_name").asString());
                            }
                            if (!allnodes.contains(nod))
                                allnodes.add(nod);
                        }
                        Iterable<Relationship> relations = path.relationships();
                        for (Relationship relationship : relations) {
                            HashMap<String, Object> rela = new HashMap();
                            rela.put("source", relationship.startNodeId());
                            rela.put("target", relationship.endNodeId());
                            rela.put("type", relationship.type());
                            allres.add(rela);
                        }
                    }
                }

            }
        }
        info.put("nodes",allnodes);
        info.put("links",allres);
        driver.close();
        return info;
    }



    public static void main(String[] args) {
        Neo4jDriver neo4jDriver = new Neo4jDriver();
        //System.out.println(neo4jDriver.getOneNoderesult("The_New_England_Journal_of_Medicine",1,2));
    }
}
