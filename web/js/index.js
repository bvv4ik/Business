
//    Схема размещения JS кода по типу, быстрый переход:  CTRL + Клик мышью
  
/*       • ФУНКЦИИ AJAX     */      
/*       • Документ Ready   */      goto_ready();
/*          - АВТОЗАГРУЗКА  */    
/*          - СОБЫТИЯ       */     
/*       • ОБщие ФУНКЦИИ    */      goto_func();











//                                  AJAX
// -----------------------------------------------------------------------------


// ------------------------------ Проверка Емайла ------------------------------
//     Проверяем зарегистрирован ли юзер или только хочет регистироватся
function  ajax_userExists(){
     $( '#imgLoading' ).fadeIn(1);     // показываем прогресс бар
     var oData= {
          sDO: "theUserExists"
          ,sEmail: $("#sEmail").val()
     };
     $.ajax({
          type:"POST"
          ,dataType:"json"
          ,url:"/Login"
          ,data:oData
          ,async:true
          ,success:function(o) {

               var s = o.sReturn;  
               if (s == "NoEmailExists") {      // Если Емайла нет в базе  // показываем доп. поля для "нового пользователя" 
                    $( "#sName, #sLastName, #sINN, #checkAgreement, #sTextAgreement, #brLogin" ).removeAttr("hidden");    
                    $( "#divLogin" ).css("height","375");
                    $( "#btLogin" ).val("Вход (Авторегистрация)").css("width","230px");

               } else if  (s == "EmailExists")  {         // иначе прячим доп. поля, т.к такой Емайл зареген
                    $( "#sName, #sLastName, #sINN, #checkAgreement, #sTextAgreement,#brLogin" ).attr("hidden","hidden");    
                    $( "#divLogin" ).css("height","190");
                    $( "#btLogin" ).val("Вход").css("width","100px");
               }   

               $( "#imgLoading" ).fadeOut( 500 );  // прячем прогресс бар при любом ответе

          }
          ,error:function(o,s) {
               alert("Произошла ошибка-- ajax_userExists()--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");
          }
          ,dataFilter:function(data, type) {
               return  data;
          }
     });         
}



// --------------------- ВХОД на сайт  ----------------------

function ajax_doLogin(){  
                  
     $("#btLogin").addClass("disabled").attr("disabled", "disabled");  // блокируем кнопку входа

     var oData= {
          sDO: "theUserLogin"
          ,sEmail : $("#divLogin #sEmail").val()
          ,sPassword: $("#divLogin #sPassword").val()
     };

     $.ajax({
          type:"POST"
          ,dataType:"json"
          ,url:"/Login"
          ,data:oData
          ,async:true
          ,success:function(o) {                                                                                 //    эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 

 //alert(o.sReturn);

               if (o.sReturn == "Добро пожаловать на сайт!"){  
                    // это сообщение на случай долгого открытия страницы из-за загруженности сервера
                    dhtmlx.message({
                         type:"default", 
                         expire:9000, 
                         text:"<br> <img src='img/wait.gif'/> &nbsp; Ожидайте... <br><br>"
                    });
                    // сохраняем "временную" Куку при успешном Входе
                    $.cookie("auth", o.sReturnCookie, {
                         expires: 2,  
                         path: '/'/*, secure: true */
                    });
                    // сохраняем/обновляем постоянную Куку при успешном входе, чтобы больше не высвечивалась подсказка
                    var date = new Date();                            //date = date.toString().replace(/\ /g,"_");    // замена всех пробелов на подчеркивание
                    $.cookie("last", date, {
                         expires: 7777,  
                         path: '/'         //, secure: true   Работает только с HTTPs
                    });
                    // так нужно обновлять страницу, чтобы произошла запись в Автокомплит и обновление страницы
                    $("#btSubmit").click();  
               // window.location.href="/index.jsp";  //dhtmlx.message({ type:"default", expire:1000, text:"<br>  &nbsp; Добро пожаловать на сайт! <br><br>" });

               } else if (o.sReturn == "FailPassword!"){              
                    dhtmlx.message({
                         type:"info", 
                         expire:7000, 
                         text: " <span style='color:red; font-weight:bold;'>Неверный пароль!</span> "+
                    "<br><br> • <a href='#' onClick='javascript: ajax_sendEmail();' > Зайти без пароля (Отправить мне ссылку на Емайл) </a> " +
                    "<br><br> • <a href='#' onClick='javascript: alert('потом');' > Зайти без пароля с подтверждением кода (Отправить мне SMS) </a> " +
                    "<br><br> • <a href='#' onClick='javascript: alert('потом');' > Сменить пароль (отправить СМС на номер 1234567890) </a> " +
                    "<br><br> "
                    }) 
               } else if (o.sReturn == "FailLimitRequest!"){  // сделано более 5 запросов в течении 2 минут, доступ заблокирован.
                    dhtmlx.message({
                         type:"info", 
                         expire:5000, 
                         text: " Вы исчерпали число допустимых запросов! Ваш IP Заблокирован на 2 минуты! " 
                    }) 
               }
               
               alert(o.sReturnCount);
               if (o.sReturnCount == "FailLimitRequest!"){ 
               }
               
          }, 
          error:function(o,s) {
               alert("Произошла ошибка ajax_doLogin() --  "+o.status+":"+o.statusText+" ("+o.responseText+")");
          }
          , dataFilter:function(data, type) {   // выполняется в любом случае
               setTimeout(function() {   $("#btLogin").removeClass("disabled").removeAttr("disabled");    }, 1000);  // РАЗблокируем кнопку входа через 1000 мс
               return data;
          }    
     });
}




