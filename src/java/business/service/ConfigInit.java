package business.service;

import business.Config;
import business.launch.Sheduled;
import java.util.concurrent.TimeUnit;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

@WebServlet(name = "ConfigInit", urlPatterns = {"/ConfigInit"}, loadOnStartup=1)
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
        
        
        
        try {
            Class.forName("business.launch.Sheduled");
        } catch (Exception oException) {
            oLog.error("[" + sCase + "]:" + oException.getMessage());
        }

        Sheduled.getExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                String sCase = "Sheduled.getExecutor().schedule.Runnable()";
                try {
                    oLog.info("[" + sCase + "]:...");
                    //TODO:
                    oLog.info("[" + sCase + "]:Ok!");
                } catch (Exception oException) {
                    oLog.error("[" + sCase + "]:", oException);
                }
            }
        }, 0, TimeUnit.SECONDS);
        
        
    }

    @Override
    public String getServletInfo() {
        return "Ininialization";
    }// </editor-fold>
}
