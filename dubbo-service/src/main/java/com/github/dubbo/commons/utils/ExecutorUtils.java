package com.github.dubbo.commons.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具：可用来执行异步任务<br>
 * 
 * @author yuzhu.peng
 * @since 2017/03/13
 *
 */
public final class ExecutorUtils {
    
    /**
     * 核心线程数
     */
    public static final int CORE_POOL_SIZE = 200;
    
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 200;
    
    /**
     * 最大等待时间
     */
    private static final long MAX_IDLE_SECOND = 120L;
    
    /**
     * 存放排队线程的队列
     */
    private static LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
    
    /**
     * 线程池service
     */
    private static ExecutorService service = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, MAX_IDLE_SECOND,
        TimeUnit.SECONDS, blockingQueue);
    
    /**
     * 提交任务<br>
     * 
     * @param runnable
     * @author yuzhu.peng
     * @since 2017年3月13日
     */
    public static final void execute(Runnable runnable) {
        try {
            service.execute(runnable);
        }
        catch (RejectedExecutionException e) {
            e.printStackTrace();
            new Thread(runnable).start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 提交任务并返回Future，future.get()会在任务执行成功时获取返回值<br>
     * 
     * @param task
     * @return
     * @author yuzhu.peng
     * @since 2017年3月13日
     */
    public static final Future<?> submit(Runnable task) {
        Future<?> future = null;
        try {
            future = service.submit(task);
        }
        catch (Exception e) {
            e.printStackTrace();
            future = null;
        }
        return future;
    }
}
