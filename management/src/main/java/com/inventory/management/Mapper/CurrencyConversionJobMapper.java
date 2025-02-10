package com.inventory.management.Mapper;

import com.inventory.management.Dtos.CurrencyConversionJobDto;
import com.inventory.management.Dtos.CurrencyConversionJobRequestDto;
import com.inventory.management.Dtos.DocumentConversionJobDto;
import com.inventory.management.Dtos.DocumentConversionJobRequestDto;
import com.inventory.management.Model.CurrencyConversionJob;
import com.inventory.management.Model.DocumentConversionJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrencyConversionJobMapper {
    CurrencyConversionJobDto toDto(CurrencyConversionJob job);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "convertedAmount", ignore = true)
    @Mapping(target = "conversionStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CurrencyConversionJob toEntity(CurrencyConversionJobRequestDto dto);
}