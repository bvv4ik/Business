/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import com.bw.io._;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SERG
 */
public class Regular {
    
      public static String sClearQuote (String sInput, String sFirst, String sLast){
         String s = sInput;
        
        String sOutput = "";
       
        String firstStr = sFirst;    //"/*";
        String secondStr = sLast;    //"*/";
        int nStatPosition = 0;
        int nEndPosition = 0;
        int nCurrentIndex = 0;
 
         // пока встречается в строке последний символ
         while (s.indexOf(secondStr, nCurrentIndex) != -1) {
                
        nStatPosition = s.indexOf(firstStr, nCurrentIndex);
        nEndPosition = s.indexOf(secondStr,nCurrentIndex)+secondStr.length();
       // System.out.println(s.substring(nStatPosition, nEndPosition));
        s = s.replace(s.substring(nStatPosition, nEndPosition), "");
        
        //nCurrentIndex = nEndPosition+secondStr.length();
        //s = s.replaceFirst(s.substring(nStatPosition, nEndPosition), "");          
       // s.replaceFirst(s.substring(nStatPosition, nEndPosition), "");
        //sOutput += s.substring(nStatPosition, nEndPosition);
        }
        
   
      return  s;
      }
    
    public static String sGetQuote (String sInput, String sFirst, String sLast){
        String s = sInput;
        String sOutput = "";
       
        String firstStr = sFirst;    //"}";
        String secondStr = sLast;    //"{";
        int nStatPosition = 0;
        int nEndPosition = 0;
        int nCurrentIndex = 0;
 
         // пока встречается в строке последний символ
         while (s.indexOf(secondStr, nCurrentIndex) != -1) {
                
        nStatPosition = s.indexOf(firstStr, nCurrentIndex);
        nEndPosition = s.indexOf(secondStr,nCurrentIndex)+secondStr.length();
        //System.out.println(s.substring(nStatPosition, nEndPosition));
        nCurrentIndex = nEndPosition+secondStr.length();
        //s = s.replaceFirst(s.substring(nStatPosition, nEndPosition), "");          
       // s.replaceFirst(s.substring(nStatPosition, nEndPosition), "");
        sOutput += s.substring(nStatPosition, nEndPosition);
        }
        
    return sOutput;
    }
    
    
    
    public static void main(String[] args){ 
        
        ArrayList oArrayList = new ArrayList();
        // нужно прежде удалить первый символ "{" в css файле
        //oArrayList = _.sLoadFromFile("C:\\NetBeansProjects\\Business\\web\\css\\jquery-ui-tabs.css");
        oArrayList = _.sLoadFromFile("C:\\NetBeansProjects\\Business\\web\\css\\index.css");
        
        String s = oArrayList.toString();
        
        
        
        s = sClearQuote(s,"/*","*/"); 
        s = sGetQuote(s,"}","{"); 
        s = s.replace(",", "\n").replace("}", "").replace("{", "").replace("\n\n", "\n").replace("\n\n", "\n").replace("\n\n", "\n");
        
       // System.out.println(s);

        String[] sss = s.split("\n");
        
        
        for (int i=0; i < sss.length; i++) {
        if (sss[i].contains("::")){
            sss[i] = "";
        }
        //    oArrayList.add(sss[i]);
        // }
        }
        
//var s = "";
//if($(".ui-widget-content").length>0) {
//s+= ".ui-widget-content"+"\n";
//}
//alert(s);
        String sRun = "var s[] = \"\";";
        //System.out.println(sss[1]);
        for (int i=0; i < sss.length; i++) {
           //sRun += "var s = \"\";";
           sRun += "if($(\""+sss[i]+"\").length>0) {";
           //sRun += "s+= \""+sss[i]+"\"+\"\\n\";";
           sRun += "}\n";
           sRun += "else ";
           sRun += "s = s+ '"    +sss[i]+    "' + \" \\n\\r \" ; ";
          //  String string = sss[i];
             
        }
         sRun += "document.write(s);"; //Эти классы и ИД отсутствуют в ДОМ!
       //  System.out.println("Эти классы и ИД присутствуют в ДОМ!");  
        System.out.println(sRun);          
        
          
//var s = "";
//if($("input::-webkit-input-placeholder ").length>0) {s+= "input::-webkit-input-placeholder"+"\n";}
//alert(s);

//var s = "";
//if($("#divAllSessinList table").length>0) {s+= "#divAllSessinList table"+"\n";}
//alert(s);

//        
        
       
}
    
}
