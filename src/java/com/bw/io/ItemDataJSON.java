package com.bw.io;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

/*
 * Работа с обьектами в формате JSON
 * 
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemDataJSON {

    private final String sDEL = "\"\"";
    private String sRes = "";
    String sJSON = "{}";
    LinkedList aFluent = new LinkedList();
    HashMap mFluent = new HashMap();
    Object oFluent;
    String sFluentJSON = "";
    String sPathDef = "";
    private boolean bFoundAny = false;
    private boolean bFound = false;

    public String set(Map m) {
        JSONObject oParams = new JSONObject();
        oParams.putAll(m);
        sJSON = oParams.toString();
        return sJSON;
    }

    
    public String getsJSON() {
        return sJSON;
    }

    public void setsJSON(String sJSON) {
        this.sJSON = sJSON;
    }

    public ItemDataJSON(String sJSON) {
        Set("", sJSON);
    }

    public ItemDataJSON() {
        Set();
    }

    public boolean bEmpty() {
        return "{}".equals(sJSON);
    }
    ContainerFactory oContainerFactoryJSON = new ContainerFactory() {
        public List creatArrayContainer() {
            return new LinkedList();
        }

        public Map createObjectContainer() {
            return new LinkedHashMap();
        }
    };
    JSONParser oParserJSON = new JSONParser();

    public ItemDataJSON _Root() {
        sFluentJSON = sJSON;
        oFluent = null;
        aFluent = a();
        mFluent = m();
        return this;
    }

    public HashMap m() {
        return m(sFluentJSON);
    }

    public HashMap m(String sJSON) {
        HashMap mTemp = new HashMap();
        try {
            if (sJSON != null) {
                mTemp = (HashMap) oParserJSON.parse(sJSON, oContainerFactoryJSON);//jsonText
            }
        } catch (Exception _) {
            //System.out.println("[m]sFluentJSON=" + sFluentJSON + ":" + _);
            mTemp = mFluent;
        }
        return mTemp;
    }

    public LinkedList a() {
        return a(sFluentJSON);
    }

    public LinkedList a(String sJSON) {
        LinkedList aTemp = new LinkedList();
        try {
            if (sJSON != null) {
                aTemp = (LinkedList) oParserJSON.parse(sJSON, oContainerFactoryJSON);//jsonText
            }
        } catch (Exception _) {
            //System.out.println("[a]sFluentJSON=" + sFluentJSON + ":" + _);
            aTemp = aFluent;
        }
        return aTemp;

    }

    public String sPath() {
        return sPathDef;
    }

    public ItemDataJSON _Path(String sPath) {
        sPathDef = sPath;
        String[] as = sPath.split("\\.");
        for (int n = 0; n < as.length; n++) {
            String sName = as[n];
            bFoundAny = false;
            _m(sName, true);
            _a(sName, true);
            if (!bFoundAny) {
                break;
            }
        }
        return this;
    }

    public ItemDataJSON _m(String sName) {
        return _m(sName, false);
    }

    public ItemDataJSON _m(String sName, boolean bSkipNull) {
        bFound = false;
        if (mFluent.size() > 0) {
            //sJSON=(String)mFluent.get(sName);
            oFluent = mFluent.get(sName);
            String s = sJSON(oFluent);
            bFound = s != null;
            bFoundAny |= bFound;
            if (!bSkipNull || bFound) {
                sFluentJSON = s;
                mFluent = m();
            }
            //aFluent= a();
        }
        return this;
    }

    public ItemDataJSON _a(String sName) {
        return _a(sName, false);
    }

    public ItemDataJSON _a(String sName, boolean bSkipNull) {
        bFound = false;
        if (mFluent.size() > 0) {
            //sJSON=(String)mFluent.get(sName);
            oFluent = mFluent.get(sName);
            //sFluentJSON=sJSON(oFluent);
            String s = sJSON(oFluent);
            bFound = s != null;
            bFoundAny |= bFound;
            if (!bSkipNull || bFound) {
                sFluentJSON = s;
                aFluent = a();
            }
        }
        return this;
    }

    public String s() {
        return sJSON(oFluent);
    }

    private String sa() {
        return sJSON(aFluent);
    }

    private String sm() {
        return sJSON(mFluent);
    }

    private String sJSON(Object o) {
        String s = null;
        try {
            s = JSONValue.toJSONString(o);
        } catch (Exception _) {
            //System.out.println("sJSON(o):" + _);
        }
        return s;
    }

    private String sJSON(LinkedList a) {
        return sJSON((Object) a);
    }

    private String sJSON(HashMap m) {
        return sJSON((Object) m);
    }

    public String sList(String sName) {
        String s = sUnPack(Get(sName)).replaceAll("\\Q\",\\E", "[;]"), sT = "";//int n=0;
        StringTokenizer oS = new StringTokenizer(s, "[;]"), oSs;
        while (oS.hasMoreTokens()) {
            oSs = new StringTokenizer(oS.nextToken(), ":");
            if (oSs.hasMoreTokens()) {
                sT = sT + (sT.length() > 0 ? "," : "") + oSs.nextToken();
            }
        }
        return sT.replaceAll("\"", "");
    }

    public String GetUnPacked(String sName) {
        return sUnPack(Get(sName));
    }

    public String sUnPack(String sData) {
        String s;
        if (!"".equals(sData)) {//String.valueOf(
            s = sData.charAt(sData.length() - 1) + "";
            if ("}".equals(s) || "]".equals(s) || "\"".equals(s) || ",".equals(s)) {
                sData = sData.substring(0, sData.length() - 1);
            }
            s = sData.charAt(0) + "";
            if ("{".equals(s) || "[".equals(s) || "\"".equals(s) || ",".equals(s)) {
                sData = sData.substring(1);
            }
        }
        return sData;
    }

    public String sPack(String sData) {
        return "\"" + sData + "\"";
    }

    public String sPack(String sName, String sData) {
        String s = sData.length() > 0 ? sData.charAt(0) + "" : "";
        if (!"{".equals(s)) {
            if (sData.indexOf(":") > 0) {
                sData = "{" + sData + "}";
            } else {
                sData = sPack(sData);
            }
        }
        return sNode(sName) + sData;
    }

    public String sNode(String sName) {
        return sPack(sName) + ":";
    }

    public final String Set() {
        Set("", sDEL);
        return Get();
    }

    public final String Set(String sName) {
        Set(sName, sDEL);
        return Get();
    }

    public final String Set(String sName, String sData) {
        boolean bDel = sDEL.equals(sData);
        if ("".equals(sName)) {
            sJSON = bDel || "".equals(sData) ? "{}" : sData;
        } else {
            String sO = sJSON, s = sNode(sName);
            if (sO.indexOf(s) >= 0) {
                sO = sO.replaceFirst("\\Q" + s + Get(sName) + "\\E", bDel ? "" : sPack(sName, sData));
            } else if (!bDel) {
                sO = sO.substring(0, sO.length() - 1)
                        + (!"{".equals(sO.charAt(sO.length() - 2) + "") ? "," : "")
                        + sPack(sName, sData) + sO.substring(sO.length() - 1);
            }
            sJSON = sO;
        }
        _Root();
        return Get();
    }

    public String Get() {
        return sJSON;
    }

    public String Get(String sName) {
        String sO = Get();
        String sRes = "";
        if ("".equals(sName)) {
            sRes = sO;
        } else {
            String sAt = sNode(sName);
            int nAt = sO.indexOf(sAt), nTo = 0, nI;//Parsing and finding Node
            if (nAt >= 0) {
                nAt = nAt + sAt.length();
                if (nAt < sO.length()) {
                    sAt = sO.charAt(nAt) + "";
                    int nAtCur, nToCur, n = 0, i, nOff = 1;
                    String sTo = "\"";
                    sTo = "{".equals(sAt) ? "}" : ("[".equals(sAt) ? "]" : (!sTo.equals(sAt) ? ",}" : sTo));
                    while (nOff > 0) {
                        nAtCur = nAt + n + 1;
                        nTo = -1;
                        for (i = 0; i < sTo.length(); i++) {
                            nI = sO.indexOf(sTo.charAt(i) + "", nAtCur);
                            if (nTo < 0 || (nI < nTo & nI >= 0)) {
                                nTo = nI;
                            }
                        }
                        if (nTo >= 0) {
                            nToCur = nTo - 1;
                            if (nToCur < sO.length() & nAtCur <= nToCur) {
                                Matcher m = Pattern.compile("\\" + sAt).matcher(sO.substring(nAtCur, nToCur + 1));
                                while (m.find()) {
                                    nOff++;
                                }
                            }
                            n = nTo - nAt;
                        } else {
                            nTo = sO.length() - 2;
                            break;
                        }
                        nOff--;
                    }
                    sRes = sO.substring(nAt, nTo + 1);
                }
            }
        }
        this.sRes = sRes;
        return sRes;
    }

    public Object fromJson() {
        return null;
    }
}
