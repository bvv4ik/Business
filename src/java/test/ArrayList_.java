/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.ArrayList;

/**
 *
 * @author SERG
 */
public class ArrayList_ {
    
    public static void main(String[] args) {
     
        // создаем ArrayList и сразу выделяем ему памяти на 1000 строк
        ArrayList<String> oArrayList = new ArrayList<String>(1000); 
        
        // добавляем 1000 строк. если добавить больше 1000 то автоматически будет выделено еще памяти.
        for (int i = 0; i < 1000; i++) {  
            oArrayList.add("add string: " + i);
        }
        
        // выводим 1000 строк на экран
//        for (String s : oArrayList) {   
//            System.out.println(s);
//        }

        // создаем ArrayList2 инициализируем его данными из первого oArrayList
        ArrayList<String> oArrayList_new = new ArrayList<String>(oArrayList); 
              
        // выводим 1000 строк на экран
        for (String s : oArrayList_new) {   
            System.out.println(s);
        }
        
        // подробнее тут:  http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
        
        
    }
    
    
    
}
