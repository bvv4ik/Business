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

    public ArrayList<String> aListAllSession = new ArrayList<String>();
    //public ArrayList<String> aListAllSession = new ArrayList<String>();
    //public String sIsOnline = "Online";
     public static ArrayList<String[]> aAllSession = new ArrayList<String[]>();
    private int countEnter = 5;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String              sReturn = "",
                            sDO = "" ,
                                sEmail = "",
                                sPassword = ""
                               //sSess = ""
                            ;
           
        try {

            sDO = request.getParameter("sDO"); //вытягиваем параметры
               sEmail = request.getParameter("sEmail");
               sPassword = request.getParameter("sPassword");
     
            
            
    //------------- ВХОД пользователя ---------------
            if ("theUserLogin".equals(sDO)) {
                
                
//                if (countEnter == 0){
//                    sReturn = "{\"sReturn\":\"" + "Вы заблокирован !" + "\"}";
//                    return;
//                }
//    int delay = 5000;   // delay for 5 sec.
//    int interval = 1000;  // iterate every sec.
//    Timer timer = new Timer();
//    timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                // Task here ...
//                
//            }
//        }, delay, interval);
//                

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
 
//Timer tim;                 
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
//       
//    }
//        
//});                 
//  tim.start();
//    tim.stop();
  
  //timer2.schedule( task, date ); //date - java.util.Date
                 
                 
                 
                
                Access A = new Access();
                if (A.bLoginExists(sEmail) == true) { // true - Емаил существует в базе
                    String Pass = "";
                    Pass = A.getPassword(sEmail);   // смотрим  Пароль по Емайлу
                    if (sPassword.equals(Pass))  {      // если Пароли совпадают
                    
                           HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                           session.setAttribute("sEmail", sEmail);
                           session.setAttribute("sPassword", sPassword);
                        
                                 Date d = new Date(/*tmp*/);
                                 DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                 String sTimeLogin = df.format(d);
                        
                                 Date d1 = new Date(session.getLastAccessedTime());         DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");                                  String sLastAccessedTime = df1.format(d1);
                                 
                           // формируем ячейки таблицы      
                        //aListAllSession.add(sEmail+"   "+session.getId()+"   "+sTimeLogin+"   "+request.getRemoteAddr()+"   "+ request.getServerName());
                        String[] sArr2 = {  
                                  sEmail,
                                  session.getId(),
                                  sTimeLogin,
                                  "150",
                                  request.getRemoteAddr(),
                                  request.getServerName()
                                  };
                             
                             aAllSession.add(sArr2);
                             
                             
                             
                                 
                             aListAllSession.add("<td>"+sEmail+"</td>"+ 
                              "<td>"+session.getId()+"</td>"+ 
                              "<td>"+sTimeLogin+"</td>"+
                              "<td>"+ sLastAccessedTime  +"</td>"+ 
                              "<td>"+request.getRemoteAddr()+"</td>"+
                              "<td>"+request.getServerName()+"</td>");
                        //+"   "+session.getCreationTime()+"   "+session.getLastAccessedTime()
                        
                        // Запись инфы в базу о пользователе при Входе пользователя
                         AccessOf.saveInfoWhenUserLogined(sEmail);
                         
                                                                              // нельзя чтобы в json было пустое значение
                        sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\" }"; //не менять
                        //sReturn = "{\"sReturn\":\"" + "Добро пожаловать на сайт!" + "\"}";
                    } else { // неверный пароль
                   //         countEnter--; 
                            sReturn = "{\"sReturn\":\"" + "Неверный Логин или Пароль!" + "\"}";
                            }

                    }
                else{  // несуществующий Логин
                 //countEnter--;   
                 sReturn = "{\"sReturn\":\"" + "Неверный Логин или Пароль!" +  "\"}";
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
                
                //for (int i = 0; i < aListAllSession.size(); i++) { }
                for (String temp: aListAllSession){ 
                  s = s+"<tr>"+ temp+"</tr>";  // делаем табличные строки
                }
                    s = "<tr>  <td>E-Mail</td><td>ID Session</td><td>TimeLogin</td><td>LastAccessedTime</td><td>RemoteAddr</td><td>ServerName</td>  </tr>"+s;
                    
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