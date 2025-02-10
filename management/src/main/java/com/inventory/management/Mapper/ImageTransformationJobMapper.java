package com.inventory.management.Mapper;

import com.inventory.management.Dtos.DocumentConversionJobDto;
import com.inventory.management.Dtos.DocumentConversionJobRequestDto;
import com.inventory.management.Dtos.ImageTransformationJobDto;
import com.inventory.management.Model.DocumentConversionJob;
import com.inventory.management.Model.ImageTransformationJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageTransformationJobMapper {
    ImageTransformationJob toEntity(ImageTransformationJobDto imageTransformationJobDto);
    ImageTransformationJobDto toDTO(ImageTransformationJob imageTransformationJob);

}