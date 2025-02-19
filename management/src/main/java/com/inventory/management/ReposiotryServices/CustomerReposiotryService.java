package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.CustomerDTO;
import com.inventory.management.Exceptions.CustomerNotFoundException;
import com.inventory.management.Exceptions.DuplicateEmailException;
import com.inventory.management.HelperClasses.CustomerSpecification;
import com.inventory.management.Model.Customer;
import com.inventory.management.Repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerReposiotryService {
    private final CustomerRepository customerRepository;

    public Optional<Customer> retriveCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        customerRepository.findByEmail(customer.getEmail())
                .ifPresent(existingCustomer -> {
                    throw new DuplicateEmailException(customer.getEmail());
                });

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with given id not found" + id));

        Optional.ofNullable(customerDTO.getStatus()).ifPresent(customer::setStatus);
        Optional.ofNullable(customerDTO.getFirstName()).ifPresent(customer::setFirstName);
        Optional.ofNullable(customerDTO.getLastName()).ifPresent(customer::setLastName);
        return customerRepository.save(customer);


    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

    public Page<Customer> saveCustomersBySpeccifications(String firstName, String lastName, String email, String status, Pageable pageable) {
        Specification<Customer> spec = Specification.where(CustomerSpecification.firstNameContains(firstName))
                .and(CustomerSpecification.lastNameContains(lastName))
                .and(CustomerSpecification.emailContains(email))
                .and(CustomerSpecification.statusEquals(status));

        return customerRepository.findAll(spec, pageable);

    }
}
