package com.inventory.management.events;

import com.inventory.management.Model.Bin;
import com.inventory.management.ServiceImpl.BinServiceImpl;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BinFullEvent extends ApplicationEvent {
    private final Bin bin;



    public BinFullEvent(BinServiceImpl binService, Bin bin) {
        super(bin);
        this.bin = bin;
    }
}
