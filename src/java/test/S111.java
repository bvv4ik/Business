/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;

import com.bw.converter.LoadTextFile;
import java.sql.SQLException;

/**
 *
 * @author Sergey
 */
public class S111 {
    
    public static void main(String[] args) throws SQLException {
     List list1 = new ArrayList(); 
    LoadTextFile lf1 = new LoadTextFile();
    list1 = lf1.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
        
    String s  = list1.get(2).toString();
      System.out.println(s);
    
    }
    
}
