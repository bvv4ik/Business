/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter.city;

import com.bw.converter.OtherMethods;
import java.sql.SQLException;
import java.util.ArrayList;

//// 3 ступени адреса:   Область(или АР) --- Город --- Район города 
// Для таблицы PlaceArea
public class Obl_city_area {

    public static boolean bShowList;
    /*Входные Рабочие данные*/
    public ArrayList<String> list1 = new ArrayList<String>();
    public ArrayList<String> list2 = new ArrayList<String>();
    public ArrayList<String> list3 = new ArrayList<String>();
    /*Массивы готовых Выходных дынных*/
    public ArrayList<String> aPlaceArea = new ArrayList<String>();

    public static void main(String args[]) throws SQLException {
        Obl_city_area o1 = new Obl_city_area();
        bShowList = true;
        o1.getData();
    }

    public void getData() throws SQLException {

        //Загружаем КОАТУ в 3 столбца  
        list1 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
        list2 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
        list3 = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");

        String sResultRow = "";


        /*3 ступени адреса*/
        String IdRegion1 = "";
        String TypeRegion1 = "";
        String NameRegion1 = "";
        String IdCoatuu1 = "";


        String IdPolis7 = "";
        String TypePolis7 = "";
        String IdCoatuu7 = "";
        String NamePolis7 = "";


        String IdArea = "";
        String IdCoatuuArea = "";
        String NameArea = "";



        list3 = OtherMethods.ClearGarbage(list3);  // Удаляем мусор из строк с названиями и делаем строку с большой буквы


        for (int i = 0; i <= list1.size() - 1; i++) { /*проходимся по списку КОАТУ*/


            IdCoatuuArea = list1.get(i);
            NameArea = list3.get(i);
            //========================================================

            if (list1.get(i).endsWith("00000000")) { // если это ПЕРВАЯ ступень - Область, АР
                if (list3.get(i).startsWith("Крим")) {
                    TypeRegion1 = "1";  // Тип АР в PlaceRegionType
                } else {
                    TypeRegion1 = "2";  // Тип ОБласти в PlaceRegionType
                }

                NameRegion1 = list3.get(i);                 // запоминаем название Области 
                IdRegion1 = Integer.toString(i + 1);               // назначаем ИД для Области
                IdCoatuu1 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти

            }



            //-----------------------------------------------------

            if (list1.get(i).substring(5, 10).equals("00000") // если это ВТОРАЯ ступень - Город
                    & list1.get(i).substring(2, 3).equals("1") // ищем только в Городах Области
                    & !"00".equals(list1.get(i).substring(3, 5))) {      // Убираем мусор типа "Міста автономної республіки крим"  

                TypePolis7 = "1";                            // это тип Города в PlacePolisType
                NamePolis7 = list3.get(i);                    // запоминаем название Района 
                IdPolis7 = Integer.toString(i + 1);           // назначаем ИД для Полиса
                IdCoatuu7 = list1.get(i);                    // запоминаем номер КОАТУ
            }



            if ("3".equals(list1.get(i).substring(5, 6)) // если это Район Города
                    & "00".equals(list1.get(i).substring(8, 10)) // убираем мусор
                    & !"0000".equals(list1.get(i).substring(6, 10)) // убираем мусор
                    & !list3.get(i).startsWith("Райони м.")) // убираем мусор
            {


                IdArea = Integer.toString(i + 1);  // назначаем  ИД для Ариа

                /*Вывод на екран 1 полиса*/
                //        sResultRow = "\n"+IdArea+"\t"+IdPolis7+"\t"+NameArea+"\t"+IdCoatuuArea;

                /*Вывод на екран 3-х таблиц, 2 ступени региона и 1 Района города*/

                if (bShowList) {
                    sResultRow = "\n" + IdRegion1 + "\t" + 1 + "\t" + NameRegion1 + "\t" + TypeRegion1 + "\t" + IdCoatuu1
                            + "\t\t" + (i + 1) + "\t" + IdPolis7 + "\t" + TypePolis7 + "\t" + NamePolis7 + "\t" + IdCoatuu7
                            + "\t\t" + IdArea + "\t" + IdPolis7 + "\t" + NameArea + "\t" + IdCoatuuArea;
                    System.out.print(sResultRow);
                }


                // формирование готового списка PlaceArea

                aPlaceArea.add(IdArea + "\t" + IdPolis7 + "\t" + NameArea + "\t" + IdCoatuuArea);

                // aPlaceRegion.add(IdRegion1+"\t"+1+"\t"+NameRegion1+"\t"+TypeRegion1+"\t"+IdCoatuu1); 
                //не треб...  aPlaceRegion.add(IdRegion2+"\t"+1+"\t"+NameRegion2+"\t"+TypeRegion2+"\t"+IdCoatuu2);
                //не треб...  aPlaceRegion.add(IdRegion3+"\t"+1+"\t"+NameRegion3+"\t"+TypeRegion3+"\t"+IdCoatuu3);

                // формирование списка PlaceRegionTree
                //не треб... aPlacePolisTree.add(IdRegion3+"\t"+IdRegion2+"\t"+(IdRegion1)); 
                //не треб...  aPlacePolisTree.add(IdRegion2+"\t"+IdRegion1+"\t"+(IdRegion1));

                // формирование списка PlacePolis
                //aPlacePolis.add((i+1)+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+NamePolis7+"\t"+IdCoatuu7); 




            }



            //=======================================================
        }

    }
}
