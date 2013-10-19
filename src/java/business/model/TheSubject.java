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
public class TheSubject {
    
     private int nID;
    private int nID_OfSubject;
  
  
// Setters
 public TheSubject _nID (int i) { nID = i; return this; }
 public TheSubject _nID_OfSubject(int i) { nID_OfSubject = i; return this; }
 
 
 // Getters
 public int nID() { return nID; }
 public int nID_OfSubject() { return nID_OfSubject; }

    
 public void load (String sLogin) throws Exception   { 
        
    Connection oDC = AccessDB.oConnectionStatic("");                      
  
    // получаем № записи TheSubject
    ResultSet oSet = oDC.prepareStatement("SELECT TS.nID FROM TheSubject TS " + 
      "LEFT JOIN TheSubjectHuman TSH ON TS.nID = TSH.nID_TheSubject " +
      "LEFT JOIN Access AC ON AC.nID_TheSubjectHuman = TSH.nID " +
      "where  sLogin = '"+sLogin+"'").executeQuery()/*executeUpdate()*/;
    //a@a.aaa 
    int n=oSet.next()?oSet.getInt(1):0; 
    
    _nID(n);
    _nID_OfSubject(1); // пока выставляем 2 т.е человек 
    
    
 }
 
         
}
