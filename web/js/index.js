var oText={
    sFirstAuthInfo_Custom:"Траляля!"
    ,sLastAuthInfo_Custom:"Труляля!"
    ,sLastAuthInfo_Custom1:"Труляля!"
};

//
//
////window.onload=function() { 
//".oAuth .sLogin"
//
        //любая собственная функция, если этот диалог понадобится отображать многократно з разных мест.()
        function showTitleFirstLogin() {//my()
            info(".oAuthForm .sLogin"//"#imgFirstLoginHelp"
                ,".oDialogs .oFirstAuthorizationInfo"//"• Уважаемый <span class'sFIO'></span>! Если Вы уже <span style='color: green;'>ЗАРЕГИСТРИРОВАННЫ</span> - просто введите свой Емаил, пароль и входите. <br><br> • Если Вы <span style='color: orange;'>НОВЫЙ</span>  пользователь - в тех же полях введите Емаил, придумайте пароль и входите, <u>система зарегистрирует вас автоматически!</u> <br><br> <div id='Title111' style=' display:block; color:grey;' > • Данное уведомление исчезнет после Вашего первого успешного Входа на сайт с данного компьютера/браузера. </div> <br> "
                ,{sCustom:oText.sFirstAuthInfo_Custom} //null//{sFIO:"Петя Васечкин",sStatus:"Руководитель"}//oParams
                ,0//5
                ,null/*function(sName,sButtom){
                    if(sName=="onClickButton"){//"onBeforeOpen,onAfterOpen,onClickButton,onBeforeClose,onAfterClose"
                        //что-то делаем при сработовшем событии "на закрытии,открытии,клике и т.д."
                        //
                        //doAny();
                    }
                }*/
                ,"TopRight"
            );
        }
         
         //Функция-обертка, для заполнения и отображения любого сообщения, с вызовом стандартного для системы плагина
        function info(oThere,oThat,oParams,nAutohideSeconds,doEvent,sPosition) {
            if(typeof oThere=="string"){
                oThere=$(oThere).parent();
            }
            if(typeof oThat=="string"){
                oThat=$(oThat);
            }
            if(oParams!=null){
                $.each(oParams, function(sName,sValue){
                    //alert("sName="+sName+",sValue="+sValue+",$(oThere).find(.sName)"+$(oThere).find("."+sName).length);
                    $(oThere).find("."+sName).text(sValue);
                });
            }
            if(nAutohideSeconds==null){
                nAutohideSeconds=0;
            }
            
            infoCustom(oThere,{  text: 'Информация:', button: 'Close'},oThat,sPosition,nAutohideSeconds,doEvent);
        }
         
         //Вызов стандартного для системы плагина, для отображения любого сообщения
        function infoCustom(oThereHTML, oTitle, oWhatHTML,sPosition,nAutohideSeconds,doEvent) {
           if(sPosition==null){
               sPosition="DownLeft";
           }
           var oPosition=
               sPosition=="DownLeft"?
                   {
                    adjust: { x: 17, y: 13 }, // принудительное смещение
                    my: 'left top',  // Position my top left...//TODO: НАСТРОИТЬ!
                    at: 'left down' // at the bottom right of...
                   }
               :sPosition=="DownRight"?
                   {//TODO: НАСТРОИТЬ!
                    adjust: { x: 17, y: 13 }, // принудительное смещение
                    my: 'left top',  // Position my top left...
                    at: 'right top' // at the bottom right of...
                   }
               :sPosition=="TopLeft"?
                   {//TODO: НАСТРОИТЬ!
                    adjust: { x: 17, y: 13 }, // принудительное смещение
                    my: 'left top',  // Position my top left...
                    at: 'right top' // at the bottom right of...
                   }
               :sPosition=="TopRight"?
                   {
                    adjust: { x: 17, y: 13 }, // принудительное смещение
                    my: 'left top',  // Position my top left...
                    at: 'right top' // at the bottom right of...
                   }
               :
                   null
               ;
           if(doEvent!=null){
               //doEvent("onOpen");
               doEvent("onClickButton","IThinking");
           }

           $(oThereHTML).qtip({ 
               content:{
                           title: oTitle
                           ,text: $(oWhatHTML).html()
                       },
                      show: {
                            ready: true
                        },
               hide: {
                   event: 'mouseon'
                }
               ,position:
                   oPosition
                   ,style: {
                       classes: 'qtip-rounded qtip-tipped' // цвет и стиль qtip-shadow qtip-green //,width : 15 // ширина
                     }
            });
        }
      


