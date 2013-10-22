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
    
    
 public static void saveInfoWhenUserLogined(String sEmail) throws Exception {
     
  
      Access Acc = new Access();
      int nID_Access = Acc.getIdAccess(sEmail); // узнаем ИД предыдущей таблицы по Логину
      //int i = Acc.nID();

      Date d = new Date();            // определяем текущую дату.
      DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
      String sTime = df.format(d);

      Connection oConnection = AccessDB.oConnectionStatic("");
      oConnection.prepareStatement("INSERT INTO AccessOf (nID_Access, sAddress, sDT, sRefer, bAgree, sData) "
              + "VALUES (" + nID_Access + ",'Ip....','" + sTime + "','ссылка откуда...',1,'доп. инф')").executeUpdate();
      //1900-11-11 11:11:11

      AccessDB.closeConnectionStatic("", oConnection);


      
      
 
 }
 
 
 
 
 
 
    
    
}
