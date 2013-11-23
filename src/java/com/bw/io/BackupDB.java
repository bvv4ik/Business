/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.io;

import business.AccessDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;



public class BackupDB {

    //  public static ArrayList<String> aListTables = new ArrayList<String>();
    private Logger oLog = Logger.getLogger(getClass());

    public static final String PATH_ROOT_BACKUP_DB = "D:\\BackupSybase\\";
    public static final int MAX_DB_FOLDERS = 30;
    
    
    
   public long getDirSize(String sDirPath) {
    File oDir = new File(sDirPath);
    long size = 0;
    if (oDir.isFile()) {
        size = oDir.length();
    } else {
        File[] subFiles = oDir.listFiles();
        for (File file : subFiles) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getDirSize(sDirPath);
            }
        }
    }
   
    return size;
}
    
   
   
   
    public static void main(String[] args) throws Exception {

        
       BackupDB oBackupDB = new BackupDB();
        
        System.out.println(oBackupDB.getDirSize("D:\\BackupSybase\\2013-11-20_19-31-18"));
         File file = new File("D:\\BackupSybase\\2013-11-20_19-31-18");
   
   // File (or directory) with new name
   File file2 = new File("D:\\BackupSybase\\2013-11-20_19-31-18---");
   
   // Rename file (or directory)
   boolean success = file.renameTo(file2);
        
        //BackupDB oBackupDB = new BackupDB();
        //String s = oBackupDB.loadTableName("UA_DP_PGASA");
    }

    
    
    // Создаем папку и добавляем в нее Бат файл
    
    public String loadTableName(String sNameDB) throws Exception {
        DOMConfigurator.configure(_.PATH_LOG4J_XML);
        String sFullPath = "";
        String sBatFileLoadData = "";
        String sBatFileCreateData = "";
        int  nCountFiles = 0;
        String sCase = "loadTableName";
        Connection oConnection = null;
        Statement oStatement = null;

        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase); // как сделать несколько апросов в одном запросе
            //ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "USE UA_DP_PGASA", oLog); // берем список всех таблиц Юзера из системной тиблицы
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "select name from sysobjects where type like 'U'", oLog);
            
            
            while (oRowset.next()) {  // создаем список BCP запросов
                sBatFileLoadData += "bcp UA_DP_PGASA.dbo." + oRowset.getString(1) + " out "+ sFullPath + oRowset.getString(1) + ".tbl -Usa -P123321123a -S pgasa-edu-ua.org:5000 -c -E -Jutf8 \n";
                sBatFileCreateData += "rem; bcp UA_DP_PGASA.dbo." + oRowset.getString(1) + " in "+ sFullPath + oRowset.getString(1) + ".tbl -Usa -P1234567 -S SERGPC -c -E -Jutf8 \n";
                nCountFiles++;
            }
           
            Runtime oRuntime = Runtime.getRuntime();

            
                                    //Process oRuntime = Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd c:/ && " + sCommand_DATA_DB + " && exit \" "); 
            oRuntime.exec("cmd /c start cmd.exe /K \"cd c:/ && " + sFullPath + "LoadTables.bat" + " && exit \" "); // Быполнить Батник через CMD

             
             String sCommand_STRUCTURE_DB = "ddlgen.bat -U sa -S pgasa-edu-ua.org:5000 -P 123321123a -TDB -D UA_DP_PGASA -XOU -O "+ sFullPath + "-db_ddl.sql";
             // запускаем БАТник- т.е генерируем скрипт DDL для восстановления структуры базы
             oRuntime.exec("cmd /c start cmd.exe /K \"cd c:/ && " + sCommand_STRUCTURE_DB + " && exit \" "); 
            
             
            // String sCommand_DDL_DEVICE = "ddlgen.bat -U sa -S pgasa-edu-ua.org:5000 -P 123321123a -TDBD -N bw_disk -O "+ sFullPath + "-device_ddl.sql" + " -E "+ sFullPath + "\\-device_ddl.err";
            // oRuntime.exec("cmd /c start cmd.exe /K \"cd c:/ && " + sCommand_DDL_DEVICE + " && exit \" "); 
            
            
            // String sCommand_CREATE_DEVICE = "isql -S SERG-PC:2048 -U sa -P 1234567 -i " + sFullPath + "-device_ddl.sql" + " -o "+ sFullPath + "-log.txt";
            String sCommand_CREATE_DEVICE = "disk init\n" +
                "name=serg_disc,\n" +
                "physname =\"/Sybase/data/serg_disc.dat\",\n" +
                "size = \"300M\"\n" +
                "go\n" +
                
                "-----\n" +
                "CREATE DATABASE UA_DP_PGASA\n" +
                "ON serg_disc=\"300M\"disk init\n" +
                "------LOG ON serg_log=\"200M\"\n"+
                "------------------- \n + Убрать шапку (заголовок) в файле создания структуры БД" ;
            
             String sCommand_CREATE_STRUCTURE_DB = "isql -S SERG-PC:2048 -U sa -P 1234567 -i " + sFullPath + "-db_ddl.sql" + " -o "+ sFullPath + "-log.txt";
   
             
            nCountFiles += 7; // Всего файлов должно быть в папке 
            // делаем кооректное название для папки
            String sCurrentDate = _.sGetDate().replace(" ", "_").replace(":", "-").replace(".", "-");
            sFullPath = PATH_ROOT_BACKUP_DB + sCurrentDate+ "\\";
            _.createDir(sFullPath);   // создаем эту папку если ее нет

           
            _.saveTextFile(sBatFileLoadData, sFullPath + "LoadTables.bat"); // сохраняем список в БАТ файл
            _.saveTextFile(sCommand_CREATE_DEVICE, sFullPath + "1_CreateDevice.txt"); 
            _.saveTextFile(sCommand_CREATE_STRUCTURE_DB, sFullPath + "2_CreateStructureDB.bat"); 
            _.saveTextFile(sBatFileCreateData, sFullPath + "3_SaveDataDB.bat"); // Батник загрузки Таблиц из текстовых файлов в Базу
         
             
             
             Thread.sleep(2000);
             File oFolder = new File(sFullPath);
             File[] aFiles = oFolder.listFiles(); // Плучаем список файлов
             if ((aFiles.length) < nCountFiles ) {
             System.out.println("Внимание не все файлы создались!");
             }
        
             
             delFoldersDB(); // удаляем старые папки с Бекапом если превышен их лимит
             //System.out.println(sLoadData);
             
            
        } catch (Exception oException) {
            oLog.error("[" + sCase + "] (sNameDB= " + sNameDB + "): Ошибка загрузки названий таблиц ", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            return sBatFileLoadData;
        }
        
        
    
    }
    
    
    // Удаляем лишние папки с бекапом базы
    
      public void delFoldersDB() throws Exception {
    
          File oFolder = new File(PATH_ROOT_BACKUP_DB);
          File[] aFiles = oFolder.listFiles(); // Плучаем список папок
          
          if ((aFiles.length) > MAX_DB_FOLDERS ) {  // если папок больше допустимого колличества
              _.deleteDir(aFiles[0].toString()); // удяляем самую старую (первую в списке директорий) запись по времени
          }
        //          for (int i = 0; i < aFiles.length; i++) {
        //             System.out.println(aFiles[i]);
        //          }
      }

      
      
      // запуск из коммандной строки
            //Process p;
            //p = Runtime.getRuntime().exec("msconfig");
            //p.waitFor();


            //File file = new File("C:\\test1\\test.xml");
            //File dir = file.getParentFile();
            //if(false == file.exists())
            //{
            //dir.mkdir();
            //}
            //file.createNewFile();

      
        // Запуск из командной строки
              // Runtime rt = Runtime.getRuntime();
             //  rt.exec("cmd /c start cmd.exe /K \"cd c:/ && " + sDirectory + " && exit \" "); // 
             //  Runtime.getRuntime().exec("cmd /c start d:\\111.bat");
             // Thread.sleep(100);
            // rt.exec("cmd /c d:\\111.bat");      
           

      
      
      
      
      
}
