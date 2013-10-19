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
public class PlaceRegion {
   
    
 private int nID;
 private int nID_PlaceCountry;
 private String sRegion; 

// Setters
 public PlaceRegion _nID(int i) { nID = i; return this; }
 public PlaceRegion _nID_PlaceCountry(int i) { nID_PlaceCountry = i; return this; }
 public PlaceRegion _sRegion(String s) { sRegion=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public int nID_PlaceCountry() { return nID_PlaceCountry; }
 public String sRegion() { return sRegion; }
 
 //Конструкторы
// public   PlaceRegion(String sCountry) throws Exception {
//    setRegion(sCountry) ;
 //   };
// public   PlaceRegion()  { };

 
 // Определяем nID Региона по одному выбранному названию региона    
// public int getID (String sRegion) throws Exception   { 
//     int i = 0;
//     Connection oDC = AccessDB.oConnectionStatic("");    
//     ResultSet oSet = oDC.prepareStatement("SELECT * FROM PlaceRegion where sRegion = '"+sRegion +"'").executeQuery();
//     if (oSet.next()){
//        i = oSet.getInt(1);
//        _nID(oSet.getInt(1)); // устанавливаем nID по названию региона
//       // _nID_PlaceCountry(oSet.getInt(2)); //i = oSet.getInt(1);
//       // _sRegion(sRegion);
//        }
//     AccessDB.closeConnectionStatic("", oDC); //   
//     return i;
// }
 
 // Получаем список всех регионов по одной выбранной стране
 public String getAllRegions (String nID_Country, String nID_RegionType) throws Exception   { 
 String s = "";  // String utf = "";  //int i = 0;
  
     
     Connection oDC = AccessDB.oConnectionStatic("");    
     ResultSet oSet = oDC.prepareStatement("SELECT nID, sRegion FROM PlaceRegion where nID_PlaceCountry = "+nID_Country + "AND nID_PlaceRegionType = "+nID_RegionType).executeQuery();
     while (oSet.next()){
     //i++;
      s += (  ",\""+ oSet.getInt(1) +"\":" + "\"" +oSet.getString(2) + "\"");
     } 
     AccessDB.closeConnectionStatic("", oDC);
     return s;    //возвращаем список регионов в виде Json строки
   }
 
 
 
 public void setRegion (int nID_Country, String sRegion) throws Exception { 
   
    Connection oDC = AccessDB.oConnectionStatic("");    
    oDC.prepareStatement("INSERT INTO PlaceRegion1 (nID_PlaceCountry, sRegion) VALUES (1, '"+sRegion +"')").executeUpdate();
    AccessDB.closeConnectionStatic("", oDC);    
};
 
}
