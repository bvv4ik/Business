<%-- 
    Document   : bw_2013-10-22
    Created on : 22.10.2013, 1:27:08
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/BW_2013-10-22.css"/> 
        <script type="text/javascript" src="js/jquery-1.8.3.js"> </script>  
        
        <script type="text/javascript" src="js/bw_2013-10-22.js"> </script> 
        
        
    </head>
    <body>
        <h1>Hello World!</h1>
        
        
          <script> 
                    //$.cookie("auth", 111, { expires: 2,  path: '/'    });
                    $(document).ready(function(){
                        
                        //oDialog oAsk
                        //showDialog($(".oDialog.oAsk"),"yes","ssss",null);
                        //askTest();
                           // sBody,sHead,aButttons,oReturn,bSkip,nWidth,nHeight)
                        ask("привет","yes",null,1);
                       // askTest();
                        //seeError("Внимание!","ывsDebug","incomplete");
                    });
               </script>
        
    <div class="oDialog_Background" style="display: none"></div>
        
    <div class="oDialog oAsk" style="display: none">
        <img class="doHideDialog"/>
        <div class="oHead">Вопрос:</div>
        <div class="oBody">
            <p>Какой ответ на вопрос?</p>
        </div>
        </br>
        <div class="aButton">
            <center>
                <input class="oButton default" type="button" value="Ok" onclick="" style="margin-top: 7px;margin-left: 10px;margin-right: 7px;"/>
            </center>
        </div>
    </div>
        
    
    <div hidden class="oDialog oError" >    
        <img class="doHideDialog"/>
        <div class="oHead">Ошибка:</div>
        <div class="oBody">
            <p>Произошла ошибка!</p>
        </div>
        </br>
        <!-- <a href="#" class="bDebug" onclick="
            var o=$(this).closest('div').find('.oDebug');
            $(o).toggle();
            $(this).text((see($(o))?'Скрыть':'Показать')+' подробности');
        ">Показать подробности</a>   -->
        <div hidden class="oDebug" >  <!--  style="display: none" -->
          <!--  (нет технических подробностей) -->
        </div>
        </br>
        <div class="oButton">
              <!--  <a class="oButtonClose" href="#">Скрыть</a> -->
             <input class="oButton default" type="button" value="Ok" onclick="" style="margin-top: 7px;margin-left: 10px;margin-right: 7px;"/>
            <button></button>
        </div>
    </div>
    
    
    </body>
</html>
