package com.inventory.management.HelperClasses;

import com.inventory.management.Model.Bin;
import org.springframework.stereotype.Service;

@Service
public class BinHelpers {
    public void updateFillLevel(Bin bin, double fillLevel) {
        if (fillLevel < 0 || fillLevel > bin.getCapacity()) {
            throw new IllegalArgumentException("Fill level must be between 0 and capacity.");
        }
        bin.setCurrentFillLevel(fillLevel);
    }

    public boolean isFull(Bin bin) {
        return bin.getCurrentFillLevel() >= (bin.getCapacity() * 0.8);
    }

}
