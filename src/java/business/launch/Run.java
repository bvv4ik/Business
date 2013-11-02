package business.launch;

import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Запуск с контролем числа потоков и времени выполнения
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class Run {

    private static Logger oLogStatic = Logger.getLogger(Run.class);
    RunREST oRunREST = null;
    String sReturnError = "";
    String sReturnAny = "";
    int nReturnStatus = 0;

    /**
     * Запуск метода класса
     *
     * @param sClass название класса
     * @param sMethod название метода
     * @param aParamClass масиив классов параметров
     * @param aParamObject масиив параметров
     * @return
     */
    public Object runClass(String sClass, String sMethod, Class[] aParamClass, Object[] aParamObject) throws Exception {
        return runClass(sClass + "." + sMethod, sClass, sMethod, aParamClass, aParamObject);
    }

    /**
     * Запуск метода класса
     *
     * @param sName имя
     * @param sClass название класса
     * @param sMethod название метода
     * @param aParamClass масиив классов параметров
     * @param aParamObject масиив параметров
     * @return
     */
    public Object runClass(String sName, String sClass, String sMethod, Class[] aParamClass, Object[] aParamObject) throws Exception {
        String sCase = "runClass";
        long nLimitMS = Limit.nLimitMS(sName);
        Limit.poolPlus(sName);
        try {
            RunClass oRun = new RunClass(sClass, sMethod, aParamClass, aParamObject);
            oRun.start();

            oRun.join(nLimitMS);
            if (oRun.isAlive()) {
                oRun.stop();
                oRun.join();
                String sLog = "[" + sCase + "]:interrupted-by-kill:" + sClass + "." + sMethod + " in " + nLimitMS + " ms";
                oLogStatic.info(sLog);
                throw new InterruptedException(sLog);
            } else {
                return oRun.oReturn();
            }
        } catch (Exception oException) {
            oLogStatic.error("[" + sCase + "](sClass=" + sClass + ",sMethod=" + sMethod + "):" + oException);
            throw oException;
        } finally {
            Limit.poolMinus(sName);
        }
    }

    public String sReturnAny() {
        return sReturnAny;
    }

    public String sReturnError() {
        return sReturnError;
    }

    public int nReturnStatus() {
        return nReturnStatus;
    }

    public RunREST oRunREST() {
        if (oRunREST == null) {
            oRunREST = new RunREST();
        }
        return oRunREST;
    }

    public business.AccessREST oREST() {
        return oRunREST().oREST();
    }

    public String sReturnREST() {
        String sCase = "sReturnREST";
        String sReturn = null;
        sReturnAny = "";
        sReturnError = "";
        nReturnStatus = 0;
        int nLimitMS = Limit.nLimitMS("REST", 15000);
        int nDelayMScustom = oREST().nTimeout();
        if (nDelayMScustom > 0) {
            nLimitMS = nDelayMScustom;
        }
        oREST()._Timeout(nLimitMS);
        String sURL = oREST().sURL();
        Map mParams = oREST().mParam();
        oLogStatic.info("[" + sCase + "](nLimitMS=" + nLimitMS + ",sURL=" + sURL + ",mParams=" + mParams + "): Starting...");
        Limit.poolPlus(sURL);
        try {
            RunREST oRun = oRunREST();
            oRun.start();
            oRun.join(nLimitMS);
            if (oRun.isAlive()) {
                oRun.stop();
                oRun.join();
                throw new InterruptedException("interrupted-by-kill:" + sURL + " in " + nLimitMS + " ms");
            } else {
                if (oREST().bReturnedOk()) {
                    sReturn = oREST().sReturn();
                }
                sReturnAny = oREST().sReturn();
                nReturnStatus = oREST().nStatus();
                if (sReturn == null) {
                    sReturnError = oREST().sReturnError();
                }
            }
        } catch (Exception oException) {
            String sError = "[" + sCase + "](sURL=" + sURL + ",mParams=" + mParams + "):" + oException;
            oLogStatic.error(sError);
            sReturnError = sError;
            //throw E;
        } finally {
            Limit.poolMinus(sURL);
        }
        return sReturn;
    }
}
