package com.viewnext.auditservice.services;

import com.viewnext.auditservice.persistence.AuditingDataDto;
import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.auditservice.persistence.repository.AuditorRepositoryI;
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
    public AuditingData saveAudit(AuditingDataDto audit) {
        AuditingData auditingData = convertDtoToAuditingData(audit);
        return auditorRepo.save(auditingData);
    }

    public AuditingData convertDtoToAuditingData(AuditingDataDto auditingDataDto) {
        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(auditingDataDto.getCreatedBy());
        auditingData.setCreatedDate(auditingDataDto.getCreatedDate());
        auditingData.setTypeRequest(auditingDataDto.getTypeRequest());

        return auditingData;
    }

}
