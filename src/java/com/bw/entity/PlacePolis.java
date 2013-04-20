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
public class PlacePolis {
    
  
  private int nID;
  private int nID_PlaceRegion;
  private String sPolis; 

// Setters
 public PlacePolis _nID(int i) { nID = i; return this; }
 public PlacePolis _nID_PlaceRegion(int i) { nID_PlaceRegion = i; return this; }
 public PlacePolis _sPolis(String s) { sPolis=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public int nID_PlaceRegion() { return nID_PlaceRegion; }
 public String sPolis() { return sPolis; }
 
 
 //Конструкторы
 //public PlacePolis(String sPolis) throws Exception {
 //   setPolis(sPolis) ;
 //};
 //public PlacePolis() { };
 
 
  // Определяем nID Города по выбранному названию     
//  public int getID (String sPolis) throws Exception   { 
//   int i = 0;
//     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
//     ResultSet oSet = oDC.prepareStatement("SELECT * FROM PlacePolis1 where sPolis = '"+sPolis +"'").executeQuery();
//     if (oSet.next()){
//        i = oSet.getInt(1);
//        }
//     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
//     return i;
//  }
  
  
 // Получаем список всех городов по одному выбранному региону
 public String getAllPolis (String nID_Region, String nID_PolisType) throws Exception   { 
 String s = ""; int i = 0;
 
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT nID, sPolis FROM PlacePolis where nID_PlaceRegion = "+nID_Region + " AND nID_PlacePolisType = " + nID_PolisType).executeQuery();
        while (oSet.next()& i<100){
        i++;
      // s += (  ",\"a"+ i +"\":" + "\"" +oSet.getString(1) + "\"");
        s += (  ",\""+ oSet.getInt(1) +"\":" + "\"" +oSet.getString(2) + "\"");
        if (i<100) break; // не загружаем больше 100 записей
        }
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
    return s;
 }
 
}