// Если отключены Куки - закрываем Вкладку страницы
if (!navigator.cookieEnabled) {
   alert('Внимание, отключены Сookie в вашем баузере, без Сookie работа с этим сайтом невозможна!');
   window.close();
}

       //$("#sEmail").attr("title","Пароль"); 
       //$("#sEmail").removeAttr("title"); //  

// функции включения-отключения подсказок
function delTitle1() {  
     $.cookie('last', 'x', { expires: 7777,  path: '/' });  
   //  $(".oAuthForm").qtip({  show: false    });     
     
}





 

$(document).ready(function() {  // });

// Если отключены Куки закрываем окно Входа - регистрации
 if (!navigator.cookieEnabled) {     $( ".oAuthForm").hide();     }  //return;

$("#sEmail").focus();


 
// обнуляем Куки для тестов ....
//$.cookie('last', null);
//$.cookie('auth', null);



var str = $.cookie("last");     // Ищем куку last
if (str == null){        // если ее нет то меняем название кнопки "Вход" на "Вход / Регистрация"
        //  $( "#btLogin" ).val("Вход / Регистрация");     // не обязательно
        // $( "#imgFirstLoginHelp" ).removeAttr("hidden");      // подсказка
        // showFirstLoginTitle();

}



           // Открываем табы с эффектом замедления
         $( ".oAuthForm" ).tabs({ hide: { effect: "shake", duration: 400 } });


// Попытка войти через  Куку
// ajax_LoginForCookie(); 


 
 $("#divAccount").hide(); // При загрузке прячем форму Аккаунта:    
dhtmlx.message.position = "bottom"; // устанавливаем позицию messages
//dhtmlx.setActiveStyleSheet("SkyBlue"); // меняем тему messages


// ---- страницы в разработке
$("#mainPageContact, #mainPagePrivateOffice, #mainPageSettings").click( function (){
         dhtmlx.message({ expire:4000, text:" <img style='padding-top:10px;' src='img/worked.jpg'/> Извините, данная страница находится в стадии разработки... <br><br>" })
});
          
 
//----- Форма Контактов... счетчик символов до 1000
	$("#textarea_contact").keyup(function () {
	       var s = $("#textarea_contact").val().length;  
	       $("#blockMessageLength").html("(Осталось символов: " + "<b>"+ -(s-1000)+ "</b> )" );
        });


//---- Скрываем форму контактов
	$("#btClose_contact").click( function (){
               $("#form_contact").css("display","none");  
               $("#FON_contact").css("display","none");  
	});         
//---- Показываем форму контактов       
        $("#divHelpLeft").click( function (){     
                $("#form_contact").css("display","block");  
                $("#FON_contact").css("display","block");
        });
        


// ---- подсказки qtip
//     $('.sInput_Account').qtip({ 
//         content: { title: {  text: 'Информация:' }//,
//                     //text: 'Введите пароль, потому что это для вашей же безопастности! <br><a href="home.html" title="Главная страница">Home</a> '
//                          },      //content: 'Stems are great for indicating' , // принудительно
//                show: 'focus',  //  show: {  delay: 1000  },	//задержка
//     //                           show: {    effect: function(offset) {
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
//              classes: 'qtip-rounded qtip-tipped' // цвет и стиль qtip-shadow qtip-green //,width : 15 // ширина
//             }
//      });

// ---- подсказки qtip 1




   
      
      
//$("#sEmail").focus();


if ($.cookie("last") == null){   // если куки нет, значит пользователь здесь впервые .....   
        $("#imgFirstLoginHelp").removeAttr("hidden"); // делаем мигающую иконку видимой
        showTitleFirstLogin();     // вызываем сообщение
}


       
 
 //---- подсказки qtip
//     $('.oAuthForm').qtip({ 
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



//---- сдвигаем логотип при наведении    
     $("#img_logo").mouseover(function(){ $(this).css("left","13px")  }).mouseout(function(){   $(this).css("left","15px")   });   


// ---- загрузка данных о сессии
     $("#mainPageAdmin").click(function(){ 
          ajax_getAllSession();
     });
    
//----Удаляем сессию пользователя
    $("#mainPageExitSession, #exitSite").click(function(){    
     dhtmlx.modalbox({  title:"Сообщение:" ,
			text:" <br>Вы действительно хотите выйти?<br><br>",
			width:"350px", height:"165px", position:"center",
			buttons:["Выйти!", "&nbsp;&nbsp;Остаться...&nbsp;&nbsp;"],
			callback:function(index){
			if (index==0) {  
                             ajax_doDestroySession();   // Удаляем сессию 
                            // $.cookie('auth', null);   // Удаляем куку  
                             }
			}
		});  
    });    

 $("#exitSite2").click(function(){    
   ajax_doDestroySession();   // Удаляем сессию 
   $.cookie('auth', null);   // Удаляем куку  
  // window.location.href="/index.jsp"; // переходим на страницу входа
});    


//---- начинаем тест                
     $("#mainPageTest").click(function(){  
             $("#left").css("display","block") ;    
     });


//----- Кликаем на ссылку создания Аккаунта 
        $(".oAuthForm #linkRegister").click(function(){     
            $(".oAuthForm").hide("slow");       // прячем окно ВХОДА
            $("#divAccount").show("slow");     // показываем окно РЕГИСТРАЦИИ    //(window.location.href="http://localhost:8080/Business/main.jsp")
        });
        
       
//---- Кликаем на крестик зарытия Окна Регистрации           
        $("#btClose_Account").click(function(){     
            $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
            $(".oAuthForm").show("slow");                  // показываем окно ВХОДА
        });
     
        
// ------- Кликаем на кнопку для отправки запроса об создания Аккаунта
        $("#divAccount #btReg").click(function(){     
            if ($("#sEmail_Account").val() == "")
                           dhtmlx.message({ type:"error", expire:4000, text:" Введите Е-Маил! <br>" })
            else if(!IsValidateEmail(  $("#sEmail_Account").val()  ))
                           dhtmlx.message({ type:"error", expire:4000, text:" Введите правильный E-Mail! <br>" })
            else if ($("#sPassword_Account").val() == "")
                           dhtmlx.message({ type:"error", expire:4000, text:" Введите Пароль! <br>" })
            else if ($("#sPassword2_Account").val() == "")
                           dhtmlx.message({ type:"error", expire:4000, text:" Введите Пароль повторно! <br>" })
            else if ($("#sPassword_Account").val() != $("#sPassword2_Account").val())
                           dhtmlx.message({ type:"error", expire:4000, text:" Пароли не совпадают! <br>" })
                 else{  doSend("theCreateAccount");      // отправляем на сервер запрос о создании Аккаунта 
                                                        //document.formReg_CreateAccount.submit();
                }
        });
    
    
    //var t1 = new Date();
    //var res = (t.getMinutes()+" "+t.getSeconds()); 
         
         
   //var mail = $("#sEmail").val();
   
         
     var oTimer = null; // создаем таймер
    
     $("#sEmail").keyup(function(){     // .oAuthForm
          
          if  (  ( !IsValidateEmail( $("#sEmail").val() ))   ){ // иначе прячим доп. поля
              
                 //return;
          }
          
            $( '#imgLoading' ).fadeIn( 500 );
    
              if (oTimer != null) {  clearTimeout(oTimer);  } // обнуляем таймер при новом нажатии мыши
       
               oTimer =   window.setTimeout(function() { 
                    $( '#imgLoading' ).fadeOut( 1 );
                    if  (  ( IsValidateEmail( $("#sEmail").val() )) /*& ($("#sPassword").val().length > 2 )*/  ){
                         ajax_userExists();  
                     }  
                     else{
                            $( "#sName, #sLastName, #sINN, #checkAgreement, #sTextAgreement,#brLogin" ).attr("hidden","hidden");    
                            $( ".oAuthForm" ).css("height","190");
                            $( "#btLogin" ).val("Вход").css("width","100px"); 
                         }
                
               oTimer  = null;  // при выполнении функции в таймере, включаем работу таймеа
          }, 1500);
             
             
});
    


//if (oTimer != null) {  clearTimeout(oTimer);  } // обнуляем таймер при новом нажатии мыши
//       
//          oTimer =   window.setTimeout(function() {    
//               if  (  ( IsValidateEmail( $("#sEmail").val() )) /*& ($("#sPassword").val().length > 2 )*/  ){
//                    ajax_userExists();  
//               }    
//               oTimer  = null;  // при выполнении функции в таймере, включаем работу таймеа
//          }, 2000);
//             
//             
//$( '#imgLoading' ).fadeIn( 500 );
   //var t = new Date();
        //  alert(t.getMinutes()+" "+t.getSeconds());         
       
    
   
// ----- Кликаем на кнопку Входа на сайт
     $(".oAuthForm #btLogin").click(function(){  

//     // если пустой Логин или пароль
//         if  ( ($(".oAuthForm #sEmail").val() == "") | ($(".oAuthForm #sPassword").val() == "")  )  {        
//               dhtmlx.message({ type:"error", expire:3000, text:"Введите Е-Маил и пароль!" });  // выводим сообщение
//               off($(".oAuthForm #btLogin"),true);     // блокируем кнопку входа
//               setTimeout(function() {    off($(".oAuthForm #btLogin"),false);    }, 3000)  // через время разблокируем кнопку
//         }
//       else  // если Логин и Пароль заполнены, то:
//           {   
//                    off($(".oAuthForm #btLogin"),true); // блокируем кнопку входа
                                       //  $("body").css("cursor","wait") ;  // курсор мфши в ожидание
                     
   if(!IsValidateEmail(  $("#sEmail").val()  ))
        {
                           dhtmlx.message({ type:"error", expire:4000, text:" Введите правильный E-Mail! <br>" }) 
                         return;                    
         }           
                    // Через 3 секунды делаем:
                    setTimeout(function() {   ajax_doLogin();  // отправляем запрос на вход
                                              off($(".oAuthForm #btLogin"),false);  // разблокируем  кнопку 

                                              }, 3000) 

                    dhtmlx.message({ type:"default", expire:3000, text:"<br> <img src='img/wait.gif'/> &nbsp; Ожидайте... <br><br>" });
         //  }
     });    
    

function  ajax_userExists(){         

$( '#imgLoading' ).fadeIn( 1 );
//,   #divBlack
//   $( '#sEmail,   #sPassword' ).attr("readonly","readonly");  

  var oData= {    sDO: "theUserExists",
                  sEmail: $("#sEmail").val()   };
 
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:true
         ,success:function(o) {
              
             var s = o.sReturnExists;   // alert(s);
            
          if (s != "YES") { // Если Емайла нет в базе  // показываем доп. поля 
                              $( "#sName, #sLastName, #sINN, #checkAgreement, #sTextAgreement, #brLogin" ).removeAttr("hidden");    
                              $( ".oAuthForm" ).css("height","375");
                              //$( "#btLogin" ).val("Вход (Авторегистрация)" );
                              $( "#btLogin" ).val("Вход (Авторегистрация)").css("width","230px");
                           } 
                               else{    // иначе прячим доп. поля
                                     $( "#sName, #sLastName, #sINN, #checkAgreement, #sTextAgreement,#brLogin" ).attr("hidden","hidden");    
                                     $( ".oAuthForm" ).css("height","190");
                                     $( "#btLogin" ).val("Вход").css("width","100px");
                                   }   
                       
                       $( "#imgLoading" ).fadeOut( 500 );
                      // $( "#divBlack" ).fadeOut( 500 );//.removeAttr("hidden");
                      // $( "#sEmail, #sPassword" ).removeAttr("readonly");
                     
                    
         }, error:function(o,s) { alert("Произошла ошибка-- ajax_userExists()--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) { return  data;}
         });
         
}
 


// AJAX  -------- создаем Аккаунт  ----------------

function doSend(doCreateAccount){         //var oData={sDO:"doIt",sName:"MyName",sParam3:"MyParam3",sParam:sParam, sParams:oGet($(".MyParams"))};

var oData= {   sDO_Account: doCreateAccount,  
             sEmail_Account : $("#sEmail_Account").val(),
             sPassword_Account: $("#sPassword_Account").val(),
             sPassword2_Account: $("#sPassword2_Account").val(),
             sLastName_Account : $("#sLastName_Account").val(),
             sFirstName_Account : $("#sFirstName_Account").val()   
     };
 
 $.ajax({type:"POST",dataType:"json",url:"/CreateAccount",data:oData,async:/*false*/true
      ,success:function(o) {                                                       //эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                                    //----$(".MyParams").val(""); // очищаем строки     //o.sReturn        
               
            if (o.sReturn_Account == "Добро пожаловать на сайт!") { // "Учетная запись создана !"
                       $("#divAccount").hide();                // прячем окно РЕГИСТРАЦИИ
                       $(".oAuthForm").show();                  // показываем окно ВХОДА
                      
                     dhtmlx.modalbox({ 
                             title: "Вход на сайт:" ,
                             text:"<br><b>Поздравляем! <br><br> Ваша учетная запись успешно создана, <br> желаете автоматически войти на сайт? </b><br><br>",
                             width:"350px",
                             position:"center",
                             buttons:["Да", "Нет"],
                             callback:function(index){
                                  if (index==0) {  // если нажата кнопка с индексом 0 (ДА)
                                         $(".oAuthForm #sEmail").val( $("#sEmail_Account").val() );
                                         $(".oAuthForm #sPassword").val($("#sPassword_Account").val()) ;
                                                                   //  http://localhost:8080/CreateAccount?sDO=theCreateAccount&sEmail=ser412@d3f.dd&sPassword=12&sPassword2=12&sLastName=ser1&sFirstName=bel1
                                         ajax_doLogin();
                                         }
                                  $(".sInput_Account").val("");     // очищаем строки регистрации 
                                  }
                     });
                  
           }
           else  // если учетная Запись не создана (выводим собщение "Логин занят" пришедшее с сервера или другое сообщение)
                {
                     dhtmlx.message({ type:"error", expire:4000, text:" "+o.sReturn_Account });
                }
           
           
         }, error:function(o,s) { alert("Произошла ошибка--doCreateAccount!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) { return data;}
         });
}





// AJAX   ----------- ВХОД на сайт  ----------------
var countEnter = 5;      // по умолчанию

function ajax_doLogin(){  

if (countEnter == -1)   return; // блокировка отправки сообщений

var oData= { sDO: "theUserLogin",
             sEmail : $(".oAuthForm #sEmail").val(),
             sPassword: $(".oAuthForm #sPassword").val()
            // sCookie: str 
           };

 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:/*false*/true
      ,success:function(o) {                                                                                 //    эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
        //  alert(o.sReturn);
            // alert(o.sReturnCookie); //
          if (o.sReturn == null)  alert('в ответ вернулся : null');
          if (o.sReturn == "Добро пожаловать на сайт!"){  
                
                // сохраняем временную Куку при успешном входе
               $.cookie("auth", o.sReturnCookie, { expires: 2,  path: '/'    });
               
                // сохраняем/обновляем постоянную Куку при успешном входе, чтобы не высвечивались подсказки
               var date = new Date(); date = date.toString().replace(/\ /g,"_"); // замена всех пробелов на подчеркивание
               $.cookie("last", date, { expires: 7777,  path: '/'    });
               //alert ($.cookie("last"));
               
               dhtmlx.message({ type:"default", expire:1000, text:"<br>  &nbsp; Добро пожаловать на сайт! <br><br>" });
               setInterval(function() { (window.location.href="/index.jsp");  }, 1000);
          }  
            else {
                dhtmlx.message({ type:"error", expire:5000, text:o.sReturn + "<br><br>( Осталось попыток: "+countEnter+" )"  })
                countEnter--;   // неудачная попытка, минус 1
                     if (countEnter == -1)  {    
                           dhtmlx.message({ type:"error", expire:10000, text:"Отправка запросов заблокирована <br>на 30 секунд!" })
                           setInterval(function() { countEnter = 5 }, 30000)
                     }
            }
           
         }, error:function(o,s) { alert("Произошла ошибка ajax_doLogin() --  "+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) { // выполняется в любом случае
                          //  $("body").css("cursor","default") ;  // курсор мыши делаем нормальный
              return data;}
         });
}




// AJAX   --------  запрос на сервер и Удаляем сессию  -----------------------
function  ajax_doDestroySession(){         
   var oData= {   sDO: "theDestroySession"       };
 
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:true
         ,success:function(o) {                                                              //эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                         window.location.href="/index.jsp"  ; // обновляем главную стр.
         }, error:function(o,s) { alert("Произошла ошибка-- ajax_doDestroySession()--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) { return data;}
         });
}



// AJAX  ----------------  список всех сессий   ---------------------------
function ajax_getAllSession(){         
   var oData= {   sDO: "theGetAllSessionList"   };
 
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:true
         ,success:function(o) {                                                                       // эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                                 
                 if (o.sReturn != null)    {  //alert(o.sReturn);
                      $("#divAllSessinList table").html(o.sReturn);
                      $("#divAllSessinList").css("display","block");
                      $("#FON_contact").css("display","block");
                 }
            
         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) { return data;}
         });
}
//
//function ajax_LoginForCookie(sCookie){
//    
//     var oData= {   sDO: "theLoginForCookie",
//                    sCookieLogin: sCookie   };
// 
// $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:true
//         ,success:function(o) {                                                                       // эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
//                //  alert(o.sReturnCookie);
//                alert(o.sReturn);
//                  if (o.sReturn == "Добро пожаловать на сайт!"){
//                       
//                       // просто обновляем время временной Куки при входе по этой куке, на сервере не меняем
//                       $.cookie("auth", o.sReturnCookie, { expires: 2,  path: '/'    });
//                       
//                       // обновляем страницу и входим
//                        dhtmlx.message({ type:"default", expire:1000, text:"<br>  &nbsp; Добро пожаловать на сайт! <br><br>" });
//                        setInterval(function() { (window.location.href="/index.jsp");  }, 1000);
//                  }
//                   
//                 // alert(o.sReturnCookie);                                                               
////                 if (o.sReturn != null)    {                //alert(o.sReturn);
////                     window.location.href="/index.jsp"  ;   // открываем/обновляем главную стр. 
////                 }
//            
//         }, error:function(o,s) { alert("Произошла ошибка--!!--theSendCoockie "+o.status+":"+o.statusText+" ("+o.responseText+")");  }
//         ,dataFilter:function(data, type) { return data;}
//         });
//}


//
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




    }); // конец             //////window.onload







