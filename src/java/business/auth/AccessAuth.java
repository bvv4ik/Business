
package business.auth;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import org.apache.log4j.Logger;


public class AccessAuth {

     private Logger oLog = Logger.getLogger(getClass());

                          //public static HashMap mConnectionOpened = new HashMap();
                          //public static ArrayList<String[]> aUserCountTry = new ArrayList<String[]>();
     // Список массивов, в которых храним данные о попытках входа пользователей
     public static ArrayList<String> aUserCountTry = new ArrayList<String>();
    
     
              
     /**
      * Сохраняем Куку пользователя в Базе при входе
      *
      * @param nID_Access - nID пользователя
      * @param sAuth - сама Кука
      * @param sDateMake - дата создания
      */
     public void saveCookieToDB(int nID_Access, String sAuth, String sDateMake, int countMax) throws Exception {

          int i = 0;
          Connection oConnection = AccessDB.oConnectionStatic("");
          try {
               ResultSet oSet = oConnection.prepareStatement("SELECT count(*) FROM AccessAuth where nID_Access = " + nID_Access).executeQuery();
               if (oSet.next()) {
                    i = oSet.getInt(1);
               }
               if (i <= countMax) {  // если меньше 4 записией в базе, то просто добавляем
                    oConnection.prepareStatement("INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                            + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')").executeUpdate();
               }
               if (i > countMax) {      // если больше допустимого кол-ва записей (4-х например)
                    // удяляем 1 самую верхнюю(старую)  запись       
                    oConnection.prepareStatement("DELETE top 1 FROM AccessAuth WHERE nID_Access = " + nID_Access).executeUpdate();
                    // добавляем новую запись
                    oConnection.prepareStatement("INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                            + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')").executeUpdate();
               }
          } catch (Exception e) {
               // return "Ошибка создания записи БД: Класс AccessAuth";
          } finally {
               AccessDB.closeConnectionStatic("", oConnection);    // так делать всегда!!1
          }
     }

     
     
     public ArrayList<String> findUserFromCookie(String sCookie, String sUserIP) throws Exception {
          String sLogin = "";
          String sPassword = "";
          String nIdUserFromCookie = "";
          String sCookieDB = "";

          ArrayList<String> list1 = new ArrayList<String>();
          Connection oConnection = AccessDB.oConnectionStatic("");
          try {
               //   31&cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr //  31%26cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr  
               // Получаем всю Куку из базы
               ResultSet oSet1 = oConnection.prepareStatement("SELECT top 1 sAuth FROM AccessAuth where sAuth = '" + sCookie + "'").executeQuery();
               if (oSet1.next()) {
                    sCookieDB = oSet1.getString(1);
               }
               if ((sCookie != "") & (sCookie.equals(sCookieDB))) {  // если две куки совпадают
                    //вырезаем ИД юзера из куки
                    if (sCookie.indexOf("&") != -1) {          // если есть знак "&"
                         nIdUserFromCookie = sCookie.substring(0, sCookie.indexOf("&")); // Берем все что сначала строки и до знака "&" 
                    }
                              // берем код Куки клиента
                              //  if (s.indexOf("&") != -1){             // если есть знак "&"
                              //  sCookieClient = s.substring(s.lastIndexOf("&")+1); // берем все что после "&" и до конца 
                              //  }
                    // Получаем Емаил и Пароль по ИД
                    ResultSet oSet = oConnection.prepareStatement("SELECT sLogin, sPassword FROM Access where nID = " + nIdUserFromCookie).executeQuery();
                    if (oSet.next()) {
                         // sLogin = oSet.getString(1); // sPassword = oSet.getString(2);
                         sLogin = oSet.getString("sLogin");
                         sPassword = oSet.getString("sPassword");
                    }
                    //  возвращаем Емайл и Пароль клиента
                    list1.add(sLogin);
                    list1.add(sPassword);
               } else {   // Кики не совпадают! ТРЕВОГА !!! возвращаем нули вместо паролей
                    list1.add("0");
                    list1.add("0");
                    oLog.info(" ALERT! ПОПЫТКА ВЗЛОМА! Ложная КУКА! Код куки не совпадает ни с одним из находящихся в Базе! IP злоумышленника : " + sUserIP);
               }
          } catch (Exception e) {
               // return "Ошибка создания записи БД: Класс AccessAuth";
          } finally {
               AccessDB.closeConnectionStatic("", oConnection);  // так делать всегда!!1
               return list1;  // возвращаем в любом случае
          }
     }

     
     
     /**
      *  Возвращаем самую старую по дате  Куку юзера из базы по Емайлу Юзера
      * @param sEmail - Маил Юзера
      * @return  строку с Кукой
      * @throws Exception
      */
     public String findCookie(String sEmail) throws Exception {

          String sCookieDB = "";
          Connection oConnection = AccessDB.oConnectionStatic("");
          try {
               ResultSet oSet1 = oConnection.prepareStatement("SELECT top 1 sAuth FROM AccessAuth AA LEFT JOIN Access Ac ON Ac.nID = AA.nID_Access where Ac.sLogin = '" + sEmail + "'").executeQuery();
               if (oSet1.next()) {
                    sCookieDB = oSet1.getString(1);
               }
               sCookieDB = sCookieDB.replace("&", "%26");  // для Хеша
          } catch (Exception e) {
               // return "Ошибка создания записи БД: Класс AccessAuth";
          } finally {
               AccessDB.closeConnectionStatic("", oConnection);  // так делать всегда!!
               return sCookieDB;      // возвращаем в любом случае
          }
     }
     
     

     /**
      * Cоздаем строку из 50 случайных символов для Куки
      * @return  строку из 50 случайных символов a-z
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
