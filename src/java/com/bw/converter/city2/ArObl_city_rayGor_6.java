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
public class ArObl_city_rayGor_6 {

    public static boolean bShowList;
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
        ArObl_city_rayGor_6 o1 = new ArObl_city_rayGor_6();
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


        String IdPolis7 = "";
        String TypePolis7 = "";
        String IdCoatuu7 = "";
        String NamePolis7 = "";

        int in = 0;

        //  OtherMethods.AddLinkRay(list1, list2, list3);

        // Исправляем ошибки и связываем получаем ссылку полиса на рут региона
        list3 = OtherMethods.FindRegionPolis(list1, list3);

        list3 = OtherMethods.ClearGarbage(list3);  // Удаляем мусор из строк с названиями и делаем строку с большой буквы


        for (int i = 0; i <= list1.size() - 1; i++) { /*проходимся по списку КОАТУ*/

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
                IdRegion1 = Integer.toString(i + 1);               // назначаем ИД для Области
                IdCoatuu1 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти
            }

            //-----------------------------------------------------
            // МІСТА АВТОНОМНОЇ РЕСПУБЛІКИ КРИМ [0110000000]
            if (list1.get(i).substring(3, 10).equals("0000000") // если это 2 ступень -  // МІСТА АВТОНОМНОЇ РЕСПУБЛІКИ КРИМ [0110000000]
                    & list1.get(i).substring(2, 3).equals("1")) {        // !!! ищем только в Городах Области  и АР          

                /*  ??Міста какие типа???*/
                TypeRegion2 = "10";                      // = тип Міста автономної ... в PlacePolisType

                NameRegion2 = list3.get(i);                        // запоминаем название    
                IdRegion2 = Integer.toString(i + 1);                 // назначаем ИД для Области
                IdCoatuu2 = list1.get(i);                          // запоминаем номер КОАТУ ОБласти
            }

            //-----------------------------------------------------
            //0110100000		СІМФЕРОПОЛЬ         
            if (list1.get(i).substring(5, 10).equals("00000") // если это 3 ступень - конкретный город СІМФЕРОПОЛЬ
                    & list1.get(i).substring(2, 3).equals("1") // !!! ищем только в Городах Области и АР
                    ) {
                TypeRegion3 = "4";                 // здесь заменяем на  "Миськрада города"
                NameRegion3 = list3.get(i);//+"-=-";              // запоминаем название    
                IdRegion3 = Integer.toString(i + 1);               // назначаем ИД для Области
                IdCoatuu3 = list1.get(i);
            }

            //-----------------------------------------------------
            //  0110130000	РАЙОНИ М.СІМФЕРОПОЛЯ
            if ((list1.get(i).substring(5, 10).equals("30000") // если это 4 ступень -РАЙОНИ миста
                    | list1.get(i).substring(5, 10).equals("36000"))
                    //   &  list1.get(i).substring(6, 7).equals("0")
                    & list1.get(i).substring(2, 3).equals("1")) {       // !!! ищем только в Городах Области и АР

                TypeRegion4 = "10";
                NameRegion4 = list3.get(i);              // запоминаем название    
                IdRegion4 = Integer.toString(i + 1);               // назначаем ИД для Области
                IdCoatuu4 = list1.get(i);

                //  IdPolis7 = Integer.toString(i+1);             //особ. случ не надо для связи с полисами
            }

