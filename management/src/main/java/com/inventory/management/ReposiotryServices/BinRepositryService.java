package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Bin;
import com.inventory.management.Repo.BinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BinRepositryService {
    private final BinRepository binRepository;

    public List<Bin> findAllBins() {
        return binRepository.findAll();
    }

    public Bin saveBin(Bin bin) {
        return binRepository.save(bin);
    }

    public Bin findBinById(Long binId) {
        return binRepository.findById(binId)
                .orElseThrow(() -> new ResourceNotFoundException("Id with given id found" + binId));
    }

    public void deleteBin(Long binId) {
        Bin bin = binRepository.findById(binId).orElseThrow(() -> new ResourceNotFoundException("Id with given bin not found" + binId));
        binRepository.delete(bin);

    }
}
