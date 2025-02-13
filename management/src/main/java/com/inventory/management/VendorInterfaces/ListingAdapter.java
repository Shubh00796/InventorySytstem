package com.inventory.management.VendorInterfaces;

import com.inventory.management.Model.PropertyListing;

public interface ListingAdapter<T> {
    PropertyListing convert(T source);
}