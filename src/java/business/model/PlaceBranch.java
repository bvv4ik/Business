/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;


public class PlaceBranch {
    
      
private int nID;
private int nID_PlacePolis;
private int nID_PlaceBranchType;
private String sBranch; 

// Setters
 public PlaceBranch _nID(int i) { nID = i; return this; }
 public PlaceBranch _nID_PlacePolis(int i) { nID_PlacePolis = i; return this; }
 public PlaceBranch _nID_PlaceBranchType(int i) { nID_PlaceBranchType = i; return this; }
 public PlaceBranch _sBranch(String s) { sBranch=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public int nID_PlacePolis() { return nID_PlacePolis; }
 public int nID_PlaceBranchType() { return nID_PlaceBranchType; }
 public String sBranch() { return sBranch; }
 
  //Конструкторы
 //public PlaceBranch(String sBranch) throws Exception {
 //   setBranch(sBranch);
 //};
 //public PlaceBranch(){
 //};
 
 
 // Определяем nID улицы по выбранному названию     
// public int getID (String sBranch) throws Exception { 
//     int i = 0;  
//    
//     Connection oConnection = AccessDB.oConnectionStatic("");    
//     ResultSet oSet = oConnection.prepareStatement("SELECT nID FROM PlaceBranch1 where sBranch = '"+sBranch+"'").executeQuery();
//     if (oSet.next()){
//         i = oSet.getInt(1);
//       //  _nID(oSet.getInt(1));  // Возвращаем nID по названию Branch
//      //   _nID_PlacePolis(PP.nID());
//      //   _nID_PlaceBranchType(PBT.nID());
//       //  _sBranch(sBranch);
//        }
//     AccessDB.closeConnectionStatic("", oConnection);      
//     return  i;
// }
 
 // Получаем список всех улиц по выбранным городу и типу
public String getAllBranch(String nID_Polis, String nID_BranchType) throws Exception {
        String s = "";  // String utf = ""; 
        int i = 0;

        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oSet = oConnection.prepareStatement("SELECT sBranch FROM PlaceBranch  where nID_PlacePolis = " + nID_Polis + " AND nID_PlaceBranchType = " + nID_BranchType).executeQuery();
        while (oSet.next()) {
            i++;
            // s += (  ",\"a"+ i +"\":" + "\"" +oSet.getString(1) + "\"");
            s += (",\"" + oSet.getInt(1) + "\":" + "\"" + oSet.getString(2) + "\"");
        }
        AccessDB.closeConnectionStatic("", oConnection);
        return s;          //возвращаем список регионов в виде Json строки
    }
 

 
}
