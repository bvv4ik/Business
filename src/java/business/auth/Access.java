
package business.auth;


import business.AccessDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;



public class Access {

     private int nID;
     private int nID_SubjectHuman;
     private String sLogin; //(уникально)
     private String sPassword; //(уникально)
     private int bDisabled; //(вырубить доступ)
     private Logger oLog = Logger.getLogger(getClass());
 //    private static Logger oLogStatic = Logger.getLogger(Access.class);

     
// Setters
     public Access _nID(int i) {
          nID = i;
          return this;
     }

     public Access _nID_SubjectHuman(int i) {
          nID_SubjectHuman = i;
          return this;
     }

     public Access _sLogin(String s) {
          sLogin = s;
          return this;
     }

     public Access _sPassword(String s) {
          sPassword = s;
          return this;
     }

     public Access _bDisabled(int b) {
          bDisabled = b;
          return this;
     }

     // Getters
     public int nID() {
          return nID;
     }

     public int nID_SubjectHuman() {
          return nID_SubjectHuman;
     }

     public String sLogin() {
          return sLogin;
     }

     public String sPassword() {
          return sPassword;
     }

     public int bDisabled() {
          return bDisabled;
     }
     //================================================

     
     

     /**
      *  Возвращает nID из таблицы Access по Емайлу из этой же таблицы
      * @param sLogin   
      * @return
      * @throws Exception
      */
     
     public String getIdAccess(String sLogin) throws Exception {
          String sCase = "getIdAccess"; //ОБРАЗЕЦ  //так делать всегда!
          //int i = null;
          String sResult = null;
          Connection oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ//так делать всегда!
          PreparedStatement oStatement = null;
          //AccessDB oAccessDB=new AccessDB(); //ОБРАЗЕЦ
          try {
               oStatement = oConnection.prepareStatement("SELECT TOP 1 nID FROM Access where sLogin = '" + sLogin + "'");

               //AccessDB.transactBegin(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
               ResultSet oSet = oStatement.executeQuery();
               if (oSet.next()) {
                    sResult = oSet.getString(1);
               }
               
               //AccessDB.transactCommit(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
          } catch (Exception oException) {
               oLog.error("[" + sCase + "](sResult=" +sResult+ "): Ошибка получения записи! Класс Access", oException); //ОБРАЗЕЦ//так делать всегда!
               //AccessDB.transactRollback(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
          } finally {
               AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ//так делать всегда!
               AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ//так делать всегда!
               return sResult;     // Вернет или Номер или null
          }
     }

     
     
     /**
      * Производит регистрацию Юзера по введенным Логину и Паролю
      *  1. Проверяет Логин и Пароль на валидность
      *  - Основная проверка введенных пользователем данных осуществляется на клиенте через JS, эта проверка данных на всякий случай 
      *    размещена на сервере, чтобы отключив JS, нельзя было отправить прямой запрос и зарегистрироватся с неверными данными иди 
      *    создать ошибку, когда в базу например будет внесена запись с запрещенными символами и из-за этого она заглчит.  
      *  2. Прописывает (целую цепочку) в Базу данные о регистрации 
      * 
      * @param sEmail - Емайл пользователя
      * @param sPassword - Пароль пользователя
      * @return - Возвращает Строку либо с описанием ошибки или с подтверждением регистрации.
      * @throws Exception
      */
     
     public String userRegistration(String sEmail, String sPassword) throws Exception {

          if ((sEmail == null) | (sPassword == null)) {
               return "Одна или несколько строк Null";
          } else if ((sEmail.equals("")) | (sPassword.equals(""))) {   // проверка на пустые строки
               return "Внимание, заполните поля!";
          } else if ((!bValidString(sEmail)) | (!bValidString(sPassword))) {    // Если true то прис. недопустимые символы 
               return "Логин или Пароль содержат недопустимые символы!";
          } else if (sPassword.length() < 10) {       //проверка длинны пароля
               return "Длинна пароля должна быть больше 10 символов!";
          } else if (bLoginExists(sEmail)) {        // если true то логин уже существует в Базе
               return "Этот Логин уже занят!";
          }
          
          String sCase = "userRegistration"; //ОБРАЗЕЦ  //так делать всегда!
          ResultSet oSet; 
          PreparedStatement oStatement = null;               
          Connection oConnection = AccessDB.oConnectionStatic(sCase);
          //AccessDB oAccessDB=new AccessDB(); //ОБРАЗЕЦ
          try {
                              
               // Открываем транзакцию для нескольких записей в БД 
                 oConnection.setAutoCommit(false);
                           // AccessDB.transactBegin(oStatement, sCase, oLog);    //Начинаем транзакцию   ОБРАЗЕЦ//  Так возникает ошибка! и в базу не пишет!

               // вставляем по умолчанию запись "1" т.е "человек"
               oStatement = oConnection.prepareStatement("INSERT INTO TheSubject (nID_OfSubject) VALUES (1)");
               oStatement.executeUpdate();
               
               oSet = oConnection.prepareStatement("SELECT @@identity").executeQuery();
               int nLastIdentityTheSubject = oSet.next() ? oSet.getInt(1) : 0;
                                                     //oSet = oConnection.prepareStatement("SELECT @@identity").executeQuery();  //int nLastIdentityTheSubject = oSet.next() ? oSet.getInt(1) : 0;

               oStatement = oConnection.prepareStatement("INSERT INTO TheSubjectHuman(nID_TheSubject, sTheSubjectHuman, sLastName, sFirstName, sSurName, sDTbirth, sDTdeath, nSex ) "
                       + "VALUES (" + nLastIdentityTheSubject + ",'Человек','Фамилия','Имя','Отчество','1900-11-11 11:11:11','1900-11-11 11:11:11',1)");
               oStatement.executeUpdate();
               
               oSet = oConnection.prepareStatement("SELECT @@identity").executeQuery();
               int nLastIdentityTheSubjectHuman = oSet.next() ? oSet.getInt(1) : 0;

               oStatement = oConnection.prepareStatement("INSERT INTO Access (nID_TheSubjectHuman, sLogin, sPassword, bDisabled ) VALUES (" + nLastIdentityTheSubjectHuman + ",'" + sEmail + "','" + sPassword + "',1)");
               oStatement.executeUpdate();
                     
               oConnection.commit();  //подтверждаем транзакцию БД
              //  AccessDB.transactCommit(oStatement, sCase, oLog);    //Подтверждаем транзакцию   ОБРАЗЕЦ//
              //------------------------------------------------------
                           
               
               
               return "Добро пожаловать на сайт!";     // нельзя менять т.к работает как Колбэк "Учетная запись создана !"
          } catch (Exception e) {
                oLog.error("[" + sCase + "]: Ошибка Регистрации пользователя! Класс Access", e);     //ОБРАЗЕЦ   //так делать всегда!
                oConnection.rollback();     // отменяем транзакцию
                                        //   AccessDB.transactRollback(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
                return "Ошибка Регистрации пользователя!: Класс Access";
          } finally {
               AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ//так делать всегда!
               AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ//так делать всегда!
          }
     }

     
    /**  Заключительный этап после регистрации Юзера - Внести его данные в Сессию,
     *   создать Куку ему и сохранить в Базу
      *  
      * @param sEmail
      * @param sPassword
      * @param oSession
      * @param sIP
      * @return
      * @throws Exception
      */
     
