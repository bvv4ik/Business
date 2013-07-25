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
  Integer count = (Integer) session.getAttribute("count");
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

        <link rel="stylesheet" type="text/css" href="css/index.css"/>

        <script type="text/javascript" src='js/message/codebase/message.js'></script>
	
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_default.css" title="Default"/>
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_growl_dark.css" title="Growl - dark"/>
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_growl_shiny.css" title="Growl - shiny"/>
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_solid.css" title="Solid"/>
	<link rel="stylesheet" type="text/css" href="js/message/codebase/themes/message_skyblue.css" title="SkyBlue"/>
        
     	<script type="text/javascript" src="js/qTip2/jquery.qtip.js"> </script>
	<link rel="stylesheet" type="text/css" href="js/qTip2/jquery.qtip.css"/>

            <title> Главная страница </title>
    </head>
    <body>


        
       <%   if (oEmail == null) // если в сессии отсутствует запись "sEmail" рисуем только формы создания аккаунта и входа
                 //  <h1> < %=oLogin% > </h1>    //  <h1> < %=value1% > </h1>    // if(value.toString().isEmpty())
       { %> 
            

        
<!-- ------------------------ ГЛАВНАЯ форма------------------------------------- -->
<div id="divFon" >     
      
  <%=count %>   
  <div id="divError">  </div>

 <!-- ------------------------ ВХОД форма ------------------------------------- -->
           <div id="divLogin" >
                <br>                             <!-- value="ser111@ss.ss" -->
               <input  class="inputs" id="sEmail"   type="text"  placeholder="Е-Маил..."  maxlength="25" />   <br> <br>
               <input  class="inputs" id="sPassword" type="Password" placeholder="Пароль..."  maxlength="25" />  <br> <br>
               <input  class="allButt" id="btLogin" type="button" value="Вход" />  
               <a id="linkRegister" href ="#" >Зарегистрироватся </a>   <br> <br>  <!--   http://localhost:8080/Business/register.jsp   -->
           </div>
 

<!-- --------------------------- АККАУНТ форма -------------------------------autocomplete="off" -->
       <div id="divAccount"  >
	 
            <div id="divHeader_Account">Создание учетной записи:     <!-- style="visibility: hidden" --> 
     		 <img id="btClose_Account" src="img/krest.jpg"   border="1"   title="Закрыть"  />
            </div>  <br>  
		    <!--  <span title="">*</span>-->
		   <input id="sEmail_Account" class="inp_Account" placeholder="Ваш Е-Маил..." title='Поле обязательное к заполнению. Введите название вашего Е-Маил, который станет уникальным Логином для входа на этот сайт. Данное поле обязательно для заполнения. <br>Так же ознакомьтесь: <a href="http://ru.wikipedia.org/wiki/Пароль" target="_blank" title="">Безопастность пароля</a>' style="color: #6495ED" type="text" value=""  maxlength="25" >                      
                   <input id="sPassword_Account" class="inp_Account" placeholder="Пароль" title="Поле обязательное к заполнению. Пароль должен содержать не менее 10 и не более 25 символов латинского алфавита и цифр (a-z, A-Z, 0-9). Для безопастности очень желательно, чтобы пароль включал в себя маленькие латинские буквы, но так же заглавные буквы и цифры. Пример безопасного пароля:  <b>Ivan33bn81T</b> , <br>Примеры НЕбесопасных паролей: <b>qwerty</b>, <b>123</b>, <b>7654321</b>. <br>Не экономьте несколько символов на своей безопасности! )) " style="color: #6495ED"  type="password" value=""  maxlength="25" >   
                   <input id="sPassword2_Account" class="inp_Account" placeholder="Пароль (повторно)" title="Поле обязательное к заполнению. <br> Защита от невнимательности! :)" style="color: #6495ED"  type="password" value=""  maxlength="25" >   <br>
		   <input id="sFirstName_Account" class="inp_Account" placeholder="Имя" title="Не обязательно, но не поленитесь представится :)" style="color: #6495ED" type="text" value="" autocomplete="off"  maxlength="25" > <br> 
		   <input id="sLastName_Account" class="inp_Account" placeholder="Фамилия" title="Не обязательно." style="color: #6495ED"  type="text" value="" autocomplete="off" maxlength="25">   <br>  <br>
                   <input class="allButt" id="btReg"  type="button" value="Создать" />             
            </div>       <!-- <a class="tooltip" href="#">Tooltip<span> Вот такая подсказочка получилась :).</span></a> -->
      </div>
          <!-- <h1>    Добро пожаловать <    %=oLogin%>  <    %=oLastName%> <    %=oFirstName%> <   %=oSureName%>    </h1> !-->

		  
<!--    <div id="progressBar_Account" >     <div></div>          </div>  <br>
<style>
#progressBar_Account {
  background-color: #1E90FF;   border-radius: 5px;   padding: 2px;  width: 253px;
}

#progressBar_Account div {
   width: 1%;    background-color: lightblue;   height: 23px;   border-radius: 3px;
}
</style>     -->
  
  
        <% } else  // иначе если есть сессия рисуем главную страницу     
        { %>    

<!--          < %=oEmail%>        -->

<!--           Добро пожаловать на главную страницу! -->


		<div  id="divMainPage">

			<div  id="divLeftNavigator">

			   <div  id="img_logo"> </div>	          <!--    <img id="img_logo" src="img/logo4.png" >  -->
				
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

			<div id="divNavigation" >
				<ul>
					<li> <a href="#">Главная</a>       </li>  <div id="separatop"></div>
					<li> <a href="#">Новости</a>	     </li>  <div id="separatop"></div>
					<li> <a href="#">Библиотека</a>    </li>  <div id="separatop"></div>
					<li> <a href="#">Настройки</a>	 </li> 	<div id="separatop"></div>
					<li> <a href="#">О нас...</a>  	 </li> 	<div id="separatop"></div>
					<li> <a href="#">Контакты</a>	     </li> 	<div id="separatop"></div>
					<li> <a href="#">Личный кабинет</a></li> 	<div id="separatop"></div>
					<li> <a id="mainPageExitSession" href="#">Выход</a>	     </li>
				</ul>
			</div>

			<div id="main_pict">
				<img src="img/main2.jpg"  alt="apelsin">
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
			 
                              </div>
			</div>
			
		</div>

		
        
        <% }
     %>

</body>
</html>

     