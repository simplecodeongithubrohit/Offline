package com.extraaaz.Offline.repository;

import com.extraaaz.Offline.model.CachedData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CachedDataRepository extends JpaRepository<CachedData, Long> {
    CachedData findByEndpoint(String endpoint);
}