// -------------------  Удаляем сессию пользователя  -----------------------

function  ajax_doDestroySession(){         
     var oData= {
          sDO: "theDestroySession"
     };
     $.ajax({
          type:"POST"
          ,dataType:"json"
          ,url:"/Login"
          ,data:oData
          ,async:true
          ,success:function(o) {                                                              //эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
               if (o.sReturn == "Destroyed!"){   
                    window.location.href="/index.jsp"  ; // обновляем главную стр.
               }
          }
          ,error:function(o,s) {
               alert("Произошла ошибка-- ajax_doDestroySession()--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");
          }
          ,dataFilter:function(data, type) {  
               return data;
          }
     });
}



// ---------------------  Получаем список всех сессий   --------------------------

function ajax_getAllSession(){
     var oData= {
          sDO: "theGetAllSessionList"
     };
     $.ajax({
          type:"POST"
          ,dataType:"json"
          ,url:"/Login"
          ,data:oData
          ,async:true
          ,success:function(o) {                                                                       // эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
               if (o.sReturn != "-none-")    { 
                    $("#divAllSessinList table").html(o.sReturn);
                    $("#divAllSessinList").css("display","block");
                    $("#FON_contact").css("display","block");
               }
          }
          ,error:function(o,s) {
               alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");
          }
          ,dataFilter:function(data, type) {
               return data;
          }
     });
}



// ----------------  Отправляем ссулку для входа на Емаил юзеру  -----------------------

function ajax_sendEmail(){         
     $( "#imgLoading" ).fadeIn( 300 );  // Показываем прогресс бар - процесс отправки
     var oData= {
          sDO: "theSendEmail",
          sEmail : $("#divLogin #sEmail").val()  // лучше здесь создать глобальную переменную ... потом доделать!!!! 
     };
     $.ajax({
          type:"POST"
          ,dataType:"json"
          ,url:"/Login"
          ,data:oData
          ,async:true
          ,success:function(o) {                                                                       // эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 

               if (o.sReturn == "MailSendOk!") {
                    //alert("Письмо со ссылкой Вам было отправлено!"); 
                    dhtmlx.message({
                         type:"info", 
                         expire:3000, 
                         text: "<br> Письмо со ссылкой успешно отправлено на Ваш Емаил!<br><br>"
                    })
                    $( "#imgLoading" ).fadeOut( 300 );  // прячем прогресс бар
               }

          }, error:function(o,s) {
               alert("Произошла ошибка-- theSendEmail !!"+o.status+":"+o.statusText+" ("+o.responseText+")");
          }
          , dataFilter:function(data, type) {
               return data;
          }
     });
}


// ------------------ Попытка зайти через Куку находящуюся на компе  ----------------------

