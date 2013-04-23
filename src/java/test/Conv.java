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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Sergey
 */
public class Conv {
    
    
    public static void showList(ArrayList<String> list) {
        // String formatted = String.format("%07d", i); // Добавляем нули
        // Collections.sort(listTree1); // Сортировка массива
        HashSet set = new HashSet(list); // Удаляем дубликаты
        list.clear();
        list.addAll(set);
           for (int i2 = 0; i2 <= list.size() - 1; i2++) { // вывод PlaceRegionTree                     //------System.out.print(/*i2+" " + */ listTree1.get(i2).toString()+" "+listTree2.get(i2).toString()+" "+listTree3.get(i2).toString()+"\n");
               //   s = s+ (list.get(i2).toString())+"\n";
               System.out.print(list.get(i2) + "\n");
        }   
    }
    
    public static String FirstCharUpper(String str) {
        str = str.toLowerCase();                     // всю строку маленькой
        char[] temp = str.toCharArray();             // строку в массив символов
        temp[0] = Character.toUpperCase(temp[0]);    // первую букву заглавной
        String text = String.valueOf(temp);          // массвив символов в строку
      return (text);
    }
    
    public static void main(String args[]) throws SQLException {

        LoadTextFile o = new LoadTextFile();
        ArrayList<String> list1 = new ArrayList<String>();    list1 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
        ArrayList<String> list2 = new ArrayList<String>();    list2 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
        ArrayList<String> list3 = new ArrayList<String>();    list3 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");

        String sResultRow = "";
        String s = "";

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

        String IdPolis4 = "";
        String TypePolis4 = "";
        String IdCoatuu4 = "";
        String NamePolis = "";

        ArrayList<String> list_PlacePolisTree = new ArrayList<String>();
        ArrayList<String> list_PlaceRegion = new ArrayList<String>();
        ArrayList<String> list_PlacePolis = new ArrayList<String>();


        
    
    
    for (int i = 0; i <= list1.size()-1/*1500*/; i++) {
 
        NamePolis = list3.get(i);     // текущий полис
        IdCoatuu4 = list1.get(i);

        // удаляем из строки:  /М.СІМФЕРОПОЛЬ и.д
        if (list3.get(i).lastIndexOf("/") != -1) {
            String sTemp = list3.get(i);
            sTemp = list3.get(i).substring(0, list3.get(i).lastIndexOf("/"));
            list3.set(i, sTemp);
        }

       //========================================================

        if (list1.get(i).endsWith("00000000")) { // если это ПЕРВАЯ ступень - область, АР

            if (list3.get(i).startsWith("АВТОНОМНА РЕСПУБЛІКА ")) {  //если есть фраза "АВТОНОМНА РЕСПУБЛІКА"
                TypeRegion1 = "1";                                                // запоминаем тип
                String sTemp = list3.get(i).replace("АВТОНОМНА РЕСПУБЛІКА ", ""); // удаляем фразу
                list3.set(i, sTemp);  //запоминаем
            }
            if (list3.get(i).contains(" ОБЛАСТЬ")) {
                TypeRegion1 = "2";
                String sTemp = list3.get(i).replace(" ОБЛАСТЬ", "");   // удаляем фразу " ОБЛАСТЬ"
                list3.set(i, sTemp);                                  //запоминаем
            }
             
            NameRegion1 = FirstCharUpper(list3.get(i));              // запоминаем название Области
            IdCoatuu1 = list1.get(i);                // запоминаем номер КОАТУ
            IdRegion1 = Integer.toString(i + 1);     // назначаем ИД

        }
       
        //========================================================
        
        if (list1.get(i).substring(5, 10).equals("00000") // если это ВТОРАЯ ступень - Район
                & list1.get(i).substring(2, 3).equals("2")) {

            if (list3.get(i).contains(" РАЙОН")) {
                TypeRegion2 = "3";                                          // тип Район
                String sTemp = list3.get(i).replace(" РАЙОН", "");           // удаляем фразу " ОБЛАСТЬ"
                list3.set(i, sTemp);                                        //запоминаем
            }

            NameRegion2 = FirstCharUpper(list3.get(i));
            IdRegion2 = Integer.toString(i + 1);
            IdCoatuu2 = list1.get(i);              // запоминаем номер КОАТУ
        }
      
      //========================================================  
       
         if ("8".equals(list1.get(i).substring(5, 6)) // если это ТРЕТЬЯ ступень - Сельрада
                & !"00".equals(list1.get(i).substring(6, 8))
                & "00".equals(list1.get(i).substring(8, 10))) {
            NameRegion3 = FirstCharUpper(list3.get(i));
            IdRegion3 = Integer.toString(i + 1);
            IdCoatuu3 = list1.get(i);                 // запоминаем номер КОАТУ

            IdPolis4 = Integer.toString(i + 1);    // связка с полисами
        }

        TypeRegion3 = "7";          // Тип Сельрада
    
         //========================================================  
        
        if      (   "8".equals(list1.get(i).substring(5, 6)) & // если это 4 ступень - село, селение
                  !"00".equals(list1.get(i).substring(6, 8))
                & !"00".equals(list1.get(i).substring(8, 10))  ) {
           
                    if ("С".equals(list2.get(i)) ) {  TypePolis4 = "3";  }  // Тип Село
                    if ("Щ".equals(list2.get(i)) ) {  TypePolis4 = "4";  }  // Тип Селение  
 
//         sResultRow = "\n"+IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1+
//                       "\t\t"+IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2+ 
//                       "\t\t"+IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3+
//                       "\t\t"+(i+1)+"\t"+IdPolis4+"\t"+TypePolis4+"\t"+NamePolis+"\t"+IdCoatuu4 ;  
           //System.out.print(sResultRow);

                // формирование списка PlaceRegion
            list_PlaceRegion.add(IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1); 
            list_PlaceRegion.add(IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2);
            list_PlaceRegion.add(IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3);
                // формирование списка PlaceRegionTree
            list_PlacePolisTree.add(IdRegion3+"\t"+IdRegion2+"\t"+(IdRegion1)); 
            list_PlacePolisTree.add(IdRegion2+"\t"+IdRegion1+"\t"+(IdRegion1));
                // формирование списка PlacePolis
            list_PlacePolis.add((i+1)+"\t"+IdPolis4+"\t"+TypePolis4+"\t"+FirstCharUpper(NamePolis)+"\t"+IdCoatuu4); 
            }
      }              // конец цикла
  
        //  Вывод данных
                 // showList(list_PlaceRegion);
                 // showList(list_PlacePolisTree);
                 // showList(list_PlacePolis);
                
                     //  System.out.print(listTree1.size());
                for (String temp: list_PlaceRegion){ System.out.println(temp); }
                
     
}
    
    
}
