package com.inventory.management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO implements Serializable {

    private Long id;
    private String name;
    private String department;
    private String email;
}
