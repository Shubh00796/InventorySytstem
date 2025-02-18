package com.inventory.management.HelperClasses;

import com.inventory.management.service.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskSchedulerRunner {
    private final TaskService taskService;
    public TaskSchedulerRunner(TaskService taskService) {
        this.taskService = taskService;
    }
    @Scheduled(fixedDelay = 60000)
    public void scheduleTasks() {
        taskService.executeDueTasks();
    }
}