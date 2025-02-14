package com.inventory.management.DesignPatternInterfaces;

import com.inventory.management.Model.ActivityData;

public interface ActivityDataAdapter<T> {
    ActivityData adapt(T dto);
}