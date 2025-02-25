package com.inventory.management.events;

import com.inventory.management.Model.Bin;
import com.inventory.management.ServiceImpl.BinServiceImpl;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClaimSubmittedEvent extends ApplicationEvent {
    private final Long claimId;


    public ClaimSubmittedEvent(Object source, Long claimId) {
        super(source);
        this.claimId = claimId;
    }

    public Long getClaimId() {
        return claimId;
    }
}
