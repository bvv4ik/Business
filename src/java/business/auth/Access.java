
package business.auth;


import business.AccessDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
public class Access {

//public static HttpServletRequest request;   
//public static int nID;
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

     // загружаем все данные из таблицы по логину 
     public int getIdAccess(String sLogin) throws Exception {
          String sCase = "getIdAccess"; //ОБРАЗЕЦ//так делать всегда!
          int i = 0;
          Connection oConnection = AccessDB.oConnectionStatic(sCase); //ОБРАЗЕЦ//так делать всегда!
          PreparedStatement oStatement = null;
          //AccessDB oAccessDB=new AccessDB(); //ОБРАЗЕЦ
          try {
               oStatement = oConnection.prepareStatement("SELECT TOP 1 nID FROM Access where sLogin = '" + sLogin + "'");

               //AccessDB.transactBegin(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
               ResultSet oSet = oStatement.executeQuery();
               if (oSet.next()) {
                    i = oSet.getInt(1);
                    //_nID(Integer.parseInt(oSet.getString(1)));
                    //_nID_SubjectHuman(Integer.parseInt(oSet.getString(2)));
                    //_sLogin(oSet.getString(3));
                    //_bDisabled(Integer.parseInt(oSet.getString(4)));
               }
               //AccessDB.transactCommit(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
          } catch (Exception oException) {
               oLog.error("[" + sCase + "](i=" + i + "):Непредвиденная ошибка создания записи!", oException); //ОБРАЗЕЦ//так делать всегда!
               //AccessDB.transactRollback(oStatement, sCase, oLog);    //ОБРАЗЕЦ//
          } finally {
               AccessDB.close(sCase, oStatement);    //ОБРАЗЕЦ//так делать всегда!
               AccessDB.closeConnectionStatic(sCase, oConnection);    //ОБРАЗЕЦ//так делать всегда!
               return i;
          }
     }

     public String userRegistration(String sEmail, String sPassword) throws Exception {

                              //HttpSession session = request.getSession(true);  
                              //   Object o = session.getAttribute("sLogin");

          /* основная проверка введенных пользователем данных осуществляется на клиенте через JS, 
           эта проверка данных на всякий случай размещена на сервере, чтобы отключив JS, нельзя было отправить прямой 
           запрос и зарегистрироватся с неверными данными иди создать ошибку, когда в базу например будет внесена запись с
           запрещенными символами и из-за этого она заглчит.  
           */

          if ((sEmail == null) | (sPassword == null)) {
               return "Одна или несколько строк Null";
          } else if ((sEmail.equals("")) | (sPassword.equals(""))) {// проверка на пустые строки
               return "Внимание, заполните поля!";
          } else if ((!bValidString(sEmail)) | (!bValidString(sPassword))) {// Если true то прис. недопустимые символы 
               return "Логин или Пароль содержат недопустимые символы!";
          } else if (sPassword.length() < 10) {//проверка длинны пароля
               return "Длинна пароля должна быть больше 10 символов!";
          } else if (bLoginExists(sEmail)) {// если true то логин уже существует в Базе
               return "Этот Логин уже занят!";
          }

//     if ((!bValidMail(sEmail))) // если "" то Емаил Ошибочный  //  return "Введите правильный Е-Маил!"; 
//     if (!sPassword.equals(sPassword2)) //проверка двух полей паролей на идентичность   //  return "Поля паролей не совпадают!";            


          Connection oConnection = AccessDB.oConnectionStatic("");
          try {

               // Подготовливаем данные для записи в БД 
               oConnection.setAutoCommit(false);
               oConnection.prepareStatement("INSERT INTO TheSubject (nID_OfSubject) VALUES (1)").executeUpdate();
               // вставляем по умолчанию запись "1" т.е "человек"

               ResultSet oSet = oConnection.prepareStatement("SELECT @@identity").executeQuery();
               int n = oSet.next() ? oSet.getInt(1) : 0;

               oConnection.prepareStatement("INSERT INTO TheSubjectHuman(nID_TheSubject, sTheSubjectHuman, sLastName, sFirstName, sSurName, sDTbirth, sDTdeath, nSex ) "
                       + "VALUES (" + n + ",'Человек','Фамилия','Имя','Отчество','1900-11-11 11:11:11','1900-11-11 11:11:11',1)").executeUpdate();

               oSet = oConnection.prepareStatement("SELECT @@identity").executeQuery();
               int n1 = oSet.next() ? oSet.getInt(1) : 0;

               oConnection.prepareStatement("INSERT INTO Access (nID_TheSubjectHuman, sLogin, sPassword, bDisabled ) VALUES (" + n1 + ",'" + sEmail + "','" + sPassword + "',1)").executeUpdate();
               oSet = oConnection.prepareStatement("SELECT @@identity").executeQuery();
              // int n2 = oSet.next() ? oSet.getInt(1) : 0;


               oConnection.commit();  //подтверждаем запись в БД

               return "Добро пожаловать на сайт!";     // нельзя менять т.к работает как Колбэк "Учетная запись создана !"

          } catch (Exception e) {
               return "Непредвиденная ошибка создания записи: Класс Access";
          } finally {
               AccessDB.closeConnectionStatic("", oConnection);  // так делать всегда!!1
          }


     }

