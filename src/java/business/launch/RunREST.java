package business.launch;

import business.AccessREST;

/**
 * Запуск REST-сервиса в виде треда (поддержка HTTP и HTTPS)
 *
 * @author Belyavtsev Vladimir Vladimirovich (BW)
 */
public class RunREST extends Thread {

    private AccessREST oREST = new business.AccessREST();

    /**
     * Ссылка на экземпляр обьекта.
     *
     * @return обьект
     */
    public AccessREST oREST() {
        return oREST;
    }

    @Override
    public void run() {
        oREST.sRequest();
    }
}
