package com.inventory.management.service;

import com.inventory.management.Dtos.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO request);
    Optional<EmployeeDTO> getEmployeeById(Long id);
    EmployeeDTO getEmployeeByEmail(String email);
    Page<EmployeeDTO> getAllEmployees(String department, Pageable pageable);
    EmployeeDTO updateEmployee(String email, EmployeeDTO request);
    void deleteEmployee(Long id);
}
