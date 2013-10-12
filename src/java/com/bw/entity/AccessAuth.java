/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

import com.bw.io.ConnectSybase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
//import java.util.ArrayList;
//import java.util.List;

/**
 *
 * @author Sergey
 */
public class AccessAuth {
     
     
     
     
     
          /**
           *     Сохраняем Куку пользователя в Базе при входе
           * @param nID_Access - nID пользователя
           * @param sAuth   - сама Кука
           * @param sDateMake - дата создания
           */
     
     public void saveCookieToDB (int nID_Access, String sAuth, String sDateMake, int countMax) throws Exception {
     int i = 0;
          Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
          try {

               
                ResultSet oSet = oDC.prepareStatement("SELECT nCount=count(*) FROM AccessAuth where nID_Access = " + nID_Access).executeQuery();
               if (oSet.next()) {
                    i = oSet.getInt("nCount");
               }

               if (i <= countMax) {  // если 0, 1 или 2 или 3 записи всего, то просто добавляем
                    oDC.prepareStatement("INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                    + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')").executeUpdate();
               }
                    
               if (i > countMax) { // если больше допустимого кол-ва записей (трех например)

                     // удяляем 1 самую верхнюю(старую)  запись       
                    oDC.prepareStatement("DELETE top 1 FROM AccessAuth WHERE nID_Access = "+nID_Access).executeUpdate();         
                    // добавляем новую запись
                    oDC.prepareStatement("INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                            + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')").executeUpdate();
               }
               
                    

          } catch (Exception e) {
              // return "Ошибка создания записи БД: Класс AccessAuth";
          } finally {
               ConnectSybase.closeConnect("UA_DP_PGASA", oDC);  // так делать всегда!!1
          }
  
}
     
     
      public  ArrayList<String> findUserFromCookie (String sCookie) throws Exception {
      String sLogin = "";
      String sPassword = "";
       ArrayList<String> list1 = new ArrayList<String>();
          Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
          try {
                //String sCookie =   "31&cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr";
                                  //  31%26cfiopfokjcotrmhhkhenhgfxpkhvhphvlfaijtkxylcvywhjhr  
                String s = sCookie;
                String nID_Access ="";
              //  String sCookieClient = "";
                String sCookieDB = "";
                
                //берем ИД клиента
                if (s.indexOf("&") != -1){          // если есть знак "&"
                nID_Access = s.substring(0, s.indexOf("&")); // Берем все что сначала строки и до знака "&" 
                }
                
                // берем код Куки клиента
               //  if (s.indexOf("&") != -1){             // если есть знак "&"
               //  sCookieClient = s.substring(s.lastIndexOf("&")+1); // берем все что после "&" и до конца 
               //  }
                
                 // Получаем Емаил и Пароль по ИД
                ResultSet oSet = oDC.prepareStatement("SELECT sLogin, sPassword FROM Access where nID = " + nID_Access).executeQuery();               
                if (oSet.next()) {
                    sLogin = oSet.getString("sLogin");
                    sPassword = oSet.getString("sPassword");
                }
                
                
                // Получаем всю Куку из базы
                ResultSet oSet1 = oDC.prepareStatement("SELECT top 1 sAuth FROM AccessAuth where sAuth = '" + sCookie+ "'").executeQuery();               
                if (oSet1.next()) {
                    sCookieDB = oSet1.getString(1);
                }
                
                if (sCookie.equals(sCookieDB)) {// если куки совпадают, то возвращаем Емайл и Пароль клиента
                    list1.add(sLogin) ;               
                    list1.add(sPassword) ;               
                }
                else 
                {   // ТРЕВОГА !!! возвращаем нули
                     list1.add("0") ;               
                     list1.add("0") ;
                }

                   // turn list1;return list1;
              // return list1;

          } catch (Exception e) {
              // return "Ошибка создания записи БД: Класс AccessAuth";
          } finally {
               ConnectSybase.closeConnect("UA_DP_PGASA", oDC);  // так делать всегда!!1
               return list1;
          }
  
}
     
     public String generateString() throws Exception {
          //создаем строку из 50 случайных символов для Куки
          String sCreateCookie = "";
          for (int i = 0; i < 50; i++) {
               Random rand = new Random();
               int nRandom = rand.nextInt();
               // No.2 Случайное целое число от 0 до 10
               nRandom = rand.nextInt(26);
               int a = (int) 'a';
               char b = (char) (a + nRandom);
               sCreateCookie += b;
          }
          sCreateCookie = /*sNID+"&"+ */ sCreateCookie;

          return sCreateCookie;
     }
     
//    public boolean verifyCookieCount (int nID_Access, int count/*, String sAuth, String sDateMake*/) throws Exception {
//          int i = 0;
//          Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
//          try {
//
//                      ResultSet oSet =oDC.prepareStatement("SELECT count(*) FROM AccessAuth where nID_Access = "+nID_Access ).executeQuery();
//               if(oSet.next()){
//                   i = oSet.getInt(1);
//               }
//               if (i == count)
//                return true;    
//
//               return false;
//
//          } catch (Exception e) {
//               return false;//"Ошибка создания записи БД: Класс AccessAuth";
//          } finally {
//               ConnectSybase.closeConnect("UA_DP_PGASA", oDC);  // так делать всегда!!1
//          }
//  
//} 
//     
//    
//     public void deleteTopCookie (int nID_Access) throws Exception {
//         
//          Connection oDC = ConnectSybase.getConnect("UA_DP_PGASA");
//          try {
//              
//               // удяляем 1 самую верхнюю  запись       
//               oDC.prepareStatement("DELETE top 1 FROM AccessAuth WHERE nID_Access = "+nID_Access).executeUpdate();            
//
//          } catch (Exception e) {
//              // return false;//"Ошибка создания записи БД: Класс AccessAuth";
//          } finally {
//               ConnectSybase.closeConnect("UA_DP_PGASA", oDC);  // так делать всегда!!1
//          }
//  
//} 
    
    
    
     
     
}
