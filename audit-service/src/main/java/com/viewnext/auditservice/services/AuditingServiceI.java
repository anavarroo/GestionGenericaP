package com.viewnext.auditservice.services;

import com.viewnext.common.model.AuditingData;

public interface AuditingServiceI {

    public AuditingData saveAudit(AuditingData auditingData);

}
