
package business.service;


import business.Config;
import business.auth.AccessAuth;
import business.auth.Access;
import business.auth.AccessOf;
import business.send.MailText;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.*;
//import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import org.apache.log4j.Logger;

@WebServlet(name = "Login", urlPatterns = {"/Login"})



  public class Login extends HttpServlet {
   
    private Logger oLog = Logger.getLogger(getClass());   
    //private ArrayList<String> aListAllSession = new ArrayList<String>();
    //private ArrayList<String[]> aAllSession = new ArrayList<String[]>();
    
    
    protected void processRequest( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        
        String    sReturn =  "{\"sReturn\":\"" + "-none-" + "\"}", // чтобы не возвращался NULL и небыло ошибки
                  sDO = "" , 
                  sEmail = "",
                  sPassword = "",                           
                  sCookie = ""
                  ;
        
        try {
             
               sDO = request.getParameter("sDO");         //вытягиваем параметры
               sEmail = request.getParameter("sEmail");
               sPassword = request.getParameter("sPassword");
               sCookie = request.getParameter("sCookieLogin");
           
               oLog.info("sDO="+sDO+", sEmail="+sEmail);  
 
               
//------------- Отправка на Емаил пользователя ссылки для "входа без пароля" ---------------
             if ("theSendEmail".equals(sDO)) {
                  Access A = new Access();
                  if (A.bLoginExists(sEmail) == true) {       // true - Емаил существует в базе
                       AccessAuth AA = new AccessAuth();
                       String sCookieDB = AA.findCookie(sEmail);  // берем куку пользователя (самую старую)  // еще нужно будет сделать, генерацию и добавление куки в базу при нажатии на "отправить ссылку на почту"
                       MailText mt = new MailText();   //sEmail   
                       mt.sendMail(sEmail, "Ваша ссылка для входа в PGASA без пароля:  \n\n  " + Config.sValue("sURL") + "/#sAuth=" + sCookieDB + "  \n\n ");
                       //  mt.sendMail(sEmail, "Ваша ссылка для входа в PGASA без пароля:  \n\n  http://localhost:8080/#sDO=theLoginForCookie&sCookieLogin="+sCookieDB +"  \n\n ") ;
                       // http://localhost:8080/#sDO=theLoginForCookie&sCookieLogin=31%26eofrrqpcrgkshspqxmkserqihewgaxqeazdrfjmgfuqunpkanu
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
             }                      //Thread.sleep(2000); // задержка отправки ответа на 2 сек.

             
//------------- ВХОД пользователя через куку ---------------
               if ("theLoginForCookie".equals(sDO)) {
                  AccessAuth AA = new AccessAuth();
                  ArrayList<String> list = new ArrayList<String>();
                  list = AA.findUserFromCookie(sCookie, request.getLocalAddr());

                  if ((list.get(0) != "0") & (list.get(1) != "0")) {
                       HttpSession session = request.getSession(true);    //создаем сессию для пользователя с его данными!
                       session.setAttribute("sEmail", list.get(0));    //sEmail
                       session.setAttribute("sPassword", list.get(1));    //sPassword
                       // Отправляем ответ, что на сайт юзера можно пускать и отправляем куку, для обновления времени.
                       sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" +sCookie+ "\" }";
                  } else {
                       sReturn = "{\"sReturn\":\"" + "Ложная кука!" + "\"}";   //не менять
                  }
             }
              
               
//------------- ВХОД пользователя через ввод Емайла и пароля ---------------
             if ("theUserLogin".equals(sDO)) {

                  Access A = new Access();                  
                  if (A.bLoginExists(sEmail) == true) {        // true - Емаил существует в базе
                       String Pass = A.getPassword(sEmail);    // смотрим  Пароль по Емайлу
                       if (sPassword.equals(Pass)) {           // если Пароли совпадают
                            Date d = new Date();               // узнаем текущую дату
                            String sTimeLogin = df.format(d);
                            String sNID = A.getNID(sEmail);     // Получаем NID пользователя по его Емайлу
                            AccessAuth AA = new AccessAuth();
                            String sGenerate = AA.generateString();  // генерируем строку 50 символов для куки
                            String sCreateCookie = sNID + "&" + sGenerate;   // соединяем в одну строку (это Кука)
                            AA.saveCookieToDB(Integer.parseInt(sNID), sCreateCookie, sTimeLogin, 3); // сохраняем Куку в Базу
                            HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                            session.setAttribute("sEmail", sEmail);
                            session.setAttribute("sPassword", sPassword);
                                        // String[] sArrSession = {  // создаем строковый  массив с инф. о пользователе
                                        //   sEmail,
                                        //   session.getId(),
                                        //   sTimeLogin,
                                        //   "Подключен",
                                        //   request.getRemoteAddr(),
                                        //   request.getServerName()
                                        //   };
                                        //aAllSession.add(sArrSession); // переносим в Список Массивов для хранения
                            // Запись в базу инфы о пользователе при попытке его Входа
                            AccessOf.saveInfoWhenUserTryLogined(sEmail, request.getLocalAddr(), true); //  true доделать

                            // нельзя чтобы в json было пустое значение
                            sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" + sCreateCookie + "\" }"; //не менять
                       } else {     // неверный пароль
                            sReturn = "{\"sReturn\":\"" + "FailPassword!" + "\"}";
                       }
                  } else {   // несуществующий Логин // значит попытка регистрации //Регистрируем  
                       Access Aсс = new Access();
                       String s = Aсс.userRegistration(sEmail, sPassword);
                       if (s.equals("Добро пожаловать на сайт!")) {   // если регистрация удалась, то полдолжаем
                            HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                            session.setAttribute("sEmail", sEmail);
                            session.setAttribute("sPassword", sPassword);
                            Date d = new Date();          // узнаем текущую дату
                            String sTimeLogin = df.format(d);
                            String sNID = A.getNID(sEmail);      // Получаем NID пользователя по Емайлу
                            AccessAuth AA = new AccessAuth();
                            String sGenerate = AA.generateString();   // генерируем строку 50 символов для куки
                            String sCreateCookie = sNID + "&" + sGenerate;    // соединяем в одну куку
                            AA.saveCookieToDB(Integer.parseInt(sNID), sCreateCookie, sTimeLogin, 3);
                       }
                       sReturn = "{   \"sReturn\"  :  \"" + s + "\"    }";  // ответ в любом случае
                  }
             }


             
// ----------- Удаляем СЕССИЮ пользователя ------------------------------    
            if ("theDestroySession".equals(sDO)) {
                HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                session.invalidate();
                sReturn = "{\"sReturn\":\"" + "Destroyed!" + "\"}";      
            } 
            


//---------- Ограничение попыток неавторизированного пользователя делать запросы.
/*
 * Возможны пролемы если одновременно много пользователей будут входить на сайт
 * и обращатся к статичному массиву... при удалии одним пользоватлей записей, у другого пользователя
 * статичный массив изменится????? 
 * 
 */       
             if (request.getAttribute("sEmail") == null) {    // если у пользователя нет сессии
                  String sUserIP = request.getLocalAddr();        // узнаем IP входящего пользователя
                  // если массив строк пустой, то создаем хотябы одну запись, иначе с пустым массивом нельзя работать
                  if (AccessAuth.aUserCountTry.size() == 0) {
                       AccessAuth.aUserCountTry.add("0 2000.01.01 11:07:55 111.111.111.111");
                  }
                  for (int i = 0; i <= AccessAuth.aUserCountTry.size() - 1; i++) {   // Ищем IP пользователя (делающего запрос) в списке
                       //String s = AccessAuth.aUserCountTry.get(i);
                       if (AccessAuth.aUserCountTry.get(i).contains(sUserIP)) {   // если IP пользователя (делающего запрос) в списке есть то
                            int nCount = Integer.parseInt(AccessAuth.aUserCountTry.get(i).substring(0, 1)); // берем число его уже сделанных запросов
                            String sTimeExpiredOld = AccessAuth.aUserCountTry.get(i).substring(2, 22); // берем (строку) дату окончания срока действия записи
                             Date oTimeExpiredOld = df.parse(sTimeExpiredOld);      // (строку)дату окончания срока действия записи превращаем в обьект
                              Date d = new Date();            // узнаем текущее время
                              String sCurrentTime = df.format(d);
                              Date oTimeCurrent = df.parse(sCurrentTime);  

                            if (oTimeExpiredOld.getTime() > oTimeCurrent.getTime()) { // если время окончания больше текущего, то 
                                 //  проверяем сколько запросов сделано пользователем:
                                 if (nCount > 5) {
                                      sReturn = "{\"sReturn\":\"" + "FailLimitRequest!" + "\"}"; // сделано более 5 запросов в течении 2 минут, доступ заблокирован.
                                      return;
                                 }
                                 // обновляем счетчики
                                 AccessAuth.aUserCountTry.set(i, (nCount + 1) + " " + sTimeExpiredOld + " " + sUserIP); // счетчик + 1, дата старая, адрес старый
                                 return;

                            } else {
                                 // удаляем устаревшую запись
                                 AccessAuth.aUserCountTry.remove(i);
                            }

                       }

                  }
                  
                  Calendar cal = Calendar.getInstance();
                  cal.add(Calendar.MINUTE, +2);         // добавляем к текущему времени 2 минуты - это время "окончания" записи 
                  String sTimeExpired = df.format(cal.getTime());
                    // Если нету ИП то просто добавляем новую запись о пользователе: "текущее Число попыток", "Время окончания", "IP"
                    AccessAuth.aUserCountTry.add("1" + " " + sTimeExpired + " " + sUserIP);
             }



//------------- Получение списка сессий (старое)             
//            if ("theGetAllSessionList".equals(sDO)) {
//                String s = "";
//                for (String[] temp: aAllSession){     // //for (int i = 0; i < aAllSession.size(); i++) { }
//                  s = s+"<tr>"+  
//                          "<td>"+temp[0]+"</td>"+ 
//                          "<td>"+temp[1]+"</td>"+ 
//                          "<td>"+temp[2]+"</td>"+ 
//                          "<td>"+temp[3]+"</td>"+ 
//                          "<td>"+temp[4]+"</td>"+ 
//                          "<td>"+temp[5]+"</td>"+ 
//                          "</tr>";  // делаем табличные строки
//                }  // Добавляем шапку к таблице
//                    s = "<tr>  <td>E-Mail</td> <td>ID Session</td><td>Время входа</td><td>Статус</td><td>IP адрес</td><td>ServerName</td>  </tr>"+s;
//                    sReturn = "{\"sReturn\":\"" + s + "\"}";
//            } 

            
            
            
//==============================================================================================

         } catch (Exception _) {
              String sErr = _.getMessage();
              // System.err.println("--ERROR_CreateAccount:  " + sErr + " _ " + sReturn);  //это вывод в лог-файл
              sReturn = "{\"sReturn\":\"Error, ошибка в сервлете \"}" + sErr;
              oLog.info("sDO=" + sDO + ", sEmail=" + sEmail);
         }                               //throw new RuntimeException(_); раскомментировав эту строку можно прерывать выполнение класса при этой ошибке
         finally {                       //этот код выполнится даже если произойдет ошибка (иногда это очень важно, чтоб, например - закрыть соединение
              response.getWriter().write(sReturn);     // возвращаемые данные
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











//================== Свалка старого кода
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

                                                            // включаем таймер
                                                            //   TimerTask task = new RunMeTask(); 
                                                          //  timer.schedule(task, 100,1000);





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

    