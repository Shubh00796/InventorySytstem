package com.inventory.management.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "settings")
    private String settings;

    @Column(name = "roles")
    private String roles;

    @Column(name = "permissions")
    private String permissions;

    private LocalDateTime createdAt;
    @Version
    private Integer version;
    private LocalDateTime updatedAt;
}
