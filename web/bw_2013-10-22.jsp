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
        <script type="text/javascript" src="js/bw_2013-10-22.js"> </script> 
        
    </head>
    <body>
        <h1>Hello World!</h1>
        
        
          <script> 
                    //$.cookie("auth", 111, { expires: 2,  path: '/'    });
                    $(document).ready(function(){
                        
                        
                        showDialog($(".oDialog_Background"));
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
        
    
    <div class="oDialog oError" style="display: none">
        <img class="doHideDialog"/>
        <div class="oHead">Ошибка:</div>
        <div class="oBody">
            <p>Произошла ошибка!</p>
        </div>
        </br>
        <a href="#" class="bDebug" onclick="
            var o=$(this).closest('div').find('.oDebug');
            $(o).toggle();
            $(this).text((see($(o))?'Скрыть':'Показать')+' подробности');
        ">Показать подробности</a>
        <div class="oDebug" style="display: none">
            (нет технических подробностей)
        </div>
        </br>
        <div class="aButton">
            <a class="oButtonClose" href="#">Скрыть</a>
        </div>
    </div>
    
    
    </body>
</html>
