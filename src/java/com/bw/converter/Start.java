package com.bw.converter;

//import javax.swing.*;
//import java.awt.event.*;
import com.bw.converter.city.Obl_city;
import java.sql.SQLException;

import com.bw.converter.ray.*;
import com.bw.converter.city.*;
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
public class Start {

    // static JFrame frame;
    /*Массивы готовых Выходных дынных*/
    public static ArrayList<String> aResult_PlacePolisTree = new ArrayList<String>();
    public static ArrayList<String> aResult_PlaceRegion = new ArrayList<String>();
    public static ArrayList<String> aResult_PlacePolis = new ArrayList<String>();
    public static ArrayList<String> aResult_PlaceArea = new ArrayList<String>();

    public static void main(String args[]) throws SQLException {

        Obl_city o1 = new Obl_city();
        o1.getData();
        aResult_PlacePolis.addAll(o1.aPlacePolis);
        aResult_PlaceRegion.addAll(o1.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o1.aPlacePolisTree);


        Obl_city_area o2 = new Obl_city_area();
        o2.getData();
        aResult_PlaceArea.addAll(o2.aPlaceArea);


        Obl_city_silr_sel o3 = new Obl_city_silr_sel();
        o3.getData();
        aResult_PlacePolis.addAll(o3.aPlacePolis);
        aResult_PlaceRegion.addAll(o3.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o3.aPlacePolisTree);


        Obl_city_smt o4 = new Obl_city_smt();
        o4.getData();
        aResult_PlacePolis.addAll(o4.aPlacePolis);
        aResult_PlaceRegion.addAll(o4.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o4.aPlacePolisTree);


        Obl_city_smt_sel o5 = new Obl_city_smt_sel();
        o5.getData();
        aResult_PlacePolis.addAll(o5.aPlacePolis);
        aResult_PlaceRegion.addAll(o5.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o5.aPlacePolisTree);

        //==================================== 

        Obl_ray_city o6 = new Obl_ray_city();
        o6.getData();
        aResult_PlacePolis.addAll(o6.aPlacePolis);
        aResult_PlaceRegion.addAll(o6.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o6.aPlacePolisTree);

        Obl_ray_city_sel o7 = new Obl_ray_city_sel();
        o7.getData();
        aResult_PlacePolis.addAll(o7.aPlacePolis);
        aResult_PlaceRegion.addAll(o7.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o7.aPlacePolisTree);

        Obl_ray_city_smt o8 = new Obl_ray_city_smt();
        o8.getData();
        aResult_PlacePolis.addAll(o8.aPlacePolis);
        aResult_PlaceRegion.addAll(o8.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o8.aPlacePolisTree);

        Obl_ray_silr_sel o9 = new Obl_ray_silr_sel();
        o9.getData();
        aResult_PlacePolis.addAll(o9.aPlacePolis);
        aResult_PlaceRegion.addAll(o9.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o9.aPlacePolisTree);

        Obl_ray_smt o10 = new Obl_ray_smt();
        o10.getData();
        aResult_PlacePolis.addAll(o10.aPlacePolis);
        aResult_PlaceRegion.addAll(o10.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o10.aPlacePolisTree);

        Obl_ray_smt_sel o11 = new Obl_ray_smt_sel();
        o11.getData();
        aResult_PlacePolis.addAll(o11.aPlacePolis);
        aResult_PlaceRegion.addAll(o11.aPlaceRegion);
        aResult_PlacePolisTree.addAll(o11.aPlacePolisTree);



//          for (String temp: aResult_PlacePolis){ System.out.println(temp); }
        aResult_PlaceRegion = OtherMethods.delDuplicates(aResult_PlaceRegion);

        for (String temp : aResult_PlaceRegion) {
            System.out.println(temp);
        }
        //          for (String temp: aResult_PlaceArea){ System.out.println(temp); }
//       for (String temp: aResult_PlacePolisTree){ System.out.println(temp); }






        //         Obl_ray_silr_sel  o1 = new Obl_ray_silr_sel();
        // OtherMethods.showDlg("sdasdasd"); 

        //String s = "0120455300";
        //System.out.println(s.substring(2, 3));




//       City_area  o5 = new City_area();
//         o5.getData();

    }
}
