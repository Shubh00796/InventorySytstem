package com.inventory.management.VendorInterfaces;

import com.inventory.management.Model.MeterReading;

public interface MeterReadingAdapter<T> {
    MeterReading adapt(T dto);
}