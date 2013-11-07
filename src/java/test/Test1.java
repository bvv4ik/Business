
package test;

import java.util.ArrayList;
import business.model.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class Test1 {
private Logger oLog = Logger.getLogger(getClass());
         
         
    public static void main(String[] args) throws Exception {
       DOMConfigurator.configure("D:/My Documents/NetBeansProjects/Business/web/WEB-INF/config/log4j.xml");
       //PropertyConfigurator.configure("D:/My Documents/NetBeansProjects/Business/web/WEB-INF/config/log4j.xml"); 
         long start = System.currentTimeMillis();

//      ArrayList<String> oArr = new ArrayList<String>();
//      PlacePolis oPlacePolis =  new PlacePolis();  
//      oArr = oPlacePolis.getStringAddressPolis("Днеп", 1);
        
       //Test1 t1 = new Test1();
       // String ss = t1.ser("ssdadasd");
      
      
      long stop = System.currentTimeMillis();
      System.out.println((double)(stop-start)/1000 + " seconds");
    }


    
      
    
    public String ser(String s) {
        //String s = "000000000";
        System.out.println(" run Test1 11111111111   "+s);
         oLog.debug("](Время выполнения: "+s);
        return (s);

    }
}
