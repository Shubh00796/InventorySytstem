package com.inventory.management.Mapper;

import com.inventory.management.Dtos.BinDTO;
import com.inventory.management.Dtos.QueueTicketDTO;
import com.inventory.management.Model.Bin;
import com.inventory.management.Model.QueueTicket;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    QueueTicketDTO toDto(QueueTicket queueTicket);

    QueueTicket toEntity(QueueTicketDTO queueTicketDTO);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(QueueTicketDTO queueTicketDTO, @MappingTarget QueueTicket queueTicket);


}
