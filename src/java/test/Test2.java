/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.log4j.Logger;
//-import org.apache.log4j.xml.DOMConfigurator;

public class Test2 {
    //  public static final Logger LOG=Logger.getLogger(Test2.class);
    //private final static Logger oLog =  Logger.getRootLogger();
    //static Logger oLog = Logger.getLogger(Test2.class);

    static Logger oLog = Logger.getLogger(Test2.class);

    public static void main(String[] args) throws Exception {
        // DOMConfigurator.configure(Config.sPathConfig() + "log4j.xml");     
//-       DOMConfigurator.configure("D:/My Documents/NetBeansProjects/Business/web/WEB-INF/config/log4j.xml");
        System.out.println(" run Test2 2222222222");
        oLog.debug("Log4j appender configuration is successful !!");
        oLog.info("Twest");
        oLog.info("[" + "sCase" + "](sLogin=" + "sLogin" + "):Ok!111");
        // LOG.info("Hello World!");
        String s1 = "Test1ddddddddd";
        s1 = null;
        oLog.info(s1);
    }
}
