/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

import com.bw.io.ConnectLdap;
import com.bw.io.ConnectSybase;
import java.sql.Connection;


import com.bw.io.ConnectSybase;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;



public class Access  {
 
//public static HttpServletRequest request;   
    
//public static int nID;
private int nID;
private int nID_SubjectHuman;
private String sLogin; //(уникально)
private String sPassword; //(уникально)
private int bDisabled; //(вырубить доступ)
  
// Setters
 public Access _nID(int i) { nID = i; return this; }
 public Access _nID_SubjectHuman(int i) { nID_SubjectHuman=i; return this; }
 public Access _sLogin(String s) { sLogin=s; return this;  }
 public Access sPassword(String s) { sPassword=s; return this;  }
 public Access _bDisabled (int b){  bDisabled=b; return this; }
 
 // Getters
 public int nID() { return nID; }
 public int nID_SubjectHuman() { return nID_SubjectHuman; }
 public String sLogin() { return sLogin; }
 public String sPassword() { return sPassword; }
 public int bDisabled(){ return bDisabled; }
 
 //================================================
 
 
 

   // загружаем все данные из таблицы по логину 
 public int getIdAccess (String sLogin) throws Exception   { 
 int i = 0;
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
 
 ResultSet oSet =oDC.prepareStatement("SELECT TOP 1 nID FROM Access where sLogin = '"+sLogin+"'").executeQuery();
 if(oSet.next()){
 i = oSet.getInt(1);
 //_nID(Integer.parseInt(oSet.getString(1)));
 //_nID_SubjectHuman(Integer.parseInt(oSet.getString(2)));
 //_sLogin(oSet.getString(3));
 //_bDisabled(Integer.parseInt(oSet.getString(4)));
   }
 
 ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
 return i;
 
}
 
 
 
 

