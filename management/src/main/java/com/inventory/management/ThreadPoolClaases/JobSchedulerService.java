package com.inventory.management.ThreadPoolClaases;

import com.inventory.management.Enums.WorkerTaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobSchedulerService implements TaskResubmitter {
    private final ThreadPoolManager threadPoolManager;
    private final ConcurrentHashMap<String, JobTask> taskMap = new ConcurrentHashMap<>();

    @Override
    public void resubmit(JobTask task) {
        log.info("Resubmitting task {} (retry attempt {}/{})", task.getTaskId(), task.getRetryCount(), task.getMaxRetries());
        threadPoolManager.submitTask(task);
    }
    private String generateTaskId() {
        return UUID.randomUUID().toString();
    }

    public  String submitTask(Runnable taskLogic, int priority , int maxRetries){
        String taskId = generateTaskId();
        JobTask task = new JobTask(taskId, priority, taskLogic, maxRetries, this);
        taskMap.put(taskId, task);
        threadPoolManager.submitTask(task);
        log.info("Task {} submitted successfully.", taskId);
        return taskId;

    }
    public boolean cancelTask(String taskId) {
        return getTaskById(taskId)
                .filter(task -> task.getStatus() == WorkerTaskStatus.QUEUED)
                .map(this::cancelQueuedTask)
                .orElseGet(() -> {
                    log.warn("Task {} could not be cancelled (it may be running or already completed).", taskId);
                    return false;
                });
    }

    public WorkerTaskStatus getTaskStatus(String taskId) {
        return getTaskById(taskId).map(JobTask::getStatus).orElse(null);
    }
    public Collection<JobTask> getAllTasks() {
        return taskMap.values();
    }

    private Optional<JobTask> getTaskById(String taskId){
        return Optional.ofNullable(taskMap.get(taskId));
    }
    private boolean cancelQueuedTask(JobTask task){
        boolean cancelled = threadPoolManager.cancelTask(task);
        if (cancelled) {
            task.setStatus(WorkerTaskStatus.CANCELLED);
            log.info("Task {} cancelled successfully.", task.getTaskId());
        }
        return cancelled;
    }
}
