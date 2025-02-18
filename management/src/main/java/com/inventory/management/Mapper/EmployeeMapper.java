package com.inventory.management.Mapper;


import com.inventory.management.Dtos.EmployeeDTO;
import com.inventory.management.Dtos.EventDTO;
import com.inventory.management.Model.Employee;
import com.inventory.management.Model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO toDTO(Employee employee);
    Employee toEntity(EmployeeDTO  employeeDTO);
}