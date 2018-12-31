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

    // 用户登录
    public HashMap<String, Object> log_in(String user_id, String password) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> res = new HashMap<>();
        res.put("status", "fail");
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match (n:user{user_id: $user_id}) return n.password as PW",
                        parameters("user_id",user_id, "password",password));
                while(result.hasNext()) {
                    Record record = result.next();
                    System.out.println(record.get("PW").asString());
                    System.out.println(password);
                    if (record.get("PW").asString().equals(password)) {
                        res.remove("status");
                        res.put("status", "success");
                    }
                }
            }
        }
        driver.close();
        return res;
    }

    // 下面的之前项目的接口也给你留着，可以参考写法
    /*
    public HashMap<String, List<HashMap<String,Object>>> getOneNoderesult(String name1, int step1, int step2) {
        Driver driver = GraphDatabase.driver("bolt://45.77.214.60:7687",
                AuthTokens.basic("neo4j","pzkpfw38t"));
        Map<Long,String> map = new HashMap<>();
        HashMap<String, List<HashMap<String,Object>>> resultgraph = new HashMap<>();
        try(Session session = driver.session()){
            try (Transaction tx = session.beginTransaction()){
                StatementResult result = tx.run("Match p=(n:" + CommonUtil.getlabel(name1) + "{Name:$name1 })-[r*" + step1 + ".." + step2 + "]-(m) return p as nodesrelation",
                        parameters("name1",name1));
                List<HashMap<String,Object>> allnodes = new ArrayList<>();
                List<HashMap<String,Object>> allrelations = new ArrayList<>();
                while(result.hasNext()){
                    System.out.println(1);
                    Record record = result.next();
                    Path path = record.get("nodesrelation").asPath();
                    Iterable<Node> nodes = path.nodes();
                    for(Node node:nodes) {
                        HashMap<String,Object> nod = new HashMap();
                        nod.put("id",node.id());
                        nod.put("name",node.get("Name").toString());
                        if(!allnodes.contains(nod))
                            allnodes.add(nod);
                    }
                    Iterable<Relationship> relations = path.relationships();
                    for(Relationship relationship:relations) {
                        HashMap<String,Object> rela = new HashMap();
                        rela.put("source",relationship.startNodeId());
                        rela.put("target",relationship.endNodeId());
                        rela.put("type",relationship.type());
                        allrelations.add(rela);
                    }

                }
                resultgraph.put("nodes",allnodes);
                resultgraph.put("links",allrelations);

            }
        }
        driver.close();
        return resultgraph;
    }
    public HashMap<String, List<HashMap<String,Object>>> getAllNodes() {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        Map<Long,String> map = new HashMap<>();
        HashMap<String, List<HashMap<String,Object>>> resultgraph = new HashMap<>();
        try(Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("Match p=(n)-[r]-(m) return p as nodesrelation");
                List<HashMap<String,Object>> allnodes = new ArrayList<>();
                List<HashMap<String,Object>> allrelations = new ArrayList<>();
                while(result.hasNext()){
                    Record record = result.next();
                    Path path = record.get("nodesrelation").asPath();
                    Iterable<Node> nodes = path.nodes();
                    for(Node node:nodes) {
                        HashMap<String,Object> nod = new HashMap();
                        nod.put("id",node.id());
                        nod.put("name",node.get("name").toString());
                        nod.put("type",node.get("type").toString());
                        nod.put("layer",node.get("layer").toString());
                        nod.put("performance",node.get("performance").toString());
                        if(!allnodes.contains(nod))
                            allnodes.add(nod);
                    }
                    Iterable<Relationship> relations = path.relationships();
                    for(Relationship relationship:relations) {
                        HashMap<String,Object> rela = new HashMap();
                        rela.put("source",relationship.startNodeId());
                        rela.put("target",relationship.endNodeId());
                        rela.put("type",relationship.type());
                        allrelations.add(rela);
                    }
                }
                resultgraph.put("nodes",allnodes);
                resultgraph.put("links",allrelations);
            }
        }
        driver.close();
        return resultgraph;
    }

    public HashMap<String, Object> addOneNode(String label, int id, String name, String type, String layer, String performance, ArrayList<Integer> relations) {
        Driver driver = GraphDatabase.driver("bolt://111.231.237.28:7687",
                AuthTokens.basic("neo4j","111111"));
        HashMap<String, Object> result = new HashMap<>();
        //System.out.println();
        result.put("state", "Operation failed");
        try(Session session = driver.session()) {
            StatementResult newID = session.run("CREATE (n: "+label+") "+
                            "SET n.id = $id "+"SET n.name = $name "+"SET n.type = $type "+"SET n.layer = $layer "+
                            "SET n.performance = $performance "+"return id(n)",
                    parameters("id",id,"name",name,"type",type,"layer",layer,"performance",performance));
            int trueId = newID.single().get(0).asInt();
            System.out.println(relations);
            for(int i=0; i<relations.size(); i++) {
                session.run("Start a=node("+trueId+"),b=node("+relations.get(i)+") Merge (a)-[r:rel{type:'Have'}]->(b)");
            }
            result.remove("state");
            result.put("state", "Operation succeeded");
        }
        driver.close();
        return result;
    }

    public HashMap<String, List<HashMap<String,Object>>> getNeighbors(String name) {
        Driver driver = GraphDatabase.driver("bolt://45.77.214.60:7687",
                AuthTokens.basic("neo4j","pzkpfw38t"));
        Map<Long,String> map = new HashMap<>();
        HashMap<String, List<HashMap<String,Object>>> resultgraph = new HashMap<>();
        try(Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("Match p=(n{name:$name})-[r]-(m) return p as nodesrelation", parameters("name",name));
                List<HashMap<String,Object>> allnodes = new ArrayList<>();
                List<HashMap<String,Object>> allrelations = new ArrayList<>();
                while(result.hasNext()){
                    Record record = result.next();
                    Path path = record.get("nodesrelation").asPath();
                    Iterable<Node> nodes = path.nodes();
                    for(Node node:nodes) {
                        HashMap<String,Object> nod = new HashMap();
                        nod.put("id",node.id());
                        nod.put("name",node.get("name").toString());
                        nod.put("type",node.get("type").toString());
                        nod.put("layer",node.get("layer").toString());
                        nod.put("performance",node.get("performance").toString());
                        if(!allnodes.contains(nod))
                            allnodes.add(nod);
                    }
                    Iterable<Relationship> relations = path.relationships();
                    for(Relationship relationship:relations) {
                        HashMap<String,Object> rela = new HashMap();
                        rela.put("source",relationship.startNodeId());
                        rela.put("target",relationship.endNodeId());
                        rela.put("type",relationship.type());
                        allrelations.add(rela);
                    }
                }
                resultgraph.put("nodes",allnodes);
                resultgraph.put("links",allrelations);
            }
        }
        driver.close();
        return resultgraph;
    }

    public HashMap<String, List<HashMap<String,Object>>> getLabel(String label) {
        Driver driver = GraphDatabase.driver("bolt://45.77.214.60:7687",
                AuthTokens.basic("neo4j","pzkpfw38t"));
        Map<Long,String> map = new HashMap<>();
        HashMap<String, List<HashMap<String,Object>>> resultgraph = new HashMap<>();
        try(Session session = driver.session()) {
            try (Transaction tx = session.beginTransaction()) {
                StatementResult result = tx.run("Match p=(n: "+label+")-[r]-(m) return p as nodesrelation");
                List<HashMap<String,Object>> allnodes = new ArrayList<>();
                List<HashMap<String,Object>> allrelations = new ArrayList<>();
                while(result.hasNext()){
                    Record record = result.next();
                    Path path = record.get("nodesrelation").asPath();
                    Iterable<Node> nodes = path.nodes();
                    for(Node node:nodes) {
                        HashMap<String,Object> nod = new HashMap();
                        nod.put("id",node.id());
                        nod.put("name",node.get("name").toString());
                        nod.put("type",node.get("type").toString());
                        nod.put("layer",node.get("layer").toString());
                        nod.put("performance",node.get("performance").toString());
                        if(!allnodes.contains(nod))
                            allnodes.add(nod);
                    }
                    Iterable<Relationship> relations = path.relationships();
                    for(Relationship relationship:relations) {
                        HashMap<String,Object> rela = new HashMap();
                        rela.put("source",relationship.startNodeId());
                        rela.put("target",relationship.endNodeId());
                        rela.put("type",relationship.type());
                        allrelations.add(rela);
                    }
                }
                resultgraph.put("nodes",allnodes);
                resultgraph.put("links",allrelations);
            }
        }
        driver.close();
        return resultgraph;
    }
    */



    public static void main(String[] args) {
        Neo4jDriver neo4jDriver = new Neo4jDriver();
        //System.out.println(neo4jDriver.getOneNoderesult("The_New_England_Journal_of_Medicine",1,2));
    }
}
