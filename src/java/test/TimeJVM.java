
package test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 *
 * @author Sergey
 */
public class TimeJVM {

    public static void main(String[] args) throws Exception {

      long start = System.currentTimeMillis();
        
    // RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
     String s = "";
     
     Runtime r = Runtime.getRuntime();
     long mem1, mem2, mem3;
     
     r.gc();
     
                System.out.println("Used Memory   : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024 + " Mbytes");
                System.out.println("Free Memory   : " + Runtime.getRuntime().freeMemory()/1024 + " Mbytes");
                System.out.println("Total Memory  : " + Runtime.getRuntime().totalMemory()/1024 + " Mbytes");
                System.out.println("Max Memory    : " + Runtime.getRuntime().maxMemory()/1024 + " Mbytes");    
     
     
     //mem1= r.totalMemory();     System.out.println("Всего: "+ mem1);
     //r.gc();

     StringBuilder sBuild = new StringBuilder();
     StringBuffer sBuffer = new StringBuffer("");
     StringBuffer sBuffer1 = new StringBuffer("");
        for (int a = 1; a <= 1000; a++) {

           //s += a+"\n"; // 18 300 ms - 100 000
           //s += "453fgdgd ghgh dfgh dfgвапвыапвыпвыпвыавпh  df"+"\n"; // 18 300 ms - 100 000
           //s.concat(Integer.toString(a)).concat("\n"); // 1113 ms - 10 000 000   --- 38693 Mbytes
           s.concat("453fgdgd ghgh dfgh dfgh  df").concat("\n"); // 1113 ms - 10 000 000   --- 38693 Mbytes
           // String c = s.concat(Integer.toString(a)).concat("\n"); // 994 ms - 10 000 000
           //sBuild.append(a).append("\n"); // 655 ms - 10 000 000      ---- 594172 Mbytes
          // sBuffer.append(a).append("\n");  // 940 ms - 10 000 000    ---  594172 Mbytes
                                     // при 100 000 000 // нехватает памяти  StringBuilder и StringBuffer
               //sBuffer1.append((r.totalMemory() - r.freeMemory())).append("\n");
               //System.out.println("Used Memory   : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024 + " Mbytes");
        }
        // long upTime = bean.getUptime();
        // System.out.printf("Up Time = %d (ms)", upTime);
        
        long stop = System.currentTimeMillis();
        System.out.println((double)(stop-start)/1000 + " seconds");
        
        System.out.println("\n\n");
        // System.out.println(s);
        //System.out.println(sBuffer1.toString());
        //System.out.println(sBuffer.toString());
       // System.out.println(sBuild.toString());

        
           System.out.println("Used Memory   : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024 + " Mbytes");
           System.out.println("Free Memory   : " + Runtime.getRuntime().freeMemory()/1024 + " Mbytes");
           System.out.println("Total Memory  : " + Runtime.getRuntime().totalMemory()/1024 + " Mbytes");
           System.out.println("Max Memory    : " + Runtime.getRuntime().maxMemory()/1024 + " Mbytes");   
        
        
    
        
      //  stop = System.currentTimeMillis();
      //  System.out.println((double)(stop-start)/1000 + " seconds");
        
       // System.out.println(sBuffer.toString());
       ///System.out.print(s);
       
       // upTime = bean.getUptime();
       // System.out.printf("Up Time = %d (ms)", upTime);

    }
}
