package com.inventory.management.Repo;

import com.inventory.management.Model.DocumentConversionJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentConversionJobRepository extends JpaRepository<DocumentConversionJob, Long> {
}