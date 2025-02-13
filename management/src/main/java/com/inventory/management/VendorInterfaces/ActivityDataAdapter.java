package com.inventory.management.VendorInterfaces;

import com.inventory.management.Model.ActivityData;

public interface ActivityDataAdapter<T> {
    ActivityData adapt(T dto);
}