package com.bw.converter;

//import javax.swing.*;
//import java.awt.event.*;
import com.bw.converter.city.Obl_city;
import java.sql.SQLException;

import com.bw.converter.ray2.*;
import com.bw.converter.city2.*;
//import com.bw.converter.ray.Obl_ray_silr_sel;
//import com.bw.converter.city.Obl_city_area;
//import com.bw.converter.city.Obl_city_silr_sel;
//import com.bw.converter.city.Obl_city_smt;
//import com.bw.converter.city.Obl_city_smt_sel;
//import com.bw.converter.ray.Obl_ray_city;
//import com.bw.converter.ray.Obl_ray_city_sel;
//import com.bw.converter.ray.Obl_ray_city_smt;
import java.util.ArrayList;

/**
 *
 * @author Sergey
 */
public class Start2 {

    // static JFrame frame;
    /*Массивы готовых Выходных дынных*/
    public static ArrayList<String> aResult_PlaceRegionTree = new ArrayList<String>();
    public static ArrayList<String> aResult_PlaceRegion = new ArrayList<String>();
    public static ArrayList<String> aResult_PlacePolis = new ArrayList<String>();
    public static ArrayList<String> aResult = new ArrayList<String>();

    //  public static ArrayList<String> aResult_PlaceArea = new ArrayList<String>();
    public static void main(String args[]) throws SQLException {

        ArObl_city1 o1 = new ArObl_city1();
        o1.getData();
        aResult_PlacePolis.addAll(o1.aPlacePolis);
        aResult_PlaceRegion.addAll(o1.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o1.aPlaceRegionTree);


        ArObl_city_city2 o2 = new ArObl_city_city2();
        o2.getData();
        aResult_PlacePolis.addAll(o2.aPlacePolis);
        aResult_PlaceRegion.addAll(o2.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o2.aPlaceRegionTree);


        ArObl_city_city_sel_16 o21 = new ArObl_city_city_sel_16();
        o21.getData();
        aResult_PlacePolis.addAll(o21.aPlacePolis);
        aResult_PlaceRegion.addAll(o21.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o21.aPlaceRegionTree);

        ArObl_city_rayGor_6 o22 = new ArObl_city_rayGor_6();
        o22.getData();
        aResult_PlacePolis.addAll(o22.aPlacePolis);
        aResult_PlaceRegion.addAll(o22.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o22.aPlaceRegionTree);


        ArObl_city_rayGor_city o23 = new ArObl_city_rayGor_city();
        o23.getData();
        aResult_PlacePolis.addAll(o23.aPlacePolis);
        aResult_PlaceRegion.addAll(o23.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o23.aPlaceRegionTree);

        ArObl_city_rayGor_silr_sel o26 = new ArObl_city_rayGor_silr_sel();
        o26.getData();
        aResult_PlacePolis.addAll(o26.aPlacePolis);
        aResult_PlaceRegion.addAll(o26.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o26.aPlaceRegionTree);



        ArObl_city_rayGor_smt_7 o25 = new ArObl_city_rayGor_smt_7();
        o25.getData();
        aResult_PlacePolis.addAll(o25.aPlacePolis);
        aResult_PlaceRegion.addAll(o25.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o25.aPlaceRegionTree);

        ArObl_city_rayGor_smt_sel o24 = new ArObl_city_rayGor_smt_sel();
        o24.getData();
        aResult_PlacePolis.addAll(o24.aPlacePolis);
        aResult_PlaceRegion.addAll(o24.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o24.aPlaceRegionTree);



        ArObl_city_sel_14 o33 = new ArObl_city_sel_14();
        o33.getData();
        aResult_PlacePolis.addAll(o33.aPlacePolis);
        aResult_PlaceRegion.addAll(o33.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o33.aPlaceRegionTree);
        ///ArObl_city_sel_14


        ArObl_city_silr_sel5 o3 = new ArObl_city_silr_sel5();
        o3.getData();
        aResult_PlacePolis.addAll(o3.aPlacePolis);
        aResult_PlaceRegion.addAll(o3.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o3.aPlaceRegionTree);


        ArObl_city_smt3 o4 = new ArObl_city_smt3();
        o4.getData();
        aResult_PlacePolis.addAll(o4.aPlacePolis);
        aResult_PlaceRegion.addAll(o4.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o4.aPlaceRegionTree);


        ArObl_city_smt_sel4 o5 = new ArObl_city_smt_sel4();
        o5.getData();
        aResult_PlacePolis.addAll(o5.aPlacePolis);
        aResult_PlaceRegion.addAll(o5.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o5.aPlaceRegionTree);


//         //==================================== 

        ArObl_ray_city9 o6 = new ArObl_ray_city9();
        o6.getData();
        aResult_PlacePolis.addAll(o6.aPlacePolis);
        aResult_PlaceRegion.addAll(o6.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o6.aPlaceRegionTree);


        ArObl_ray_city_sel_15 o7 = new ArObl_ray_city_sel_15();
        o7.getData();
        aResult_PlacePolis.addAll(o7.aPlacePolis);
        aResult_PlaceRegion.addAll(o7.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o7.aPlaceRegionTree);


        ArObl_ray_city_smt_13 o8 = new ArObl_ray_city_smt_13();
        o8.getData();
        aResult_PlacePolis.addAll(o8.aPlacePolis);
        aResult_PlaceRegion.addAll(o8.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o8.aPlaceRegionTree);


        ArObl_ray_silr_sel_12 o9 = new ArObl_ray_silr_sel_12();
        o9.getData();
        aResult_PlacePolis.addAll(o9.aPlacePolis);
        aResult_PlaceRegion.addAll(o9.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o9.aPlaceRegionTree);

        ArObl_ray_smt10 o10 = new ArObl_ray_smt10();
        o10.getData();
        aResult_PlacePolis.addAll(o10.aPlacePolis);
        aResult_PlaceRegion.addAll(o10.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o10.aPlaceRegionTree);

        ArObl_ray_smt_sel_11 o11 = new ArObl_ray_smt_sel_11();
        o11.getData();
        aResult_PlacePolis.addAll(o11.aPlacePolis);
        aResult_PlaceRegion.addAll(o11.aPlaceRegion);
        aResult_PlaceRegionTree.addAll(o11.aPlaceRegionTree);


        // убираем дубликаты
        aResult_PlaceRegion = OtherMethods.delDuplicates(aResult_PlaceRegion);
        aResult_PlacePolis = OtherMethods.delDuplicates(aResult_PlacePolis);
        aResult_PlaceRegionTree = OtherMethods.delDuplicates(aResult_PlaceRegionTree);




//   for (String temp: aResult_PlaceRegion){ System.out.println(temp); }     

        //  for (String temp: aResult_PlacePolis){ System.out.println(temp); }

        for (String temp : aResult_PlaceRegionTree) {
            System.out.println(temp);
        }



//        
//        //===========================================
//        // выводим только уникальные номера Коату
//        for (String temp: aResult_PlaceRegion){ 
//             // System.out.println(temp.substring( (temp.length()-12), (temp.length()-2) )) ; 
//           aResult.add(temp.substring( (temp.length()-12), (temp.length()-2) ));
//          }
//
//        // выводим только уникальные номера Коату
//        for (String temp: aResult_PlacePolis){ 
//         //   System.out.println(temp.substring(temp.length()-10)); 
//           aResult.add(temp.substring(temp.length()-10));
//        }
//        
//        aResult = OtherMethods.delDuplicates(aResult);
//        for (String temp: aResult){ System.out.println(temp); }
//        //===========================================


        //      for (String temp: aResult_PlaceArea){ System.out.println(temp); }





        //         Obl_ray_silr_sel  o1 = new Obl_ray_silr_sel();
        // OtherMethods.showDlg("sdasdasd"); 

        //String s = "0120455300";
        //System.out.println(s.substring(2, 3));




//       City_area  o5 = new City_area();
//         o5.getData();

    }
}
