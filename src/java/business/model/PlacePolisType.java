
package business.model;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;


public class PlacePolisType {
    
private int nID;
private String sPolisType; 
private int nPlacePolisTypeOrder; 

// Setters
 public PlacePolisType _nID(int i) { nID = i; return this; }
 public PlacePolisType _sPolisType(String s) { sPolisType=s; return this;  }
 public PlacePolisType _nPlacePolisTypeOrder(int i) { nPlacePolisTypeOrder=i; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public String sPolisType() { return sPolisType; }
 public int nPlacePolisTypeOrder() { return nPlacePolisTypeOrder; }
 
 
 // Получаем список всех типов улиц 
public String getAllPolisType() throws Exception   { 
 String s = "";  // String utf = ""; 
 int i = 0;
  
     Connection oDC = AccessDB.oConnectionStatic("");    
     ResultSet oSet = oDC.prepareStatement("SELECT nID, sPolisType FROM PlacePolisType").executeQuery();
     while (oSet.next()){
     i++;
     //s += (  ",\"a"+ i +"\":" + "\"" +oSet.getString(1) + "\"");
      s += (  ",\""+ oSet.getInt(1) +"\":" + "\"" +oSet.getString(2) + "\"");
     } 
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
     AccessDB.closeConnectionStatic("", oDC);
     return s; //возвращаем список регионов в виде Json строки
   }
 
}
