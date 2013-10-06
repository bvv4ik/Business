  //function doMyFunction(){
   //alert("это тест");
 //  }
 function fObjToArray(o){
  
    var s = "<option> </option>";   //  var obj = o ;

    //-------var arr = []; // создаем пустой инициализированный строковой массив.
  //  var inte = 0;   
    for (var i in o) {
     //   inte++;
        //alert(o[i]);
        //alert(i + ' ' + o[i]);
        //-------  arr.push(o[i]); // добавляем в массив данные из JSON объекта
        //------if (inte >= 2) 
        if (i != "sReturnLogin") 
            s += "<option value= "+i+"  >" + o[i] +"</option>"
        }
     alert(s);
    return s;
}



    

function doSend(sParam1, nParam2, nParam3, nParam4, nParam5, nParam6, nParam7){
   //var oData={sDO:"doIt",sName:"MyName",sParam3:"MyParam3",sParam:sParam, sParams:oGet($(".MyParams"))};
    var oData= {  //sParams:oGet($(".MyParams")),
             
             sDO: sParam1, //"addUser", 
             nID_Country: $("#sCountry").val() ,//nParam2,
             nID_RegionType: $("#sRegionType").val() ,//nParam3,
             nID_Region: $("#sRegion").val() ,//nParam4,
             nID_PolisType: $("#sPolisType").val() ,//nParam5,
             nID_Polis:$("#sPolis").val() ,// nParam6,

             answer: nParam7,
             
             sLogin: $("#sLogin").val(),
             sPassword: $("#sPassword").val(),
            // sEmail: $("#sEmail").val(),
           //  sLoginVerify: $("#sPassword").val(),
         
         sLoginEmail : $("#sLoginEmail").val(),
         sPasswordReg1 : $("#sPasswordReg1").val(),
         sPasswordReg2 : $("#sPasswordReg2").val()//,
         /*
         sCountry : $("#sCountry").val(),
         sRegion : $("#sRegion").val(),
         sPolis : $("#sPolis").val(),
         sArea : $("#sArea").val(),
         
         sBranchType : $("#sBranchType").val(),
         sBranch : $("#sBranch").val(),
         
         sBuildType : $("#sBuildType").val(),
         sBuild : $("#sBuild").val(),
         
         sPart : $("#sPart").val(),
         sCellType : $("#sCellType").val(),
         sCell : $("#sCell").val()
         */
         
         
     //    sEmailReg : $("#sEmailReg").val()
     
     };


   $.ajax({type:"POST",dataType:"json",url:"/LoginServlet",data:oData,async:/*false*/true
      ,success:function(o) {//учти, что эта функция сработает гораздо позже, чем завершится выполнение всей функции doSend, т.к. это асинхронный режим работы.... потому безсмысленно обращаться за данными в конце ее(после: "dataFilter.... });") 
                                                                     //   alert("данные переданы, и получен ответ:" ); //+o.toSource()
                                                                     //$(".MyParams").val(""); // очищаем строки     //o.sReturn
               // $("#sReportLogin").text("Ответ от сервера: "+o.sReturnLogin);
                if (o.sReturnLogin == " 1 Логин  есть в базе! "){
                (window.location.href="/main.jsp")}  //location.assign("http://example.com/");
                         
              if (o.sReturnLogin == "Сессия удалена"){
                 location.href="/index.jsp"}
             
           /*
               if (o.sReturnLogin != ""){
               var s = o.sReturnLogin;                 
               // --------------------------------------------
               String.prototype.replaceAll = function(search, replace){
               return this.split(search).join(replace);
                  }
                var s1 = s.replaceAll('..', '</option> \n');
                var s2 = s1.replaceAll('.', '<option>');
                // заменить все символі или строки на другие        
           
               $("#Region option").html(s2);  // Замена ХТМЛ кода
                 }
              // --------------------------------------------                 
             */
                //$("#sReportRegistration4").text(o.sReturnLogin);
               // $("#sReportForm").text(o.sReturnLogin);
              
                //// ---- !! $('#sReportForm').val("df");
                
               
             //   var res = JSON.stringify(o); // преобразовать объект в строку
              //  alert(res);
             
             //  var res1 = res.substring(22,res.length-1) ;  // взять подсроку
                    
 //for (var i in JSON.array.data) {
 //alert(i + ' ' + JSON.array.data[i]);
//console.profile();             
//console.time("Execution time took");
//console.timeEnd("Execution time took"); 
//console.profileEnd();

var s =  "<option> </option>";

//-------var arr = []; // создаем пустой инициализированный строковой массив.
  var inte = 0;   
  for (var i in o) {
  inte++;
  //alert(o[i]);
  //alert(i + ' ' + o[i]);
  //-------  arr.push(o[i]); // добавляем в массив данные из JSON объекта
  //------if (inte >= 2) 
  if (i != "sReturnLogin") 
s += "<option value= "+i+"  >" + o[i] +"</option>"
//console.log(i, s);
     
}

 if (o.sReturnLogin == "Country"){
 $("#sCountry").html(s);  // Замена ХТМЛ кода
// $("#c1").text("Страна: "+ (document.getElementById("sCountry").options.length-1).toString());
 window.document.getElementById("sCountry").style.cursor = 'default';
//console.log();
//alert("df");
}


if (o.sReturnLogin == "RegionType"){
 $("#sRegionType").html(s);  // Замена ХТМЛ кода
 //$("#c2").text("Страна: "+(inte-1));
 alert(s);
}

if (o.sReturnLogin  == "Region") {
 $("#sRegion").html(s); 
 //$("#PolisSpan").text("Город: "+(inte-1)  );
//  alert(s);
}

 if (o.sReturnLogin == "PolisType"){
 $("#sPolisType").html(s);  // Замена ХТМЛ кода
// $("#c1").text("Город: "+(inte-1));
 }

//alert(s);

//if (o.sReturnLogin  == "Polis")
 //$("#sArea").html(s);  // Замена ХТМЛ кода

if (o.sReturnLogin  == "BranchType")
 $("#sBranchType").html(s);  // Замена ХТМЛ кода

if (o.sReturnLogin  == "Branch")
 $("#sBranch").html(s);  // Замена ХТМЛ кода

if (o.sReturnLogin  == "Build")
 $("#sBuild").html(s);  // Замена ХТМЛ кода

if (o.sReturnLogin  == "BuildType")
 $("#sBuildType").html(s);  // Замена ХТМЛ кода



if (o.sReturnLogin  == "Part")
 $("#sPart").html(s);  // Замена ХТМЛ кода

if (o.sReturnLogin  == "CellType")
 $("#sCellType").html(s);  // Замена ХТМЛ кода

if (o.sReturnLogin  == "Cell")
 $("#sCell").html(s);  // Замена ХТМЛ кода



// Функция автозаполнения              
    /*
    $(function() {
        var availableTags = arr;//res2; критерий поимка наш массив
            //["Днепропетровская область","Киевская область","ActionScript2"];
         $( "#sRegion" ).autocomplete({
            source: availableTags
        });
                         
    });
    
    $(function() {
        var availableTags1 = arr;//res2; критерий поимка наш массив
            //["Днепропетровская область","Киевская область","ActionScript2"];
           $( "#sPolis" ).autocomplete({
            source: availableTags1
        });
                         
    });
    */
    
    
  
    
          //for (var i = 1; i < 9; i++) {              }
           
           
         }, error:function(o,s) { alert("Произошла ошибка--!!"+o.status+":"+o.statusText+" ("+o.responseText+")");  }
         ,dataFilter:function(data, type) {/*alert("это окно будет появляться и при ошибке и при успехе")*/;return data;}
         });
}
 //$(window).onload(function(){  alert("привет пользователь!");        }); 



