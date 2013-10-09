//@Servlet(urlMappings={"/MyApp"})
//@WebServlet(name="mytest", urlPatterns={"/myurl"}) 
//(name="mytest", urlPatterns={"/myurl"}) ;
package com.bw.service;

import javax.swing.Timer;

import com.bw.entity.*;
import com.bw.io.ConnectLdap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
//import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.Servlet;

import javax.servlet.http.*;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
//@Servlet(urlMappings={"/CreateAccount"})
//@WebServlet(  
//        name = "CreateAccount",
//        urlPatterns = {"/CreateAccount", "/helloanno"},
//        asyncSupported = false,
//        initParams = {
//                @WebInitParam(name = "name", value = "admin"),
//                @WebInitParam(name = "param1", value = "value1"),
//                @WebInitParam(name = "param2", value = "value2")
//        }
//)


public class Login extends HttpServlet {

     
          
  //   public static java.util.Timer timer = new java.util.Timer();
   //  public static int TimerCount = 0;
     
     
//   public  class RunMeTask extends TimerTask   //extends HttpServlet
//  {     
//	@Override
//	public void run() { 		//System.out.println("Run Me ~");    
//                TimerCount++;    
//                
//                String[] sArrSession1 = {  // создаем строковый  массив с инф. о пользователе
//                                  "sEmail", "sEmail", "sEmail", "sEmail", "sEmail", "sEmail",
//                                  };
//                             
//                     aAllSession.add(sArrSession1); // переносим в Список Массивов для хранения
//                 //sIDCurrentSession =  HttpServletRequest.getSession().getId();
//                 HttpSession session = request.getSession();
//                 if (request. session.getId() == null)
//                     //    sIDSession
//                      Login.
//                     
//                if (TimerCount == 50)
//                timer.cancel();
//	}
//}   
//   
   
      
     
    
  
     
     
     //============================
// private static TimerTask task = new TimerTask() {
//  
//  @Override
//  public void run() //(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
//    
//  {     //Do work!         //    aListAllSession.remove(0);
//           //System.out.println("1");
//            //aAllSession.remove(0);    
//       String[] sArrSession1 = {  // создаем строковый  массив с инф. о пользователе
//                                  "sEmail", "sEmail", "sEmail", "sEmail", "sEmail", "sEmail",
//                                  };
//                        
//                   aAllSession.add(sArrSession1); // переносим в Список Массивов для хранения
//               //     sIDCurrentSession =  HttpServletRequest.getSession().getId();
//                 //HttpSession session = Login.getSession();
//                 Login.
//                   
//            timer.cancel();  
//           //if (request.getSession() == null){
//          //if (request.getSession() == null){
//          //  }
//               
//      }
//};

    
 
   
  //  public static String sIDCurrentSession = "";
   
    public static ArrayList<String> aListAllSession = new ArrayList<String>();
    //public ArrayList<String> aListAllSession = new ArrayList<String>();
    //public String sIsOnline = "Online";
    public static ArrayList<String[]> aAllSession = new ArrayList<String[]>();
    private int countEnter = 5;
//    public static HttpServletRequest request1;
    
