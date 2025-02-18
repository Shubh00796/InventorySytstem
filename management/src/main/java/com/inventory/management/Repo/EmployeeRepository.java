package com.inventory.management.Repo;

import com.inventory.management.Model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface  EmployeeRepository extends JpaRepository<Employee , Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByEmail(String email);

    Page<Employee> findByDepartment(String department, Pageable pageable);
}
