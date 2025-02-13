package com.inventory.management.Dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrbanListingDTO {
    private String headline;
    private String summary;
    private Double cost;
    private String location;
    private String dateListed;
    private String agent;
}