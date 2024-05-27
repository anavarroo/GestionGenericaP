package com.viewnext.crud_service.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditingDataDto implements Serializable {

    private long id;
    private String createdBy;
    private Date createdDate;
    private String typeRequest;

}
