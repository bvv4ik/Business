/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import com.bw.io.ConnectSybase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class NewClassFile {
 
// List list = new ArrayList(); 
// String s = "";  
  
 static void insertFromFileIntoDB (String path ) throws SQLException{
      int iDatch = 0;
      String s = "";  
      List list = new ArrayList(); 
      BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                list.add(sCurrentLine);       
            }
                
            System.out.println("Всего элементов в массиве  "+list.size());
           // System.out.println(list.get(5));
 
          
                    
            
                    
            for (int i = 26762; i <= list.size()-1; i++) {
                s = list.get(i)+"";
             
              // System.out.println(s.substring(12, 22)); 
               // iDatch++;
                if (s.substring(12, 22).equals("PlacePolis") )
                s = "SET IDENTITY_INSERT PlacePolis  ON \n"+ s;
                
                if (s.substring(12, 23).equals("PlaceRegion") )
                s = "SET IDENTITY_INSERT PlaceRegion  ON \n"+ s;
                
                if (s.substring(12, 27).equals("PlaceRegionTree") )
              s=s;
                    //  s = "SET IDENTITY_INSERT PlaceRegionTree ON \n"+ s;
                
               
                System.out.println(s);
              //  insertSrting(s);
                    insertSrting(s);
                s = "";
                
                  
                //if s.substring(1, 15) 
                //System.out.println(s.substring(12, 22));
                
                //s = s+ list.get(i)+"\n";
                //list.remove(i);

//                if ((iDatch == 10) | 
//                   (i == list.size()))    {
//                    
//                    iDatch = 0;
//                    
//                
//                    
//                    System.out.println(s);
//                    System.out.println("--------------------");
//                    s = "";
//                    
//                    //break;
//                }
                
            }
         
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
  
  }  
  
  
   static void insertSrting (String s) throws SQLException   { 
    Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");    
      
    oDC.prepareStatement(s).executeUpdate();
    ConnectSybase.closeConnect("UA_DP_PGASA",oDC);    
    
     }



 public static void main(String[] args) throws SQLException {
  //insertFromFileIntoDB("C:\\3.txt");
  insertFromFileIntoDB("C://9.txt");

  
 }
 
  
//public static void main(String[] args) {
//
//       // String[] strings;
//        //Object a = new String[];
//       // String[] ArrS  = new String[48240];
//        
//        List list = new ArrayList();
//        BufferedReader br = null;
//
//        try {
//            String sCurrentLine;
//            br = new BufferedReader(new FileReader("C:\\3.txt"));
//            while ((sCurrentLine = br.readLine()) != null) {
//                //System.out.println(sCurrentLine);
//                list.add(sCurrentLine);
//            
//            }
//            //strings = new String("dfsdf");
//               //String[] s = (String[]) list.toArray(new String[0]);
//          //  String utf = new String(s[1].getBytes(), "UTF-8"); // перекодировка                   
//            
//            System.out.println(list.size());
//            //System.out.println(utf);
//            System.out.println(list.get(5));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//
//    }
   
    
}