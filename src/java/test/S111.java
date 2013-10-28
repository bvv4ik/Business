/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
 

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

/**
 *
 * @author Sergey
 */
public class S111 {
    
    
     
    public static void main(String[] args) throws SQLException, InterruptedException, IOException {
    
     //String s = String.format("%02d", 1);
         int sCookie = 10;
        String sReturn = "{'sReturn':'Добро пожаловать на сайт!', "
                + "'sReturnCookie':"+sCookie+"}";
         System.out.println(sReturn);
        sReturn = sReturn.replaceAll("'", "\"");
        System.out.println(sReturn);
//    List list1 = new ArrayList(); 
//    LoadTextFile lf1 = new LoadTextFile();
//    list1 = lf1.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
//        
//    String s  = list1.get(2).toString();
//    System.out.println(s);
    
    
   // String sss = "dsf sdf asg asdfg adfg";
    
 //   String s= "1234567890";
 // System.out.println(s.substring(7, 10));
         
     /*     String sRandomCookie = "";
                         for (int i = 0; i < 30; i++) { 
                             Random rand = new Random();
                             int nRandom = rand.nextInt();
                             // No.2 Случайное целое число от 0 до 10
                              nRandom = rand.nextInt(26);
                              int a = (int) 'a';
                              char b = (char) (a + nRandom);
                              sRandomCookie += b;
                            }
                         System.out.println(sRandomCookie);
int a = (int) 'a';
System.out.println(a);
char b = (char) (a + 1);
System.out.println(b);
*/
         
//       String sCookie =   "31&cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr";
//       
//       if (sCookie.indexOf("&") != -1){          // если есть знак "/"
//             String sCookie1 = sCookie.substring(0, sCookie.indexOf("&")); // Берем все что сначала строки и до знака "/"
//       System.out.println(sCookie1);
//                         
//       }
//       
//       if (sCookie.indexOf("&") != -1){             // если есть знак "/"
//              String sCookie2 = sCookie.substring(sCookie.lastIndexOf("&")+1); // берем все что после "/" и до конца
//
//          System.out.println(sCookie2);
//       }
        
    
    }
    
}
