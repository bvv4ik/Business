package business.auth;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.apache.log4j.Logger;

/*
 
 
 CREATE table AccessAuth (       --  Cookies 
     nID INT identity,
     nID_Access INT,
     sAuth VARCHAR(100),
     sDateMake VARCHAR(100),
     PRIMARY KEY(nID)
 )
 
 drop table AccessAuth
 */


/**
 * Работа с Cookie
 * @author Sergey
 */
public class AccessAuth {

     private Logger oLog = Logger.getLogger(getClass());
     // Список в котором храним данные о попытках входа пользователей
     public static HashMap<String, String> map = new HashMap<String, String>();   //public static ArrayList<String[]> aUserCountTry = new ArrayList<String[]>();   //public static ArrayList<String> aUserCountTry = new ArrayList<String>();
     
    private int nID;
    private int nID_Access;
    private String sAuth;  // Строка с Кукой
    private String sDateMake;  // Дата создания

// Setters
    public AccessAuth _nID(int i) { nID = i; return this;    }
    public AccessAuth _nID_Access(int i) { nID_Access = i;  return this;   }
    public AccessAuth _sAuth(String s) { sAuth = s;   return this;   }
    public AccessAuth _sDateMake(String s) {   sDateMake = s;     return this;    }

// Getters
    public int nID() {      return nID;    }
    public int nID_Access() {    return nID_Access;    }
    public String sAuth() {    return sAuth;   }
    public String sDateMake() {      return sDateMake;    }
    //================================================
     

