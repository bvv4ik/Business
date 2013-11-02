/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

    private static java.util.Timer timer2 = new java.util.Timer();
    private static TimerTask task = new TimerTask() {
        public void run() {     //Do work!         //    aListAllSession.remove(0);
            System.out.println("1");
            //timer2.cancel();         
        }
    };

    public static void main(String args[]) {

        //  вызывается 1 раз               
        //--------java.util.Timer timer2 = new java.util.Timer();
//  TimerTask task = new TimerTask() {
//      public void run()
//      {     //Do work!         //    aListAllSession.remove(0);
//           System.out.println("1");
//          timer2.cancel();         
//      }
//  };


        timer2.schedule(task, 1000, 1000);



    }
}
