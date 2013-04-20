/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zLogic;

import com.bw.io.ConnectLdap;
import com.bw.io.ConnectSybase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.servlet.http.HttpSession;
/**
 *
 * @author Ser
 */


public class Registration1  {
    
    
    
    
 public static boolean bLoginExists (String sLogin) {    
    String s = ""; 
    Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
 try{
 
 ResultSet oSet =oDC.prepareStatement("SELECT * FROM Access WHERE sLogin='"+sLogin+"'").executeQuery();
 if(oSet.next()){
        s = oSet.getString(3);   // System.out.println(s);
        }
            if (sLogin.equals(s)) // Если Логин есть в базе
      return true ;//""; //"такой Логин уже есть";       
    }
  
 catch (Exception _){
                    String sErr=_.getMessage();
                    System.err.println("ERROR: "+sErr+"_"+" ---- bLoginExists");//это вывод в лог-файл
                    //return null;//"Ошибка приложения";
                    }
finally {   
          }
  ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
  return false;  //"Логин свободен";
 }
 

 
public static boolean bValidString (String sVerify ) {    

String s = sVerify; //"ascDFMSes"; // проверяемая строка
String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-"; // допустимые символы

for (int i = 0; i < s.length(); i++ ) {
     char c = s.charAt(i);
     if (check.indexOf(String.valueOf(c)) == -1) { //в строке есть неразрешенный символ
       return false;//"В строке содержатся недопустимые символы!";
                      }
                      // System.out.println("OK !");
       }
      return true; // строка валидная
}



public static boolean bValidMail (String sVerify ) {    

final Pattern pattern = Pattern.compile("^[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}");
final Matcher matcher = pattern.matcher(sVerify);
//"mail@sd.com"
if (matcher.find()) 
return true;//"Емаил правильный!"; //Емаил правильный!
else
return false;//""; //Ошибочный!
}



         
 public static String sUserRegistration (String sLogin, String sPassword1, String sPassword2, String sEmail) throws Exception {
  

   if  ( (sLogin==null) | (sPassword1==null) | (sPassword2==null) | (sEmail==null) )
     return "Она или несколько строк Нулл";
 
     if  ( (sLogin.equals("")) | (sPassword1.equals("")) | (sPassword2.equals("")) | (sEmail.equals("")) )  // проверка на пустые строки
  return "Внимание, не все поля формы заполнены!";
     
  //   return "111";
     
    if ((!bValidString(sLogin)) | (!bValidString(sPassword1))) // Если true то прис. недопустимые символы
  return "Логин или пароль содержат недопустимые символы!";

    if (bLoginExists(sLogin)) // если true то логин уже существует в Базе
  return "Логин занят! ";
  
    if (!sPassword1.equals(sPassword2)) //проверка двух полей паролей на идентичность
  return "Поля паролей не совпадают!";       
 
    if ((!bValidMail(sEmail))) // если "" то Емаил Ошибочный
  return "Введиди правильный Е-Маил!"; 
 
  // return "111"; 
  
      Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");                      
  // try{
  
      
       
   // Подготовливаем данные для записи в БД 
      oDC.setAutoCommit(false);
      oDC.prepareStatement("INSERT INTO TheSubject (nID_OfSubject) VALUES (1)").executeUpdate();
   
      //----- oDC.prepareStatement("insert Contact values()").executeUpdate();
      ResultSet oSet2 =oDC.prepareStatement("SELECT @@identity").executeQuery();
      int n=oSet2.next()?oSet2.getInt(1):0;
      //System.out.println("----------------------------");   System.out.println(n);
    
      oDC.prepareStatement("INSERT INTO TheSubjectHuman(nID_TheSubject, sTheSubjectHuman, sLastName, sFirstName, sSurName, sDTbirth, sDTdeath, nSex ) " +
      "VALUES ("+n+",'Человек','Фамилия','Имя','Отчество','1900-11-11 11:11:11','1900-11-11 11:11:11',1)").executeUpdate();

      ResultSet oSet3 =oDC.prepareStatement("SELECT @@identity").executeQuery();
      int n1=oSet3.next()?oSet3.getInt(1):0;
      //System.out.println("----------------------------");   System.out.println(n1);
       
      oDC.prepareStatement("INSERT INTO Access (nID_TheSubjectHuman, sLogin, bDisabled ) VALUES ("+n1+",'"+sLogin+"',1)").executeUpdate();

      // пробуем записать Логин и Пароль в Лдап
      if (!ConnectLdap.bWrite(sLogin, sPassword1)) // если false занчит ошибка подключения к Лдап
      {
          oDC.rollback();
          return "Ошибка Лдап подключения, учетная запись не создана!";
      }

      // Если записалось в Лдап, то подтверждаем запись в БД
      oDC.commit();
     
    //  ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
      ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
      
      return "Учетная запись успешно создана!";  
              
 }   
// catch (Exception _){
     //               String sErr=_.getMessage();
     //               System.err.println("ERROR: "+sErr+"_"+" ---- UserRegistration");//это вывод в лог-файл
       //             oDC.rollback();
       //             return "Ошибка приложения , запись не создана !" + "---- UserRegistration" ; 
       //             }
//finally {           } 
   
  // */
   
   
// }
 

     
   
 
    //- добавить пользоватетя,
 public static void AddLoginPasswordMailUser(String sLogin, String sPassword, String sEmail, String sReturn) {
 String s1;
    try
 {
 if (!sLogin.equals("")) // если логин не равен пустой строке
 if (!sPassword.equals("")) // если пароль не равен пустой строке
 if (!sEmail.equals("")) // если Емаил не равен пустой строке
 {
 Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
 oDC.prepareStatement("INSERT INTO Access (nID_SubjectHuman, sLogin, bDisabled ) VALUES (2,'"+sLogin+"',1)").executeUpdate();
 
 ResultSet oSet =oDC.prepareStatement("SELECT @@identity").executeQuery();
 int n=oSet.next()?oSet.getInt(1):0;
 
 
 s1 = "begin transaction " +
      "INSERT INTO Access (nID_SubjectHuman, sLogin, bDisabled ) VALUES (3,'"+sLogin+"',1) " +

      "INSERT INTO TheSubjectHuman(nID_TheSubject, sTheSubjectHuman, sLastName, sFirstName, sSurName, sDTbirth, sDTdeath, nSex ) " +
      "VALUES (2,'Человек3','df','fd','df','2012.11.10','2012.11.13',1) " +

      "commit transaction";

 //ResultSet oSet =oDC.prepareStatement("SELECT @@identity").executeQuery();
 //int n=oSet.next()?oSet.getInt(1):0;
 //и в "n" у тебя будет твой индекс

 //oDC.prepareStatement(s1).executeUpdate();;
  
  System.out.println("----------------------------");
  System.out.println(n);

  ConnectSybase.closeConnect("UA_DP_PGASA",oDC);
  }
 
 }
 catch (Exception _){
                    String sErr=_.getMessage();
                    System.err.println("ERROR: "+sErr+"_"+"AddLoginPasswordMailUser");//это вывод в лог-файл
                    }
 
}
 
 

 

 
 
 

 
 
 
 
}


