

$(document).ready(function() {  // });

 
//window.onload=function() { 

 $( "#divLogin" ).tabs({ hide: { effect: "shake", duration: 400 } });
//  $( "#divLogin" ).effect( "shake" );
//Попытка войти через  Куку
ajax_LoginForCookie(); 



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
     $('.sInput_Account').qtip({ 
         content: {
                     title: {
                     text: 'Информация:'
                            }//,
                     //text: 'Введите пароль, потому что это для вашей же безопастности! <br><a href="home.html" title="Главная страница">Home</a> '
                          },
                       //content: 'Stems are great for indicating' , // принудительно
                show: 'focus',
              //  show: {  delay: 1000  },	//задержка
     //                          show: {    effect: function(offset) {
     //                           $(this).slideDown(500); // "this" refers to the tooltip 
     //                           }
     //                           },	
            hide: 'blur',
             position: {
                 adjust: { x: 7 }, // принудительное смещение
                 my: 'left top',  // Position my top left...
                 at: 'center right' // at the bottom right of...
             },
                  style: {
              classes: 'qtip-rounded qtip-tipped' // цвет и стиль qtip-shadow qtip-green
                                 //width : 15 // ширина
             }
      });




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
			if (index==0) {  ajax_doDestroySession();    }
			}
		});  
    });    

//---- начинаем тест                
     $("#mainPageTest").click(function(){  
             $("#left").css("display","block") ;    
     });


//----- Кликаем на ссылку создания Аккаунта 
        $("#divLogin #linkRegister").click(function(){     
            $("#divLogin").hide("slow");       // прячем окно ВХОДА
            $("#divAccount").show("slow");     // показываем окно РЕГИСТРАЦИИ    //(window.location.href="http://localhost:8080/Business/main.jsp")
        });
        
       
//---- Кликаем на крестик зарытия Окна Регистрации           
        $("#btClose_Account").click(function(){     
            $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
            $("#divLogin").show("slow");                  // показываем окно ВХОДА
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
    
    
   
// ----- Кликаем на кнопку Входа на сайт
     $("#divLogin #btLogin").click(function(){  

//     // если пустой Логин или пароль
//         if  ( ($("#divLogin #sEmail").val() == "") | ($("#divLogin #sPassword").val() == "")  )  {        
//               dhtmlx.message({ type:"error", expire:3000, text:"Введите Е-Маил и пароль!" });  // выводим сообщение
//               off($("#divLogin #btLogin"),true);     // блокируем кнопку входа
//               setTimeout(function() {    off($("#divLogin #btLogin"),false);    }, 3000)  // через время разблокируем кнопку
//         }
//       else  // если Логин и Пароль заполнены, то:
//           {   
//                    off($("#divLogin #btLogin"),true); // блокируем кнопку входа
                                       //  $("body").css("cursor","wait") ;  // курсор мфши в ожидание
                    // Через 3 секунды делаем:
                    setTimeout(function() {   ajax_doLogin();  // отправляем запрос на вход
                                              off($("#divLogin #btLogin"),false);  // разблокируем  кнопку 
                                              //document.cookie = "name333333=Ser25";
                                            //  $.cookie("auth", "1234567_",{ expires: 5,  path: '/'    });
                                             
                                             // var s= $.cookie("name333333");
                                             // alert(s);
                                              }, 3000) 

                    dhtmlx.message({ type:"default", expire:3000, text:"<br> <img src='img/wait.gif'/> &nbsp; Ожидайте... <br><br>" });
         //  }
     });    
    


 


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
               
            if (o.sReturn_Account == "Учетная запись создана !") {
                       $("#divAccount").hide();                // прячем окно РЕГИСТРАЦИИ
                       $("#divLogin").show();                  // показываем окно ВХОДА
                      
                     dhtmlx.modalbox({ 
                             title: "Вход на сайт:" ,
                             text:"<br><b>Поздравляем! <br><br> Ваша учетная запись успешно создана, <br> желаете автоматически войти на сайт? </b><br><br>",
                             width:"350px",
                             position:"center",
                             buttons:["Да", "Нет"],
                             callback:function(index){
                                  if (index==0) {  // если нажата кнопка с индексом 0 (ДА)
                                         $("#divLogin #sEmail").val( $("#sEmail_Account").val() );
                                         $("#divLogin #sPassword").val($("#sPassword_Account").val()) ;
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
             sEmail : $("#divLogin #sEmail").val(),
             sPassword: $("#divLogin #sPassword").val()
               // bBlocked: false,
            // sCookie: str 
           };

 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:/*false*/true
      ,success:function(o) {                                                                                 //    эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
        //  alert(o.sReturn);
            // alert(o.sReturnCookie); //
          if (o.sReturn == null)  alert('в ответ вернулся : null');
          if (o.sReturn == "Добро пожаловать на сайт!"){  
                
                // сохраняем/обновляем Куку при успешном входе
               $.cookie("auth", o.sReturnCookie, { expires: 2,  path: '/'    });
                
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

function ajax_LoginForCookie(){
    
     // Ищем Куку
     var str = $.cookie("auth");
     var oData= {   sDO: "theUserLogin",
                    sCookie: str   };
 
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:true
         ,success:function(o) {                                                                       // эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                //  alert(o.sReturnCookie); 
                  
                   
                 // alert(o.sReturnCookie);                                                               
//                 if (o.sReturn != null)    {                //alert(o.sReturn);
//                     window.location.href="/index.jsp"  ;   // открываем/обновляем главную стр. 
//                 }
            
         }, error:function(o,s) { alert("Произошла ошибка--!!--theSendCoockie "+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) { return data;}
         });
}


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
