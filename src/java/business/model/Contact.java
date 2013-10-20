/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;

public class Contact {

private int nID;
  
// Setters
 public Contact _nID(int i) { nID = i; return this; }

 // Getters
 public int nID() { return nID; }
    
 
 public void setContact (String sLogin, 
                         String sLinkType, 
                         String sLinkTypeInfo, 
                         String sLinkProvider, 
                         String sLink) throws Exception { 
      
    Connection oConnection = AccessDB.oConnectionStatic("");                      

    // получаем № записи TheSubject   //a@a.aaa 
    TheSubject T = new TheSubject();
    T.load(sLogin);
    int i = T.nID();
    //LinkType L = new LinkType();
    //L._sLinkType(sLinkType);
    //L._sLinkTypeInfo(sLinkTypeInfo);
    
    oConnection.prepareStatement("INSERT INTO LinkType (sLinkType, sLinkTypeInfo) "
            + "VALUES ("+sLinkType+",'"+sLinkType+"'").executeUpdate();
     
    ResultSet oSet =oConnection.prepareStatement("SELECT @@identity").executeQuery();
    int n=oSet.next()?oSet.getInt(1):0; 
    
    oConnection.prepareStatement("INSERT INTO LinkProvider (nID_LinkType, nID_TheSubject, sLinkProvider) "
              + "VALUES ("+n+",'"+i+"','"+sLinkProvider+"')").executeUpdate();
    
    oConnection.prepareStatement("INSERT INTO Contact VALUES()").executeUpdate();
    
  //  ResultSet oSet2 =oConnection.prepareStatement("SELECT @@identity").executeQuery();
  //  int n1=oSet2.next()?oSet2.getInt(1):0;    
    
    
    AccessDB.closeConnectionStatic("", oConnection); 
  
  }   
 
 
 
}
