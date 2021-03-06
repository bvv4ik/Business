package zOld;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package students.logic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;

/**
 *
 * @author Ser
 */
public class DBconn {

    // Connection con = null;
    // PreparedStatement stmt = null;
    //  try {
    //     Class.forName("com.mysql.jdbc.Driver");
    //  String url = "jdbc:mysql://localhost:3306/students";
    //  con = DriverManager.getConnection(url, "root", "root");
    public static Connection getConnect(String sName) {
        Connection oConnection = null;
        //  PreparedStatement stmt = null;
        // ResultSet rs = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            //String url = "jdbc:mysql://localhost:3306/students";
            String url = "jdbc:mysql://localhost:3306/dbStudy"; //+sName
            oConnection = DriverManager.getConnection(url, "root", "root");

            //  oConnection=((javax.sql.DataSource)new InitialContext().lookup(sName)).getConnection();
//на самом деле достаточно только этой строчки, но лучше писать трай-кэтч.. чтоб всегда отлавливать проблеммы с соединением и вываливать их в лог.
        } catch (Exception _) {
            System.err.println("ERROR[getConnect](sName=" + sName + "):" + _.getMessage());
            _.printStackTrace(System.out);
        }
        return oConnection;
    }

//И такой метод, для закрытия:
    public static void closeConnect(String sName, Connection oConnection) {
        try {
            if (oConnection != null) {
                oConnection.close();
            }
        } catch (Exception _) {
            System.err.println("ERROR[closeConnect](sName=" + sName + "):" + _.getMessage());
        }
    }
}
