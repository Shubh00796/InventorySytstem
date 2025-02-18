package com.inventory.management.ThreadPoolClaases;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public interface TaskResubmitter {



    public void resubmit(JobTask task);

}