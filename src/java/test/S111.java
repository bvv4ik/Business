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
    
        
//    List list1 = new ArrayList(); 
//    LoadTextFile lf1 = new LoadTextFile();
//    list1 = lf1.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
//        
//    String s  = list1.get(2).toString();
//    System.out.println(s);
    
    
    String sss = "dsf sdf asg asdfg adfg";
    sss.toUpperCase();
    
   //  sss.replaceAll(sss, sss);
    // sss = capitalize("sss");
     String literal = "kode Java - Learn Java Programming by Examples";
     literal = literal.toLowerCase();
     char[] temp = literal.toCharArray();
     temp[0] =  Character.toUpperCase(temp[0]);
     String text = String.valueOf(temp);
     System.out.println(text);
        
//        for (int i = 0; i < 10/*temp.length*/; i++)
//        {
//            System.out.print(temp[i]);
//        }
        
        
//        char[] data = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
//	String text = String.valueOf(data);
//	System.out.println(text);
//        
        
    
    }
    
}
