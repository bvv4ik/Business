package business.launch;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;

/**
 * Запуск метода в виде треда
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class RunClass extends Thread {

    private static Logger oLog = Logger.getLogger(Run.class);
    private String sClass = "";
    private String sMethod = "";
    private Class[] aParamClass;
    private Object[] aParamObject;
    private Object oReturn;

    public RunClass(String sClass, String sMethod, Class[] aParamClass, Object[] aParamObject) {
        this.sClass = sClass;
        this.sMethod = sMethod;
        this.aParamClass = aParamClass;
        this.aParamObject = aParamObject;
    }

    @Override
    public void run() {
        String sCase = "run";
        try {
            Class oClassLink = Class.forName(sClass);
            Object oClass = oClassLink.newInstance();
            Method oMethod = oClassLink.getMethod(sMethod, aParamClass);
            oReturn = oMethod.invoke(oClass, aParamObject);
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sClass=" + sClass + ",sMethod=" + sMethod + "):", oException);
        }
    }

    public Object oReturn() {
        return oReturn;
    }
}
