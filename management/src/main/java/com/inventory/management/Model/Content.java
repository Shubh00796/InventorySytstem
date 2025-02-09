package com.inventory.management.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    private String id;
    private String title;
    private String type;
    private String description;
}