function ajax_LoginForCookie(sCookie){
    
     var oData= {   sDO: "theLoginForCookie",
                    sCookieLogin: sCookie   };
 
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:true
         ,success:function(o) {                                                                       // эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                //  alert(o.sReturnCookie);
              //  alert(o.sReturn);
                  if (o.sReturn == "Добро пожаловать на сайт!"){
                       
                       //alert(111);
                       // просто обновляем время временной Куки (на компе ) при входе по этой куке, на сервере не меняем
                       $.cookie("auth", o.sReturnCookie, { expires: 2,  path: '/'    });
                       
                      // window.location.href="/index.jsp";
                       // обновляем страницу и входим
                         
                        
                        $( '#imgLoading' ).fadeIn( 500 );
                        dhtmlx.message({ type:"default", expire:1000, text:"<br>  &nbsp; Добро пожаловать на сайт! <br><br>" });
                        setInterval(function() { (window.location.href="/index.jsp");  }, 3000);
                  }
                   
                 // alert(o.sReturnCookie);                                                               
//                 if (o.sReturn != null)    {                //alert(o.sReturn);
//                     window.location.href="/index.jsp"  ;   // открываем/обновляем главную стр. 
//                 }
            
         }, error:function(o,s) { alert("Произошла ошибка--!!--theSendCoockie "+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) { return data;}
         });
}







