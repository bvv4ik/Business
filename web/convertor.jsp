<%@page import="com.bvv.io.ItemDataStream.aSourceType"%>
<%@page import="com.bvv.io.ItemDataStream"%>
<%@page import="com.bvv.io.ItemDataTable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html; charset=UTF-8"%><%
    request.setCharacterEncoding("UTF-8");
    HashMap m=new ItemDataStream()._Source(aSourceType.file, "D:/KOATUU_11122012.xls")
            .mColumn("sID,sTypeShort,sNameOriginal,sNewColumn,sPoliseType,sRegionType,nIndexPolise,nIndexRegion,nRow", 0, 100);
    ItemDataTable o=new ItemDataTable(m);
    int nRows=o.nRows("sID"),nIndexPolise=0,nIndexRegion=0;
    String sRegionTypeLast="";
    for(int nRow=0;nRow<nRows;nRow++){ //
        o._Row(nRow);
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
        o._("nRow", nRow+"");
        o._("nIndexPolise", nIndexPolise+"");
        o._("nIndexRegion", nIndexRegion+"");
        o._("sNewColumn", o.s("sNameOriginal").toLowerCase());
    }
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head> 
    <title>Convertor</title>
</head><body>
    <%=m%>
</body></html>
