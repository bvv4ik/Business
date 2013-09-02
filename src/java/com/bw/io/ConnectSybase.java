

package com.bw.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import  java.sql.SQLException;
import javax.naming.InitialContext;




public class ConnectSybase {
    
   // Connection con = null;
   // PreparedStatement stmt = null;
    
      //  try {
       //     Class.forName("com.mysql.jdbc.Driver");
          //  String url = "jdbc:mysql://localhost:3306/students";
          //  con = DriverManager.getConnection(url, "root", "root");
            
    
        public static Connection getConnect(String sName) {
        Connection oDC=null; 
      //  PreparedStatement stmt = null;
       // ResultSet rs = null;
        try{

            Class.forName("com.sybase.jdbc3.jdbc.SybDataSource");
            

 //           String url = "jdbc:sybase:Tds:SERGEY-PC:2048/"+sName;
  //        oDC = DriverManager.getConnection(url, "sa", "1234567");
         String url = "jdbc:sybase:Tds:pgasa-edu-ua.org:5000/"+sName;
          oDC = DriverManager.getConnection(url, "sa", "123321123a");


        //  oDC=((javax.sql.DataSource)new InitialContext().lookup(sName)).getConnection();
//на самом деле достаточно только этой строчки, но лучше писать трай-кэтч.. чтоб всегда отлавливать проблеммы с соединением и вываливать их в лог.
        
        } catch (ClassNotFoundException e) {
System.out.println("error: failed to load Sybase driver.");
//e.printStackTrace();
} 
        catch (SQLException e) {
System.out.println("error: failed to create a connection object.");
e.printStackTrace();
} catch (Exception e) {
System.out.println("other error:");
e.printStackTrace();
//}
        //}catch(Exception _){
         //   System.err.println("ERROR[getConnect](sName="+sName+"):"+_.getMessage());
            //_.printStackTrace(System.out);
            
          
        }
        return oDC;
    }

//И такой метод, для закрытия:
public static void closeConnect(String sName, Connection oDC) {
        try{
            if(oDC!=null){oDC.close();}
        }catch(Exception _){
            System.err.println("ERROR[closeConnect](sName="+sName+"):"+_.getMessage());
            
        }
    }

    

    
    
}
