//$(document).ready(function() { 
// });

window.onload=function() { 

// ---------------  При загрузке прячем форму Аккаунта:    
    $("#divAccount").hide();
  //  $("#divError").hide(); //-------------------------



$("#divError").click(function(){
   
   //alert("dfsdfsd");
  // dhtmlx.alert("Текст сообщения.");
  dhtmlx.message.position = "bottom";
  dhtmlx.message({ type:"error", expire:20000, text:"Вы нажали кнопку <br> для сообщения со стилем! кликните, чтобы закрыть." })
  dhtmlx.message({ type:"error", expire:1000, text:"Вы нажали кнопку <br> для сообщения со стилем2!" })
  
  //setTimeout(function() { alert('2 секунды') }, 2000) //зажержка выполнения
  
});

//---------------- сдвигаем логотип при наведении    
    $("#img_logo").mouseover(function(){ $(this).css("left","13px")  })        // var s = $("#img_logo").css("left");     // alert("s");
    .mouseout(function(){   $(this).css("left","15px")   });   // сдвигаем логотип при наведении 

$("#img_logo").click(function(){
   ajax_getAllSession();
});
    
//---------------- Удаляем сессию пользователя
    $("#mainPageExitSession").click(function(){    
        if (confirm("Хотите отключится?")) {
            doSend2("theDestroySession");//alert("Привет!")
        } else {
        // alert("Вы нажали кнопку отмена")
        }
    });    

             
//-------------------- Кликаем на ссылку создания Аккаунта 
        $("#divLogin #linkRegister").click(function(){     
            $("#divLogin").hide("slow");       // прячем окно ВХОДА
            $("#divAccount").show("slow");     // показываем окно РЕГИСТРАЦИИ
                 //  $("#divLogin").fadeOut(500, function () {  //  $("#divAccount").fadeIn(500);   //   });
                //(window.location.href="http://localhost:8080/Business/main.jsp")
        });
        
        
       
//-------------------- Кликаем на крестик зарытия Окна Регистрации           
        $("#btClose_Account").click(function(){     
            $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
            $("#divLogin").show("slow");                  // показываем окно ВХОДА
        });
     
        
// ------------------ Кликаем на кнопку создания Аккаунта
        $("#divAccount #btReg").click(function(){     
            if ($("#sEmail_Account").val() == "")
                alert("Введите Е-маил!")
            else 
                if(!IsValidateEmail($("#sEmail_Account").val())){
                alert("Введите правильный E-Mail!");
            }
        else
            if ($("#sPassword_Account").val() == "")
                alert("Введите Пароль!")
        else
            if ($("#sPassword2_Account").val() == "")
                alert("Введите Пароль повторно!")
        else
            if ($("#sPassword_Account").val() != $("#sPassword2_Account").val())
                alert("Введенные пароли не совпадают!")
        else{
            doSend("theCreateAccount");      // отправляем на сервер запрос о создании Аккаунта 
            //document.location.href = "http://www.site.ru";   //document.formReg_CreateAccount.submit();
            }
    });
    
    
// ------------------ Кликаем на кнопку Входа на сайт
$("#divLogin #btLogin").click(function(){    
    doSend1("theUserLogin");
});    
    


 


// AJAX запрос1  -- создаем Аккаунт

function doSend(nameDO){         //var oData={sDO:"doIt",sName:"MyName",sParam3:"MyParam3",sParam:sParam, sParams:oGet($(".MyParams"))};
var oData= {   sDO_Account: nameDO,  
             sEmail_Account : $("#sEmail_Account").val(),
             sPassword_Account: $("#sPassword_Account").val(),
             sPassword2_Account: $("#sPassword2_Account").val(),
             sLastName_Account : $("#sLastName_Account").val(),
             sFirstName_Account : $("#sFirstName_Account").val()   
     };

 
 $.ajax({type:"POST",dataType:"json",url:"/CreateAccount",data:oData,async:/*false*/true
      ,success:function(o) {                                            //учти, что эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                        //----$(".MyParams").val(""); // очищаем строки     //o.sReturn
               alert(o.sReturn_Account);
            if (o.sReturn_Account == "Учетная запись создана !") {
            $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
            $("#divLogin").show("slow");                  // показываем окно ВХОДА
            
          //  $("#divLogin #sEmail").val( $("#sEmail_Account").val() ); // автоматически вводим Емаил 
           }

         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) {/*alert("это окно будет появляться и при ошибке и при успехе")*/;return data;}
         });
}




// AJAX запрос 2  --- проверка логина и пароля для входа на сайт
function doSend1(nameDO){         
var oData= {   sDO: nameDO,  
             sEmail : $("#divLogin #sEmail").val(),
             sPassword: $("#divLogin #sPassword").val()
     };
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:/*false*/true
      ,success:function(o) {                                                    //учти, что эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                                //----$(".MyParams").val(""); // очищаем строки     //o.sReturn
            alert(o.sReturn);
           // alert(o.sSes);
            if (o.sReturn == "Добро пожаловать на сайт!"){   (window.location.href="/index.jsp")     }  

         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) {/*alert("это окно будет появляться и при ошибке и при успехе")*/;return data;}
         });
}




// AJAX запрос 3    // отправляем запрос на сервер и Удаляем сессию
function doSend2(sNameDo){         
var oData= {   sDO: sNameDo  
             //sEmail : $("#divLogin #sEmail").val(),
             //sPassword: $("#divLogin #sPassword").val()
     };
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:/*false*/true
      ,success:function(o) {                                                    //учти, что эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                                //----$(".MyParams").val(""); // очищаем строки     //o.sReturn
            alert(o.sReturn);
            window.location.href="/index.jsp"  ;
            //if (o.sReturn == "Добро пожаловать на сайт!"){   (window.location.href="/index.jsp")     }  
            

         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) {/*alert("это окно будет появляться и при ошибке и при успехе")*/;return data;}
         });
}



// AJAX запрос 4    // отправляем запрос на сервер и Удаляем сессию
function ajax_getAllSession(){         
var oData= {   sDO: "theGetAllSessionList"  
             //sEmail : $("#divLogin #sEmail").val(),
             //sPassword: $("#divLogin #sPassword").val()
     };
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:/*false*/true
      ,success:function(o) {                                                    //учти, что эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                                //----$(".MyParams").val(""); // очищаем строки     //o.sReturn
            if (o.sReturn != null)
                alert(o.sReturn);
           
            //if (o.sReturn == "Добро пожаловать на сайт!"){   (window.location.href="/index.jsp")     }  

         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) {/*alert("это окно будет появляться и при ошибке и при успехе")*/;return data;}
         });
}




 // ----------------- Функция блокировка выделения страницы
 //должно быть в самом низу скрипта, иначе блокирует некоторые элементы
//    function disableSelection(target){ 
//        if (typeof target.onselectstart!="undefined") //IE route 
//            target.onselectstart=function(){return false} 
//        else if (typeof target.style.MozUserSelect!="undefined") //Firefox route 
//            target.style.MozUserSelect="none" 
//        else //All other route (ie: Opera) 
//            target.onmousedown=function(){return false} 
//        target.style.cursor = "default" ;
//    }; 
//    
//    // ----------------- Блокируем выделение мышью Форм
//    var somediv=document.getElementById("divFon") ;
//    disableSelection(somediv); 
    
    };


  // ----------------- Функция проверки валидности Е-маила
    function IsValidateEmail(email) {
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,6})$/;
        return reg.test(email);
    }


    // $(document).ready(function(){
    //  $("#divFon").append("<strong>Hello</strong>");
    // $("#divFon").append(s);   //alert(s.valueOf());
    //});
    

