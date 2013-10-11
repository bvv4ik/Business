/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;

import com.bw.io.ConnectSybase;
import java.sql.Connection;
import java.sql.ResultSet;

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

               
                ResultSet oSet = oDC.prepareStatement("SELECT count(*) FROM AccessAuth where nID_Access = " + nID_Access).executeQuery();
               if (oSet.next()) {
                    i = oSet.getInt(1);
               }

               if (i >= countMax) { // если больше допустимого кол-ва записей (трех например)

                     // удяляем 1 самую верхнюю(старую)  запись       
                    oDC.prepareStatement("DELETE top 1 FROM AccessAuth WHERE nID_Access = "+nID_Access).executeUpdate();         
                    // добавляем новую запись
                    oDC.prepareStatement("INSERT INTO AccessAuth(nID_Access, sAuth, sDateMake) "
                            + "VALUES (" + nID_Access + ", '" + sAuth + "', '" + sDateMake + "')").executeUpdate();

                    // return "Сохранено";
               }

          } catch (Exception e) {
              // return "Ошибка создания записи БД: Класс AccessAuth";
          } finally {
               ConnectSybase.closeConnect("UA_DP_PGASA", oDC);  // так делать всегда!!1
          }
  
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
