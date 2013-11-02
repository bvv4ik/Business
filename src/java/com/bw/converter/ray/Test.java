/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter.ray;

import java.util.ArrayList;

/**
 *
 * @author Sergey
 */
public class Test {

    public static ArrayList AddLinkRay(ArrayList<String> arrID, ArrayList<String> arrType, ArrayList<String> arrName) {
        String sNamePolis = "";


        for (int i = 0; i <= arrName.size() - 1; i++) {


            if (arrName.get(i).contains("-----")) {
                System.out.println(arrID.get(i) + "  " + arrName.get(i));
            }
        }

        return arrName;
    }
    // Вывод всего мусора типа Миста пидпорядковани...
//      int in = 0;
//        for (int i = 0; i <= list3.size() - 1; i++) {
//        
//            if ( list3.get(i).length() >= 25 
//               &  !list3.get(i).contains("/")    
//            //if ( list3.get(i).contains("СЕЛА ")
//               // | list3.get(i).contains("МІСТА")    
//              //   |   list3.get(i).contains("СЕЛИЩА ")
//                  )
//            {
//           //System.out.println(list1.get(i) +"  "+ list3.get(i)); 
//           System.out.println(list3.get(i)); 
//           in++;
//               }
//                   }
//         System.out.println(in); 
}
