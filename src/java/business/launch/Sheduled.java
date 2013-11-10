package business.launch;

public class Sheduled {

    public static class Holder {

        public static final java.util.concurrent.ScheduledThreadPoolExecutor oProcess = new java.util.concurrent.ScheduledThreadPoolExecutor(16);
    }

    public static java.util.concurrent.ScheduledThreadPoolExecutor getExecutor() {
        return Holder.oProcess;
    }
}
