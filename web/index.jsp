<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%               //session.setAttribute("sLogin","Vasiliy");
                 //session.setAttribute("sLogin","text1");
                 // session.removeAttribute("sLogin");
                 //session.invalidate();
 // if ((session.getAttribute("sLogin")) != ""){
 // String value = session.getAttribute("sLogin").toString(); }
 //String sLogin = session.getAttribute("sLogin1").toString();
 //if (!session.equals(null){
// При загрузке страницы на сервере проверяем есть ли в сессии запись "sEmail"
  Object oEmail= session.getAttribute("sEmail");  
  Object oFirstName = session.getAttribute("sFirstName");
  Object oLastName = session.getAttribute("sLastName");
  Object oSureName = session.getAttribute("sSureName");
  Object oIP = request.getServerName(); //getRemoteUser();//
  //Object oIP = session.getId();       //request.getRemoteAddr();      //request.getRemoteUser(); 
  //String sLogin = session.getAttribute("sLogin");       
%>
<!DOCTYPE html>
<html>
    <head>
         <title> Главная </title>         
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         
         <link rel="stylesheet" type="text/css" href="css/reset.css"/> <!--  сброс браузерных стилей -->
                                                                                            <!--  <script type="text/javascript" src="---js/jquery-1.7.2.js"></script>       <script type="text/javascript" src="---js/ajax1.js"> </script>          <!--  < link rel="stylesheet" type="text/css" href="css/my_style_1.css"/>  -->
        <script type="text/javascript" src="js/jquery-1.8.3.js"> </script>  <!--  Библ. jquery должна быть первой   -->
        <script type="text/javascript" src="js/jquery-ui.js"> </script>        
        <script type="text/javascript" src="js/index.js"> </script>   <!--  мои скрипты  -->

        <!--  <script src="http://code.jquery.com/jquery-1.9.1.js"> </script>          <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>   <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />-->    <!--  <link rel="stylesheet" href="css/jquery-ui-1.9.2.custom.css" /> -->
        
        <link rel="stylesheet" href="css/jquery-ui-tabs.css" />
        <link rel="stylesheet" type="text/css" href="css/index.css"/>   <!--  мои стили  -->
        
        <script type="text/javascript" src='js/message/codebase/message.js'></script>        
        <link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_default.css" title="Default"/>
                                             <!--<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_growl_dark.css" title="Growl - dark"/>
                                             <link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_growl_shiny.css" title="Growl - shiny"/>
                                             <link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_solid.css" title="Solid"/>
                                             <link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_skyblue.css" title="SkyBlue"/>  
                                             -->
     	<script type="text/javascript" src="js/jquery.qtip.js"> </script>
	<link rel="stylesheet" type="text/css" href="css/jquery.qtip.css"/> <!-- манящий уголок  -->
        
        <script type="text/javascript" src="js/jquery.cookie.js"></script>     
        
    </head>
    <body>

       <%    //oEmail = 1 ; 
            if (oEmail == null) // если в сессии отсутствует запись "sEmail" рисуем только формы создания аккаунта и входа
                 //  <h1> < %=oLogin% > </h1>    //  <h1> < %=value1% > </h1>    // if(value.toString().isEmpty())
       { %>  
  
               <!-- Эта проверка обязательно должна быть здесь, когда главная страница еще не загружена! нельзя перемещать в общий JS файл! -->
               <script> 
                    //$.cookie("auth", 111, { expires: 2,  path: '/'    });
                    $(document).ready(function(){
                         var sAuth = $.cookie("auth");     // Ищем куку auth, если есть то:
                         if (sAuth != null){     
                              ajax_LoginForCookie(sAuth); // пробуем войти через КУку - отправляем куку на сервер ...
                              //alert(sAuth);
                         }
                    });
               </script>
 
       
       
<!-- ------------- ФОН ----------------- -->
<div id="divFon" >     
      <!--<div id="divError" onClick="ajax_sendEmail(); "  >    </div> <br>       alert(1);   <!--  может быть кода понадобится  для отладки -->
         
                      	
 <!-- ----------- ВХОД форма ----------------- -->
           <div  id="divLogin" > 
                <img  hidden id="imgLoading" src="img/loading6.gif" width="105" height="16" style=" left: 154px; top: -67px; position: absolute" />      

                <form  class="formLogin" >
                                        <!-- <img hidden id="imgFirstLoginHelp" src="img/help1.png" alt="help"  width="32" height="32" style=" position: absolute; top: -30px;left: 420px;" /> -->
                     <br>               <!-- value="ser111@ss.ss"    value="111"   autocomplete="on" -->
                     <center> 
                     <input  class="sInput_Login" id="sEmail"   type="text" maxlength="55" autocomplete="on" placeholder="Е-Маил..."  /> 
                     <input  class="sInput_Login" id="sPassword" type="Password" value="" placeholder="Пароль..."  maxlength="25" title="" />  <!-- onClick='javascript: delTitle1();'   без "border: 1px solid inherit" не работает ... <input type='checkbox'  id='111' value='sdfsdf' checked='checked' /> -->

                     <input  hidden class="sInput_Login" id="sName" type="text" value="" placeholder="Имя (не обязательно)"  maxlength="25" style="background: rgb(178, 182, 189);" />  
                     <input  hidden class="sInput_Login" id="sLastName" type="text" value="" placeholder="Фамилия (не обязательно)"  maxlength="25" style="background: rgb(178, 182, 189);" />  
                     <input  hidden class="sInput_Login" id="sINN" type="text" value="" placeholder="ИНН (не обязательно)"  maxlength="25" style="background: rgb(178, 182, 189);" />  </center>   
                      <br hidden id="brLogin">  
                     <center> <input hidden  type="checkbox" id="checkAgreement"  checked> 
                     <a hidden  id="sTextAgreement" href="agreement.html" target="_blank"  style="font-size: 13px; cursor: help; text-decoration: none; color: rgb(180, 180, 180);">
                          &nbsp; Я согласен на хранение своих персональных данных (?). </a>
                     </center> 

                          <!--    <a id="linkRegister" href ="#" >Регистрация </a>   <br>   -->
                     <center> 
                     <input  style ="margin-top: 20px;" class="allButt" id="btLogin" type="button" value="&nbsp;&nbsp;&nbsp;  Вход  &nbsp;&nbsp;&nbsp;" /> </center> 
                         <button hidden id="btSubmit" type="submit"> Submit </button>
                </form>

           </div>
 
 
<!-- <h1>    Добро пожаловать <    %=oLogin%>  <    %=oLastName%> <    %=oFirstName%> <   %=oSureName%>    </h1> !-->

<!--  Подпись внизу   -->  <!--  <div style="font-size: 12px; position:absolute; float:right; color:white; left: 30px; height:30px; top:91%; padding-top:7px; border-top:1px solid white ">Created by: Belyavtsev Sergey Vladimirovitch <br>All rights reserved. 2013</div>  -->

  
        <% } else  // иначе если есть сессия рисуем ГЛАВНУЮ страницу     
        { %>    

       
<!--     Добро пожаловать на главную страницу! -->
     <script src="js/peel.js" type="text/javascript"></script>  <!--  подключаем манящий уголок только здесь --> 


<!-- ----------  СПИСОК СЕССИЙ (окно) ---------- -->  
     <div id="divAllSessinList" >   
             <input type="button" value="Закрыть" style="position:relative; left:700px;" onClick="$('#divAllSessinList').css('display','none'); $('#FON_contact').css('display','none');" >
             <table id="table1" cellspacing="0"> </table>
     </div>


<!-- --------- КОНТАКТ (окно) ----------------- -->          
     <div id="form_contact">
               <p id="btClose_contact" >X</p>
                         <h1 id="title1"> <b>Форма  обратной связи</b></h1>
                         <div> <b>Здесь вы можете оставить свое сообщение:</b> </div>
                         <div> <b>Ваше имя:</b>  <input class="theInput"  type="text" value="Сергей"  maxlength="30" readonly="" style="background: #cccccc; color: gray" /></div>
                         <div> <b>Ваш e-mail:</b> <input class="theInput"  type="text"  value="Serg@mail.ru" maxlength="30" readonly="" style="background: #cccccc; color: gray" /></div>
                         <div> <b>Тема сообщения:</b> <select class="theInput"  id="sel" >           <option value="" selected="selected"> --- выбрать тему ---</option>       <option value="Предлагаю...">Предлагаю...</option>  	  <option value="Нашел ошибку!">Нашел ошибку!</option> 	      <option value="Помогите...">Помогите...</option>      <option value="Желаю...">Желаю...</option>           </select></div>
                         <div  id="blockMessage" > <b>Сообщение:</b>  </div>
                         <div  id="blockSelect" >   <textarea id="textarea_contact" rows="14" cols="57" placeholder="" maxlength="1000"  ></textarea></div>                    
                         <div  id="blockMessageLength"> (Осталось символов: <b>1000</b>)  </div>
                         <div>  <input class="theInput"  style="left : 320px; width:110px"  type="button"  value="Отправить" onClick="alert('Ваше сообщение типа отправлено!'); $('#textarea_contact').val(''); " > </div>
     </div>

<!-- ------- темный фон за всеми окнами  --------  -->
     <div id="FON_contact">  </div> 


 <!-- ---------- ПОМОЩЬ слева (окно) ------------ -->               
     <div id="divHelpLeft"> <img src="img/help1.png" style="position:relative; top:-5px; left:8px;" align="left"/><center> Задать вопрос </center></div>


     
<!-- ---------- ГЛАВНАЯ страница  ------------ -->                    
     <div  id="divMainPage">
     <img id="exitSite" src="img/exit1.png" style="position:absolute;  top:8px; right:8px; cursor:pointer;" title="Один клик - обычный выход, зажатая левая кнопка мыши более 2 секунд - закрытие вкладки">
     <!--  ДЛЯ ТЕСТОВ  
     <img id="exitSite2" src="img/krest.jpg" style="position:absolute;  top:8px; right:-48px; cursor:pointer;" title="Выход - удаление сессии "> 
     -->                    

     <div  id="img_logo"> </div>                                    <!--     <a id="linkAbout" style=" position:absolute; left:800px; top:40px; text-decoration: none; color:white;" target="_blank" href="space_galery.html" title="">Инструменты создания<br> данного сайта...</a>             --> 
     <div style=" position : relative; top: -50px; left: 260px; color:white; font-size: 26px; font-family: verdana; ">Знание - сила!</div>




  <style>
         /*Стили для Табов*/
       #panelTabs{
            height : 930px;
            border :1px solid black;
       }
       #panelTabs > ul li img {                /*выравниваем картинки табов*/
            width:20px; height:20px;  top:8px; left:8px;  position:absolute;
       }           
                               /* .ui-tabs .ui-tabs-nav li a {   outline: none;  font-size:15px; font-family: verdana;  }  /*отключаем желтую рамку на Табах*/ 
       #panelTabs > ul {
            padding-top: 5px;
            padding-left: 25px;
                                     /*ba ckground: -webkit-linear-gradient(top, rgb(97, 97, 97) 0%, rgb(0, 0, 0) 100%);*/
            border: 1px solid black; 
       }                   
                      
       #panelTabs > ul li a {  cursor : default;          
         height: 23px;
       }       
       #panelTabs > ul li   {
            height : 36px;   
            outline : none;   /*отключаем браузерную желтую рамку на активных Табах */ 
            font-family: verdana;
       }
 
  </style>
  

