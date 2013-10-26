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
//import javax.swing.*;
//import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import java.awt.event.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Sergey
 */
public class OtherMethods {
    
    static JFrame frame;
    
    public static void showDlg(String sMessage) {
        JOptionPane.showMessageDialog(frame, sMessage);
        
    }
    
     public static ArrayList<String> delDuplicates(ArrayList<String> aList) {
         HashSet set = new HashSet(aList); // Удаляем дубликаты
        aList.clear();
        aList.addAll(set);
        return aList;
     }
    
    
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
     
     // Выбрать последние цифры связки региона и полиса / центра региона
     public static String getLastNum (String s) {
              String sNamePolis = "";
              
              
              if (s.indexOf("/") != -1){ // если есть знак "/"
              sNamePolis = s.substring(s.lastIndexOf("/")+1); // берем все что после "/" и до конца
                }
              else{ //если в строке не встречается знак "/"
                  sNamePolis = "0"; //"=====" значит нет ссылки на рут город Региона
              }
              
              
            return sNamePolis;
     }
     
     // Выбрать последние цифры связки региона и полиса / центра региона
     public static String getFirstName (String s) {
              String sNamePolis = s;
              
              if (s.indexOf("/") != -1){ // если есть знак "/"
               sNamePolis = s.substring(0, s.indexOf("/")); // Берем все что сначала строки и до знака "/"
              }
               
               
            return sNamePolis;
     }
     
     
    // Делаем связку Района города и дальнейшие полисы (что в Коату никак не связано)
     public static ArrayList AddLinkRay(ArrayList<String> arrID, ArrayList<String> arrType, ArrayList<String> arrName) {
         String sNamePolis = "";
         int curr = 1;       
         
        for (int i = 0; i <= arrName.size() - 1; i++) {

            if (arrType.get(i).lastIndexOf("Р") != -1) // если это район
                     {
                
                sNamePolis = arrName.get(i).substring(0,4); // запоминаем название района (первые 4 символов)
                
                 for (int i2 = i; i2 <= /*arrName.size()-1*/i+55; i2++) { // поиск с найденного места - всего 35 след. позций
                    
                     if (i2 <= arrName.size()-1) // чтобы не выйти за предел длинны массива 
                     if  (   
                             arrName.get(i2).contains(" "+sNamePolis) // совпадают 4 букв названия Района
                         &    arrName.get(i2).contains("РАЙРАДІ") // и есть слово "Райрады"
                        
                            )    {
                                           // другая ссілка
                                           //arrName.set(i2, arrName.get(i2) + "-----"+curr /*+ arrID.get(i)*/); // делаем связку
                                      // ДОбавляем в назв. района "==" чтобы знать, что эти районы имеют 
                                      // продолжение, значит 
                                      // остальные (кот. не имеют "==") не имеют продолжения и к ним вяжутся ?улицы?
                                      arrName.set(i, arrName.get(i) + "="/*+curr*/); 
                                      curr ++;
                                    // if (arrName.get(i).contains("==")) {}
                                    //  System.out.println(arrName.get(i));
                
                    }
                     
                    }
 curr = 1;
        }
        //   if (arrName.get(i).contains("="))
       //   System.out.println(arrID.get(i) +"  "+ arrName.get(i)); 
        }
           
        return arrName;
    }
     
     
     
