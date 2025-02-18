package com.inventory.management.Repo;

import com.inventory.management.Enums.TaskStatus;
import com.inventory.management.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByScheduledTimeLessThanEqualAndStatus(LocalDateTime time, TaskStatus status);
}