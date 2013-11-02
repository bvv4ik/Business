/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Sergey
 */
public class NewClassTest {

    public static void main(String[] args) throws Exception {
//    String s = "";  // String utf = ""; 
//    int i = 0;
//     
//     Connection oConnection = AccessDB.oConnectionStatic("");    
//     ResultSet oRowset = oConnection.prepareStatement("select nID, sCountry from PlaceCountry").executeQuery();
//     while (oRowset.next()){
//     i++;
// 
//     
//     s +=         "<option value= \""+ oRowset.getInt(1) + "\"> "+oRowset.getString(2)+ "</option>" ;        
//                     
//     } 
//        //  if((i%2)!=0) если кратно 2
//        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
//     AccessDB.closeConnectionStatic("", oConnection);
// 
//    System.out.println(s);

        //====================================================

        String s = "";  // String utf = ""; 
        int i = 0;

        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oRowset = oConnection.prepareStatement("SELECT nID, sRegionType FROM PlaceRegionType").executeQuery();
        while (oRowset.next()) {
            i++;
            //s += (  ",\"a"+ i +"\":" + "\"" +oRowset.getString(1) + "\"");
            s += (",\"" + oRowset.getInt(1) + "\":" + "\"" + oRowset.getString(2) + "\"");
        }
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
        AccessDB.closeConnectionStatic("", oConnection);

        System.out.println(s);
    }
}
