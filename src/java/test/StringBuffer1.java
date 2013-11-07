/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.sql.SQLException;
import java.lang.StringBuffer.*;

/**
 *
 * @author Sergey
 */
public class StringBuffer1 {
     
     
          public static void main(String[] args) {
               
         StringBuffer sBuffer = new StringBuffer("Hello World");
         System.out.println(sBuffer.capacity());
         System.out.println(sBuffer.append(10));
         System.out.println(sBuffer.insert(3,"ser"));
         System.out.println(sBuffer.delete(1, 3));
         System.out.println(sBuffer.subSequence(1,7));
         sBuffer.setCharAt(2, 's');
         System.out.println(sBuffer.toString());

StringBuffer stringBuffer2 = new StringBuffer("Hello World");
System.out.println(stringBuffer2.length());
System.out.println(stringBuffer2.capacity());
          
          //StringBuffer buff = new StringBuffer(50);
          //String contents = new StringBuffer(50);
          //String s = new StringBuffer("He is ").append(age);

//          for (int i = 0; i < 10; i++) {
//              // contents.append("hello world\n");
//              
//          }
          }

     private StringBuffer1(String abC) {
          throw new UnsupportedOperationException("Not yet implemented");
     }
     }

  
