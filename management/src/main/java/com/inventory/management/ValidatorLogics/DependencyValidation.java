package com.inventory.management.ValidatorLogics;

import com.inventory.management.Enums.TaskStatus;
import com.inventory.management.Model.Task;
import com.inventory.management.Repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DependencyValidation {

    private final TaskRepository taskRepository;

    /**
     * Executes all due tasks whose dependencies are completed.
     */
    public void executeTasks() {
        LocalDateTime now = LocalDateTime.now();

        List<Task> dueTasks = taskRepository.findByScheduledTimeLessThanEqualAndStatus(now, TaskStatus.PENDING);

        for (Task task : dueTasks) {
            if (!areDependenciesCompleted(task)) {
                continue;  // Skip the task if dependencies are not completed
            }

            try {
                executeTask(task);
            } catch (Exception e) {
                handleTaskFailure(task, e);
            }
        }
    }

    /**
     * Checks if all dependencies of a task are completed.
     *
     * @param task The task to check dependencies for.
     * @return true if all dependencies are completed, false otherwise.
     */
    private boolean areDependenciesCompleted(Task task) {
        return task.getDependencies().stream()
                .allMatch(dep -> dep.getStatus() == TaskStatus.COMPLETED);
    }

    /**
     * Executes a task, handles recurrence, and updates its status.
     *
     * @param task The task to execute.
     */
    private void executeTask(Task task) {
        task.setStatus(TaskStatus.RUNNING);
        taskRepository.save(task);

        try {
            // Simulate task execution
            task.setStatus(TaskStatus.COMPLETED);

            // Handle recurrence logic
            if (task.getRecurrence() != null && !task.getRecurrence().isEmpty()) {
                rescheduleTask(task);
            }
        } finally {
            taskRepository.save(task);
        }
    }

    /**
     * Reschedules a task based on its recurrence interval.
     *
     * @param task The task to reschedule.
     */
    private void rescheduleTask(Task task) {
        try {
            long minutes = Long.parseLong(task.getRecurrence());
            task.setScheduledTime(task.getScheduledTime().plusMinutes(minutes));
            task.setStatus(TaskStatus.PENDING);
        } catch (NumberFormatException e) {
            // If recurrence value is invalid, mark the task as completed
            task.setStatus(TaskStatus.COMPLETED);
        }
    }

    /**
     * Handles task failure by updating its status and logging the error.
     *
     * @param task The task that failed.
     * @param e    The exception that caused the failure.
     */
    private void handleTaskFailure(Task task, Exception e) {
        task.setStatus(TaskStatus.FAILED);
        taskRepository.save(task);
        // Optional: Add proper logging here
        System.err.println("Task execution failed: " + task.getId() + " - " + e.getMessage());
    }
}
