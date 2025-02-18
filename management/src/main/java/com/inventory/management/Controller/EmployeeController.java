package com.inventory.management.Controller;

import com.inventory.management.Dtos.EmployeeDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Creating a new employee.");
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        log.info("Getting employee by ID: {}", id);
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@PathVariable String email) {
        log.info("Getting employee by email: {}", email);
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @RequestParam(value = "department", required = false) String department,
            Pageable pageable) {
        log.info("Getting all employees with optional department filter.");
        Page<EmployeeDTO> employees = employeeService.getAllEmployees(department, pageable);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{email}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable String email,
            @RequestBody EmployeeDTO employeeDTO) {
        log.info("Updating employee with email: {}", email);
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(email, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
