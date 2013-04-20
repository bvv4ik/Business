/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 *
 * @author Sergey
 */
public class TimeJVM {
    
    public static void main(String[] args) throws Exception {
     
        
        for (int a=1; a<=40000; a++) {
  if(a == 39999) {
    break;
  }
  System.out.println(a + " ");
}
System.out.print("Конец");
        
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

        //
        // Returns the up time of the Java virtual machine in
        // milliseconds.
        //
        long upTime = bean.getUptime();
        System.out.printf("Up Time = %d (ms)", upTime);
        
    }
    
}
