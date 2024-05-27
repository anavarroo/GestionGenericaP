package com.viewnext.auditservice.controllers;

import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.auditservice.services.AuditingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/audit")
public class AuditingController {

    private final AuditingServiceImpl auditingMngm;

    @Autowired
    public AuditingController(AuditingServiceImpl auditingMngm) {
        this.auditingMngm = auditingMngm;
    }

    @PostMapping
    public ResponseEntity<AuditingData> createAudit(@RequestBody AuditingData audit) {
        auditingMngm.saveAudit(audit);
        return new ResponseEntity<>(audit, HttpStatus.CREATED);
    }
}
