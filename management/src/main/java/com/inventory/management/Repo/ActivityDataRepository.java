package com.inventory.management.Repo;

import com.inventory.management.Model.ActivityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityDataRepository extends JpaRepository<ActivityData, Long> {
    Optional<ActivityData> findByDeviceId(String deviceId);
}