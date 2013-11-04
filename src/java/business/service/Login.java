package business.service;

import business.Config;
import business.auth.AccessAuth;
import business.auth.Access;
import business.auth.AccessOf;
import business.send.MailText;
import com.bw.io._;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        int sLimitRequest = 10;

        String sReturn = "{\"sReturn\":\"" + "-none-" + "\"}", // чтобы не возвращался NULL и небыло ошибки                  
                sDO = "",
                sEmail = "",
                sPassword = "",
                sCookie = "";

        try {

            sDO = request.getParameter("sDO");         //вытягиваем параметры
            sEmail = request.getParameter("sEmail");
            sPassword = request.getParameter("sPassword");
            sCookie = request.getParameter("sCookieLogin");

            // oLog.info(" sDO= " + sDO + ", sEmail= " + sEmail);
            oLog.info(" sDO=" + sDO + ", sEmail=" + sEmail + ", sPassword=" + sPassword + ", sCookie=" + sCookie);   // .substring(0, 3)+"..." 


            //   Access oAccess1 = new Access();
            //String sReturn1 =
            new Access()
                    ._Password("123123")
                    ._Login("sss@dddd.ddd")
                    //._nDisabled(1)
                    .save("sdddd@dfdd.gg");


            //AccessREST oAccessREST = new AccessREST();
            //String sReturn1 = new Access()


            // oAccess1.save("sdddd@dfdd.gg");


//---------- Ограничение попыток неавторизированного пользователя делать запросы.
 /*   Должно находится в самом начале сервлета!
             *    Создается статический HashMap на сервере и при каждом запросе неавторизированного пользователя
             *    в эту таблицу записываются: "IP пользователя"     и     "число попыток" + "Дата и время"
             *    если в течении 2 минут пользователь привысит число запросов (5 шт), то остальные запросы будут игнорироватся  в течении 2 минут. 
             */

            if (request.getAttribute("sEmail") == null) {    // если у пользователя нет сессии
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String sUserIP = request.getLocalAddr();        // узнаем IP входящего пользователя
                String sIP = AccessAuth.map.get(sUserIP);       // ищем его IP в массиве
                if (sIP != null) {       // если IP пользователя (делающего запрос) есть в списке, то:
                    int nCount = Integer.parseInt(sIP.substring(0, 2));    // берем число его уже сделанных запросов
                    String sTimeExpiredOld = sIP.substring(3, 22);         // берем (строку) дату окончания срока действия записи
                    Date oTimeExpiredOld = df.parse(sTimeExpiredOld);      // (строку)дату окончания срока действия записи превращаем в обьект
                    Date d = new Date();                                   // узнаем текущее время
                    String sCurrentTime = df.format(d);
                    Date oTimeCurrent = df.parse(sCurrentTime);
                    if (oTimeExpiredOld.getTime() > oTimeCurrent.getTime()) {   // если время окончания больше текущего, то 
                        if (nCount <= 0) {                                       //  проверяем сколько запросов сделано пользователем: мах 99.
                            sLimitRequest = 0;
                            sReturn = "{\"sReturn\":\"" + "FailLimitRequest!" + "\"}";   // сделано более позволенного запросов в течении 2 минут...
                            return;        // вываливаемся из сервлета и ничего больше не обрабатываем
                        }   // обновляем счетчик, а время истечения срока оставляем старое
                        AccessAuth.map.put(sUserIP, String.format("%02d", (nCount - 1)) + " " + sTimeExpiredOld);
                        sLimitRequest = nCount - 1;
                    } else {                  // удаляем устаревшую запись // обновляем
                        // AccessAuth.map.remove(sUserIP);
                        // дата истекла, добавляем 1-ю запись о пользователе 
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MINUTE, +2);         // добавляем к текущему времени 2 минуты - это время "окончания" записи.
                        String sTimeExpired = df.format(cal.getTime());
                        AccessAuth.map.put(sUserIP, "10" + " " + sTimeExpired);
                    }

                } else {     //  если IP нет в списке, добавляем 1-ю запись о пользователе 
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MINUTE, +2);         // добавляем к текущему времени 2 минуты - это время "окончания" записи.
                    String sTimeExpired = df.format(cal.getTime());
                    AccessAuth.map.put(sUserIP, "10" + " " + sTimeExpired);
                }
            }




