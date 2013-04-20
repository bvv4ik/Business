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
public class PlaceArea {
  
  
private int nID;
private int nID_PlacePolis;
private String sArea; 

// Setters
 public PlaceArea _nID(int i) { nID = i; return this; }
 public PlaceArea _nID_PlacePolis(int i) { nID_PlacePolis=i; return this; }
 public PlaceArea _sArea(String s) { sArea=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public int nID_PlacePolis() { return nID_PlacePolis; }
 public String sArea() { return sArea; }
    
 //Конструкторы
//public   PlaceArea(String sArea) throws Exception {
//    setArea(sArea) ;
// };
//public   PlaceArea(){  };
 
 
  // Определяем nID Района по одному выбранному названию Района    
  public int getID (String sArea) throws Exception   { 
  int i = 0;
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT * FROM PlaceArea1 where sArea = '"+sArea +"'").executeQuery();
     if (oSet.next()){
         i = oSet.getInt(1);
         }
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
     return i;
  }
 
  // получаем список всех регинонов по названию города
 public String getAllAreas (String   nID_PlacePolis) throws Exception   { 
 String s = "";//int i = 0;
 
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT sArea FROM PlaceArea where nID_PlacePolis ="+nID_PlacePolis).executeQuery();
        while (oSet.next()){
        //i++;
        s += (  ",\""+ oSet.getInt(1) +"\":" + "\"" +oSet.getString(2) + "\"");
        } 
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
    return s;
 }
 
 
}
