package business;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import java.io.Writer;
import java.util.LinkedHashMap;

public class Parser {

    public final static XStream oParser = new XStream(new JettisonMappedXmlDriver());

    static {
        setAlias(oParser);
    }

    public static XStream oJSON(boolean DROP_ROOT_MODE) {
        XStream oJSON;
        if (DROP_ROOT_MODE) {
            oJSON = new XStream(new JsonHierarchicalStreamDriver() {
                @Override
                public HierarchicalStreamWriter createWriter(Writer oWriter) {
                    return new JsonWriter(oWriter, JsonWriter.DROP_ROOT_MODE);
                }
            });
        } else {
            oJSON = new XStream(new JsonHierarchicalStreamDriver());
        }
        setAlias(oJSON);
        oJSON.alias("Integer", Integer.TYPE);
        oJSON.alias("Boolean", Boolean.TYPE);
        oJSON.alias("Long", Long.TYPE);
        oJSON.alias("map", LinkedHashMap.class);
        return oJSON;
    }

    public static XStream oXML() {
        XStream oXML = new XStream();
        setAlias(oXML);
        return oXML;
    }

    private static void setAlias(XStream o) {
        oParser.setMode(o.NO_REFERENCES);
        //xStream.alias("Result", Return.class);
    }
}