<!--  Панель ТАБов  --> 
 
<div id="panelTabs">
            <!--  название корневых вкладок -->
     <ul>
          <li> <img id="im1" src="img/tabs/mail1.png"  align="left"  />  <a href="#tabMessages">&emsp;&nbsp;Сообщения</a></li>
          <li> <img  id="im2" src="img/tabs/zadaniya.png" align="left"  /> <a href="#tabJob">&emsp;&nbsp;Задания</a></li> 
          <li> <img  id="im3" src="img/tabs/test1.png" align="left" /> <a  href="#tabMaterials">&emsp;&nbsp;Материалы</a></li>
          <li> <img  id="im4" src="img/tabs/user.png" align="left" /> <a href="#tabPrifile">&emsp;&nbsp;Профиль</a></li>
          <li> <img  id="im5" src="img/tabs/stats1.png" align="left"  /> <a href="#tabStatistic">&emsp;&nbsp;Статистика</a></li>
          <li> <img  id="im6" src="img/tabs/structure.png" align="left"  /> <a href="#tabStructure">&emsp;&nbsp;Структура</a></li>
     </ul>
          
     <!-- внутренность корневых закладок -->
     <!-- 1 закладка сообщений  --> 	 
     <div id="tabMessages">
          


     <style>  
          /*Стили для настроек*/
          #filterMsg {   border: 1px solid black; padding: 10px; background: rgb(207, 207, 207); 
           border-radius: 5px 5px 5px 5px;
          }
          #filterMsg label{   color:blue;    }
          #filterMsg input{   cursor:pointer;  display:none;    }
          #filterMsg table{   float:none;    }
          #filterMsg table tr td label:hover{   cursor:pointer;    }
          #filterMsg table tr td{    text-align:center;     padding: 6px; padding-bottom: 3px; padding-top: 3px;    }
          #filterMsg table tr td div{      /*название фильтров*/
            float:left;        font-weight:bold;      cursor:default;
          } 

           #filterHeader{
               font-size:16px; font-weight: bold;  color: rgb(134, 134, 134);
               width: 210px;
               left: 0px;
               position: relative;
               background: rgb(207, 207, 207);
               padding-bottom: 7px;
          }
     </style>


     <script>
          $(document).ready(function(){
               setFilter(); // инициализация (подчеркивания) активных Радио

               $("#filterMsg table tr td input").click(function(){    
                    setFilter();        // устанавливаем подчеркивания  активных Радио      // $(this).prev().css("checked","checked");   
               }); 

               function setFilter() {  // устанавливаем подчеркивания активных Радио
                    $("#filterMsg table tr td input").each(function() {
                         if ($(this).is( ":checked" ))
                              $(this).next().css("border-bottom","1px solid rgb(12, 69, 214)").css("font-weight","bold").css("color","rgb(12, 69, 214)"); 
                         else
                              $(this).next().css("border-bottom","0px solid blue").css("font-weight","normal").css("color","blue");  
                    });           
               };

          }); 
     </script>


     <!--     <!-- align="left" border="0" cellspacing="0" cellpadding="0"  -->
     <div id="filterMsg">          
     <div id="filterHeader"style="">Показать сообщения:</div>

          <table>   
               <tr>   
                    <td style="width:87px;"> <div>Тип:</div>   </td>
                    <td style="width:65px;"> <input type="radio" name="tip" id="1" />  <label for="1">Личные</label>  </td>   
                    <td style="width:110px;"> <input type="radio" name="tip" id="2" />  <label for="2">Общественные</label></td>
                    <td style="width:50px;"> <input type="radio" name="tip" id="3" checked="checked" />  <label for="3">Все</label> </td>
               </tr> 
          </table>      
          <table>    
               <tr>         
                    <td style="width:88px;"> <div>Период:</div></td>
                    <td style="width:70px;"> <input  type="radio" name="period" id="11"  />     <label for="11">1 Неделя</label></td>
                    <td style="width:70px;"> <input  type="radio" name="period" id="22" checked="checked" />   <label for="22">1 Месяц</label></td>
                    <td style="width:80px;"> <input  type="radio" name="period" id="33" />    <label for="33">6 Месяцев</label></td>
                    <td style="width:45px;"> <input  type="radio" name="period" id="44" />    <label for="44">1 Год</label></td>
                    <td style="width:45px;"> <input  type="radio" name="period" id="55" />    <label for="55">Все</label></td>
               </tr>          
          </table>               
          <table>
               <tr>         
                    <td style="width:90px;"> <div>Статус:</div></td>
                    <td style="width:70px;"> <input type="radio" name="status" id="111" checked="checked" />      <label for="111">Активные</label></td>
                    <td style="width:110px;"> <input type="radio" name="status" id="222" />       <label for="222">Завершенные</label></td>
                    <td style="width:40px;"> <input type="radio" name="status" id="333"  />    <label for="333">Все</label></td>
               </tr> 
          </table>

     </div>  


     <br>   <!--  большое поле для сообщений сообщений  -->
     <div id="divListMessages" style="height: 500px; border: 1px solid black; overflow: scroll">  </div>
     <br>


     </div>   <!--  конец закладки сообщений  -->



     <!-- 2   -->  	<div id="tabJob">
          <p>  Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
     </div>
 
     
     
     <!-- 3  -->  <div id="tabMaterials">
          <br>
          <br>
          <br>
          <div id="subTabsMaterials" style="height:500px;">
               <ul> <!--   style="background:#fac34e;" -->   
                    <li><a href="#tab1"> 11111111 </a></li>
                    <li><a href="#tab2">22222222</a></li> 
                    <li><a href="newhtml.html">My id 1</a></li> <!--  подгрузка через АЯКС  -->
                    <!--   <li><a href="#tab3">333333333</a></li>  	  -->
               </ul>
                     <!--   <li><a href="newhtml.html">My id 1</a></li>-->
               <div id="tab1">
                    <p>1 Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo. Vivamus sed magna quis ligula eleifend adipiscing. Duis orci. Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique tempus lectus.</p>
               </div>
                    
               <div id="tab2">
                    <p>2 Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
               </div>
                    
               <!--  <div id="tab3">
                    <p> 3 Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
               </div>  -->
                    
          </div>
     </div>
          
          
     <!-- 4  -->  <div id="tabPrifile" >
          <br>
          <br>
          <br>
          <div id="subTabsPrifile" style="height:500px;">
               <ul>    
                    <li><a href="#tab1111"> Коллектив </a></li>
                    <li><a href="#tab2222">Документы</a></li> 
                    <li><a href="#tab3333">Данные</a></li>  	  
                    <li><a href="#tab4444">Администрирование</a></li>  	  
                    <li><a href="#tab5555">Настройки</a></li>  	  
               </ul>
                    
               <div id="tab1111">
                    <p> 33243423</p>
               </div>
                    
               <div id="tab2222">
                    <p> 324234524534</p>
               </div>
                    
               <div id="tab3333">
                    <p>12312</p>
               </div>
                    
               <div id="tab4444">
                    <p>12312</p>
               </div>
                    
               <div id="tab5555">
                    <p>12312</p>
               </div>
          </div>
     </div>
          
          
          
     <!-- 5  -->  <div id="tabStatistic">
          <br>
          <br>
          <br>
          <div id="subTabsStatistic" style="height:500px;">
               <ul>    
                    <li><a href="#tab111">Успеваемость</a></li>
                    <li><a href="#tab222">Нагрузка</a></li> 
                    <li><a href="#tab333">Социология</a></li>  	  
               </ul>
                    
               <div id="tab111">
                    <p>11111</p>
               </div>
                    
               <div id="tab222">
                    <p>22222</p>
               </div>
                    
               <div id="tab333">
                    <p>33333</p>
               </div>
                    
          </div>			 
     </div>
          
          
          
     <!-- 6  -->   <div id="tabStructure">
          <br>
          <br>
          <br>
          <div id="subTabsStructure" style="height:500px;">
               <ul>    
                    <li><a href="#tab11">Студенты</a></li>
                    <li><a href="#tab22">Преподаватели</a></li> 
                    <li><a href="#tab33">Руководство</a></li>  	  
                    <li><a href="#tab44">Факультеты</a></li>  	  
                    <li><a href="#tab55">Кафедры</a></li>  	  
                    <li><a href="#tab66">Группы</a></li>  	  
               </ul>
                    
               <div id="tab11">
                    <p>11111</p>
               </div>
                    
               <div id="tab22">
                    <p>22222</p>
               </div>
                    
               <div id="tab33">
                    <p>33333</p>
               </div>
                    
               <div id="tab44">
                    <p>44</p>
               </div>
                    
               <div id="tab55">
                    <p>555</p>
               </div>
                    
               <div id="tab66">
                    <p>66</p>
               </div>
                    
          </div>		 
     </div>
          
          
          
          
