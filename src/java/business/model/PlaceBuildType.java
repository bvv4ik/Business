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
public class PlaceBuildType {
    

 
private int nID;
private String sBuildType; 

// Setters
 public PlaceBuildType _nID(int i) { nID = i; return this; }
 public PlaceBuildType _sBuildType(String s) { sBuildType=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public String sBuildType() { return sBuildType; }
 
 
 // Определяем nID Типа дома по одному выбранному типу
public int getID (String sBuildType) throws Exception { 
     int i = 0;  
     Connection oConnection = AccessDB.oConnectionStatic("");    
     ResultSet oSet = oConnection.prepareStatement("SELECT nID FROM PlaceBuildType1 where sBuildType = '"+sBuildType+"'").executeQuery();
     if (oSet.next()){
         i = oSet.getInt(1);
        // _nID(oSet.getInt(1));  // Возвращаем nID по названию типа дома
         //_sBranchType(sBranchType);
        }
     AccessDB.closeConnectionStatic("", oConnection);      
     return  i;
 }
 
// Получаем список всех типов домов
public String getAllBuildType() throws Exception   { 
 String s = "";  // String utf = ""; 
 int i = 0;
  
     Connection oConnection = AccessDB.oConnectionStatic("");    
     ResultSet oSet = oConnection.prepareStatement("SELECT sBuildType FROM PlaceBuildType1").executeQuery();
     while (oSet.next()){
     i++;
     s += (  ",\"a"+ i +"\":" + "\"" +oSet.getString(1) + "\"");
     } 
       
     AccessDB.closeConnectionStatic("", oConnection);
     return s; //возвращаем список регионов в виде Json строки
   }
 
 
}
