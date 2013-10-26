/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zLogic;

import business.AccessDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

/**
 *
 * @author Ser
 */
public class Authorization {
  

    
public static String getFirstLastSureName11 (String Name/*, String[] Value */) {    

String[] ss = {   "This",   "is",   "Java",   "String",   "array" };
//return "--";
if (Name == "sFirstName") return "Сергей";
if (Name == "sLastName") return "Белявцев";
if (Name == "sSureName") return "Владимирович";

//return ss;
return "";
//return "[3]"; //Ошибочный!

}




public static String getFirstLastSureName(String Value, String sLogin) {
  String Last="";
  String First=""; 
  String Sure="";
 Connection oConnection = AccessDB.oConnectionStatic("");
 try{
   // HttpSession session = request.getSession(true);  
   // Object o = session.getAttribute("sLogin");
  
 ResultSet oRowset =oConnection.prepareStatement("SELECT * FROM TheSubjectHuman TSH "
         + "LEFT JOIN Access AC ON AC.nID_TheSubjectHuman = TSH.nID where  sLogin = '"+sLogin+"'").executeQuery();
 if(oRowset.next()){
 Last = oRowset.getString(4);
 First = oRowset.getString(5);
 Sure = oRowset.getString(6);
 //System.out.println(s);
 //String sPasswordDB = oRowset.getString(2);
 }
  
 //return "10"; 
 if (Value == "sLastName") return Last; 
 if (Value == "sFirstName") return First; 
 if (Value == "sSureName") return Sure; 
 
 return "ошибка";
 }

 catch (Exception _){
                    String sErr=_.getMessage();
                    System.err.println("ERROR: "+sErr+"_"+"AddLoginPasswordMailUser");//это вывод в лог-файл
                    return "";
                    }
 finally{
         AccessDB.closeConnectionStatic("", oConnection);
        }
   
 }


 //   "sFirstName",sLoginInter);
  //      session.setAttribute("sLastName",sLoginInter);
   //     session.setAttribute("sSureName"
    
   /* 
   public void AutorizationUser1(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        
  HttpSession session = request.getSession(true);    
  session.setAttribute("sLogin","text");
  
  String value = session.getAttribute("sLogin").toString();
  //String sLogin = session.getAttribute("sLogin");
  
  System.out.println("*******************");
  System.out.println(value);
  
    }
    */
   
    
    
}