    protected void processRequest( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String              sReturn = "",
                            sDO = "" , 
                                sEmail = "",
                                sPassword = "",
                                
                                sCookie = "",
                               sSess = ""
                            ;
           
 // sSess = request.getSession().getId(); 
        
        try {

//       HttpSession session = request.getSession(true);  
//   Object o = session.getAttribute("sLogin");
             
            
//      TimerTask task = new TimerTask() {
//       
//
//        
//  @Override
//  public  void  run() //(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
//    
//  {     //Do work!         //    aListAllSession.remove(0);
//     //HttpServletRequest request2;//
//     // HttpSession session1 = request1.getSession();
//      //  String sessionId  = session1.getId(); 
//       //System.out.println("1");
//            //aAllSession.remove(0);    
//
//       
//      String sessionId  = sSess;
//     //  HttpSession session = Login.request.getSession();
//       //String sessionId = session.getId();
//       // HttpServletRequest request2;// = getSession().getId();
//       //String sessionId  =  request2.getSession().getId();
//               //Login.request1.getSession().getId();
//       
//              // HttpServletRequest.getSession().getId();
//       String[] sArrSession1 = {  // создаем строковый  массив с инф. о пользователе
//                                  "sEmail", "sEmail", "sEmail", "sEmail", "sEmail", "sessionId",
//                                  };       
//                        HttpSession session = request.getSession();
//                       // Login.request.getSession().getId();
//                   aAllSession.add(sArrSession1); // переносим в Список Массивов для хранения
//                   
//                   TimerCount++;   
//                   
//                   //sIDCurrentSession
//                   
//                    if (TimerCount == 5)
////                timer.cancel();
//            timer.cancel();  
////           sIDCurrentSession =  session.getId();            
//         //  sIDCurrentSession = request.getPathInfo();
//           
//      }
//};    
         
             
             
               sDO = request.getParameter("sDO"); //вытягиваем параметры
               sEmail = request.getParameter("sEmail");
               sPassword = request.getParameter("sPassword");
     
               sCookie = request.getParameter("sCookie");
            
               // if ("theUserLoginCoockie".equals(sDO)){                }
               
               
               
               if ("theUserExists".equals(sDO)){
                 Access Ac = new Access();
                if (Ac.bLoginExists(sEmail) == true){ // true - Емаил существует в базе
                 sReturn = "{\"sReturnExists\":\"" + "YES" + "\"}";
               }
                else
                sReturn = "{\"sReturnExists\":\"" + "NO" + "\"}";     
               }
                    
    //------------- ВХОД пользователя ---------------
            if ("theUserLogin".equals(sDO)){
           
              //Вход по Куке
                 // если кука не пустая то по куки достаем Логин и Пароль из базы
           if (!"".equals(sCookie)){ 
               // sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\" }"; //не менять
               
               // sEmail = "1";
               // sPassword = "2";
               }     
                 
                 
            // Вход по Логину - Паролю 
                Access A = new Access();
                if (A.bLoginExists(sEmail) == true) { // true - Емаил существует в базе
                   // String Pass = "";
                    String Pass = A.getPassword(sEmail);   // смотрим  Пароль по Емайлу
                    if (sPassword.equals(Pass))  {      // если Пароли совпадают
                    
                         
                         String sNID = A.getNID(sEmail); // Получаем NID Логина
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
                              sCreateCookie = sNID+"&"+ sCreateCookie;
                               
                          
                         
                           HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                           session.setAttribute("sEmail", sEmail);
                           session.setAttribute("sPassword", sPassword);
                        
                                 Date d = new Date(/*tmp*/);
                                 DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                 String sTimeLogin = df.format(d);
                        
                                 //Date d1 = new Date(session.getLastAccessedTime());         DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");                                  String sLastAccessedTime = df1.format(d1);
                                 
                           // формируем ячейки таблицы      
                        //aListAllSession.add(sEmail+"   "+session.getId()+"   "+sTimeLogin+"   "+request.getRemoteAddr()+"   "+ request.getServerName());
                      
                       
                        String[] sArrSession = {  // создаем строковый  массив с инф. о пользователе
                                  sEmail,
                                  session.getId(),
                                  sTimeLogin,
                                  "Подключен",
                                  request.getRemoteAddr(),
                                  request.getServerName()
                                  };
                             
                             aAllSession.add(sArrSession); // переносим в Список Массивов для хранения
                             
                             
                             
                                 
//                             aListAllSession.add(
//                                     
//                              "<td>"+sEmail+"</td>"+ 
//                              "<td>"+session.getId()+"</td>"+ 
//                              "<td>"+sTimeLogin+"</td>"+
//                              "<td>"+sLastAccessedTime  +"</td>"+ 
//                              "<td>"+request.getRemoteAddr()+"</td>"+
//                              "<td>"+request.getServerName()+"</td>");
                       
                        
                        // Запись инфы в базу о пользователе при Входе пользователя
                         AccessOf.saveInfoWhenUserLogined(sEmail);
                         
                         // включаем таймер
                      //   TimerTask task = new RunMeTask(); 
                        // this.request1 = request ;
    	               //  timer.schedule(task, 100,1000);
                         // sIDCurrentSession = session.getId();
                         
                           
                                                                              // нельзя чтобы в json было пустое значение
                        sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" + sCreateCookie+ "\" }"; //не менять
                        
                                
//                        while (TimerCount < 5){
//                             TimerCount++;
//                         String[] sArrSession1 = {  // создаем строковый  массив с инф. о пользователе
//                                  "sEmail", "sEmail", "sEmail", "sEmail", "sEmail", "sessionId",
//                                  };       
//                            aAllSession.add(sArrSession1); // переносим в Список Массивов для хранения
//                             Thread.sleep(1000);
//                        }
                        
                        
                        //sReturn = "{\"sReturn\":\"" + "Добро пожаловать на сайт!" + "\"}";
                    } else { // неверный пароль
                   //         countEnter--; 
                            sReturn = "{\"sReturn\":\"" + "Неверный Логин или Пароль!" + "\"}";
                            }

                    }
                else{  // несуществующий Логин
                                         //countEnter--;   
                                         //sReturn = "{\"sReturn\":\"" + "Неверный Логин или Пароль!" +  "\"}";
                         //Регистрируем  
                        Access Aсс = new Access();
                        String s = Aсс.userRegistration(sEmail, sPassword);
                        sReturn = "{   \"sReturn\":\""+s+"\"    }"; 
                        //sReturn = "{   \"sReturn\":\""+s+"\"   \"sReturnFirst\":\""+s+"\"    }"; 
                           
                           HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                           session.setAttribute("sEmail", sEmail);
                           session.setAttribute("sPassword", sPassword);
                       //(Осталось попыток: " +countEnter+" )"+
                 }
         
                
                
                
                
            }

            
            
            
            
            
            if ("theDestroySession".equals(sDO)) {
                HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                session.invalidate();
                
                sReturn = "{\"sReturn\":\"" + "Сессия удалена!" + "\"}";
                
            } 
            
            
            if ("theGetAllSessionList".equals(sDO)) {
                String s = "";
                
                //for (int i = 0; i < aAllSession.size(); i++) { }
                for (String[] temp: aAllSession){     //aListAllSession
                  s = s+"<tr>"+  
                          "<td>"+temp[0]+"</td>"+ 
                          "<td>"+temp[1]+"</td>"+ 
                          "<td>"+temp[2]+"</td>"+ 
                          "<td>"+temp[3]+"</td>"+ 
                          "<td>"+temp[4]+"</td>"+ 
                          "<td>"+temp[5]+"</td>"+ 
                          "</tr>";  // делаем табличные строки
                }  // Добавляем шапку к таблице
                    s = "<tr>  <td>E-Mail</td> <td>ID Session</td><td>Время входа</td><td>Статус</td><td>IP адрес</td><td>ServerName</td>  </tr>"+s;
                    
                sReturn = "{\"sReturn\":\"" + s + "\"}";
            } 

            
            
            
            
//======================================================================

        } catch (Exception _) {
            String sErr = _.getMessage();
            System.err.println("--ERROR_CreateAccount:  " + sErr + " _ " + sReturn);  //это вывод в лог-файл
            sReturn = "{\"sReturn\":\"Error, ошибка в сервлете \"}" + sErr;

        }                             //throw new RuntimeException(_); раскомментировав эту строку можно прерывать выполнение класса при этой ошибке
        finally {                    //этот код выполнится даже если произойдет ошибка (иногда это очень важно, чтоб, например - закрыть соединение
                                     //  response.getWriter().write(sReturn);
           // if (sReturn == "") sReturn = "Пусто";
            response.getWriter().write(sReturn); // возвращаемые данные

        }


        
        
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
    
   
}

//==================
//                         try {
//                         // Выборка всех записей сессии    
//                        Enumeration keys = session.getAttributeNames();
//                        while (keys.hasMoreElements())
//                            {
//                            String key = (String)keys.nextElement();
//                            sSess = sSess + (key + "_" + session.getValue(key) + "");
//                            }
//                        
//                             } catch (Exception e) {
//                                String sErr = e.getMessage();
//                                System.err.println("--ERROR_CreateAccount:  " + sErr + " _ " + sReturn);  //это вывод в лог-файл
//                                sReturn = "{\"sReturn\":\"Error, ошибка в сервлете \"}" + sErr;
//                                     }  
     //==================    





