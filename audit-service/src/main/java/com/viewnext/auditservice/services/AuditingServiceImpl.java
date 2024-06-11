package com.viewnext.auditservice.services;

import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.auditservice.persistence.repository.AuditorRepositoryI;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

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

        String to = "correopruebacorreop@gmail.com";
        String subject = "New Audit Entry";
        String body = ("A new audit entry has been created:\n\n" +
                "Author: " + savedAudit.getCreatedBy() + "\n" +
                "Date: " + savedAudit.getCreatedDate() + "\n" +
                "Endpoint: " + savedAudit.getTypeRequest());

        emailService.sendEmail(to, subject, body);

        return savedAudit;
    }


}
