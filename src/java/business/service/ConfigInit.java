package business.service;

import business.Config;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;


@WebServlet(name = "ConfigInit", urlPatterns = {"/ConfigInit"})

public class ConfigInit extends HttpServlet {

    @Override
    public void init() {   
        final String sCase = "init";
        //final Logger oLogSafe = Logger.getLogger(ConfigInit.class);
        //oLogSafe.info("[" + sCase + "]Initialization...");
        System.out.println("[" + sCase + "]Initialization...");
        Config.oConfig(getServletContext().getRealPath(""));
        System.out.println("[" + sCase + "](sPath()=" + Config.sPath() + "):Initialized!");
        //final Logger oLog = Logger.getLogger(ConfigInit.class);
        //oLog.info("[" + sCase + "](sPath()=" + Config.sPath() + "):Initialization finished!");
        final Logger oLog = Logger.getLogger(ConfigInit.class);
        oLog.info("[" + sCase + "](sPath()=" + Config.sPath() + "):Initialization finished!");
    }

     @Override
     public String getServletInfo() {
          return "Ininialization";
     }// </editor-fold>
}
