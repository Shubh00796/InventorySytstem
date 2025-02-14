package com.inventory.management.Mapper;


import com.inventory.management.Dtos.ShippingQuoteDTO;
import com.inventory.management.Model.ShippingQuote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShippingQuoteMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    ShippingQuote toEntity(ShippingQuoteDTO quoteDTO);

    ShippingQuoteDTO toDTO(ShippingQuote quote);
}