package com.viewnext.register_service.persistence.repository;

import com.viewnext.register_service.persistence.model.AuditingData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditorRepositoryI extends JpaRepository<AuditingData, Long> {
}
