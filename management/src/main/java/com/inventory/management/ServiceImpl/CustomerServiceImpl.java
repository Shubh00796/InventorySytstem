package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.CustomerDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.CustomerMapper;
import com.inventory.management.Model.Customer;
import com.inventory.management.ReposiotryServices.CustomerReposiotryService;
import com.inventory.management.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerReposiotryService reposiotryService;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO request) {
        Customer entity = customerMapper.toEntity(request);
        Customer savedToRepo = reposiotryService.saveCustomer(entity);


        return customerMapper.toDTO(savedToRepo);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return Optional.ofNullable(reposiotryService.retriveCustomerById(id).map(customerMapper::toDTO).orElseThrow(() -> new ResourceNotFoundException("Id with given Customer not found" + id)));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO request) {
        return customerMapper.toDTO(reposiotryService.updateCustomer(id, request));
    }

    @Override
    public void deleteCustomer(Long id) {
        reposiotryService.deleteCustomer(id);

    }

    @Override
    public Page<CustomerDTO> searchCustomers(String firstName, String lastName, String email, String status, Pageable pageable) {
        Page<Customer> customerPage = reposiotryService.saveCustomersBySpeccifications(firstName, lastName, email, status, pageable);

        return customerPage.map(customerMapper::toDTO);
    }
}
