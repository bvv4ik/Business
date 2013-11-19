package business.model;

import business.AccessDB;
import business.ManagerSQL;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/******
 go
 drop table Access
 
 go
 CREATE table Access (        
     nID INT identity,
     nID_TheSubjectHuman INT not null,
     sLogin VARCHAR(25) not null,            --  СДЕЛАТЬ уникально UNIQUE!
     sPassword VARCHAR(25) not null,         -- 
     nDisabled INT default -1,               -- (вырубить доступ)  0 - доступ открыт, 1 - закрыт, -1 - неопределено
     sDateMake DATETIME default getDate() null,
     sDateEdit DATETIME default getDate() null,
     PRIMARY KEY(nID),
 )

-- Все поля индексировать?
--CREATE INDEX IndexID ON Access (nID)
--DROP INDEX Access.Index_nID
CREATE unique INDEX Index_nID_TheSubjectHuman ON Access (nID_TheSubjectHuman)
DROP INDEX Access.Index_nID_TheSubjectHuman

 
 
--ALTER TABLE Access ADD sDateMake DATETIME default getDate() null 
--ALTER TABLE Access ADD sDateEdit DATETIME default getDate() null
--alter table Access drop bDisabled
--alter table Access add nDisabled INT default -1
-- alter table Access  modify nID_TheSubjectHuman INT null
-- alter table Access  modify sLogin INT UNIQUE (sLogin)
    
**/


public class Access {

      public static ArrayList<String[]> aResult = new ArrayList<String[]>();
     public static ArrayList<String> aResult2 = new ArrayList<String>();
     public static ArrayList<String> aResult3 = new ArrayList<String>();
     public static String sObject = "";
     
    private Logger oLog = Logger.getLogger(getClass());
    private int nID;
    private int nID_TheSubjectHuman;
    private String sLogin;   // ( сделать уникальным)
    private String sPassword;
    private int nDisabled = -1; // (вырубить доступ. 0 доступ открыт, 1 доступ закрыт, -1 неопределно)
    private String sDateMake;   // дата создания записи
    private String sDateEdit;   // дата редактирпования записи
    
     public int nID() {
          return nID;
     }

     public Access _ID(int i) {
          nID = i;
          return this;
     }

     public int nID_TheSubjectHuman() {
          return nID_TheSubjectHuman;
     }

     public Access _ID_TheSubjectHuman(int i) {
          nID_TheSubjectHuman = i;
          return this;
     }

     public String sLogin() {
          return sLogin;
     }

     public Access _Login(String s) {
          sLogin = s;
          return this;
     }

     public String sPassword() {
          return sPassword;
     }

     public Access _Password(String s) {
          sPassword = s;
          return this;
     }

     public int nDisabled() {
          return nDisabled;
     }

     /**
      * Выключить/заблокировать юзера (0 доступ открыт, 1 закрыт, -1 неопределено)
      *
      * @param n
      * @return
      */
     public Access _Disabled(int n) {
          nDisabled = n;
          return this;
     }

     public String sDateMake() {
          return sDateMake;
     }

     public Access _sDateMake(String s) {
          sDateMake = s;
          return this;
     }

     public String sDateEdit() {
          return sDateEdit;
     }

     public Access _sDateEdit(String s) {
          sDateEdit = s;
          return this;
     }


    
    
    
    
    //================================================
    public Access() {
    }

    public Access(String sLogin) throws Exception {
        load(sLogin);
    }

    /**
     * Инициализируем класс данными прользователя по Логину
     *
     * @param sLogin - Емаил пользователя
     * @throws Exception
     */
    public void load(String sLogin) throws Exception {
        String sCase = "load(sLogin)";
        Connection oConnection = null;
        Statement oStatement = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 *  FROM Access where sLogin = '" + sLogin + "'", oLog);
            while (oRowset.next()) {
                nID = oRowset.getInt("nID");
                nID_TheSubjectHuman = oRowset.getInt("nID_TheSubjectHuman");
                sLogin = oRowset.getString("sLogin");
                sPassword = oRowset.getString("sPassword");
                nDisabled = oRowset.getInt("nDisabled");
            }
        } catch (Exception oException) {
            oLog.error("[" + sCase + "] (sLogin= " + sLogin + "): Ошибка Конструктора ", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
        }
    }

