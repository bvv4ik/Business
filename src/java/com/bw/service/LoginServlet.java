//@Servlet(urlMappings={"/MyApp"})
//@WebServlet(name="mytest", urlPatterns={"/myurl"}) 


package com.bw.service;

   
//import  com.bw.entity.TheSubjectHuman;


import  com.bw.entity.*;

//import zLogic.Registration1;
import com.bw.io.ConnectLdap;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
import java.util.Enumeration;

import javax.servlet.ServletContext;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.*;
//import javax.servlet.*;

//import com.bw.entity.Access;
//import com.bw.entity.AccessOf;
@WebServlet(name = "Login", urlPatterns = {"/LoginServlet"})

//@Servlet(urlMappings={"/MyApp"})

public class LoginServlet extends HttpServlet{


//public void Boolean (String Str){
//    if Str.equals(Str) ""{}
//    }  
   
    
protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

    
response.setContentType("text/html;charset=utf-8"); 
request.setCharacterEncoding("utf-8");

String   test ="",       
        sFind ="",
               sReturnLogin="",       
                  sDO="",
                  nID_Country="",
                  nID_RegionType="",
                  nID_Region="",
                 
                  nID_PolisType="",
                  nID_Polis="",
                  nID_Area="",
                  nID_BranchType="",
                  nID_Branch="",
                nID_BuildType="",
                nID_Build="",
                nID_CellType="",
                nID_Cell="",
                nID_Part="",
        
                answer="",
         
        
        
                  sLoginEnter="",
                  sPasswordEnter="",
               // sLoginVerify="",
                    sLoginEmail = "",
                    sPasswordReg1  = "",
                    sPasswordReg2  = "";
                               
