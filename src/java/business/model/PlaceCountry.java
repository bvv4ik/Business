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
public class PlaceCountry {

    private int nID;
    private int nID_TheSubject;
    private String sCountry;

// Setters
    public PlaceCountry _nID(int i) {
        nID = i;
        return this;
    }

    public PlaceCountry _nID_TheSubject(int i) {
        nID_TheSubject = i;
        return this;
    }

    public PlaceCountry _sCountry(String s) {
        sCountry = s;
        return this;
    }

    // Getters
    public int nID() {
        return nID;
    }

    public int nID_TheSubject() {
        return nID_TheSubject;
    }

    public String sCountry() {
        return sCountry;
    }

//Конструкторы
//  public PlaceCountry(String sCountry) throws Exception {
//    setCountry(sCountry) ;  
//    };
    // public PlaceCountry() {  };
    public String getAllCountry() throws Exception {
        String s = "";  // String utf = "";  //int i = 0;

        //PlaceCountry PC = new PlaceCountry(sCountry); // получаем ID страны

        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oRowset = oConnection.prepareStatement("select nID, sCountry from PlaceCountry").executeQuery();
        while (oRowset.next()) {
            //   i++;
            // s += (  ",\"a"+ i +"\":" + "\"" +oRowset.getString(1) + "\""); 
            s += (",\"" + oRowset.getInt(1) + "\":" + "\"" + oRowset.getString(2) + "\"");
        }
        //   if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
        AccessDB.closeConnectionStatic("", oConnection);
        return s; //возвращаем список регионов в виде Json строки
    }

// Определяем nID Страны по одному выбранному названию Страны        
//public int getID (String sCountry) throws Exception { 
//     int i = 0;  
//     Connection oConnection = AccessDB.oConnectionStatic("");    
//    
//     ResultSet oRowset = oConnection.prepareStatement("SELECT nID FROM PlaceCountry where sCountry = '"+sCountry +"'").executeQuery();
//     while (oRowset.next()){
//         i = oRowset.getInt(1);  // Возвращаем nID по названию страны
//         //_nID(oRowset.getInt(1));  // Возвращаем nID по названию страны
//         //_sCountry(sCountry);
//        }
//     AccessDB.closeConnectionStatic("", oConnection);      //
//     return  i;
// }
    public void addCountry(String sCountry) throws Exception {

        Connection oConnection = AccessDB.oConnectionStatic("");
        oConnection.prepareStatement("INSERT INTO PlaceCountry (nID_TheSubject, sCountry) VALUES (2, '" + sCountry + "')").executeUpdate();
        AccessDB.closeConnectionStatic("", oConnection);
    }
;

/*
 public void setPlaceCountry (String sCountry) throws Exception { 
          
 Connection oConnection = AccessDB.oConnectionStatic("");    
    
 ResultSet oRowset = oConnection.prepareStatement("SELECT nID FROM PlaceCountry1 where sCountry = '"+sCountry +"'").executeQuery();
 while (oRowset.next()){
 _nID(oRowset.getInt(1)); // Устанавливаем nID по названию страны
 _sCountry(sCountry);
       
 }
 //_sCountry("Украина"); //Country;  // временнно не закончено
 AccessDB.closeConnectionStatic("", oConnection);
       
 }
 */
}
