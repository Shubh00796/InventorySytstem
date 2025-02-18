package com.inventory.management.ThreadPoolClaases;

import com.inventory.management.Configs.ThreadPoolConfig;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Slf4j
public class ThreadPoolManager {
    private final BlockingQueue<Runnable> workQueue;
    private final ThreadPoolExecutor executor;
    private final long shutdownTimeout;

    public ThreadPoolManager(ThreadPoolConfig config) {
        this.workQueue = createWorkQueue();
        this.executor = createThreadPoolExecutor(config);
        this.shutdownTimeout = 60; // Default shutdown timeout in seconds
        log.info("Thread pool initialized with {} threads.", config.getSize());
    }
    private ThreadPoolExecutor createThreadPoolExecutor(ThreadPoolConfig config) {
        return new ThreadPoolExecutor(
                config.getSize(),
                config.getSize(),
                0L,
                TimeUnit.MILLISECONDS,
                workQueue,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
    private BlockingQueue<Runnable> createWorkQueue() {
        return new PriorityBlockingQueue<>();
    }

    public Future<?> submitTask(JobTask task){
        Future<?> future = executor.submit(task);
        log.info("Task {} submitted.", task.getTaskId());
        return future;

    }
    public boolean cancelTask(JobTask task){
        boolean remove = workQueue.remove(task);
        if(remove){
            log.info("Task {} removed from the queue.", task.getTaskId());

        }
        return remove;

    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down thread pool...");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(shutdownTimeout, TimeUnit.SECONDS)) {
                log.warn("Forcing shutdown of thread pool...");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Thread pool shutdown interrupted: {}", e.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }



}