              //sEmailReg  = "";           
             // PrintWriter pw = response.getWriter();
   
      
// ServletContext context = this.getServletContext();
  //    context.setAttribute("HELLO.WORLD", "Hello World 123");
    
   

  
     
      
try{
   
    
    

      
      
  //  String clientIP = request.getRemoteAddr();
    
    test=request.getParameter("test");
    sFind=request.getParameter("sFind");
    
    sDO=request.getParameter("sDO"); //аналогично остальные параметры вытянуть
    
    nID_Country=request.getParameter("nID_Country");
    
    nID_Region=request.getParameter("nID_Region");
    nID_RegionType=request.getParameter("nID_RegionType");
    
    answer=request.getParameter("answer");

    
    sLoginEnter=request.getParameter("sLogin"); 
    sPasswordEnter=request.getParameter("sPassword"); 

   // sLoginVerify=request.getParameter("sLoginVerify"); 
    
    sLoginEmail = request.getParameter("sLoginEmail"); 
    sPasswordReg1 =request.getParameter("sPasswordReg1"); 
    sPasswordReg2 =request.getParameter("sPasswordReg2"); 
    
//    sCountry =request.getParameter("sCountry"); 
//    sRegion =request.getParameter("sRegion"); 
//    sPolis =request.getParameter("sPolis"); 
//    sArea =request.getParameter("sArea"); 
//    
//    sBranchType =request.getParameter("sBranchType");
//    sBranch =request.getParameter("sBranch");
//    
//    sBuildType =request.getParameter("sBuildType");
//    sBuild =request.getParameter("sBuild");
//    
//    sPart =request.getParameter("sPart");
//    sCellType =request.getParameter("sCellType");
//    sCell =request.getParameter("sCell");
    
    
  //  sEmailReg  =request.getParameter("sEmailReg"); 
   
    //Access oAccess = new Access()._bDisabled(1)._sLogin("MyLogin")._sPassword("MyPassword").save();
       
//======================================================================
   
   if("DeleteSession".equals(sDO)) {
   HttpSession session = request.getSession(true);    
   session.invalidate();
   sReturnLogin="{\"sReturnLogin\":\"Сессия удалена\"}";
  // return;
   }

//======================================================================
   
   if ("RegisteredUser".equals(sDO))   {
      Access A = new Access();
      String s = A.userRegistration(sLoginEmail, sPasswordReg1);
           
      sReturnLogin = "{\"sReturnLogin\":\""+s+"\"}"; 
      }    
   //  sReturnLogin = "{\"sReturnLogin\":\"Учетная запись успешно создана!\"}"; 
   //   System.out.println(s); 
   
//======================================================================

  if ("UserLogining".equals(sDO)) {

      // Ищем в Лдапе пароль по Логину...
      if (!ConnectLdap.isLoginExists(sLoginEnter)) {
          sReturnLogin = "{\"sReturnLogin\":\"Неверный Логин\"}";
      }

      // если логин есть то берем пароль
      String s = ConnectLdap.sGetPassword(sLoginEnter);

      if (("".equals(s))) {
          sReturnLogin = "{\"sReturnLogin\":\"Ошибка подключения к Лдап\"}";
      }

  // если введеный Логин-Пароль и Логин-Пароль из Лдапа совпадают
    if ((s).equals("description: "+sPasswordEnter))
    {    
        HttpSession session = request.getSession(true);    
        session.setAttribute("sLogin", sLoginEnter);
        
       // TheSubjectHuman s = new TheSubjectHuman();
        
        s = TheSubjectHuman.getLastName(sLoginEnter);
        session.setAttribute("sLastName",s);
        
        AccessOf AO = new AccessOf();
      //  AO.save(sLoginEnter);
        // Сохраняем в историю момент посещения
        
        /*
        s = Authorization.getFirstLastSureName("sFirstName", sLoginEnter);
        session.setAttribute("sFirstName",s);
        
        s = Authorization.getFirstLastSureName("sLastName", sLoginEnter);
        session.setAttribute("sLastName",s);
        
        s =  Authorization.getFirstLastSureName("sSureName", sLoginEnter);              
        session.setAttribute("sSureName",s); */
       
        sReturnLogin="{\"sReturnLogin\":\" 1 Логин  есть в базе! \"}";
    }else {
           sReturnLogin="{\"sReturnLogin\":\"Неверный логин или пароль!\"}";
           }
 }
     
   
  if ("addCountry".equals(sDO)) {         //Получаем список типов Стран
      PlaceCountry PC = new PlaceCountry();   
      PC.addCountry(answer);
      sReturnLogin = "{\"sReturnLogin\":\"AddCountry\"  }";
     
      }
  
  if ("getAllCountry".equals(sDO)) {         //Получаем список типов Стран
      PlaceCountry PC = new PlaceCountry();   
      sReturnLogin = "{\"sReturnLogin\":\"Country\""+ PC.getAllCountry() +" }"; 
      
    //  String s = clientIP;
   
      
      }
    
  if ("getAllRegionType".equals(sDO)) {       //Получаем список типов Регионов
       PlaceRegionType PRT = new PlaceRegionType();  
       sReturnLogin = "{\"sReturnLogin\":\"RegionType\""+ PRT.getAllRegionType() +" }"; 
     }
    
  if ("getAllRegions".equals(sDO)) {  //Получаем список регионов по ID страны и ID Типа региона
      PlaceRegion PR = new PlaceRegion();
      sReturnLogin = "{\"sReturnLogin\":\"Region\""+ PR.getAllRegions(nID_Country, nID_RegionType) +" }"; 
      }

    if ("getAllPolisType".equals(sDO)) {
        PlacePolisType PPT = new PlacePolisType();   //Получаем список типов Городов
        sReturnLogin = "{\"sReturnLogin\":\"PolisType\"" + PPT.getAllPolisType() + " }";
    }

    
    if ("getAllPolis".equals(sDO)) {
        PlacePolis PP = new PlacePolis(); //Получаем список Городов
        sReturnLogin = "{\"sReturnLogin\":\"Polis\"" + PP.getAllPolis(nID_Region, nID_PolisType) + " }";
    }

    if ("getAllAreas".equals(sDO)) {
        PlaceArea PA = new PlaceArea(); //Получаем список Районов городов
        sReturnLogin = "{\"sReturnLogin\":\"Area\"" + PA.getAllAreas(nID_Polis) + " }";
    }

    if ("getAllBranchType".equals(sDO)) {
        PlaceBranchType PBT = new PlaceBranchType(); //Получаем список типов Улиц
        sReturnLogin = "{\"sReturnLogin\":\"BranchType\"" + PBT.getAllBranchType() + " }";
    }
     
     
        
     
     
     
     
     
     if ("getAllBranch".equals(sDO)) {
        PlaceBranch PB = new PlaceBranch();              //получаем список улиц
        sReturnLogin = "{\"sReturnLogin\":\"Branch\""+ PB.getAllBranch(nID_Polis, nID_BranchType) +" }";
        }
   

     if ("getAllBuildType".equals(sDO)) {
         PlaceBuildType PBT = new PlaceBuildType();
         String s =  PBT.getAllBuildType() ;
         sReturnLogin = "{\"sReturnLogin\":\"BuildType\""+ s +" }";
        }
        
        
        if ("getAllBuild".equals(sDO)) {
                
         //получаем список всех зданий 
        PlaceBuild PB1 = new PlaceBuild();
        String s = "";
        //if (nID_PlaceBuildType == 0)
        s =  PB1.getAllBuild(nID_Branch, nID_BuildType, nID_Area) ;
        //else
        //s =  PB1.getAllBuild(nID_PlaceBranch, nID_PlaceBuildType) ;

        sReturnLogin = "{\"sReturnLogin\":\"Build\""+ s +" }";
       }
        
    if ("getAllPart".equals(sDO)) {
        PlacePart PP = new PlacePart();           //получаем список Подъездов
        String s =  PP.getAllPart(nID_Build) ;       
        sReturnLogin = "{\"sReturnLogin\":\"Part\""+ s +" }";
        }
    
     if ("getAllCellType".equals(sDO)) {
         PlaceCellType PCT = new PlaceCellType();   //Получаем полный список частей строения     
         String s =  PCT.getAllCellType() ;
         sReturnLogin = "{\"sReturnLogin\":\"CellType\""+ s +" }";
        }
     
    if ("getAllCell".equals(sDO)) {        
         PlaceCell PC = new PlaceCell();        //Получаем полный список квартир  
         String s =  PC.getAllCell(nID_Part) ;                
         sReturnLogin = "{\"sReturnLogin\":\"Cell\""+ s +" }";
        }
   
    
    
    //=========================================
    if ("AddBuild".equals(sDO)) {
              
        PlaceBuild PB = new PlaceBuild();
        PB.AddBuild(nID_Branch, nID_BuildType, nID_Area, 8,7,"78","4",1,77);
        }

        if ("Save".equals(sDO)) {               
        Place P = new Place();
        P.save(nID_Cell, nID_Part, nID_Build);
        }
    
    if ("test".equals(sDO))   {   
       sReturnLogin = " [{ \"nID_Region\":\"34\" ,\"sRegion\":\"Днепропетровская\",\"value\":\"Днепропетровская обл.\" }, { \"nID_Region\":38 ,\"value\":\"Киевская обл. Район Мироновский\"} ] "; 
    }
        
   if ("getPolis".equals(sDO))   {   
     //sReturnLogin = " [{ \"nID_Region\":\"34\" ,\"sRegion\":\"Днепропетровская\",\"value\":\"Днепропетровская обл.\" }, { \"nID_Region\":38 ,\"value\":\"Киевская обл. Район Мироновский\"} ] ";
    // sReturnLogin = " [{ \"nID_Polis\":\"34\"  ,  \"value\":\"Днепропетровская обл.\" }, { \"nID_Polis\":38  ,  \"value\":\"Киевская обл. Район Мироновский\"} ] ";

        PlacePolis pp = new PlacePolis();
        pp.getStringAddressPolis(sFind/*"дніпр"*/, 1/*здесь вставить переменную*/);
   //sReturnLogin = "[{ \"nID_Polis\": 5128  ,  \"value\":\"   обл. ДНІПРОПЕТРОВСЬКА  м. ДНІПРОДЗЕРЖИНСЬК \"} ,{ \"nID_Polis\": 5115  ,  \"value\":\"   обл. ДНІПРОПЕТРОВСЬКА  м. ДНІПРОПЕТРОВСЬК \"} ]";        
   //for (String temp: pp.aResult2){ System.out.println(temp); }
   //for (String temp: pp.aResult3){ System.out.println(temp); }
 
       sReturnLogin = pp.sObject;
       return;
      // String s = sReturnLogin;
     //"row":102,

     
     /*sReturnLogin = "[{
 "nID_Region":34
,"sRegion":"Днепропетровская"
,"sRegionType":"обл."
,"value":"Днепропетровская обл."

},

{
 "nID_Region":38
,"sRegionName":"Киевская"
,"sRegionType":"обл."
,"value":"Киевская обл. Район Мироновский"}]";
   */
   }
   
   
   if ("LoginVerify".equals(sDO))   {      }
//======================================================================
    
}

catch(Exception _){
                    String sErr=_.getMessage();
                    System.err.println("--ERROR:  "+sErr+" _ "+sReturnLogin);//это вывод в лог-файл
                    //sReturnLogin="{\"sReturnLogin\":\"Error+++++++\",\"sDO\":\""+sDO+"\"}" ; 
                    sReturnLogin="{\"sReturnLogin\":\"Error, ошибка в сервлете \"}" + sErr ; 
                    
                    
                    //throw new RuntimeException(_);
                  }
                            //throw new RuntimeException(_); раскомментировав эту строку можно прерывать выполнение класса при этой ошибке
finally{                   //этот код выполнится даже если произойдет ошибка (иногда это очень важно, чтоб, например - закрыть соединение
                           //  response.getWriter().write(sReturn);
            response.getWriter().write(sReturnLogin);  
             
        
            
       }


}


protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);

    

}
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {processRequest(request,response);}

public String getServletInfo() {return "Short description";}
}