  // вызывается 1 раз               
//  java.util.Timer timer2 = new java.util.Timer();
//  TimerTask task = new TimerTask() {
//      public void run()
//      {     //Do work!         //    aListAllSession.remove(0);
//           aListAllSession.add("<td>"+1+"</td>"+ 
//                              "<td>"+2+"</td>"+ 
//                              "<td>"+3+"</td>"+
//                              "<td>"+ 4 +"</td>"+ 
//                              "<td>"+5+"</td>"+
//                              "<td>"+6+"</td>");
//      }
//  };
//  timer2.schedule( task, 5000 );
  
 
////int delay = 1000; //milliseconds
////  ActionListener taskPerformer = new ActionListener() {
////    
////       public void actionPerformed(ActionEvent evt) {
////          //...Perform a task...
////           aListAllSession.add("<td>"+1+"</td>"+ 
////                              "<td>"+2+"</td>"+ 
////                              "<td>"+3+"</td>"+
////                              "<td>"+ 4 +"</td>"+ 
////                              "<td>"+5+"</td>"+
////                              "<td>"+6+"</td>");
////       //    setRepeats(false) ;
////      }
////  };
////   new Timer(delay, taskPerformer).start();
 
//Timer tim = null;                 
//tim = new Timer(1000, new ActionListener() {
//    //@Override
//    public void actionPerformed(ActionEvent ae) {
//
//         aListAllSession.add("<td>"+1+"</td>"+ 
//                              "<td>"+2+"</td>"+ 
//                              "<td>"+3+"</td>"+
//                              "<td>"+ 4 +"</td>"+ 
//                              "<td>"+5+"</td>"+
//                              "<td>"+6+"</td>");
//    //tim.stop();    
//    }
//        
//});                 
//  tim.start();
   
  
  //timer2.schedule( task, date ); //date - java.util.Date
                 