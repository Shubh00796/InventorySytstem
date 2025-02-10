package com.inventory.management.Mapper;

import com.inventory.management.Dtos.DocumentConversionJobDto;
import com.inventory.management.Dtos.DocumentConversionJobRequestDto;
import com.inventory.management.Model.DocumentConversionJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentConversionJobMapper {
    DocumentConversionJob toEntity(DocumentConversionJobDto documentConversionJobDto);
    DocumentConversionJobDto  toDTO(DocumentConversionJob documentConversionJob);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "convertedContent", ignore = true)
    @Mapping(target = "conversionStatus", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    DocumentConversionJob toEntity(DocumentConversionJobRequestDto requestDto);
}