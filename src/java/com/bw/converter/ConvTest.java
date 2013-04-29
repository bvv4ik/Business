/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter;




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
public class ConvTest {
    
    
    public static void showList(ArrayList<String> list) {
        // String formatted = String.format("%07d", i); // Добавляем нули перед цифрами
        // Collections.sort(listTree1); // Сортировка массива
    //    HashSet set = new HashSet(list); // Удаляем дубликаты
    //    list.clear();
    //    list.addAll(set);
           for (int i2 = 0; i2 <= list.size() - 1; i2++) { // вывод PlaceRegionTree                     //------System.out.print(/*i2+" " + */ listTree1.get(i2).toString()+" "+listTree2.get(i2).toString()+" "+listTree3.get(i2).toString()+"\n");
               //   s = s+ (list.get(i2).toString())+"\n";
               System.out.print(list.get(i2) + "\n");
        }   
    }
    

    
    public static void main(String args[]) throws SQLException {

       // LoadTextFile o = new LoadTextFile();
        ArrayList<String> list1 = new ArrayList<String>();    list1 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
        ArrayList<String> list2 = new ArrayList<String>();    list2 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
        ArrayList<String> list3 = new ArrayList<String>();    list3 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");

        String sResultRow = "";
        String s = "";

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

        String IdPolis4 = "";
        String TypePolis4 = "";
        String IdCoatuu4 = "";
        String NamePolis = "";

        /*Выходные дынные*/
        ArrayList<String> list_PlacePolisTree = new ArrayList<String>();
        ArrayList<String> list_PlaceRegion = new ArrayList<String>();
        ArrayList<String> list_PlacePolis = new ArrayList<String>();


        list3 = OtherMethods.ClearGarbage(list3);  
    
    
    for (int i = 0; i <= list1.size()-1; i++) { /*проходимся по списку КОАТУ*/
 
        NamePolis = list3.get(i);     // запоминаем текущее название полиса
        IdCoatuu4 = list1.get(i);     // запоминаем 10-и значный текущий номер Коату
       
    
       //========================================================

        if (list1.get(i).endsWith("00000000")) { // если это ПЕРВАЯ ступень - Область, АР
              if (list3.get(i).startsWith("КРИМ")) {
                    TypeRegion1 = "1";
                } else {
                    TypeRegion1 = "2";
                }
                         
            NameRegion1 = OtherMethods.FirstCharUpper(list3.get(i));        // запоминаем название Области и делаем Заглавной 1 букву, остальное строчные
            IdRegion1 = Integer.toString(i+1);               // назначаем ИД для Области
            IdCoatuu1 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти

        }
       
        //========================================================
        if (list1.get(i).substring(5, 10).equals("00000")        // если это ВТОРАЯ ступень - Район
                & list1.get(i).substring(2, 3).equals("2")) {    // ищем только в Районах Области
                                                
            
              TypeRegion2 = "3";   
 
            NameRegion2 = OtherMethods.FirstCharUpper(list3.get(i));     // запоминаем название Района и делаем Заглавной 1 букву, остальное строчные
            IdRegion2 = Integer.toString(i + 1);           // назначаем ИД для Региона
            IdCoatuu2 = list1.get(i);                      // запоминаем номер КОАТУ
        }
      //========================================================  
//       
//         if ("8".equals(list1.get(i).substring(5, 6)) // если это ТРЕТЬЯ ступень - Сельрада
//                & !"00".equals(list1.get(i).substring(6, 8))   //
//                & "00".equals(list1.get(i).substring(8, 10))) {   // берем только название селрады
//            NameRegion3 = FirstCharUpper(list3.get(i));
//            IdRegion3 = Integer.toString(i+1);        // назначаем ИД для Сельрады
//            IdCoatuu3 = list1.get(i);                 // запоминаем номер КОАТУ
//
//            IdPolis4 = Integer.toString(i+1);        // переменная, связка с 4-й ступенью (полисами)
//            TypeRegion3 = "7";                       // выставляем Тип Сельрада в PlaceRegionType
//         }
//   
//      //-----------------------------------------------------
//        
//        if      (   "8".equals(list1.get(i).substring(5, 6)) & // если это 4 ступень - село, селение
//                  !"00".equals(list1.get(i).substring(6, 8))
//                & !"00".equals(list1.get(i).substring(8, 10))  ) {
//           
//                    if ("С".equals(list2.get(i)) ) {  TypePolis4 = "3";  }  // выставляем Тип Села  в PlacePolisType
//                    if ("Щ".equals(list2.get(i)) ) {  TypePolis4 = "4";  }  // выставляем Тип Селения   в PlacePolisType
// 
              /*Вывод на екран 4-х таблиц, 3 ступени региона и 1 полиса*/       
////         sResultRow = "\n"+IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1+
////                       "\t\t"+IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2+ 
////                       "\t\t"+IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3+
////                       "\t\t"+(i+1)+"\t"+IdPolis4+"\t"+TypePolis4+"\t"+NamePolis+"\t"+IdCoatuu4 ;  
//           //System.out.print(sResultRow);
//
//                // формирование готового списка PlaceRegion
//            list_PlaceRegion.add(IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1); 
//            list_PlaceRegion.add(IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2);
//            list_PlaceRegion.add(IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3);
//                // формирование списка PlaceRegionTree
//            list_PlacePolisTree.add(IdRegion3+"\t"+IdRegion2+"\t"+(IdRegion1)); 
//            list_PlacePolisTree.add(IdRegion2+"\t"+IdRegion1+"\t"+(IdRegion1));
//                // формирование списка PlacePolis
//            list_PlacePolis.add((i+1)+"\t"+IdPolis4+"\t"+TypePolis4+"\t"+FirstCharUpper(NamePolis)+"\t"+IdCoatuu4); 
//            }
//        
        //=======================================================
        
       
      
      //======================================================== ================================= 
       
         if ("55".equals(list1.get(i).substring(5, 7)) // если это ТРЕТЬЯ ступень - СМТ
                & !"000".equals(list1.get(i).substring(7, 10))  // не берем строку если в ней мусор: "СЕЛИЩА МІСЬКОГО ТИПУ ...Р-НУ "
                & "00".equals(list1.get(i).substring(8, 10))) {  // берем только само СМТ, но не подчиненных ему
            NameRegion3 = OtherMethods.FirstCharUpper(list3.get(i));
            IdRegion3 = Integer.toString(i+1);          // назначаем ИД для СМТ
            IdCoatuu3 = list1.get(i);                 // запоминаем номер КОАТУ

            IdPolis4 = Integer.toString(i+1);       // связка с полисами в PlaceRegionType
            TypeRegion3 = "6";                      // выставляем Тип Селищрада в PlaceRegionType
         }
   
      //-----------------------------------------------------
        
        if ("55".equals(list1.get(i).substring(5, 7)) // если это 4 ступень - село, селище (подчиненное  СМТ)
            & !"000".equals(list1.get(i).substring(7, 10))   // не берем строку если в ней мусор: "СЕЛИЩА МІСЬКОГО ТИПУ ...Р-НУ "
            & !"00".equals(list1.get(i).substring(8, 10))) {   // берем только само СМТ, но не подчиненных ему
           
                    if ("С".equals(list2.get(i)) ) {  TypePolis4 = "3";  }  // Тип Село в PlacePolisType
                    if ("Щ".equals(list2.get(i)) ) {  TypePolis4 = "4";  }  // Тип Селение   в PlacePolisType
 
         sResultRow = "\n"+IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1+
                       "\t\t"+IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2+ 
                       "\t\t"+IdRegion3+"\t"+1+"\t"+NameRegion3+"-"+"\t"+TypeRegion3+"\t"+IdCoatuu3+
                       "\t\t"+(i+1)+"\t"+IdPolis4+"\t"+TypePolis4+"\t"+NamePolis+"\t"+IdCoatuu4 ;  
           System.out.print(sResultRow);

                // формирование списка PlaceRegion
//            list_PlaceRegion.add(IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1); 
//            list_PlaceRegion.add(IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2);
//            list_PlaceRegion.add(IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3);
//                // формирование списка PlaceRegionTree
//            list_PlacePolisTree.add(IdRegion3+"\t"+IdRegion2+"\t"+(IdRegion1)); 
//            list_PlacePolisTree.add(IdRegion2+"\t"+IdRegion1+"\t"+(IdRegion1));
//                // формирование списка PlacePolis
//            list_PlacePolis.add((i+1)+"\t"+IdPolis4+"\t"+TypePolis4+"\t"+FirstCharUpper(NamePolis)+"\t"+IdCoatuu4); 
//         
           
        }
        //===========================================================================================
        
        
        
        
//        if ("55".equals(list1.get(i).substring(5, 7))           // если это ТРЕТЬЯ ступень - СМТ
//                & !"000".equals(list1.get(i).substring(7, 10))    // не берем строку если в ней мусор: "СЕЛИЩА МІСЬКОГО ТИПУ ...Р-НУ "
//                & "00".equals(list1.get(i).substring(8, 10))) {  // берем только само СМТ, но не подчиненных ему
//           // NameRegion3 = FirstCharUpper(list3.get(i));
//           // IdRegion3 = Integer.toString(i + 1);
//           // IdCoatuu3 = list1.get(i);                 // запоминаем номер КОАТУ
//
//           // IdPolis4 = Integer.toString(i+1);    // связка с полисами
//           // TypeRegion3 = "6";          // Тип Селищрада
//            TypePolis4 = "2";   // Тип СМТ в PlacePolisType
//            
//             sResultRow = 
//                     "\n"+IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1+
//                      "\t\t"+IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2+ 
//                     //  "\t\t"+IdRegion3+"\t"+1+"\t"+NameRegion3+"-"+"\t"+TypeRegion3+"\t"+IdCoatuu3+
//                       "\t\t"+(i+1)+"\t"+IdRegion2+"\t"+TypePolis4+"\t"+NamePolis+"\t"+IdCoatuu4 ;  
//           System.out.print(sResultRow);
//            
//         }
        
        
        
        
        
        
        
      }              // конец цикла
  
        //  Вывод данных
                 // showList(list_PlaceRegion);
                 // showList(list_PlacePolisTree);
                 // showList(list_PlacePolis);
                
                     //  System.out.print(listTree1.size());
                for (String temp: list_PlacePolis){ System.out.println(temp); }
                
// запуск из коммандной строки
//Process p;
//p = Runtime.getRuntime().exec("msconfig");
//p.waitFor();

// задержка
// === Thread.sleep(3000);

}
    
    
}
