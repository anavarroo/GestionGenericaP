package com.viewnext.auditservice.services;

import com.viewnext.crud_service.persistence.model.AuditingData;

public interface AuditingServiceI {

    public AuditingData saveAudit(AuditingData auditingData);

}
