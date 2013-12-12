/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author SERG
 */
public class Collections1 {
    
    public static void main(String[] args) {
        
//       long start = System.currentTimeMillis(); 
//        
//    // не сортированный список, все ключи должны быть уникальными
//    Map<String, String> oHashMap = new HashMap<String, String>();
//
//        for (int i = 0; i < 10000; i++) { // длбавляем 100 записей
//            oHashMap.put("Bruce"+i, "Willis"+i);
//        }
//      
//        // for (String temp: oHashMap.values()){ // выводим ключи .keySet() или значения .values()
//        //     System.out.println(temp);
//        // }
//
//        for (Map.Entry<String, String> entry : oHashMap.entrySet()) { // выводим все
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }    
//
//         long stop = System.currentTimeMillis();
//         System.out.println((double)(stop-start)/1000 + " seconds");
//         System.out.println("");
//     
 //======================================================================================
         

// long  start = System.currentTimeMillis(); 
// 
// // TreeMap - сортированый список, ключи должны быть уникальные
//    Map oTreeMap = new TreeMap<String, String>();
//       for (int i = 0; i < 1000; i++) { // длбавляем 100 записей
//            oTreeMap.put("Bruce"+i, "Willis"+i);
//        }
//    oTreeMap.put("Aey_2","element_2");
//    oTreeMap.put("Cey_1","element_1");
//    oTreeMap.put("Bey_3","element_3");
//  
////    Collection entrySet = oTreeMap.entrySet();
////    Iterator it = entrySet.iterator();  
////    System.out.println("TreeMap entries : ");
////     while(it.hasNext())
////     System.out.println(it.next());
//    
//    for (Object o : oTreeMap.entrySet()) {  // или по отдельности .values()  .KeySet()
//        System.out.println(o.toString());    
//    }
//      
//   long  stop = System.currentTimeMillis();
//         System.out.println((double)(stop-start)/1000 + " seconds");
//         System.out.println("");
    
//===================================================================
         
Map<String, String> treeMap = new TreeMap<String, String>();
treeMap.put("Bruce", "Willis");
treeMap.put("Arnold", "Schwarzenegger");
treeMap.put("Jackie", "Chan");
treeMap.put("Sylvester", "Stallone");
treeMap.put("Chuk", "Norris");
        
System.out.println("TreeMap");
for (Map.Entry<String, String> e : treeMap.entrySet()) {
    System.out.println(e.getKey() + " " + e.getValue());
}
         
//======================================================

Map<String, String> hashMap = new HashMap<String, String>();
hashMap.put("Bruce", "Willis");
hashMap.put("Arnold", "Schwarzenegger");
hashMap.put("Jackie", "Chan");
hashMap.put("Sylvester", "Stallone");
hashMap.put("Chuk", "Norris");
        
System.out.println("HashMap");
for (Map.Entry<String, String> e : hashMap.entrySet()) {
    System.out.println(e.getKey() + " " + e.getValue());
}

    
  }


//        TreeMap treeMap = new TreeMap();
//treeMap.put("Bruce", "Willis");
//treeMap.put("Arnold", "Schwarzenegger");
//treeMap.put("Jackie", "Chan");
//treeMap.put("Sylvester", "Stallone");
//treeMap.put("Chuck", "Norris");
//     
//Set set = treeMap.entrySet();      
//while (set.iterator().hasNext()){
//    set.
//    System.out.println(e.getKey()+" "+ e.getValue());
//    }
//for(TreeMap.Entry e : treeMap.entrySet()){
// 
//    System.out.println(e.getKe
   
//y()+" "+ e.getValue());
//}



    }
    
