package com.inventory.management.DesignPatternInterfaces;

import com.inventory.management.Model.PropertyListing;

public interface ListingAdapter<T> {
    PropertyListing convert(T source);
}