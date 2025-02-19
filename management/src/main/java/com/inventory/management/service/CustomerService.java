package com.inventory.management.service;

import com.inventory.management.Dtos.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO request);
    Optional<CustomerDTO> getCustomerById(Long id);
    CustomerDTO updateCustomer(Long id, CustomerDTO request);
    void deleteCustomer(Long id);
    Page<CustomerDTO> searchCustomers(String firstName, String lastName, String email, String status, Pageable pageable);
}