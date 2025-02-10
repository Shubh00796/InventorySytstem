package com.inventory.management.Repo;

import com.inventory.management.Model.ImageTransformationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageTransformationJobRepository extends JpaRepository<ImageTransformationJob, Long> {
}