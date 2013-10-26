
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
  
     Connection oConnection = AccessDB.oConnectionStatic("");    
     ResultSet oRowset = oConnection.prepareStatement("SELECT nID, sPolisType FROM PlacePolisType").executeQuery();
     while (oRowset.next()){
     i++;
     //s += (  ",\"a"+ i +"\":" + "\"" +oRowset.getString(1) + "\"");
      s += (  ",\""+ oRowset.getInt(1) +"\":" + "\"" +oRowset.getString(2) + "\"");
     } 
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
     AccessDB.closeConnectionStatic("", oConnection);
     return s; //возвращаем список регионов в виде Json строки
   }
 
}