//------------- Отправка на Емаил пользователя ссылки для "входа без пароля" ---------------
            if ("theSendEmail".equals(sDO)) {
                Access oAccess = new Access();
                if (oAccess.bLoginExists(sEmail) == true) {       // true - Емаил существует в базе
                    AccessAuth oAccessAuth = new AccessAuth();
                    String sCookieDB = oAccessAuth.sFindCookie(sEmail);  // берем куку пользователя (самую старую)  // еще нужно будет сделать, генерацию и добавление куки в базу при нажатии на "отправить ссылку на почту"
                    MailText mt = new MailText();   // http://localhost:8080/#sDO=theLoginForCookie&sCookieLogin=31%26eofrrqpcrgkshspqxmkserqihewgaxqeazdrfjmgfuqunpkanu
                    mt.sendMail(sEmail, "Ваша ссылка для входа в PGASA без пароля:  \n\n  " + Config.sValue("sURL") + "/#sAuth=" + sCookieDB + "  \n\n ");
                    sReturn = "{\"sReturn\":\"" + "MailSendOk!" + "\"}";
                } else {
                    sReturn = "{\"sReturn\":\"" + "MailSendFail!" + "\"}";
                }
            }


//------------- проверка существования Емайла---------------
            if ("theUserExists".equals(sDO)) {
                Access oAccess = new Access();
                if (oAccess.bLoginExists(sEmail) == true) {    // true - Емаил существует в базе
                    sReturn = "{\"sReturn\":\"" + "EmailExists" + "\"}";
                } else {
                    sReturn = "{\"sReturn\":\"" + "NoEmailExists" + "\"}";
                }    //Thread.sleep(2000); // задержка отправки ответа на 2 сек.
            }


//------------- ВХОД пользователя через куку ---------------
            if ("theLoginForCookie".equals(sDO)) {
                AccessAuth oAccessAuth = new AccessAuth();
                ArrayList<String> list = new ArrayList<String>();
                list = oAccessAuth.aFindUserFromCookie(sCookie, request.getLocalAddr());
                if ((list.get(0) != "0") & (list.get(1) != "0")) {
                    HttpSession session = request.getSession(true);    //создаем сессию для пользователя с его данными!
                    session.setAttribute("sEmail", list.get(0));       //sEmail
                    session.setAttribute("sPassword", list.get(1));    //sPassword
                    // Отправляем ответ, что на сайт юзера можно пускать и отправляем куку, для обновления времени.
                    sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" + sCookie + "\" }";
                } else {
                    sReturn = "{\"sReturn\":\"" + "Ложная кука!" + "\"}";   //не менять
                }
            }



//------------- ВХОД пользователя через ввод Емайла и пароля ---------------
            if ("theUserLogin".equals(sDO)) {
                String sCase = "AJAX  (theUserLogin)";  // Для Лога
                String sCreateCookie = "";  // поправить, иногда создается пустая Кука?????

                Access oAccess = new Access(sEmail);
                if (oAccess.nDisabled() == 1) {  // проверяем если пользователь заблокирован
                    // Запись в базу инфы о пользователе при попытке его Входа
                    AccessOf oAccessOf = new AccessOf();
                    oAccessOf.saveInfo(sEmail, request.getLocalAddr(), 0);
                    sReturn = "{\"sReturn\":\"" + "Доступ заблокирован Администрацией!" + "\"}";   //не менять
                    return;
                }

                if (oAccess.bLoginExists(sEmail) == true) {        // true - Емаил существует в базе
                    String sPasswordDB = oAccess.sGetPassword(sEmail);    // смотрим  Пароль по Емайлу
                    if (sPassword.equals(sPasswordDB)) {           // если Пароли совпадают

                        HttpSession session = request.getSession(true);
                        sCreateCookie = oAccess.sAfretRegister(sEmail, sPassword, session, request.getLocalAddr()); // пускаем на сайт

                        sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" + sCreateCookie + "\" }"; //не менять  // нельзя чтобы в json было пустое значение
                    } else {     // неверный пароль
                        sReturn = "{\"sReturn\":\"" + "FailPassword!" + "\"}";
                    }
                } else {   // несуществующий Логин // значит попытка регистрации //Регистрируем  и пускаем на сайт

                    oAccess = new Access();
                    String s = oAccess.sUserRegistration(sEmail, sPassword);
                    if (s.equals("Добро пожаловать на сайт!")) {   // если регистрация удалась, то полдолжаем


                        //AccessREST oAccessREST = new AccessREST();
                           /*String sReturn1 = new AccessREST()
                         ._URL(sPassword)
                         ._Timeout(sLimitRequest)
                         ._Param("name1", "value1")
                         ._Param("name2", "value2")
                         ._Param("name3", "value3")
                         ._Param("name4", "value4")
                         .sRequest();
                         ;*/

                        HttpSession session = request.getSession(true);
                        sCreateCookie = oAccess.sAfretRegister(sEmail, sPassword, session, request.getLocalAddr()); // пускаем на сайт

                        sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\", \"sReturnCookie\"  : \"" + sCreateCookie + "\" }"; //не менять
                    }
                    sReturn = "{   \"sReturn\"  :  \"" + s + "\"    }";     // ответ в любом случае
                }
            }



