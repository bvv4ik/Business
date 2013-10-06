<%-- 
    Document   : index
    Created on : Oct 16, 2012, 2:12:15 PM
    Author     : Ser
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


 
<%


      
  //session.setAttribute("sLogin","Vasiliy");
  //session.setAttribute("sLogin","text1");
  
 // session.removeAttribute("sLogin");
 // session.removeAttribute("sLogin1");
  
  //session.invalidate();
  
 // if ((session.getAttribute("sLogin")) != ""){
 // String value = session.getAttribute("sLogin").toString(); }
 //String sLogin = session.getAttribute("sLogin1").toString();

  //if (!session.equals(null){
  Object oLogin = session.getAttribute("sLogin");
 // Object oFirstName = session.getAttribute("sFirstName");
  Object oLastName = session.getAttribute("sLastName");
 // Object oSureName = session.getAttribute("sSureName");
  // }
  //if (oLogin == null)
  
  //Object oIP = session.getId(); 
  //request.getRemoteAddr();
  //request.getRemoteUser();
  
  //String value = session.getAttribute("sLogin").toString();
  //String value1 = session.getAttribute("sLogin1").toString();
  
  //String sLogin = session.getAttribute("sLogin");
               
        
           // "belya
            //параметр можно брать из сессии и/или базы (session.getAttribute(‘sLogin’);)
%>
  



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
        <script type="text/javascript" src="js/ajax1.js"> </script>
             <link rel="stylesheet" type="text/css" href="css/my_style_1.css"/>
             
        <title> Главная страница </title>
    </head>
    <body>
        
        <h1>
        
        
           <% 
      
         if (oLogin == null)
        //   if ("".equals(oLogin))
               
                          
       //  if("222".equals(oLogin))
       //  <h1> < %=oLogin% > </h1> 
       //  <h1> < %=value1% > </h1> 
       //  if (value.isEmpty()) 
       // if(oLogin.equals("Vasiliy"))
       // if(value.toString().isEmpty())
       { %> 
                                                         
                
         НЕверный логин!
         <script>
         alert("Вы не авторизированы, авторизируйтесь пожалуйста!");
         location.href="/index.jsp";
         </script>        
         
         
              
        <% } else 
        
        { %>    
          
         
        <h1>
            Добро пожаловать < %=oLogin%>  <%=oLastName%> < %=oFirstName%> < %=oSureName%>    </h1>
        
        
        
         
        <% }


            %>
        
          </h1>
            
        
       <div id="s3">  <input id="cmdExit" type="button" value="Выход"  />  </div> 
       
      
      
        
        
        
        
        
    </body>
</html>

