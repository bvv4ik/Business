    package business.model;

    import business.AccessDB;
    import java.sql.Connection;
    import java.sql.ResultSet;


public class TheSubjectHuman {


private int nID;
private int nID_TheSubject;
private String sTheSubjectHuman;
private String sLastName; 
private String sFirstName;
private String sSurName;
private String sDTbirth;
private String sDTdeath;
private int nSex; 
  

// Setters
 public TheSubjectHuman _nID(int i) { nID = i; return this; }
 public TheSubjectHuman _nID_TheSubject(int i) { nID_TheSubject=i; return this; }
 public TheSubjectHuman _sTheSubjectHuman(String s) { sTheSubjectHuman=s; return this;  }
 public TheSubjectHuman _sLastName(String s) { sLastName=s; return this;  }
 public TheSubjectHuman _sFirstName(String s) { sFirstName=s; return this;  }
 public TheSubjectHuman _sSurName(String s) { sSurName=s; return this;  }
 public TheSubjectHuman _sDTbirth(String s) { sDTbirth=s; return this;  }
 public TheSubjectHuman _sDTdeath(String s) { sDTdeath=s; return this;  }
 public TheSubjectHuman _nSex (int n){  nSex=n; return this; }
 
 // Getters
 public int nID() { return nID; }
 public int nID_TheSubject() { return nID_TheSubject; }
 public String sTheSubjectHuman() { return sTheSubjectHuman; }
 public String sLastName() { return sLastName; }
 public String sFirstName() { return sFirstName; }
 public String sSurName() { return sSurName; }
 public String sDTbirth() { return sDTbirth; }
 public String sDTdeath() { return sDTdeath; }
 public int nSex() { return nSex; }
 

   // загружаем все данные из таблицы по логину 
 public void getTableData (String sLogin) throws Exception   { 
 //String L = ""; 
 Connection oConnection = AccessDB.oConnectionStatic("");
 
 ResultSet oRowset =oConnection.prepareStatement("SELECT * FROM TheSubjectHuman TSH "
         + "LEFT JOIN Access AC ON AC.nID_TheSubjectHuman = TSH.nID where  sLogin = '"+sLogin+"'").executeQuery();
 if(oRowset.next()){
 
 _nID(Integer.parseInt(oRowset.getString(1)));
 _nID_TheSubject(Integer.parseInt(oRowset.getString(2)));
 _sTheSubjectHuman(oRowset.getString(3));
 _sLastName(oRowset.getString(4));
 _sFirstName(oRowset.getString(5));
 _sSurName(oRowset.getString(6));
 _sDTbirth(oRowset.getString(7)) ;  
 _sDTdeath(oRowset.getString(8));
 _nSex(Integer.parseInt(oRowset.getString(9)));
 
   }
 AccessDB.closeConnectionStatic("", oConnection); 
    
}
    

    
public static String getLastName(String sLogin) throws Exception  {
 String L = ""; 
 Connection oConnection = AccessDB.oConnectionStatic("");
 //try{
    
 ResultSet oRowset =oConnection.prepareStatement("SELECT * FROM TheSubjectHuman TSH "
         + "LEFT JOIN Access AC ON AC.nID_TheSubjectHuman = TSH.nID where  sLogin = '"+sLogin+"'").executeQuery();
 if(oRowset.next()){
 L = oRowset.getString(4);
 //First = oRowset.getString(5);
 //Sure = oRowset.getString(6);
 }
 AccessDB.closeConnectionStatic("", oConnection); 
 return L;
  
 }
    
    
    
}