    // Прееделываем названия в ссылки 
    public static ArrayList FindRegionPolis(ArrayList<String> arrID, ArrayList<String> arrName) {
      int Err = 0;
        String sRegion = "";
      String sTypePolis = "";
      String sNamePolis = "";
      String sIDPolis = "";
      String nTwoID = "";
      int count = 0;
      int count1 = 0;
      
      
        for (int i = 0; i <= arrName.size()-1; i++) {
      
        //   nCeuurentID = Integer.parseInt(arrID.get(i)) ;
            
          if ( (arrName.get(i).lastIndexOf("/") != -1) // если такой символ есть то
            //  & (arrName.get(i).contains("РАЙОН"))  // если такое слово
                                                       ) 
          { 
              //int iChar = arrInput.get(i).lastIndexOf("/");
// Исправляем ошибки в названиях
if (arrName.get(i).contains("КУНИЦЬКА/С.КУНИЧНЕ")){    arrName.set(i, "КУНИЦЬКА/С.КУНИЧЕ");  }
if (arrName.get(i).contains("МЕЖИСИТІВСЬКА/С.МЕЖИСИТІ")){    arrName.set(i, "МЕЖИСИТІВСЬКА/С.МЕЖИСИТЬ");  }
if (arrName.get(i).contains("МИКІЛЬСЬКА/С.МІКІЛЬСЬКЕ-НА-ДНІПРІ")){    arrName.set(i, "МИКІЛЬСЬКА/С.МИКІЛЬСЬКЕ-НА-ДНІПРІ");  }
if (arrName.get(i).contains("ОСТРІЙКІВСЬКА/С.ОСТРІЙКІ")){    arrName.set(i, "ОСТРІЙКІВСЬКА/С.ОСТРІЙКИ");  }
if (arrName.get(i).contains("ПЕРЕЯСЛАВСЬКА/С.ПЕРЕЯСЛАВСКЕ")){    arrName.set(i, "ПЕРЕЯСЛАВСЬКА/С.ПЕРЕЯСЛАВСЬКЕ");  }
if (arrName.get(i).contains("ПОВІТНЕНСЬКА/С.ПОВІТНЕ")){    arrName.set(i, "ПОВІТНЕНСЬКА/С.ПОВІТНО");  }
if (arrName.get(i).contains("СВИТАЗІВСЬКА/С.СВИТЯЗІВ")){    arrName.set(i, "СВИТАЗІВСЬКА/С.СВИТАЗІВ");  }
if (arrName.get(i).contains("БАРАТІВСЬКА/С.БРАТІВКА")){    arrName.set(i, "БАРАТІВСЬКА/С.БАРАТІВКА");  }
if (arrName.get(i).contains("ВОЗНЕСЕНСЬКА-ДРУГА/С.ВОЗНЕСЕНКА-ДРУГА")){    arrName.set(i, "ВОЗНЕСЕНСЬКА ДРУГА/С.ВОЗНЕСЕНКА ДРУГА");  }
if (arrName.get(i).contains("ТАВЕРІВСЬКА/С ТАВЕРІВКА")){    arrName.set(i, "ТАВЕРІВСЬКА/С.ТАВЕРІВКА");  }
if (arrName.get(i).contains("КАЛІЇВСЬКА/С.КАЛІІВКА")){    arrName.set(i, "КАЛІЇВСЬКА/С.КАЛІЇВКА");  }
if (arrName.get(i).contains("АРКАДІВСЬКА/С.АРАКАДІВКА")){    arrName.set(i, "АРКАДІВСЬКА/С.АРКАДІВКА");  }
if (arrName.get(i).contains("ОЛЕКСІЇВСЬКА/С.ОЛЕКСІІВКА")){    arrName.set(i, "ОЛЕКСІЇВСЬКА/С.ОЛЕКСІЇВКА");  }
if (arrName.get(i).contains("АНТОНОВЕЦЬКА/С.АНТОНОВЦІ")){    arrName.set(i, "АНТОНОВЕЦЬКА/С.АНТОНІВЦІ");  }
if (arrName.get(i).contains("БУБНІВСЬКО-СЛОБІДСЬКА/С.БУДНІВСЬКА СЛОБІДКА")){    arrName.set(i, "БУБНІВСЬКО-СЛОБІДСЬКА/С.БУБНІВСЬКА СЛОБІДКА");  }
if (arrName.get(i).contains("БАКАЇВСЬКА/С.БАКАІВКА")){    arrName.set(i, "БАКАЇВСЬКА/С.БАКАЇВКА");  }
if (arrName.get(i).contains("БІЛОШИЦЬКО-СЛОБІДСЬКА/С.БІЛЬШИЦЬКА СЛОБОДА")){    arrName.set(i, "БІЛОШИЦЬКО-СЛОБІДСЬКА/С.БІЛОШИЦЬКА СЛОБОДА");  }
if (arrName.get(i).contains("СТАРОГУТКІВСЬКА/С.СТАРА ГУТА")){    arrName.set(i, "СТАРОГУТКІВСЬКА/С.СТАРА ГУТКА");  }

if (arrName.get(i).equals("М.КИЇВ")){    arrName.set(i, "КИЇВ");  }
if (arrName.get(i).contains("М.СЕВАСТОПОЛЬ")){    arrName.set(i, "СЕВАСТОПОЛЬ");  }

if (arrName.get(i).equals("РАЙОНИ М. КИЇВ")){    arrName.set(i, "РАЙОНИ М.КИЇВ");  }
if (arrName.get(i).equals("ЧУДНІВСЬКИЙ РАЙОН/М ЧУДНІВ")){    arrName.set(i, "ЧУДНІВСЬКИЙ РАЙОН/М.ЧУДНІВ");  }
if (arrName.get(i).equals("ТАВЕРІВСЬКА/С ТАВЕРІВКА")){    arrName.set(i, "ТАВЕРІВСЬКА/С.ТАВЕРІВКА");  }
if (arrName.get(i).equals("САМІЙЛІВСЬКА/С САМІЙЛІВКА")){    arrName.set(i, "САМІЙЛІВСЬКА/С.САМІЙЛІВКА");  }
if (arrName.get(i).equals("ПОЛІСЬКИЙ РАЙОН/СМТ.КРАСЯТИЧІ")){    arrName.set(i, "ПОЛІСЬКИЙ РАЙОН/СМТ КРАСЯТИЧІ");  }
        
if (arrName.get(i).contains("ОРДЖОНІКІДЗЕВСЬКА/С.МИКОЛАЇВКА")){    arrName.set(i, "ОРДЖОНІКІДЗЕВСЬКА/C.ОРДЖОНІКІДЗЕ");  }


if (arrName.get(i).contains("ВЕЛИКОБІЛОЗЕРСЬКИЙ РАЙОН/С.ВЕЛИКА БІЛОЗЕРКА")){    arrName.set(i, "ВЕЛИКОБІЛОЗЕРСЬКИЙ РАЙОН/С.ВЕЛИКА БІЛОЗЕРКА(ЧАСТИНА 1 СЕЛА)");  }
if (arrName.get(i).contains("ВЕЛИКОБІЛОЗЕРСЬКА/С.ВЕЛИКА БІЛОЗЕРКА")){    arrName.set(i, "ВЕЛИКОБІЛОЗЕРСЬКА/С.ВЕЛИКА БІЛОЗЕРКА(ЧАСТИНА 1 СЕЛА)");  }

if (arrName.get(i).contains("НОВОПЕТРІВСЬКА/С.ВЕЛИКА БІЛОЗЕРКА")){    arrName.set(i, "НОВОПЕТРІВСЬКА/С.ВЕЛИКА БІЛОЗЕРКА(ЧАСТИНА 2 СЕЛА)");  }

if (arrName.get(i).contains("ТРУДОВА/С.ВЕЛИКА БІЛОЗЕРКА")){    arrName.set(i, "ТРУДОВА/С.ВЕЛИКА БІЛОЗЕРКА(ЧАСТИНА 3 СЕЛА)");  }

if (arrName.get(i).contains("ЧЕРВОНА/С.ВЕЛИКА БІЛОЗЕРКА")){    arrName.set(i, "ЧЕРВОНА/С.ВЕЛИКА БІЛОЗЕРКА(ЧАСТИНА 4 СЕЛА)");  }

if (arrName.get(i).contains("СЕРЕДИНО-БУДСЬКИЙ РАЙОН/М.СЕРЕДИНО-БУДА")){    arrName.set(i, "СЕРЕДИНО-БУДСЬКИЙ РАЙОН/М.СЕРЕДИНА-БУДА");  }

if (arrName.get(i).contains("СЕЛИЩА МІСЬКОГО ТИПУ, ПІДПОРЯДКОВАНІ ГОРНЯЦЬКІЙ РАЙРАДІ М.МАКІЇВКИ")){    arrName.set(i, "СЕЛИЩА МІСЬКОГО ТИПУ, ПІДПОРЯДКОВАНІ ГІРНЯЦЬКІЙ РАЙРАДІ М.МАКІЇВКИ");  }



//КИЇВСЬКА ОБЛАСТЬ/М.КИЇВ



              //вырезаем название Региона
              sRegion = arrName.get(i).substring(0, arrName.get(i).lastIndexOf("/"));
              nTwoID = arrID.get(i).substring(0, 2);
              
      
              if (("С.").equals(arrName.get(i).substring(arrName.get(i).lastIndexOf("/") + 1, arrName.get(i).lastIndexOf("/") + 3))) {
                  sNamePolis = arrName.get(i).substring(arrName.get(i).lastIndexOf("/") + 3/*, arrName.get(i).length()*/);
              }
              if (("М.").equals(arrName.get(i).substring(arrName.get(i).lastIndexOf("/") + 1, arrName.get(i).lastIndexOf("/") + 3))) {
                  sNamePolis = arrName.get(i).substring(arrName.get(i).lastIndexOf("/") + 3);
              }
              if (("СМТ ").equals(arrName.get(i).substring(arrName.get(i).lastIndexOf("/") + 1, arrName.get(i).lastIndexOf("/") + 5))) {
                  sNamePolis = arrName.get(i).substring(arrName.get(i).lastIndexOf("/") + 5/*, arrName.get(i).length()*/);
              }
              if (("С-ЩЕ ").equals(arrName.get(i).substring(arrName.get(i).lastIndexOf("/") + 1, arrName.get(i).lastIndexOf("/") + 6))) {
                  sNamePolis = arrName.get(i).substring(arrName.get(i).lastIndexOf("/")+6/*, arrName.get(i).length()*/ );
              } 
              

              
               if  (arrName.get(i).contains("/М.КИЇВ")){ // Если "/Киев" то идем до конца и ищем полис
              for (int i2 = i; i2 <= arrName.size()-1/*i+1000*/; i2++) { // поиск с найденного места
                  if  (arrName.get(i2).equals("М.КИЇВ")) {
                      arrName.set(i, /*count+" "+*/   sRegion + "///" + (i2+1)  /*sIDPolis*/);
                  }
              }
              }
              
            //    if (!arrName.get(i).contains("РАЙОН"))  // если нету слова район
                 for (int i2 = i; i2 <= arrName.size()-1/*i+1000*/; i2++) { // поиск с найденного места
                    if  (arrName.get(i2).equals(sNamePolis))   {
                      
                    
                      if (count <= 10) // проверка первых 10 записей
                     arrName.set(i, /*count+" "+*/   sRegion + "///" + (i2+1)  /*sIDPolis*/);
                    //System.out.println(arrName.get(i));
                    count = 0; // на сколько ниже
              
                    break;
                    }
                    else {
                        count++;
                        if (i2 == arrName.size()-1) {
                    //   arrName.set(i, sRegion + "///" + "-----------------------------" /*sIDPolis*/);
                        ++Err; // сколько полисов находится выше районов и треб. особый поиск
                        count = 0;
                   //     System.out.println(arrName.get(i));
                        }
//                        
                    }
                  
                 }
                   count = 0;
     //=================================================
                 // для некоторых районов особый поиск с самого начала списка
     
                 if (!arrName.get(i).contains("///")){ // Если строка не содержит ссылки значит...
                  for (int i3 = 0; i3 <= arrName.size()-1; i3++) {   
                    if  (                         
                            (arrName.get(i3).equals(sNamePolis))    // если область совпадает
                        & nTwoID.equals(arrID.get(i3).substring(0, 2))  // сравниваем области
                          
                            ) {
               
                     arrName.set(i, /*count1+" "+*/sRegion + "//////" + (i3+1) /*+ "======="*/);
                    // System.out.println(arrName.get(i));
                     count1 = 0;
                    break;
                    }
                    else {
                          count1++;
                    }
                 
                 }
                  
                 }
                count1 = 0;
           //-------- 
             //   System.out.println(arrName.get(i)); // вывод списка
              
          }
          
          
       }
//          System.out.println("===");  // вывод ошибок
//          System.out.println(Err);
//          System.out.println("===");
          return (arrName);
    }
     
    
    
    
    
    
    
