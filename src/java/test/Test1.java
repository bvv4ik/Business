
package test;

import business.Parser;
import com.bw.io._;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import java.util.Map;

@XStreamAlias("message")

class Test1 {

private Logger oLog = Logger.getLogger(getClass());


        private String name;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }



    public static void main(String[] args) throws Exception, IOException {
    
         DOMConfigurator.configure(_.PATH_LOG4J_XML);
         long start = System.currentTimeMillis();
        
         
         Test1 t1 = new Test1();
         t1.setName("Sergey");
         
         
         ArrayList<String[]> aL = new ArrayList<String[]>();
         String[] sArr2 = {"1","2","3","4"};
         String[] sArr3 = {"3","4","4","5"};
         aL.add(sArr2);
         aL.add(sArr3);
         
         // L.add("12yyy3");
         // L.add("12wer3");
         
         
         
         Map m = new LinkedHashMap();
         m.put("param1", "value1");
         m.put("param2", "value2");
         m.put("param3", "value3");
         
         //  try {
         
        
         
         //   } catch (IOException e) {
         //}
        //  XStream oJson = Parser.oToJSON(true);
        // String sss= oJson.toXML(aL);
      
         
        String sJson = Parser.objectToJSON(t1, false);
         System.out.println(sJson);
         
         //JSONObject jsonObj = new JSONObject(); //("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
         //jsonObj.put("sJson", m);
         
         
         //System.out.println(jsonObj.toString());
         //System.out.println(jsonObj.names());
         
         
         
         
         
         //String sss = (HashMap)xstream.fromXML(sJson);
         
         //System.out.println(product.getName());
         
         // HashMap m2  = new HashMap((HashMap) Parser.oParser.fromXML(sReturn));
         
         
         //       XStream oXML = Parser.objectToXML();
         //       String sss1= oXML.toXML(m);
         //       System.out.println(sss1);
         
         
         long stop = System.currentTimeMillis();
         System.out.println((double)(stop-start)/1000 + " seconds");
         
         
         //String sss = System.getProperty("user.dir");
         // String path = new java.io.File(".").getCanonicalPath();
         //System.out.println(path);           
    
    }


    
    private String ser(String s) {
        //System.out.println(" run Test1 11111111111   "+s);
       //  oLog.debug("](Время выполнения: "+s);
       
         
               //ОБРАЗЕЦ!!!
                HashMap m  = new HashMap();
                m.put("param1", "value1");
                m.put("param2", "value2");
                m.put("param3", "value3");
                
                
                
                
               //String sReturn = m.toString();                       
               // String sReturn = Parser.oParser.toXML(m);
               // String sReturn = Parser.oParser.toXML(m);

                        //Parser.oParser.toXML(m);
              //ОБРАЗЕЦ!!!
              //ОБРАЗЕЦ!!!
              //  HashMap m2  = new HashMap((HashMap) Parser.oParser.fromXML(sReturn));
              //ОБРАЗЕЦ!!!
                
                
              //  String s1=Text.My+"";
                 // System.out.println(sReturn);
         
         
         
         
        return (s);

    }
}
