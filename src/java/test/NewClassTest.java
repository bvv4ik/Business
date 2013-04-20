/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.bw.io.ConnectSybase;
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
//     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
//     ResultSet oSet = oDC.prepareStatement("select nID, sCountry from PlaceCountry").executeQuery();
//     while (oSet.next()){
//     i++;
// 
//     
//     s +=         "<option value= \""+ oSet.getInt(1) + "\"> "+oSet.getString(2)+ "</option>" ;        
//                     
//     } 
//        //  if((i%2)!=0) если кратно 2
//        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
//     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
// 
//    System.out.println(s);
    
    //====================================================
    
     String s = "";  // String utf = ""; 
     int i = 0;
  
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT nID, sRegionType FROM PlaceRegionType").executeQuery();
     while (oSet.next()){
     i++;
     //s += (  ",\"a"+ i +"\":" + "\"" +oSet.getString(1) + "\"");
      s += (  ",\""+ oSet.getInt(1) +"\":" + "\"" +oSet.getString(2) + "\"");
     } 
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
   
     System.out.println(s);
      }    
}