     public static ArrayList ClearGarbage(ArrayList<String> arrInput) {
         
          for (int i = 0; i <= arrInput.size()-1; i++) { 
              
              
                  // удаляем из строки:  /М.СІМФЕРОПОЛЬ и т.п
       //       if (arrInput.get(i).lastIndexOf("/") != -1) {             // если такой символ есть то
       //           String sTemp = arrInput.get(i).substring(0, arrInput.get(i).lastIndexOf("/")); // вырезаем из строки все, что до єтого знака
       //           arrInput.set(i, sTemp);                        // сохраняем в списке
       //       }

              if (arrInput.get(i).startsWith("АВТОНОМНА РЕСПУБЛІКА ")) {          //если есть фраза "АВТОНОМНА РЕСПУБЛІКА"
                  String sTemp = arrInput.get(i).replace("АВТОНОМНА РЕСПУБЛІКА ", "");    // удаляем фразу
                  arrInput.set(i, sTemp);       //запоминаем в списке
              }

              if (arrInput.get(i).contains(" ОБЛАСТЬ") ) {                   //если есть такая фраза
                  String sTemp = arrInput.get(i).replace(" ОБЛАСТЬ", "");   // удаляем фразу " ОБЛАСТЬ"
                  arrInput.set(i, sTemp);                                   //запоминаем в списке
              }

              if (arrInput.get(i).contains(" РАЙОН")
                     & !arrInput.get(i).contains("РАЙОННОГО")
                      
                      ) {                       //если есть такая фраза
                  String sTemp = arrInput.get(i).replace(" РАЙОН", "");           // удаляем фразу " ОБЛАСТЬ"
                  arrInput.set(i, sTemp);                                        //запоминаем в списке
              }


                  // это ДОЛЖНО БЫТЬ В САМОМ КОНЦЕ данного метода!
                  //Делаем первую букву СТРОКИ большой, остальное маленькими
            //  String s = OtherMethods.FirstCharUpper(arrInput.get(i));
            //  arrInput.set(i, s);         //запоминаем в списке
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
            
        } catch (IOException oException) {
            oException.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }

            } catch (IOException oException) {
                oException.printStackTrace();
            }
            
            return list;
        }
  
  }        
         
         
    
}
