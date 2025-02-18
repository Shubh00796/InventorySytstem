package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.TaskDTO;
import com.inventory.management.Exceptions.TaskNotFoundException;
import com.inventory.management.Mapper.TaskMapper;
import com.inventory.management.Model.Task;
import com.inventory.management.ReposiotryServices.TaskReposiotryService;
import com.inventory.management.ValidatorLogics.DependencyValidation;
import com.inventory.management.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskReposiotryService repositoryService;
    private final TaskMapper mapper;
    private final DependencyValidation dependencyValidation;

    @Override
    public TaskDTO createTask(TaskDTO request) {
        log.info("Creating new task: {}", request);
        Task task = mapper.taskDTOToTask(request);
        Task savedTask = repositoryService.saveTasks(task, request);
        return mapper.taskToTaskDTO(savedTask);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        log.info("Fetching task with ID: {}", id);
        return repositoryService.retriveTasksByIdFromDb(id)
                .map(mapper::taskToTaskDTO)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
    }

    @Override
    public Page<TaskDTO> getAllTasks(String status, Pageable pageable) {
        log.info("Fetching all tasks with status: {}", status);
        return repositoryService.retriveAllTasksFromDb(pageable)
                .map(mapper::taskToTaskDTO);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO request) {
        log.info("Updating task with ID: {}", id);
        return mapper.taskToTaskDTO(repositoryService.updateTasks(id, request));
    }

    @Override
    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);
        repositoryService.deleteTask(id);
    }

    @Override
    public void executeDueTasks() {
        log.info("Executing due tasks...");
        dependencyValidation.executeTasks();
    }
}
