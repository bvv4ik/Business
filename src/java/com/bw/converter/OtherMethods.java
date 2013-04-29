/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sergey
 */
public class OtherMethods {
    
     public static void showList(ArrayList<String> list) {
        // String formatted = String.format("%07d", i); // Добавляем нули перед цифрами
        // Collections.sort(listTree1); // Сортировка массива
    //    HashSet set = new HashSet(list); // Удаляем дубликаты
    //    list.clear();
    //    list.addAll(set);
           for (int i2 = 0; i2 <= list.size() - 1; i2++) { // вывод PlaceRegionTree                     //------System.out.print(/*i2+" " + */ listTree1.get(i2).toString()+" "+listTree2.get(i2).toString()+" "+listTree3.get(i2).toString()+"\n");
               //   s = s+ (list.get(i2).toString())+"\n";
               System.out.print(list.get(i2) + "\n");
        }   
    }
    
    
    
     public static ArrayList ClearGarbage(ArrayList<String> arrInput) {
         
          for (int i = 0; i <= arrInput.size()-1; i++) { 
              
              
                  // удаляем из строки:  /М.СІМФЕРОПОЛЬ и т.п
              if (arrInput.get(i).lastIndexOf("/") != -1) {             // если такой символ есть то
                  String sTemp = arrInput.get(i).substring(0, arrInput.get(i).lastIndexOf("/")); // вырезаем из строки все, что до єтого знака
                  arrInput.set(i, sTemp);                        // сохраняем в списке
              }

              if (arrInput.get(i).startsWith("АВТОНОМНА РЕСПУБЛІКА ")) {          //если есть фраза "АВТОНОМНА РЕСПУБЛІКА"
                  String sTemp = arrInput.get(i).replace("АВТОНОМНА РЕСПУБЛІКА ", "");    // удаляем фразу
                  arrInput.set(i, sTemp);       //запоминаем в списке
              }

              if (arrInput.get(i).contains(" ОБЛАСТЬ")) {                   //если есть такая фраза
                  String sTemp = arrInput.get(i).replace(" ОБЛАСТЬ", "");   // удаляем фразу " ОБЛАСТЬ"
                  arrInput.set(i, sTemp);                                   //запоминаем в списке
              }

              if (arrInput.get(i).contains(" РАЙОН")) {                       //если есть такая фраза
                  String sTemp = arrInput.get(i).replace(" РАЙОН", "");           // удаляем фразу " ОБЛАСТЬ"
                  arrInput.set(i, sTemp);                                        //запоминаем в списке
              }


              // это ДОЛЖНО БЫТЬ В САМОМ КОНЦЕ данного метода!
              //Делаем первую букву СТРОКИ большой, остальное маленькими
              String s = OtherMethods.FirstCharUpper(arrInput.get(i));
              arrInput.set(i, s);         //запоминаем в списке
         }
        return (arrInput);
     }
    
    
    
        public static String FirstCharUpper(String str) {
        str = str.toLowerCase();                     // делаем всю строку маленькой
        char[] temp = str.toCharArray();             // переводим строку в массив символов
        temp[0] = Character.toUpperCase(temp[0]);    // первую букву делаем заглавной
        String text = String.valueOf(temp);          // массвив символов переводим в строку
        return (text);                                 // возвращаем строку
    }
    
         
   public static ArrayList ListFromFile (String path ) throws SQLException{
      ArrayList<String> list = new ArrayList<String>(); 
      BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                list.add(sCurrentLine);       
            }
           // System.out.println("Всего элементов в массиве  "+list.size());
          //      System.out.println(s);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            return list;
        }
  
  }        
         
         
    
}
