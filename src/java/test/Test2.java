/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
   
public class Test2 {
      //  public static final Logger LOG=Logger.getLogger(Test2.class);
      //private final static Logger logger =  Logger.getRootLogger();
      //static Logger logger = Logger.getLogger(Test2.class);
       static Logger logger = Logger.getLogger(Test2.class);
       
      public static void main(String[] args) throws Exception {
      // DOMConfigurator.configure(Config.sPathConfig() + "log4j.xml");     
       DOMConfigurator.configure("log4j.xml");
       System.out.println(" run Test2 2222222222");    
    logger.debug("Log4j appender configuration is successful !!");
       logger.info("Twest");   
         logger.info("[" + "sCase" + "](sLogin="+"sLogin"+"):Ok!111");
    // LOG.info("Hello World!");
    String s1 = "Test1ddddddddd";
    s1 = null;
    logger.info(s1);   
    }
    
}


