package business.cache;

import business.AccessDB;
import business.Config;
import com.bw.io._;
import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Кэширование таблиц
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class Table extends Object implements Serializable {

    private static Logger oLog = Logger.getLogger(Table.class);
    private static Map mTable = new HashMap();

    public Table() {
    }

    public static synchronized boolean initialize() throws Exception {
        String sCase = "init";
        String sFileName = "tables.cache";
        oLog.info("[" + sCase + "](sFileName=" + sFileName + "): Loading...");
        try {
            mTable.clear();
            BufferedReader oReader = new BufferedReader(new FileReader(Config.sPathConfig() + sFileName));
            for (String sRow; null != (sRow = oReader.readLine());) {
                Map mTableThis = new LinkedHashMap();
                String[] asRow = sRow.split(";");
                String sTable = asRow[0];
                String sConnectionName = asRow[1];
                String sSQL = asRow[2];
                //for(String s:asRow){
                oLog.info("[" + sCase + "](sTable=" + sTable + "): Loaded!");
                Connection oConnection = null;
                Statement oStatement = null;
                try {
                    oConnection = AccessDB.oConnectionStatic(sCase, sConnectionName);
                    oStatement = AccessDB.oStatementDefaultStatic(oConnection, sCase);
                    ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, sSQL, oLog, true);
                    ResultSetMetaData oRowsetMD = oRowset.getMetaData();
                    if (oRowset.next()) {
                        Map mValue = new HashMap();
                        for (int i = 1; i <= oRowsetMD.getColumnCount(); i++) {
                            mValue.put(oRowsetMD.getColumnLabel(i), oRowset.getObject(i));
                        }
                        mTableThis.put(Integer.toString(oRowset.getRow()), mValue);
                    }
                    mTable.put(sTable, mTableThis);
                } catch (Throwable oException) {
                    oLog.error("[" + sCase + "](sTable=" + sTable + ",sFileName=" + sFileName + "):", oException);
                } finally {
                    AccessDB.close(sCase, oStatement);
                    AccessDB.closeConnectionStatic(sCase, sConnectionName, oConnection);
                }
            }
            oLog.info("[" + sCase + "](sFileName=" + sFileName + "): Saved!");
        } catch (Throwable oException) {
            if (null != mTable) {
                mTable.clear();
            }
            oLog.error("[" + sCase + "](sFileName=" + sFileName + "):", oException);
            return false;
        }
        return true;
    }

    public static synchronized HashMap mTable() throws Exception {
        if (mTable.isEmpty()) {
            initialize();
        }
        return (HashMap) mTable;
    }

    public static synchronized HashMap mRow(String sTableName) throws Exception {
        Object oTable = mTable().get(sTableName);
        return oTable == null ? new HashMap() : (HashMap) oTable;
    }

    public static synchronized HashMap mRow(String sTableName, String sFieldName, String sFieldValue) throws Exception {
        String sCase = "mRow";
        //oLog.debug("[" + sCase + "](sTableName=" + sTableName + ",sFieldName=" + sFieldName + ",sFieldValue=" + sFieldValue + "): Loading...");
        HashMap mTableThis = new HashMap((Map) mRow(sTableName));
        //oLog.debug("[" + sCase + "](mTable=" + mTable + "): Loading(2)...");
        HashMap mTableNew = new HashMap();
        Iterator iterator = mTableThis.keySet().iterator();
        Object oKey;
        int nKeyReturn = 1;
        while (iterator.hasNext()) {
            oKey = iterator.next();
            if (((Map) mTableThis.get(oKey)).get(sFieldName).toString().trim().equalsIgnoreCase(sFieldValue.trim())) {
                mTableNew.put(Integer.toString(nKeyReturn++), mTableThis.get(oKey));
            }
        }
        return mTableNew;
    }

    /**
     * Получить карту со свойствами(значениями полей строки таблицы) - по
     * названию и значению поля из таблицы.<br> Если не будет найдено в кэше, то
     * будет перечитано из базы.
     *
     * @param sTableName - название таблицы
     * @param sFieldName - название поля
     * @param sFieldValue - значение поля
     * @param oStatement
     * @param oLog
     * @return карта
     * @throws Exception
     */
    public static synchronized HashMap mRowValue(String sTableName, String sFieldName, String sFieldValue, Statement oStatement, Logger oLog) throws Exception {
        String sCase = "mRowValue";
        HashMap mValue = (HashMap) mRow(sTableName, sFieldName, sFieldValue).get("1");
        if (mValue != null) {
            return mValue;
        } else {
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT * FROM " + sTableName
                    + " WHERE " + sFieldName + " = " + (_.bNumber(sFieldValue) ? sFieldValue : "'" + sFieldValue + "'"), oLog, false);
            if (oRowset.next()) {
                ResultSetMetaData oRowsetMD = oRowset.getMetaData();
                mValue = new HashMap();
                for (int i = 1; i <= oRowsetMD.getColumnCount(); i++) {
                    mValue.put(oRowsetMD.getColumnName(i), oRowset.getObject(i));
                }
                return mValue;
            } else {
                throw new Exception("[" + sCase + "](sTableName=" + sTableName + ",sFieldName=" + sFieldName + ",sFieldValue=" + sFieldValue + "):Element not found!");
            }
        }
    }
}
