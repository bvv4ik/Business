

    
$(document).ready(function() { 
    
                // });
// ---------------  При загрузке прячем форму Аккаунта:
    $("#divAccount").hide();    
 

//window.onload=function() { 
    
    $("#img_logo").mouseover(function(){ // сдвигаем картинку    // var s = $("#img_logo").css("left");     // alert("s");
        $(this).css("left","13px")
    });
   

   $("#img_logo").mouseout(function(){  // сдвигаем картинку    //   var s = $("#img_logo").css("left");    alert(s);
        $(this).css("left","15px") 
   });

        // alert("!!1"); 
         
    // ----------------- Функция проверки валидности Е-маила
    function IsValidateEmail(email) {
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,6})$/;
        return reg.test(email);
    }
    
    // ----------------- Функция блокировка выделения страницы (1 вариант)
    function disableSelection(target){ 
        if (typeof target.onselectstart!="undefined") //IE route 
            target.onselectstart=function(){return false} 
        else if (typeof target.style.MozUserSelect!="undefined") //Firefox route 
            target.style.MozUserSelect="none" 
        else //All other route (ie: Opera) 
            target.onmousedown=function(){return false} 
        target.style.cursor = "default" ;
    }; 
    
    // ----------------- Блокируем выделение мышью Форм
    var somediv=document.getElementById("divFon") ;
    disableSelection(somediv); 
    
    
    // $(document).ready(function(){
    //  $("#divFon").append("<strong>Hello</strong>");
    // $("#divFon").append(s);   //alert(s.valueOf());
    //});
    
    
    
    
    
    //-------------------- после загрузка страницы
    
        
        $("#divLogin #linkRegister").click(function(){     // при клике на ссылку регистрации
            $("#divLogin").hide("slow");       // прячем окно ВХОДА
            $("#divAccount").show("slow");     // показываем окно РЕГИСТРАЦИИ
            
             //  $("#divLogin").fadeOut(500, function () {
             //  $("#divAccount").fadeIn(500);
             //   });
     
            
            //(window.location.href="http://localhost:8080/Business/main.jsp")
            // http://localhost:8080/Business/register.jsp 
        });
        
        
        

        $("#btClose_Account").click(function(){     // кликаем на крестик зарытия Окна Регистрации           
            $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
            $("#divLogin").show("slow");                  // показываем окно ВХОДА
     
     //$("#divAccount").fadeOut(500, function () {
    //$("#divLogin").fadeIn(500);
     //});
     
        });
     
        
        
// ------------------ кликаем на кнопку создания Аккаунта

        $("#divAccount #btReg").click(function(){     
            if ($("#sEmail_Account").val() == "")
                alert("Введите Е-маил!")
            else
            
            if(!IsValidateEmail($("#sEmail_Account").val())){
                alert("Введите настоящий E-Mail!");
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
           // alert("Ваша учетная запись создана."); 
            doSend("theCreateAccount");      // отправляем на сервер запрос о создании Аккаунта 
            //document.location.href = "http://www.site.ru";   //document.formReg_CreateAccount.submit();
            }
    });
    
    
    
    
    
$("#divLogin #btLogin").click(function(){    
doSend1("theUserLogin");
});    
    
//

 
   


     






// AJAX запрос

function doSend(nameDO){         //var oData={sDO:"doIt",sName:"MyName",sParam3:"MyParam3",sParam:sParam, sParams:oGet($(".MyParams"))};
var oData= {   sDO_Account: nameDO,  
             sEmail_Account : $("#sEmail_Account").val(),
             sPassword_Account: $("#sPassword_Account").val(),
             sPassword2_Account: $("#sPassword2_Account").val(),
             sLastName_Account : $("#sLastName_Account").val(),
             sFirstName_Account : $("#sFirstName_Account").val()   
     };

 $.ajax({type:"POST",dataType:"json",url:"/CreateAccount",data:oData,async:/*false*/true
      ,success:function(o) { //учти, что эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                     //----$(".MyParams").val(""); // очищаем строки     //o.sReturn
               alert(o.sReturn_Account);
            if (o.sReturn_Account == "Учетная запись создана !") {
            $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
            $("#divLogin").show("slow");                  // показываем окно ВХОДА
            
            
          //  $("#divLogin #sEmail").val( $("#sEmail_Account").val() ); // автоматически вводим Емаил 
           // $("#divLogin #sPassword").val("");
            
            }

         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) {/*alert("это окно будет появляться и при ошибке и при успехе")*/;return data;}
         });
}




// AJAX запрос 2
function doSend1(nameDO){         
var oData= {  
               sDO: nameDO,  
             sEmail : $("#divLogin #sEmail").val(),
             sPassword: $("#divLogin #sPassword").val()
     };
 $.ajax({type:"POST",dataType:"json",url:"/Login",data:oData,async:/*false*/true
      ,success:function(o) { //учти, что эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                    //----$(".MyParams").val(""); // очищаем строки     //o.sReturn
           alert(o.sReturn);
           // if (o.sReturn == "Учетная запись создана !") {
           // $("#divAccount").hide("slow");                // прячем окно РЕГИСТРАЦИИ
           // $("#divLogin").show("slow");   }              // показываем окно ВХОДА 
           if (o.sReturn == "Добро пожаловать на сайт!"){   (window.location.href="/index.jsp")     }  

         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) {/*alert("это окно будет появляться и при ошибке и при успехе")*/;return data;}
         });
         
}



    });