     public String afretRegister (String sEmail, String sPassword, HttpSession oSession, String sIP) throws Exception {
            String sCase = "afretRegister";
             Access A = new Access();              
             Date d = new Date();          // узнаем текущую дату
             DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
             String sTimeLogin = df.format(d);

             String sNID = A.getIdAccess(sEmail);      // Получаем NID пользователя (из Access) по его Емайлу
             if (sNID == null) {
                oLog.error("[" + sCase + "]  :  Ошибка! nID из Access вернулся пустой! Класс Access"); 
                return "Ошибка userRegistration";       // прерываем дальнейший вход на сайт
             }
            // HttpSession session = request.getSession(true);    //создаем сессию для пользователя
             oSession.setAttribute("sEmail", sEmail);
             oSession.setAttribute("sPassword", sPassword);
             

             AccessAuth AA = new AccessAuth();
             String sGenerate = AA.generateString();   // генерируем строку 50 символов для куки
             String sCreateCookie = sNID + "&" + sGenerate;    // соединяем в одну куку
             AA.saveCookieToDB(Integer.parseInt(sNID), sCreateCookie, sTimeLogin, 3);
             
             // Запись в базу инфы о пользователе при попытке его Входа
             AccessOf.saveInfoTryLogined(sEmail, sIP, true); //  true доделать
             
           return sCreateCookie;
      }
     
     
     /**
      *  Возвращает Пароль по Логину
      *  Возвращает "" если такого Пароля у Логина нету 
      * @param sLogin
      * @return
      */
     
     public String getPassword(String sLogin) {
          String sCase = "getPassword";
          String sResult = "";
          ResultSet oSet;
          PreparedStatement oStatement = null;
          Connection oConnection = AccessDB.oConnectionStatic(sCase);
          try {
               oStatement = oConnection.prepareStatement("SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'");
               //oStatement = 
               oSet = oStatement.executeQuery();
               if (oSet.next()) {
                    sResult = oSet.getString(1);
               }

          } catch (Exception oException) {
               oLog.error("[" + sCase + "]:Ошибка получения Пароля!", oException);     //ОБРАЗЕЦ//так делать всегда!
          } finally {
               AccessDB.close(sCase, oStatement);
               AccessDB.closeConnectionStatic(sCase, oConnection);
          }

          if (sResult != null) {       // Если Пароль есть у данного логина в базе
               return sResult;         // отправляем Пароль        
          } else {
               return "";              // "" - такого Пароля нет у Логина"; 
          }
     }

     
     
     /**
      *  Если возвращает true значит такой Емайл в Базе присутствует,
      *  false - значит отсутствует
      * @param sLogin
      * @return
      */
     public boolean bLoginExists(String sLogin) {
          String sCase = "bLoginExists";
          String sResult = "";
          PreparedStatement oStatement = null;
          Connection oConnection = AccessDB.oConnectionStatic(sCase);
          try {
              ResultSet oSet = oConnection.prepareStatement("SELECT TOP 1 sLogin FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
              // ResultSet oSet = oStatement.executeQuery();
               if (oSet.next()) {
                    sResult = oSet.getString(1);    
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
      *   Проверка строки на наличие в ней только символов:
      *   ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-@.
      * @param sVerify
      * @return  True - значит строка содержит только перечисленные символы
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
      *  Проверка Емайла на синтаксическую валидность
      * @param sVerify
      * @return   True - Значит валидный Емайл
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
//               ResultSet oSet = oConnection.prepareStatement("SELECT TOP 1 nID FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
//
//               if (oSet.next()) {
//                    s = oSet.getString(1);
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
