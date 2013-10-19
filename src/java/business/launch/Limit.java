package business.launch;

import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * Контоль числа запущенных потоков и времени ожидания
 * 
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class Limit {

    public static Logger oLog = Logger.getLogger(Run.class);
    private static HashMap mLimit = new HashMap();

    /**
     * Увеличивает значение счётчика запущенных потоков
     * @param sName имя
     */
    public static void poolPlus(String sName) {
        String sCase = "poolPlus";
        int nPool = 0;
        int nPoolLimit = nLimitMS(sName);
        try {
            if (mLimit.get(sName) == null) {
                nPool = 1;
            } else {
                nPool = Integer.parseInt(mLimit.get(sName).toString(), 10) + 1;
            }
            if (nPool <= nPoolLimit) {
                mLimit.put(sName, Integer.toString(nPool));
            } else {
                oLog.warn("[" + sCase + "](sName=" + sName + ",nPool=" + nPool + ",nPoolLimit=" + nPoolLimit + "):pool overloaded!!!");
            }
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](sName=" + sName + ",nPool=" + nPool + ",nPoolLimit=" + nPoolLimit + "):error limit comparing:", oException);
        }
    }

    /**
     * Уменьшает значение счётчика запущенных потоков
     * @param sName имя
     */
    public static void poolMinus(String sName) {
        String sCase = "poolMinus";
        int nPool = 0;
        try {
            nPool = Integer.parseInt(mLimit.get(sName).toString(), 10) - 1;
            mLimit.put(sName, Integer.toString(nPool));
        } catch (Exception ex) {
            oLog.error("[" + sCase + "](sName=" + sName + ",nPool=" + nPool + "):", ex);
        }
    }
    public static HashMap mPool() {
        return mLimit;
    }
    
    
    /**
     * Лимит ожидания (милисекунд)
     * @param sName имя
     * @return
     */
    static public int nLimitMS(String sName) {
        return nLimitMS(sName, 4000);
    }
    /**
     * Лимит ожидания (милисекунд)
     * @param sName имя
     * @param nDefaultLimitMS дефолтный лимит
     * @return
     */    
    static public int nLimitMS(String sName, int nDefaultLimitMS) {
        String sCase = "nLimitMS";
        return nLimitValue(sName, sCase, nDefaultLimitMS == 0 ? 15000 : nDefaultLimitMS);
    }
    
    /**
     * Лимит пула
     * @param sName имя
     * @return
     */
    static public int nLimitPool(String sName) {
        return nLimitPool(sName, 3);
    }
    /**
     * Лимит пула
     * @param sName имя
     * @param nDefaultLimitPool дефолтный лимит
     * @return
     */
    static public int nLimitPool(String sName, int nDefaultLimitPool) {
        String sCase = "nLimitPool";
        return nLimitValue(sName, sCase, nDefaultLimitPool);
    }
    
    /**
     * Значение лимита, определяется в таблице "LaunchLimit"
     * @param sName имя
     * @param sParamName имя параметра
     * @param nValueDefault значение по умолчанию
     * @return
     */
    static public int nLimitValue(String sName, String sParamName, int nValueDefault) {
        String sCase = "nLimitValue";
        int nLimitValue=nValueDefault;
        try {
            HashMap mRow = business.cache.Table.mRow("LaunchLimit", "sName", sName);
            if (mRow.size() > 0) {
                HashMap mParam = (HashMap) mRow.get("1");
                nLimitValue = Integer.parseInt(mParam.get(sParamName).toString(), 10);
            } else {
                oLog.warn("[" + sCase + "](nValueDefault=" + nValueDefault + ",nLimitValue=" + nLimitValue + ",sName=" + sName + ",sParamName=" + sParamName + "):undefined limit");
            }
        } catch (Exception oException) {
            oLog.error("[" + sCase + "](nValueDefault=" + nValueDefault + ",nLimitValue=" + nLimitValue + ",sName=" + sName + ",sParamName=" + sParamName + "):error limit receiving:", oException);
        }
        return nLimitValue;
    }
    
    
}