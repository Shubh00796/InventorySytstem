package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.BinDTO;
import com.inventory.management.Mapper.BinMapper;
import com.inventory.management.Model.Bin;
import com.inventory.management.ReposiotryServices.BinRepositryService;
import com.inventory.management.service.BinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BinServiceImpl implements BinService {
    private final BinRepositryService repositryService;
    private final BinMapper mapper;

    @Override
    public BinDTO createBin(BinDTO binDTO) {
        if(binDTO.getCapacity()<= 0){
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        Bin entity = mapper.toEntity(binDTO);

        return null;
    }

    @Override
    public BinDTO updateBin(BinDTO binDTO) {
        return null;
    }

    @Override
    public Optional<BinDTO> getBinById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<BinDTO> getAllBins() {
        return List.of();
    }

    @Override
    public void deleteBin(Long id) {

    }
}
