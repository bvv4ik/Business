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
public class PlaceBranchType {

    private int nID;
    private String sBranchType;

// Setters
    public PlaceBranchType _nID(int i) {
        nID = i;
        return this;
    }

    public PlaceBranchType _sBranchType(String s) {
        sBranchType = s;
        return this;
    }

    // Getters
    public int nID() {
        return nID;
    }

    public String sBranchType() {
        return sBranchType;
    }

    //Конструктор
    // public PlaceBranchType(String sBranchType) throws Exception {
//    setBranchType(sBranchType) ;
// };
//  public PlaceBranchType() { 
    //};
    // Определяем nID Типа улицы по одному выбранному типу
    public int getID(String sBranchType) throws Exception {
        int i = 0;
        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oRowset = oConnection.prepareStatement("SELECT nID FROM PlaceBranchType1 where sBranchType = '" + sBranchType + "'").executeQuery();
        if (oRowset.next()) {
            i = oRowset.getInt(1);
            // _nID(oRowset.getInt(1));  // Возвращаем nID по названию типа Улицы
            //_sBranchType(sBranchType);
        }
        AccessDB.closeConnectionStatic("", oConnection);
        return i;
    }

// Получаем список всех типов улиц 
    public String getAllBranchType() throws Exception {
        String s = "";  // String utf = ""; 
        int i = 0;

        Connection oConnection = AccessDB.oConnectionStatic("");
        ResultSet oRowset = oConnection.prepareStatement("SELECT sBranchType FROM PlaceBranchType1").executeQuery();
        while (oRowset.next()) {
            i++;
            s += (",\"a" + i + "\":" + "\"" + oRowset.getString(1) + "\"");
        }
        //  if((i%2)!=0) если кратно 2
        //   utf = new String( s1.getBytes(), "Cp1251" ); // перекодировка
        AccessDB.closeConnectionStatic("", oConnection);
        return s; //возвращаем список регионов в виде Json строки
    }
}
