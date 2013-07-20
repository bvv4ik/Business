/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter.city2;

import com.bw.converter.OtherMethods;
import com.bw.converter.OtherMethods;
import com.bw.converter.ray.Obl_ray_silr_sel;
import java.sql.SQLException;
import java.util.ArrayList;

    //// 2 ступени адреса:   Область(или АР) --- Город 

public class ArObl_city_rayGor_city {
    
    public static boolean bShowList ;
    
         /*Входные Рабочие данные*/
    public ArrayList<String> list1 = new ArrayList<String>();
    public ArrayList<String> list2 = new ArrayList<String>();   
    public ArrayList<String> list3 = new ArrayList<String>();   
    public ArrayList<String> list4 = new ArrayList<String>();   
    
       /*Массивы готовых Выходных дынных, с дубликатами*/
    public ArrayList<String> aPlaceRegion = new ArrayList<String>();
    public ArrayList<String> aPlaceRegionTree = new ArrayList<String>();
    public ArrayList<String> aPlacePolis = new ArrayList<String>();
    
    public ArrayList<String> aPlaceArea = new ArrayList<String>();

    

    
     public static void main(String args[]) throws SQLException {
        ArObl_city_rayGor_city o1 = new ArObl_city_rayGor_city();
        bShowList = true;
        o1.getData();
    } 
//  мавсамыма а
    
