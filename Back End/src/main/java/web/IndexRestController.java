package web;

import neo4j.Neo4jDriver;
import org.neo4j.ogm.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexRestController {
    @Autowired
    private Neo4jDriver neo4jDriver;
    @RequestMapping(value = "/api/log_in" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> getOneNodeData(@RequestBody Map<String, Object> map) {
        String user_id = (String)map.get("user_id");              // 记住json一定要这么写获取数据，下面的以前项目的接口有的是xxx类型的数据所以写法不一样
        String password = (String)map.get("password");
        return neo4jDriver.log_in(user_id, password);
    }
    
    /*
    @Cacheable(cacheNames = "getonenodedatapart")
    @RequestMapping(value = "/api/getOneNodeDataPart" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, List<HashMap<String,Object>>> getOneNodeDataPart(@RequestParam("name_1") String name1,@RequestParam("edge_1") int edge1,@RequestParam("edge_2") int edge2) {
        HashMap<String, HashMap<String, List<HashMap<String,Object>>>> hashMap = new HashMap();
        return neo4jDriver.getOneNoderesult(name1,edge1,edge2);
    }
    @RequestMapping(value = "/api/getAll" , method = RequestMethod.GET ,produces = "application/json")
    public HashMap<String, List<HashMap<String,Object>>> getAll() {
        HashMap<String, HashMap<String, List<HashMap<String,Object>>>> hashMap = new HashMap();
        return neo4jDriver.getAllNodes();
    }
    @RequestMapping(value = "/api/addOneNode" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, Object> addOneNode(@RequestBody Map<String, Object> map) {
        String label = (String)map.get("label");
        int id = (int)map.get("id");
        String name = (String)map.get("name");
        String type = (String)map.get("type");
        String layer = (String)map.get("layer");
        String performance = (String)map.get("performance");
        ArrayList<Integer> relations = (ArrayList<Integer>)map.get("relations");
        return neo4jDriver.addOneNode(label, id, name, type, layer, performance, relations);
    }
    @RequestMapping(value = "/api/getNeighbors" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, List<HashMap<String,Object>>> getNeighbors(@RequestParam("name") String name) {
        HashMap<String, HashMap<String, List<HashMap<String,Object>>>> hashMap = new HashMap();
        return neo4jDriver.getNeighbors(name);
    }
    @RequestMapping(value = "/api/getLabel" , method = RequestMethod.POST ,produces = "application/json")
    public HashMap<String, List<HashMap<String,Object>>> getLabel(@RequestParam("label") String label) {
        HashMap<String, HashMap<String, List<HashMap<String,Object>>>> hashMap = new HashMap();
        return neo4jDriver.getLabel(label);
    }
    */
}
