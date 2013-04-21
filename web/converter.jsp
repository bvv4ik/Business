

<%@page import="com.bw.converter.LoadTextFile"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.bw.converter.ItemDataStream.aSourceType"%>
<%@page import="com.bw.converter.ItemDataStream"%>
<%@page import="com.bw.converter.ItemDataTable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>


<!-- %@page contentType="text/html; charset=UTF-8"% -->

<%
    request.setCharacterEncoding("UTF-8");
    
    LoadTextFile o = new LoadTextFile();
    List list1 = new ArrayList(); 
         list1 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/1.txt");
    List list2 = new ArrayList(); 
         list2 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
    List list3 = new ArrayList(); 
         list3 = o.ListFromFile("D:/Java_study/---Projects/KOATUU/3.txt");
              
    String s  = ""; //list1.get(2).toString();
    
    for(int i=0;i<list1.size()/*list1.size()*/;i++) {
    s = s + "\n"+ list3.get(i).toString();
           //System.out.println(list1.get(i).toString());
     }
    s = s + list1.size();
    
    
  
    
    //LoadTextFile lf2 = new LoadTextFile();
    //lf2.ListFromFile("D:/Java_study/---Projects/KOATUU/2.txt");
    
 // HashMap m=new ItemDataStream()._Source(aSourceType.file, "D:/Java_study/---Projects/KOATUU/KOATUU_11122012.xls")
 //           .mColumn("sID", 1, 100);
    //,sTypeShort,sNameOriginal
//List<Integer> keyList = Collections.list(Collections.enumeration(map.keySet()));

    
 //   ItemDataTable o=new ItemDataTable(m);
 //   int nRows=o.nRows("sID"),nIndexPolise=0,nIndexRegion=0;
  //  String sRegionTypeLast="";
  
  //   String s = valueList.get(1);
     
          
   // for(int nRow=0;nRow<nRows;nRow++){
      //  o._Row(nRow);
        

    
        
       
        /*
        o._("sNewColumn", o.s("sID")+"_"+nRow);
        String sPoliseType=
            o.s("sTypeShort").startsWith("С")?"Село":
            o.s("sTypeShort").startsWith("М")?"Місто":
            o.s("sTypeShort").startsWith("Т")?"СМТ":
            o.s("sTypeShort").startsWith("Щ")?"Селище":
                "";
        o._("sPoliseType", sPoliseType);
        String sRegionType=
            o.s("sTypeShort").startsWith("Р")?"Район":
            o.s("sNameOriginal").indexOf("РАЙРАДІ")>=0?"Райрада":
            o.s("sNameOriginal").indexOf("МІСЬКРАДІ")>=0?"Міськрада":
            o.s("sNameOriginal").indexOf("АВТОНОМНА РЕСПУБЛІКА")>=0?"АР":
            o.s("sNameOriginal").startsWith("РАЙОНИ ")?"Район":
            o.s("sNameOriginal").startsWith("СІЛЬРАДИ")?"Сільрада":
                sRegionTypeLast;
        
        o._("sRegionType", sRegionType);
        sRegionTypeLast=sRegionType; 
        
        if("".equals(sPoliseType)){
            nIndexRegion++;
        }else{
            nIndexPolise++;
        }
        */   
        
      //  o._("nRow", nRow+"");
     //   o._("nIndexPolise", nIndexPolise+"");
     //   o._("nIndexRegion", nIndexRegion+"");
     //   o._("sNewColumn", o.s("sNameOriginal").toLowerCase());
   //  }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <pre> 
         <%=s%> 
        </pre>
    </body>
</html>
