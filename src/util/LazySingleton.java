package util;

public class LazySingleton {
    private static LazySingleton INSTANCE = new LazySingleton();

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
/*    public static synchronized LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null)
                    INSTANCE = new LazySingleton();
            }
        }
        return INSTANCE;
    }*/

}
