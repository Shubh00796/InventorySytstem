package com.inventory.management.events;

import com.inventory.management.Model.Bin;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class BinFullEvent extends ApplicationEvent {
    private final Bin bin;


    public BinFullEvent(Bin bin) {
        super(bin);
        this.bin = bin;
    }
}
