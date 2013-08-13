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
  
  
  
  // }

  //Object oIP = session.getId();       //request.getRemoteAddr();      //request.getRemoteUser(); 
  //String sLogin = session.getAttribute("sLogin");       
%>
  


       <!-- Хеад формаы  -->

<!DOCTYPE html>
<html>
    <head>
         
         
             <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--  <script type="text/javascript" src="---js/jquery-1.7.2.js"></script>       <script type="text/javascript" src="---js/ajax1.js"> </script>          <!--  < link rel="stylesheet" type="text/css" href="css/my_style_1.css"/>  -->
        
       
        
        <script type="text/javascript" src="js/index.js"> </script>   
        <script src="http://code.jquery.com/jquery-1.9.1.js"> </script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/index.css"/>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
        
       
        
        <script type="text/javascript" src='js/message/codebase/message.js'></script>
	
	 <link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_default.css" title="Default"/>
	<!--<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_growl_dark.css" title="Growl - dark"/>
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_growl_shiny.css" title="Growl - shiny"/>
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_solid.css" title="Solid"/>
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_skyblue.css" title="SkyBlue"/>  
        -->
     	<script type="text/javascript" src="js/qTip2/jquery.qtip.js"> </script>
	<link rel="stylesheet" type="text/css" href="js/qTip2/jquery.qtip.css"/>
        
        
        

            <title> Главная страница </title>
    </head>
    <body>


       
       <%    //oEmail = 1 ; 
            if (oEmail == null) // если в сессии отсутствует запись "sEmail" рисуем только формы создания аккаунта и входа
                 //  <h1> < %=oLogin% > </h1>    //  <h1> < %=value1% > </h1>    // if(value.toString().isEmpty())
       { %> 
            

        
<!-- ------------------------ ГЛАВНАЯ форма------------------------------------- -->
<div id="divFon" >     
      

  <div id="divError" style="visibility: hidden">  </div>
  
  

 <!-- ------------------------ ВХОД форма ------------------------------------- -->
           <div id="divLogin" >
                <br>                             <!-- value="ser111@ss.ss"    value="111"-->
               <input  class="sInput_Login" id="sEmail"   type="text" value="ser111@ss.ss" placeholder="Е-MAIL..."  maxlength="25" />   <br> <br>
               <input  class="sInput_Login" id="sPassword" type="Password" value="111" placeholder="ПАРОЛЬ..."  maxlength="25" />  <br> <br>
               <input  class="allButt" id="btLogin"  type="button" value="Вход" />  
               <a id="linkRegister" href ="#" >Регистрация </a>   <br> <br>  <!--   http://localhost:8080/Business/register.jsp   -->
           </div>
 



<!-- --------------------------- АККАУНТ форма -------------------------------autocomplete="off" -->
       <div id="divAccount"  >
	 
            <div id="divHeader_Account">Создание учетной записи:     <!-- style="visibility: hidden" --> 
     		 <img id="btClose_Account" src="img/krest.jpg"   border="1"   title="Закрыть"  />
            </div>  <br>  
		    <!--  <span title="">*</span>-->    
		   <input id="sEmail_Account" class="sInput_Account" placeholder="Ваш Е-Маил..." title='Поле, обязательное для заполнения. Введите название вашего Е-Маил, который станет уникальным Логином для входа на этот сайт. <br>Так же ознакомьтесь: <a href="http://ru.wikipedia.org/wiki/Пароль" target="_blank" title="">Безопастность пароля</a>' type="text" value=""  maxlength="70" >                      
                   <input id="sPassword_Account" class="sInput_Account" placeholder="Пароль" title="Поле, обязательное для заполнения. Пароль должен содержать не менее 10 и не более 25 символов латинского алфавита и цифр (a-z, A-Z, 0-9). Для надежности очень желательно, чтобы пароль включал в себя не только маленькие латинские буквы, но так же заглавные буквы и цифры. Пример безопастного пароля:  <b>Ivan33bn81T</b> , <br>Примеры небесопастных паролей: <b>qwerty</b>, <b>123</b>, <b>7654321</b>. <br>Не экономьте несколько символов на своей безопастности! )) "  type="password" value=""  maxlength="25" >   
                   <input id="sPassword2_Account" class="sInput_Account" placeholder="Пароль (повторно)" title="Поле обязательное для заполнения. <br> Защита от невнимательности! :)"   type="password" value=""  maxlength="25" >   <br>
		   <input id="sFirstName_Account" class="sInput_Account" placeholder="Имя" title="Желательно, но необязательно :)"  type="text" value="" autocomplete="off"  maxlength="25" > <br> 
		   <input id="sLastName_Account" class="sInput_Account" placeholder="Фамилия" title="Пока не обязательно."   type="text" value="" autocomplete="off" maxlength="25">   <br>  <br>
                   <input class="allButt" id="btReg"  type="button" value="Создать" />             
            </div>       <!-- <a class="tooltip" href="#">Tooltip<span> Вот такая подсказочка получилась :).</span></a> -->
      </div>
          <!-- <h1>    Добро пожаловать <    %=oLogin%>  <    %=oLastName%> <    %=oFirstName%> <   %=oSureName%>    </h1> !-->


