/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter;


import java.sql.SQLException;
import com.bw.converter.obl.Obl_ray_silr_sel;
import com.bw.converter.city.Vetka_004_Area;

/**
 *
 * @author Sergey
 */
public class Start {
    
     public static void main(String args[]) throws SQLException {
         
         Obl_ray_silr_sel  o1 = new Obl_ray_silr_sel();
         o1.getData();
         //o1.list1 
         
//         Vetka_002  o2 = new Vetka_002();
//         o2.getData();
//         Vetka_003  o3 = new Vetka_003();
//         o3.getData();

   //     Vetka_004  o4 = new Vetka_004();
  //     o4.getData();
         
       Vetka_004_Area  o5 = new Vetka_004_Area();
         o5.getData();
         
     }
    
    
    
    
}
