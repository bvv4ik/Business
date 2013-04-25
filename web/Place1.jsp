<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>place</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        
        <script type="text/javascript" src="js/jquery-1.8.2.js"></script>
        <!--  работа Автозаполнения  -->
        <script type="text/javascript" src="js/jquery-ui-1.9.1.custom.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css"/>
        <link rel="stylesheet" type="text/css" href="css/Place1.css"/>
        <script type="text/javascript" src="js/ajax1.js"></script> 
        
    </head>
    <body>
        
        
    <div id="Form1" class="allForms">  <div id="Header">Внесение данных: <input id="btHide" type="Button" value="X" /></div>
      <div id="Form2" class="allForms">  
            <div id="Form3"> 


<div id="n1"> <span id="c1">Страна:</span>     <select id="sCountry" class="allSelect">    </select>   
<input id="btCountry" type="Button" value="+" />  
</div>
<%String configPath = getInitParameter("configPath");%>
<%String clientIP = request.getRemoteAddr();

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int portNumber = request.getServerPort();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String query = request.getQueryString();
 String path = getServletContext().getRealPath("");
%>
 
                
<%= clientIP %><br>
<%= scheme %><br>
<%= serverName %><br>
<%= portNumber %><br>
<%= contextPath %><br>
<%= servletPath %><br>
<%= pathInfo %><br>
<%= query %><br>
<%= path %><br>

<div id="n2"><span id="c1">РегионТип:</span>     <select id="sRegionType" class="allSelect">    </select>  </div>

    
<div id="n12">Регион:     <select id="sRegion" class="allSelect">    </select>  </div>
<div id="n3">ГородТип:      <select id="sPolisType" class="allSelect">     </select>  </div>
<div id="n3">Город:      <select id="sPolis" class="allSelect">     </select>  </div>
<div id="n4">Тип улицы:  <select id="sBranchType"class="allSelect" >   </select>  </div>
<div id="n5">Название улицы:  <select id="sBranch" class="allSelect" >  </select>  </div>
<!-- <img src="----img/ui-anim_basic_16x16.gif" width="16" height="16" id="Wait" alt="" />  -->  
<div id="n6">Район:   <select id="sArea" class="allSelect">  </select>   </div> 
<div id="n7">Тип здания: <select id="sBuildType" class="allSelect">  </select> </div>
<!--option value=""> </option>   <option> Улица </option>    -->             
<br>
<div id="n8">Здание:   <select id="sBuild" class="allSelect">  </select>   <input id="cmdAddBuild" type="Button" value="+" />   </div>
<div id="n8">Подъезд:   <select id="sPart" class="allSelect">     </select>  <input id="cmdAddPart" type="Button" value="+" />   </div>
 <!-- <div id="n9"> Тип ч. строения:   <select id="sCellType" class="str">  </select>   </div>   -->
<div id="n10">№ помещения:   <select id="sCell" class="allSelect">  </select>  </div>
<br>
<div id="n11">  <input id="btClear" type="Button" value="Очистить" />  <input id="btSave" type="Button" value="Сохранить" /> </div>
<br>
<br>
<!-- ------------------------------------------------------------ -->
<div id="n12">Город: <input id="sPolis1" type="text" class="allSelect" >  </input>  </div>

                </div> <!-- Form3 -->
            </div> <!-- Form2 -->
        </div>   <!-- Form1 -->


        
 	



<script>
    
   function cursor(bool, obj){
     if (bool == true)
     //document.getElementById(obj/*"sCountry"*/).style.cursor = 'wait';   
 obj.style.cursor = 'wait';   
     if (bool == false)
     obj.style.cursor = 'default';  
     //document.getElementById("sCountry").style.cursor = 'default';        
    };


//$(document).ready(function(){    $("#Form1").draggable();    });

$("#btHide").click(function(){   $("#Form1").hide()    });
$("#btCountry").click(function(){     
////var answer1 = prompt("Добавить страну?","введите название...");
////if (answer1){

//doSend("addCountry", "answer");   

 ////$.post("/LoginServlet",{sDO:"addCountry", answer: answer1});
 
//------doSend("getAllCountry");    
//////}
//alert("a");

});

