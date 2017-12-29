package mage.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Util method to work with threads.
 *
 * @author ayrat
 */
@SuppressWarnings("unchecked")
public final class ThreadUtils {

    public static final ThreadPoolExecutor threadPool;
    public static final ThreadPoolExecutor threadPool2;
    public static final ThreadPoolExecutor threadPool3;
    private static int threadCount;

    static {
        /**
         * used in CardInfoPaneImpl
         *
         */
        threadPool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "Util" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        });
        threadPool.prestartAllCoreThreads();

        /**
         * Used for MageActionCallback
         */
        threadPool2 = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "TP2" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        });
        threadPool2.prestartAllCoreThreads();
        /**
         * Used for Enlarged view
         */

        threadPool3 = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "EV" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        });
        threadPool3.prestartAllCoreThreads();
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void wait(Object lock) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }
}
