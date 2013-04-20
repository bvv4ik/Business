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
public class PlaceCellType {
 

private int nID;
private String sCellType; 

// Setters
 public PlaceCellType _nID(int i) { nID = i; return this; }
 public PlaceCellType _sCellType(String s) { sCellType=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public String sCellType() { return sCellType; }
    
 // Определяем nID Типа части строения по одному выбранному типу
public int getID (String sCellType) throws Exception { 
     int i = 0;  
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT nID FROM PlaceCellType where sCellType = '"+sCellType+"'").executeQuery();
     if (oSet.next()){
         i = oSet.getInt(1);
        // _nID(oSet.getInt(1));  // Возвращаем nID по названию типа дома
         //_sBranchType(sBranchType);
        }
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);      
     return  i;
 }
 
// Получаем список всех типов части строения
public String getAllCellType() throws Exception   { 
 String s = "";  // String utf = ""; 
 int i = 0;
  
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
     ResultSet oSet = oDC.prepareStatement("SELECT sCellType FROM PlaceCellType").executeQuery();
     while (oSet.next()){
     i++;
     s += (  ",\"a"+ i +"\":" + "\"" +oSet.getString(1) + "\"");
     } 
       
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
     return s; //возвращаем список регионов в виде Json строки
   }
 
 
 
}
