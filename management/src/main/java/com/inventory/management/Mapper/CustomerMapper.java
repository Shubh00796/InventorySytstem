package com.inventory.management.Mapper;


import com.inventory.management.Dtos.CustomerDTO;
import com.inventory.management.Dtos.EmployeeDTO;
import com.inventory.management.Model.Customer;
import com.inventory.management.Model.Employee;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO  customerDTO);

    List<CustomerDTO> toDTOList(List<Customer> customers);
}