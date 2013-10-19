/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Ser
 */
public class Link {
    
      
   private int nID;
   private int nID_Contact;
   private int nID_LinkProvider;
   private String sLink;
   
   
// Setters
  public Link _nID(int i) { nID = i; return this; }
  public Link _nID_Contact(int i) { nID_Contact = i; return this; }
  public Link _nID_LinkProvider(int i) { nID_LinkProvider = i; return this; }
  public Link _sLink(String s) { sLink=s; return this;  }
  
  
 // Getters
 public int nID() { return nID; }
 public int nID_Contact() { return nID_Contact; }
 public int nID_LinkProvider() { return nID_LinkProvider; }
 public String sLink() { return sLink; }
 
 
  

   public void setLink (String sLogin, String sLinkProvider) throws Exception   { 
    
    Connection oDC = AccessDB.oConnectionStatic("");    
    
    oDC.prepareStatement("insert Contact values()").executeUpdate();
    
    ResultSet oSet2 =oDC.prepareStatement("SELECT @@identity").executeQuery();
    int n1=oSet2.next()?oSet2.getInt(1):0;    
    
     AccessDB.closeConnectionStatic("", oDC); 
             
   }
    
    
//    SELECT TS.nID FROM TheSubject TS 
//LEFT JOIN TheSubjectHuman TSH ON TS.nID = TSH.nID_TheSubject
//LEFT JOIN Access AC ON AC.nID_TheSubjectHuman = TSH.nID 
//where  sLogin = 'a@a.aaa'
    
 //   oDC.prepareStatement("INSERT INTO Link (nID_Contact, nID_LinkProvider, sLink) "
  //            + "VALUES ("+i+",'Ip....','"+sTime+"','ссылка откуда...',1,'доп. инф')").executeUpdate();
     //1900-11-11 11:11:11
      
  
 
 
 
 
}
