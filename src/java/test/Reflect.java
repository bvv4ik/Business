/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.lang.reflect.*;
/**
 *
 * @author Ser
 */
public class Reflect {
    
  public static void main(String[] args) {
      
        Test1 object = new Test1();
        Class clazz = object.getClass();
           try{
         Method method = clazz.getMethod("ser",new Class[] {String.class});      
         //if(method!=null) {//вызываем}
           //Object result =  
                   method.invoke(object, new Object[] {"run"});
           // System.out.println("Result = " + result);
           //}
         
        }catch(Exception e){
        }
        
        
            //Object result = method.invoke(object);
            //System.out.println("Result = " + result);
  /*
Class c = Test1.getClass();
Method Main ;
try{Main=c.getMethod("Main",Test1.class);
     } catch (Exception ex) {}
if(Main!=null) {//вызываем}
    }
*/


  }
  
}