<center></center>
<div style=" font-size: 12px; position:absolute; float:right; color:white; left: 30px; /*width:300px;*/ height:30px; top:91%;/*top:770px;*/  padding-top:7px; border-top:1px solid white ">Created by: Belyavtsev Sergey Vladimirovitch <br>All rights reserved</div>



<!--    <div id="progressBar_Account" >     <div></div>          </div>  <br>
<style>
#progressBar_Account {
  background-color: #1E90FF;   border-radius: 5px;   padding: 2px;  width: 253px;
}

#progressBar_Account div {
   width: 45%;    background-color: lightblue;   height: 23px;   border-radius: 3px;
}
</style>     -->
  
  
        <% } else  // иначе если есть сессия рисуем главную страницу     
        { %>    

<!--          < %=oEmail%>        -->
<!--           Добро пожаловать на главную страницу! -->

<div id="divAllSessinList" >    
<input type="button" value="Закрыть" style="position:relative; left:700px;" onClick="$('#divAllSessinList').css('display','none'); $('#FON_contact').css('display','none');" >
<table id="table1" cellspacing="0">   
</table>

</div>

<!-- --------------------------- КОНТАКТ форма ------------------------------- -->          
<div id="form_contact">
          <p id="btClose_contact" >X</p>
                    <h1 id="title1"> <b>Форма  обратной связи</b></h1>
                    <div> <b>Здесь вы можете оставить свое сообщение:</b> </div>
                    <div> <b>Ваше имя:</b>  <input class="theInput"  type="text" value="Сергей"  maxlength="30" readonly="" style="background: #cccccc; color: gray" /></div>
                    <div> <b>Ваш e-mail:</b> <input class="theInput"  type="text"  value="Serg@mail.ru" maxlength="30" readonly="" style="background: #cccccc; color: gray" /></div>
                    <div> <b>Тема сообщения:</b> <select class="theInput"  id="sel" >           <option value="" selected="selected"> --- выбрать тему ---</option>       <option value="Предлагаю...">Предлагаю...</option>  	  <option value="Нашел ошибку!">Нашел ошибку!</option> 	      <option value="Помогите...">Помогите...</option>      <option value="Желаю...">Желаю...</option>           </select></div>
                    <div  id="blockMessage" > <b>Сообщение:</b>  </div>
                    <div  id="blockSelect" >   <textarea id="textarea_contact" rows="14" cols="57" placeholder="" maxlength="1000" ></textarea></div>
                    <div  id="blockMessageLength"> (Осталось символов: <b>1000</b>)  </div>
                    <div>  <input class="theInput"  style="left : 320px; width:110px"  type="button"  value="Отправить"> </div>

</div>


<div id="FON_contact">           
</div>
<!-- ---------------------------------------------------------- -->          


     
         
<div id="divHelp"> <img src="img/help1.png" style="position:relative; top:-5px; left:8px;" align="left"/><center> Задать вопрос </center></div>
<style>
     #divHelp{
          padding-top: 9px;
          -moz-transform: rotate(90deg);
    -webkit-transform: rotate(90deg);
    -o-transform: rotate(90deg);
    writing-mode: tb-rl;

    position: fixed ;
    background: rgb(133, 185, 116); /*rgb(126, 172, 255);*/
    border-radius: 10px 10px 0px 0px;
    top : 395px;
    left : -86px;
    width : 200px;
    height : 40px;
    border: 1px solid rgb(119, 155, 250);
    b ox-shadow: 0 0 120px rgba(255, 255, 253, 0.65);
    font-size: 18px;
    font-family: arial;
    cursor: default;
     }