     public String getPassword(String sLogin) {
          String sCase = "getPassword";
          String s = "";
          Connection oConnection = AccessDB.oConnectionStatic("");
          try {
               ResultSet oSet = oConnection.prepareStatement("SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
               if (oSet.next()) {
                    s = oSet.getString(1);
               }
               if (s != null) { // Если Пароль есть у данного логина в базе
                    return s;        // отправляем Пароль        
               }

          } catch (Exception oException) {
               oLog.error("[" + sCase + "]:Ошибка создания записи!", oException); //ОБРАЗЕЦ//так делать всегда!
               //String sErr = _.getMessage();
//            System.err.println("ERROR: " + sErr + "_" + " ---- bLoginExists");   //это вывод в лог-файл
               //return null;//"Ошибка приложения";
          } finally {      //AccessDB.closeConnectionStatic("", oConnection);
               AccessDB.closeConnectionStatic("", oConnection);
          }
          return "";  // "" - такого Пароля нет у Логина"; 
     }

     public String getNID(String sLogin) {
          String s = "";
          Connection oConnection = AccessDB.oConnectionStatic("");
          try {
               ResultSet oSet = oConnection.prepareStatement("SELECT TOP 1 nID FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
               if (oSet.next()) {
                    s = oSet.getString(1);
               }
               if (s != null) { // Если nID есть у данного логина в базе
                    return s;        // отправляем nID        
               }

          } catch (Exception _) {
               String sErr = _.getMessage();
               System.err.println("ERROR: " + sErr + "_" + " ---- getNID");   //это вывод в лог-файл

          } finally {
               AccessDB.closeConnectionStatic("", oConnection);
          }
          return "";  // "" - такого nID нет у Логина"; 
     }

     public boolean bLoginExists(String sLogin) {
          String s = "";
          Connection oConnection = AccessDB.oConnectionStatic("");
          try {
               ResultSet oSet = oConnection.prepareStatement("SELECT TOP 1 sLogin FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
               if (oSet.next()) {
                    s = oSet.getString(1);   // System.out.println(s);
               }
               if ((s != "") && (s != null) && (sLogin.equals(s))) { // Если Логин есть в базе
                    return true;        //" true - такой Логин уже занят";       
               } else {
                    return false;
               }

          } catch (Exception _) {
               String sErr = _.getMessage();
               System.err.println("ERROR: " + sErr + "_" + " ---- bLoginExists");   //это вывод в лог-файл
               //return null;//"Ошибка приложения";
          } finally {
               AccessDB.closeConnectionStatic("", oConnection);
          }
          return false;  //"false - Логин свободен";
     }

     public boolean bValidString(String sVerify) {

          String s = sVerify; //"ascDFMSes"; // проверяемая строка
          String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-@."; // допустимые символы

          for (int i = 0; i < s.length(); i++) {
               char c = s.charAt(i);
               if (check.indexOf(String.valueOf(c)) == -1) { //в строке есть неразрешенный символ
                    return false;//"В строке содержатся недопустимые символы!";
               }
               // System.out.println("OK !");
          }
          return true; // строка валидная
     }

     public boolean bValidMail(String sVerify) {

          final Pattern pattern = Pattern.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
          final Matcher matcher = pattern.matcher(sVerify);
//"mail@sd.com"
          if (matcher.find()) {
               return true;//"Емаил правильный!"; //Емаил правильный!
          } else {
               return false;//""; //Ошибочный!
          }
     }

     public void save() {
     }
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
