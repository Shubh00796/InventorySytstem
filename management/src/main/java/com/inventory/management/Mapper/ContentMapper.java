package com.inventory.management.Mapper;

import com.inventory.management.Dtos.ContentDTO;
import com.inventory.management.Model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    @Mapping(target = "type", expression = "java(content.getType() != null ? content.getType() : \"Unknown\")")
    ContentDTO toDTO(Content content);

    Content toEntity(ContentDTO dto);
}
