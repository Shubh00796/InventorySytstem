package com.inventory.management.Configs;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "threadpool")
public class ThreadPoolConfig {


    @Min(1)
    private int size = 10;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
