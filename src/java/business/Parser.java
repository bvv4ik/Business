package business;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.Writer;
import java.util.LinkedHashMap;

public class Parser {

   // public static XStream oParser = new XStream(new JettisonMappedXmlDriver());

  //  static {
  //      setAlias(oParser);
   // }

     public static String objectToXML(Object inputObject) {
        String sReturn = null;
        XStream oXML;
        //oXML = new XStream(new JettisonMappedXmlDriver());
        oXML = new XStream(new DomDriver());
        //if (bMode){
        //oXML.setMode(XStream.NO_REFERENCES);
        //}
        oXML.alias("Integer", Integer.TYPE);
        oXML.alias("Boolean", Boolean.TYPE);
        oXML.alias("Long", Long.TYPE);
        oXML.alias("map", LinkedHashMap.class);
        
        sReturn = oXML.toXML(inputObject); // Превращаем объект в строку и возвращаем
        return sReturn;
        //return oXML;
     }
     
    
    public static String objectToJSON(Object inputObject, boolean bMode) {
        String sReturn = null;
        XStream oJSON;
        if (bMode) { // Иерархия или нет
            oJSON = new XStream(new JsonHierarchicalStreamDriver() {
                
                @Override
                public HierarchicalStreamWriter createWriter(Writer oWriter) {
                    return new JsonWriter(oWriter, JsonWriter.DROP_ROOT_MODE); //  STRICT_MODE    DROP_ROOT_MODE
                }
            });
        } else {
            oJSON = new XStream(new JettisonMappedXmlDriver());
        }
        
        
        //oJSON.setMode(XStream.NO_REFERENCES);        // setAlias(oJSON);
        oJSON.alias("Integer", Integer.TYPE);
        oJSON.alias("Boolean", Boolean.TYPE);
        oJSON.alias("Long", Long.TYPE);
        oJSON.alias("map", LinkedHashMap.class);
        
        sReturn = oJSON.toXML(inputObject); // Превращаем объект в строку и возвращаем
        return sReturn;
    }

//    public static XStream oXML() {
//        XStream oXML = new XStream();
//        setAlias(oXML);
//        return oXML;
//    }

//    private static void setAlias(XStream o) {
//        oParser.setMode(o.NO_REFERENCES);
//        //xStream.alias("Result", Return.class);
//    }
}