</div>



<!--   
<br>

 <div id="divNavigation" >
            <ul>    
                    <img src="img/key.png" style="position: absolute; padding-top:8px;padding-left:8px;" /> <li> <a id="mainPageAdmin" href="#" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;На сайте</a>  	 </li> 	<div id="separatop"></div>
                    <img src="img/nastr1.png" style="position: absolute; padding-top:8px;padding-left:8px;" /> <li>  <a id="mainPageSettings" href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Настройки</a>	 </li> 	<div id="separatop"></div>
                    <li> <img src="img/user.png" style="position:relative; top:11px; left:5px; padding-right:5px;" align="left"/> <a id="mainPagePrivateOffice" href="#">Личный кабинет</a></li> 	<div id="separatop"></div>
                    <li> <img src="img/test1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageTest" href="#">Тесты</a>    </li>  <div id="separatop"></div>
                    <li> <img src="img/mail1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageContact" href="#">Сообщения</a> </li>   <div id="separatop"></div>
                    <li> <img src="img/help1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageHelp" target="_blank" href="space_galery1.html" >О сайте</a> </li>   <div id="separatop"></div> 
                    <li> <img src="img/exit1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageExitSession" href="#">Выход</a>	     </li>
            </ul> 
    </div>
-->

<script>
     /* ------ Базовая форма для Jquery UI меню */
     $(function() {  $( "#menu" ).menu();  });	
     //$( "#menu" ).menu({ icons: { submenu: "ui-icon ui-icon-play" } }); // выставляем вид стрелки