//if(confirm("Сообщение"))   {   // Нажата кнопка OK   . . . }
//else  {   // Нажата кнопка Cancel   . . . }

//var cmd="doSend('LoginVerify')";
//idTimer=window.setTimeout(cmd, 3000);



//$(function(){                        //doMyFunction() ;  эта функция всегда будет запускаться при загрузке страници!
$(document).ready(function() { 



 // Вход пользователя
$("#cmdAuthorization").click(function(){
doSend("UserLogining");
});


// Выход и удаление сессии
$("#cmdExit").click(function(){ 
 doSend("DeleteSession");  
});


// Проверка логина 
$("#cmdLoginVerify").click(function(){ 
 doSend("LoginVerify");    //   alert("LoginVerify do send");    
 });
 




//    doSend("LoginVerify");

 // Отправка формы регистрации 
 //=================================================================
$("#cmdRegistration").click(function(){
doSend("RegisteredUser");
/* 
var StringsCheck=false;
var EmailCheck=false;
var PasswordCheck=false;
//var EmailVerify=false;

if ($("#sPasswordRegistration1").val() == $("#sPasswordRegistration2").val())
    {EmailCheck=true;
    $("#sReportRegistration1").text("");}
    else     {  
        $("#sReportRegistration1").text(" Введенные пароли не совпадают");
    //    alert("Введенные пароли не совпадают");    
    }
if (($("#sPasswordRegistration1").val() == "") || ($("#sPasswordRegistration2").val() == ""))
    {$("#sReportRegistration1").text("Недопустимые пароли");
    EmailCheck=false;
    }



var ValueEmail = $("#sEmailRegistration").val();  
reg = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
if (ValueEmail.match(reg))
        {PasswordCheck=true; 
        $("#sReportRegistration2").text("");} 
        else
        {
            //alert("Пожалуйста, введите корректный e-mail")
            $("#sReportRegistration2").text(" Пожалуйста, введите корректный e-mail");
        }
if ($("#sLoginRegistration").val() == "" | 
    $("#sPasswordRegistration1").val() == "" |
    $("#sPasswordRegistration2").val() == "" |
    $("#sEmailRegistration").val() == "")
{
$("#sReportRegistration3").text ("Не все поля заполнены"); // alert("Не все поля заполнены");
}
else
 {StringsCheck=true;
 $("#sReportRegistration3").text("");}


if (EmailCheck==true) // если  маил правильный
if (PasswordCheck==true) //если пароли совпадают
if (StringsCheck==true) //если все строки заполнены
doSend("RegisteredUser")
//doSend("LoginVerify");
alert("najato") ;
//OK
//doSend("RegisteredUser"); 
*/

//var EmailVerify=false;

});
 //=================================================================



//document.title = 'выа';
//<input type="button" style="border: 1px gray solid; font-size: 11px; width:120; background-Color:#f6f6f6; color:#424242; font-family: Verdana; font-size:11px;" value="Закрыть окно" onClick="javascript:self.close();">



    $("#cmdSeeParams").click(function(){
            var sNew=oGet($(".MyParams"));
            alert("Обьект с параметрами будет: "+sNew);
    });
    
    
    $("#cmdSetParams").click(function(){
        var sParams=$("#sParams").val();
        if(sParams==""){alert("Введите строку обьекта параметров!!!")}
        else{
                oSet(sParams);
                alert("Обьект с параметрами проставлен!");
             }
           });
    
    
});


        //мои полезные функции
        function see(s,b){if(b==null)b=$(s).css('display')!='none';else if(b)$(s).show();else $(s).hide();return b;}
        function off(s,b){b=b!=null?b:$(s).attr('disabled');$(s).attr('disabled',b);return b;}
        function on(s,b,o){var bZ=!off(s);b=b==null?bZ:b;if(bZ!=b){off(s,!b);if(o!=null)o(b);}return b;}
        function oGet(oAt){var s="";oAt.each(function(n,o){if (o.type!="button"&(o.type!="radio"||o.checked)){s=s+(n>0?",":"")+"\""+o.id+"\":\""+(o.type=="checkbox"?o.checked:o.value.replace(/\"/g,"&quot;"))+"\"";}});return "{"+s+"}";}

        function oSet(oTo){$.each(oTo,function(key,val){if(key!=''){var s="#"+key;
        if($(s).attr("type")=="checkbox"){$(s).attr("checked",eval(val));}
        else if($(s).attr("type")=="radio"){$('[id='+key+']:[value='+val+']').attr('checked',true);}
        else {$(s+":[type!=button]").val(val.replace(/<br\/>/g,"<br/>\n").replace(/<br>/g,"<br>\n").replace(/<\/p>/g,"</p>\n").replace(/&quot;/g,"\""));}
        }});
}

