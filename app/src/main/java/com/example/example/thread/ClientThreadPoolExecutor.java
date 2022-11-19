package com.example.example.thread;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientThreadPoolExecutor {
    private static final String TAG = ClientThreadPoolExecutor.class.getSimpleName();
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE_TIME = 30L;
    private static final long WAIT_COUNT = 128;

    private static ThreadPoolExecutor pool = createThreadPoolExecutor();
    //UI线程，优先级较低
    private static ExecutorService jobsForUI = Executors.newFixedThreadPool(
            CORE_POOL_SIZE, new ClientThreadFactory("ClientJobsForUI", Thread.NORM_PRIORITY - 1));

    private static ThreadPoolExecutor createThreadPoolExecutor() {
        if (pool == null) {
            pool = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(128),
                    new ClientThreadFactory("ClientThreadPool", Thread.NORM_PRIORITY),
                    new ClientHandlerException());
        }
        return pool;
    }

    public static void runInBackground(Runnable runnable) {
        if (pool == null) {
            createThreadPoolExecutor();
        }
        pool.execute(runnable);
    }


    private static Thread mainThread;
    private static Handler mainHandler;

    static {
        Looper mainLooper = Looper.getMainLooper();
        mainThread = mainLooper.getThread();
        mainHandler = new Handler(mainLooper);
    }

    public static boolean isOnMainThread() {
        return mainThread == Thread.currentThread();
    }

    public static void runOnMainThread(Runnable runnable) {
        if (isOnMainThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    public static void runOnMainThread(Runnable runnable, long delay) {
        if (delay <= 0) runOnMainThread(runnable);
        else mainHandler.postDelayed(runnable, delay);
    }

    public static class ClientThreadFactory implements ThreadFactory {
        private AtomicInteger counter = new AtomicInteger();
        private String prefix = "";
        private int priority = Thread.NORM_PRIORITY;

        public ClientThreadFactory(String prefix, int priority) {
            this.prefix = prefix;
            this.priority = priority;
        }

        public ClientThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread executor = new Thread(runnable, prefix + " #" + counter.getAndIncrement());
            executor.setDaemon(true);
            executor.setPriority(priority);
            return executor;
        }
    }

    private static class ClientHandlerException extends ThreadPoolExecutor.AbortPolicy {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            Log.d(TAG, "rejectedExecution:" + r);
            Log.e(TAG, logAllThreadStackTrace().toString());
            //Tips.showForce("任务被拒绝", 5000);
            if (!pool.isShutdown()) {
                pool.shutdown();
                pool = null;
            }
            pool = createThreadPoolExecutor();
        }
    }

    public static StringBuilder logAllThreadStackTrace() {
        StringBuilder builder = new StringBuilder();
        Map<Thread, StackTraceElement[]> liveThreads = Thread.getAllStackTraces();
        for (Thread key : liveThreads.keySet()) {
            builder.append("Thread ").append(key.getName())
                    .append("\n");
            StackTraceElement[] trace = liveThreads.get(key);
            for (StackTraceElement stackTraceElement : trace) {
                builder.append("\tat ").append(stackTraceElement).append("\n");
            }
        }
        return builder;
    }

}
