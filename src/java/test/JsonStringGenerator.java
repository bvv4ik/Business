/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author http://java-sample-program.blogspot.in/  
 * HashMap to Json String generator
 */
public class JsonStringGenerator {
 

 /**
  * Method to convert map into json format
  * @param map with data to be converted into json
  * @return json string
  */
 public static String createJsonString(HashMap jsonMap) throws IOException {
  System.out.println("Map:"+jsonMap);
  Writer writer = new StringWriter();
  JsonGenerator jsonGenerator = new JsonFactory().
        createJsonGenerator(writer);
  ObjectMapper mapper = new ObjectMapper();
  mapper.writeValue(jsonGenerator, jsonMap);
  jsonGenerator.close();
  System.out.println(writer.toString());
  return writer.toString();
 }
 
 public static void main(String[] args)  throws IOException {
  HashMap<String, String> map = new HashMap<String, String>();
  map.put("1", "Jason Stathom");
  map.put("2", "Brad Pitt");
  map.put("3", "Angelina");
  createJsonString(map);
 }
 
}