            //-----------------------------------------------------
            // 0110136300  Р	ЗАЛІЗНИЧНИЙ
            if (list2.get(i).equals("Р") // если это 7 ступень - конкретній Район 
                    & list1.get(i).substring(2, 3).equals("1") // !!! ищем только в Городах Области и АР
                    ) {

                TypePolis7 = "5";                      // особ. случ. 5 =  тип "район Города" в PlaceRegionType!

                IdPolis7 = Integer.toString(i + 1); // особ. случ

                /*Вывод на екран 2-х таблиц, 1 ступени региона и 1 полиса*/
                if (bShowList) {
                    sResultRow = "\n" + IdRegion1 + "\t" + 1 + "\t" + NameRegion1 + "\t" + TypeRegion1 + "\t" + IdCoatuu1
                            + "\t\t" + IdRegion2 + "\t" + 1 + "\t" + NameRegion2 + "\t" + TypeRegion2 + "\t" + IdCoatuu2
                            + "\t\t" + IdRegion3 + "\t" + 1 + "\t" + NameRegion3 + "\t" + TypeRegion3 + "\t" + IdCoatuu3
                            + "\t\t" + IdRegion4 + "\t" + 1 + "\t" + NameRegion4 + "\t" + TypeRegion4 + "\t" + IdCoatuu4
                            + "\t\t" +/* (i+1)  + "\t" +*/ IdPolis7 + "\t" + TypePolis7 + "\t" + NamePolis7 + "\t" + IdCoatuu7;
                    System.out.print(sResultRow);
                }


                // формирование готового списка PlaceRegion
                // nID   sRegion   nID_PlaceCountry   nID_PlaceRegionType  nID_PlacePolis_Root   sID_National  nID_PlaceArea
                aPlaceRegion.add(IdRegion1 + "\t" + OtherMethods.getFirstName(NameRegion1) + "\t" + 1 + "\t" + TypeRegion1 + "\t" + OtherMethods.getLastNum(NameRegion1) + "\t" + IdCoatuu1 + "\t" + "0");
                aPlaceRegion.add(IdRegion2 + "\t" + OtherMethods.getFirstName(NameRegion2) + "\t" + 1 + "\t" + TypeRegion2 + "\t" + OtherMethods.getLastNum(NameRegion2) + "\t" + IdCoatuu2 + "\t" + "0");
                aPlaceRegion.add(IdRegion3 + "\t" + OtherMethods.getFirstName(NameRegion3) + "\t" + 1 + "\t" + TypeRegion3 + "\t" + OtherMethods.getLastNum(NameRegion3) + "\t" + IdCoatuu3 + "\t" + "0");
                aPlaceRegion.add(IdRegion4 + "\t" + OtherMethods.getFirstName(NameRegion4) + "\t" + 1 + "\t" + TypeRegion4 + "\t" + OtherMethods.getLastNum(NameRegion4) + "\t" + IdCoatuu4 + "\t" + "0");
                // особ. случ.
                aPlaceRegion.add(IdPolis7 + "\t" + OtherMethods.getFirstName(NamePolis7) + "\t" + 1 + "\t" + TypePolis7 + "\t" + OtherMethods.getLastNum(NamePolis7) + "\t" + IdCoatuu7 + "\t" + "0");


                // формирование списка PlaceRegionTree
                // особ. случ.
                aPlaceRegionTree.add(IdPolis7 + "\t" + IdRegion4 + "\t" + (IdRegion1));

                aPlaceRegionTree.add(IdRegion4 + "\t" + IdRegion3 + "\t" + (IdRegion1));
                aPlaceRegionTree.add(IdRegion3 + "\t" + IdRegion2 + "\t" + (IdRegion1));
                aPlaceRegionTree.add(IdRegion2 + "\t" + IdRegion1 + "\t" + (IdRegion1));
                aPlaceRegionTree.add("--------------------");

                // формирование списка PlacePolis
                //  nID,   sPolis ,  nID_PlaceRegion,   nID_PlacePolisType,   sID_National  
                //  aPlacePolis.add((i+1)+"\t"+NamePolis7+"\t"+IdPolis7+"\t"+TypePolis7+"\t"+IdCoatuu7);    
                // особ. случ. не надо

                // формирование списка PlaceArea
                //  nID,   nID_PlacePolis
                in++;
                aPlaceArea.add((in) + "\t" + IdPolis7);
                // aPlaceArea.add("dfdfdfdf");    
                // System.out.print(IdPolis7);


            }

            //=======================================================
        }


        System.out.println("===");
        System.out.println("===");
        System.out.println(aPlaceArea.size());

        //  aPlaceRegion = OtherMethods.delDuplicates(aPlaceRegion);
        //for (String temp: aPlaceRegion){ System.out.println(temp); }

        //  aPlaceRegionTree = OtherMethods.delDuplicates(aPlaceRegionTree);
//   for (String temp: aPlaceRegionTree){ System.out.println(temp); }

        // aPlacePolis = OtherMethods.delDuplicates(aPlacePolis);
        //for (String temp: aPlacePolis){ System.out.println(temp); }


        //  aPlaceArea = OtherMethods.delDuplicates(aPlaceArea);
        // for (String temp: aPlaceArea){ System.out.println(temp); }



    }
}