     /**
      * Сохраняем Cookie пользователя в Базе (при входе)
      *
      * @param nID_Access - nID пользователя
      * @param sAuth - сама Кука
      * @param sDateMake - дата создания
      * @param countMax - Регулятор коол-ва Кук в Базе на 1 пользователя. Если в БД Куков этого юзера больше этого числа, 
      * то 1 запись удаляется и 1 добавляется, если меньше этого числа, то просто добавляется 1 запись.
      * @throws Exception
      */
     public void saveCookieToDB(int nID_Access, String sAuth, String sDateMake, int countMax) throws Exception {
          String sCase = "saveCookieToDB";
          int nCountRows = 0;
          Statement oStatement = null;
          Connection oConnection = null;     
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); 
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); 
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT count(*) FROM AccessAuth where nID_Access = " + nID_Access, oLog); //ОБРАЗЕЦ            
               if (oRowset.next()) {
                    nCountRows = oRowset.getInt(1);
               }
               if (nCountRows <= countMax) {  // если меньше 4 записией в базе, то просто добавляем 1 запись
                    AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                                                   + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')", oLog);       
               }
               if (nCountRows > countMax) {      // если больше допустимого кол-ва записей (4-х например)
                    // удяляем 1 самую верхнюю(старую)  запись       
                    AccessDB.nRowsetUpdate(oStatement, sCase, "DELETE top 1 FROM AccessAuth WHERE nID_Access = " + nID_Access, oLog);           
                    // добавляем новую запись
                    AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                                                  + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')", oLog);           
               }
          } catch (Exception oException) {
               oLog.error("[" +sCase+ "](nID_Access= " +nID_Access+ " sAuth= " +sAuth+ " sDateMake= " +sDateMake+ " countMax= " +countMax+"  :  Ошибка! nID из Access пустой!", oException);
          } finally {
            AccessDB.close(sCase, oStatement); 
            AccessDB.closeConnectionStatic(sCase, oConnection);
          }
     }

     /**
      * По Куке получаем из БД Емаил и Пароль пользователя
      *
      * @param sCookie - Кука Пользователя
      * @param sUserIP - Пишем в Лог IP нарушителя, если Кука Ложная
      * @return - Возаращаем Логин и Пароль пользователя в виде массива строк: 0
      *           строка - Логин, 1 строка Пароль
      * @throws Exception
      */
     public ArrayList<String> aFindUserFromCookie(String sCookie, String sUserIP) throws Exception {
          String sCase = "aFindUserFromCookie";
          String nIdUserFromCookie = "";
          String sCookieDB = "";
          ArrayList<String> list = new ArrayList<String>();
          Statement oStatement = null;
          Connection oConnection = null;
          try {
               oConnection = AccessDB.oConnectionStatic(sCase);
               oStatement = AccessDB.oStatementStatic(oConnection, sCase);       
               //   31&cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr //  31%26cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr  
               // Получаем всю Куку из базы
               ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT top 1 sAuth FROM AccessAuth where sAuth = '" + sCookie + "'", oLog);
               if (oRowset.next()) {
                    sCookieDB = oRowset.getString(1);
               }
               if ((sCookie != "") & (sCookie.equals(sCookieDB))) {// если две куки совпадают
                    //вырезаем ИД юзера из куки
                    if (sCookie.indexOf("&") != -1) { // если есть знак "&"
                         nIdUserFromCookie = sCookie.substring(0, sCookie.indexOf("&")); // Берем все что сначала строки и до знака "&"   //  sCookieClient = s.substring(s.lastIndexOf("&")+1); // берем все что после "&" и до конца 
                    }
                    // Получаем Емаил и Пароль по ИД
                    oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT sLogin, sPassword FROM Access where nID = " + nIdUserFromCookie, oLog);
                    if (oRowset.next()) {
                         list.add(oRowset.getString("sLogin"));
                         list.add(oRowset.getString("sPassword"));
                    }
                    //  возвращаем Емайл и Пароль клиента
               } else {     // Если Куки не совпадают! ТРЕВОГА !!! возвращаем нули вместо логина и пароля, и с ними юзер не войдет.
                    list.add("0");
                    list.add("0");
                    oLog.info(" ПОПЫТКА ВЗЛОМА! Ложная КУКА!  IP злоумышленника : " + sUserIP);
               }
          } catch (Exception oException) {
               oLog.error("[" + sCase + "] sCookie= " + sCookie + " sUserIP= " + sUserIP + ": Ошибка записи Куки в базу!", oException);
          } finally {
               AccessDB.close(sCase, oStatement); 
               AccessDB.closeConnectionStatic(sCase, oConnection);
               return list;     // возвращаем в любом случае
          }
     }

     
     /**
      * Возвращаем самую старую по дате Куку юзера из базы по Емайлу Юзера
      *
      * @param sEmail - Маил Юзера
      * @return - строку с Кукой
      * @throws Exception
      */
     public String sFindCookie(String sEmail) throws Exception {
          String sCase = "sFindCookie";
          String sCookieDB = null;
          Statement oStatement = null;
          Connection oConnection = null;
          try {
               oConnection = AccessDB.oConnectionStatic(sCase); 
               oStatement = AccessDB.oStatementStatic(oConnection, sCase); 
               ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sAuth FROM AccessAuth AA LEFT JOIN Access Ac ON Ac.nID = AA.nID_Access where Ac.sLogin = '" + sEmail + "'", oLog);//ОБРАЗЕЦ  
               if (oRowset.next()) {
                    sCookieDB = oRowset.getString(1);
               }
               sCookieDB = sCookieDB.replace("&", "%26");  // меняем для корректного отображения в Хеше
          } catch (Exception oException) {
               oLog.error("[" + sCase + "]: Ошибка получения Куки из базы! Класс AccessAuth", oException);
          } finally {
               AccessDB.close(sCase, oStatement);   
               AccessDB.closeConnectionStatic(sCase, oConnection);    
               return sCookieDB;         // возвращаем в любом случае
          }
     }

     /**
      * Генерируем строку из 50 случайных символов для Куки
      *
      * @return - строку из 50 случайных символов a-z
      * @throws Exception
      */
     public String generateString() throws Exception {
          String sCreateCookie = "";
          for (int i = 0; i < 50; i++) {
               Random rand = new Random();
               int nRandom = rand.nextInt();
               // No.2 Случайное целое число от 0 до 10
               nRandom = rand.nextInt(26);
               int a = (int) 'a';
               char b = (char) (a + nRandom);
               sCreateCookie += b;
          }
          return sCreateCookie;
     }
}
