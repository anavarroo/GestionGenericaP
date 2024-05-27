package com.viewnext.auditservice.persistence.repository;

import com.viewnext.auditservice.persistence.model.AuditingData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditorRepositoryI extends JpaRepository<AuditingData, Long> {
}