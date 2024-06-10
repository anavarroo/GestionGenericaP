package com.viewnext.auditservice.services;

import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.auditservice.persistence.repository.AuditorRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditingServiceImpl implements AuditingServiceI {

    private final AuditorRepositoryI auditorRepo;
    private final EmailServiceImpl emailService;

    @Autowired
    public AuditingServiceImpl(AuditorRepositoryI auditorRepo, EmailServiceImpl emailService) {
        this.auditorRepo = auditorRepo;
        this.emailService = emailService;
    }

    @Override
    public AuditingData saveAudit(AuditingData audit) {
        AuditingData savedAudit = auditorRepo.save(audit);
        String emailContent = "A new audit entry has been created:\n\n" +
                "Author: " + savedAudit.getCreatedBy() + "\n" +
                "Date: " + savedAudit.getCreatedDate() + "\n" +
                "Endpoint: " + savedAudit.getTypeRequest();
        emailService.sendEmail("alejandro1052004@gmail.com", "New Audit Entry", emailContent);
        return savedAudit;
    }


}