    /**
     * По Емэйлу пользователя меняем его данные в базе
     *
     * @param sLogin
     * @throws Exception
     */
    public void save(String sLoginOld) throws Exception {
        long start = System.currentTimeMillis(); // Вычисляем время выполнения метода
        String sCase = "save";
        Connection oConnection = null;
        Statement oStatement = null;
        //String sLoginOld = sLogin(); 
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            String sSQL = new ManagerSQL()
                    ._Pair("sDateEdit = getDate()")
                    ._Pair("sLogin = '" + sLogin() + "'", sLogin() != null)
                    ._Pair("sPassword = '" + sPassword() + "'", sPassword() != null)
                    ._Pair("nDisabled = " + nDisabled(), nDisabled() != -1)
                    .sSQL("UPDATE Access SET ", " WHERE sLogin='" + sLoginOld + "'"); //sLoginOld
            AccessDB.nRowsetUpdate(oStatement, sCase, sSQL, oLog);

                    
            /*if (sLogin() != null) {
             AccessDB.nRowsetUpdate(oStatement, sCase, "UPDATE Access SET sLogin = '" + sLoginNew + "' WHERE sLogin='" + sLoginOld + "'", oLog);
             } */

        } catch (Exception oException) {
            oLog.error("[" + sCase + "]( sLogin()= " + sLogin() + " sLogin()= " + sLogin() 
                    + " nDisabled()= " + nDisabled() + " ): Ошибка записи!", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            long stop = System.currentTimeMillis();  // Вычисляем время выполнения метода
            String sTime = ((double)(stop-start)/1000 + " seconds");
            oLog.info("[" +sCase+ "](Время выполнения: " +sTime);
        }
    }

