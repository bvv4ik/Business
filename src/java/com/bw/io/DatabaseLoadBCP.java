/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.io;

import business.AccessDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class DatabaseLoadBCP {

   //  public static ArrayList<String> aListTables = new ArrayList<String>();
     private Logger oLog = Logger.getLogger(getClass());

     
     
      public static void main(String[] args) throws Exception {
       // ArrayList<String> oAlist = new ArrayList<String>();
         
           DatabaseLoadBCP oDatabaseLoadBCP = new DatabaseLoadBCP();
         String s =  oDatabaseLoadBCP.loadTableName("UA_DP_PGASA");
        System.out.println(s);
      }
     
     
     public String loadTableName(String sNameDB) throws Exception {
          DOMConfigurator.configure("D:/My Documents/NetBeansProjects/Business/web/WEB-INF/config/log4j.xml");
          //ArrayList<String> aListTables = new ArrayList<String>();
          String sOut ="";
          String sCase = "loadTableName";
          Connection oConnection = null;
          Statement oStatement = null;
        
          try {
               oConnection = AccessDB.oConnectionStatic(sCase);
               oStatement = AccessDB.oStatementStatic(oConnection, sCase);
                         //select name from sysobjects where type like "%U%"
              ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "select name from sysobjects where type like 'U'", oLog);
              String sDate = _.sGetDate().replaceAll(" ", "_").replaceAll(":", "-");
              String sDirectory = "mkdir D:\\BackupSybase\\"+sDate+"\\";
               while (oRowset.next()) {
                    sOut += "bcp UA_DP_PGASA.dbo." + oRowset.getString(1) + " out D:\\BackupSybase\\"+sDate+"\\"+ oRowset.getString(1) + ".txt -Usa -P123321123a -S Business -c -E -Jutf8 \n";
               }

                Runtime rt = Runtime.getRuntime();
                //rt.exec("cmd /c start cmd.exe /K \"cd c:/ && "+sDirectory+" && exit \" "); // 
              //  Runtime.getRuntime().exec("cmd /c start d:\\111.bat");
                
               // Thread.sleep(100);
              //  _.saveTextFile(sOut, sDirectory.replaceAll("mkdir ", "").replaceAll("//", "\\") + "get.bat");
               // rt.exec("cmd /c d:\\111.bat");      
                oLog.info("[" + sCase + "] (sNameDB= " + sNameDB + "): Ошибка загрузки названий таблиц ");
                
          } catch (Exception oException) {
               oLog.error("[" + sCase + "] (sNameDB= " + sNameDB + "): Ошибка загрузки названий таблиц ", oException);
          } finally {
               AccessDB.close(sCase, oStatement);
               AccessDB.closeConnectionStatic(sCase, oConnection);
               return sOut;
          }
     }
     
     
        public String CreateRequest(ArrayList<String> aListTablesName) throws Exception {
            ArrayList<String> aListTables = new ArrayList<String>();
            aListTables = aListTablesName;
            
             return "";
        }
     //ArrayList<String>
     
     
}
