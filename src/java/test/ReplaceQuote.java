
package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Ищет в JS файле строки текста (сообщений), помещает их в объект и заменяет в JS файле текст на название в объекте.
 * Объект и измененный JS файл сохраняются в отдельных новых файлах.
 * Знак • , это спец символ экранирования цитаты.
 * Все цитаты должны выглядить строго так:  "•Пример сообщения•"  или '•Пример сообщения•', пробелы между ковычкой и 
 * спец символом • недопустимы, например цитаты: " •Пример сообщения•"  или '•Пример сообщения•  '  - вызовут ошибку и 
 * строка не будет обработана. Пользователь будет уведомлен сообщением о номере строки с ошибкой, для исправления.
 * @author Sergey
 */
public class ReplaceQuote {

     public static ArrayList<String> aListLoad = new ArrayList<String>(); // Здесь хранится текст файла JS
     public static ArrayList<String> aObject = new ArrayList<String>(); // здесь созданный объект из цитат

     
     
     public static void main(String[] args) throws SQLException, IOException {

          ReplaceQuote oReplaceQuote = new ReplaceQuote();
          oReplaceQuote.start("D:/My Documents/NetBeansProjects/Business/web/js/index.js" // Откуда открываем свой JS
                            , "C:/NewJS.txt"   // Куда сохраняем модифицированный новый JS 
                            , "C:/NewObject.txt"); // Куда сохраняем Обьект
     }

     
     public void start(String sPathOpenJS, String sPathSaveJS, String sPathSaveObject) throws IOException {
          aListLoad = LoadFromFile(sPathOpenJS);

          for (int nJS = 0; nJS < aListLoad.size(); nJS++) { // проходим по всему списку файла JS
               String sLine = aListLoad.get(nJS);    // берем 1 строчку
               if (sLine.contains("•")) {  // если строка содержит хоть 1 знак "•" то
                    int nCount = 0;  // счетчик символов "•"
                    for (int nChar = 0; nChar < sLine.length(); nChar++) {   // проходимся по строке
                         if (sLine.substring(nChar, nChar + 1).equals("•")) { // если в строке больше 1 символа "•"
                              nCount++;   // увеличиваем счетчик
                         }
                    }
                    if ((nCount % 2) == 0) {  // Всё ОК! В строке встретилось четное кол-во знаков '•', значит целостность цитат не нарушена.
                         ReplaceQuote.makeObject(nCount / 2, nJS); // создаем объект столько раз, сколько было найдено цитат в строке
                    } else {
                         System.out.println("--- ERROR!  Нарушение целостности цитаты в строке: (" + (nJS + 1) + ") - НЕЧЕТНОЕ число меток '•', а длжно быть четно (открывающаяся и закрывающаяся)!");
                    }
               }
          }
          aObject.add("}");
          saveFile(aListLoad, sPathSaveJS);  // сохраняем в файл измененния в JS
          saveFile(aObject, sPathSaveObject); // сохраняем в файл объект с цитатами
     }

     /**
      * Создаем объект и меняем цитату на запись из этого объекка столько раз,
      * сколько цитат встретилось в строке
      *
      * @param nCount - кооличество цитат в строке
      * @param nLine - номер строки файла JS
      */
     public static void makeObject(int nCount, int nLine) {

          for (int i = 0; i < nCount; i++) {
               String sLine = aListLoad.get(nLine); // открываем строку
               int nFirst = sLine.indexOf("•");         // получаем индекс первого символа (первой встреченной) цитаты
               int nLast = sLine.indexOf("•", nFirst + 1); // получаем индекс последнего символа (первой встреченной) цитаты
               String sQuote = sLine.substring(nFirst - 1, nLast + 1 + 1);      // вырезаем цитату вместе с ковычками и исмволом "•", например:  "•Клик мышью•". кавычки могут быть одинарные, например: '•Клик мышью•'
               //System.out.println(sQuote.substring(0,1));               //System.out.println(sQuote.substring(sQuote.length()-1, sQuote.length())  );
               if ( // проверяем, чтобы в цитате обязвтельно присутствовали символы ковычек впереди и в конце, иначе ошибка!!!
                       (sQuote.substring(0, 1).equals("\""))
                       ^ (sQuote.substring(0, 1).equals("'"))
                       && (sQuote.substring(sQuote.length() - 1, sQuote.length()).equals("\""))
                       ^ (sQuote.substring(sQuote.length() - 1, sQuote.length()).equals("'"))) {
                    // Значит OK!
               } else {
                    System.out.println(""
                            + "ERROR! В строке:" + (nLine + 1) + "\r\n"
                            + "Преобразование цитаты в данной строке не выполнено! \r\n"
                            + "Символ \" и символ •, должны быть слитно (рядом), например: \"•Цитата•\"  \r\n"
                            + "исправте ошибку и перезапустите метод! \r\n"
                            + "-------------------------------------------");
                    return; // прекращаем обработку строки.
               }
               String sQuoteTrunc = sQuote.substring(2, sQuote.length() - 2); // отсекаем первые и последние 2 символа
               int nSize = aObject.size(); // счетчик строк Объекта
               // создаем объект JS
               if (nSize >= 2) {
                    aObject.add(",s" + nSize + ":\"" + sQuoteTrunc + "\"");
               }
               if (nSize == 0) {
                    aObject.add("var oaText={");
                    aObject.add("s" + nSize + ":\"" + sQuoteTrunc + "\"");
               }
               String sReplaceQoute = sLine.replace(sQuote, "oaText.s" + nSize); // заменяем первую встречную цитату на имя Объекта
               aListLoad.set(nLine, sReplaceQoute);  // сохраняем изменения в списке
          }
     }

