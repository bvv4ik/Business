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
    
  //  String IdCoatuu = "";
    
    int IdRegion1 = 0;
    int IdRegion2 = 0;
    int IdRegion3 = 0;
     int IdRegion4 = 0;
    
    String TypeRegion1 = "";
    String NameRegion1 = "";
    

    String TypeRegion2 = "";
    String NameRegion2 = "";
    

    String TypeRegion3 = "";
    String NameRegion3 = "";
    
    String NamePolis = "";
    
    for(int i=0;i<2000/*list1.size()*/;i++) {
    //sCurrentRow = list3.get(i).toString();
    //String formatted = String.format("%07d", i);
    
        NamePolis = list3.get(i).toString();
        //IDPolis = list3.get(i).toString();
        
        sCurrentRow1 = list1.get(i).toString();
        
        sCurrentRow2 = list2.get(i).toString();
        
        sCurrentRow3 = list3.get(i).toString();
        if (sCurrentRow3.lastIndexOf("/") != -1)
        sCurrentRow3 = sCurrentRow3.substring(0, sCurrentRow3.lastIndexOf("/"));
        
        
           // }
        //System.out.println(lastIndexOf(sCurrentRow1,1));
 
        //int i = sCurrentRow1.lastIndexOf("/");
        
      //  if (sCurrentRow.endsWith("00000000")){
      //      sCurrentRow = "0-0-";
      //  }
     
       //  IdRegion1 = String.format("%07d", i);     //Integer.toString(i);  
       // IdRegion2 = String.format("%07d", i);
        //IdRegion3 = String.format("%07d", i);  
        
        //if (list3.get(i).toString().contains("00000000")){
       //   sCurrentRow1 = list1.get(i).toString();
       if (sCurrentRow1.endsWith("00000000")){
           NameRegion1 = sCurrentRow3;//list3.get(i).toString();
           IdRegion1 = i; //Integer.toString(String.format("%07d", i));                        //String.format("%07d", i); //
        }
       if (sCurrentRow3.startsWith("АВТОНОМНА РЕСПУБЛІКА")){
           TypeRegion1 = "1"; 
           }
        if (sCurrentRow3.contains("ОБЛАСТЬ")){ //endsWith
           TypeRegion1 = "2"; 
           }
    
       
       if (("00000".equals(sCurrentRow1.substring(5, 10))) &
          (sCurrentRow1.substring(2, 3).equals("2")))        {
       NameRegion2 = sCurrentRow2;
        IdRegion2 = i;// String.format("%07d", i);        
       }
       if (sCurrentRow3.contains(" РАЙОН")){
           TypeRegion2 = "3"; 
           }
      
       
       
          if (("8".equals(sCurrentRow1.substring(5, 6))) & 
              (!"00".equals(sCurrentRow1.substring(6, 8))) &
              ("00".equals(sCurrentRow1.substring(8, 10)))) {
       NameRegion3 = sCurrentRow3;
        IdRegion3 = i;//Integer.toString(i);//String.format("%07d", i); 
       ID = i-1;
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
           
            IdRegion4 = i;
            
            sResultRow = "\n" +IdRegion1+"   1   "+" "+NameRegion1+" "+TypeRegion1
                    + " " +IdRegion1+"   1   "+NameRegion2+" "+TypeRegion2
                    + " " +IdRegion1+"   1   "+NameRegion3+" "+TypeRegion3
                    + " " + ID +"  "+NamePolis
                    ;  
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