// Дополниельные функции
  // ----------------- Функция проверки валидности Е-маила
    function IsValidateEmail(email) {
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,6})$/;
        return reg.test(email);
    }

    
    
  // полезные функции
        function see(s,b){if(b==null)b=$(s).css('display')!='none';else if(b)$(s).show();else $(s).hide();return b;}
        function off(s,b){b=b!=null?b:$(s).attr('disabled');$(s).attr('disabled',b);return b;}
        function on(s,b,o){var bZ=!off(s);b=b==null?bZ:b;if(bZ!=b){off(s,!b);if(o!=null)o(b);}return b;}
        function oGet(oAt){var s="";oAt.each(function(n,o){if (o.type!="button"&(o.type!="radio"||o.checked)){s=s+(n>0?",":"")+"\""+o.id+"\":\""+(o.type=="checkbox"?o.checked:o.value.replace(/\"/g,"&quot;"))+"\"";}});return "{"+s+"}";}

        function oSet(oTo){$.each(oTo,function(key,val){if(key!=''){var s="#"+key;
        if($(s).attr("type")=="checkbox"){$(s).attr("checked",eval(val));}
        else if($(s).attr("type")=="radio"){$('[id='+key+']:[value='+val+']').attr('checked',true);}
        else {$(s+":[type!=button]").val(val.replace(/<br\/>/g,"<br/>\n").replace(/<br>/g,"<br>\n").replace(/<\/p>/g,"</p>\n").replace(/&quot;/g,"\""));}
        }});  }





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
                            //   $(".oAuthForm #sEmail").val( $("#sEmail_Account").val() );
                            //   $(".oAuthForm #sPassword").val($("#sPassword_Account").val()) ;
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
