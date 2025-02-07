package com.inventory.management.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {


    private String email;
    private String message;
    private String channel;
    private String subject;

}