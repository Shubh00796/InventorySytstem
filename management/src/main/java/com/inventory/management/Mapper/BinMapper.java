package com.inventory.management.Mapper;

import com.inventory.management.Dtos.BinDTO;
import com.inventory.management.Dtos.ReportDTO;
import com.inventory.management.Enums.ReportType;
import com.inventory.management.Model.Bin;
import com.inventory.management.Model.Report;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BinMapper {

    BinDTO toDto(Bin bin);

    Bin toEntity(BinDTO binDTO);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BinDTO binDTO, @MappingTarget Bin bin);


}
