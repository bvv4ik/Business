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
 * Работа с Cookie пользователя
 *
 * @author Sergey
 */
public class AccessAuth {

     private Logger oLog = Logger.getLogger(getClass());
     // Список в котором храним данные о попытках входа пользователей
     public static HashMap<String, String> map = new HashMap<String, String>();
                      //public static ArrayList<String[]> aUserCountTry = new ArrayList<String[]>();   //public static ArrayList<String> aUserCountTry = new ArrayList<String>();
     

     /**
      * Сохраняем Cookie пользователя в Базе (при входе)
      *
      * @param nID_Access - nID пользователя
      * @param sAuth - сама Кука
      * @param sDateMake - дата создания
      * @param countMax - Регулятор коол-ва Кук в Базе на 1 пользователя. Если в
      * БД Куков этого юзера больше этого числа, то 1 запись удаляется и 1
      * добавляется, если меньше этого числа, то просто добавляется 1 запись.
      * @throws Exception
      */
     public void saveCookieToDB(int nID_Access, String sAuth, String sDateMake, int countMax) throws Exception {
          String sCase = "saveCookieToDB";
          int nCountRows = 0;
          Statement oStatement = null;
          Connection oConnection = null;     
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT count(*) FROM AccessAuth where nID_Access = " + nID_Access, oLog); //ОБРАЗЕЦ            
               if (oRowset.next()) {
                    nCountRows = oRowset.getInt(1);
               }
               if (nCountRows <= countMax) {  // если меньше 4 записией в базе, то просто добавляем 1 запись
                    AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                                                   + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')", oLog);//ОБРАЗЕЦ            
               }
               if (nCountRows > countMax) {      // если больше допустимого кол-ва записей (4-х например)
                    // удяляем 1 самую верхнюю(старую)  запись       
                    AccessDB.nRowsetUpdate(oStatement, sCase, "DELETE top 1 FROM AccessAuth WHERE nID_Access = " + nID_Access, oLog);//ОБРАЗЕЦ            
                    // добавляем новую запись
                    AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                                                  + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')", oLog); //ОБРАЗЕЦ            
               }
          } catch (Exception oException) {
               oLog.error("[" + sCase + "]: Ошибка записи Куки в базу! Класс AccessAuth", oException);
          } finally {
            AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ
            AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ
          }
     }

     /**
      * По Куке получаем из БД Емаил и Пароль пользователя
      *
      * @param sCookie - Кука Пользователя
      * @param sUserIP - Пишем в Лог IP нарушителя, если Кука Ложная
      * @return - Возаращаем Логин и Пароль пользователя в виде массива строк: 0
      * строка - Логин, 1 строка Пароль
      * @throws Exception
      */
     public ArrayList<String> findUserFromCookie(String sCookie, String sUserIP) throws Exception {
          String sCase = "findUserFromCookie";
          String sLogin = "";
          String sPassword = "";
          String nIdUserFromCookie = "";
          String sCookieDB = "";

          ArrayList<String> list1 = new ArrayList<String>();
          //Connection oConnection = AccessDB.oConnectionStatic(sCase);
           Statement oStatement = null;
          Connection oConnection = null;     
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
          
          //try {
               //   31&cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr //  31%26cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr  
               // Получаем всю Куку из базы
               ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT top 1 sAuth FROM AccessAuth where sAuth = '" + sCookie + "'", oLog);//ОБРАЗЕЦ  
              // ResultSet oSet1 = oConnection.prepareStatement("SELECT top 1 sAuth FROM AccessAuth where sAuth = '" + sCookie + "'").executeQuery();
               if (oRowset.next()) {
                    sCookieDB = oRowset.getString(1);
               }
               if ((sCookie != "") & (sCookie.equals(sCookieDB))) {  // если две куки совпадают
                    //вырезаем ИД юзера из куки
                    if (sCookie.indexOf("&") != -1) {          // если есть знак "&"
                         nIdUserFromCookie = sCookie.substring(0, sCookie.indexOf("&")); // Берем все что сначала строки и до знака "&" 
                    }
                    // берем код Куки клиента
                    //  sCookieClient = s.substring(s.lastIndexOf("&")+1); // берем все что после "&" и до конца 
                    // Получаем Емаил и Пароль по ИД
                    oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT sLogin, sPassword FROM Access where nID = " + nIdUserFromCookie, oLog);//ОБРАЗЕЦ  
                    //ResultSet oRowset = oConnection.prepareStatement("SELECT sLogin, sPassword FROM Access where nID = " + nIdUserFromCookie).executeQuery();
                    if (oRowset.next()) {
                         sLogin = oRowset.getString("sLogin");    
                         sPassword = oRowset.getString("sPassword");
                    }
                    //  возвращаем Емайл и Пароль клиента
                    list1.add(sLogin);
                    list1.add(sPassword);
               } else {     // Если Куки не совпадают! ТРЕВОГА !!! возвращаем нули вместо логина и пароля, и с ними юзер не войдет.
                    list1.add("0");
                    list1.add("0");
                    oLog.info(" ALERT! ПОПЫТКА ВЗЛОМА! Ложная КУКА! Код куки не совпадает ни с одним из находящихся в Базе! IP злоумышленника : " + sUserIP);
               }
          } catch (Exception oException) {
               // return "Ошибка создания записи БД: Класс AccessAuth";
               oLog.error("[" + sCase + "]: Ошибка записи Куки в базу! Класс AccessAuth", oException);
          } finally {
            AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ
            AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ
               return list1;     // возвращаем в любом случае
          }
     }

     /**
      * Возвращаем самую старую по дате Куку юзера из базы по Емайлу Юзера
      *
      * @param sEmail - Маил Юзера
      * @return - строку с Кукой
      * @throws Exception
      */
     public String findCookie(String sEmail) throws Exception {
          String sCase = "findUserFromCookie";
          String sCookieDB = null;
          //Connection oConnection = AccessDB.oConnectionStatic(sCase);
           Statement oStatement = null;
          Connection oConnection = null;     
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
          //try {
              // ResultSet oRowset = oConnection.prepareStatement("SELECT top 1 sAuth FROM AccessAuth AA LEFT JOIN Access Ac ON Ac.nID = AA.nID_Access where Ac.sLogin = '" + sEmail + "'").executeQuery();
               ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sAuth FROM AccessAuth AA LEFT JOIN Access Ac ON Ac.nID = AA.nID_Access where Ac.sLogin = '" + sEmail + "'", oLog);//ОБРАЗЕЦ  
               if (oRowset.next()) {
                    sCookieDB = oRowset.getString(1);
               }
               sCookieDB = sCookieDB.replace("&", "%26");  // меняем для корректного отображения в Хеше
          } catch (Exception oException) {
               oLog.error("[" + sCase + "]: Ошибка получения Куки из базы! Класс AccessAuth", oException);
          } finally {
               AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ
               AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ
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
