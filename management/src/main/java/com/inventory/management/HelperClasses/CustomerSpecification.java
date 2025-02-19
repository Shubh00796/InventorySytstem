package com.inventory.management.HelperClasses;

import com.inventory.management.Model.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    private static Specification<Customer> likeIgnoreCase(String field, String value) {
        return (root, query, cb) ->
                (value == null || value.isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    private static Specification<Customer> equalsIgnoreCase(String field, String value) {
        return (root, query, cb) ->
                (value == null || value.isEmpty())
                        ? cb.conjunction()
                        : cb.equal(cb.lower(root.get(field)), value.toLowerCase());
    }

    public static Specification<Customer> firstNameContains(String firstName) {
        return likeIgnoreCase("firstName", firstName);
    }

    public static Specification<Customer> lastNameContains(String lastName) {
        return likeIgnoreCase("lastName", lastName);
    }

    public static Specification<Customer> emailContains(String email) {
        return likeIgnoreCase("email", email);
    }

    public static Specification<Customer> statusEquals(String status) {
        return equalsIgnoreCase("status", status);
    }
}