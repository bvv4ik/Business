package business.cache;

import business.AccessDB;
import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Кэширование пулла коннекшинов к базе
 * 
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class Pool {

    static Logger oLogStatic = Logger.getLogger(Pool.class);
    private static Pool oPool = null;
    private Map mDataSource = new HashMap();

    synchronized public static Pool getInstance(String sCaseCaller, String sPath) {
        return null != oPool ? oPool : (oPool = new Pool(sCaseCaller, sPath));
    }

    private Pool(String sCaseCaller, String sPath) {
        String sCase = "Pool";
        mDataSource = new HashMap();
        try {
            File[] aCache = new File(sPath).listFiles();
            for (int n = 0; n < aCache.length; n++) {
                Properties oProperty = new Properties();
                oProperty.load(new FileInputStream(aCache[n]));
                mDataSource.put(aCache[n].getName(), org.apache.commons.dbcp.BasicDataSourceFactory.createDataSource(oProperty));
            }
            oLogStatic.info("[" + sCase + "](sPath=" + sPath + ", mDataSource.size()=" + mDataSource.size() + "):Ok!");
        } catch (Exception oException) {
            oLogStatic.error("[" + sCase + "](sPath=" + sPath + ", mDataSource.size()=" + mDataSource.size() + "):", oException);
            throw new RuntimeException(oException);
        }
    }

    synchronized public static void reset() {
        oPool = null;
    }

    public Connection getConnection(String sName) throws Exception {//Exception
        String sCase = "getConnection";
        //oLog.debug("[" + sCase + "]:sName=" + sName + ",mDataSource.size()=" + mDataSource.size() + ",AccessDB.nConnections="+AccessDB.nConnections);
        oLogStatic.debug("[" + sCase + "]:sName=" + sName + ",mDataSource.size()=" + mDataSource.size() + ",nConnections="+AccessDB.nConnections);
        oLogStatic.debug("[" + sCase + "]:mConnection=" + AccessDB.mConnectionOpened);
        try {
            Connection oConnection=((org.apache.commons.dbcp.BasicDataSource) mDataSource.get(sName)).getConnection();
            return oConnection;
        } catch (Exception oException) {
            oLogStatic.error("[" + sCase + "](sName=" + sName + ",mDataSource.size()=" + mDataSource.size()
                    + ",mConnection.size()=" + AccessDB.mConnectionOpened.size() + ",nConnections="+AccessDB.nConnections+"):", oException);
            oLogStatic.info("[" + sCase + "](mDataSource=" + mDataSource + "):");
            oLogStatic.info("[" + sCase + "](AccessDB.mConnection=" + AccessDB.mConnectionOpened + "):");
            throw oException;
        }
    }
}
