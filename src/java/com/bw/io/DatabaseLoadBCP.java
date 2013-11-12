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
          ArrayList<String> aListTables = new ArrayList<String>();
          String sCase = "loadTableName";
          Connection oConnection = null;
          Statement oStatement = null;
          try {
               oConnection = AccessDB.oConnectionStatic(sCase);
               oStatement = AccessDB.oStatementStatic(oConnection, sCase);
               //select name from sysobjects where type like "%U%"
               ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "select name from sysobjects where type like \"%U%\"", oLog);
               if (oRowset.next()) {
                    aListTables.add("bcp UA_DP_PGASA.dbo." + oRowset.getString("name") + " out e:\\" + oRowset.getString("name") + ".txt -Usa -P123321123a -S Business -c -E -Jutf8");
               }
          } catch (Exception oException) {
               oLog.error("[" + sCase + "] (sNameDB= " + sNameDB + "): Ошибка загрузки названий таблиц ", oException);
          } finally {
               AccessDB.close(sCase, oStatement);
               AccessDB.closeConnectionStatic(sCase, oConnection);
               return aListTables.toString();
          }
     }
     
     
        public String CreateRequest(ArrayList<String> aListTablesName) throws Exception {
            ArrayList<String> aListTables = new ArrayList<String>();
            aListTables = aListTablesName;
            
             return "";
        }
     //ArrayList<String>
     
     
}
