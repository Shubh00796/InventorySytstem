package com.inventory.management.Mapper;

import com.inventory.management.Dtos.BinDTO;
import com.inventory.management.Dtos.EnergyMeterDTO;
import com.inventory.management.Model.Bin;
import com.inventory.management.Model.EnergyMeter;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnergyMeterMapper {

    EnergyMeterDTO toDto(EnergyMeter energyMeter);

    EnergyMeter toEntity(EnergyMeterDTO energyMeterDTO);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EnergyMeterDTO energyMeterDTO, @MappingTarget EnergyMeter energyMeter);


}