     public void saveFile(ArrayList<String> aList, String sFilePath) throws IOException {
          FileWriter writer = new FileWriter(sFilePath); //("c:/output.txt");
          try {
               //     FileWriter writer = new FileWriter("c:/output.txt");
               for (int n = 0; n < aList.size(); n++) {
                    writer.write(aList.get(n) + "\r\n");
               }
               writer.close();
          } catch (IOException ioE) {
               ioE.printStackTrace();
          } finally {
               writer.close();
          }
     }

     public ArrayList LoadFromFile(String path) {
          ArrayList<String> list = new ArrayList<String>();
          BufferedReader oBR = null;
          try {
               String sCurrentLine;
               oBR = new BufferedReader(new FileReader(path));
               while ((sCurrentLine = oBR.readLine()) != null) {
                    list.add(sCurrentLine);
               }
               // System.out.println("Всего элементов в массиве  "+list.size());
          } catch (IOException oException) {
               oException.printStackTrace();
          } finally {
               try {
                    if (oBR != null) {
                         oBR.close();
                    }
               } catch (IOException oException) {
                    oException.printStackTrace();
               }
               return list;
          }

     }
}






//                    if (nCount == 2) {   // создаем запись в Объекте и заменяем в тексте цитату на номер записи в этом объекте
//                         int nFirst = sLine.indexOf("•");
//                         int nLast = sLine.lastIndexOf("•");
//                         String sQuote = sLine.substring(nFirst-1, nLast+1+1);                      //String sQuoteTrunc = sQuote.replaceAll("•","").replaceAll("'","");
//                         String sQuoteTrunc = sQuote.substring(2, sQuote.length() - 2);
//                         //System.out.println(sLine.substring(nFirst, nLast+1));
//
//                         int nSize = aObject.size(); // счетчик строк Объекта
//                         // создаем объект JS
//                         if (nSize >= 2) {
//                              aObject.add(",s" + nSize + ":\"" + sQuoteTrunc + "\"");
//                         }
//                         if (nSize == 0) {
//                              aObject.add("var oaText={");
//                              aObject.add("s" + nSize + ":\"" + sQuoteTrunc + "\"");
//                         }
//
//                         String sReplaceQoute = sLine.replace(sQuote, "oaText.s" + nSize);
//                         aListLoad.set(i, sReplaceQoute);  //
//                         // sChangePassword:"Изменить пароль"
//                    }
// System.out.println(string);
//public static void main(String[] args) throws SQLException, IOException {
//
//          OtherMethods oOtherMethods = new OtherMethods();
//          aListLoad = oOtherMethods.ListFromFile("D:/My Documents/NetBeansProjects/Business/web/js/index.js");
//
//          for (int nJS = 0; nJS < aListLoad.size(); nJS++) { // проходим по всему списку файла JS
//               String sLine = aListLoad.get(nJS);    // берем 1 строчку
//               if (sLine.contains("•")) {  // если строка содержит хоть 1 знак "•" то
//                    int nCount = 0;  // счетчик символов "•"
//                    for (int nChar = 0; nChar < sLine.length(); nChar++) {   // проходимся по строке
//                         if (sLine.substring(nChar, nChar+1).equals("•")) { // если в строке больше 1 символа "•"
//                              nCount++;   // увеличиваем счетчик
//                         }
//                    }
//                    
//                    if ((nCount % 2) == 0) {  // Всё ОК! В строке встретилось четное кол-во знаков '•', значит целостность цитат не нарушена.
//                         TestReplaceQuote.makeObject(nCount/2, nJS); // создаем объект столько раз, сколько было найдено цитат в строке
//                    } else {
//                         System.out.println("--- ERROR!  Нарушение целостности цитаты в строке: (" +(nJS+1)+ ") - НЕЧЕТНОЕ число меток '•', а длжно быть четно (открывающаяся и закрывающаяся)!");
//                    }
//
//               }
//          }
//          // saveFile(aList);
//          aObject.add("}");
//          saveFile(aObject, "c:/aObject.txt"); // сохраняем в файл объект с цитатами
//          saveFile(aListLoad, "c:/aJS.txt");  // сохраняем в файл измененния в JS
//     }