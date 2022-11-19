package com.example.example.connection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteServiceFactory {
    //负责重连
    private ExecutorService bossPool;
    //负责心跳
    private ExecutorService workPool;

    public synchronized void initBossLoopGroup() {
        initBossLoopGroup(1);
    }

    public synchronized void initBossLoopGroup(int size) {
        destroyBossLoopGroup();
        bossPool = Executors.newFixedThreadPool(size);
    }

    public synchronized void initWorkLoopGroup() {
        initWorkLoopGroup(1);
    }

    public synchronized  void initWorkLoopGroup(int size) {
        destroyWorkLoopGroup();
        workPool = Executors.newFixedThreadPool(size);
    }

    public synchronized void destroyBossLoopGroup() {
        if(bossPool != null) {
            try {
                bossPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                bossPool = null;
            }
        }
    }

    public synchronized void destroyWorkLoopGroup() {
        if(workPool != null) {
            try {
                workPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                workPool = null;
            }
        }
    }

    public synchronized void destroy() {
        destroyBossLoopGroup();
        destroyWorkLoopGroup();
    }

    public void executeBossTask(Runnable r) {
        if(bossPool == null) {
            initBossLoopGroup();
        }
        bossPool.execute(r);
    }

    public void executeWorkTask(Runnable r) {
        if(workPool == null) {
            initWorkLoopGroup();
        }
        workPool.execute(r);
    }
}