// Эффекты
/*  $("#mainPageLibrary").click(function() {
$("#men").effect("fade", { mode: "show", direction: "horizontal" }, 400); 
 });
 
 $("#men").mouseover(function() {
 $("#men").effect("fade", { mode: "hide", direction: "horizontal" }, 400); 
     });    */
 
</script>




<!-- --------- МЕНЮ (окно)--------- -->

<!--
<div id="men" >
<ul id="menu" >  
 
     <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> О ВУЗе </a>
          <ul>
               <li><a href="#"> Структура </a></li> <!-- target="_blank" -- >  
               <li><a href="#">Новости </a></li>  
               <li><a href="#">Структура </a></li> 
               <li><a href="#">Факультеты</a></li>
               <li><a href="#">Ученый совет</a></li> 
               <li><a href="#">Руководство</a></li> 
               <li><a href="#">Преподаватели и сотрудники</a></li> 
               <li><a href="#">Телефонный справочник</a></li> 
               <li><a href="#">История</a>	</li> 
               <li><a href="#">Устав</a></li> 
               <li><a href="#">Библиотека</a></li> 
               <li><a href="#">Контакты</a></li> 
               <li><a href="#">Полезные ссылки</a></li> 
          </ul> 
     </li>
          
     <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Абитуриентам </a>
          <ul>
               <li> <a href="#">Почему выбирают именно нас?</a> </li>
               <li> <a href="#">Приемная комиссия</a>	</li>
               <li> <a href="#">Направления, специальности, экзамены</a>	</li>
               <li> <a href="#">Стоимость обучения</a> 		</li>
               <li> <a href="#">Бланки и образцы документов</a>	</li>
               <li> <a href="#">Правила приема</a>	</li>
               <li> <a href="#">Где работают выпускники</a> 	</li>
               <li> <a href="#">Отзывы выпускников</a>			</li>
               <li> <a href="#">Фото-экскурсия</a>			</li>
          </ul> 
     </li>
          
     <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Студентам </a>
          <ul>
               <li><a href="#">Расписание занятий</a>		</li>
               <li><a href="#">Учебные дисциплины</a>		</li>
               <li><a href="#">Учебные планы</a>		</li>
               <li><a href="#">Спортивная жизнь</a>	</li>
               <li><a href="#">Правила перевода</a>	</li>
               <li><a href="#">Трудоустройство</a>		</li>
               <li><a href="#">Учебные материалы</a>	</li>
               <li><a href="#">Студенческие новости</a>	</li>
          </ul> 
     </li>
             
     <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Научная деятельность </a>
          <ul>
               <li><a href="#">Список конференций</a>		</li>
               <li><a href="#">Публикации</a>	</li>
          </ul> 
     </li>
             
     <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Выпускникам </a>
          <ul>
               <li><a href="#">Ассоциация выпускников</a>		</li>
               <li><a href="#">Форумы</a>			</li>
               <li><a href="#">Выпускники в соцсетях</a>		</li>
               <li><a href="#">Отзывы выпускников</a>		</li>
               <li><a href="#">Семинары и тренинги</a>		</li>
          </ul> 
     </li>
             
     <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Дополнительное образование </a>
          <ul>
               <li>	<a href="#">Бланки и образцы документов</a>	</li>
               <li>	<a href="#">Карьерный рост</a>			</li>
               <li>	<a href="#">Направления, специальности, экзамены</a>	</li>
               <li>	<a href="#">Информация о дополнительном образовании, цены</a>	</li>
               <li>	<a href="#">Правила приема</a>		</li>
               <li>     <a href="#">Где работают выпускники</a>	</li>
          </ul> 
                  
     </li>	
             
     <li> <img id="img_menu" src="img/ar2.png" align="left" /><a href="#">  Документы </a>
          <ul>
               <li><a href="#">Шаблоны документов</a>	</li>
               <li><a href="#">Нормативно-правовые акты</a>	</li>
          </ul> 
     </li>	
             
     <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Общение </a>
          <ul>
               <li>  <a href="#">Блоги</a>		</li>
               <li>  <a href="#">Форумы</a>		</li>
               <li>  <a href="#">Анкетирование</a>		</li>
          </ul> 
     </li>
	
