
package com.bw.service;

import javax.swing.Timer;

import com.bw.entity.*;
import com.bw.io.ConnectLdap;
import com.bw.io.MailText;
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

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


@WebServlet(name = "Login", urlPatterns = {"/Login"})




  public class Login extends HttpServlet {
   
    private Logger oLog = Logger.getLogger(getClass()); /*getClass()*/
    
    public static ArrayList<String> aListAllSession = new ArrayList<String>();
    //public ArrayList<String> aListAllSession = new ArrayList<String>();
    public static ArrayList<String[]> aAllSession = new ArrayList<String[]>();
    

    
    protected void processRequest( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


          
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        
        DOMConfigurator.configure(getServletContext().getRealPath("")+"/WEB-INF/config/"  + "log4j.xml");
        
        String              sReturn = "-nol-",
                            sDO = "" , 
                                sEmail = "",
                                sPassword = "",                           
                                sCookie = ""
                                //sSess = ""
                            ;
        

        try {

//      HttpSession session = request.getSession(true);  
//   Object o = session.getAttribute("sLogin");
            
   //      oLog.info(" Hello from Servlet===333");    
        // oLog.error(" Hello from Servlet---------111");    
             
               sDO = request.getParameter("sDO");   //вытягиваем параметры
               sEmail = request.getParameter("sEmail");
               sPassword = request.getParameter("sPassword");
               sCookie = request.getParameter("sCookieLogin");
            
               
//------------- Отправка ссылки для "входа без пароля" на Емаил---------------
             if ("theSendEmail".equals(sDO)) {               
                  
                  Access A = new Access(); 
                  if (A.bLoginExists(sEmail) == true) {       // true - Емаил существует в базе
                  
                  AccessAuth AA = new AccessAuth();
                  String sCookieDB = AA.findCookie(sEmail) ;  // берем куку пользователя (самую старую)  // еще нужно будет сделать, генерацию и добавление куки в базу при нажатии на "отправить ссылку на почту"

                  MailText mt = new MailText();   //sEmail   
                //  mt.sendMail(sEmail, "123");
                  // надо сделать еще генерацию Куки для пользователей у которых нету куки в Базе ??? 
                  mt.sendMail(sEmail, "Ваша ссылка для входа в PGASA без пароля:  \n\n  http://localhost:8080/#sDO=theLoginForCookie&sCookieLogin="+sCookieDB +"  \n\n ") ;
                  //  http://localhost:8080/#sDO=theLoginForCookie&sCookieLogin=31%26eofrrqpcrgkshspqxmkserqihewgaxqeazdrfjmgfuqunpkanu
                  
                  sReturn = "{\"sReturn\":\"" + "MailSendOk!" + "\"}";
                  } else {
                         sReturn = "{\"sReturn\":\"" + "MailSendFail!" + "\"}";
                         }
             }
               
//------------- проверка существования Емайла---------------
             if ("theUserExists".equals(sDO)) {
                  Access Ac = new Access();
                  if (Ac.bLoginExists(sEmail) == true) {    // true - Емаил существует в базе
                       sReturn = "{\"sReturn\":\"" + "EmailExists" + "\"}";
                  } else {
                       sReturn = "{\"sReturn\":\"" + "NoEmailExists" + "\"}";
                  }
                                                            //Thread.sleep(2000); // задержка отправки ответа на 2 сек.
             }

//------------- ВХОД пользователя через куку ---------------
               if ("theLoginForCookie".equals(sDO)){ 
              
                    AccessAuth AA = new AccessAuth();
                    ArrayList<String> list1 = new ArrayList<String>();
                    list1 = AA.findUserFromCookie(sCookie, request.getLocalAddr());
                                          
                           String s1 =  list1.get(0); // Емаил
                           String s2 =  list1.get(1);  // Пароль

                           if ((s1!="0")&(s2!="0")) {

                                HttpSession session = request.getSession(true);    //создаем сессию для пользователя с его данными!
                                session.setAttribute("sEmail", s1); //sEmail
                                session.setAttribute("sPassword", s2); //sPassword

                              // Отправляем ответ, что на сайт юзера можно пускать и отправляем куку, для обновления времени.
                                sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" + sCookie + "\" }"; 
                           }
                           else {
                                sReturn = "{\"sReturn\":\"" + "Ложная кука!" + "\"}"; 
                              // sReturn = "{\"sReturn\":\"" + "Добро пожаловать на сайт!" + "\"}"; //не менять
                           }
               }
              
               
//------------- ВХОД пользователя через ввод Емайла и пароля ---------------
            if ("theUserLogin".equals(sDO)){
                 
                Access A = new Access();
                if (A.bLoginExists(sEmail) == true) {       // true - Емаил существует в базе
                    String Pass = A.getPassword(sEmail);    // смотрим  Пароль по Емайлу
                    if (sPassword.equals(Pass))  {          // если Пароли совпадают
                    
                            Date d = new Date();       // узнаем текущее время
                            DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            String sTimeLogin = df.format(d);

                              String sNID = A.getNID(sEmail);     // Получаем NID пользователя по его Емайлу
                              AccessAuth AA = new AccessAuth(); 
                              String sGenerate = AA.generateString();  // генерируем строку 50 символов для куки
                              String sCreateCookie = sNID+"&"+sGenerate;   // соединяем в одну строку
                              // сохраняем Куку в Базу
                              AA.saveCookieToDB(Integer.parseInt(sNID), sCreateCookie, sTimeLogin, 3);

                                 HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                                 session.setAttribute("sEmail", sEmail);
                                 session.setAttribute("sPassword", sPassword);
                 
                                               
             
                        String[] sArrSession = {  // создаем строковый  массив с инф. о пользователе
                                  sEmail,
                                  session.getId(),
                                  sTimeLogin,
                                  "Подключен",
                                  request.getRemoteAddr(),
                                  request.getServerName()
                                  };
                         aAllSession.add(sArrSession); // переносим в Список Массивов для хранения
                              
     
                        // Запись в базу инфы о пользователе при его Входе
                            AccessOf.saveInfoWhenUserLogined(sEmail);
                         
                                                            // включаем таймер
                                                         //   TimerTask task = new RunMeTask(); 
                                                          //  timer.schedule(task, 100,1000);
                         
                       
                                               // нельзя чтобы в json было пустое значение
                        sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" + sCreateCookie+ "\" }"; //не менять
                        
                                
//                         String[] sArrSession1 = {  // создаем строковый  массив с инф. о пользователе
//                                  "sEmail", "sEmail", "sEmail", "sEmail", "sEmail", "sessionId",
//                                  };       
//                            aAllSession.add(sArrSession1); // переносим в Список Массивов для хранения
                        
                        ;
                    } else {    // неверный пароль FailPassword   Неверный Логин или Пароль
                            sReturn = "{\"sReturn\":\"" + "FailPassword!" + "\"}";
                            }

                    }
                    else{   // несуществующий Логин // значит попытка регистрации
                             //Регистрируем  
                            Access Aсс = new Access();
                            String s = Aсс.userRegistration(sEmail, sPassword);
                              if (s.equals("Добро пожаловать на сайт!")){ // регистрация  удалась, то полдолжаем
                               
                                   HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                                   session.setAttribute("sEmail", sEmail);
                                   session.setAttribute("sPassword", sPassword);

                                     Date d = new Date();
                                     DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                     String sTimeLogin = df.format(d);

                                       String sNID = A.getNID(sEmail); // Получаем NID пользователя по Емайлу
                                       AccessAuth AA = new AccessAuth(); 
                                       String sGenerate = AA.generateString();
                                       String sCreateCookie = sNID+"&"+sGenerate; // соединяем в одну куку

                                       AA.saveCookieToDB(Integer.parseInt(sNID), sCreateCookie, sTimeLogin, 3);
                             }
                              
                              sReturn = "{   \"sReturn\"  :  \""+s+"\"    }";  // ответ в любом случае

                     }
         
                
                
                
                
            }


                     
            if ("theDestroySession".equals(sDO)) {
                HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                session.invalidate();
                
                sReturn = "{\"sReturn\":\"" + "Destroyed!" + "\"}";      
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
        



//------------------------ таймеры
          
//      TimerTask task = new TimerTask() {
     
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


//-------------------------------

//   public static java.util.Timer timer = new java.util.Timer();
     
     
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

    