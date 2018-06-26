package util;

public class LazySingleton {
    private static LazySingleton INSTANCE = new LazySingleton();

    private LazySingleton() {
    }

    public static synchronized LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null)
                    INSTANCE = new LazySingleton();
            }
        }
        return INSTANCE;
    }

}
