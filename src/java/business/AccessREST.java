package business;

/**
 * Доступ через REST-сервисы
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class AccessREST extends com.bw.io.ItemAccessREST {

    @Override
    public void logging(String sMessage, Exception _, boolean bAlert, boolean bTrace) {
        Log.Do(sMessage, _, bAlert, bTrace, Log.oLogAccessREST);
    }
}