    /**
     * Возвращает nID из таблицы Access по Емайлу из этой же таблицы
     *
     * @param sLogin - Емаил пользователя
     * @return
     * @throws Exception
     */
    public int nGetIdAccess(String sLogin) throws Exception {
        String sCase = "nGetIdAccess";
        int nID = 0;
        Connection oConnection = null;
        Statement oStatement = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 nID FROM Access where sLogin = '" + sLogin + "'", oLog);
            while (oRowset.next()) {
                nID = oRowset.getInt(1);
            }
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](nID= " + nID + " sLogin= " + sLogin + "): Ошибка получения записи! Класс Access", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            return nID;     // Вернет ID, а если Емайла нету то вернет 0 
        }
    }

    /**
     * Регистрирует Юзера по введенным Логину и Паролю 1. 1.Проверяет Логин и
     * Пароль на валидность - Основная проверка осуществляется на клиенте а, эта
     * проверка на всякий случай на сервере, чтобы отключив JS, нельзя было
     * отправить прямой запрос и зарегистрироватся с неверными данными иди
     * создать ошибку. 2. Прописывает (целую цепочку) в Базу данные о
     * регистрации
     *
     * @param sEmail - Емайл пользователя
     * @param sPassword - Пароль пользователя
     * @return - Возвращает Строку либо с описанием ошибки или с подтверждением
     * регистрации.
     * @throws Exception
     */
    public String sUserRegistration(String sEmail, String sPassword) throws Exception {
        long start = System.currentTimeMillis();  // Вычисляем время выполнения метода
        String sCase = "sUserRegistration";

        if ((sEmail == null) | (sPassword == null)) {
            return "Одна или несколько строк Null";
        } else if ((sEmail.equals("")) | (sPassword.equals(""))) {//проверка на пустые строки
            return "Внимание, заполните поля!";
        } else if ((!bValidString(sEmail)) | (!bValidString(sPassword))) {//Если true то прис. недопустимые символы 
            return "Логин или Пароль содержат недопустимые символы!";
        } else if (sPassword.length() < 10) {//проверка длинны пароля
            return "Длинна пароля должна быть больше 10 символов!";
        } else if (sLoginExists(sEmail)!=null) {//если != null то логин уже существует в Базе
            return "Этот Логин уже занят!";
        }

        Statement oStatement = null;
        Connection oConnection = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
            AccessDB.transactBegin(oStatement, sCase, oLog);    //Начинаем транзакцию  ОБРАЗЕЦ//  

            // вставляем по умолчанию запись "1" т.е "человек"
            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO TheSubject (nID_OfSubject) VALUES (1)", oLog);
            int nLastIdentityTheSubject = AccessDB.nRowsetID(oStatement, sCase, oLog);//ОБРАЗЕЦ!!!

            //TODO: -- Поправить поля таблицы черег AlterTable, сделав допустимость null-значений в полях, и соотв. раскомментировать строку
            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO TheSubjectHuman(nID_TheSubject, nSex) "
                    + "VALUES (" + nLastIdentityTheSubject + ",1)", oLog);
            //AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO TheSubjectHuman(nID_TheSubject, sTheSubjectHuman, sLastName, sFirstName, sSurName, sDTbirth, sDTdeath, nSex ) "
            //           + "VALUES (" + nLastIdentityTheSubject + ",'Человек','Фамилия','Имя','Отчество','1900-11-11 11:11:11','1900-11-11 11:11:11',1)", oLog);
            int nLastIdentityTheSubjectHuman = AccessDB.nRowsetID(oStatement, sCase, oLog);//ОБРАЗЕЦ!!!

            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO Access (nID_TheSubjectHuman, sLogin, sPassword, nDisabled )"
                    + " VALUES (" + nLastIdentityTheSubjectHuman + ",'" + sEmail + "','" + sPassword + "',0)", oLog);

            AccessDB.transactCommit(oStatement, sCase, oLog);    //Подтверждаем транзакцию   ОБРАЗЕЦ//
            return "Добро пожаловать на сайт!";     // нельзя менять т.к работает как Колбэк "Учетная запись создана !"
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sEmail= " + sEmail + "  : Ошибка Регистрации пользователя! Класс Access", oException);     //ОБРАЗЕЦ   //так делать всегда!
            AccessDB.transactRollback(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
            return "Ошибка Регистрации пользователя!: Класс Access";
        } finally {
            AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ
            AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ
            
            long stop = System.currentTimeMillis();  // Вычисляем время выполнения метода
            String sTime = ((double)(stop-start)/1000 + " seconds");
            oLog.info("[" +sCase+ "](Время выполнения: " +sTime);
        }
    }

    /**
     * Заключительный этап после регистрации Юзера - Внести его данные в Сессию,
     * создать ему Куку и сохранить ее в Базу
     *
     * @param sEmail
     * @param sPassword
     * @param oSession
     * @param sIP - IP данного пользователя
     * @return - Возвращает сгенерированную Куку пользователя
     * @throws Exception
     */
    public String sAfretRegister(String sEmail, String sPassword, HttpSession oSession, String sIP) throws Exception {
        String sCase = "sAfretRegister";
        Access A = new Access();
        Date d = new Date();          // узнаем текущую дату
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String sTimeLogin = df.format(d);

        int nID = A.nGetIdAccess(sEmail);      // Получаем NID пользователя (из Access) по его Емайлу
        if (nID == 0) {
            oLog.error("[" + sCase + "](sEmail= " + sEmail + " sIP= " + sIP + "  :  Ошибка! nID из Access вернулся пустой! Класс Access");
            return "Ошибка sUserRegistration, nID == 0";       // прерываем дальнейший вход на сайт
        }
        oSession.setAttribute("sEmail", sEmail);
        oSession.setAttribute("sPassword", sPassword);

        AccessAuth oAccessAuth = new AccessAuth();
        String sGenerate = oAccessAuth.generateString();   // генерируем строку 50 символов для куки
        String sCreateCookie = nID + "&" + sGenerate;    // соединяем в одну куку
        oAccessAuth.saveCookieToDB(nID, sCreateCookie, sTimeLogin, 3);

        // Запись в базу инфы о пользователе при попытке его Входа (при первой регистрации)
        AccessOf oAccessOf = new AccessOf();
        oAccessOf.saveInfo(sEmail, sIP, 1); // 1 - доступ был открыт т.к первый раз входит

        return sCreateCookie;
    }

    /**
     * Возвращает Пароль по Логину. Возвращает null если такого Пароля у Логина
     * нету
     *
     * @param sLogin
     * @return
     */
    public String sGetPassword(String sLogin) {
        String sCase = "sGetPassword";
        String sPassword = null;
        Statement oStatement = null;
        Connection oConnection = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'", oLog); //ОБРАЗЕЦ
            while (oRowset.next()) {
                sPassword = oRowset.getString(1);
            }

        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sLogin= " + sLogin + " :Ошибка получения Пароля!", oException);
            throw oException;
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            return sPassword;
        }

    }

    /**
     * Возвращает Емайл есои он в Базе присутствует, null - если отсутствует
     *
     * @param sLogin - Емаил Пользователя
     * @return -  Емайл пользователя или null
     */
    public String sLoginExists(String sLogin) throws IOException {
        long start = System.currentTimeMillis(); // Вычисляем время выполнения метода
        String sCase = "bLoginExists";
        String sResult = null;
        Statement oStatement = null;
        Connection oConnection = null;
        
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sLogin FROM Access WHERE sLogin=lower('" + sLogin + "')", oLog); //ОБРАЗЕЦ            
            while (oRowset.next()) {
                sResult = oRowset.getString(1);
            }
               //Thread.sleep(2000);
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sLogin= " + sLogin + " : Ошибка проверки Логина!", oException);     //ОБРАЗЕЦ//так делать всегда!                 // String sErr = _.getMessage();  System.err.println("ERROR: " + sErr + "_" + " ---- bLoginExists");   //это вывод в лог-файл
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            long stop = System.currentTimeMillis();  // Вычисляем время выполнения метода
            String sTime = ((double)(stop-start)/1000 + " seconds");
            oLog.info("[" +sCase+ "](Время выполнения: " +sTime);
            return sResult;    // возвращаем Логин
        }

