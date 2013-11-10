  //Старая версия, не задействуется.
package zOld;

import business.model.Access;
import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
// пример настроки аннотации
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
public class CreateAccount extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String sReturn_Account = "",
                sDO_Account = "",
                sEmail_Account = "",
                sPassword_Account = "",
                sPassword2_Account = "",
                sLastName_Account = "",
                sFirstName_Account = "";

        try {

            sDO_Account = request.getParameter("sDO_Account"); //вытягиваем параметры
            sEmail_Account = request.getParameter("sEmail_Account");
            sPassword_Account = request.getParameter("sPassword_Account");
            sPassword2_Account = request.getParameter("sPassword2_Account");
            sLastName_Account = request.getParameter("sLastName_Account");
            sFirstName_Account = request.getParameter("sFirstName_Account");


//            http://localhost:8080/Business/CreateAccount?sDO=theCreateAccount&sEmail=ser412@d3f.dd&sPassword=12&sPassword2=12&sLastName=ser1&sFirstName=bel1

            if ("theCreateAccount".equals(sDO_Account)) {

                Access oAccess = new Access();
                String s = oAccess.sUserRegistration(sEmail_Account, sPassword_Account);
                sReturn_Account = "{\"sReturn_Account\":\"" + s + "\"}";

            }

//            if ("theCreateAccount".equals(sDO)) {
//                String s = "Serg! sadf sdf asd fasd asdf asd asdf asdf asdf asd";
//                sReturn = s;
//            }


//======================================================================

        } catch (Exception _) {
            String sErr = _.getMessage();
            System.err.println("--ERROR_CreateAccount:  " + sErr + " _ " + sReturn_Account);  //это вывод в лог-файл
            sReturn_Account = "{\"sReturn_Account\":\"Error, ошибка в сервлете \"}" + sErr;

        } //throw new RuntimeException(_); раскомментировав эту строку можно прерывать выполнение класса при этой ошибке
        finally {                    //этот код выполнится даже если произойдет ошибка (иногда это очень важно, чтоб, например - закрыть соединение
            //  response.getWriter().write(sReturn);
            response.getWriter().write(sReturn_Account); // возвращаемые данные

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