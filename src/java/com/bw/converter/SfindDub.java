/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter;

import com.bw.converter.city2.*;
import com.bw.converter.OtherMethods;
import com.bw.converter.OtherMethods;
import java.sql.SQLException;
import java.util.ArrayList;

//// 2 ступени адреса:   Область(или АР) --- Город 
public class SfindDub {

    public static boolean bShowList;
    /*Входные Рабочие данные*/
    public ArrayList<String> list1 = new ArrayList<String>();
    public ArrayList<String> list2 = new ArrayList<String>();
    public ArrayList<String> list3 = new ArrayList<String>();
    public ArrayList<String> list4 = new ArrayList<String>();
    public ArrayList<String> listAll = new ArrayList<String>();
    /*Массивы готовых Выходных дынных, с дубликатами*/
    public ArrayList<String> aPlaceRegion = new ArrayList<String>();
    public ArrayList<String> aPlaceRegionTree = new ArrayList<String>();
    public ArrayList<String> aPlacePolis = new ArrayList<String>();
    public ArrayList<String> aResult = new ArrayList<String>();

    public static void main(String args[]) throws SQLException {
        SfindDub o1 = new SfindDub();
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

        listAll = OtherMethods.ListFromFile("D:/Java_study/---Projects/KOATUU/5.txt");





        for (int i = 0; i <= listAll.size() - 1; i++) { /*проходимся по своему списку номеров Коату*/

            String s = listAll.get(i);

            for (int i2 = 0; i2 <= list1.size() - 1; i2++) { /*проходимся по списку КОАТУ*/

                String s1 = list1.get(i2);

                if (s.equals(s1)) {

                    list1.set(i2, "");
                    // String s2 = list1.get(i2);
                    // aResult.add(list1.get(i2)+" "+list2.get(i2)+" "+list3.get(i2)); 
                    break;
                }

            }


        }

        System.out.println("===");
        System.out.println("===");
        // System.out.println(aPlacePolis.size());
        for (int i2 = 0; i2 <= list1.size() - 1; i2++) {
            if (!list1.get(i2).equals("")) {
                System.out.println(list1.get(i2) + "\t" + list2.get(i2) + "\t" + list3.get(i2));
            }

        }

        //  aPlaceRegion = OtherMethods.delDuplicates(aPlaceRegion);
        // for (String temp: aResult){        System.out.println(temp);





    }
    //  aPlaceRegion = OtherMethods.delDuplicates(aPlaceRegion);
    // for (String temp: aPlaceRegion){ System.out.println(temp); }
    // aPlaceRegionTree = OtherMethods.delDuplicates(aPlaceRegionTree);
    // for (String temp: aPlaceRegionTree){ System.out.println(temp); }
    // aPlacePolis = OtherMethods.delDuplicates(aPlacePolis);
    //  for (String temp: aPlacePolis){ System.out.println(temp); }
}