function ajax_LoginForCookie(sCookie){
     var oData= {
          sDO: "theLoginForCookie"
          ,sCookieLogin: sCookie
     };
     $.ajax({
          type:"POST"
          ,dataType:"json"
          ,url:"/Login"
          ,data:oData
          ,async:true
          ,success:function(o) {                                                                       // эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 

               if (o.sReturn == "Добро пожаловать на сайт!"){
                    // при входе по куке просто обновляем ее время (на компе), а на сервере не меняем!
                    $.cookie("auth", o.sReturnCookie, {
                         expires: 2,  
                         path: '/' /*, secure: true*/
                    });
                    // обновляем страницу
                    //window.location.href="/index.jsp" 
                    // так нужно обновлять страницу, чтобы произошла запись в Автокомплит и обновление страницы
                    $("#btSubmit").click();   
                    // но знак # в ХЭШЕ при удалении ХЕША не убирается, и страницы не открывается, 
                    // поэтому чтобы загрузился index.jsp нужно дополнительно выполнить:
                    window.location.href="/index.jsp" 

               } else if (o.sReturn == "Ложная кука!"){
                    dhtmlx.message({
                         type:"error", 
                         expire:7000, 
                         text:"Ай-ай-ай! <br>Не хорошо пытатся войти по ложным данным !"
                    });
               }
          }, error:function(o,s) {
               alert("Произошла ошибка--!!--theLoginForCookie "+o.status+":"+o.statusText+" ("+o.responseText+")");
          }
          , dataFilter:function(data, type) {
               return data;
          }
     });
}



//                          КОГДА ДОКУМЕНТ СФОРМИРОВАН 
// -----------------------------------------------------------------------------
function goto_ready(){}; 

