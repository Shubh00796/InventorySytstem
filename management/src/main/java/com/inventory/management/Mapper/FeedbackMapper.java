package com.inventory.management.Mapper;

import com.inventory.management.Dtos.ClaimDTO;
import com.inventory.management.Dtos.FeedbackDTO;
import com.inventory.management.Model.Claim;
import com.inventory.management.Model.Feedback;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    FeedbackDTO toDto(Feedback feedback);

    Feedback toEntity(FeedbackDTO feedbackDTO);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FeedbackDTO feedbackDTO, @MappingTarget Feedback feedback);
}

