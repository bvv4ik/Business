
package com.bw.converter.ray;

import com.bw.converter.OtherMethods;
import com.bw.converter.OtherMethods;
import java.sql.SQLException;
import java.util.ArrayList;


 //// 4 ступени адреса   Область(или АР) --- Район --- Город(Міськрада) --- СМТ

public class Obl_ray_city_smt {
       
    
     public static boolean bShowList ;
     
        /*Входные Рабочие данные*/
    public ArrayList<String> list1 = new ArrayList<String>();
    public ArrayList<String> list2 = new ArrayList<String>();   
    public ArrayList<String> list3 = new ArrayList<String>();   
   
        /*Массивы готовых Выходных дынных, с дубликатами*/
    public ArrayList<String> list_PlacePolisTree = new ArrayList<String>();
    public ArrayList<String> list_PlaceRegion = new ArrayList<String>();
    public ArrayList<String> list_PlacePolis = new ArrayList<String>();
    
   
   public static void main(String args[]) throws SQLException {
        Obl_ray_city_smt o1 = new Obl_ray_city_smt();
        bShowList = true;
        o1.getData();
    }

     
        public void getData() throws SQLException {
            
            //Загружаем КОАТУ в 3 столбца  
 
           list1 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
           list2 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
           list3 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");

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

        String IdPolis7 = "";
        String TypePolis7 = "";
        String IdCoatuu7 = "";
        String NamePolis7 = "";

       list3 = OtherMethods.ClearGarbage(list3);  // Удаляем мусор из строк с названиями и делаем строку с большой буквы
    
    
    for (int i = 0; i <= list1.size()-1; i++) { /*проходимся по списку КОАТУ*/
 
        // это СМТ в единственном экземпляре в КОАТУ поєтому делаем вручную
        
        if (list1.get(i).endsWith("0120445100")){
         
         IdRegion1 = "1";
         TypeRegion1 = "1"; // АР
         NameRegion1 = "Крим"; // КРИМ
         IdCoatuu1 = "0100000000";

         IdRegion2 = "143";
         TypeRegion2 = "3";     // 3 это тип Район Области 
         NameRegion2 = "Бахчисарайський";
         IdCoatuu2 = "0120400000";

         IdRegion3 = "145";
         TypeRegion3 = "4";  // 4 єто міськрада города
         NameRegion3 = "Бахчисарай"; 
         IdCoatuu3 = "0120410100";

         IdPolis7 = "145";
         TypePolis7 = "2"; // 2 это ПГТ
         NamePolis7 = "Научний";
         IdCoatuu7 = "0120445100";
            
            
        }

        /*
        NamePolis7 = list3.get(i);     // запоминаем текущее название полиса
        IdCoatuu7 = list1.get(i);     // запоминаем 10-и значный текущий номер Коату
       
    
       //========================================================

        if (list1.get(i).endsWith("00000000")) { // если это ПЕРВАЯ ступень - Область, АР
               if (list3.get(i).startsWith("Крим")) {
                    TypeRegion1 = "1";                // выставляем тип АР в PlaceRegionType
                } else {
                    TypeRegion1 = "2";              // выставляем тип Область в PlaceRegionType
                }
                         
            NameRegion1 = OtherMethods.FirstCharUpper(list3.get(i));        // запоминаем название Области и делаем Заглавной 1 букву, остальное строчные
            IdRegion1 = Integer.toString(i+1);               // назначаем ИД для Области
            IdCoatuu1 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти

        }
       
        //========================================================
        if (list1.get(i).substring(5, 10).equals("00000")        // если это ВТОРАЯ ступень - Район
                & list1.get(i).substring(2, 3).equals("2")) {    // ищем только в Районах Области
                                                
            TypeRegion2 = "3";     // 3 это тип Район Области в PlaceRegionType
 
            NameRegion2 = OtherMethods.FirstCharUpper(list3.get(i));     // запоминаем название Района и делаем Заглавной 1 букву, остальное строчные
            IdRegion2 = Integer.toString(i + 1);           // назначаем ИД для Региона
            IdCoatuu2 = list1.get(i);                      // запоминаем номер КОАТУ
        }
    
    
       
      if      ("1".equals(list1.get(i).substring(5, 6))           // если это ТРЕТЬЯ ступень - Город
               & !"0000".equals(list1.get(i).substring(6, 10)) // не берем строку если в ней мусор       
              & "00".equals(list1.get(i).substring(8, 10))  // если это корень
                  ) {  
            NameRegion3 = OtherMethods.FirstCharUpper(list3.get(i));
            IdRegion3 = Integer.toString(i+1);          // назначаем ИД для Города
            IdCoatuu3 = list1.get(i);                 // запоминаем номер КОАТУ
            TypeRegion3 = "4";                       // выставляем тип Миськрада в PlaceRegionType
            
            IdPolis7 = Integer.toString(i+1);       // связка с полисами в PlaceRegionType
         }
   
      //-----------------------------------------------------
        
        if      ("45".equals(list1.get(i).substring(5, 7))           // если это 4 ступень - смт (подчиненное  Городу)
            & !"000".equals(list1.get(i).substring(7, 10))        // если это подчиненное
                ) {    
           
                   TypePolis7 = "2";  // 2 это ПГТ
                  //  if ("С".equals(list2.get(i)) ) {  TypePolis7 = "3";  }  // Тип Село в PlacePolisType
                  //  if ("Щ".equals(list2.get(i)) ) {  TypePolis7 = "4";  }  // Тип Селение   в PlacePolisType
 
           sResultRow = "\n"+IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1+
                       "\t\t"+IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2+ 
                       "\t\t"+IdRegion3+"\t"+1+"\t"+NameRegion3+"-"+"\t"+TypeRegion3+"\t"+IdCoatuu3+
                       "\t\t"+(i+1)+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+NamePolis7+"\t"+IdCoatuu7 ;  
           System.out.print(sResultRow);

                // формирование списка PlaceRegion
            list_PlaceRegion.add(IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1); 
            list_PlaceRegion.add(IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2);
            list_PlaceRegion.add(IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3);
                // формирование списка PlaceRegionTree
            list_PlacePolisTree.add(IdRegion3+"\t"+IdRegion2+"\t"+(IdRegion1)); 
            list_PlacePolisTree.add(IdRegion2+"\t"+IdRegion1+"\t"+(IdRegion1));
                // формирование списка PlacePolis
            list_PlacePolis.add((i+1)+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+NamePolis7+"\t"+IdCoatuu7); 
            
           }

        */
        
    }
     
    sResultRow = "\n"+IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1+
                       "\t\t"+IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2+ 
                       "\t\t"+IdRegion3+"\t"+1+"\t"+NameRegion3+"-"+"\t"+TypeRegion3+"\t"+IdCoatuu3+
                       "\t\t"+43000+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+NamePolis7+"\t"+IdCoatuu7 ;  
           System.out.print(sResultRow);
    
                 // формирование списка PlaceRegion
            list_PlaceRegion.add(IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1); 
            list_PlaceRegion.add(IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2);
            list_PlaceRegion.add(IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3);
                // формирование списка PlaceRegionTree
            list_PlacePolisTree.add(IdRegion3+"\t"+IdRegion2+"\t"+(IdRegion1)); 
            list_PlacePolisTree.add(IdRegion2+"\t"+IdRegion1+"\t"+(IdRegion1));
                // формирование списка PlacePolis
            list_PlacePolis.add((43000)+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+NamePolis7+"\t"+IdCoatuu7); 
            
              for (String temp: list_PlaceRegion){ System.out.println(temp); }
              for (String temp: list_PlacePolis){ System.out.println(temp); }
              for (String temp: list_PlacePolisTree){ System.out.println(temp); }
           
 }
        
}