</style>     


		<div  id="divMainPage">
                     
                     <a style=" position:absolute; left:800px; top:40px; text-decoration: none; color:white;" target="_blank" href="space_galery1.html" title="">Инструменты создания<br> данного сайта...</a>             
                     <div  id="img_logo"> </div>
                      
                       <div id="divNavigation" >
				<ul>                                                                                                                                                     
                                       <!-- <li> <img src="img/help1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageHelp" href="#">Связь</a> </li>   <div id="separatop"></div>   -->
                                        <li> <img src="img/key.png" style="position:relative; top:11px; left:5px;" align="left"/>                      <a id="mainPageAdmin" href="#" >На сайте</a>  	 </li> 	<div id="separatop"></div>
					<li> <img src="img/nastr1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageSettings" href="#">Настройки</a>	 </li> 	<div id="separatop"></div>
					<li> <img src="img/user.png" style="position:relative; top:11px; left:5px; padding-right:5px;" align="left"/> <a id="mainPagePrivateOffice" href="#">Личный кабинет</a></li> 	<div id="separatop"></div>
					<li> <img src="img/library1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageLibrary" href="#">Тесты</a>    </li>  <div id="separatop"></div>
                                        <li> <img src="img/mail1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageContact" href="#">Сообщения</a> </li>   <div id="separatop"></div>
					<li> <img src="img/exit1.png" style="position:relative; top:11px; left:5px; padding-right:7px;" align="left"/> <a id="mainPageExitSession" href="#">Выход</a>	     </li>
				</ul>
			</div>
                     
                     
      	
	<script>

$(function() {  /*Базовая для меню UI*/
$( "#menu" ).menu();
});
	
$( "#menu" ).menu({ icons: { submenu: "ui-icon ui-icon-play" } }); 

$("#mainPageLibrary").click(function() {
$("#men").effect("fade", { mode: "show", direction: "horizontal" }, 400); /*blind*/
 });
 /*divMainPage*/
 $("#men").mouseover(function() {
 $("#men").effect("fade", { mode: "hide", direction: "horizontal" }, 400); /*blind*/
     });
 
</script>

<style>

.ui-menu { 
  width: 210px;  /* ширина меню*/
  font-size:12px;  
  background: rgb(95, 92, 92);
 box-shadow: 0 0 120px rgba(255, 255, 255, 0.3); 
  }
  
  
#men{
ou tline : 1px solid black;
top : 250px;
left: 15px;
position : absolute;
color: white; 
width:250px; 
height:280px; 
dis play:none;
 

}

#men ul{
font-size:16px; 
ba ckground: url(img/stall1.jpg);
bac kground-size: 100% 100% contain; 
 background-image: url(../img/body-bg1.jpg);

border : 1px solid lightblue;

}

#men ul li a {
color: black;
fo nt-weight : bold;
padding-top : 7px;
padding-bottom : 7px;
font-family: arial;
fo nt-size: 17px;
color: rgb(99, 145, 156); /*white;*/
}
#img_menu{
margin-top:12px; 
margin-right:10px; 
margin-left:7px;  

}

</style>

