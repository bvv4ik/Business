/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Sergey
 */
public class InsertFromFile {
//    
//    public void getA()   { 
//    Connection oConnection = AccessDB.oConnectionStatic("");    
//      
//    oConnection.prepareStatement("INSERT INTO PlaceRegion1 (nID_PlaceCountry, sRegion) VALUES (1, '"+sRegion +"')").executeUpdate();
//    AccessDB.closeConnectionStatic("", oConnection);    
//     }

    public static void main(String[] args) {
        File file = new File("C:/111.txt");
        StringBuilder contents = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            // reader = new InputStreamReader(new FileInputStream(f), "UTF-8");


            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text)
                        .append(System.getProperty(
                        "line.separator"));
            }
        } catch (IOException oException) {
            oException.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException oException) {
                oException.printStackTrace();
            }
        }

        // show file contents here
        System.out.println(contents.toString());
    }
}
