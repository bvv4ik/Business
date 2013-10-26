
package com.bw.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import  java.sql.SQLException;
import javax.naming.InitialContext;



/**
 * Подключаемся к Базе сибейса
 * @author Sergey
 */

public class __ConnectSybase {
    
      // Connection con = null;
      // PreparedStatement stmt = null;
      //  try {
       //     Class.forName("com.mysql.jdbc.Driver");
          //  String url = "jdbc:mysql://localhost:3306/students";
          //  con = DriverManager.getConnection(url, "root", "root");
            
    
        /**
      *
      * @param sName - Название базы
      * @return
      */
     
     public static Connection getConnect(String sName) {
        Connection oConnection=null;     // PreparedStatement stmt = null;     // ResultSet rs = null;
        try{

            Class.forName("com.sybase.jdbc3.jdbc.SybDataSource");
            
// настройки моей домашней базы
  //            String url = "jdbc:sybase:Tds:SERGEY-PC:2048/"+sName;
   //          oConnection = DriverManager.getConnection(url, "sa", "1234567");
             
// удаленная база у Вовы дома
        String url = "jdbc:sybase:Tds:pgasa-edu-ua.org:5000/"+sName;
         oConnection = DriverManager.getConnection(url, "sa", "123321123a");


//на самом деле достаточно только этой строчки, но лучше писать трай-кэтч.. чтоб всегда отлавливать проблеммы с соединением и вываливать их в лог.
    
          } catch (ClassNotFoundException oException) {
               System.out.println("error: failed to load Sybase driver.");
               oException.printStackTrace();
          } catch (SQLException oException) {
               System.out.println("error: failed to create a connection object.");
               oException.printStackTrace();
          } catch (Exception oException) {
               System.out.println("other error:");
               oException.printStackTrace();
//}
               //}catch(Exception _){   // пишем ошибку в лог
               //   System.err.println("ERROR[getConnect](sName="+sName+"):"+_.getMessage());
               //_.printStackTrace(System.out);


          }
          return oConnection;
     }

     
//
     
     /**
      *  метод, для закрытия соединения с базой:
      * @param sName
      * @param oConnection
      */
     
     public static void closeConnect(String sName, Connection oConnection) {
        try{
            if(oConnection!=null){oConnection.close();}
        }catch(Exception _){
            System.err.println("ERROR[closeConnect](sName="+sName+"):"+_.getMessage());
            
        }
    }

    

    
    
}
