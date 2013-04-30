/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter.obl;

import com.bw.converter.OtherMethods;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.StringTokenizer;


////  3 ступени адреса:   Область(или АР) --- Район --- СМТ 

public class Obl_ray_smt {
  
    
      /*Входные Рабочие данные*/
    public ArrayList<String> list1 = new ArrayList<String>();
    public ArrayList<String> list2 = new ArrayList<String>();   
    public ArrayList<String> list3 = new ArrayList<String>();   
   
        /*Массивы готовых Выходных дынных, с дубликатами*/
    public ArrayList<String> list_PlacePolisTree = new ArrayList<String>();
    public ArrayList<String> list_PlaceRegion = new ArrayList<String>();
    public ArrayList<String> list_PlacePolis = new ArrayList<String>();
    
   
       public static void main(String args[]) throws SQLException {
        Obl_ray_smt o1 = new Obl_ray_smt();
        o1.getData();
    }
    
      public void getData() throws SQLException {
      
       //Загружаем КОАТУ в 3 столбца (2-й не задействуется)        
          list1 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");  
         // list2 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");  
          list3 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");
      
        String sResultRow = "";
      
        
        String IdRegion1 = "";
        String TypeRegion1 = "";
        String NameRegion1 = "";
        String IdCoatuu1 = "";

        String IdRegion2 = "";
        String TypeRegion2 = "";
        String NameRegion2 = "";
        String IdCoatuu2 = "";


        String IdPolis7 = "";
        String TypePolis7 = "";
        String IdCoatuu7 = "";
        String NamePolis7 = "";

    

        list3 = OtherMethods.ClearGarbage(list3);  // Удаляем мусор из строк с названиями и делаем строку с большой буквы 
    
    for (int i = 0; i <= list1.size()-1; i++) {          /*проходимся по списку КОАТУ*/
 
        NamePolis7 = list3.get(i);                 // запоминаем текущее название полиса
        IdCoatuu7 = list1.get(i);                 // запоминаем 10-и значный текущий номер Коату
      
       //========================================================

        if (list1.get(i).endsWith("00000000")) {              // если это ПЕРВАЯ ступень - Область, АР
                if (list3.get(i).startsWith("Крим")) {
                    TypeRegion1 = "1";                // выставляем тип АР в PlaceRegionType
                } else {
                    TypeRegion1 = "2";              // выставляем тип Область в PlaceRegionType
                }

                NameRegion1 = OtherMethods.FirstCharUpper(list3.get(i));        // запоминаем название Области и делаем Заглавной 1 букву, остальное строчные
                IdRegion1 = Integer.toString(i+1);                  // назначаем ИД для Области
                IdCoatuu1 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти

        }
       
        //========================================================
        if (list1.get(i).substring(5, 10).equals("00000")        // если это ВТОРАЯ ступень - Район
                & list1.get(i).substring(2, 3).equals("2")) {    // ищем только в Районах Области!!!
            
            TypeRegion2 = "3";         // выставляем тип Район Области в PlaceRegionType
            NameRegion2 = OtherMethods.FirstCharUpper(list3.get(i));     // запоминаем название Района и делаем Заглавной 1 букву, остальное строчные
            IdRegion2 = Integer.toString(i+1);           // назначаем ИД для Региона
            IdCoatuu2 = list1.get(i);                      // запоминаем номер КОАТУ
            
            IdPolis7 = Integer.toString(i+1);  // для связи с полисами
        }
    
        //======================================================================
        
        if ("55".equals(list1.get(i).substring(5, 7))           // если это ТРЕТЬЯ ступень - СМТ
                & !"000".equals(list1.get(i).substring(7, 10))    // не берем строку если в ней мусор: "СЕЛИЩА МІСЬКОГО ТИПУ ...Р-НУ "
                & "00".equals(list1.get(i).substring(8, 10))) {  // берем только само СМТ, но не подчиненных ему
           
            TypePolis7 = "2";   // выставляем Тип СМТ в PlacePolisType
            
             sResultRow = 
                     "\n"+IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1+  
                     "\t\t"+IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2+ 
                     
                     "\t\t"+(i+1)+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+NamePolis7+"\t"+IdCoatuu7 ;  
           System.out.print(sResultRow);
            
               // формирование списка PlaceRegion
            list_PlaceRegion.add(IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1); 
            list_PlaceRegion.add(IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2);        
                // формирование списка PlaceRegionTree
            list_PlacePolisTree.add(IdRegion2+"\t"+IdRegion1+"\t"+(IdRegion1));
                // формирование списка PlacePolis
            list_PlacePolis.add((i+1)+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+NamePolis7+"\t"+IdCoatuu7); 
           
         }
       
      }              // конец цикла
  
        //  Вывод данных
                 // showList(list_PlaceRegion);
                 // showList(list_PlacePolisTree);
                 // showList(list_PlacePolis);
                
                     //  System.out.print(listTree1.size());
            //    for (String temp: list_PlacePolis){ System.out.println(temp); }
                
      }
    
}