<div id="men" style="" >
	<ul id="menu" style="">  
 
  <!-- target="_blank" -->  
  <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> О ВУЗе </a>
        <ul>
                    <li>    <a href="#"> Структура </a>   </li>  			
  	                <li>	<a href="#">Новости </a>	</li>  
					<li>	<a href="#">Структура </a>	</li> 
					<li>	<a href="#">Факультеты</a>		</li>
					<li>	<a href="#">Ученый совет</a>	</li> 
					<li>	<a href="#">Руководство</a>	</li> 
					<li>	<a href="#">Преподаватели и сотрудники</a>	</li> 
					<li>	<a href="#">Телефонный справочник</a>	</li> 
					<li>	<a href="#">История</a>		</li> 
					<li>	<a href="#">Устав</a>		</li> 
					<li>	<a href="#">Библиотека</a>	</li> 
					<li>	<a href="#">Контакты</a>	</li> 
					<li>	<a href="#">Полезные ссылки</a>	</li> 
        </ul> 
	</li>

  <li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Абитуриентам </a>
        <ul>
                    <li> <a href="#">Почему выбирают именно нас?</a> </li>
					<li> <a href="#">Приемная комиссия</a>	</li>
					<li> <a href="#">Направления, специальности, экзамены</a>					</li>
					<li> <a href="#">Стоимость обучения</a> 				</li>
					<li> <a href="#">Бланки и образцы документов</a>	</li>
					<li> <a href="#">Правила приема</a>	</li>
					<li> <a href="#">Где работают выпускники</a> 	</li>
					<li> <a href="#">Отзывы выпускников</a>			</li>
					<li> <a href="#">Фото-экскурсия</a>			</li>
        </ul> 
	</li>
	
	<li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Студентам </a>
        <ul>
                     <li> 	<a href="#">Расписание занятий</a>		</li>
					<li>	<a href="#">Учебные дисциплины</a>		</li>
					<li>	<a href="#">Учебные планы</a>		</li>
					<li>	<a href="#">Спортивная жизнь</a>	</li>
					<li>	<a href="#">Правила перевода</a>	</li>
					<li>	<a href="#">Трудоустройство</a>		</li>
					<li>	<a href="#">Учебные материалы</a>	</li>
					<li>	<a href="#">Студенческие новости</a>	</li>
        </ul> 
	</li>
	
	<li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Научная деятельность </a>
        <ul>
            <li>	<a href="#">Список конференций</a>		</li>
					<li>	<a href="#">Публикации</a>	
        </ul> 
	</li>
	
	<li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Выпускникам </a>
        <ul>
                    <li>	<a href="#">Ассоциация выпускников</a>		</li>
					<li>	<a href="#">Форумы</a>			</li>
					<li>	<a href="#">Выпускники в соцсетях</a>		</li>
					<li>	<a href="#">Отзывы выпускников</a>		</li>
					<li>	<a href="#">Семинары и тренинги</a>		</li>
        </ul> 
	</li>
	
		<li> <img id="img_menu" src="img/ar2.png" align="left" /> <a href="#"> Дополнительное образование </a>
        <ul>
                    <li>	<a href="#">Бланки и образцы документов</a>			</li>
					<li>	<a href="#">Карьерный рост</a>			</li>
					<li>	<a href="#">Направления, специальности, экзамены</a>		</li>
					<li>	<a href="#">Информация о дополнительном образовании, цены</a>				</li>
					<li>	<a href="#">Правила приема</a>		</li>
					<li>    <a href="#">Где работают выпускники</a>	</li>
        </ul> 

	</li>	
        
        <li> <img id="img_menu" src="img/ar2.png" align="left" /><a href="#">  Документы </a>
        <ul>
                    <li>	<a href="#">Шаблоны документов</a>		</li>
					<li>	<a href="#">Нормативно-правовые акты</a>	</li>
        </ul> 
	</li>
	
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
                     



                     
<!--  
			<div  id="divLeftNavigator">		   	         
				
				<br><br> <hr> <br>
				<span> 1. О ВУЗе </span>
				<ul>
					<li>	Новости		</li>
					<li>	<a href="#" name="fir">Структура </a>	</li>
					<li>	<a href="#">Факультеты</a>		</li>
					<li>	<a href="#">Ученый совет</a>	</li>
					<li> 	Руководство		</li>
					<li>	<a href="#">Преподаватели и сотрудники</a>	</li>
					<li>	<a href="#">Телефонный справочник</a>	</li>
					<li>	<a href="#">История</a>		</li>
					<li>	<a href="#">Устав</a>		</li>
					<li>	<a href="#">Библиотека</a>	</li>
					<li>	<a href="#">Контакты</a>	</li>
					<li>	<a href="#">Полезные ссылки</a>	</li>
				</ul>
				<hr>
				<br>
				<span>2. Абитуриентам </span>
				<ul>
					<li> <a href="#">Почему выбирают именно нас?</a> </li>
					<li> <a href="#">Приемная комиссия</a>	</li>
					<li> <a href="#">Направления, специальности, экзамены</a>					</li>
					<li> <a href="#">Стоимость обучения</a> 				</li>
					<li> <a href="#">Бланки и образцы документов</a>	</li>
					<li> <a href="#">Правила приема</a>	</li>
					<li> <a href="#">Где работают выпускники</a> 	</li>
					<li> <a href="#">Отзывы выпускников</a>			</li>
					<li> <a href="#">Фото-экскурсия</a>			</li>
				</ul>
				<hr>
				<br>
				<span> 3. Студентам </span>
				<ul>
					<li> 	<a href="#">Расписание занятий</a>		</li>
					<li>	<a href="#">Учебные дисциплины</a>		</li>
					<li>	<a href="#">Учебные планы</a>		</li>
					<li>	<a href="#">Спортивная жизнь</a>	</li>
					<li>	<a href="#">Правила перевода</a>	</li>
					<li>	<a href="#">Трудоустройство</a>		</li>
					<li>	<a href="#">Учебные материалы</a>	</li>
					<li>	<a href="#">Студенческие новости</a>	</li>
				</ul>
				<hr>
				<br>
				<span>4. Научная деятельность </span>
				<ul>
					<li>	<a href="#">Список конференций</a>		</li>
					<li>	<a href="#">Публикации</a>			</li>
				</ul>
				<hr>
				<br>
				<span> 5. Выпускникам </span>
				<ul>
					<li>	<a href="#">Ассоциация выпускников</a>		</li>
					<li>	<a href="#">Форумы</a>			</li>
					<li>	<a href="#">Выпускники в соцсетях</a>		</li>
					<li>	<a href="#">Отзывы выпускников</a>		</li>
					<li>	<a href="#">Семинары и тренинги</a>		</li>
				</ul>
				<hr>
				<br>
				<span> 6. Дополнительное образование</span>
				<ul>
					<li>	<a href="#">Бланки и образцы документов</a>			</li>
					<li>	<a href="#">Карьерный рост</a>			</li>
					<li>	<a href="#">Направления, специальности, экзамены</a>		</li>
					<li>	<a href="#">Информация о дополнительном образовании, цены</a>				</li>
					<li>	<a href="#">Правила приема</a>		</li>
					<li>    <a href="#">Где работают выпускники</a>	</li>
				</ul>
				<hr>
				<br>
				<span> 7. Документы </span>
				<ul>
					<li>	<a href="#">Шаблоны документов</a>		</li>
					<li>	<a href="#">Нормативно-правовые акты</a>	</li>
				</ul>
				<hr>

				<br>
				<span> 8. Общение </span>
				<ul>
					<li>  <a href="#">Блоги</a>		</li>
					<li>  <a href="#">Форумы</a>		</li>
					<li>  <a href="#">Анкетирование</a>		</li>
				</ul>
				<hr>
				<br>

			</div>

			-->
                        
                        
                        
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
                     
                     
			</div>
			
		</div>
                <div style="width:1000px; height:120px;">
                </div>

                <style>
     #space_galery{
     display:none; 
     z-i ndex:10000;     
     }
     
