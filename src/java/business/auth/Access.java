
package business.auth;


import business.AccessDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/*
 
 
  
 CREATE table Access (        
     nID INT identity,
     nID_TheSubjectHuman INT not null,
     sLogin VARCHAR(25) not null,            -- (уникально)
     sPassword VARCHAR(25) not null,         -- (уникально)
     bDisabled TINYINT not null,             -- вырубить доступ
     PRIMARY KEY(nID),
 )

drop table Access
--alter table Access  modify nID_TheSubjectHuman INT null

* 
 */
public class Access {

    private int nID;
    private int nID_SubjectHuman;
    private String sLogin; //(уникально)
    private String sPassword; //(уникально)
    private int bDisabled; //(вырубить доступ)

    private Logger oLog = Logger.getLogger(getClass());
    

// Setters
    public Access _nID(int i) { nID = i; return this;    }
    public Access _nID_SubjectHuman(int i) { nID_SubjectHuman = i;  return this;   }
    public Access _sLogin(String s) { sLogin = s;   return this;   }
    public Access _sPassword(String s) {   sPassword = s;     return this;    }
    public Access _bDisabled(int b) {   bDisabled = b;   return this;  }

// Getters
    public int nID() {      return nID;    }
    public int nID_SubjectHuman() {    return nID_SubjectHuman;    }
    public String sLogin() {    return sLogin;   }
    public String sPassword() {      return sPassword;    }
    public int bDisabled() {      return bDisabled;    }
    //================================================

    
    /**
     * Возвращает nID из таблицы Access по Емайлу из этой же таблицы
     *
     * @param sLogin - Емаил пользователя
     * @return
     * @throws Exception
     */
    public int getIdAccess(String sLogin) throws Exception {
        String sCase = "getIdAccess";    //ОБРАЗЕЦ  
        int nID=0;
        Connection oConnection = null;
        Statement oStatement = null;
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ  
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 nID FROM Access where sLogin = '" +sLogin+ "'", oLog);
            if (oRowset.next()) {
                nID = oRowset.getInt(1);
            }
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](nID= " +nID+ "): Ошибка получения записи! Класс Access", oException); //ОБРАЗЕЦ//так делать всегда!
        } finally {
            AccessDB.close(sCase, oStatement); //ОБРАЗЕЦ 
            AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ
            return nID;     // Вернет число или 0 
        }
    }

    /**
     * Регистрирует Юзера по введенным Логину и Паролю 1.
     * 1.Проверяет  Логин и Пароль на валидность - Основная проверка введенных пользователем
     * данных осуществляется на клиенте через JS, эта проверка данных на всякий
     * случай размещена на сервере, чтобы отключив JS, нельзя было отправить
     * прямой запрос и зарегистрироватся с неверными данными иди создать ошибку,
     * когда в базу например будет внесена запись с запрещенными символами и
     * из-за этого она заглчит. 
     * 2. Прописывает (целую цепочку) в Базу данные о регистрации
     *
     * @param sEmail - Емайл пользователя
     * @param sPassword - Пароль пользователя
     * @return - Возвращает Строку либо с описанием ошибки или с подтверждением регистрации.
     * @throws Exception
     */
    public String sUserRegistration(String sEmail, String sPassword) throws Exception {
        String sCase = "sUserRegistration";   //ОБРАЗЕЦ  
        
        if ((sEmail == null) | (sPassword == null)) {
            return "Одна или несколько строк Null";
        } else if ((sEmail.equals("")) | (sPassword.equals(""))) {//проверка на пустые строки
            return "Внимание, заполните поля!";
        } else if ((!bValidString(sEmail)) | (!bValidString(sPassword))) {//Если true то прис. недопустимые символы 
            return "Логин или Пароль содержат недопустимые символы!";
        } else if (sPassword.length() < 10) {//проверка длинны пароля
            return "Длинна пароля должна быть больше 10 символов!";
        } else if (bLoginExists(sEmail)) {//если true то логин уже существует в Базе
            return "Этот Логин уже занят!";
        }

        //ResultSet oRowset; 
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

            AccessDB.nRowsetUpdate(oStatement, sCase, "INSERT INTO Access (nID_TheSubjectHuman, sLogin, sPassword, bDisabled )"
                    + " VALUES (" + nLastIdentityTheSubjectHuman + ",'" + sEmail + "','" + sPassword + "',1)", oLog);
            
            AccessDB.transactCommit(oStatement, sCase, oLog);    //Подтверждаем транзакцию   ОБРАЗЕЦ//
            return "Добро пожаловать на сайт!";     // нельзя менять т.к работает как Колбэк "Учетная запись создана !"
        } catch (Exception oException) {
            oLog.error("[" + sCase + "]: Ошибка Регистрации пользователя! Класс Access", oException);     //ОБРАЗЕЦ   //так делать всегда!
            AccessDB.transactRollback(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
            return "Ошибка Регистрации пользователя!: Класс Access";
        } finally {
            AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ
            AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ
        }
    }

    /**
     * Заключительный этап после регистрации Юзера - Внести его данные в Сессию,
     * создать ему Куку и сохранить ее в Базу
     *
     * @param sEmail
     * @param sPassword
     * @param oSession 
     * @param sIP -  IP данного пользователя
     * @return  - Возвращает сгенерированную Куку пользователя
     * @throws Exception
     */
    public String afretRegister(String sEmail, String sPassword, HttpSession oSession, String sIP) throws Exception {
        String sCase = "afretRegister";
        Access A = new Access();
        Date d = new Date();          // узнаем текущую дату
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String sTimeLogin = df.format(d);

        int nID = A.getIdAccess(sEmail);      // Получаем NID пользователя (из Access) по его Емайлу
        if (nID == 0) {
            oLog.error("[" + sCase + "]  :  Ошибка! nID из Access вернулся пустой! Класс Access");
            return "Ошибка sUserRegistration, nID == 0";       // прерываем дальнейший вход на сайт
        }
        oSession.setAttribute("sEmail", sEmail);
        oSession.setAttribute("sPassword", sPassword);

        AccessAuth oAccessAuth = new AccessAuth();
        String sGenerate = oAccessAuth.generateString();   // генерируем строку 50 символов для куки
        String sCreateCookie = nID + "&" + sGenerate;    // соединяем в одну куку
        oAccessAuth.saveCookieToDB(nID, sCreateCookie, sTimeLogin, 3);

        // Запись в базу инфы о пользователе при попытке его Входа
        AccessOf.saveInfoTryLogined(sEmail, sIP, true); //  true доделать

        return sCreateCookie;
    }

    
   
    /**
     * Возвращает Пароль по Логину. Возвращает null если такого Пароля у Логина нету
     *
     * @param sLogin
     * @return
     */
    public String getPassword(String sLogin) {
        String sCase = "getPassword";
        String sPassword = null;
        Statement oStatement = null;
        Connection oConnection = null;     
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'", oLog); //ОБРАЗЕЦ
                    // AccessDB.nRowsetUpdate(oStatement, sCase, "SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'", oLog);
                    //oStatement = oConnection.prepareStatement("SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'");
            if (oRowset.next()) {
                sPassword = oRowset.getString(1);
            }

        } catch (Exception oException) {
            oLog.error("[" + sCase + "]:Ошибка получения Пароля!", oException);     //ОБРАЗЕЦ
            throw oException;
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            return sPassword;
        }

    }

    /**
     * Возвращает true если такой Емайл в Базе присутствует, false - если отсутствует
     *
     * @param sLogin - Емаил Поьзователя
     * @return
     */
    public boolean bLoginExists(String sLogin) {
        String sCase = "bLoginExists";
        String sResult = null;
        Statement oStatement = null;
        Connection oConnection = null;     
        try {
            oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); //ОБРАЗЕЦ
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "SELECT TOP 1 sLogin FROM Access WHERE sLogin='" + sLogin + "'", oLog); //ОБРАЗЕЦ            
            if (oRowset.next()) {
                sResult = oRowset.getString(1);
            }

        } catch (Exception oException) {
            oLog.error("[" + sCase + "]: Ошибка проверки Логина!", oException);     //ОБРАЗЕЦ//так делать всегда!                 // String sErr = _.getMessage();  System.err.println("ERROR: " + sErr + "_" + " ---- bLoginExists");   //это вывод в лог-файл
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);     //AccessDB.closeConnectionStatic("", oConnection);
        }

        if ((sResult != "") && (sResult != null) && (sLogin.equals(sResult))) {    // Если Логин есть в базе
            return true;    // true - такой Логин уже занят       
        } else {
            return false;   // false - Логин свободен
        }
    }

    /**
     * Проверка строки на наличие в ней только символов:
     *  ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-@.
     * @param sVerify
     * @return True - значит строка содержит только перечисленные символы
     */
    public boolean bValidString(String sVerify) {
        String s = sVerify; //"ascDFMSes"; // проверяемая строка
        String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-@."; // допустимые символы
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (check.indexOf(String.valueOf(c)) == -1) { //в строке есть неразрешенный символ
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
//               if (oRowset.next()) {
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