</ul>
</div>
                     
   -->





                     
 <!--  конец листа ГЛАВНОЙ страницы  -->                     
</div> 





<!-- </div> нижний футер отступ -->
     <div style="width:1000px; height:120px;"> </div>  
   



<!--  Helper справа  -->

<style type="text/css">
a {  outline: none; }
#help { display: block;  font-size: 12px;  position: fixed;   right: 0;  top: 250px; width: 279px;   z-index: 5;   font-family: Tahoma,Verdana,Arial,Helvetica,sans-serif; }
#help img{    border: 0px;  }
#help-panel { background-color: #FFFFFF;  border: 1px solid #CCCCCC;  display: none; float: right;  width: 240px; }
#help-panel a:hover{     }
#help-panel .collapse {  display: none;  float: left;  padding: 15px 10px; font-size: 12px;  line-height: 15px;}
#help-panel .collapse a {   font-size: 11px;  color: black;  text-decoration: underline;  font-size: 12px;}
#support-header {  background: url(img/helper/24-7-bg.gif) repeat-x scroll 0 0 transparent;  height: 17px;  padding: 2px 0 0 10px; }
#help-panel .main {  background: url(img/helper/item-bg.gif) repeat-x scroll 0 0 transparent;	  float: left;  height: 37px; width: 240px; }
#help-panel a.title {  background: url("img/helper/but-right.gif") no-repeat scroll 10px 15px transparent;  color: #444;  display: block;   font-weight: bold;  padding: 12px 10px 12px 22px;  font-size: 12px;}
#help-panel a.title span {  color: #666;  display: block;  font-weight: bold;  font-size: 12px;}
#help-panel a.selected {  background: url("img/helper/but-down.gif") no-repeat scroll 10px 15px transparent;   color:blue;  }
#help-panel a:hover{  background: rgb(163, 163, 163);	}
#help a {     text-decoration: none; }
#help-button {     float: right; }
</style>
	
	
<div id="help">
<div id="help-panel" style="display: none;">
    <!-- Блок телефонов -->
    <div class="main"><a class="title" href="javascript:void(0);">Телефоны</a></div>    
        <div class="collapse">
            <strong>8-068-405-31-22</strong><br />  ПН-ПТ с 9.00 до 17.00,<br />   СБ с 10.00 до 15.00,<br />   Сергей
        </div>
    <!-- Блок службы поддержки -->
    <div class="main"><a class="title" href="javascript:void(0);">Служба поддержки</a></div>    
        <div class="collapse">
        <a href="http://www.google.com" target="_blank">Ответы на частые вопросы</a><br><br>
        <a href="http://www.google.com" target="_blank">Задать свой вопрос</a><br><br>
        <a href="http://www.google.com" target="_blank">Просто вопрос</a><br><br>
        </div>
    <!-- Блок перехода к форме заказа -->
    <div class="main"><a class="title" href="#zakaz">Пункт меню1 (пустой)</a></div>  
    <!-- Блок перехода к частым вопросам  -->
    <div class="main"><a class="title" href="#faq_block">Пункт меню2 (пустой)</a></div>  
