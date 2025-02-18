package com.inventory.management.Controller;

import com.inventory.management.Enums.WorkerTaskStatus;
import com.inventory.management.HelperClasses.TaskRequest;
import com.inventory.management.ThreadPoolClaases.JobSchedulerService;
import com.inventory.management.ThreadPoolClaases.JobTask;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/worker/tasks")
@RequiredArgsConstructor
public class JobSchedulerController {

    private final JobSchedulerService jobSchedulerService;

    /**
     * Submits a new task with given priority and maxRetries.
     *
     * @param taskRequest Contains task priority and maxRetries.
     * @return Task ID.
     */
    @PostMapping("/submit")
    public ResponseEntity<String> submitTask(@Valid @RequestBody TaskRequest taskRequest) {
        String taskId = jobSchedulerService.submitTask(() -> {
            try {
                // Simulating task logic with sleep
                Thread.sleep(2000);
                System.out.println("Task executed successfully!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, taskRequest.getPriority(), taskRequest.getMaxRetries());

        return ResponseEntity.ok("Task submitted successfully with ID: " + taskId);
    }

    /**
     * Cancels a task by ID.
     *
     * @param taskId Task ID to cancel.
     * @return Success or failure message.
     */
    @PostMapping("/cancel/{taskId}")
    public ResponseEntity<String> cancelTask(@PathVariable String taskId) {
        boolean cancelled = jobSchedulerService.cancelTask(taskId);
        if (cancelled) {
            return ResponseEntity.ok("Task " + taskId + " cancelled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Task " + taskId + " could not be cancelled.");
        }
    }

    /**
     * Retrieves the status of a task by ID.
     *
     * @param taskId Task ID to check.
     * @return Task status.
     */
    @GetMapping("/status/{taskId}")
    public ResponseEntity<WorkerTaskStatus> getTaskStatus(@PathVariable String taskId) {
        WorkerTaskStatus status = jobSchedulerService.getTaskStatus(taskId);
        return status != null ? ResponseEntity.ok(status) : ResponseEntity.notFound().build();
    }

    /**
     * Retrieves all tasks.
     *
     * @return Collection of all tasks.
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<JobTask>> getAllTasks() {
        return ResponseEntity.ok(jobSchedulerService.getAllTasks());
    }
}