$(function(){                                                                     //$(document).ready(function() {    //window.onload=function() { 
//--------------------------  АВТОЗАГРУЗКА -------------------------------------

     //----- по умолчанию кнопка Входа затемнена и отключена 
     $("#btLogin").addClass("disabled").attr("disabled", "disabled")           
          
     //-----
     $("#sEmail").focus();

     //----- Если отключены Куки закрываем окно Входа - регистрации и закрываем Вкладку страницы
     if (!navigator.cookieEnabled) {    
          $( "#divLogin").hide();  
          alert('Внимание, отключены Сookie в вашем баузере, без Сookie работа с этим сайтом невозможна!');
          window.close();
     }  

     //----- пробуем зайти по ссылке из почты
     LoginViaLink(); 

     //-----   проверка посещения пользователем сайта
     if ($.cookie("last") == null){   // если куки нет, значит пользователь/браузер/компьютер на сайте впервые .....   
          //   $("#imgFirstLoginHelp").removeAttr("hidden"); // делаем мигающую иконку с вопросом видимой
          showTitleFirstLogin();     // вызываем сообщение
     }

     //----- ( ДЛЯ ТЕСТОВ ) обнуляем Куки  ....
     //$.cookie('last', null);
     //$.cookie('auth', null);

     //---- ( ДЛЯ ТЕСТОВ ) создаем ложную Куку для проверки записи Алерта в лог log4j 
     //    $.cookie("auth", "ffffgdf", { expires: 2,  path: '/'/*, secure: true */ });

     //---- Открываем Табы с эффектом замедления
     $( "#divLogin" ).tabs({
          hide: {
               effect: "shake", 
               duration: 400
          }
     });
//---- устанавливаем параметры messages 
dhtmlx.message.position = "bottom"; 
//dhtmlx.setActiveStyleSheet("SkyBlue"); // меняем тему messages





function goto_event(){};
     // ------------------------------ СОБЫТИЯ -------------------------------------

     //----- Форма Контактов... ограничение набора до 1000 символов 
     $("#textarea_contact").keyup(function () {
          var s = $("#textarea_contact").val().length;  
          $("#blockMessageLength").html("(Осталось символов: " + "<b>"+ -(s-1000)+ "</b> )" );
     });
     //---- Скрываем форму контактов
     $("#btClose_contact").click( function (){  
          $("#form_contact, #FON_contact").css("display","none");    
     });         
     //---- Показываем форму контактов       
     $("#divHelpLeft").click( function (){     
          $("#form_contact, #FON_contact").css("display","block");  
     });
     //---- Сдвигаем логотип при наведении мышки    
     $("#img_logo").mouseover(function(){
          $(this).css("left","13px")
     }).mouseout(function(){
          $(this).css("left","15px")
     });   
//     // ---- Загрузка данных о сессии в таблицу
//     $("#mainPageAdmin").click(function(){
//          ajax_getAllSession();
//     });

     //---- Удаляем сессию пользователя и куку
     $("#exitSite").click(function(){    
          ajax_doDestroySession();       // Удаляем сессию 
          $.cookie('auth', null);        // Удаляем куку  	
     });    

     //---- ( ДЛЯ ТЕСТОВ ) Удаляем токо сессию пользователя 
     $("#exitSite2").click(function(){    
          ajax_doDestroySession();       // Удаляем сессию 
     });    

     //---- Начинаем тестирование по предмету                
     $("#mainPageTest").click(function(){    
          $("#left").css("display","block") ;   
     });     
               //var t1 = new Date();     //var res = (t.getMinutes()+" "+t.getSeconds()); 
               //  & ($("#sPassword").val().length > 2 )      // проверка длинны пароля


     // !!надо будет еще сделать, чтобы отлавливались только ограниченные клавиши (без стрелок вверх, вниз)
     //----  Отправляем запрос на проверку существования Емайла когда уже введен синтаксически 
     //      верный адрес и пользователь не нажимает кнопки больше 2 секунд.
     var oTimer = null;   // создаем глобальный таймер
     $("#sEmail").keyup(function(){    
          if  (  ( IsValidateEmail( $("#sEmail").val() ))   ){   // если Емайл синтаксически валидный то
               $( '#imgLoading' ).fadeIn( 300 );            // показывем Прогресс бар
          }
          if (oTimer != null) {
               clearTimeout(oTimer);
          }        // обнуляем предыдущ. таймер при новом нажатии кнопки клавиатуры

          oTimer =  window.setTimeout(function() {         // в момент выполнения задачи таймера, через N- секунд длаем:
               $( '#imgLoading' ).fadeOut( 1 );                 // прячем прогресс бар

               if  (  ( IsValidateEmail( $("#sEmail").val() ))   ){
                    ajax_userExists();  
               } else {                // Если Емайл не валидный, то показываем только 2 поля
                    $( "#sName, #sLastName, #sINN, #checkAgreement, #sTextAgreement, #brLogin" ).attr("hidden","hidden");    
                    $( "#divLogin" ).css("height","190");
                    $( "#btLogin" ).val("Вход").css("width","100px"); 
               }
               oTimer = null;  // при выполнении функции в таймере, включаем работу таймеа
          }, 1500);
     });


     // ----- Кликаем на кнопку Входа на сайт
     $("#divLogin #btLogin").click(function(){  
          // проверяем чтобы Логин или пароль не пустой были
          if  ( ($("#divLogin #sEmail").val() == "") | ($("#divLogin #sPassword").val() == "")  )  {        
               dhtmlx.message({
                    type:"error",  
                    expire:3000, 
                    text:"Введите Е-Маил и пароль!"
               });    // выводим сообщение             
               return; 
          } else  if(!IsValidateEmail(  $("#sEmail").val()  )) {
               dhtmlx.message({
                    type:"error", 
                    expire:4000,  
                    text:" Введите корректный E-Mail! <br>"
               }) 
               return;                    
          }           

          ajax_doLogin();  // отправляем запрос на вход                            
                            // dhtmlx.message({ type:"default", expire:9000, text:"<br> <img src='img/wait.gif'/> &nbsp; Ожидайте... <br><br>" });
     });    


            
     //---- при откускании кнопок (в том числе интера) на полях формы для Входа     
     $(".sInput_Login").keyup(function(event) { 
              
          // проверяем поля на пустоту и включен ли чекбокс
          if ( ($("#sEmail").val() == "") | ($("#sPassword").val() == "") | (!$("#checkAgreement").is(":checked" )) ) { 
               // если есть пустые поля или чекбокс не включен, значит отключаем кнопку
               $("#btLogin").addClass("disabled").attr("disabled", "disabled");
          }
          else {  // если поля заполнены и чекбокс включен то - // включаем кнопку
               $("#btLogin").removeClass("disabled").removeAttr("disabled");
               // если это была кнопка "интер" то программно кликаем по кнопке "входа"
               if ( (event.keyCode==13) /*& ($("#checkAgreement").is(":checked" )) */  ) {
 
                    $('#btLogin').click();  // кликаем на вход                                          
               }                     
          }
     });
      
           
      
     //----- в момент изменения состояния чекбокса "согласия на хранение данных" (включения - выключения)
     $("#checkAgreement").change(function() {
           
          // проверяем если все поля заполнены и чекбокс включен
          if ($("#checkAgreement").is(":checked" ) & ($("#sEmail").val() != "") & ($("#sPassword").val() != "")   ) {
               // включаем кнопку Входа
               $("#btLogin").removeClass("disabled").removeAttr("disabled");
  
          } else {   // отключаем кнопку Входа            
               $("#btLogin").addClass("disabled").attr("disabled", "disabled");
          }             
     });


  // кликаем по картинке Таба и открываем этот Таб иначе не откроется если кликнуть по картинке
  $("#im1").click(function() {   $( "#panelTabs" ).tabs( "option", "active", 0 );   });
  $("#im2").click(function() {   $( "#panelTabs" ).tabs( "option", "active", 1 );   });
  $("#im3").click(function() {   $( "#panelTabs" ).tabs( "option", "active", 2 );   });
  $("#im4").click(function() {   $( "#panelTabs" ).tabs( "option", "active", 3 );   });
  $("#im5").click(function() {   $( "#panelTabs" ).tabs( "option", "active", 4 );   });
  $("#im6").click(function() {   $( "#panelTabs" ).tabs( "option", "active", 5 );   });
   
  
     // анимация при открытии - скрытии таба                
  $( "#panelTabs" ).tabs({ show: { effect: "blind", duration: 200 } });

// ---------------------------- КОНЕЦ СОБЫТИЙ ----------------------------------
});  
// -------------------------- конец Document Ready -----------------------------      







