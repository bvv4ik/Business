//@Servlet(urlMappings={"/MyApp"})
//@WebServlet(name="mytest", urlPatterns={"/myurl"}) 
//(name="mytest", urlPatterns={"/myurl"}) ;
package com.bw.service;

import com.bw.entity.*;
import com.bw.io.ConnectLdap;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
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

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String              sReturn = "",
                            sDO = "" ,
                                sEmail = "",
                                sPassword = "",
                               sSess = "";
           
        try {

            sDO = request.getParameter("sDO"); //вытягиваем параметры
               sEmail = request.getParameter("sEmail");
               sPassword = request.getParameter("sPassword");
     
            
            
    //------------- ВХОД пользователя ---------------
            if ("theUserLogin".equals(sDO)) {
                
                String Pass = "";
                
                Access A = new Access();
                if (A.bLoginExists(sEmail) == true) { // true - Емаил существует

                    Pass = A.getPassword(sEmail);   // смотрим  Пароль по Емайлу
                    if (sPassword.equals(Pass))      // если Пароли совпадают
                    {
                        HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                        session.setAttribute("sEmail", sEmail);
                        session.setAttribute("sPassword", sPassword);
                        
                        
                        // Запись инфы в базу о пользователе при Входе пользователя
                         AccessOf.saveInfoWhenUserLogined(sEmail);
     //==================
                        Enumeration keys = session.getAttributeNames();
                        while (keys.hasMoreElements())
                            {
                            String key = (String)keys.nextElement();
                            sSess = sSess + (key + "- " + session.getValue(key) + "");
                            
                            }
     //==================                   
                        sReturn = "{  \"sReturn\"  :  \"Добро пожаловать на сайт!\",  \"sSes\"  :  \""+sSess+"\" }";
                        //sReturn = "{\"sReturn\":\"" + "Добро пожаловать на сайт!" + "\"}";
                    } else {
                            sReturn = "{\"sReturn\":\"" + "Неверный Пароль или Логин!" + "\"}";
                            }

                    }
                else{
                 sReturn = "{\"sReturn\":\"" + "Неверный Пароль или Логин!" + "\"}";
                 }
         
            }

            if ("theDestroySession".equals(sDO)) {
                HttpSession session = request.getSession(true);    //создаем сессию для пользователя
                session.invalidate();
                sReturn = "{\"sReturn\":\"" + "Сессия удалена!" + "\"}";
                
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