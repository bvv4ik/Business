 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.parser.*;
import zLogic.Authorization;
//import org.groupby.parser.JSON.*;
//import org.groupby.parser.JSON.*;
//import com.google.gson.Gson;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import java.io.*;
//import com.bw.entity.access.Access;
/**
 *
 * @author Ser
 */
public class New {

    /**
     * @param args the command line arguments
     */
    //fromJson
    public static void main(String[] args) throws Exception {
        // TODO code application logic here


        String jsonText = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
        JSONParser parser = new JSONParser();
        ContainerFactory containerFactory = new ContainerFactory() {
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }
        };

        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();
            System.out.println("==iterate result==");
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();

                if ((entry.getKey()).equals("first")) {
                    System.out.println(entry.getKey() + "---" + entry.getValue());
                }
                //System.out.println(entry.getKey() + "---" + entry.getValue());
                //System.out.println(entry.getValue()) ;
                //+ "---" + entry.getValue());
            }

            //System.out.println("==toJSONString()==");
            // System.out.println(JSONValue.toJSONString(json));
        } catch (Exception pe) {
            System.out.println(pe);
        }



        /*
         JSONObject obj=new JSONObject();
         obj.put("name","foo");
         obj.put("num",new Integer(100));
         obj.put("balance",new Double(1000.21));
         obj.put("is_vip",new Boolean(true));
         obj.put("nickname",null);
         System.out.print(obj);      
         */




        /*
         s="{}";
         obj=JSONValue.parse(s);
         System.out.println(obj);
                
         s="[5,]";
         obj=JSONValue.parse(s);
         System.out.println(obj);
                
         s="[5,,2]";
         obj=JSONValue.parse(s);
         System.out.println(obj);
         */

        //  Access ac = new Access() ;  
        // ac._bDisabled(10);    
        //ac.SetnID(10);
        //ac.SetsLogin("ser");
        //System.out.println(ac.GetnID());        
        //System.out.println(ac.GetsLogin());        
        //System.out.println();
        // System.out.println("*******************");
        // System.out.println(ac.bDisabled());


        //=== ConnectLdap c = new ConnectLdap();
        //c.getPasswordUser1("neworganizationalUnit2");

        //c.CreateLoginAndPasswordInLdap("sergo3", "1");
        System.out.println("*******************");
//------  String ssss = ConnectLdap.sGetPassword("sergo3");


// ------System.out.println(ssss);
        // c.searchLdap("sergo1");
        System.out.println("*******************");
        //System.out.println(c.searchLdap("sergo33"));
// System.out.println(c.FindLdapLogin("123"));
        String[] sss = {"123"};

        //sss[] = '324','324','324';
        //= Authorization.getFirstLastSureName(String);
        // System.out.println(sss[3]);

        //Authorization.getFirstLastSureName(sss);

        //String[] sss1 = Authorization.getFirstLastSureName("df",sss);
        //  System.out.println(sss1[1]);
        //System.out.println(Authorization.getFirstLastSureName("df",sss).toString());
        //System.out.println(Authorization.getFirstLastSureName("sLastName"));
        //System.out.println(Authorization.getFirstLastSureName("sFirstName"));
        //System.out.println(Authorization.getFirstLastSureName("sSureName"));


        System.out.println(Authorization.getFirstLastSureName("sFirstName", "222"));
        System.out.println(Authorization.getFirstLastSureName("sLastName", "222"));
        System.out.println(Authorization.getFirstLastSureName("sSureName", "222"));

        // Registration lo = new Registration();
        // System.out.println(lo.VerifyLoginUnique("3331")); 

        System.out.println("*******************");
        System.out.println("*******************");
//  System.out.println(ac.getID("11111"));

        //LoginUsers lo = new LoginUsers();       
        //lo.AddLoginPasswordMailUser("4444", "4444", "4444", "sReturnLogin");

//   Connection oConnection = AccessDB.oConnectionStatic("");


        //System.out.println(lo.CreateUserInDB("5555599--5"));
        //lo.CreateUserInDB("5555599--4");

        //System.out.println("*******************");
        //System.out.println(c.sReturnPasswordUser);





        Dog d = new Dog();
        d.squearSum(10, 12);
        long l = d.squearSum(10, 12);
        System.out.println(l);
        System.out.println("Курс " + d.toUSD(5, 5.7));



//String sha1_ad1 = AeSimpleSHA1.SHA1("1");   
//System.out.println(sha1_ad1);

        //c.modfyPasswordUser();       
        //setPasswordUser


    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        session.setAttribute("sLogin", "text");

        String value = session.getAttribute("sLogin").toString();
        //String sLogin = session.getAttribute("sLogin");

        System.out.println("*******************");
        System.out.println(value);

    }
}