 public static String userRegistration (String sEmail, String sPassword, String sPassword2) throws Exception {
  
 
// HttpSession session = request.getSession(true);  
//   Object o = session.getAttribute("sLogin");

   if  ( (sEmail==null) | (sPassword==null) | (sPassword2==null)/* | (sEmail==null)*/ )
        return "Одна или несколько строк Null";
 
     if  ( (sEmail.equals("")) | (sPassword.equals("")) | (sPassword2.equals("")) /* | (sEmail.equals(""))*/ )  // проверка на пустые строки
  return "Внимание, не все поля формы заполнены!";
   
//     if ((!bValidMail(sEmail))) // если "" то Емаил Ошибочный
//  return "Введите правильный Е-Маил!"; 
     
    if ((!bValidString(sEmail)) | (!bValidString(sPassword))) // Если true то прис. недопустимые символы
  return "Логин или Пароль содержат недопустимые символы!";

     if (!sPassword.equals(sPassword2)) //проверка двух полей паролей на идентичность
  return "Поля паролей не совпадают!";       
     
     if (bLoginExists(sEmail)) // если true то логин уже существует в Базе
    return "Этот Логин уже занят! ";
   
     
 
    

      Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");                       
   try{
     
   // Подготовливаем данные для записи в БД 
      oDC.setAutoCommit(false);
      oDC.prepareStatement("INSERT INTO TheSubject (nID_OfSubject) VALUES (1)").executeUpdate();
      // вставляем по умолчанию запись "1" т.е "человек"
      
      //----- oDC.prepareStatement("insert Contact values()").executeUpdate();
      ResultSet oSet2 = oDC.prepareStatement("SELECT @@identity").executeQuery();
      int n = oSet2.next()  ?  oSet2.getInt(1)  :  0 ;
      //System.out.println("----------------------------");   System.out.println(n);
    
      oDC.prepareStatement("INSERT INTO TheSubjectHuman(nID_TheSubject, sTheSubjectHuman, sLastName, sFirstName, sSurName, sDTbirth, sDTdeath, nSex ) " +
      "VALUES ("+n+",'Человек','Фамилия','Имя','Отчество','1900-11-11 11:11:11','1900-11-11 11:11:11',1)").executeUpdate();

      ResultSet oSet3 =oDC.prepareStatement("SELECT @@identity").executeQuery();
      int n1 = oSet3.next() ? oSet3.getInt(1)  :  0;
      //System.out.println("----------------------------");   System.out.println(n1);
       
      oDC.prepareStatement("INSERT INTO Access (nID_TheSubjectHuman, sLogin, sPassword, bDisabled ) VALUES ("+n1+",'"+sEmail+"','"+sPassword+"',1)").executeUpdate();
      ResultSet oSet4 =oDC.prepareStatement("SELECT @@identity").executeQuery();
      int n2 = oSet4.next() ? oSet4.getInt(1)  :  0;

      
      //подтверждаем запись в БД
      oDC.commit();
     
     // ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
      
      
      
      return "Учетная запись создана !";  // нельзя менять т.к работает как Колбэк
 
   }catch (Exception e){
          return "Ошибка создания записи: Класс Access";  
    }
   finally{
    ConnectSybase.closeConnect("UA_DP_PGASA",oDC);  // так делать всегда!!1
   }
      
    
 } 
 
 
    
public static String getPassword (String sLogin) {    
String s = "";
        Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
        try {
            ResultSet oSet = oDC.prepareStatement("SELECT TOP 1 sPassword FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
            if (oSet.next()) {
                s = oSet.getString(1);   
            }
            if ( s != null) { // Если Пароль есть у данного логина в базе
                return s;        // отправляем Пароль        
            }
            ConnectSybase.closeConnect("UA_DP_PGASA", oDC);
        } catch (Exception _) {
            String sErr = _.getMessage();
            System.err.println("ERROR: " + sErr + "_" + " ---- bLoginExists");   //это вывод в лог-файл
            //return null;//"Ошибка приложения";
        } finally {      //ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
        }
        return "";  // "" - такого Пароля нет у Логина"; 
}
     
    public static boolean bLoginExists(String sLogin) {
        String s = "";
        Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
        try {
            ResultSet oSet = oDC.prepareStatement("SELECT TOP 1 sLogin FROM Access WHERE sLogin='" + sLogin + "'").executeQuery();
            if (oSet.next()) {
                s = oSet.getString(1);   // System.out.println(s);
            }
            if ((s != "") && (s != null) && (sLogin.equals(s))) { // Если Логин есть в базе
                return true;        //" true - такой Логин уже занят";       
            }
            
        } catch (Exception _) {
            String sErr = _.getMessage();
            System.err.println("ERROR: " + sErr + "_" + " ---- bLoginExists");   //это вывод в лог-файл
            //return null;//"Ошибка приложения";
        } finally {      //ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
             ConnectSybase.closeConnect("UA_DP_PGASA", oDC);
        }
        return false;  //"false - Логин свободен";
    }





 
private static boolean bValidString (String sVerify ) {    

String s = sVerify; //"ascDFMSes"; // проверяемая строка
String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-@."; // допустимые символы

for (int i = 0; i < s.length(); i++ ) {
     char c = s.charAt(i);
     if (check.indexOf(String.valueOf(c)) == -1) { //в строке есть неразрешенный символ
       return false;//"В строке содержатся недопустимые символы!";
                      }
                      // System.out.println("OK !");
       }
      return true; // строка валидная
}



public static boolean bValidMail (String sVerify) {    

final Pattern pattern = Pattern.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
final Matcher matcher = pattern.matcher(sVerify);
//"mail@sd.com"
if (matcher.find()) 
return true;//"Емаил правильный!"; //Емаил правильный!
else
return false;//""; //Ошибочный!
}

 
 
 
 
 public void save()
 {  }
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
 boolean bAuthorized(String sLogin, String sPassword){
 //boolean b = false;
      
 return true;
 }
 
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



        
