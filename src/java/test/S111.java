/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;

import com.bw.converter.LoadTextFile;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Sergey
 */
public class S111 {
    
    public static void main(String[] args) throws SQLException, InterruptedException, IOException {
    
        
//    List list1 = new ArrayList(); 
//    LoadTextFile lf1 = new LoadTextFile();
//    list1 = lf1.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
//        
//    String s  = list1.get(2).toString();
//    System.out.println(s);
    
    
    String sss = "dsf sdf asg asdfg adfg";
    
    
   //медленно
//String[] fields = new String[] {"a","b","c","d","e","f","g"};
//String s = "";
//
//
//
//for (int i = 0; i < 30000; i++) {
//    s = s + fields[1];
//}
//
//
//System.out.println(s);


////быстро
String[] fields = new String[] {"a","b","c","d","e","f","g"};
StringBuilder s = new StringBuilder();


for (int i = 0; i < 3000; i++) {
    s.append(fields[1]);
}
System.out.println(s.toString());
     

String[]s2 = new String[100];
s2[2] = "adf";

// запуск из коммандной строки
Process p;
p = Runtime.getRuntime().exec("msconfig");
//p.waitFor();

// задержка
// === Thread.sleep(3000);


        
    
    }
    
}
