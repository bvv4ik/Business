/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.auth;

import business.AccessDB;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

//import java.net.*; 

/**
 *
 * @author Ser
 */
public class AccessOf {
    

private int nID;
private int nID_Access;
private String sAddress;
private String sDT;
private String sRefer;
private int bAgree;
private String sData;
  
// Setters
 public AccessOf _nID(int i) { nID = i; return this; }
 public AccessOf _nID_Access(int i) { nID_Access = i; return this; }
 public AccessOf _sAddress(String s) { sAddress=s; return this;  }
 public AccessOf _sDT(String s) { sDT=s; return this;  }
 public AccessOf _sRefer(String s) { sRefer=s; return this;  }
 public AccessOf _bAgree(int i) { bAgree = i; return this; }
 public AccessOf _sData(String s) { sData=s; return this;  }
 
 
 // Getters
 public int nID() { return nID; }
 public int nID_Access() { return nID_Access; }
 public String sAddress() { return sAddress; }
 public String sDT() { return sDT; }
 public String sRefer() { return sRefer; }
 public int bAgree() { return bAgree; }
 public String sData() { return sData; }
    
    
     /**
      *  Сохраняем информацию о пользователе при его попытке Входа
      * @param sEmail
      * @param sIP
      * @throws Exception
      */
     public static void saveInfoWhenUserTryLogined(String sEmail, String sIP, boolean bAgree) throws Exception {

      Access Acc = new Access();
      int nID_Access = Acc.getIdAccess(sEmail); // узнаем ИД предыдущей таблицы по Логину

      Date d = new Date();            // определяем текущую дату.
      DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
      String sTime = df.format(d);
      
      Connection oConnection = AccessDB.oConnectionStatic("");
      try {  
           oConnection.prepareStatement("INSERT INTO AccessOf (nID_Access, sAddress, sDT, sRefer, bAgree, sData) "
                   + "VALUES (" + nID_Access + ",'" + sIP + "','" + sTime + "','ссылка откуда...',1,'доп. инф')").executeUpdate();                 //1900-11-11 11:11:11
      } catch (Exception e) {
           // return "Ошибка создания записи БД: Класс AccessAuth";
      } finally {
           AccessDB.closeConnectionStatic("", oConnection);    // так делать всегда!!1
      }



      
      
 
 }
 
 
 
 
 
 
    
    
}
