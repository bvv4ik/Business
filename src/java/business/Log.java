package business;

import org.apache.log4j.Logger;

/**
 * Логирование
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class Log {

    /**
     * Инициализация своего обьекта-логера
     */
    public static Logger oLog = Logger.getLogger(Log.class);
    public static Logger oLogAccess = Logger.getLogger(business.model.Access.class);
    public static Logger oLogAccessREST = Logger.getLogger(AccessREST.class);
    public static Logger oLogAccessDB = Logger.getLogger(AccessDB.class);
    public static Logger oLogAuthLDAP = Logger.getLogger(business.auth.AccessLDAP.class);
    public static Logger oLogConfig = Logger.getLogger(business.Config.class);
    public static Logger oLogCache = Logger.getLogger(business.cache.Table.class);
    public static Logger oLogLaunch = Logger.getLogger(business.launch.Run.class);
    public static Logger oLogSend = Logger.getLogger(business.send.MailText.class);
    //public static Logger oLogMisc = Logger.getLogger(business.);

    /**
     * Переопределение метода для своего логера
     *
     * @param sMessage данные для логирования
     * @param _ обьект Exception
     * @param bAlert true, если нужно логировать как ошибку
     * @param bTrace true, если нужно выводить StackTrace
     * @param oLog
     */
    public static void Do(String sMessage, Exception oException, boolean bAlert, boolean bTrace, Logger oLog) {//Class oClass

        boolean bToTrace = bAlert && bTrace && oException != null;
        if (bToTrace) {
            oLog.error(sMessage, oException);
        }
        if (sMessage != null && !bToTrace) {
            if (bAlert) {
                oLog.error(sMessage);
            } else {
                oLog.info(sMessage);
            }
        }
    }
}
