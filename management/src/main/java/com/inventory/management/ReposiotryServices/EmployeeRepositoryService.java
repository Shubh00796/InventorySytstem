package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.EmployeeDTO;
import com.inventory.management.Exceptions.DuplicateEmailException;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Employee;
import com.inventory.management.Repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeRepositoryService {

    private final EmployeeRepository repository;

    public Page<Employee> retriveAllEmployees(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public Page<Employee> retrieveEmployeesByDepartment(String department, Pageable pageable) {
        return repository.findByDepartment(department, pageable);
    }

    public Optional<Employee> retriveEmployeeById(Long id) {
        return repository.findById(id);
    }


    public Optional<Employee> retriveEmployeeByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }


    public Employee updateEmployee(String email, EmployeeDTO employeeDTO) {
        Employee employee = updateEmployeesIfNotNull(email, employeeDTO);

        return repository.save(employee);


    }

    public void deleteEmployee(Long id) {
        Employee employee = deleteEmployeeById(id);

        repository.delete(employee);

    }

    private Employee deleteEmployeeById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found for the given Employee" + id));
        return employee;
    }

    private Employee updateEmployeesIfNotNull(String email, EmployeeDTO employeeDTO) {
        Employee employee = repository.findByEmail(email)
                .orElseThrow(() -> new DuplicateEmailException("Employee with the given email id not found:" + email
                ));
        Optional.ofNullable(employeeDTO.getName()).ifPresent(employee::setName);
        Optional.ofNullable(employeeDTO.getDepartment()).ifPresent(employee::setDepartment);
        return employee;
    }


}
