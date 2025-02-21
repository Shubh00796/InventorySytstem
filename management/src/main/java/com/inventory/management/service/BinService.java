package com.inventory.management.service;

import com.inventory.management.Dtos.BinDTO;

import java.util.List;
import java.util.Optional;

public interface BinService {

    BinDTO createBin(BinDTO binDTO);

    BinDTO updateBin(BinDTO binDTO);

    Optional<BinDTO> getBinById(Long id);

    List<BinDTO> getAllBins();

    void deleteBin(Long id);
}