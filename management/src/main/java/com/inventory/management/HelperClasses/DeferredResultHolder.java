package com.inventory.management.HelperClasses;

import com.inventory.management.Dtos.ClaimDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeferredResultHolder {

    private final Map<Long, DeferredResult<ResponseEntity<ClaimDTO>>> resultMap = new ConcurrentHashMap<>();

    public void add(Long claimId, DeferredResult<ResponseEntity<ClaimDTO>> result) {
        resultMap.put(claimId, result);
    }

    public Optional<DeferredResult<ResponseEntity<ClaimDTO>>> getId(Long claimId) {
        return Optional.ofNullable(resultMap.get(claimId));
    }

    public void remove(Long claimId) {
        resultMap.remove(claimId);
    }
}
