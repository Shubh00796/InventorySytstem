package com.inventory.management.Dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDTO {
    private String id;
    private String title;
    private String type;
    private String description;
}
