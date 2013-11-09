package business.model;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class PlacePolis {

     private Logger oLog = Logger.getLogger(getClass());
     private int nID;
     private int nID_PlaceRegion;
     private String sPolis;

// Setters
     public PlacePolis _nID(int i) {
          nID = i;
          return this;
     }

     public PlacePolis _nID_PlaceRegion(int i) {
          nID_PlaceRegion = i;
          return this;
     }

     public PlacePolis _sPolis(String s) {
          sPolis = s;
          return this;
     }

     // Getters
     public int nID() {
          return nID;
     }

     public int nID_PlaceRegion() {
          return nID_PlaceRegion;
     }

     public String sPolis() {
          return sPolis;
     }
     //public String[] sArr ;
     public static ArrayList<String[]> aResult = new ArrayList<String[]>();
     public static ArrayList<String> aResult2 = new ArrayList<String>();
     public static ArrayList<String> aResult3 = new ArrayList<String>();
     public static String sObject = "";
     //Конструкторы
     //public PlacePolis(String sPolis) throws Exception {
     //   setPolis(sPolis) ;
     //};
     //public PlacePolis() { };

     // Определяем nID Города по выбранному названию     
//  public int getID (String sPolis) throws Exception   { 
//   int i = 0;
//     Connection oConnection = AccessDB.oConnectionStatic("");    
//     ResultSet oRowset = oConnection.prepareStatement("SELECT * FROM PlacePolis1 where sPolis = '"+sPolis +"'").executeQuery();
//     if (oRowset.next()){
//        i = oRowset.getInt(1);
//        }
//     AccessDB.closeConnectionStatic("", oConnection);
//     return i;
//  }
     
     
      public static void main(String[] args) throws Exception {
       // ArrayList<String> oAlist = new ArrayList<String>();
         
        PlacePolis pp = new PlacePolis();
        String s = pp.getStringAddressPolis("ДН", 1);

        System.out.println(s);
      }
     
     
     
// Получаем список всех городов по одному выбранному региону 
     public String getStringAddressPolis(String sPolis, int nID_PlaceCountry)  {
           DOMConfigurator.configure("D:/My Documents/NetBeansProjects/Business/web/WEB-INF/config/log4j.xml");
          String sCase = "getStringAddressPolis";
          String s = "";
          String s2 = "";
          int i_count = 1;

          aResult.clear();

          Connection oConnection = null;
          Statement oStatement = null;
          //Connection oConnection = AccessDB.oConnectionStatic("");
          //ResultSet oRowset = oConnection.prepareStatement(
          try {
               oConnection = AccessDB.oConnectionStatic(sCase);
               oStatement = AccessDB.oStatementStatic(oConnection, sCase);
               ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase,
                       " SELECT sPRT6=PRT6.sRegionType,  sPR6=PR6.sRegion, sPPT66=PPT66.sPolisType, sPPP6=PPP6.sPolis, "
                       + " sPRT5=PRT5.sRegionType,  sPR5=PR5.sRegion, sPPT55=PPT55.sPolisType, sPPP5=PPP5.sPolis, "
                       + " sPRT4=PRT4.sRegionType,  sPR4=PR4.sRegion, sPPT44=PPT44.sPolisType, sPPP4=PPP4.sPolis, "
                       + " sPRT3=PRT3.sRegionType,  sPR3=PR3.sRegion, sPPT33=PPT33.sPolisType, sPPP3=PPP3.sPolis, "
                       + " sPRT2=PRT2.sRegionType,  sPR2=PR2.sRegion, sPPT22=PPT22.sPolisType, sPPP2=PPP2.sPolis, "
                       + " sPRT1=PRT1.sRegionType,  sPR1=PR1.sRegion, sPPT11=PPT11.sPolisType, sPPP1=PPP1.sPolis, "
                       + " sPPT1=PPT1.sPolisType,  PP.sPolis,  PP.nID "
                       //                "SELECT  PP.sPolis,  PP.nID, sPPT1=PPT1.sPolisType, sPR1=PR1.sRegion " 

                       + "  FROM PlacePolis PP  "
                       + "left JOIN PlacePolisType PPT1 ON PPT1.nID = PP.nID_PlacePolisType  " // только 1 раз для типа полиса 

                       + "left JOIN PlaceRegion PR1 ON PR1.nID = (SELECT nID_PlaceRegion  FROM PlaceRegionTree where nID_PlaceRegion = PP.nID_PlaceRegion) "
                       + "left JOIN PlaceRegionType PRT1 ON PRT1.nID = PR1.nID_PlaceRegionType " //  -- тип 1-го региона 
                       + "left JOIN PlacePolis PPP1 ON PPP1.nID = PR1.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT11 ON PPT11.nID = PPP1.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR2 ON PR2.nID = (SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR1.nID) "
                       + "left JOIN PlaceRegionType PRT2 ON PRT2.nID = PR2.nID_PlaceRegionType  " //-- тип 2-го региона 
                       + "left JOIN PlacePolis PPP2 ON PPP2.nID = PR2.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT22 ON PPT22.nID = PPP2.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR3 ON PR3.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR2.nID) "
                       + "left JOIN PlaceRegionType PRT3 ON PRT3.nID = PR3.nID_PlaceRegionType  " //-- тип 3-го региона 
                       + "left JOIN PlacePolis PPP3 ON PPP3.nID = PR3.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT33 ON PPT33.nID = PPP3.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR4 ON PR4.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR3.nID) "
                       + "left JOIN PlaceRegionType PRT4 ON PRT4.nID = PR4.nID_PlaceRegionType  " // -- тип 4-го региона 
                       + "left JOIN PlacePolis PPP4 ON PPP4.nID = PR4.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT44 ON PPT44.nID = PPP4.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR5 ON PR5.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR4.nID) "
                       + "left JOIN PlaceRegionType PRT5 ON PRT5.nID = PR5.nID_PlaceRegionType  " //-- тип 5-го региона 
                       + "left JOIN PlacePolis PPP5 ON PPP5.nID = PR5.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT55 ON PPT55.nID = PPP5.nID_PlacePolisType " //-- тип полиса на замену региона 

                       + "left JOIN PlaceRegion PR6 ON PR6.nID = ( SELECT  nID_PlaceRegion_Node  FROM PlaceRegionTree where nID_PlaceRegion = PR5.nID) "
                       + "left JOIN PlaceRegionType PRT6 ON PRT6.nID = PR6.nID_PlaceRegionType " //-- тип 6-го региона 
                       + "left JOIN PlacePolis PPP6 ON PPP6.nID = PR6.nID " //-- полис на замену региона 
                       + "left JOIN PlacePolisType PPT66 ON PPT66.nID = PPP6.nID_PlacePolisType " //-- тип полиса на замену региона  

                       //+ "where  PP.sPolis LIKE \'"+sPolis+"%\' " 
                       + "where PR1.nID_PlaceCountry = " + nID_PlaceCountry + " and  PP.sPolis LIKE \'" + sPolis + "%\' ", oLog);

               while (oRowset.next() & i_count < 100) { // не загружаем больше 100 записей
                    i_count++;
                    ////// s += (  ",\"a"+ i +"\":" + "\"" +oRowset.getString(1) + "\"");
//1 2 3 4
//null null null null 
//5 6 7 8
//обл. ПОЛТАВСЬКА null null 
//9 10 11 12
//г.п РАЙОНИ ПОЛТАВСЬКОЇ ОБЛАСТІ null null 
//13 14 15 16
//р-н. ПИРЯТИНСЬКИЙ null null 
//17 18 19 20
//г.п МІСТА РАЙОННОГО ПІДПОРЯДКУВАННЯ ПИРЯТИНСЬКОГО Р-НУ null null 
//21 22 23 24
//м.р. ПИРЯТИН м. ПИРЯТИН 
//25 26 27
//с. ЗАМОСТИЩЕ 26466        


                    String[] sArr2 = {oRowset.getString(1), oRowset.getString(2), oRowset.getString(3), oRowset.getString(4),
                         oRowset.getString(5), oRowset.getString(6), oRowset.getString(7), oRowset.getString(8),
                         oRowset.getString(9), oRowset.getString(10), oRowset.getString(11), oRowset.getString(12),
                         oRowset.getString(13), oRowset.getString(14), oRowset.getString(15), oRowset.getString(16),
                         oRowset.getString(17), oRowset.getString(18), oRowset.getString(19), oRowset.getString(20),
                         oRowset.getString(21), oRowset.getString(22), oRowset.getString(23), oRowset.getString(24),
                         oRowset.getString(25), oRowset.getString(26), oRowset.getString(27)
                    };

                    aResult.add(sArr2);

               }
              // AccessDB.closeConnectionStatic("", oConnection);



               // String s = "";
               // String s1 = "";

               //String[] sArr3

               sObject = "";

               aResult2.clear();

               for (int i = 0; i <= aResult.size() - 1; i++) {
                    String[] sArr = aResult.get(i);

                    s = "";

                    //for (String temp: aResult_PlaceRegionTree){ System.out.println(temp); }
                    //    System.out.println( sArr[0]+" "+sArr[1]+" "+sArr[2]+" "+sArr[3]+"   "+sArr[4]+" "+sArr[5]+" "+sArr[6]+" "+sArr[7] ); 


                    if ((sArr[0] != null)
                            & (!"г.п".equals(sArr[0]))) {
                         if (sArr[2] != null) { // если есть замена
                              s.concat( s + "  " + sArr[2] + " " + sArr[3]); // если есть замена
                         } else {
                              s.concat( s + "  " + sArr[0] + " " + sArr[1]); // если нет замены
                         }
                    }

                    if ((sArr[4] != null)
                            & (!"г.п".equals(sArr[4]))) {
                         if (sArr[6] != null) { // если есть замена
                              s.concat( s + "  " + sArr[6] + " " + sArr[7]); // если есть замена
                         } else {
                              s.concat( s + "  " + sArr[4] + " " + sArr[5]); // если нет замены
                         }
                    }

                    if ((sArr[8] != null)
                            & (!"г.п".equals(sArr[8]))) {
                         if (sArr[10] != null) { // если есть замена
                              s.concat( s + "  " + sArr[10] + " " + sArr[11]); // если есть замена
                         } else {
                              s.concat( s + "  " + sArr[8] + " " + sArr[9]); // если нет замены
                         }
                    }


                    if ((sArr[12] != null)
                            & (!"г.п".equals(sArr[12]))) {
                         if (sArr[14] != null) { // если есть замена
                              s.concat( s + "  " + sArr[14] + " " + sArr[15]); // если есть замена
                         } else {
                              s.concat( s + "  " + sArr[12] + " " + sArr[13]); // если нет замены
                         }
                    }

                    if ((sArr[16] != null)
                            & (!"г.п".equals(sArr[16]))) {
                         if (sArr[18] != null) { // если есть замена
                              s.concat( s + "  " + sArr[18] + " " + sArr[19]); // если есть замена
                         } else {
                              s.concat( s + "  " + sArr[16] + " " + sArr[17]); // если нет замены
                         }
                    }

                    if ((sArr[20] != null)
                            & (!"г.п".equals(sArr[20]))) {
                         if (sArr[22] != null) { // если есть замена
                              s.concat( s + "  " + sArr[22] + " " + sArr[23]); // если есть замена
                         } else {
                              s.concat( s + "  " + sArr[20] + " " + sArr[21]); // если нет замены
                         }
                    }


                    s = s + " <b>" + sArr[24] + " " + sArr[25] + "</b> "/*+ sArr[26]*/; // если есть замена


                    aResult2.add(s);


//        s =   oRowset.getString(1)+" "
//            + oRowset.getString(2)+" "
//            + oRowset.getString(3)+" "


                    sObject = sObject + ("{ \"nID_Polis\": " + sArr[26] + "  ,  \"value\":\" " + aResult2.get(i) + " \"} ,");
                    //aResult3.add(" { \"nID_Polis\":\" "+sArr[26]+" \"  ,   \"value\":\" "+aResult2.get(i) + " \" } , ");   
                    //if  (i != aResult2.size()-1){

               }

               sObject = "[" + sObject.substring(0, sObject.length() - 1) + "]";

               // делаем объект  
// aResult3.add("\"[");
//  for (int i = 0; i <= aResult2.size()-1; i++)  {
//
//  aResult3.add(" { \"nID_Polis\":\" "+aResult2.get(i)+" \"  ,   \"value\":\"22222\" }  ");
//  if  (i != aResult2.size()-1){
//  aResult3.add(" , ");
//}



               //}
               //aResult3.add("\"]");

               //String ss = Arrays.toString(sArr);       
             //------------  System.out.println(sObject);

               //s += (  ",\""+ oRowset.getInt(1) +"\":" + "\"" +oRowset.getString(2) + "\"");
               //  if (i==1000) break; // не загружаем больше 100 записей


               //sReturnLogin = " [{ \"nID_Region\":\"34\"  ,  \"value\":\"Днепропетровская обл.\" }, { \"nID_Region\":38  ,  \"value\":\"Киевская обл. Район Мироновский\"} ] ";
               //sReturnLogin =  " [{ \"nID_Region\":\"34\"  ,  \"value\":\"Днепропетровская обл.\" }, { \"nID_Region\":38  ,  \"value\":\"Киевская обл. Район Мироновский\"} ] ";




          } catch (Exception oException) {
                 oLog.info("[" + sCase + "] (sLogin= " + "): Ошибка Конструктора ", oException);
                 System.out.println(oException);
          } finally {
               AccessDB.close(sCase, oStatement); 
               AccessDB.closeConnectionStatic(sCase, oConnection);  
               return sObject;
          }
     }

     // Получаем список всех городов по одному выбранному региону
     public String getAllPolis(String nID_Region, String nID_PolisType) throws Exception {
          String s = "";
          int i = 0;

          Connection oConnection = AccessDB.oConnectionStatic("");
          ResultSet oRowset = oConnection.prepareStatement("SELECT nID, sPolis FROM PlacePolis where nID_PlaceRegion = " + nID_Region + " AND nID_PlacePolisType = " + nID_PolisType).executeQuery();
          while (oRowset.next() & i < 100) {
               i++;
               // s += (  ",\"a"+ i +"\":" + "\"" +oRowset.getString(1) + "\"");
               s += (",\"" + oRowset.getInt(1) + "\":" + "\"" + oRowset.getString(2) + "\"");
               if (i < 100) {
                    break; // не загружаем больше 100 записей
               }
          }
          AccessDB.closeConnectionStatic("", oConnection);
          return s;
     }
}
