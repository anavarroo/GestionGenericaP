package com.viewnext.register_service.auditing.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Auditoria")
public class AuditingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "Created_Date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Last_Modified_By")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "Last_Modified_Date")
    @LastModifiedBy
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

}