// ---------------------------- ФУНКЦИИ ----------------------------------------
function goto_func(){};
 
// обязательная инициализация закладок Jquery
          $(function() {    $( "#panelTabs" ).tabs( );     });  
          $(function() {    $( "#subTabsMaterials" ).tabs();     });
          $(function() {    $( "#subTabsPrifile" ).tabs();     });
          $(function() {    $( "#subTabsStatistic" ).tabs();     });
          $(function() {    $( "#subTabsStructure" ).tabs();     });  
// --------------- Всплывающая подсказка ---------------------
function showTitleFirstLogin() {

     $("#divLogin").qtip({ 
          content: {
               title: {
                    text: 'Информация:'
               }  // , button: 'Close'
               ,
               text: "• Если Вы уже <span style='color: green;'>зарегистрированны</span> - просто введите свой Емаил, пароль и входите. <br><br> • Если Вы <span style='color: rgb(245, 114, 11);'>новый</span>  пользователь - в тех же полях введите Емаил, придумайте пароль и входите, <span style='color: rgb(245, 114, 11);'><b>система зарегистрирует вас автоматически!</b></span> <br><br> <div id='Title111' style=' display:block; color:grey;' > • Данное уведомление исчезнет после Вашего первого успешного Входа на сайт с данного компьютера/браузера. </div> <br> "
          },      //content: 'Stems are great for indicating' , // принудительно
          //show: 'focus',
          show: {
               ready: true
          },
          //  show: {  delay: 1000  },	//задержка
          //                           show: {    effect: function(offset) {
          //                           $(this).slideDown(500); // "this" refers to the tooltip 
          //                           }
          //                           },
          //hide: 'blur',
          hide: {
               event: false//'mouseout'
          },
          //              hide: {   
          //                    // event: 'unfocus'
          //                   //   event: 'click'
          //                  //target: $('#delTitle11').click(),
          //                  //event: 'unfocus',
          //                 event: 'blur'
          //                  //solo: true
          //                  //event: false
          //                },
          position: {
               adjust: {
                    x: 27, 
                    y: 7
               }, // принудительное смещение
               my: 'left top',  // Position my top left...
               at: 'right top' // at the bottom right of...
          },
          style: {
               classes: 'qtip-rounded qtip-tipped' // цвет и стиль qtip-shadow qtip-green //,width : 15 // ширина
          }
     });
}
      

    //--------- Функция замены символов/строк в строке на другие   ---------------
String.prototype.replaceAll = function(search, replace){
     return this.split(search).join(replace);
}
  

    // -----------  Попытка войти через ссылку в почтовом ящике ----------------
