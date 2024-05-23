package com.viewnext.auditservice.services;

import com.viewnext.auditservice.persistence.model.AuditingData;

public interface AuditingServiceI {

    public AuditingData saveAudit(AuditingData auditingData);

}