// ----------- Удаляем СЕССИЮ пользователя ------------------------------    
            if ("theDestroySession".equals(sDO)) {
                HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                session.invalidate();
                sReturn = "{\"sReturn\":\"" + "Destroyed!" + "\"}";
            }




//==============================================================================================

        } catch (Exception _) {
            String sErr = _.getMessage();
            // System.err.println("--ERROR_CreateAccount:  " + sErr + " _ " + sReturn);  //это вывод в лог-файл
            sReturn = "{\"sReturn\":\"Error, ошибка в сервлете '/Login' \"}" + sErr;
            //oLog.info("sDO= " + sDO + ", sEmail= " + sEmail+ ", sPassword= " + sPassword.substring(0, 3)+"..." + ", sCookie= " + sCookie); 
            oLog.info("sDO= " + sDO + ", sEmail= " + sEmail + ", sCookie= " + sCookie);
        } //throw new RuntimeException(_); раскомментировав эту строку можно прерывать выполнение класса при этой ошибке
        finally {                       //этот код выполнится даже если произойдет ошибка (иногда это очень важно, чтоб, например - закрыть соединение
            sReturn = _.ConcatJson(sReturn, "{\"sLimitRequest\":\"" + sLimitRequest + "\"}");
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
// String[] sArrSession = {  // создаем строковый  массив с инф. о пользователе
//   sEmail,
//   session.getId(),
//   sTimeLogin,
//   "Подключен",
//   request.getRemoteAddr(),
//   request.getServerName()
//   };
// aAllSession.add(sArrSession); // переносим в Список Массивов для хранения
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
//---------------------------------------------               
   /*          if (request.getAttribute("sEmail") == null) {    // если у пользователя нет сессии
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
 Date d = new Date();              // узнаем текущее время
 String sCurrentTime = df.format(d);
 Date oTimeCurrent = df.parse(sCurrentTime);  

 if (oTimeExpiredOld.getTime() > oTimeCurrent.getTime()) { // если время окончания больше текущего, то 
 //  проверяем сколько запросов сделано пользователем:
 if (nCount > 5) {
 sReturn = "{\"sReturn\":\"" + "FailLimitRequest!" + "\"}"; // сделано более 5 запросов в течении 2 минут, значит доступ заблокирован.
 return;  // вываливаемся из сервлета
 }
 // обновляем счетчики
 AccessAuth.aUserCountTry.set(i, (nCount + 1) + " " + sTimeExpiredOld + " " + sUserIP); // счетчик + 1, дата старая, адрес старый
 //return; 

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
               
 */
//                         try {
//                         // Выборка всех записей сессии    
//                        Enumeration keys = session.getAttributeNames();
//                        while (keys.hasMoreElements())
//                            {
//                            String key = (String)keys.nextElement();
//                            sSess = sSess + (key + "_" + session.getValue(key) + "");
//                            }
//                        
//                             } catch (Exception oException) {
//                                String sErr = oException.getMessage();
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

