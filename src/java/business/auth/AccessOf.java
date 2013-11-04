package business.auth;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;


/*
 
 CREATE table AccessOf (   -- Учет в момент входа
     nID INT identity,
     nID_Access INT,
     sAddress VARCHAR(100),   -- IP пользователя
     sDT DATETIME,            -- Дата попытки     // !!!сделать: default getDate() null,
     sRefer VARCHAR(200),     -- Ссылатель?
     sData VARCHAR(1024),     -- Доп Данные
     nAgree INT null,         -- был ли допуск  1 - был допуск, 0 небыло допуска.
 PRIMARY KEY(nID)
 )

alter table AccessOf drop bAgree
alter table AccessOf add nAgree INT null
 alter table AccessOf  modify nID_TheSubjectHuman INT null

 */
public class AccessOf {

    private Logger oLog = Logger.getLogger(getClass());
    private int nID;
    private int nID_Access;
    private String sAddress;
    private String sDT;
    private String sRefer;
    private int nAgree;
    private String sData;

    // Setters
    public AccessOf _nID(int i) {
        nID = i;
        return this;
    }

    public AccessOf _nID_Access(int i) {
        nID_Access = i;
        return this;
    }

    public AccessOf _sAddress(String s) {
        sAddress = s;
        return this;
    }

    public AccessOf _sDT(String s) {
        sDT = s;
        return this;
    }

    public AccessOf _sRefer(String s) {
        sRefer = s;
        return this;
    }

    public AccessOf _nAgree(int i) {
        nAgree = i;
        return this;
    }

    public AccessOf _sData(String s) {
        sData = s;
        return this;
    }

    // Getters
    public int nID() {
        return nID;
    }

    public int nID_Access() {
        return nID_Access;
    }

    public String sAddress() {
        return sAddress;
    }

    public String sDT() {
        return sDT;
    }

    public String sRefer() {
        return sRefer;
    }

    public int nAgree() {
        return nAgree;
    }

    public String sData() {
        return sData;
    }

    /**
     * Сохраняем информацию о пользователе при его попытке Входа
     *
     * @param sEmail
     * @param sIP
     * @throws Exception
     */
    public void saveInfo(String sEmail, String sIP, int nAgree) throws Exception {
        String sCase = "saveInfo";
        Access oAccess = new Access(sEmail);
        int nID = oAccess.nID();   //= oAccess.nGetIdAccess(sEmail); // узнаем ИД предыдущей таблицы по Логину

        Date d = new Date();  // определяем текущую дату.
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String sTime = df.format(d);

        Connection oConnection = null;
        Statement oStatement = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO AccessOf (nID_Access, sAddress, sDT, sRefer, nAgree, sData) "
                    + "VALUES (" + nID + ",'" + sIP + "','" + sTime + "','ссылка откуда...'," + nAgree + ",'доп. инф')", oLog);

        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sEmail= " + sEmail + " sIP= " + sIP + " nAgree= " + nAgree + ") : Ошибка записи данных в базу!", oException);
            throw oException;  // выбрасываем ошибку наверх
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
        }






    }
}