function LoginViaLink(){ 
     // http://localhost:8080/Login?sDO=theLoginForCookie&sCookieLogin=31%26eofrrqpcrgkshspqxmkserqihewgaxqeazdrfjmgfuqunpkanu
     // http://localhost:8080/#sDO=theLoginForCookie&sCookieLogin=31%26clorpqywosuzgdjsdxntpurutpmxuctirpexlnnczxbhcrxgto 
     var sHash=location.hash;  // Получаем ХЭШ
     // не очень надержная проверка, если ХЭШ будет с ошибками то может не распарсить правильно.
     if (sHash != "") { //если ХЭШ не пустой то 
          // превращаем ХЭШ в обьект // replaceAll("&","\", \"").
          var sHash = "{"+ sHash.replaceAll("#","\"").replaceAll("=","\":\"").replaceAll("%26","&") +"\"}"; 
          var oHash = JSON.parse(sHash);                            //alert(myobj.sCookieLogin); //alert(myobj.sDO); 
          // Если в КЭШЕ есть Кука, то пытаемся по ней войти
          if(oHash.sAuth!=null){   
               location.hash = "";
               ajax_LoginForCookie(oHash.sAuth);
               //"theLoginForCookie"
          }
     }
}



//---- Дополниельные функции

// ----------------- Функция проверки валидности Е-маила ----------------------  
function IsValidateEmail(email) {
     var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,6})$/;
     return reg.test(email);
}

