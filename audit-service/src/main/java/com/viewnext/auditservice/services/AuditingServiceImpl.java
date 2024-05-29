package com.viewnext.auditservice.services;

import com.viewnext.auditservice.persistence.repository.AuditorRepositoryI;
import com.viewnext.crud_service.persistence.model.AuditingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditingServiceImpl implements AuditingServiceI {

    private final AuditorRepositoryI auditorRepo;

    @Autowired
    public AuditingServiceImpl(AuditorRepositoryI auditorRepo) {
        this.auditorRepo = auditorRepo;
    }

    @Override
    public AuditingData saveAudit(AuditingData audit) {
        return auditorRepo.save(audit);
    }


}
