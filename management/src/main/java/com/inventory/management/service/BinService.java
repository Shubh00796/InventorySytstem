package com.inventory.management.service;

import com.inventory.management.Dtos.BinDTO;

import java.util.List;
import java.util.Optional;

public interface BinService {

    BinDTO createBin(BinDTO binDTO);

    BinDTO updateBin(BinDTO binDTO) throws InterruptedException;

    Optional<BinDTO> getBinById(Long id);

    List<BinDTO> getAllBins(int page ,int size);

    void deleteBin(Long id) throws InterruptedException;
}