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
    
    
    
    String IdRegion1 = "";
    String IdRegion2 = "";
    String IdRegion3 = "";
    
    
    String TypeRegion1 = "";
    String NameRegion1 = ""; 
    String IdCoatuu1 = "";

    String TypeRegion2 = "";
    String NameRegion2 = "";
    String IdCoatuu2 = "";
    
    String TypeRegion3 = "";
    String NameRegion3 = "";
    String IdCoatuu3 = "";
    
    String IdPolis4 = "";
    String TypePolis4 = "";
    String IdCoatuu4 = "";
    
    
     
    String NamePolis = "";
    
  
    
    
    
    for (int i = 0; i <= 1500; i++) {
    //sCurrentRow = list3.get(i).toString();
    //String formatted = String.format("%07d", i);
    
      
        
      NamePolis = list3.get(i).toString(); // текущий полис
      IdCoatuu4 = list1.get(i).toString();  
 
      
        //IdCoatuu = list1.get(i).toString();
        
       // sCurrentRow1 = list1.get(i).toString();
      //  sCurrentRow2 = list2.get(i).toString();
      //  sCurrentRow3 = list3.get(i).toString();
        if ((list3.get(i).toString()).lastIndexOf("/") != -1) { // удаляем /М.СІМФЕРОПОЛЬ и.д
            String temp = (list3.get(i).toString());
            temp = (list3.get(i).toString()).substring(0, (list3.get(i).toString()).lastIndexOf("/"));
            list3.set(i, temp);
        }
        
        //System.out.println(lastIndexOf(sCurrentRow1,1)); 
        //int i = sCurrentRow1.lastIndexOf("/");
        
      
       
        if ((list1.get(i).toString()).endsWith("00000000")) { // если это ПЕРВАЯ ступень - область, АР
            
           
            if ((list3.get(i).toString()).startsWith("АВТОНОМНА РЕСПУБЛІКА ")) {  //если фраза "АВТОНОМНА РЕСПУБЛІКА"
            TypeRegion1 = "1";                                                // запоминаем тип
            String sTemp = (list3.get(i).toString()).replace("АВТОНОМНА РЕСПУБЛІКА ",""); // удаляем
            list3.set(i, sTemp);  //запоминаем
        }
        if ((list3.get(i).toString()).contains(" ОБЛАСТЬ")) { //endsWith
            TypeRegion1 = "2";
            String sTemp = (list3.get(i).toString()).replace(" ОБЛАСТЬ",""); // удаляем фразу " ОБЛАСТЬ"
            list3.set(i, sTemp);  //запоминаем
        }
            
             
            NameRegion1 = list3.get(i).toString();  // запоминаем название Области
            IdCoatuu1 = list1.get(i).toString();  // запоминаем номер КОАТУ
            IdRegion1 = String.format("%07d", i + 1);  // назначаем ИД
            
        }
       

        //========================================================
        
        if ( ((list1.get(i).toString()).substring(5, 10)).equals("00000") // // если это ВТОРАЯ ступень - район
                & ((list1.get(i).toString()).substring(2, 3).equals("2"))) {
            
             if ((list3.get(i).toString()).contains(" РАЙОН")) {
            TypeRegion2 = "3"; // тип Район
            String sTemp = (list3.get(i).toString()).replace(" РАЙОН",""); // удаляем фразу " ОБЛАСТЬ"
            list3.set(i, sTemp);  //запоминаем
        }
            
            
            NameRegion2 = (list3.get(i).toString());
            IdRegion2 = String.format("%07d", i + 1);
            IdCoatuu2 = list1.get(i).toString();  // запоминаем номер КОАТУ
        }
       
      
      //========================================================  
       
         if (("8".equals((list1.get(i).toString()).substring(5, 6))) // если это ТРЕТЬЯ ступень - сельрада
                & (!"00".equals((list1.get(i).toString()).substring(6, 8)))
                & ("00".equals((list1.get(i).toString()).substring(8, 10)))) {
            NameRegion3 = list3.get(i).toString();
            IdRegion3 = String.format("%07d", i + 1);
            IdCoatuu3 = list1.get(i).toString();  // запоминаем номер КОАТУ
           // ID = i + 1;           // связка с полисами
            IdPolis4 = String.format("%07d", i+1); // связка с полисами
        }
         
          TypeRegion3 = "7"; 
//      
    
         //========================================================  
        
        if      (("8".equals((list1.get(i).toString()).substring(5, 6))) & // если это 4 ступень - село, селение
                (!"00".equals((list1.get(i).toString()).substring(6, 8)))
                & (!"00".equals((list1.get(i).toString()).substring(8, 10)))) {
           
                    if ("С".equals(list2.get(i).toString())) {
                        TypePolis4 = "3";  // село
                    }
                    if ("Щ".equals(list2.get(i).toString())) {
                        TypePolis4 = "4";   // селение
                    }
            
                 
             String IDs = String.format("%07d", i+1);
           
            
         sResultRow = "\n" + IdRegion1 +"   1   "+" "+NameRegion1+" "+TypeRegion1 + " " + IdCoatuu1 +    
                     " | " + IdRegion2 +"   1   "+NameRegion2+" "+TypeRegion2 + " " + IdCoatuu2 + 
                     " | " + IdRegion3 +"   1   "+NameRegion3+" "+TypeRegion3 + " " + IdCoatuu3 + 
                     " | " + (i+1) + " " + IdPolis4 +"  "+ TypePolis4 +"  "+  NamePolis + " " + IdCoatuu4    ;  
            
         List listTree1 = new ArrayList(42000); 
         listTree1.add(IdRegion3);
         List listTree2 = new ArrayList(42000); 
         listTree2.add(IdRegion2);
         List listTree3 = new ArrayList(42000); 
         
         
            System.out.print(sResultRow);
            }
      // }
       //if (sResultRow == null) sResultRow = "s";
       
        
      //  s = s + "\n"+ list3.get(i).toString();
           //System.out.println(list1.get(i).toString());
     }
    // s = s + list1.size();
    
}
    
    
}
