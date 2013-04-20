/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

import com.bw.entity.Access;
import com.bw.io.ConnectSybase;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Ser
 */
public class Place {
    
    
private int nID;
private int nID_Contact;
private int nID_Product;
private int nID_PlaceCell;
private int nID_PlacePart;
private int nID_PlaceBuild;
private String sPlace; 

// Setters
 public Place _nID(int i) { nID = i; return this; }
 public Place _nID_Contact(int i) { nID_Contact=i; return this; }
 public Place _nID_Product(int i) { nID_Product=i; return this; }
 public Place _nID_PlaceCell(int i) { nID_PlaceCell=i; return this; }
 public Place _nID_PlacePart(int i) { nID_PlacePart=i; return this; }
 public Place _nID_PlaceBuild(int i) { nID_PlaceBuild=i; return this; }
 public Place _sPlace(String s) { sPlace=s; return this;  }
  
 // Getters
 public int nID() { return nID; }
 public int nID_Contact() { return nID_Contact; }
 public int nID_Product() { return nID_Product; }
 public int nID_PlaceCell() { return nID_PlaceCell; }
 public int nID_PlacePart() { return nID_PlacePart; }
 public int nID_PlaceBuild() { return nID_PlaceBuild; }
 public String sPlace() { return sPlace; }
    
    
    public void save(String nID_Cell, String nID_Part, String nID_Build) throws Exception {

        // 1.Получаем индекс юзера.
        Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
        // ResultSet oSet = oDC.prepareStatement("SELECT nID FROM PlaceCountry where sCountry = '"+sCountry+"'").executeQuery();
        //int n=oSet.next()?oSet.getInt(1):0; 
        int n = 1; //индекс юзера, для тета "1";
        oDC.prepareStatement("UPDATE Place SET nID_PlaceCell = " + nID_Cell + " where Place.nID = " + n).executeUpdate();
        oDC.prepareStatement("UPDATE Place SET nID_PlacePart = " + nID_Part + " where Place.nID = " + n).executeUpdate();
        oDC.prepareStatement("UPDATE Place SET nID_PlaceBuild = " + nID_Build + " where Place.nID = " + n).executeUpdate();

        ConnectSybase.closeConnect("UA_DP_PGASA", oDC);

    }
 public void setPlace1 (String sLogin) throws Exception   { 
 
 //  TheSubject T = new TheSubject();
  //  T.load(sLogin);
   // int i = T.nID();   
 
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
    
    // ResultSet oSet = oDC.prepareStatement("SELECT nID FROM PlaceCountry where sCountry = '"+sCountry+"'").executeQuery();
     //int n=oSet.next()?oSet.getInt(1):0; 
 int n = 1;
     //oDC.prepareStatement("UPDATE Place1 SET nID_TheSubject = "+i+" where PlaceCountry.nID = "+n+" ").executeUpdate();
     
 int s1 = 1; // nID_Contact INT,

 String s2 = "''";  //sCountry VARCHAR(150),  -- Страна
 String s3 = "''";  //sRegion VARCHAR(150),           -- Регион, область, штат
 String s4 = "''";  //sPolis VARCHAR(150),            -- название места: города,села, ПГТ и т.д.: Киев,Одесса и т.д.
 String s5 = "''";  //sArea VARCHAR(150),             -- район: Бабушкинский, Кировский
 String s6 = "''";  //sBranchType VARCHAR(150),       -- тип: Улица, Проспект, Переулок, Бульвар, Тупик, Площадь и т.д.
 String s7 = "''";  //sBranch VARCHAR(150),           -- ветка: название улицы, проспекта, переулка и т.д.
 String s8 = "''";  //sBuildType VARCHAR(150),        -- тип строения: Дом-Частный,Дом-Многоквартирный,Общежитие,Здание-Нежилое
 String s9 = "''";  //sCellType VARCHAR(150),         -- тип части строения: комната, квартира, подвал, чердак, крыша)

 int s10 = 0; //nLevels INT,                   -- (уровней(этажей), опционально)
 int s11 = 0; //nParts INT,                    --(частей(подъездов), опционально)
 String s12 = "''"; //sBuild  VARCHAR(20),           --(номер(+буква)): 2а,14,125б...)
 String s13 = "''"; //sBuildBlock  VARCHAR(20),      --(блок(корпус), опционально)
 int s14 = 0; //nCellAt INT,                   --(начальный номер помещения)
 int s15 = 0; //nCellTo INT,                   --(конечный номер помещения)
 int s16 = 0; //nPart INT,                     --(номер подъезда)
 String s17 = "''"; //sPartInfo VARCHAR(150),        --(опционально, код подъезда, пояснение)
 double s18 = 0.1; //dX REAL,                       --(Latitude,широта)
 double s19 = 0.1; //dY REAL,                       --(Longtitude,долгота)
 double s20 = 0.1; //dZ REAL,                       --(Atitude,долгота)
 int s21 = 0; //nLevel INT,                    --(уровень(этаж), опционально)
 int s22 = 0; //nCell INT,                     --(номер квартиры, комнаты и т.д.: 13,26)
 String s23 = "''"; //sCell VARCHAR(10),             --(буква к номеру квартиры/комнаты: а,б)            

//oDC.prepareStatement("INSERT Place1 " +
oDC.prepareStatement("INSERT INTO Place1 (nID_Contact, sCountry, sRegion, sPolis, sArea, sBranchType, sBranch, sBuildType, sCellType, nLevels, nParts, sBuild, sBuildBlock, nCellAt, nCellTo, nPart, sPartInfo, dX, dY, dZ, nLevel, nCell, sCell) " + 
         
         //"VALUES ("+s1+","+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8+","+s9+","+s10+","+s11+","+s12+","+s13+","+s14+","+s15+","+s16+","+s17+","+s18+","+s19+","+s20+","+s21+","+s22+","+s23+")").executeUpdate();
 "VALUES (1,'страна','регион','место','район','тип ул','назв ул','тип стр','тип час стр',0,0,'ном и буква','блок',0,0,0,'код',0,0,0,0,0,'буква' )").executeUpdate();
  
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
 //   INSERT INTO Place1 (nID_Contact, sCountry, sRegion, sPolis, sArea, sBranchType, sBranch, sBuildType, sCellType, nLevels, nParts, sBuild, sBuildBlock, nCellAt, nCellTo, nPart, sPartInfo, dX, dY, dZ, nLevel, nCell, sCell) 
 //         VALUES (1,           'страна', 'регион','место','район','тип ул',  'назв ул','тип стр', 'тип час стр',0,     0,      'ном и буква','блок',0,0,0,'код',0,0,0,0,0,'буква')
     
 }
 
 
 


 
 
  public void setPlace (String sLogin, String sCountry, String sRegion) throws Exception   { 
 
   TheSubject T = new TheSubject();
    T.load(sLogin);
    int i = T.nID();   
 
     Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
    
     ResultSet oSet = oDC.prepareStatement("SELECT nID FROM PlaceCountry where sCountry = '"+sCountry+"'").executeQuery();
     int n=oSet.next()?oSet.getInt(1):0; 
 
     oDC.prepareStatement("UPDATE PlaceCountry SET nID_TheSubject = "+i+" where PlaceCountry.nID = "+n+" ").executeUpdate();
     
     ConnectSybase.closeConnect("UA_DP_PGASA",oDC); 
    
 }

 
}
