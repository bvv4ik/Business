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
public class PlaceBuild {

    private int nID;
    private int nID_PlaceBranch;
    private int nID_PlaceBuildType;
    private int nID_PlaceArea;
    private int nLevels;
    private int nParts;
    private String sBuild;
    private String sBuildBlock;
    private int nCellAt;
    private int nCellTo;

// Setters
    public PlaceBuild _nID(int i) {
        nID = i;
        return this;
    }

    public PlaceBuild _nID_PlaceBranch(int i) {
        nID_PlaceBranch = i;
        return this;
    }

    public PlaceBuild _nID_PlaceBuildType(int i) {
        nID_PlaceBuildType = i;
        return this;
    }

    public PlaceBuild _nID_PlaceArea(int i) {
        nID_PlaceArea = i;
        return this;
    }

    public PlaceBuild _nLevels(int i) {
        nLevels = i;
        return this;
    }

    public PlaceBuild _nParts(int i) {
        nParts = i;
        return this;
    }

    public PlaceBuild _sBuild(String s) {
        sBuild = s;
        return this;
    }

    public PlaceBuild _sBuildBlock(String s) {
        sBuildBlock = s;
        return this;
    }

    public PlaceBuild _nCellAt(int i) {
        nCellAt = i;
        return this;
    }

    public PlaceBuild _nCellTo(int i) {
        nCellTo = i;
        return this;
    }

    // Getters
    public int nID() {
        return nID;
    }

    public int nID_PlaceBranch() {
        return nID_PlaceBranch;
    }

    public int nID_PlaceBuildType() {
        return nID_PlaceBuildType;
    }

    public int nID_PlaceArea() {
        return nID_PlaceArea;
    }

    public int nLevels() {
        return nLevels;
    }

    public int nParts() {
        return nParts;
    }

    public String sBuild() {
        return sBuild;
    }

    public String sBuildBlock() {
        return sBuildBlock;
    }

    public int nCellAt() {
        return nCellAt;
    }

    public int nCellTo() {
        return nCellTo;
    }

    // Получаем ИД по названию выбранному строению
// public int getID (String sBuild) throws Exception { 
//     int i = 0;  
//    
//     Connection oConnection = AccessDB.oConnectionStatic("");    
//     ResultSet oRowset = oConnection.prepareStatement("SELECT nID FROM PlaceBuild1 where sBuild = '"+sBuild+"'").executeQuery();
//     if (oRowset.next()){
//         i = oRowset.getInt(1);
//        }
//     AccessDB.closeConnectionStatic("", oConnection);      
//     return  i;
// }
    // Получаем список всех зданий по выбранной улице, типу здания и району.
    public String getAllBuild(String nID_Branch, String nID_BuildType, String nID_Area) throws Exception {
        String s = "";
        int i = 0;

        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oRowset = oConnection.prepareStatement("SELECT sBuild FROM PlaceBuild where nID_PlaceBranch = " + nID_Branch + " AND nID_PlaceBuildType = " + nID_BuildType + " AND nID_PlaceArea = " + nID_Area).executeQuery();
        while (oRowset.next()) {
            i++;
            s += (",\"a" + i + "\":" + "\"" + oRowset.getString(1) + "\"");
        }
        AccessDB.closeConnectionStatic("", oConnection);
        return s;
    }

// Получаем список всех зданий по выбранной улице и типу здания
    public String getAllBuild(int nID_PlaceBranch, int nID_PlaceBuildType) throws Exception {
        String s = "";  // String utf = ""; 
        int i = 0;
        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oRowset = oConnection.prepareStatement("SELECT sBuild FROM PlaceBuild1 where nID_PlaceBranch = " + nID_PlaceBranch + " where nID_PlaceBuildType =" + nID_PlaceBuildType).executeQuery();
        while (oRowset.next()) {
            i++;
            s += (",\"a" + i + "\":" + "\"" + oRowset.getString(1) + "\"");
        }
        AccessDB.closeConnectionStatic("", oConnection);
        return s;
    }

    public void AddBuild(String nID_Branch, String nID_BuildType, String nID_Area, int nLevels, int nParts, String sBuild, String sBuildBlock, int nCellAt, int nCellTo) throws Exception {
        String s = "";
        // int i = 0;
        Connection oConnection = AccessDB.oConnectionStatic("");
        oConnection.prepareStatement("INSERT INTO PlaceBuild1 (nID_PlaceBranch, nID_PlaceBuildType, nID_PlaceArea, nLevels, nParts, sBuild, sBuildBlock, nCellAt, nCellTo) "
                + "VALUES (" + nID_Branch + ", " + nID_BuildType + ", " + nID_Area + ", " + nLevels + ", " + nParts + ", '" + sBuild + "','" + sBuildBlock + "', " + nCellAt + ", " + nCellTo + " )").executeUpdate();

        AccessDB.closeConnectionStatic("", oConnection);

    }
}