</div>
<a id="help-button" href="javascript:void(0);"><img alt="" src="img/helper/button.png"/></a>
</div>

<script type="text/javascript">
$(function(){
    var current_index = null;    
    $('#help-button').click(function(){        
        $('#help-panel').toggle(300);
    });
    $('a.title').click(function(){             
        if(current_index != $('a.title').index(this)){
            $('.collapse:visible').slideUp(500);
            $('a.title').removeClass('selected');
        }        
        if($(this).parent().next().attr('class') == 'collapse'){        
            if($(this).parent().next().is(":hidden")){
                $(this).addClass('selected');
            } else if($(this).parent().next().is(":visible")) {
                $(this).removeClass('selected');
            }
            $(this).parent().next().slideToggle(500); // показать
        }
        current_index = $('a.title').index(this);        
    });
    
    $('#help-panel').mouseleave(function(){
      $('#help-panel').toggle(300); //$('#help-panel').slideToggle(500); // спрятать
    });    
});
</script>

<!--  -------------------------------------------------------    -->






<style>
/* ----- тесты ------ */
#left textarea{
     resize: none;
}

#left {
     border:1px solid black;
     background:rgb(173, 173, 173);
     position:absolute;
     width:560px;
     height:590px;
     left: 300px;
     top:100px;
     border-radius: 7px 7px 7px 7px;
     font-size : 16px;
     font-family: verdana;
     font-weight : bold;
        display:none;
}

#left, #left textarea/*, textarea*/{  /*отключаем выделение */
     -moz-user-select: -moz-none;
     -o-user-select: none;
     -khtml-user-select: none;
     -webkit-user-select: none;
     user-select: none;
     cursor : default;
}

#left div{		 
     margin: 7px;		  
}

#left textarea{
     border-radius: 4px 4px 4px 4px;
     padding:7px;
     border:1px solid black;
      outline: none; /*отключаем желтую подсветку*/
     font-size : 16px;
     font-family: verdana;
     margin-left : 20px;
}

#frameDivReportTest{
     position:absolute;
     left: 300px;
     top:100px;
     width:560px;
     height:590px;
     padding : 5px;
     border:1px solid black;
     background:rgb(150, 150, 255);
       display:none; /*изначально скрытый*/
}

#divReportTest{
     padding : 5px;
     border:1px solid black;
     background:rgb(173, 173, 173);
     width:98%;
     height:90%;
     background: white;
     overflow:scroll; /* прокуртка*/
     z-index : 10;
}
</style>

<!--  окно отчета  -->
     <div id="frameDivReportTest"> 
     <div id="divReportTest"></div>
     <input type="button" id="closeDivReportTest" value="Закрыть" style="margin-left: 470px; margin-top: 15px;" onClick="$('#frameDivReportTest').css('display','none')" />
     </div>

<!--  окно теста -->
<div  id="left">
     <div id="numVopr"> Вопрос</div>
     <textarea class="" rows="8" cols="60" id="tarea_vopros"  readonly=""  ></textarea>
     <br>
     <br>
     <div id="answers"> Варианты ответов (чтобы выбрать, кликните): </div>
     <textarea class="tarea" rows="2" cols="60" id="tarea1" readonly=""  ></textarea>
     <textarea class="tarea" rows="2" cols="60"  id="tarea2" readonly=""></textarea>
     <textarea class="tarea" rows="2" cols="60"  id="tarea3" readonly=""></textarea>
     <textarea class="tarea" rows="2" cols="60"  id="tarea4" readonly=""></textarea>
     <textarea class="tarea" rows="2" cols="60"  id="tarea5" readonly=""></textarea>
     <!-- <a href="#">Прервать тест</a> -->
</div>


