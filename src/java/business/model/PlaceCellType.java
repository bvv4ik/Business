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
public class PlaceCellType {
 

private int nID;
private String sCellType; 

// Setters
 public PlaceCellType _nID(int i) { nID = i; return this; }
 public PlaceCellType _sCellType(String s) { sCellType=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public String sCellType() { return sCellType; }
    
 // Определяем nID Типа части строения по одному выбранному типу
public int getID (String sCellType) throws Exception { 
     int i = 0;  
     Connection oConnection = AccessDB.oConnectionStatic("");    
     ResultSet oRowset = oConnection.prepareStatement("SELECT nID FROM PlaceCellType where sCellType = '"+sCellType+"'").executeQuery();
     if (oRowset.next()){
         i = oRowset.getInt(1);
        // _nID(oRowset.getInt(1));  // Возвращаем nID по названию типа дома
         //_sBranchType(sBranchType);
        }
     AccessDB.closeConnectionStatic("", oConnection);      
     return  i;
 }
 
// Получаем список всех типов части строения
public String getAllCellType() throws Exception   { 
 String s = "";  // String utf = ""; 
 int i = 0;
  
     Connection oConnection = AccessDB.oConnectionStatic("");    
     ResultSet oRowset = oConnection.prepareStatement("SELECT sCellType FROM PlaceCellType").executeQuery();
     while (oRowset.next()){
     i++;
     s += (  ",\"a"+ i +"\":" + "\"" +oRowset.getString(1) + "\"");
     } 
       
     AccessDB.closeConnectionStatic("", oConnection);
     return s; //возвращаем список регионов в виде Json строки
   }
 
 
 
}
