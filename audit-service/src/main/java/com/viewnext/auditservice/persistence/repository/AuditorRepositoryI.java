package com.viewnext.auditservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditorRepositoryI extends JpaRepository<AuditingData, Long> {
}
