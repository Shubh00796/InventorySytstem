package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.TaskDTO;
import com.inventory.management.Enums.TaskStatus;
import com.inventory.management.Exceptions.TaskNotFoundException;
import com.inventory.management.Model.Task;
import com.inventory.management.Repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskReposiotryService {



    private final TaskRepository taskRepository;



    public Page<Task> retriveAllTasksFromDb(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Optional<Task> retriveTasksByIdFromDb(Long id) {
        return taskRepository.findById(id);
    }

    public Task saveTasks(Task task, TaskDTO request) {
        checkIfTaskIsAlredayPresentAndThenSetStatus(task);
        Set<Task> dependencies = getDependencies(request);
        task.setDependencies(dependencies);

        return taskRepository.save(task);
    }


    public Task updateTasks(Long id, TaskDTO dto) {
        Task task = updateTasksIfNotNullAndThenSetStatus(id, dto);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with given id not found" + id));

        taskRepository.delete(task);

    }


    private void checkIfTaskIsAlredayPresentAndThenSetStatus(Task task) {
        if (task.getId() != null && taskRepository.existsById(task.getId())) {
            throw new IllegalArgumentException("Task with ID " + task.getId() + " already exists. Task cannot be overwritten.");
        }

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
    }

    private Task updateTasksIfNotNullAndThenSetStatus(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with given id not found" + id));

        Optional.of(dto.getName()).ifPresent(task::setName);
        Optional.of(dto.getDescription()).ifPresent(task::setDescription);
        Optional.of(dto.getRecurrence()).ifPresent(task::setRecurrence);
        Optional.of(dto.getScheduledTime()).ifPresent(task::setScheduledTime);
        task.setStatus(TaskStatus.RUNNING);
        return task;
    }


    public Set<Task> getDependencies(TaskDTO request) {
        if (request.getDependencyIds() == null || request.getDependencyIds().isEmpty()) {
            return Set.of();
        }

        Set<Task> dependencies = taskRepository.findAllById(request.getDependencyIds())
                .stream()
                .collect(Collectors.toSet());

        if (dependencies.size() != request.getDependencyIds().size()) {
            Set<Long> foundIds = dependencies.stream().map(Task::getId).collect(Collectors.toSet());
            Set<Long> missingIds = request.getDependencyIds().stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new TaskNotFoundException("Dependency tasks not found: " + missingIds);
        }

        return dependencies;
    }
}
