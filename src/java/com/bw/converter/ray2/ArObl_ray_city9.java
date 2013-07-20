/**
 * @author Sergey Belyavtsev
 */
package com.bw.converter.ray2;

import com.bw.converter.OtherMethods;
import java.sql.SQLException;
import java.util.ArrayList;

 
////  4 ступени адреса:  Область(или АР) --- Район --- Сільрада --- Село/селище 
   

public class ArObl_ray_city9 {
    
     public static boolean bShowList ;
  
       /*Входные Рабочие данные из Коату*/
    public ArrayList<String> list1 = new ArrayList<String>();
    public ArrayList<String> list2 = new ArrayList<String>();   
    public ArrayList<String> list3 = new ArrayList<String>();   
    public ArrayList<String> list4 = new ArrayList<String>();   
    
       /*Массивы готовых Выходных дынных, с дубликатами*/
    public ArrayList<String> aPlaceRegion = new ArrayList<String>();
    public ArrayList<String> aPlaceRegionTree = new ArrayList<String>();
    public ArrayList<String> aPlacePolis = new ArrayList<String>();
 
    
    
    public static void main(String args[]) throws SQLException {
        ArObl_ray_city9 o1 = new ArObl_ray_city9();
        bShowList = true;
        o1.getData();
    }

    
    public void getData() throws SQLException {

           //Загружаем КОАТУ в 3 столбца   
           list1 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
           list2 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
           list3 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");
           list4 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/4.txt");

        String sResultRow = "";

        /*4 ступени адреса*/
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


        
        
        String IdPolis7 = "";
        String TypePolis7 = "";
        String NamePolis7 = "";
        String IdCoatuu7 = "";
        
     
        // !!!  получаем связку района города и дальнеших полисов
        //   OtherMethods.AddLinkRay(list1, list2, list3);

        
         // Исправляем ошибки и связываем получаем ссылку полиса на рут региона
        list3 = OtherMethods.FindRegionPolis(list1, list3);
        
//        for (String temp: list3){          
//            if (temp.contains("==="))
//            System.out.println(temp); }
        
        list3 = OtherMethods.ClearGarbage(list3);   // Удаляем мусор из строк с названиями и делаем строку с большой буквы

        
    for (int i = 0; i <= list1.size()-1; i++) {                     /*проходимся по списку КОАТУ*/
   // for (int i = 0; i <= 2000/*list1.size()-1*/; i++) {              /*проходимся по списку КОАТУ*/
        NamePolis7 = list3.get(i);                      // запоминаем текущее название полиса
        IdCoatuu7 = list1.get(i);                       // запоминаем 10-и значный текущий номер Коату
    
       //========================================================
       //АВТОНОМНА РЕСПУБЛІКА или ОБЛАСТЬ  0100000000
        if (list1.get(i).endsWith("00000000")) { // если это 1 ступень - Область, АР
              if (list3.get(i).contains("КРИМ")) {
                    TypeRegion1 = "1";                // 1 = тип АР в PlaceRegionType
                } else {
                    TypeRegion1 = "2";              // 2 = тип Область в PlaceRegionType
                }
            NameRegion1 = list3.get(i);                  // запоминаем название Области 
            IdRegion1 = Integer.toString(i+1);           // назначаем ИД для Области
            IdCoatuu1 = list1.get(i);                    // запоминаем номер КОАТУ ОБласти
        }

        //========================================================
       //  РАЙОНИ АВТОНОМНОЇ РЕСПУБЛІКИ КРИМ [0120000000]
       if (list1.get(i).substring(3, 10).equals("0000000")        // если это 2 ступень - Районы области
                & list1.get(i).substring(2, 3).equals("2")) {    // ищем только в Районах Области
                                                
            TypeRegion2 = "10";                         // 10 = тип "Районы" 
           
            NameRegion2 = list3.get(i);               // запоминаем название Района // РАЙОНИ АВТОНОМНОЇ РЕСПУБЛІКИ КРИМ [0120000000]
            IdRegion2 = Integer.toString(i + 1);           // назначаем ИД для Региона
            IdCoatuu2 = list1.get(i);                      // запоминаем номер КОАТУ
        }
        
        //========================================================
        //  БІЛОГІРСЬКИЙ РАЙОН/М.БІЛОГІРСЬК [0120700000]
        if (list1.get(i).substring(5, 10).equals("00000")        // если это 3 ступень - Конкретный Район
                & list1.get(i).substring(2, 3).equals("2")) {    // ищем только в Районах Области
                                                
            NameRegion3 = list3.get(i);               // запоминаем название Района 
            IdRegion3 = Integer.toString(i + 1);           // назначаем ИД для Региона
            IdCoatuu3 = list1.get(i);                      // запоминаем номер КОАТУ
            
            TypeRegion3 = "3";                         // 3 = тип Район Области в PlaceRegionType
        }

        //========================================================  
        // МІСТА РАЙОННОГО ПІДПОРЯДКУВАННЯ БІЛОГІРСЬКОГО Р-НУ [0120710000]
         if ("10000".equals(list1.get(i).substring(5, 10)) // если это 4 ступень - МІСТА  Районов
            & list1.get(i).substring(2, 3).equals("2")    // ищем только в Районах Области и АР
                 ) {   
            
            NameRegion4 = list3.get(i);
            IdRegion4 = Integer.toString(i+1);        // назначаем ИД для Сельрады
            IdCoatuu4 = list1.get(i);                 // запоминаем номер КОАТУ ???? пусть будет, если надо будет удалить это запись
            TypeRegion4 = "10";                     // 11 = Тип "Миста" ...
            
            IdPolis7 = Integer.toString(i+1);        // переменная, связка с 7-й ступенью (полисами)
         }
     
                
         //-----------------------------------------------------
        // м. БІЛОГІРСЬК [0120710100]
        if      ( ("10".equals(list1.get(i).substring(5, 7)) 
                  | "11".equals(list1.get(i).substring(5, 7)) 
                ) 
                
                &  !"00".equals(list1.get(i).substring(6, 8))   // если это 7 ступень - конкр. полис м.БІЛОГІРСЬК
                & "00".equals(list1.get(i).substring(8, 10))  // и это НЕ село!
                & list1.get(i).substring(2, 3).equals("2")   // ищем только в Районах Области и АР
                ) {  
           
            TypePolis7 = "1";    // 1 = Тип "Город"
            
              /*Вывод на экран 5-х таблиц, 4 ступени региона и 1 полиса.
               данные в формате Экселя. можно вставлять в Эксель в виде таблицы */
          if (bShowList) {
        sResultRow = "\n" + IdRegion1 + "\t" + 1 + "\t" + NameRegion1 + "\t" + TypeRegion1 + "\t" + IdCoatuu1
                 + "\t\t" + IdRegion2 + "\t" + 1 + "\t" + NameRegion2 + "\t" + TypeRegion2 + "\t" + IdCoatuu2
                 + "\t\t" + IdRegion3 + "\t" + 1 + "\t" + NameRegion3 + "\t" + TypeRegion3 + "\t" + IdCoatuu3
                 + "\t\t" + IdRegion4 + "\t" + 1 + "\t" + NameRegion4 + "\t" + TypeRegion4 + "\t" + IdCoatuu4
               
                    + "\t\t" + (i+1)  + "\t" + IdPolis7 + "\t" + TypePolis7 + "\t" + NamePolis7 + "\t" + IdCoatuu7;
        System.out.print(sResultRow);
    }

      // формирование готового списка PlaceRegion
            // nID   sRegion   nID_PlaceCountry   nID_PlaceRegionType  nID_PlacePolis_Root   sID_National  nID_PlaceArea
            aPlaceRegion.add(IdRegion1+"\t"+OtherMethods.getFirstName(NameRegion1)+"\t"+1+"\t"+TypeRegion1+"\t"+ OtherMethods.getLastNum(NameRegion1) +"\t"+IdCoatuu1+"\t"+"0"); 
            aPlaceRegion.add(IdRegion2+"\t"+OtherMethods.getFirstName(NameRegion2)+"\t"+1+"\t"+TypeRegion2+"\t"+ OtherMethods.getLastNum(NameRegion2) +"\t"+IdCoatuu2+"\t"+"0");
            aPlaceRegion.add(IdRegion3+"\t"+OtherMethods.getFirstName(NameRegion3)+"\t"+1+"\t"+TypeRegion3+"\t"+ OtherMethods.getLastNum(NameRegion3) +"\t"+IdCoatuu3+"\t"+"0");
            aPlaceRegion.add(IdRegion4+"\t"+OtherMethods.getFirstName(NameRegion4)+"\t"+1+"\t"+TypeRegion4+"\t"+ OtherMethods.getLastNum(NameRegion4) +"\t"+IdCoatuu4+"\t"+"0");
           
           
     // формирование списка PlaceRegionTree
            //nID_PlaceRegion,   nID_PlaceRegion_Node,   nID_PlaceRegion_Root
         
            aPlaceRegionTree.add(IdRegion4+"\t"+IdRegion3+"\t"   +(IdRegion1)); 
            aPlaceRegionTree.add(IdRegion3+"\t"+IdRegion2+"\t"   +(IdRegion1)); 
            aPlaceRegionTree.add(IdRegion2+"\t"+IdRegion1+"\t"   +(IdRegion1));
            
     // формирование списка PlacePolis
            //  nID,   sPolis ,  nID_PlaceRegion,   nID_PlacePolisType,   sID_National  
            aPlacePolis.add((i+1)+"\t"+NamePolis7+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+IdCoatuu7); 
            
              // формирование списка PlaceArea
          //  aPlaceArea.add(IdRegion4+"\t"+NameRegion4+"\t"+TypeRegion4+"\t"+IdCoatuu4); 
          //  aPlaceArea.add(IdRegion5+"\t"+NameRegion5+"\t"+TypeRegion5+"\t"+IdCoatuu5); 
            
            }
        
           //=======================================================
        }  
        System.out.println("---");
        System.out.println("---");
      //  System.out.println(aPlaceRegion.size()/4);
        System.out.println(aPlacePolis.size());
   
 // aPlaceRegion = OtherMethods.delDuplicates(aPlaceRegion);
 //  for (String temp: aPlaceRegion){ System.out.println(temp); }

//   aPlaceRegionTree = OtherMethods.delDuplicates(aPlaceRegionTree);
//   for (String temp: aPlaceRegionTree){ System.out.println(temp); }

 //  aPlacePolis = OtherMethods.delDuplicates(aPlacePolis);
 //  for (String temp: aPlacePolis){ System.out.println(temp); }
          //for (String temp: aPlaceArea){ System.out.println(temp); }
   }
    
}
