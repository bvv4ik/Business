
package com.bw.converter;

//import javax.swing.*;
//import java.awt.event.*;

import com.bw.converter.city.Obl_city;
import java.sql.SQLException;
import com.bw.converter.ray.Obl_ray_silr_sel;
import com.bw.converter.city.Obl_city_area;
import com.bw.converter.city.Obl_city_silr_sel;
import com.bw.converter.city.Obl_city_smt;
import com.bw.converter.city.Obl_city_smt_sel;
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

          
          
          for (String temp: aResult_PlacePolis){ System.out.println(temp); }
          
          
          
          //for (String temp: o1.list_PlacePolis){ System.out.println(temp); }
          
          
          
          //         Obl_ray_silr_sel  o1 = new Obl_ray_silr_sel();
       // OtherMethods.showDlg("sdasdasd"); 
       
         //String s = "0120455300";
         //System.out.println(s.substring(2, 3));
         
         
   
         
//       City_area  o5 = new City_area();
//         o5.getData();
         
     }
    
    
    
    
}