//---- полезные функции от Вовы
        function see(s,b){if(b==null)b=$(s).css('display')!='none';else if(b)$(s).show();else $(s).hide();return b;}
        function off(s,b){b=b!=null?b:$(s).attr('disabled');$(s).attr('disabled',b);return b;}
        function on(s,b,o){var bZ=!off(s);b=b==null?bZ:b;if(bZ!=b){off(s,!b);if(o!=null)o(b);}return b;}
        function oGet(oAt){var s="";oAt.each(function(n,o){if (o.type!="button"&(o.type!="radio"||o.checked)){s=s+(n>0?",":"")+"\""+o.id+"\":\""+(o.type=="checkbox"?o.checked:o.value.replace(/\"/g,"&quot;"))+"\"";}});return "{"+s+"}";}

        function oSet(oTo){$.each(oTo,function(key,val){if(key!=''){var s="#"+key;
        if($(s).attr("type")=="checkbox"){$(s).attr("checked",eval(val));}
        else if($(s).attr("type")=="radio"){$('[id='+key+']:[value='+val+']').attr('checked',true);}
        else {$(s+":[type!=button]").val(val.replace(/<br\/>/g,"<br/>\n").replace(/<br>/g,"<br>\n").replace(/<\/p>/g,"</p>\n").replace(/&quot;/g,"\""));}
        }});  }






















//                              СВАЛКА СТАРОГО КОДА
//  ----------------------------------------------------------------------------


// ------ если кликаем на текст - то программно кликам на сам чекбокс.
// $("#textCheckAgreement").click(function() {    $("#checkAgreement").click();   });
    


// ----------------------------  Работа с ХЕШЕМ, примеры от Вовы
// 
//loadHash();
//var oHash={};
//
//        function loadHash(){
//            var asHash=location.hash.replace("#","").split("&");
//            // alert(asHash);
//            $(asHash).each(function(n,o){
//                if(o!=""&&o!=null){
//                    var as=o.split("=");
//                    var sName=as[0].trim();
//                    var sValue=as.length>1?as[1]:"";
//                    if(sName.length>0){oHash[sName]=sValue;}
//                }
//            });
//        }
//   
////а потом уже, из любого места юзать:
//        if(oHash.sDO!=null){
//              alert(oHash.sDO);
//           //alert(location.hash);         
//        }
//---------------------------------------------------------

//     window.onhashchange=onHashModified;   //привязываем эвент к нашей функции
//     function onHashModified(){
//              //делаем чето, когда произойдет изменение Хэша
//              //  alert(1);
//     }

/*if ("onhashchange" in window) {
         alert("The browser supports the hashchange event!");
     }
     function locationHashChanged() {
         if (location.hash === "#somecoolfeature") {
             somecoolfeature();
         }
     }
     window.onhashchange = locationHashChanged;*/





   // (ВРЕМЕННО) ---- страницы в разработке 
     //               $("#mainPageContact, #mainPagePrivateOffice, #mainPageSettings").click( function (){
     //                   dhtmlx.message({ expire:4000, text:" <img style='padding-top:10px;' src='img/worked.jpg'/> Извините, данная страница находится в стадии разработки... <br><br>" })
     //               });


/*     // плавное мигание объекта
     $.fn.wait = function(time, type) {
          time = time || 100;
          type = type || "fx";
          return this.queue(type, function() {
               var self = this;
               setTimeout(function() {
                    $(self).dequeue();
               }, time);
          });
     };
     // прекращаем минание при наведении
     var b = true;  $("#imgFirstLoginHelp").mouseover(function(){    b = false;   });
     
     function runIt() {
          if (b) { $("#imgFirstLoginHelp").wait().animate({"opacity": 0.3},1000).wait().animate({"opacity": 1},900,runIt);  }
     }
     runIt();
*/





/*   тестовое окно
dhtmlx.modalbox({ 
		text:"<div >"+ 
                
               '<input  id="sEmail"   type="text" value="" placeholder="Е-Маил..."  maxlength="25" />   <br> <br>' +
               '<input  id="sPassword" type="Password" value="" placeholder="Пароль..."  maxlength="25" />  <br> <br>' +
               '<input   id="btLogin1" onClick="alert(1);" type="button" value="Вход" />  ' +
               '<a id="linkRegister" href ="#" >Регистрация </a>   <br> <br><br><br><br><br><br><br><br><br><br><br><br><br> ' +
                "</div>",
			title:"Личный кабинет:&nbsp&nbsp&nbsp&nbsp   " ,
                        width:"550px",
                        height:"450px",
			position:"center",
			buttons:["Да"], //, "Нет" 
			callback:function(index){
			if (index==0) {  //
                            //   $("#divLogin #sEmail").val( $("#sEmail_Account").val() );
                            //   $("#divLogin #sPassword").val($("#sPassword_Account").val()) ;
                               alert("DA!");
                        }
                        if (index==1) { 
                        alert("net !");
                        }
				//dhtmlx.message("Button "+index+" was pressed")
                           //     $(".sInput_Account").val("");     // очищаем строки регистрации 
			}
		});
*/



// $(document).ready(function(){
    //  $("#divFon").append("<strong>Hello</strong>");
    // $("#divFon").append(s);   //alert(s.valueOf());
    //});
    
    
                 // var pos = $("#form_contact").position();
//	if (pos.left <= 0) {
//		$("#form_contact").css("left","250px");
//		pos.left = 250;
//		}
//		else{
//		pos.left = -150;
//		$("#form_contact").css("left","-150px");
//		}




// старое
////  ----------------- Функция блокировка выделения страницы
// //должно быть в самом низу скрипта, иначе блокирует некоторые элементы
//    function disableSelection(target){ 
//        if (typeof target.onselectstart!="undefined") //IE route 
//            target.onselectstart=function(){return false} 
//        else if (typeof target.style.MozUserSelect!="undefined") //Firefox route 
//            target.style.MozUserSelect="none" 
//        else //All other route (ie: Opera) 
//            target.onmousedown=function(){return false} 
//        target.style.cursor = "default" ;
//    }; 
//    // ----------------- Блокируем выделение мышью Форм
//    var somediv=document.getElementById("#form_contact") ;
//    disableSelection(somediv); 



// 
// Диалоговое окошко с кнопками
//  dhtmlx.modalbox({  title:"Сообщение:" ,
//			text:" <br>Вы действительно хотите выйти?<br><br>",
//			width:"350px", height:"165px", position:"center",
//			buttons:["Выйти!", "&nbsp;&nbsp;Остаться...&nbsp;&nbsp;"],
//			callback:function(index){
//			if (index==0) {  
//                              // ajax_doDestroySession();   // Удаляем сессию 
//                              // $.cookie('auth', null);   // Удаляем куку  
//                             }
//			}
//		});  



 //---- подсказки qtip
//     $('.sInput_Login').qtip({ 
//         content: {
//                     title: {
//                     text: 'Информация:'
//                            }//,
//                     //text: 'Введите пароль, потому что это для вашей же безопастности! <br><a href="home.html" title="Главная страница">Home</a> '
//                          },
//                       //content: 'Stems are great for indicating' , // принудительно
//                show: 'focus',
//                show: {
//                      ready: true
//                  },
//              //  show: {  delay: 1000  },	//задержка
//     //                          show: {    effect: function(offset) {
//     //                           $(this).slideDown(500); // "this" refers to the tooltip 
//     //                           }
//     //                           },	
//            hide: 'blur',
//             position: {
//                 adjust: { x: 7 }, // принудительное смещение
//                 my: 'left top',  // Position my top left...
//                 at: 'center right' // at the bottom right of...
//             },
//                  style: {
//              classes: 'qtip-rounded qtip-tipped' // цвет и стиль qtip-shadow qtip-green
//                                 //width : 15 // ширина
//             }
//      });


//----- Кликаем на ссылку создания Аккаунта 
//        $("#divLogin #linkRegister").click(function(){     
//            $("#divLogin").hide("slow");       // прячем окно ВХОДА
//            $("#divAccount").show("slow");     // показываем окно РЕГИСТРАЦИИ 
//        });
        
 //---- Кликаем на крестик зарытия Окна Регистрации           
//        $("#btClose_Account").click(function(){     
//            $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
//            $("#divLogin").show("slow");                  // показываем окно ВХОДА
//        });


     
// ------- Кликаем на кнопку для отправки запроса об создания Аккаунта
//        $("#divAccount #btReg").click(function(){     
//            if ($("#sEmail_Account").val() == "")
//                           dhtmlx.message({ type:"error", expire:4000, text:" Введите Е-Маил! <br>" })
//            else if(!IsValidateEmail(  $("#sEmail_Account").val()  ))
//                           dhtmlx.message({ type:"error", expire:4000, text:" Введите правильный E-Mail! <br>" })
//            else if ($("#sPassword_Account").val() == "")
//                           dhtmlx.message({ type:"error", expire:4000, text:" Введите Пароль! <br>" })
//            else if ($("#sPassword2_Account").val() == "")
//                           dhtmlx.message({ type:"error", expire:4000, text:" Введите Пароль повторно! <br>" })
//            else if ($("#sPassword_Account").val() != $("#sPassword2_Account").val())
//                           dhtmlx.message({ type:"error", expire:4000, text:" Пароли не совпадают! <br>" })
//                 else{  
//                      doSend("theCreateAccount");      // отправляем на сервер запрос о создании Аккаунта 
//                                                        //document.formReg_CreateAccount.submit();
//                }
//        });

//// AJAX  -------- создаем Аккаунт  ----------------
//
//function doSend(doCreateAccount){         //var oData={sDO:"doIt",sName:"MyName",sParam3:"MyParam3",sParam:sParam, sParams:oGet($(".MyParams"))};
//
//var oData= {   sDO_Account: doCreateAccount,  
//             sEmail_Account : $("#sEmail_Account").val(),
//             sPassword_Account: $("#sPassword_Account").val(),
//             sPassword2_Account: $("#sPassword2_Account").val(),
//             sLastName_Account : $("#sLastName_Account").val(),
//             sFirstName_Account : $("#sFirstName_Account").val()   
//     };
// 
// $.ajax({type:"POST",dataType:"json",url:"/CreateAccount",data:oData,async:/*false*/true
//      ,success:function(o) {                                                       //эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
//                                                                                    //----$(".MyParams").val(""); // очищаем строки     //o.sReturn        
//               
//            if (o.sReturn_Account == "Добро пожаловать на сайт!") { // "Учетная запись создана !"
//                       $("#divAccount").hide();                // прячем окно РЕГИСТРАЦИИ
//                       $("#divLogin").show();                  // показываем окно ВХОДА
//                      
//                     dhtmlx.modalbox({ 
//                             title: "Вход на сайт:" ,
//                             text:"<br><b>Поздравляем! <br><br> Ваша учетная запись успешно создана, <br> желаете автоматически войти на сайт? </b><br><br>",
//                             width:"350px",
//                             position:"center",
//                             buttons:["Да", "Нет"],
//                             callback:function(index){
//                                  if (index==0) {  // если нажата кнопка с индексом 0 (ДА)
//                                         $("#divLogin #sEmail").val( $("#sEmail_Account").val() );
//                                         $("#divLogin #sPassword").val($("#sPassword_Account").val()) ;
//                                                                   //  http://localhost:8080/CreateAccount?sDO=theCreateAccount&sEmail=ser412@d3f.dd&sPassword=12&sPassword2=12&sLastName=ser1&sFirstName=bel1
//                                         ajax_doLogin();
//                                         }
//                                  $(".sInput_Account").val("");     // очищаем строки регистрации 
//                                  }
//                     });
//                  
//           }
//           else  // если учетная Запись не создана (выводим собщение "Логин занят" пришедшее с сервера или другое сообщение)
//                {
//                     dhtmlx.message({ type:"error", expire:4000, text:" "+o.sReturn_Account });
//                }
//           
//           
//         }, error:function(o,s) { alert("Произошла ошибка--doCreateAccount!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
//         ,dataFilter:function(data, type) { return data;}
//         });
//}
