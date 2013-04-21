

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
              
     String sCurrentRow1  = "";
     String sCurrentRow2  = "";
     String sCurrentRow3  = "";
     
      String sResultRow  = ""; //list1.get(2).toString();
    String s = "";
    String IdRegion1 = "";
    String TypeRegion1 = "";
    String NameRegion1 = "";
    
    String IdRegion2 = "";
    String TypeRegion2 = "";
    String NameRegion2 = "";
    
    String IdRegion3 = "";
    String TypeRegion3 = "";
    String NameRegion3 = "";
    
    for(int i=0;i<900/*list1.size()*/;i++) {
    //sCurrentRow = list3.get(i).toString();
    //String formatted = String.format("%07d", i);
    
        
        sCurrentRow1 = list1.get(i).toString();
        sCurrentRow2 = list2.get(i).toString();
        sCurrentRow3 = list3.get(i).toString();
        
        
      //  if (sCurrentRow.endsWith("00000000")){
      //      sCurrentRow = "0-0-";
      //  }
        
        //if (list3.get(i).toString().contains("00000000")){
          sCurrentRow1 = list1.get(i).toString();
       if (sCurrentRow1.endsWith("00000000")){
           NameRegion1 = list3.get(i).toString();
           IdRegion1 = String.format("%07d", i+1); //Integer.toString(i);           
        }
       if (sCurrentRow3.startsWith("АВТОНОМНАЯ РЕСПУБЛИКА")){
           TypeRegion1 = "1"; 
           }
        if (sCurrentRow3.endsWith("ОБЛАСТЬ")){
           TypeRegion1 = "2"; 
           }
    
       
       if (sCurrentRow1.substring(5, 10) == "00000"){
       NameRegion2 = list3.get(i).toString();
       IdRegion2 = String.format("%07d", i);
       }
       if (sCurrentRow3.endsWith("РАЙОН")){
           TypeRegion2 = "3"; 
           }
      
       
       
          if ((sCurrentRow1.substring(6, 7) == "8") & 
              (sCurrentRow1.substring(7, 9) != "00") &
              (sCurrentRow1.substring(8, 10) == "00")) {
       NameRegion3 = list3.get(i).toString();
       IdRegion3 = String.format("%07d", i);
       }
       if (sCurrentRow2.endsWith("С")){
           TypeRegion3 = "4"; 
           }
       if (sCurrentRow2.endsWith("Щ")){
           TypeRegion3 = "5"; 
           }
    
       
       if ((sCurrentRow1.substring(6, 7) == "8") & 
              (sCurrentRow1.substring(7, 9) != "00") &
              (sCurrentRow1.substring(8, 10) != "00")) {
           
            sResultRow = sResultRow + "\n" +IdRegion1+" "+TypeRegion1+" "+NameRegion1
                    + " " +IdRegion2+" "+TypeRegion2+" "+NameRegion2
                    + " " +IdRegion3+" "+TypeRegion3+" "+NameRegion3;   
            }
      // }
       //if (sResultRow == null) sResultRow = "s";
       
        
      //  s = s + "\n"+ list3.get(i).toString();
           //System.out.println(list1.get(i).toString());
     }
    // s = s + list1.size();
    
    
//    if (copy(s,6,5)= '00000') and
//   (copy(s,3,1)= '2') then         // 2 òîëüêî ðàéîí  1 ãîðîäà
//   (copy(s,4,2)<> '00')        then   //èíä ðàéîíà
  //       begin
  //       RayName :=  {richedit0.Lines[i]+}FormatFloat('00000#',i+1)+ ''''+  richedit3.Lines[i];
   //      end;
    
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
         <%=sResultRow%> 
        </pre>
    </body>
</html>
