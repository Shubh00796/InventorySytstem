package com.inventory.management.Mapper;

import com.inventory.management.Dtos.ContentDTO;
import com.inventory.management.Model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    ContentDTO toDTO(Content content);
    Content toEntity(ContentDTO contentDTO);
}