//$("#btAddBuild").click(function(){        doSend("AddBuild");      }); 
           
        //$(document).ready(function() { 
       // doSend("getAllRegionType"); // загружаем статичные данные
        //doSend("getAllBuildType"); // загружаем статичные данные
        //doSend("getAllBranchType"); // загружаем статичные данные
        //doSend("getAllCellType");  // загружаем статичные данные
/* doSend("getAllCountry"); */

var nID_Country ="";
var nID_RegionType = "";
var nID_Region = ""; 
var nID_PolisType = "";
var nID_Polis = "";



$("#sCountry").mouseover(function(){        
    //document.body.style.cursor = 'wait';
    
   
    //document.getElementById("sCountry").style.cursor = 'wait';
    if (this.options.length-1 == -1) 
    doSend("getAllCountry");
    
    //cursor(true, this)
    
    //alert ((this.options.length-1).toString());
      
     //var s = <%= getServletContext().getAttribute("HELLO.WORLD") %> ;
     //     alert (s);
   ///====  document.getElementById("sCountry").style.cursor = 'wait'; // устан друг курс

    //----$("#Form1").css('cursor', 'wait'); 

    
    
});
    /*
        */



$("#sCountry").change(function(){        
nID_Country = $("#sCountry option:selected").val(); // Получаем val выделенного элемента  //var sel = document.getElementById("sCountry"); nID_Country = sel.options[sel.selectedIndex].value; // Получаем значение выделенного элемента 
//document.getElementById("sCountry").options.length;

   doSend("getAllRegionType");    
}); 


$("#sRegionType").change(function(){     
nID_RegionType = $("#sRegionType option:selected").val(); //  var sel = document.getElementById("sRegionType");  nID_RegionType  = sel.options[sel.selectedIndex].value; // Получаем значение выделенного элемента 
doSend("getAllRegions", nID_Country, nID_RegionType);
});


$("#sRegion").change(function(){  
var sel = document.getElementById("sRegion");  nID_Region  = sel.options[sel.selectedIndex].value; // Получаем значение выделенного элемента 
doSend("getAllPolisType");
});
     
     
$("#sPolisType").change(function(){      
var sel = document.getElementById("sPolisType");  nID_PolisType  = sel.options[sel.selectedIndex].value; // Получаем значение выделенного элемента 
doSend("getAllPolis", nID_PolisType, nID_Region);
});        


$("#sPolis").change(function(){      
var sel = document.getElementById("sPolis");  nID_Polis  = sel.options[sel.selectedIndex].value; // Получаем значение выделенного элемента 
    //doSend("getAllAreas", );       
});        
        
        
        $("#sBranchType").change(function(){   doSend("getAllBranch");       });       
        $("#sBranch").change(function(){      doSend("getAllBuild");       });       
        $("#sBuildType").change(function(){ doSend("getAllBuild");     });       
        $("#sArea").change(function(){   doSend("getAllBuild");        }); 
        $("#sBuild").change(function(){    doSend("getAllPart");        }); 
        $("#sPart").change(function(){     doSend("getAllCell");        }); 
        
        $("#btSave").click(function(){      doSend("Save");       }); 





     $("#sPolis1").autocomplete({
                minLength:2,  delay:500, width: 400,  selectOnly:true, //  selectFirst:true,
                source:function(req,res){ 
                var oData= { sDO:"getPolis", sFind:$("#sPolis1").val()  };
            
                $.post("/LoginServlet",oData,function(o){res(o);},'json');},
                focus:function(event,ui){return false;}, select:function(event,ui){  
                ////doSelect(ui.item.nID); //nID_Region   //    return false;
               //var 
                nID_Region = ui.item.nID_Region;
                alert(ui.item.nID_Region); //--------------- 
                }
        }).data("autocomplete")._renderItem=function(ul,item)
        {return $("<li></li>").data("item.autocomplete",item)
            .append("<a>"+item.value+" "+item.nID_Region+"</a>").appendTo(ul);//+item.nID+")"
            // alert(item.nID_Polis); //---------------    
        };
        
        
</script>


</body>
</html>

