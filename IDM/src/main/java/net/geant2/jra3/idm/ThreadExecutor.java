/*
 * ThreadExecutor.java
 *
 * 2006-02-16
 */
package net.geant2.jra3.idm;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class for responsible for executing Runnable objects. Manages pool of threads.
 * Number of active threads in a pool is accesible through poolSize field.
 * 
 * Class is a singleton.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 */
public class ThreadExecutor {
    private static int minPoolSize = 25;
    private static int maxPoolSize = 50;
    
    private static ThreadPoolExecutor instance = null;

    /**
     * Restricted constructor. Use getInstance instead. 
     *
     */
    private ThreadExecutor() {
    }

    /**
     * Method for obtaining instance of Executor.
     *  
     * @return instance of Executor
     */
    public static ThreadPoolExecutor getInstance() {
        if (instance == null) {
            instance = new ThreadPoolExecutor(minPoolSize, maxPoolSize, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        }

        return instance;
    }

    /**
     * @return Returns the poolSize.
     */
    public static int getMinPoolSize() {
        return minPoolSize;
    }

    /**
     * @param poolSize The poolSize to set.
     */
    public static void setMinPoolSize(int poolSize) {
        ThreadExecutor.minPoolSize = poolSize;
    }
    
    /**
     * @return Returns the poolSize.
     */
    public static int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     * @param poolSize The poolSize to set.
     */
    public static void setMaxPoolSize(int poolSize) {
        ThreadExecutor.maxPoolSize = poolSize;
    }
}
