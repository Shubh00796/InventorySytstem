package com.inventory.management.Mapper;

import com.inventory.management.Dtos.VoucherDTO;
import com.inventory.management.FactoryInterfaces.VoucherCodeGenerator;
import com.inventory.management.Model.Voucher;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")

public interface VoucherMapper {

    VoucherDTO toDTO(Voucher voucher);
    Voucher toEntity(VoucherDTO voucherDTO);

    List<VoucherDTO> toDTOList(List<Voucher> vouchers);
    List<Voucher> toEntityList(List<VoucherDTO> voucherDTOs);

    @AfterMapping
    default void generateCodeIfMissing(@MappingTarget Voucher voucher){
        if(voucher.getCode() == null || voucher.getCode().isBlank()){
            voucher.setCode(UUID.randomUUID().toString().toUpperCase());
        }
    }
}