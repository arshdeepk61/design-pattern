package org.example.creational;

/**
 * SINGLETON (Creational)
 * ----------------------
 * Intent: Ensure a class has only ONE instance and provide a global point of access to it.
 *
 * Use when:
 *   - Exactly one object is needed to coordinate actions (config, logging, connection pool).
 *
 * Interview points:
 *   - Lazy vs eager initialization.
 *   - Thread safety: double-checked locking with `volatile`, or the Bill Pugh holder idiom.
 *   - The enum singleton (Effective Java) is the simplest fully thread-safe, serialization-safe form.
 */
public class SingletonDemo {

    /** Thread-safe, lazy, no synchronization cost after init: Bill Pugh "holder" idiom. */
    static final class ConfigManager {
        private int loadCount;

        private ConfigManager() {
            loadCount++;
            System.out.println("ConfigManager constructed (count=" + loadCount + ")");
        }

        // The holder class is loaded only when getInstance() is first called -> lazy + thread-safe.
        private static class Holder {
            private static final ConfigManager INSTANCE = new ConfigManager();
        }

        public static ConfigManager getInstance() {
            return Holder.INSTANCE;
        }

        public String get(String key) {
            return "value-for-" + key;
        }
    }

    /** Classic double-checked locking variant (shown for interview comparison). */
    static final class Logger {
        private static volatile Logger instance;

        private Logger() {}

        public static Logger getInstance() {
            if (instance == null) {                 // first check (no lock)
                synchronized (Logger.class) {
                    if (instance == null) {         // second check (with lock)
                        instance = new Logger();
                    }
                }
            }
            return instance;
        }

        public void log(String msg) {
            System.out.println("[LOG] " + msg);
        }
    }

    public static void main(String[] args) {
        ConfigManager a = ConfigManager.getInstance();
        ConfigManager b = ConfigManager.getInstance();
        System.out.println("Same instance? " + (a == b));
        System.out.println(a.get("db.url"));

        Logger.getInstance().log("Application started");
        System.out.println("Logger same instance? " + (Logger.getInstance() == Logger.getInstance()));
    }
}
