/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import com.bw.io._;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import static test.ReplaceQuote.aListLoad;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author SERG
 */
public class CodeMin {
    public static ArrayList<String> aListLoad = new ArrayList<String>();
    public static ArrayList<String[]> aList2 = new ArrayList<String[]>();
    
    
    public static void main(String[] args) throws IOException  {

         // CodeMin oCodeMin = new CodeMin();
          aListLoad = _.sLoadFromFile("C:\\jquery-1.9.1.min.js");
          String s = "";
          String s1 = "";
          for(String ss: aListLoad){
             s = s.concat(ss);
             s1 = s1.concat(ss);
          }
          
          System.out.println(s.length());     
          
          s = s.replace("!"," ")
               .replace(","," ")
               .replace("}"," ")
               .replace("{"," ")
               .replace("="," ")
               .replace("]"," ")
               .replace("["," ")
               .replace(";"," ")             
               .replace(")"," ")
               .replace("("," ")
               .replace("$"," ")
               .replace("+"," ")
               .replace("-"," ")
               .replace("%"," ")
               .replace("^"," ")
               .replace("&"," ")
               .replace("*"," ")
               .replace("#"," ")
               .replace("@"," ")
               .replace("?"," ")
               .replace("/"," ")
               .replace("|"," ")
               .replace(">"," ")
               .replace("<"," ")
               .replace("."," ")
               .replace(":"," ")
                   .replace("'"," ")
                   .replace("\""," ")
               .replace("~"," ")

                 .replace("\\"," ")
                 //.replace("\"","•")
                // .replace("  "," ")
                  
                  ;
          aListLoad.clear();

          String[] parts = s.split(" ");
         
          for (int i = 0; i < parts.length; i++) { // выбрасываем пустые строки и меньше 3 символов
              if (!parts[i].equals("") & (parts[i].length() >= 3)){
                  aListLoad.add(parts[i]);
              }              
            }
         // _.saveTextFile(aListLoad.toString(), "d:\\123_unsort.txt");
          Collections.sort(aListLoad);  // сортировка по алфавиту
          //System.out.println(aListLoad.toString());
          
          HashMap<String, String> hm = new HashMap<String, String>();
          
          int nCount = 1;
          
          for (int i=aListLoad.size()-1; i >= 1 ; i--) { // считаем сколько раз повт. каждое слово
          
             if (aListLoad.get(i).equals(aListLoad.get(i-1))){
                 //aListLoad.remove(i);
             nCount++;
            }
          else{
              if (nCount >= 1) {
              // aListLoad.set(i, aListLoad.get(i)+"\t"+nCount);
              hm.put(aListLoad.get(i), Integer.toString(nCount));
              nCount=1;
              }
                  
          }
             
             
          }
          
           String sOut="";
           for (Map.Entry<String, String> e : hm.entrySet()) {
               if (!e.getValue().equals("1")){
                   //System.out.println(e.getValue() ); 
               sOut = sOut.concat(e.getKey() +" "+ e.getValue()+"\n");
               Random r = new Random(); 
                int i = r.nextInt(99); // случайное число
               s1 = s1.replace(e.getKey(), Integer.toString(i)/*"29"*/);
               }
           }
         
//          String sOut="";
//          for(String ss: aListLoad){
//          sOut = sOut.concat(ss+"\n");
//          }
//          
          _.saveTextFile(sOut, "d:\\123.txt");
          _.saveTextFile(s1, "d:\\-123.txt");
         // System.out.println(s1);    
        }
          //System.out.println(parts.toString());    
          
    
}
