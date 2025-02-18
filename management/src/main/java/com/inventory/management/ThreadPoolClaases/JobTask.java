package com.inventory.management.ThreadPoolClaases;

import com.inventory.management.Enums.TaskStatus;
import com.inventory.management.Enums.WorkerTaskStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@RequiredArgsConstructor
@ToString
@Slf4j
public class JobTask implements Runnable ,Comparable<JobTask>{

    private final String taskId;
    private final int priority;
    private final Runnable taskLogic;
    private final int maxRetries;
    private final TaskResubmitter resubmitter;
    private volatile WorkerTaskStatus status = WorkerTaskStatus.QUEUED;
    private int retryCount = 0;



    @Override
    public void run() {
        if(isCancelled()){
            logCancellation();
            return;
        }
        transitionToStatus(WorkerTaskStatus.IN_PROGRESS);
        log.info("Executing task {} (priority: {})", taskId, priority);

        try {
            taskLogic.run();
            transitionToStatus(WorkerTaskStatus.COMPLETED);
            log.info("Task {} completed successfully.", taskId);

        } catch (Exception e) {
            log.error("Task {} encountered an error: {}", taskId, e.getMessage());

            handleFailure();
        }


    }

    private void handleFailure() {
        if (canRetry()) {
            incrementRetryCount();
            transitionToStatus(WorkerTaskStatus.QUEUED);
            log.info("Retrying task {} (attempt {}/{})", taskId, retryCount, maxRetries);
            resubmitter.resubmit(this);
        } else {
            transitionToStatus(WorkerTaskStatus.FAILED);
            log.error("Task {} failed after {} retries.", taskId, retryCount);
        }
    }


    private boolean isCancelled(){
        return status == WorkerTaskStatus.CANCELLED;

    }
    private void logCancellation() {
        log.info("Task {} is cancelled before execution.", taskId);
    }
    private void transitionToStatus(WorkerTaskStatus newStatus){
        this.status = newStatus;
    }
    private boolean canRetry() {
        return retryCount < maxRetries;
    }
    private void incrementRetryCount() {
        retryCount++;
    }

    @Override
    public int compareTo(JobTask other) {
        return Integer.compare(this.priority, other.priority);
    }

}
