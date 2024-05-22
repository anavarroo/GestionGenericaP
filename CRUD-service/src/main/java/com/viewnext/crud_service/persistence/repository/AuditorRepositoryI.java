package com.viewnext.crud_service.persistence.repository;

import com.viewnext.crud_service.persistence.model.AuditingData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditorRepositoryI extends JpaRepository<AuditingData, Long> {
}
