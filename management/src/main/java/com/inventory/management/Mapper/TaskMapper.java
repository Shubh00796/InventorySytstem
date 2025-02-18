package com.inventory.management.Mapper;

import com.inventory.management.Dtos.TaskDTO;
import com.inventory.management.Model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({
            @Mapping(source = "dependencies", target = "dependencyIds", qualifiedByName = "mapTasksToIds")
    })
    TaskDTO taskToTaskDTO(Task task);

    @Mappings({
            @Mapping(source = "dependencyIds", target = "dependencies", qualifiedByName = "mapIdsToTasks")
    })
    Task taskDTOToTask(TaskDTO taskDTO);

    @Named("mapTasksToIds")
    default Set<Long> mapTasksToIds(Set<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapIdsToTasks")
    default Set<Task> mapIdsToTasks(Set<Long> ids) {
        return ids.stream()
                .map(id -> {
                    Task task = new Task();
                    task.setId(id);
                    return task;
                })
                .collect(Collectors.toSet());
    }
}
