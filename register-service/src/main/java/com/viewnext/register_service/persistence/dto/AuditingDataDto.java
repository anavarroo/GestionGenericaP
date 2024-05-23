package com.viewnext.register_service.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditingDataDto {

    private long id;
    private String createdBy;
    private Date createdDate;
    private String typeRequest;

}
