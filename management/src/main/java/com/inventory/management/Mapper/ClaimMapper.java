package com.inventory.management.Mapper;

import com.inventory.management.Dtos.BinDTO;
import com.inventory.management.Dtos.ClaimDTO;
import com.inventory.management.Model.Bin;
import com.inventory.management.Model.Claim;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    ClaimDTO toDto(Claim claim);

    Claim toEntity(ClaimDTO claimDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "policyNumber", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ClaimDTO claimDTO, @MappingTarget Claim claim);
}

