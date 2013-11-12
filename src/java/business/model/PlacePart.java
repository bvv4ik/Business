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
public class PlacePart {

    private int nID;
    private int nID_PlaceBuild;
    private int nPart;
    private String sPartInfo;
    private Float dX;
    private Float dY;
    private Float dZ;

// Setters
    public PlacePart _nID(int i) {
        nID = i;
        return this;
    }

    public PlacePart _nID_PlaceBuild(int i) {
        nID_PlaceBuild = i;
        return this;
    }

    public PlacePart _nPart(int i) {
        nPart = i;
        return this;
    }

    public PlacePart _sPartInfo(String s) {
        sPartInfo = s;
        return this;
    }

    public PlacePart _dX(Float f) {
        dX = f;
        return this;
    }

    public PlacePart _dY(Float f) {
        dY = f;
        return this;
    }

    public PlacePart _dZ(Float f) {
        dZ = f;
        return this;
    }

    // Getters
    public int nID() {
        return nID;
    }

    public int nID_PlaceBuild() {
        return nID_PlaceBuild;
    }

    public int nPart() {
        return nPart;
    }

    public String sPartInfo() {
        return sPartInfo;
    }

    public Float dX() {
        return dX;
    }

    public Float dY() {
        return dY;
    }

    public Float dZ() {
        return dZ;
    }

    // Определяем nID подъезда по выбранному подъезду     
// public int getID (String sPart) throws Exception { 
//     int i = 0;  
//     int nPart = Integer.parseInt(sPart);
//    
//     Connection oConnection = AccessDB.oConnectionStatic("");    
//     ResultSet oRowset = oConnection.prepareStatement("SELECT nID FROM PlacePart where nPart = "+nPart ).executeQuery();
//     while (oRowset.next()){
//         i = oRowset.getInt(1);
//       //  _nID(oRowset.getInt(1));  // Возвращаем nID по названию Branch
//      //   _nID_PlacePolis(PP.nID());
//      //   _nID_PlaceBranchType(PBT.nID());
//       //  _sBranch(sBranch);
//        }
//     AccessDB.closeConnectionStatic("", oConnection);      
//     return  i;
// }
    // Получаем список всех подъездов улиц по выбранным строению
    public String getAllPart(String nID_PlaceBuild) throws Exception {
        String s = "";  // String utf = ""; 
        int i = 0;

        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oRowset = oConnection.prepareStatement("SELECT nPart FROM PlacePart where nID_PlaceBuild = " + nID_PlaceBuild).executeQuery();
        while (oRowset.next()) {
            i++;
            s += (",\"a" + i + "\":" + "\"" + oRowset.getInt(1) + "\"");
        }
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
        AccessDB.closeConnectionStatic("", oConnection);
        return s; //возвращаем список регионов в виде Json строки
    }
}
