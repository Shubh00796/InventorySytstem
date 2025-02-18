package com.inventory.management.DesignPatternInterfaces;

import com.inventory.management.Model.MeterReading;

public interface MeterReadingAdapter<T> {
    MeterReading adapt(T dto);
}