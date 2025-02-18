package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.EmployeeDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.EmployeeMapper;
import com.inventory.management.Model.Employee;
import com.inventory.management.ReposiotryServices.EmployeeRepositoryService;
import com.inventory.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepositoryService repositoryService;
    private final EmployeeMapper mapper;

    @Value("${feature.employee.enabled:true}")
    private boolean employeeFeatureEnabled;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO request) {

        log.info("Creating a new employee with email: {}", request.getEmail());
        Employee entity = mapper.toEntity(request);
        Employee savedEmployee = repositoryService.saveEmployee(entity);
        return mapper.toDTO(savedEmployee);
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        log.info("Fetching employee by ID: {}", id);
        return repositoryService.retriveEmployeeById(id)
                .map(mapper::toDTO);
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        log.info("Fetching employee by email: {}", email);
        return repositoryService.retriveEmployeeByEmail(email)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(String department, Pageable pageable) {
        log.info("Fetching all employees with department filter: {}", department);
        Page<Employee> employeePage = getPaginatedEmployeesAfterFiltering(department, pageable);
        return employeePage.map(mapper::toDTO);
    }

    @Override
    public EmployeeDTO updateEmployee(String email, EmployeeDTO request) {
        log.info("Updating employee with email: {}", email);
        Employee updatedEmployee = repositoryService.updateEmployee(email, request);
        return mapper.toDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        repositoryService.deleteEmployee(id);
    }

    private Page<Employee> getPaginatedEmployeesAfterFiltering(String department, Pageable pageable) {
        return (department != null && !department.isEmpty())
                ? repositoryService.retrieveEmployeesByDepartment(department, pageable)
                : repositoryService.retriveAllEmployees(pageable);
    }
}