/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

import com.bw.io.ConnectSybase;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Ser
 */
public class PlaceCell {
  
private int nID;
private int nID_PlacePart;
private int nID_PlaceCellType;
private int nLevel;
private int nCell;
private String sCell; 

// Setters
 public PlaceCell _nID(int i) { nID = i; return this; }
 public PlaceCell _nID_PlacePart(int i) { nID_PlacePart=i; return this; }
 public PlaceCell _nID_PlaceCellType(int i) { nID_PlaceCellType=i; return this; }
 public PlaceCell _nLevel(int i) { nLevel=i; return this; }
 public PlaceCell _nCell(int i) { nCell=i; return this; }
 public PlaceCell _sCell(String s) { sCell=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public int nID_PlacePart() { return nID_PlacePart; }
 public int nID_PlaceCellType() { return nID_PlaceCellType; }
 public int nLevel() { return nLevel; }
 public int nCell() { return nCell; }
 public String sCell() { return sCell; }
 
  // Определяем nID квартиры по выбранной квартире
// public int getID (String sPart) throws Exception { 
//     int i = 0;  
//     
//     int nPart = Integer.parseInt(sPart);
//     
//     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
//     ResultSet oSet = oDC.prepareStatement("SELECT nID FROM PlacePart where nPart = "+nPart).executeQuery();
//     if (oSet.next()){
//         i = oSet.getInt(1);
//         }
//     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);      
//     return  i;
// }
    
 public String getAllCell(String   nID_PlacePart) throws Exception   { 
 String s = "";  
 int i = 0;
  
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT nCell FROM PlaceCell where nID_PlacePart = "+nID_PlacePart).executeQuery();
     while (oSet.next()){
     i++;
     s += (  ",\"a"+ i +"\":" + "\"" +oSet.getInt(1) + "\"");
     } 
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
     return s; //возвращаем список регионов в виде Json строки
   }
 
 
}