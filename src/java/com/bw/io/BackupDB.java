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
    public static final int MAX_DB_FOLDERS = 20;
    
    
    
    public static void main(String[] args) throws Exception {

        BackupDB oBackupDB = new BackupDB();
        String s = oBackupDB.loadTableName("UA_DP_PGASA");
      //  System.out.println(s);
    }

    
    
    // Создаем папку и добавляем в нее Бат файл
    
    public String loadTableName(String sNameDB) throws Exception {
        DOMConfigurator.configure(_.PATH_LOG4J_XML);
        String sFullPath = "";
        String sLoadData = "";
        String sCreateData = "";
        int  nCountFiles = 0;
        String sCase = "loadTableName";
        Connection oConnection = null;
        Statement oStatement = null;

        try {
            oConnection = AccessDB.oConnectionStatic(sCase);
            oStatement = AccessDB.oStatementStatic(oConnection, sCase);
            //ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "USE UA_DP_PGASA", oLog); // берем список всех таблиц Юзера из системной тиблицы
            ResultSet oRowset = AccessDB.oRowsetQuery(oStatement, sCase, "select name from sysobjects where type like 'U'", oLog);
            String sCurrentDate = _.sGetDate().replace(" ", "_").replace(":", "-").replace(".", "-"); // делаем кооректное название для создания папки с этим названием
            sFullPath = PATH_ROOT_BACKUP_DB + sCurrentDate+ "\\";
            _.createDir(sFullPath);   // создаем эту папку если ее нет
            while (oRowset.next()) {  // создаем список BCP запросов
                sLoadData += "bcp UA_DP_PGASA.dbo." + oRowset.getString(1) + " out "+ sFullPath + oRowset.getString(1) + ".tbl -Usa -P123321123a -S pgasa-edu-ua.org:5000 -c -E -Jutf8 \n";
                sCreateData += "bcp UA_DP_PGASA.dbo." + oRowset.getString(1) + " in "+ sFullPath + oRowset.getString(1) + ".tbl -Usa -P1234567 -S SERG-PC:2048 -c -E -Jutf8 \n";
                nCountFiles++;
            }
            
            nCountFiles += 7; // Всего файлов должно быть в папке
            _.saveTextFile(sLoadData, sFullPath + "LoadTables.bat"); // сохраняем список в БАТ файл

             
            Runtime oRuntime = Runtime.getRuntime();
            String sCommand_DATA_DB = sFullPath + "LoadTables.bat" ; // Путь к Батнику 
            //Process oRuntime = Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd c:/ && " + sCommand_DATA_DB + " && exit \" ");
             
             
            oRuntime.exec("cmd /c start cmd.exe /K \"cd c:/ && " + sCommand_DATA_DB + " && exit \" "); // Быполнить Батник через CMD
             
//             BufferedReader reader =
//            new BufferedReader(new InputStreamReader(oRuntime.getInputStream()));
//            while ((reader.readLine()) != null) {}
//            oRuntime.waitFor();
             
             
             // Создаем БАТ файл для генерации скрипта DDL 
             String sCommand_STRUCTURE_DB = "ddlgen.bat -U sa -S pgasa-edu-ua.org:5000 -P 123321123a -TDB -D UA_DP_PGASA -XOU -O "+ sFullPath + "-db_ddl.sql";
             // запускаем БАТник- т.е генерируем скрипт DDL для восстановления структуры базы
             oRuntime.exec("cmd /c start cmd.exe /K \"cd c:/ && " + sCommand_STRUCTURE_DB + " && exit \" "); 
            
             
             String sCommand_DDL_DEVICE = "ddlgen.bat -U sa -S pgasa-edu-ua.org:5000 -P 123321123a -TDBD -N bw_disk -O "+ sFullPath + "-device_ddl.sql" + " -E "+ sFullPath + "\\-device_ddl.err";
             oRuntime.exec("cmd /c start cmd.exe /K \"cd c:/ && " + sCommand_DDL_DEVICE + " && exit \" "); 
            
            
             String sCommand_CREATE_DEVICE = "isql -S SERGPC:2048 -U sa -P 1234567 -i " + sFullPath + "-device_ddl.sql" + " -o "+ sFullPath + "-log.txt";
             _.saveTextFile(sCommand_CREATE_DEVICE, sFullPath + "1_CreateDevice.bat"); 
            
             String sCommand_CREATE_STRUCTURE_DB = "isql -S SERGPC:2048 -U sa -P 1234567 -i " + sFullPath + "-db_ddl.sql" + " -o "+ sFullPath + "-log.txt";
             _.saveTextFile(sCommand_CREATE_STRUCTURE_DB, sFullPath + "2_CreateStructureDB.bat"); 

             _.saveTextFile(sCreateData, sFullPath + "3_SaveDataDB.bat");

             
             Thread.sleep(2000);
             File oFolder = new File(sFullPath);
             File[] aFiles = oFolder.listFiles(); // Плучаем список файлов
             if ((aFiles.length) < nCountFiles ) {
             System.out.println("Внимание не все файлы создались!");
             }
        
             
             delFoldersDB();
             //System.out.println(sLoadData);
             
            
        } catch (Exception oException) {
            oLog.error("[" + sCase + "] (sNameDB= " + sNameDB + "): Ошибка загрузки названий таблиц ", oException);
        } finally {
            AccessDB.close(sCase, oStatement);
            AccessDB.closeConnectionStatic(sCase, oConnection);
            return sLoadData;
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
