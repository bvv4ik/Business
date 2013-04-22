/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.bw.converter.LoadTextFile;


import com.bw.converter.ItemDataStream.aSourceType;
import com.bw.converter.ItemDataStream;
import com.bw.converter.ItemDataTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Sergey
 */
public class Conv {
    
    public static void main(String args[]) throws SQLException {
        
     LoadTextFile o = new LoadTextFile();
    List list1 = new ArrayList(); 
         list1 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
    List list2 = new ArrayList(); 
         list2 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
    List list3 = new ArrayList(); 
         list3 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");
              
     String sCurrentRow1  = "";
     String sCurrentRow2  = "";
     String sCurrentRow3  = "";
     
      String sResultRow  = ""; //list1.get(2).toString();
    String s = "";
    int ID = 0;
    
    String IdCoatuu_Obl = "";
    
    String IdRegion1 = "";
    String IdRegion2 = "";
    String IdRegion3 = "";
    
    String TypeRegion1 = "";
    String NameRegion1 = "";    

    String TypeRegion2 = "";
    String NameRegion2 = "";
    
    String TypeRegion3 = "";
    String NameRegion3 = "";
    
    String NamePolis = "";
    
  
    
    
    
    for (int i = 0; i <= 1500; i++) {
    //sCurrentRow = list3.get(i).toString();
    //String formatted = String.format("%07d", i);
    
      
        
        NamePolis = list3.get(i).toString();
     
 
      
        //IdCoatuu = list1.get(i).toString();
        
       // sCurrentRow1 = list1.get(i).toString();
        sCurrentRow2 = list2.get(i).toString();
        sCurrentRow3 = list3.get(i).toString();
        if ((list3.get(i).toString()).lastIndexOf("/") != -1) {
            String temp = (list3.get(i).toString());
            temp = (list3.get(i).toString()).substring(0, (list3.get(i).toString()).lastIndexOf("/"));
            list3.set(i, temp);
        }
        
        //System.out.println(lastIndexOf(sCurrentRow1,1)); 
        //int i = sCurrentRow1.lastIndexOf("/");
        

        
       
        if ((list1.get(i).toString()).endsWith("00000000")) {
            NameRegion1 = list3.get(i).toString();
            IdCoatuu_Obl = list1.get(i).toString();
            IdRegion1 = String.format("%07d", i + 1);
        }
        if ((list3.get(i).toString()).startsWith("АВТОНОМНА РЕСПУБЛІКА ")) {
            TypeRegion1 = "1";
            String temp = (list3.get(i).toString()).replace("АВТОНОМНА РЕСПУБЛІКА ","");
            list1.set(i, temp);
            
        }
        if (sCurrentRow3.contains("ОБЛАСТЬ")) { //endsWith
            TypeRegion1 = "2";
        }

        
       if (("00000".equals(sCurrentRow1.substring(5, 10))) &
          (sCurrentRow1.substring(2, 3).equals("2")))        {
        NameRegion2 = sCurrentRow3;
        IdRegion2 = String.format("%07d", i+1);        
       }
       if (sCurrentRow3.contains(" РАЙОН")){
           TypeRegion2 = "3"; 
           }
      
       
       
          if (("8".equals(sCurrentRow1.substring(5, 6))) & 
              (!"00".equals(sCurrentRow1.substring(6, 8))) &
              ("00".equals(sCurrentRow1.substring(8, 10)))) {
       NameRegion3 = sCurrentRow3;
        IdRegion3 = String.format("%07d", i+1); 
       ID = i+1;
       }
       if ("С".equals(sCurrentRow2)){
           TypeRegion3 = "4"; 
           }
       if ("Щ".equals(sCurrentRow2)){
           TypeRegion3 = "5"; 
           }
    
       
        if (("8".equals(sCurrentRow1.substring(5, 6))) & 
              (!"00".equals(sCurrentRow1.substring(6, 8))) &
              (!"00".equals(sCurrentRow1.substring(8, 10)))) {
           
         //   IdRegion4 = String.format("%07d", i+1);
            
            sResultRow = "\n" + IdRegion1 +"   1   "+" "+NameRegion1+" "+TypeRegion1 + " " + IdCoatuu_Obl +    
                     " | " + IdRegion2 +"   1   "+NameRegion2+" "+TypeRegion2 +
                     " | " + IdRegion3 +"   1   "+NameRegion3+" "+TypeRegion3 +
                     " | " + (i+1) + " " + IdRegion3 +"  "+NamePolis     ;  
            
            System.out.println(sResultRow);
            }
      // }
       //if (sResultRow == null) sResultRow = "s";
       
        
      //  s = s + "\n"+ list3.get(i).toString();
           //System.out.println(list1.get(i).toString());
     }
    // s = s + list1.size();
    
}
    
    
}
