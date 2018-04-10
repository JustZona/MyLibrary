package com.example.mylibrary.ThreadRun;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zy on 2017/2/6.
 * 线程池.
 */
public class ThreadPoolFramework {

    /**线程池核心线程数.*/
    private static int CORE_POOL_SIZE = 10;
    /**线程池最大线程数.*/
    private static int MAX_POOL_SIZE = 100;
    /** 额外线程空状态生存时间.*/
    private static long KEEP_ALIVE_TIME = 60;
    /**阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程.*/
    private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

    public static ThreadPoolExecutor threadPoolExecutor;

    static {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,KEEP_ALIVE_TIME, TimeUnit.SECONDS,workQueue,new ThreadPoolExecutor.AbortPolicy());
    }

    public static void init(ThreadPoolExecutor threadPoolExecutor){
        ThreadPoolFramework.threadPoolExecutor.shutdownNow();
        ThreadPoolFramework.threadPoolExecutor = null;
        ThreadPoolFramework.threadPoolExecutor = threadPoolExecutor;
    }

    /**
     * 添加线程.
     * @param runnable
     */
    public static void execute(Runnable runnable){
        threadPoolExecutor.execute(runnable);
    }
}