      public void getData() throws SQLException {
        
          //Загружаем КОАТУ в 3 столбца  
           list1 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt"); 
           list2 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
           list3 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");
           list4 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/4.txt");

        String sResultRow = "";

        String sFour = "";
        

        /*2 ступени адреса*/
        String IdRegion1 = "";
        String TypeRegion1 = "";
        String NameRegion1 = "";
        String IdCoatuu1 = "";

        String IdRegion2 = "";
        String TypeRegion2 = "";
        String NameRegion2 = "";
        String IdCoatuu2 = "";

        String IdRegion3 = "";
        String TypeRegion3 = "";
        String NameRegion3 = "";
        String IdCoatuu3 = "";

        String IdRegion4 = "";
        String TypeRegion4 = "";
        String NameRegion4 = "";
        String IdCoatuu4 = "";      
        
        String IdRegion5 = "";
        String TypeRegion5 = "";
        String NameRegion5 = "";
        String IdCoatuu5 = "";      
        
        String IdRegion6 = "";
        String TypeRegion6 = "";
        String NameRegion6 = "";
        String IdCoatuu6 = "";  

        
        String IdPolis7 = "";
        String TypePolis7 = "";
        String IdCoatuu7 = "";
        String NamePolis7 = "";

    
       // Помечаем районі с продолжением знаком "="
     //   OtherMethods.AddLinkRay(list1, list2, list3);
        
       // Исправляем ошибки и связываем получаем ссылку полиса на рут региона
        list3 = OtherMethods.FindRegionPolis(list1, list3); 

        list3 = OtherMethods.ClearGarbage(list3);  // Удаляем мусор из строк с названиями и делаем строку с большой буквы
    
    
    for (int i = 0; i <= list1.size()-1; i++) { /*проходимся по списку КОАТУ*/
 
        NamePolis7 = list3.get(i);     // запоминаем текущее название полиса
        IdCoatuu7 = list1.get(i);     // запоминаем 10-и значный текущий номер Коату
       
    
       //========================================================
        //АВТОНОМНА РЕСПУБЛІКА или ОБЛАСТЬ  0100000000
        if (list1.get(i).endsWith("00000000")) { // если это 1 ступень - Область, АР
               if (list3.get(i).startsWith("КРИМ")) {
                    TypeRegion1 = "1";                // 1 = тип АР в PlaceRegionType
                } else {
                    TypeRegion1 = "2";               // 2 = тип Область в PlaceRegionType
                }
            NameRegion1 = list3.get(i);                      // запоминаем название Области
            IdRegion1 = Integer.toString(i+1);               // назначаем ИД для Области
            IdCoatuu1 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти
        }

       //-----------------------------------------------------
        // МІСТА АВТОНОМНОЇ РЕСПУБЛІКИ КРИМ [0110000000]
        if (list1.get(i).substring(3, 10).equals("0000000")        // если это 2 ступень -  // МІСТА АВТОНОМНОЇ РЕСПУБЛІКИ КРИМ [0110000000]
                      & list1.get(i).substring(2, 3).equals("1")   )  {        // !!! ищем только в Городах Области  и АР          
                  
         /*  ??Міста какие типа???*/  
         TypeRegion2 = "10";                      // 11 = тип "Міста"
            
        NameRegion2 = list3.get(i);                        // запоминаем название    
        IdRegion2 = Integer.toString(i+1);                 // назначаем ИД для Области
        IdCoatuu2 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти
        }
        
      //-----------------------------------------------------
        //0110100000		СІМФЕРОПОЛЬ         
     if (list1.get(i).substring(5, 10).equals("00000")        // если это 3 ступень - конкретный город Ялта
           & list1.get(i).substring(2, 3).equals("1")        // !!! ищем только в Городах Области и АР
             )  {
        TypeRegion3 = "4";       // здесь заменяем на  "Миськрада города"
        NameRegion3 = list3.get(i);              // запоминаем название    
        IdRegion3 = Integer.toString(i+1);               // назначаем ИД для Области
        IdCoatuu3 = list1.get(i);                          
      }

     //-----------------------------------------------------
    //  0110130000	РАЙОНИ М.СІМФЕРОПОЛЯ
     if ( //list1.get(i).substring(5, 6).equals("3")        // если это 4 ступень -РАЙОНИ миста
          // &  list1.get(i).substring(7, 10).equals("000")
             (list1.get(i).substring(5, 10).equals("30000")        // если это 4 ступень -РАЙОНИ миста
           |  list1.get(i).substring(5, 10).equals("36000") )
                     
           & list1.get(i).substring(2, 3).equals("1")  )  {       // !!! ищем только в Городах Области и АР
             
        TypeRegion4 = "10";
        NameRegion4 = list3.get(i);              // запоминаем название    
        IdRegion4 = Integer.toString(i+1);               // назначаем ИД для Области
        IdCoatuu4 = list1.get(i);                          
      
     }

      //-----------------------------------------------------
     // Р ЗАЛІЗНИЧНИЙ [0110136300]
      if (      list2.get(i).equals("Р")                    // если это 5 ступень - конкретній Район          
             &  list4.get(i).substring(0, 1).equals("1")              
             &  list1.get(i).substring(2, 3).equals("1")  // !!! ищем только в Городах Области и АР
            //  & list3.get(i).endsWith("=") // = значит, что район имеет продолжение
            ) {
      
           TypeRegion5 = "5";
        NameRegion5 = list3.get(i);              // запоминаем название    
        IdRegion5 = Integer.toString(i+1);               // назначаем ИД для Области
        IdCoatuu5 = list1.get(i);      
        
      }
     //-----------------------------------------------------
//sNamePolis = arrName.get(i).substring(0,4);

     // МІСТА, ПІДПОРЯДКОВАНІ ПРОЛЕТАРСЬКІЙ РАЙРАДІ 
     if (   list4.get(i).substring(0, 1).equals("6")  // если это 6 ступень - СЕЛИЩА МІСЬКОГО ТИПУ району... 
           
           //   &  list3.get(i).contains("СЕЛИЩА МІСЬКОГО ТИПУ")
            //  &  list3.get(i).contains("РАЙРАДІ")
             
            & list1.get(i).substring(2, 3).equals("1")  // !!! ищем только в Городах Области и АР
            )  {
     
         TypeRegion6 = "10";
        NameRegion6 = list3.get(i);              // запоминаем название    
        IdRegion6 = Integer.toString(i+1);               // назначаем ИД для Области
        IdCoatuu6 = list1.get(i);                        
      
         IdPolis7 = Integer.toString(i+1);             //  для связи с полисами
     }         
//       1 - район
//       2 - смт пидпор
//       3 - смт
//       4 - село, сел
      //   5 - селище ??
//       6 - мист пидпор
//       7 мисто
//       8 сельрад пыдпорядк
//       9 сельрада
//                                                                       
             
    //-----------------------------------------------------
   // смт АЕРОФЛОТСЬКИЙ [0110165300]

     //String s = list1.get(i).substring(7, 9);
     if (  //  list4.get(i).equals("1")                             // если это 7 ступень - конкретній смт     
             list4.get(i).substring(0, 1).equals("7") 
         //  &  !list1.get(i).substring(7, 8).equals("0")
             & list1.get(i).substring(2, 3).equals("1")  // !!! ищем только в Городах Области и АР
            )  {    
        
            TypePolis7 = "1";                      // 1 = город
            
            
            /*Вывод на екран 2-х таблиц, 1 ступени региона и 1 полиса*/ 
            if (bShowList) {
             sResultRow = "\n" + IdRegion1 + "\t" + 1 + "\t" + NameRegion1 + "\t" + TypeRegion1 + "\t" + IdCoatuu1
                        + "\t\t" + IdRegion2 + "\t" + 1 + "\t" + NameRegion2 + "\t" + TypeRegion2 + "\t" + IdCoatuu2
                        + "\t\t" + IdRegion3 + "\t" + 1 + "\t" + NameRegion3 + "\t" + TypeRegion3 + "\t" + IdCoatuu3
                        + "\t\t" + IdRegion4 + "\t" + 1 + "\t" + NameRegion4 + "\t" + TypeRegion4 + "\t" + IdCoatuu4
                        + "\t\t" + IdRegion5 + "\t" + 1 + "\t" + NameRegion5 + "\t" + TypeRegion5 + "\t" + IdCoatuu5
                        + "\t\t" + IdRegion6 + "\t" + 1 + "\t" + NameRegion6 + "\t" + TypeRegion6 + "\t" + IdCoatuu6
                        + "\t\t" + (i+1)  + "\t" + IdPolis7 + "\t" + TypePolis7 + "\t" + NamePolis7 + "\t" + IdCoatuu7;
           System.out.print(sResultRow);
             }
           
           
              // формирование готового списка PlaceRegion
           // nID   sRegion   nID_PlaceCountry   nID_PlaceRegionType  nID_PlacePolis_Root   sID_National  nID_PlaceArea
            aPlaceRegion.add(IdRegion1+"\t"+OtherMethods.getFirstName(NameRegion1)+"\t"+1+"\t"+TypeRegion1+"\t"+ OtherMethods.getLastNum(NameRegion1) +"\t"+IdCoatuu1+"\t"+"0"); 
            aPlaceRegion.add(IdRegion2+"\t"+OtherMethods.getFirstName(NameRegion2)+"\t"+1+"\t"+TypeRegion2+"\t"+ OtherMethods.getLastNum(NameRegion2) +"\t"+IdCoatuu2+"\t"+"0"); 
            aPlaceRegion.add(IdRegion3+"\t"+OtherMethods.getFirstName(NameRegion3)+"\t"+1+"\t"+TypeRegion3+"\t"+ OtherMethods.getLastNum(NameRegion3) +"\t"+IdCoatuu3+"\t"+"0"); 
            aPlaceRegion.add(IdRegion4+"\t"+OtherMethods.getFirstName(NameRegion4)+"\t"+1+"\t"+TypeRegion4+"\t"+ OtherMethods.getLastNum(NameRegion4) +"\t"+IdCoatuu4+"\t"+"0"); 
            aPlaceRegion.add(IdRegion5+"\t"+OtherMethods.getFirstName(NameRegion5)+"\t"+1+"\t"+TypeRegion5+"\t"+ OtherMethods.getLastNum(NameRegion5) +"\t"+IdCoatuu5+"\t"+"0"); 
            aPlaceRegion.add(IdRegion6+"\t"+OtherMethods.getFirstName(NameRegion6)+"\t"+1+"\t"+TypeRegion6+"\t"+ OtherMethods.getLastNum(NameRegion6) +"\t"+IdCoatuu6+"\t"+"0"); 
            
        //    aPlaceRegion.add(IdPolis7+"\t"+OtherMethods.getFirstName(NamePolis7)+"\t"+1+"\t"+TypePolis7+"\t"+ OtherMethods.getLastNum(NamePolis7) +"\t"+IdCoatuu7+"\t"+"0"); 
          
          
              // формирование списка PlaceRegionTree
           //не треб... aPlacePolisTree.add(IdRegion3+"\t"+IdRegion2+"\t"+(IdRegion1)); 
        //  aPlaceRegionTree.add(IdPolis7+"\t"+IdRegion6+"\t"+    (IdRegion1));
        //  aPlaceRegionTree.add("---------------------");               
      //      aPlaceRegionTree.add(NameRegion1 +" "+NameRegion2 +" "+NameRegion3 +" "+NameRegion4+" "+NameRegion5+" "+NameRegion6  );
         //   aPlaceRegionTree.add("---------------------");               
          aPlaceRegionTree.add(IdRegion6+"\t"+IdRegion5+"\t"+    (IdRegion1));
          aPlaceRegionTree.add(IdRegion5+"\t"+IdRegion4+"\t"+    (IdRegion1));
          aPlaceRegionTree.add(IdRegion4+"\t"+IdRegion3+"\t"+    (IdRegion1));
          aPlaceRegionTree.add(IdRegion3+"\t"+IdRegion2+"\t"+    (IdRegion1));
          aPlaceRegionTree.add(IdRegion2+"\t"+IdRegion1+"\t"+    (IdRegion1));      
          aPlaceRegionTree.add("---------------------");      

          // формирование списка PlacePolis
           //  nID,   sPolis ,  nID_PlaceRegion,   nID_PlacePolisType,   sID_National  
          aPlacePolis.add((i+1)+"\t"+NamePolis7+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+IdCoatuu7);    
          
          // формирование списка PlaceArea
           //  nID,   nID_PlacePolis
      //    aPlaceArea.add((i+1)+"\t"+IdPolis7);    
          
          
            }
        
        //=======================================================
        }  
   
    
       System.out.println("===");
          System.out.println("===");
       System.out.println(aPlacePolis.size());
    
    //  aPlaceRegion = OtherMethods.delDuplicates(aPlaceRegion);
 // for (String temp: aPlaceRegion){ System.out.println(temp); }

//   aPlaceRegionTree = OtherMethods.delDuplicates(aPlaceRegionTree);
 // for (String temp: aPlaceRegionTree){ System.out.println(temp); }

  // aPlacePolis = OtherMethods.delDuplicates(aPlacePolis);
   //for (String temp: aPlacePolis){ System.out.println(temp); }
         

   //  aPlaceArea = OtherMethods.delDuplicates(aPlaceArea);
  // for (String temp: aPlaceArea){ System.out.println(temp); }

 //  for (String temp: list3){ System.out.println(temp); }
    
   }
    
}
