package com.example.mylibrary.ThreadRun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zy on 2017/2/6.
 * 线程池.
 */
public class ThreadPool{

    /**
     * NOW:代表执行完当前任务，
     * 对于已经接受但还未执行的
     * 任务进行销毁.
     *
     * AFTER:执行完所有已经接受
     * 的任务，不再接受新的任务.
     */
    public enum Model{
        NOW,
        AFTER
    }
	
	static {
		cachedThreadPool = Executors.newCachedThreadPool();
		fixedThreadPool = Executors.newFixedThreadPool(10);
		scheduledThreadPool  = Executors.newScheduledThreadPool(10);
		singleThreadExecutor = Executors.newSingleThreadExecutor();
	}

    public static int MAX_EX=10;
    public static int MAX_Sc=10;
    /**
     * CachedThreadPool创建一个可缓存线程池,
     * 如果线程池长度超过处理需要，
     * 可灵活回收空闲线程，若无可回收，则新建线程.
     * @param runnable
     */
    private static ExecutorService cachedThreadPool;
    public static ExecutorService CachedThreadPool(Runnable runnable) {
        if (cachedThreadPool.isShutdown()){
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        cachedThreadPool.execute(runnable);
        return cachedThreadPool;
    }

    /**
     * 创建一个定长线程池，
     * 可控制线程最大并发数，
     * 超出的线程会在队列中等待.
     */
    private static ExecutorService fixedThreadPool;
    public static ExecutorService FixedThreadPool(Runnable runnable){
        if (fixedThreadPool.isShutdown()){
            fixedThreadPool = Executors.newFixedThreadPool(MAX_EX);
        }
        fixedThreadPool.execute(runnable);
        return fixedThreadPool;
    }

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行.
     * @param runnable
     * @param delay
     * 开始延迟时间.
     * @param time
     * 循环间隔时间.
     * 单位:s
     * 返回值为Future类型,cancel方法终止任务.
     * cancel参数为true则立即终止，false则任务完成后终止.
     *
     */
    private static ScheduledExecutorService scheduledThreadPool;
    public static Future ScheduledThreadPool(long delay,long time,Runnable runnable){
        if (scheduledThreadPool.isShutdown()){
            scheduledThreadPool  = Executors.newScheduledThreadPool(MAX_Sc);
        }
        return scheduledThreadPool .scheduleAtFixedRate(runnable,delay,time, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建一个单线程化的线程池，
     * 它只会用唯一的工作线程来执行任务，
     * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行.
     */
    private static ExecutorService singleThreadExecutor;
    public static ExecutorService SingleThreadExecutor(Runnable runnable){
        if (singleThreadExecutor.isShutdown()){
            singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        singleThreadExecutor.execute(runnable);
        return singleThreadExecutor;
    }


    /**
     * 关闭各个线程池.
     * @param model
     */
    public static void clear(Model model){
        shutCached(model);
        shutFixed(model);
        shutScheduled(model);
        shutSingle(model);
    }

    public static void shutSingle(Model model){
        if (!scheduledThreadPool.isShutdown()){
            if (singleThreadExecutor!=null){
                switch (model){
                    case NOW:
                        singleThreadExecutor.shutdownNow();
                        break;
                    case AFTER:
                        singleThreadExecutor.shutdown();
                        break;
                }
            }
        }

    }

    public static void shutScheduled(Model model){
        if (!scheduledThreadPool.isShutdown()){
            if (scheduledThreadPool!=null){
                switch (model){
                    case NOW:
                        scheduledThreadPool.shutdownNow();
                        break;
                    case AFTER:
                        scheduledThreadPool.shutdown();
                        break;
                }
            }
        }

    }

    public static void shutFixed(Model model){
        if (!fixedThreadPool.isShutdown()){
            if (fixedThreadPool!=null){
                switch (model){
                    case NOW:
                        fixedThreadPool.shutdownNow();
                        break;
                    case AFTER:
                        fixedThreadPool.shutdown();
                        break;
                }
            }
        }

    }

    public static void shutCached(Model model){
        if (!cachedThreadPool.isShutdown()){
            if (cachedThreadPool!=null){
                switch (model){
                    case NOW:
                        cachedThreadPool.shutdownNow();
                        break;
                    case AFTER:
                        cachedThreadPool.shutdown();
                        break;
                }
            }
        }
    }


}