<script>
$(document).ready(function(){
    // меняем фон наведенной кнопки
$(".tarea").mouseover(function() {  $(this).css('background','rgb(149, 190, 253)');  })
           .mouseout(function() {   $(this).css('background','white');    });

var count = 0; // счетчик вопросов
var countPrav = 0; // кол. правильных
var sReportTest = ""; // отчет

var arr = [  /*'Сколько будет 1 + 1 ?',   '1', '2', '3', '4', '5',       '2',     */
			  
     '1.	Web-страница (документ HTML) представляет собой: ',
'a.	Текстовый файл с расширением txt или doc',
'b.	Текстовый файл с расширением htm или html',
'c.	Двоичный файл с расширением com или exe',
'd.	Графический файл с расширением gif или jpg',
'е.	Графический файл с расширением gif111 или jpg111',
     'b.	Текстовый файл с расширением htm или html',

     '2.	Для просмотра Web-страниц в Интернете используются программы: ',
'a.	MicroSoft Word или Word Pad',
'b.	MicroSoft Access или MicroSoft Works',
'c.	Internet Explorer или Opera (Google Chrome)',
'd.	HTMLPad или Front Page ',
'e.	HTdsfMLPgfad или Fdfgront Pdfgage ',
    'c.	Internet Explorer или Opera (Google Chrome)',
	
'3.	Тег - это: ',
'a.	Специальная команда, записанная в угловых скобках < > ',
'b.	Текст, в котором используются спецсимволы ',
'c.	Указатель на другой файл или объект ',
'd.	Фрагмент программы, включённой в состав Web-страницы ',
'e.	Фрагмент ',
      'a.	Специальная команда, записанная в угловых скобках < > ',

'4.	Для чего предназначен тег <BODY> ? ',
'a.	Идентификатор заголовка окна просмотра',
'b.	Идентификатор заголовка документа HTML ',
'c.	Идентификатор перевода строки ',
'd.	Тело HTML документа',
'e.	Фрагмент стиля страницы ',
    'd.	Тело HTML документа',

'5.	Для вставки изображения в документ HTML используется команда: ',
'a.	<img src="ris.jpg"> ',
'b.	<body background="ris.jpg"> ',
'c.	<a href="ris.jpg"> ',
'd.	<input="ris.jpg"> ',
'e.	<input="ris1.jpg"> ',
'a.	<img src="ris.jpg"> ',

'6.	Гиперссылка задается тегом: ',
'a.	<font color="file.htm">',
'b.	<img src="http://www.chat.ru"> ',
'c.	<a href="file.htm">текст</a> ',
'd.	<embed="http://www.da.ru"> ',
'e.	<embed="http://www.da.ru"> ',
'c.	<a href="file.htm">текст</a> ',


 
'7.	Гиперссылки на Web - странице могут обеспечить переход: ',
'a.	только в пределах данной web – страницы',
'b.	только на web - страницы данного сервера ',
'c.	на любую web - страницу данного региона ',
'd.	на любую web - страницу любого сервера Интернет ',
'e.	на страницу локального сервера ',
'd.	на любую web - страницу любого сервера Интернет ',

'8.	Ссылка на адрес электронной почты задается тегом: ',
'a.	kompas@email.ru',
'b.	<a href="mailto:svetlana@narod.ru">текст</a> ',
'c.	<a href="marina@mail.ru">текст</a> ',
'd.	<piter@mailru.com> ',
'e.	<kompas@email.ru> ',
'b.	<a href="mailto:svetlana@narod.ru">текст</a> ',

'9.	Гипертекст - это: ',
'a.	Текст очень большого размера',
'b.	Текст, в котором используется шрифт большого размера ',
'c.	Структурированный текст, где возможны переходы по выделенным меткам',
'd.	Текст, в который вставлены объекты с большим объемом информации',
'e.	Закодированный текст',
'c.	Структурированный текст, где возможны переходы по выделенным меткам',

'10.	Для создания Web-страниц используются программы: ',
'a.	DreamWeaver и Notepad++',
'b.	Turbo Pascal и QBasic ',
'c.	Visual Basic и ACDSee ',
'd.	ScanDisk и Defrag ',
'e.	Visual C ',
'a.	DreamWeaver и Notepad++',

'11.	Каким тегом определяется абзац текста?',
'a.	<br>',
'b.	<div>',
'c.	<p>',
'd.	<textarea>',
'e.	<a>',
'c.	<p>',

'12.	Какие теги из перечисленных ниже определяют элементы-контейнеры?',
'a.	<a>',
'b.	<br>',
'c.	<div>',
'd.	<img>',
'e.	<diiv>',
'c.	<div>',

'13.	За что отвечает параметр SIZE  тега <FONT> ?',
'a.	Длинну строки',
'b.	Расстояние между буквами',
'c.	Расстояние между строчками',
'd.	Размер шрифта',
'e.	Размер абзаца',
'd.	Размер шрифта', 

'14.	Какой тег используется для организации списков?',
'a.	<tr>',
'b.	<hr>',
'c.	<ol>',
'd.	<th>',
'e.	<br>',
'c.	<ol>'
 ];
	
     // инициализация
     //$("#numVopr").text(arr[count+0]); // № вопроса
     $("#tarea_vopros").val(arr[count+0]); // сам вопрос
     $("#tarea1").val(arr[count+1]); // устанавливаем варианты ответов
     $("#tarea2").val(arr[count+2]);
     $("#tarea3").val(arr[count+3]);
     $("#tarea4").val(arr[count+4]);
     $("#tarea5").val(arr[count+5]);
     
        
     // кликаем на ответ
     $(".tarea").click(function() {
          
          $("#left").hide("fade", {}, 300); // сворачиваем окно теста
          //$(this).effect("explode", {}, 1000);
          
          if ($(this).val() == arr[count+6]) {  //проверка ответ правильный
               ++countPrav;  // считаем правильные ответы
          }       //alert(countPrav);
          
          // формируем отчет
          sReportTest += '<xmp>Вопрос '+((count/7)+1)+ '\n' ;
          sReportTest +=  ''+arr[count+0]+ '' +'\n' ; // сам вопрос
          sReportTest += arr[count+1]+ '\n';
          sReportTest += arr[count+2]+ '\n';
          sReportTest += arr[count+3]+ '\n';
          sReportTest += arr[count+4]+ '\n';
          sReportTest += arr[count+5]+ '\n';
          sReportTest += 'Правильный ответ:  ' + arr[count+6]+ '\n';
          sReportTest += 'Ваш ответ:  ' + $(this).val()+ '\n </xmp>';     
          //alert(sReportTest );
          
          count+=7;  /*переходим к следующему вопросу, .....7 элементов занимает один вопрос в массиве*/
                   
          setTimeout(function() {    // делаем задержку чтобы загрузились вопросы
               $("#numVopr").text('Вопрос '+((count/7)+1) + " / " + arr.length/7); // № вопроса
               
               $("#tarea_vopros").val(arr[count+0]); // сам вопрос             
               $("#tarea1").val(arr[count+1]); // устанавливаем варианты ответов
               $("#tarea2").val(arr[count+2]);
               $("#tarea3").val(arr[count+3]);
               $("#tarea4").val(arr[count+4]);
               $("#tarea5").val(arr[count+5]);
               
               if ((count % 2) == 0)     // если четное меняем фон окна через 0,3 секунды
                    $("#left").css("background","rgb(173, 173, 173)");
               else       // если не четное меняем фон окна 
                    $("#left").css("background","rgb(134, 130, 130)");           
          }, 300); // конец таймера
          
                 
          if (arr[count+0] == null) { // В массиве больше нет вопросов 
                     
               if (confirm("Тестирование завершено! Вы ответили правильно на "+countPrav+" вопрос(ов) из "+arr.length/7 + '\n Хотите посмотреть детальный отчет?')) {
                    //alert("Привет!")
                    sReportTest += " ------------------------------------------- <br> Вы ответили правильно на "+countPrav+" вопрос(ов) из "+arr.length/7 +'\n' ;
                    $('#divReportTest').html(sReportTest) ;
                    $('#frameDivReportTest').show();
                    countPrav = 0;     // сбрасываем кол. правильных
                    count = 0;        // сбрасываем счетчик вопросов
                    sReportTest = ""; // сбрасываем отчет
                    
               }  else { // если нажать отмена (не показывать отчет)
                    countPrav = 0;     // сбрасываем кол. правильных
                    count = 0;        // сбрасываем счетчик вопросов
                    sReportTest = ""; // сбрасываем отчет
               }            
          }  else{
               $('#left').show('blind', { direction: 'vertical' }, 500); 
               }
               //$("#left").show("drop", "left", 800); // показываем след. вопрос
               //$('#left').show('drop', { direction: 'right' },1000);
               //$('#left').show('fade', { direction: 'right' },1000);
               // $('#left').clone().prependTo("body").show('fade', {}, 1000);
          
     });
});



