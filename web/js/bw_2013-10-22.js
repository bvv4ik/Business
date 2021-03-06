var oFunctionReturn=null;

var bDebug=true;
var oaParamOur={
    sOurTelephones:"1243343,23432424"
    ,sOurOrganization:"ПГАСА"

};
var oaParamWho={
    sFIO:"Вася Пупкин"
    ,sRole:"Вассал"
};

var oaText={
    sChangePassword:"Изменить пароль"
    ,sChangeLogin:"Изменить пароль"
    ,sDearVisitor:sParsed("Добро пожаловать (sFIO) в (sOurOrganization), Ваша роль (sRole)!")
}

//================
// === УТИЛИТЫ ===
//================
function sParsed(sText){//,oaParam
    var oaParam=$.extend({},oaParamOur,oaParamWho);
    $.each(oaParam, function(sName,sValue){
        sText=sText.replace("("+sName+")", sValue);//, flags
    });return sText;
}
//$.extend(oData,{filterState:"withReport"});//notClosed

function sTest(){
    //var oData={};
    //$.extend({},oaParamOur,oaParamWho);
    //alert(sParsed(oaText.sDearVisitor,$.extend({},oaParamOur,oaParamWho)));
    alert(oaText.sDearVisitor);//sParsed(
}


//Получение название функции
function sFunction(p){
    if(p==null){return null;}
    return (p==null?this.arguments:p).callee.toString().match(/function ([^(]*)\(/)[1];
}

function sObject(o){
    try{
        return o==null?"null":typeof o=="string"?o:$.toJSON(o);//o.toSource()//$.toJSON//JSON.parse
    }catch(_){
        doDebug(arguments,_);
    }
}
function sParam(o){
    try{
        return o==null?"null":typeof o=="string"?o:$.param(o);
    }catch(_){
        doDebug(arguments,_);
    }
    /*if(see("#dialog",aaa==2)){
        $("#dialog").find("").text("ss")
    }*/
}

//Узнать видимость элемента или отобразить/скрыть его (с защитой)
function see(s,b){
    if(b==null){
        if(s!=null&&$(s).length>0){
            b=$(s).css('display')!='none';
        }else{
            b=false;
        }
    }else if(b){
        $(s).show();
    }else{
        $(s).hide();
    }
    return b;
}
//Узнать выключенность элемента или выключить/включить его
function off(s,b){
    b=b!=null?b:$(s).attr('disabled');
    $(s).attr('disabled',b);
    return b;
}
//Узнать включенность элемента или включить/выключить его
function on(s,b,o){
    var bZ=!off(s);
    b=b==null?bZ:b;
    if(bZ!=b){
        off(s,!b);
        if(o!=null){
            o(b);
        }
    }
    return b;
}
//Получить шестнадцатеричное число из десятичного
function sDec2Hec(d) {
    return d.toString(16);
}
//Получить десятичное число из шестнадцатеричного
function nHec2Dec(h) {
    return parseInt(h,16);
}
//Получить общий цвет из трех базовых
function sColor(nRed,nGreen,nBlue) {
    return "#"+sDec2Hec(nBlue+(256*nGreen)+(65536*nRed));
}



//====================
// === УТИЛИТЫ ДАТ ===
//====================

function sDate(oDateGot){//YYYY-MM-DD
    var s="";
    oDateGot=oDateGot==null?new Date():oDateGot;
    try{
        s=oDateGot.getFullYear()
        +"-"+(oDateGot.getMonth()<9?"0":"")+(oDateGot.getMonth()+1)
        +"-"+(oDateGot.getDate()<10?"0":"")+oDateGot.getDate();
    }catch(_){
        s=sDate(new Date());
    }
    return s;
}
function sDateDot(oDateGot){//DD.MM.YYYY
    var s="";
    try{
        s=(oDateGot.getDate()<10?"0":"")+oDateGot.getDate()
        +"."+(oDateGot.getMonth()<9?"0":"")+(oDateGot.getMonth()+1)
        +"."+oDateGot.getFullYear();
    }catch(_){
        s=sDateDot(new Date());
    }
    return s;
}
function sDateMax(oDateGot,sDelimiter,bResetTime){//YYYY-MM-DD
    var s=sDate(oDateGot==null?new Date():oDateGot);
    try{
        s+=(sDelimiter==null?" ":sDelimiter)+(bResetTime?"0:00:00.000":oDateGot.getHours()
            +":"+oDateGot.getMinutes()+":"+oDateGot.getSeconds()+"."+oDateGot.getMilliseconds());
    }catch(_){
        s=sDateMax(new Date(),sDelimiter, bResetTime);
    }
    return s;
}

function oDate(sDateGot){//YYYY-MM-DD
    var o=new Date();
    try{
        var aS=sDateGot.trim().split("-");
        o=new Date(aS[0],parseFloat(aS[1])-1,aS[2]);
    }catch(_){}
    return o;
} 
function oDateDot(sDateGot){//DD.MM.YYYY
    var oDate=new Date();
    try{
        var aS=sDateGot.trim().split(".");
        oDate=new Date(aS[2],parseFloat(aS[1])-1,aS[0]);
    }catch(_){}
    return oDate;
}
function oDateMax(sDateGot){//YYYY-MM-DD hh:mm:ss.nnn
    var o=new Date();
    try{
        var s=sDateGot.replace("-"," ","g").replace("-"," ","g").replace("-"," ","g")
        .replace(":"," ","g").replace(":"," ","g").replace("."," ","g");
        var a=s.split(" ");
        if(a.length==7){
            o=new Date(a[0],parseInt(a[1])-1,a[2],a[3],a[4],a[5],a[6]);
        }else{
            o=$.datepicker.parseDate('yy-mm-dd',sDateGot);
        }
    }catch(_){}
    return o;
}

function oDateAddDays(oDateOld,nAdd){
    var d=new Date();
    oDateOld=oDateOld==null?d:oDateOld;
    try{
        d.setTime(oDateOld.getTime() + nAdd * 24 * 60 * 60 * 1000);
    }catch(_){}
    return d;
}
function oDateAddMounths(oDateOld,n){
    var d=new Date();
    oDateOld=oDateOld==null?d:oDateOld;
    if(n<0){
        return d;
    }
    try{
        d=oDate(oDateOld.getFullYear()+"-"+(oDateOld.getMonth()+1+n)+"-"+oDateOld.getDate());
    }catch(_){}
    return d;
}
function sDateShort(sDateGot){
    return sDateGot==null||sDateGot==""?"":sDateDot(oDate(sDateGot.split(" ")[0]));
}



//================
// === ДИАЛОГИ ===
//================

var nCountAutoclose = 0;
function timerAutoclose(oThis) {
     
     // alert($(oThis).css("display"));
     if ( (nCountAutoclose > 0) & ($(oThis).is(":visible")) ) {
    
          setTimeout(function() {  
               nCountAutoclose-- ; 
               var sTextHead = oThis.find(".oHead").text();
               sTextHead = sTextHead.substring(0,(sTextHead.length)-1);
               //oThis.find(".oHead").text(sTextHead+" "+nCountAutoclose);
               oThis.find(".oButton").attr("value","Ok ("+nCountAutoclose+")")
               //alert(1);
               timerAutoclose(oThis);
          }, 1000);                            
     }
     else{
          //$(oThis).hide();   
          hideDialog(oThis);
     }
      
}


//Открыть диалог
function showDialog(oThis,nWidth,nHeight,nDelayAutoclose, oThey, bBlockBackground){
    doDebug(arguments,":nWidth="+nWidth+","+$(oThis).html());
    try{
        if(bBlockBackground){
            $(".oDialog_Background").show();
        }
        var nYClient=document.body.clientHeight,nYDialog=$(oThis).height();
        var nUpDialog=window.scrollY+(nYDialog<nYClient?(nYClient-nYDialog)/2:23);
        //$(oThis).css("left",((document.body.clientWidth-$(oThis).width())/2)+"px");
        // $(oThis).css("top",nUpDialog+"px").show();
        $(oThis).css("left", $(oThey).css("left") );
        $(oThis).css("top", $(oThey).attr("height")+$(oThis).attr("top")).show();

        $(oThis).css("width", nWidth);
        $(oThis).css("Height", nHeight);
        $(oThis).show();
        
        //$(oThis).css("background-color", "lightgrey");
        
        if (nDelayAutoclose != null) {
            nCountAutoclose = nDelayAutoclose;
            timerAutoclose($(oThis));
        }
    //alert(1);
    // hideDialog(oThis);
    //doRepaint(oThis);//авто-перерисовка в некоторых случаях
    //nDelayAutoclose//функция-таймер для автозакрытия поЖ hideDialog(oThis)
        
        
    }catch(_){
        doDebug(arguments,_);
    }
}
//Закрыть диалог
function hideDialog(oThis){
    doDebug(arguments, ""+$(oThis).html());
    try{
        oThis=$(oThis).closest('.oDialog');
        $(oThis).hide();
        if($('.oDialog:visible:first').length==0){
            $(".oDialog_Background").hide();
            //window.close();//В каких-то случаях можно и закрыть все окно
        }
    }catch(_){
        doDebug(arguments, _);
    }
}

//Шаблон диалога-вопроса
function ask(sBody,sHead,aButttons,oReturn,bSkip,nWidth,nHeight, nAutoHide, oThey, bBlockBackground){
    //alert(sBody);    //alert(sHead);
    try{
        if(bSkip){
            if(oReturn!=null){
                oReturn();
            }
            return;
        }else{  
            var oThis=$(".oDialog.oAsk");
           if(aButttons==null){
                if(sHead=="yes"){
                    sHead="Сделайте выбор";
                    aButttons=[{
                        sName:"Нет",
                        sClass:"oButtonRed"
                    },{
                        sName:"Да",
                        sClass:null
                    }];
                }else if(sHead=="accept"){
                    sHead="Подтвердите";
                    aButttons=[{
                        sName:"Закрыть",
                        sClass:"oButtonRed"
                    },{
                        sName:"Подтвердить",
                        sClass:null
                    }];
                }else{
//                    aButttons=[{
//                        sName:"Отменить",
//                        sClass:"oButtonRed"
//                    },{
//                        sName:"Принять",
//                        sClass:null
//                    }];
                    aButttons=[{
                        sName:"Ok ("+nAutoHide+")",
                        sClass:null
                    }];
                }
            }
            
            
            if(sHead!=null){
                oThis.find(".oHead").html(sHead);
            }
            if(sBody==null||sBody==""){
                sBody="Сделайте выбор!";
            }
            oThis.find(".oBody").html(sBody);
            if(oReturn!=null){
                oThis.find('.oButton:not(.default)').remove();
               
               for(var n=0;n<aButttons.length;n++){
                    var sName=aButttons[n].sName
                        ,sClass=aButttons[n].sClass
                        ,oNode;
                    if(sClass==null){
                        sClass="oButtonGreen";
                    }
                    oNode=oThis.find('.oButton.default');
                    oNode=oNode.clone().insertAfter(oThis.find('.oButton:last')).removeClass("default").addClass(sClass);
                    oNode.val(sName);
                    oFunctionReturn=oReturn;
                    oNode.attr("onclick","oFunctionReturn("+n+");hideDialog(this);")
                    oThis.find('.oButton.default').remove();
                }
                
                
            }
                      
            showDialog(oThis,nWidth,nHeight,nAutoHide, oThey, bBlockBackground);                
        }
    }catch(_){
        doError("Ошибка вывода диалога", ":"+_+"\nsHead:"+sHead+",\nsBody:"+sBody+"","attention");
    }
}

function doError(sBody,sDebug,sHead){
    //alert(":"+_+"\nsHead:"+sHead+",\nsBody:"+sBody+"");
    seeError(sBody,sDebug,sHead);
    if(sHead!="fatal"){
        doDebug(arguments, _+"\nsHead:"+sHead+",\nsBody:"+sBody+"");
    }
}

function seeError(sBody,sDebug,sHead){
    try{
        var oThis=$(".oDialog.oError");
        if(sHead=="attention"){
            sHead="Внимание!";
        }else if(sHead=="incomplete"){
            if(sBody==null){
                sBody="Введены не все данные или введены не корректно!";
            }
            sHead="Заполнены не все данные!";
        }else if(sHead=="reject"){
            sHead="Запрос отклонен!";
        }else if(sHead=="unprocessed"){
            sHead="Пропущена ошибка";
        }else if(sHead=="wonder"){
            sHead="Необычная ошибка";
            doDebug(arguments,sBody+":\n"+sDebug);
        }else if(sHead=="fatal"){
            sHead="Фатальная ошибка";
        }
        if(sHead!=null){
            oThis.find(".oHead").html(sHead);
        }
        if(sBody==null||sBody==""){
            sBody="(без описания)";
        }
        oThis.find(".oBody").html(sBody);

        if(sDebug==null){
            sDebug="";
        }
        var sLoginThis=null;
        try{
            sLoginThis=sLogin;
        }catch(_){}
        sDebug=sDateMax()+"<br>(sLogin:"+sLoginThis+")"+"\n<br>"+sDebug;
        oThis.find(".oDebug").html(sDebug);

        showDialog(oThis);
    }catch(_){
        alert("Ошибка вывода ошибки["+sFunction(arguments)+"]:"+_+"\nsBody:"+sBody+",\nsHead:"+sHead+",\nsDebug:"+sDebug);
    }
}

//Тест диалога-вопроса
function askTest(){
    ask("В чем вопрос?","Вот",[{
        sName:"Не быть",
        sClass:"oButtonRed"
    },{
        sName:"Быть",
        sClass:"oButtonGreen"
    },{
        sName:"ХЗ",
        sClass:"oButtonGray"
    }],function (nReturn){
        alert("nReturn="+nReturn);
    })
}
//Тест диалога-вопроса
function askTest1(){
    ask(null,null,null, null, null,200,200)
   
}

//==============
// === ДЕБАГ ===
//==============

//function doDebug(sInfo,sFunction,sDebug){
function doDebug(oArguments,sInfo,oData,bConsoleOnly){
//function doDebug(oArguments,oData,bOnlyConsole,bLogAlways,bBreakGroup){try{
            //var s=$.param(oData),n=s.indexOf("&");
            //addLog(sFunc(oArguments)+(n<0?s:s.substr(0,n)));
            //if(bDebug){console.log(sFunc(arguments));}

    try{
        
        var sFuncName="";
        if(bDebug){
            try{
                if(oArguments==null){
                    oArguments=arguments;
                }else{
                    sFuncName="["+sFunction(oArguments)+"]";
                }console.log(sDateMax(new Date())+sFuncName+sInfo+(oData!=null?sObject(oData):""));
            }catch(_){
                
            }
        }
        if(bDebug&&bConsoleOnly!=true){
            if(oArguments==null){
                oArguments=arguments;
            }else{
                sFuncName="["+sFunction(oArguments)+"]";
            }//addLog(sFuncName+(oData!=null?sObject(oData):""),bBreakGroup);
        }
    
    //logDebug(sInfo,sFunction);//Логирование (в массив)
    //saveDebug(sInfo,sFunction,sDebug,oStack);//Сохранение (на сервер)
    }catch(_){
        //doError("Сбой обработки ошибки","[sFunction:"+sFunction(arguments)+"]:"+_+"\n<br>"+sInfo+"\n<br>"+sFuncName+"\n<br>"+sObject(oData),"fatal");
        alert("Сбой обработки ошибки"+"[sFunction:"+sFunction(arguments)+"]:"+_+"\n<br>"+sInfo+"\n<br>"+sFuncName+"\n<br>"+sObject(oData));
    }
}



//===============
// === ОНЛОАД ===
//===============
$(function(){
    //важные обязательные дефолтные установки
    $('a[href=#]').attr('href',"javascript:void(0)");
    $('.oButtonClose').attr("onclick","hideDialog(this);").attr("alt","Скрыть").attr("title","Скрыть окно");
    $('img.doHideDialog').attr("onclick","hideDialog(this);").attr("alt","Скрыть").attr("title","Скрыть окно")
    .attr("src","img/dialogHide.png").attr("width","10").attr("height","10");
    //закрытие верхнего диалога по эскейпу
    $("body").keydown(function(event) {
        if(event.keyCode==27){
            if($('.oDialog:visible:first').length>0){
                hideDialog($('.oDialog:visible:last'));
            }
        }
    });

});