</style>


<div id="space_galery" >
 
 <div class="example" id="gall" >
    <img src="images/pic1.jpg"><span>Picture 1 title<br>and description.</span> 
    <img src="images/pic2.jpg"><span>Picture 2 description.</span> 
    <img src="images/pic3.jpg"><span>Picture 3 description.</span> 
    <img src="images/pic4.jpg"><span>Picture 4 description.</span> 
    <img src="images/pic5.jpg"><span>Picture 5 description.</span> 
    <img src="images/pic6.jpg"><span>Picture 6 description.</span> 
<!--     <img src="images/pic7.jpg"><span>Picture 7 description.</span> 
    <img src="images/pic8.jpg"><span>Picture 8 description.</span> 
    <img src="images/pic9.jpg"><span>Picture 9 description.</span> 
    <img src="images/pic10.jpg"><span>Picture 10 description.</span> 
    <img src="images/pic11.jpg"><span>Picture 11 description.</span> 
    <img src="images/pic12.jpg"><span>Picture 12 description.</span> 
    <img src="images/pic13.jpg"><span>Picture 13 description.</span> 
    <img src="images/pic14.jpg"><span>Picture 14 description.</span> 
    <img src="images/pic15.jpg"><span>Picture 15 description.</span> 
 -->
	
</div>

 <div style="top:0;position:fixed;">
 <input type="button" value="Закрыть" style="padding:7px; margin:7px; background:yellow;" onClick=" "/>
<!-- <hr style="clear:both;" />
<h4>
    <a href="http://www.script-tutorials.com/3d-gallery-using-javascript/">back to original article page</a>
</h4>
 -->
</div> 

</div> 

                
		
        
        <% }
     %>

</body>
</html>

     