package com.inventory.management.HelperClasses;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotNull(message = "Priority must not be null.")
    @Min(value = 0, message = "Priority must be a non-negative number.")
    private Integer priority;

    @NotNull(message = "Max retries must not be null.")
    @Min(value = 0, message = "Max retries must be a non-negative number.")
    private Integer maxRetries;
}