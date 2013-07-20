<%@page import="com.bvv.io.ItemDataStream.aSourceType"%>
<%@page import="com.bvv.io.ItemDataStream"%>
<%@page import="com.bvv.io.ItemDataTable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html; charset=UTF-8"%><%
    request.setCharacterEncoding("UTF-8");
    HashMap m=new ItemDataStream()._Source(aSourceType.file, "D:/KOATUU_11122012.xls")
            .mColumn("sID,sTypeShort,sName,nRow,nID_RegionType,sRegionType,nID_Region,nID_PolisType,sPolisType,nID_Polis,bRegion,sItemName,nID_Item", 0, 1000);
    ItemDataTable o=new ItemDataTable(m);
    int nRows=o.nRows("sID");//,nIndexPolise=0,nIndexRegion=0
    int nID_Region=0;
    int nID_Polis=0;
    System.out.println("nRows="+nRows);
    String sRegionTypeLast="",sRegionTypeAllLast="",sRows="";
    for(int nRow=0;nRow<nRows;nRow++){ // //
        o._Row(nRow);
        String sID=o.s("sID");
        if(sID.length()==9){
            sID="0"+sID;
        }
        o._("sID", sID);
        
        
        String sRegionTypeAll=//http://localhost:8081/convertor_BW.jsp
            o.s("sName").indexOf("АВТОНОМНА РЕСПУБЛІКА")>=0?"1:АР":
            o.s("sName").indexOf("ОБЛАСТЬ/")>=0?"2:Область":
            (o.s("sName").indexOf("МІСЬКРАДІ")>=0&&!o.s("sName").endsWith("МІСЬКРАДІ"))||o.s("sName").indexOf("РАЙОНИ М.")>=0?"4:Міськрада":
            o.s("sTypeShort").startsWith("Р")||o.s("sName").startsWith("РАЙОНИ ")?"3:Район":
            o.s("sName").indexOf("РАЙРАДІ")>=0?"5:Райрада":
            o.s("sName").startsWith("СІЛЬРАДИ")||o.s("sName").indexOf("/С.")>=0?"7:Сільрада":
            o.s("sName").endsWith(" Р-НУ")||(o.s("sName").startsWith("СЕЛИЩА ")&&o.s("sName").endsWith("МІСЬКРАДІ"))?"6:Селищрада":
                sRegionTypeAllLast;
        
        
//        o._("sNewColumn", o.s("sID")+"_"+nRow);
        String sPolisTypeAll=
            o.s("sTypeShort").startsWith("М")?"1:Місто":
            o.s("sTypeShort").startsWith("Т")?"2:СМТ":
            o.s("sTypeShort").startsWith("С")?"3:Село":
            o.s("sTypeShort").startsWith("Щ")?"4:Селище":
                "";

//        o._("sNewColumn", o.s("sID")+"_"+nRow);
        String sAreaTypeAll=
            o.s("sTypeShort").startsWith("М")?"1:Місто":
            o.s("sTypeShort").startsWith("Т")?"2:СМТ":
            o.s("sTypeShort").startsWith("С")?"3:Село":
            o.s("sTypeShort").startsWith("Щ")?"4:Селище":
                "";
               
        boolean bRegion="".equals(sPolisTypeAll);
        if(bRegion){//ЭТО РЕГИОН!
            if(!"".equals(sRegionTypeAll)){
                String[] asRegionType=sRegionTypeAll.split(":");
                String sRegionType=asRegionType[1];
                int nID_RegionType=Integer.parseInt(asRegionType[0]);
                sRegionTypeAllLast=sRegionTypeAll;
                sRegionTypeLast=sRegionType;
                o._("nID_RegionType", nID_RegionType+"");
                o._("sRegionType", sRegionType);
/*                sRegionTypeLast=sRegionType;*/
            }
        }else{
            if(!"".equals(sPolisTypeAll)){//ЭТО ПОЛИС!!!
                String[] asPolisType=sPolisTypeAll.split(":");
                String sPolisType=asPolisType[1];
                int nID_PolisType=Integer.parseInt(asPolisType[0]);
                o._("nID_PolisType", nID_PolisType+"");
                o._("sPolisType", sPolisType);
            }
        }

        String sItemName=o.s("sName").toLowerCase();
        //o._("sName", o.s("sName").toLowerCase());
        o._("bRegion", bRegion+"");
        int nID_Item;
        if(bRegion){//ЭТО РЕГИОН!
            //INSERT REGION
            nID_Region=nRow;
            o._("nID_Region", nID_Region+"");
            nID_Item=nRow;
        }else{
            nID_Polis=nRow;
            o._("nID_Polis", nID_Polis+"");
            nID_Item=nRow;
        }
        o._("nID_Item",nID_Item+"");
        o._("sItemName",sItemName);
        
        o._("nRow", nRow+"");
        //o._("sName", o.s("sName").toLowerCase());
        String sRow=""+o.s("sID")+"\t"+(bRegion?"/":"")//+"\t"+o.s("nRow")
                //+"\t"+o.s("nID_RegionType")+"\t"+o.s("sRegionType")+"\t"+o.s("nID_Region")
                //+"\t"+o.s("nID_PolisType")+"\t"+o.s("sPolisType")+"\t"+o.s("nID_Polis")
                +(bRegion?//+"\t"
                    o.s("nID_RegionType")+"-"+o.s("sRegionType")+"\t"//+o.s("nID_Region")
                    :
                    o.s("nID_PolisType")+"-"+o.s("sPolisType")+"\t"//+o.s("nID_Polis")
                    )
                +"\t"+o.s("nID_Item")+"-"+o.s("sItemName");//+"\t"+o.s("sTypeShort")//o.s("bRegion")
                //+"\t"+o.s("sTypeShort")+"\t"+o.s("sName");
        
        //String sRow="dddddddd";
        System.out.println(sRow);
        sRows+=sRow+"<br>\n";
    }
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head> 
    <title>Convertor</title>
</head><body>
    <%=sRows%>
</body></html>
