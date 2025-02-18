package com.inventory.management.service;

import com.inventory.management.Dtos.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskDTO createTask(TaskDTO request);
    TaskDTO getTaskById(Long id);
    Page<TaskDTO> getAllTasks(String status, Pageable pageable);
    TaskDTO updateTask(Long id, TaskDTO request);
    void deleteTask(Long id);
    void executeDueTasks();
}