package business;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Конфигурационные параметры и функции
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class Config {

    private static volatile Config oConfig;
    private static String sPath = "D:/My Documents/NetBeansProjects/Business/web/";    // Было "" и не работало
    private static Logger oLogStatic = Logger.getLogger(Config.class);
    private final static Map mVariable = java.util.Collections.synchronizedMap(new java.util.HashMap());

    private Config(String sPath) {
        _Path(sPath);
    }

    public static Config oConfig(String sPath) {
        Config oInstance = oConfig;
        if (oInstance == null && sPath != null) {
            synchronized (Config.class) {
                oInstance = oConfig;
                if (oInstance == null) {
                    oConfig = oInstance = new Config(sPath);
                }
            }
        }
        return oInstance;
    }

    /**
     * Установить путь к корневому каталогу приложения
     *
     * @param sPath путь
     * @return this
     */
    private Config _Path(String sPath) {
        Config.sPath = sPath;
        //Инициализации дефолтных значений и путей:
        DOMConfigurator.configure(Config.sPathConfig() + "log4j.xml");
        return this;
    }

    /**
     * @return Путь к корневому каталогу приложения
     */
    public static String sPath() {
        return sPath + File.separator;
    }

    /**
     * @return Путь к каталогу конфигурацй приложения
     */
    public static String sPathConfig() {
        return sPath() + "WEB-INF" + File.separator + "config" + File.separator;
    }

    /**
     * @return Путь к каталогу с настройками баз приложения
     */
    public static String sPathDB() {
        return sPathConfig() + "db";

    }

    /**
     * Значение переменной, перебирая все возможные источники и добавляя ее в
     * общий массив переменных.
     *
     * @param sName имя
     * @return
     */
    public static String sValue(String sName) {
        String sValue = (String) mVariable.get(sName);
        if (null == sValue) {
            if (bCashed(sName)) {
                sValue = (String) mVariable.get(sName);
            }
            if (sValue == null) {
                sValue = sValueFile(sName);
                if (sValue != null) {
                    mVariable.put(sName, sValue);
                }
            }

        }
        return null == sValue ? "" : sValue;
    }

    /**
     * Закеширована-ли ли переменная контекста (с переносом в общий массив для
     * переменных)
     *
     * @param sName
     * @return
     */
    private static boolean bCashed(String sName) {
        String sCase = "bCashed";
        boolean b = false;
        try {
            javax.naming.Context oContext = (javax.naming.Context) new javax.naming.InitialContext().lookup("java:comp/env");
            String sValue = "";
            Object oValue = null;
            try {
                oValue = oContext.lookup(sName);
            } catch (Exception oException) {
                oLogStatic.info("[" + sCase + "](sName=" + sName + "):no value:" + oException.getMessage());
            }
            try {
                if (oValue != null) {
                    sValue = oValue.toString();
                    b = true;
                    mVariable.put(sName, sValue);
                } else {
                    oLogStatic.warn("[" + sCase + "](sName=" + sName + "):null value:");
                }
            } catch (Exception oException) {
                oLogStatic.error("[" + sCase + "](sName=" + sName + "):fail value:", oException);
            }
            oLogStatic.info("[" + sCase + "](sName=" + sName + ",sValue=" + sValue + ")");
        } catch (Exception oException) {
            oLogStatic.error("[" + sCase + "](sName=" + sName + "):", oException);
        }
        return b;
    }

    /**
     * Значение переменной файла свойств
     *
     * @param sName имя
     * @return
     */
    public static String sValueFile(String sName) {
        String sCase = "sValueFile";
        String sValue = null;
        Properties oProperty = new Properties();
        try {
            oProperty.load(new FileInputStream(sPathConfig() + "application.properties"));
            sValue = oProperty.getProperty(sName);
            //mVariable.put(sName, sValue);
            oLogStatic.info("[" + sCase + "](sName=" + sName + ",sValue=" + sValue + ")");
        } catch (Exception oException) {
            oLogStatic.error("[" + sCase + "](sName=" + sName + "):", oException);
        }
        return sValue;
    }
}
