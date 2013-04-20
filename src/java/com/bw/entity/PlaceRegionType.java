
package com.bw.entity;

import com.bw.io.ConnectSybase;
import java.sql.Connection;
import java.sql.ResultSet;


public class PlaceRegionType {
  
    
private int nID;
private String sRegionType; 
private String sRegionTypeShort; 
private int nRegionTypeOrder; 

// Setters
 public PlaceRegionType _nID(int i) { nID = i; return this; }
 public PlaceRegionType _sRegionType(String s) { sRegionType=s; return this;  }
 public PlaceRegionType _sRegionTypeShort(String s) { sRegionTypeShort=s; return this;  }
 public PlaceRegionType _nRegionTypeOrder(int i) { nRegionTypeOrder=i; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public String sRegionType() { return sRegionType; }
 public String sRegionTypeShort() { return sRegionTypeShort; }
 public int nRegionTypeOrder() { return nRegionTypeOrder; }
 
 //Конструктор
 // public PlaceBranchType(String sBranchType) throws Exception {
//    setBranchType(sBranchType) ;
// };
//  public PlaceBranchType() {

 //};
         
 
 // Определяем nID Типа улицы по одному выбранному типу
//public int getID (String sBranchType) throws Exception { 
//     int i = 0;  
//     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
//     ResultSet oSet = oDC.prepareStatement("SELECT nID FROM PlaceBranchType1 where sBranchType = '"+sBranchType+"'").executeQuery();
//     if (oSet.next()){
//         i = oSet.getInt(1);
//        // _nID(oSet.getInt(1));  // Возвращаем nID по названию типа Улицы
//         //_sBranchType(sBranchType);
//        }
//     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);      
//     return  i;
// }
 
// Получаем список всех типов улиц 
public String getAllRegionType() throws Exception   { 
 String s = "";  // String utf = "";  //int i = 0;
  
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT nID, sRegionType FROM PlaceRegionType").executeQuery();
     while (oSet.next()){
     //i++;
      s += (  ",\""+ oSet.getInt(1) +"\":" + "\"" +oSet.getString(2) + "\"");
     } 
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
     return s; //возвращаем список регионов в виде Json строки
   }

 
}


