/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

 
import java.util.HashMap;
import java.util.Map;
 
import org.codehaus.jackson.map.ObjectMapper;
 
public class MapJsonExample {
    public static void main(String[] args) {
 
	try {
 
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
 
		Map map = new HashMap();
		map.put("name", "mkyong");
		map.put("age", "29");
 
		//convert map to JSON string
		json = mapper.writeValueAsString(map);
 
		System.out.println(json);
 
	} catch (Exception e) {
		e.printStackTrace();
	}
 
    }
}