var arr1 = [0,1,2,3,4,5,6,7,8,9];

/* получить случайное целое число из диапазона */
function random(min, max) {
     var range = max - min + 1;
     return Math.floor(Math.random()*range) + min;
}

/* перемешать массив */
function shuffle(arr) {
     var r_i; // случайный индекс
     var v; // временная переменная
     for (var i = 0; i < arr.length-1; i++) {
          /* получаем случайный индекс (кроме последнего) */
          r_i = random(0, arr.length-1);
          /* меняем местами случайный элемент массива с последним */
          v = arr[r_i];
          arr[r_i] = arr[arr.length-1];
          arr[arr.length-1] = v;
     }
     return arr;
}
</script>

<!--  --------------------------------------------------  -->



     
        <% }
     %>

</body>
</html>












 
<!-- ---------- АККАУНТ форма (пока не нужна) ---------  autocomplete="off" 

       <div hidden id="divAccount"  >
               <div id="divHeader_Account">Создание учетной записи: 
                    <img id="btClose_Account" src="img/krest.jpg"   border="1"   title="Закрыть"  />
               </div>  <br>  
		   <input id="sEmail_Account" class="sInput_Account" placeholder="Ваш Е-Маил..." title='Поле, обязательное для заполнения. Введите название вашего Е-Маил, который станет уникальным Логином для входа на этот сайт. <br>Так же ознакомьтесь: <a href="http://ru.wikipedia.org/wiki/Пароль" target="_blank" >Безопастность пароля</a>' type="text" value=""  maxlength="70" >                      
                   <input id="sPassword_Account" class="sInput_Account" placeholder="Пароль" title="Поле, обязательное для заполнения. Пароль должен содержать не менее 10 и не более 25 символов латинского алфавита и цифр (a-z, A-Z, 0-9). Для надежности очень желательно, чтобы пароль включал в себя не только маленькие латинские буквы, но так же заглавные буквы и цифры. Пример безопастного пароля:  <b>Ivan33bn81T</b> , <br>Примеры небесопастных паролей: <b>qwerty</b>, <b>123</b>, <b>7654321</b>. <br>Не экономьте несколько символов на своей безопастности! )) "  type="password" value=""  maxlength="25" >   
                   <input id="sPassword2_Account" class="sInput_Account" placeholder="Пароль (повторно)" title="Поле обязательное для заполнения. <br> Защита от невнимательности! :)"   type="password" value=""  maxlength="25" >   <br>
		   <input id="sFirstName_Account" class="sInput_Account" placeholder="Имя" title="Желательно, но необязательно :)"  type="text" value="" autocomplete="off"  maxlength="25" > <br> 
		   <input id="sLastName_Account" class="sInput_Account" placeholder="Фамилия" title="Пока не обязательно."   type="text" value="" autocomplete="on" maxlength="25">   <br>  <br>
                   <input class="allButt" id="btReg"  type="button" value="Создать" />             
            </div>     
      </div>
-->   



<!--    <div id="progressBar_Account" >     <div></div>          </div>  <br>
<style>
#progressBar_Account {
  background-color: #1E90FF;   border-radius: 5px;   padding: 2px;  width: 253px;
}

#progressBar_Account div {
   width: 45%;    background-color: lightblue;   height: 23px;   border-radius: 3px;
}
</style>     -->



  <!--  
         <div id="main_pict">
                 <img src="img/main2.jpg">
                 <br>	<br>

                 <div id="main_text">
                         <p>
                                 Подготовка в университете специалистов разных учебно-квалификационных уровней осуществляется по
                                 39 направлениям и 121 специальности, в том числе: баклавров 115 специальностей с лицензированным объемом 10440
                                 человек и 6250 человек на дневной и заочной формах обучения соответственно; специалистов 116
                                 специальностей - 8475 человек и 5180 человек; магистров - 2086 человек и 1170 человек.
                         </p>
                         <p>
                                 Кроме того, лицензирована
                                 переподготовка специалистов (1350 человек дневной формы и 1600 человек заочной формы обучения), подготовка к
                                 поступлению в высшие учебные заведения граждан Украины - 7600 лиц и иностранных граждан - 1000 лиц, а также
                                 повышение квалификации по базовым направлениям (специальностями) - 3900 человек в год. Аккредитованных
                                 специальностей: IV уровень - 96 (79,3%), III уровень - 15 (12,4%), II уровень - 8 (6,6%).
                         </p>
                         <tr>
                 </div>
           </div>  -->