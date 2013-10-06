<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

    <head>
        <title>place</title>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script type="text/javascript" src="js/ajax1.js"></script>

        <script type="text/javascript" src="js/jquery-1.8.2.js"></script>
        <!-- 
        работа Автозаполнения  -->
        <script type="text/javascript" src="js/jquery-ui-1.9.1.custom.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css"/>
    </head>

    <body>

        <div id="Form1" class="allForms">  <div id="Header">Внесение данных:</div>
            <div id="Form2" class="allForms">  
                <div id="Form3"> 



                    <div id="n1">  <span id="CountrySpan"> Страна: </span>
                        <input id="btRefreshCountry" type="Button" value="+" />
                        <select id="sCountry" name="Countr">                  
                            <!--   <option value=""> </option>                 
                            <option> Украина </option>                 -->
                        </select>                     
                    </div>
                    <script>
                        
                            $("#btRefreshCountry").click(function(){
                            doSend("getAllCountry");
                            });
                            
                    </script>
                    
                    <div id="n2">  <span id="RegionSpan"> Регион: </span>
                        <select id="sRegion" class="str">    </select>                                   
                    </div>


                    <div id="n3">  <span id="PolisSpan"> Город: </span>
                        <select id="sPolis" class="str">     </select> 
                    </div>

                    <div id="n4">  <span id="BranchTypeSpan"> Тип улицы: </span>
                        <select id="sBranchType"class="str" >     </select>                     
                    </div>

                    <div id="n5">   <span id="BranchSpan"> Название улицы: </span>
                        <select id="sBranch" class="str" >  </select>
                        <img src="img/ui-anim_basic_16x16.gif" width="16" height="16" id="Wait" alt="" />
                    </div>

                    <!--<div id="n6">    <span> Район: </span>
                      <select id="sArea" class="str">                  
                      <option value=""> </option> <option> Бабушкинский </option>                 
                      </select>                     
                    </div> 
                    --
                    
                    <div id="n7">  <span> Тип здания: </span>
                      <select id="sBuildType" class="str">                  
                      <option value=""> </option>   <option> Улица </option>                 
                      </select>                     
                    </div>
                    -->
                    <br>

                    <div id="n8">  <span id="BuildSpan"> Здание: </span>
                        <select id="sBuild" class="str">    </select>                     
                        <input id="cmdAddBuild" type="Button" value="+" />
                    </div>


                    <div id="n8">  <span id="PartSpan"> Подъезд: </span>
                        <select id="sPart" class="str">     </select>                     
                        <input id="cmdAddPart" type="Button" value="+" />
                    </div>

                    <!--
                    <div id="n9"> <span> Тип ч. строения: </span>
                      <select id="sCellType" class="str">                  
                      <option value=""> </option>   
                      </select>                     
                    </div>
                    -->

                    <div id="n10"> <span id="CellSpan"> № помещения: </span>
                        <select id="sCell" class="str">     </select>                     
                    </div>

                    <br>

                    <div id="n11"> 
                        <input id="cmdCancel" type="Button" value="Очистить" />
                        <input id="cmdSave" type="Button" value="Сохранить" />
                    </div>


                    <br>
                    <br>

                    <!-- ------------------------------------------------------------ -->

                    <div id="n12">   <span id="Polis1"> Город: </span>
                        <input id="sPolis1" type="text" class="str" >  </input>
                    </div>



                </div> <!-- Form3 -->
            </div> <!-- Form2 -->
        </div>   <!-- Form1 -->





    <style>  

        #Header{ padding-left: 10px; font-weight:bold; }    

        .allForms{  -moz-border-radius: 5px; /*╨Ю╨║╤А╤Г╨│╨╗╨╡╨╜╨╕╨╡ ╤Д╨╛╤А╨╝╤Л*/ 
                    -webkit-border-radius: 5px; /*Chrome ╨╕ Safari*/ 
                    position: absolute;  }

        #Form1{ /* glavnaya forma*/
            background-color:#CCD8E8;
            border : 1px solid #B2BBC2;

            left : 60px;
            top : 30px;


            width : 400px;
            height : 440px;
        }

        #Form2{
            background-color:#E0E7F9;
            border : 1px solid #BAC7D7;

            left : 5px;
            top : 18px;
            width : 376px;
            height : 404px;

            padding : 6px;   
        }

        #sCountry, #sRegion, #sPolis, #sArea, #sBranchType, #sBranch, #sBuildType, #sBuild, 
        #sPart, #sCellType, #sCell, sPolis1 {
            position: absolute; /**/
            left : 160px; /**/
            height : 21px;
            width : 200px;
        }


        span{
            font-weight:bold;
            font-size:15px;
        }


        #n1, #n2, #n3, #n4, #n5, #n6,  #n7, #n8, #n9, #n10, #n12{
            height : 25px;                /**/
            /* padd   ing : 5px;              /**/
            /*border : 1px solid red;      /**/
        }

        #Wait{
            position : absolute;
            left : 355px;
            padding-top : 2px;              /**/
        }

        #sArea {left : 165px;}
        #sBui  ldType{left : 165px;}

        .ui-autocomplete {
            max-height: 220px;
            overflow-y: auto;
            /* prevent horizontal scrollbar */
            overflow-x: hidden;
        }


        #FormAddBuild  {
            position : absolute;
            Left : 500px;
            border : 1px solid #BAC7D7;

        }  

    </style>  	




    <script>
        $("#sPolis1").autocomplete({
            minLength:2,
            delay:500,
            width: 400,
            //  selectFirst:true,
            selectOnly:true,
            source:function(req,res){ 
                var oData= { sDO:"getPolis", sFind:$("#sPolis1").val()  };
            
                $.post("/LoginServlet",oData,function(o){res(o);},'json');},
            focus:function(event,ui){return false;}, 
            select:function(event,ui){  
                ////doSelect(ui.item.nID); //nID_Region
                //    return false;
                var nID_Region = ui.item.nID_Region;
                alert(ui.item.nID_Region); //--------------- 
     
            }
        }).data("autocomplete")._renderItem=function(ul,item)
        {return $("<li></li>").data("item.autocomplete",item)
            .append("<a>"+item.value+" "+item.nID_Region+"</a>").appendTo(ul);//+item.nID+")"
            // alert(item.nID_Polis); //---------------    
        };
            
        /*
    $("#example2").autocomplete("autocomplete.php", {
                delay:10,
                minChars:2,
                matchSubset:1,
                autoFill:true,
                matchContains:1,
                cacheLength:10,
                selectFirst:true,
                formatItem:liFormat,
                maxItemsToShow:10,
                onItemSelect:selectItem
              });
         */

    </script>



    <script>

        //$(document).ready(function() { 
        see("#Wait",false);

        //doSend("getAllBuildType");
        //doSend("getAllBranchType");
        //doSend("getAllCellType");


    
        $("#sCountry").change(function(){
            //see("#Wait",true);  //$(".str").val("");  //$(".str").text("");
            $('#sRegion').val(""); $('#sPolis').val("");  $('#sBranchType').val(""); $('#sBranch').val(""); $('#sArea').val(""); $('#sBuildType').val(""); $('#sBuild').val("");
            doSend("getAllRegions");
    
        });

        $("#sRegion").change(function(){
            $('#sPolis').val("");  $('#sBranchType').val(""); $('#sBranch').val(""); $('#sArea').val(""); $('#sBuildType').val(""); $('#sBuild').val("");
            doSend("getAllPolis");
        });

        //$("#sPolis").change(function(){
        //$('#sBranchType').val(""); $('#sBranch').val(""); $('#sArea').val(""); $('#sBuildType').val(""); $('#sBuild').val("");
        //doSend("getAllAreas");
        //});        

        $("#sBranchType").change(function(){
            $('#sBranch').val(""); $('#sArea').val(""); $('#sBuildType').val(""); $('#sBuild').val("");
            doSend("getAllBranch");
        });       


        $("#sBranch").change(function(){
            $('#sArea').val(""); $('#sBuildType').val(""); $('#sBuild').val("");
            doSend("getAllBuild");
        });       


        //$("#sBuildType").change(function(){
        //$('#sBuild').val("");
        //doSend("getAllBuild");
        //});       

        //$("#sArea").change(function(){
        //$('#sBuild').val("");    
        //doSend("getAllBuild");
        //}); 

        $("#sBuild").change(function(){
            doSend("getAllPart");
        }); 

        $("#sPart").change(function(){
            doSend("getAllCell");
        }); 





        $("#cmdAddBuild").click(function(){
            doSend("AddBuild");
        }); 



        $("#cmdSave").click(function(){
            doSend("Save");
        }); 



    </script>





</body>

</html>

