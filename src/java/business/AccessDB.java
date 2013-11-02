package business;

import com.bw.io.*;
import java.sql.*;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * Доступ к БД
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class AccessDB implements IConnectionBaseByName {// extends ItemAccessBase

    public static int nConnections = 0;
    public static HashMap mConnectionOpened = new HashMap();
    static String sConnectionNameDefault = "UA_DP_PGASA";
    static String sDriverJDBC = Config.sValue("sDriverJDBC");
    private static Logger oLog = Logger.getLogger(AccessDB.class);

    /**
     * считает не закрытые соединения, в т.ч. по методам, в которых они
     * используются
     */
    public static void nConnections(boolean bAdd, String sCaseCaller, String sConnectionNameDefault) {
        String sConnection = sConnectionNameDefault + "|" + sCaseCaller;
        int nConnectionsThis = 0;
        if (mConnectionOpened.containsKey(sConnection)) {
            //mConnection.put(sConnection,nConnectionsThis);
            //}else{
            nConnectionsThis = (Integer) mConnectionOpened.get(sConnection);
        }
        nConnections += (bAdd ? 1 : -1);
        nConnectionsThis += (bAdd ? 1 : -1);
        mConnectionOpened.put(sConnection, nConnectionsThis);
    }

    /*@Override
     public void logging(String sMessage, Exception _, boolean bAlert, boolean bTrace) {
     Log.Do(sMessage, _, bAlert, bTrace, oLog);
     }*/
    @Override
    public Connection oConnection(String sCaseCaller, String sBaseName) {
        return oConnectionStatic(sCaseCaller, sBaseName);
    }

    @Override
    public void closeConnection(String sCaseCaller, String sBaseName, Connection oConnection) {
        closeConnectionStatic(sCaseCaller, sBaseName, oConnection);
    }

    /**
     * Возвращает соединение к базе(из пула) с сервером (умолчательное)
     *
     * @return Connection
     */
    public Connection oConnection(String sCaseCaller) {
        return oConnection(sCaseCaller, sConnectionNameDefault);
    }

    /**
     * Закрывает соединение к базе(в пуле) с сервером (умолчательное)
     *
     * @param oConnection обьект соединения
     */
    public void closeConnection(String sCaseCaller, Connection oConnection) {
        closeConnection(sCaseCaller, sConnectionNameDefault, oConnection);
    }

    /**
     * Возвращает соединение из пула с дефолтным именем
     *
     * @return Connection
     */
    public static Connection oConnectionStatic(String sCaseCaller) {
        return oConnectionStatic(sCaseCaller, sConnectionNameDefault);
    }

    /**
     * Возвращает соединение из пула с соответствующим именем
     *
     * @param sConnectionNameDefault имя пула из которого будет полученно
     * соединение
     *
     * @return Connection
     */
    public static Connection oConnectionStatic(String sCaseCaller, String sConnectionName) {
        String sCase = "oConnectionStatic(sConnectionName)";
        Connection oConnection = null;
        try {
            oLog.debug("[" + sCase + "](sConnectionName=" + sConnectionName + "):");
            nConnections(true, sCaseCaller, sConnectionName);
            oConnection = business.cache.Pool.getInstance(sCaseCaller, Config.sPathDB()).getConnection(sConnectionName);
        } catch (Exception _) {
            oLog.error("[" + sCase + "](sConnectionName=" + sConnectionName + ",Config.sPathDB()=" + Config.sPathDB() + "):" + _.getMessage());
        }
        return oConnection;
    }

    /**
     * Создаёт соединение с сервером БД
     *
     * @param sDriverJDBC jdbc driver classname<br/> если будет получена пустая
     * сторка, то будет использован по умолчанию
     * @param sPathJDBC - a database url of the form jdbc:subprotocol:subname
     * @param sLoginJDBC - the database user on whose behalf the connection is
     * being made
     * @param sPasswordJDBC - the user's password
     * @return Connection
     */
    private static Connection oConnection(String sCaseCaller, String sDriverJDBC, String sPathJDBC, String sLoginJDBC, String sPasswordJDBC) throws Exception {
        String sCase = "oConnection(sCase, jdbc...)";
        try {
            Class.forName(sDriverJDBC.equals("") ? sDriverJDBC : sDriverJDBC);
            //nConnections++;
            //String sConnection=sCaseCaller+"_"+sPathJDBC;
            //mConnection.containsKey(sCaseCaller+"_"+sPathJDBC);
            //countConnections(true, sCaseCaller, sPathJDBC);
            return DriverManager.getConnection(sPathJDBC, sLoginJDBC, sPasswordJDBC);
        } catch (Exception _) {
            oLog.error("[" + sCase + "](sDriverJDBC=" + sDriverJDBC + ",sPathJDBC=" + sPathJDBC
                    + ",sLoginJDBC=" + sLoginJDBC + ",sPasswordJDBC=" + sPasswordJDBC + "):" + _.getMessage());
            throw _;
        }
    }

    /**
     * Закрывает соединение с БД с дефолтным именем
     *
     * @param sConnectionNameDefault имя пула из которого было полученно
     * соединение
     * @param oConnection соединение которое должно быть закрыто
     *
     */
    public static void closeConnectionStatic(String sCaseCaller, Connection oConnection) {
        closeConnectionStatic(sCaseCaller, sConnectionNameDefault, oConnection);
    }

    /**
     * Закрывает соединение с БД с соответствующим именем
     *
     * @param sConnectionNameDefault имя пула из которого было полученно
     * соединение
     * @param oConnection соединение которое должно быть закрыто
     *
     */
    public static void closeConnectionStatic(String sCaseCaller, String sConnectionName, Connection oConnection) {
        String sCase = "closeConnectionStatic(sConnectionName)";
        try {
            nConnections(false, sCaseCaller, sConnectionName);
            close_(sCaseCaller, oConnection);//TODO: close ConnectionCashe Name
        } catch (Exception _) {
            oLog.warn("[" + sCase + "](sConnectionName=" + sConnectionName + "):" + _.getMessage());
        }
    }

    /**
     * Закрывает ровсет БД
     *
     * @param oRowset обьект стэйтмэнта
     */
    public static void close(ResultSet oRowset) {
        String sCase = "close(oRowset)";
        try {
            if (null != oRowset) {
                oRowset.close();
            } else {
                throw new Exception("oRowset is null!");
            }
        } catch (Exception _) {
            oLog.warn("[" + sCase + "]:" + _.getMessage());
        } finally {
            oRowset = null;
        }
    }

    /**
     * Возвращает стейтмент соединения к базе(из пула) с сервером
     *
     * @return Connection
     */
    public static Statement oStatementStatic(Connection oConnection, String sCaseCaller) throws SQLException {
        //oStatement = oConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return oConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //return oConnection(sCaseCaller, "C_J_Control");
    }

    /**
     * Возвращает дефолтный стейтмент соединения к базе(из пула) с сервером
     *
     * @return Connection
     */
    public static Statement oStatementDefaultStatic(Connection oConnection, String sCaseCaller) throws SQLException {
        return oConnection.createStatement();
    }

    /**
     * Закрывает стэйтмэнт БД
     *
     * @param oStatement обьект стэйтмэнта
     */
    public static void close(String sCaseCaller, Statement oStatement) {
        String sCase = "close(oStatement)";
        try {
            if (null != oStatement) {
                oStatement.close();
            } else {
                throw new Exception("oStatement is null!");
            }
        } catch (Exception _) {
            oLog.warn("[" + sCase + "]:" + _.getMessage());
        } finally {
            oStatement = null;
        }
    }

    /**
     * Закрывает соединение с БД
     *
     * @param oConnection обьект коннэкшина
     */
    private static void close(String sCaseCaller, Connection oConnection) {
        String sCase = "close(oConnection)";
        try {
            close_(sCaseCaller, oConnection);
        } catch (Exception _) {
            oLog.warn("[" + sCase + "]:" + _.getMessage());
        }
    }

    private static void close_(String sCaseCaller, Connection oConnection) throws Exception {
        try {
            //nConnections--;
            if (null != oConnection) {
                oConnection.close();
            } else {
                throw new Exception("oConnection is null!");
            }
        } finally {
            oConnection = null;
        }
    }

    /**
     * Начинает транзакцию
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param oLog обьект логера
     * @throws SQLException
     */
    public static void transactBegin(Statement oStatement, String sCaseCaller, Logger oLog) throws SQLException {
        String sCase = "transactBegin";
        oLog.debug("[" + sCase + "][" + sCaseCaller + "]:...");
        try {
            if (null != oStatement) {
                oStatement.executeUpdate("begin tran " + sCaseCaller);
            } else {
                oLog.error("[" + sCase + "][" + sCaseCaller + "]:oStatement is null!");
                throw new SQLException("oStatement is null!");
            }
        } catch (SQLException _) {
            oLog.error("[" + sCase + "][" + sCaseCaller + "]:", _);
            throw new SQLException("Transact-begin fail!");
        }
    }

    /**
     * Коммитит транзакцию
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param oLog обьект логера
     * @throws SQLException
     */
    public static void transactCommit(Statement oStatement, String sCaseCaller, Logger oLog) throws SQLException {
        String sCase = "transactCommit";
        oLog.debug("[" + sCase + "][" + sCaseCaller + "]:");
        try {
            if (null != oStatement) {
                oStatement.executeUpdate("commit tran " + sCaseCaller);
            } else {
                oLog.error("[" + sCase + "][" + sCaseCaller + "]:oStatement is null!");
                throw new SQLException("oStatement is null!");
            }
        } catch (SQLException _) {
            oLog.error("[" + sCase + "][" + sCaseCaller + "]:", _);
            throw new SQLException("Transact-commit fail!");
        }
    }

    /**
     * Откатывает транзакцию
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param oLog обьект логера
     */
    public static void transactRollback(Statement oStatement, String sCaseCaller, Logger oLog) {
        String sCase = "transactRollback";
        oLog.warn("[" + sCase + "][" + sCaseCaller + "]:");
        try {
            if (null != oStatement) {
                oStatement.executeUpdate("rollback tran " + sCaseCaller);
            } else {
                oLog.error("[" + sCase + "][" + sCaseCaller + "]:oStatement is null!");
            }
        } catch (SQLException _) {
            oLog.error("[" + sCase + "][" + sCaseCaller + "]!", _);
        }
    }

    /**
     * Получить ровсет по запросу SQL
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param sSQL строка запроса
     * @param oLog обьект логера
     * @return обьект ровсета
     * @throws SQLException
     */
    public static ResultSet oRowsetQuery(Statement oStatement, String sCaseCaller, String sSQL, Logger oLog) throws SQLException {
        return oRowsetQuery(oStatement, sCaseCaller, sSQL, oLog, false);
    }

    /**
     * Получить ровсет по запросу SQL, с опциональным дебаг-логированием
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param sSQL строка запроса
     * @param oLog обьект логера
     * @param bNoLog true = отключить дебаг-логирование (кроме ошибки)
     * @return обьект ровсета
     * @throws SQLException
     */
    public static ResultSet oRowsetQuery(Statement oStatement, String sCaseCaller, String sSQL, Logger oLog, boolean bNoLog) throws SQLException {
        String sCase = "oRowsetQuery";
        if (sSQL == null) {
            return null;
        }
        try {
            if (null != oStatement) {
                ResultSet oRowsetQuery;
                if (!bNoLog) {
                    oLog.debug("[" + sCase + "][" + sCaseCaller + "]:...");
                }
                oRowsetQuery = oStatement.executeQuery(sSQL);
                if (!bNoLog) {
                    oLog.debug("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):Ok!");
                }
                return oRowsetQuery;
            } else {
                oLog.error("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):oStatement is null!");
                throw new SQLException("oStatement is null!");
            }
        } catch (SQLException _) {
            oLog.error("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):", _);
            throw new SQLException("SQL-query fail!");
        }
    }

    /**
     * Получить число по выполнению апдейта SQL
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param sSQL строка запроса
     * @param oLog обьект логера
     * @return число строк
     * @throws SQLException
     */
    public static int nRowsetUpdate(Statement oStatement, String sCaseCaller, String sSQL, Logger oLog) throws SQLException {
        return nRowsetUpdate(oStatement, sCaseCaller, sSQL, oLog, false);
    }

    /**
     * Получить число по выполнению апдейта SQL, с опциональным
     * дебаг-логированием
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param sSQL строка запроса
     * @param oLog обьект логера
     * @param bNoLog true = отключить дебаг-логирование (кроме ошибки)
     * @return число строк
     * @throws SQLException
     */
    public static int nRowsetUpdate(Statement oStatement, String sCaseCaller, String sSQL, Logger oLog, boolean bNoLog) throws SQLException {
        String sCase = "nRowsetUpdate";
        if (sSQL == null) {
            return 0;
        }
        try {
            if (null != oStatement) {
                int nRowsetUpdate;
                if (!bNoLog) {
                    oLog.debug("[" + sCase + "][" + sCaseCaller + "]:...");
                }
                nRowsetUpdate = oStatement.executeUpdate(sSQL);
                if (!bNoLog) {
                    oLog.debug("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):Ok!");
                }
                return nRowsetUpdate;
            } else {
                oLog.error("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):oStatement is null!");
                throw new SQLException("oStatement is null!");
            }
        } catch (SQLException _) {
            oLog.error("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):", _);
            throw new SQLException("SQL-update fail!");
        }
    }

    /**
     * Получить число по выполнению экзекьюта SQL
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param sSQL строка запроса
     * @param oLog обьект логера
     * @return true, при успехе
     * @throws SQLException
     */
    public static boolean bRowsetExecute(Statement oStatement, String sCaseCaller, String sSQL, Logger oLog) throws SQLException {
        return bRowsetExecute(oStatement, sCaseCaller, sSQL, oLog, false);
    }

    /**
     * Получить число по выполнению экзекьюта SQL, с опциональным
     * дебаг-логированием
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param sSQL строка запроса
     * @param oLog обьект логера
     * @param bNoLog true = отключить дебаг-логирование (кроме ошибки)
     * @return true, при успехе
     * @throws SQLException
     */
    public static boolean bRowsetExecute(Statement oStatement, String sCaseCaller, String sSQL, Logger oLog, boolean bNoLog) throws SQLException {
        String sCase = "bRowsetExecute";
        if (sSQL == null) {
            return false;
        }
        try {
            if (null != oStatement) {
                boolean bRowsetExecute;
                if (!bNoLog) {
                    oLog.debug("[" + sCase + "][" + sCaseCaller + "]:...!");
                }
                bRowsetExecute = oStatement.execute(sSQL);
                if (!bNoLog) {
                    oLog.debug("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):Ok!");
                }
                return bRowsetExecute;
            } else {
                oLog.error("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):oStatement is null!");
                throw new SQLException("oStatement is null!");
            }
        } catch (SQLException _) {
            oLog.error("[" + sCase + "][" + sCaseCaller + "](sSQL=" + sSQL + "):", _);
            throw new SQLException("SQL-execute fail!");
        }
    }

    /**
     * Получить ID последнего созданной с identity строки
     *
     * @param oStatement обьект стэйтмэнта
     * @param sCaseCaller кейс/метод вызывающего
     * @param oLog обьект логера
     * @return ИД
     * @throws SQLException
     */
    public static int nRowsetID(Statement oStatement, String sCaseCaller, Logger oLog) throws SQLException {
        try {
            ResultSet oRowset = oStatement.executeQuery("SELECT @@identity");
            int nID = oRowset.next() ? oRowset.getInt(1) : 0;
            return nID;
        } catch (SQLException _) {
            oLog.error("[" + sCaseCaller + "]:", _);
            throw new SQLException("SQL-query of identity fail!");
        }
    }
}