//        if ( (sResult != null) && (!sResult.equals("")) && (sLogin.equalsIgnoreCase(sResult))) {    // Если Логин есть в базе
//            return true;    // true - такой Логин уже существует       
//        } else {
//            return false;   // false - Логин свободен
//        }
    }


    /**
     * Проверка строки на наличие в ней только символов:
     * ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-
     *
     * @.
     * @param sVerify
     * @return True - значит строка содержит только перечисленные символы
     */
    public boolean bValidString(String sVerify) {
        String s = sVerify; //"ascDFMSes"; // проверяемая строка
        String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-@."; // допустимые символы
        for (int n = 0; n < s.length(); n++) {
            char sChar = s.charAt(n);
            if (check.indexOf(String.valueOf(sChar)) == -1) { //в строке есть неразрешенный символ
                return false;//"В строке содержатся недопустимые символы!";
            }
        }
        return true; // строка валидная
    }

    /**
     * Проверка Емайла на синтаксическую валидность
     *
     * @param sVerify
     * @return True - Значит валидный Емайл
     */
    public boolean bValidMail(String sVerify) {
        final Pattern pattern = Pattern.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
        final Matcher matcher = pattern.matcher(sVerify);
        //"mail@sd.com"
        if (matcher.find()) {
            return true;    //"Емаил правильный!"; //Емаил правильный!
        } else {
            return false;    //""; //Ошибочный!
        }
    }
    //----------------------------------------------------------------
    
    
    
    
    
    
//     /**
//      *  Возвращает nID из таблицы Access по Емайлу в этой же таблице
//      * @param sLogin
//      * @return
//      */
//     public String getNID(String sLogin) {
//          String s = "";
//          Connection oConnection = AccessDB.oConnectionStatic("");
//          try {
//               ResultSet oRowset = oConnection.prepareStatement("SELECT TOP 1 nID FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
//
//               while (oRowset.next()) {
//                    s = oRowset.getString(1);
//               }
//               if (s != null) {    // Если nID есть у данного логина в базе
//                    return s;        // отправляем nID        
//               }
//
//          } catch (Exception _) {
//               String sErr = _.getMessage();
//               System.err.println("ERROR: " + sErr + "_" + " ---- getNID");   //это вывод в лог-файл
//
//          } finally {
//               AccessDB.closeConnectionStatic("", oConnection);
//          }
//          return "";  // "" - такого nID нет у Логина"; 
//     }
    //public boolean save(boolean b) {
    //b = true;
    //if (b) {
    //   for (int i = 1; i <= 10; i++) {
    //System.out.println("гав-гав"); }
    //s = "wsd";
    //return b;
//типа метод записи в базу параметров          
//} 
    //Access oAccess = new Access()._bDisabled(1).save();
    //.save(false);
    //public static void main(String args[]) {
//._sLogin("MyLogin")._sPassword("MyPassword").save();
    //SetnID
    //Access ac = new Access() ;  
    // Access oAccess1 = new Access();//.save(false);
    //  ac.SetnID(10);
    // System.out.println(oAccess1.bDisabled);
    //}
//- проверить соответствие указанного пароля указанному логину,
//     boolean bAuthorized(String sLogin, String sPassword) {
//          //boolean b = false;
//
//          return true;
//     }
    /*
 
     //- отредактировать его права,
     boolean bEdited(String sLogin, String oNewData){
 
     }
     //- удалить его из таблицы,
     boolean bDeleted(String sLogin){
 
     }
     //- проверить - доступна-ли ему определенная функция,
     boolean bAccept(String sLogin, String sDO){}

 
     //- какая у него роль, дата создания и т.д.
     boolean sRole(String sLogin){
     }
 
     boolean sDate(String sLogin){
     }

     */
}
