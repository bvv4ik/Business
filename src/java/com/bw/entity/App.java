/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.entity;
  import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author Sergey
 */


public class App {
     
     
     public static Timer timer = new Timer();
     public static int TimerCount = 0;
     
     
public static class RunMeTask extends TimerTask
{     
	@Override
	public void run() {
		System.out.println("Run Me ~");
                TimerCount++;
                if (TimerCount == 5)
                timer.cancel();
	}
}   
 

    public static void main( String[] args )
    {
    	TimerTask task = new RunMeTask();    	//Timer timer = new Timer();
    	timer.schedule(task, 100,1000);
    }

     
}
