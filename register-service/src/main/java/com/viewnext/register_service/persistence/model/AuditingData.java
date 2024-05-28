package com.viewnext.register_service.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditingData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Author")
    private String createdBy;

    @Column(name = "Created_Date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "EndPoint")
    private String typeRequest;

    @Override
    public String toString() {
        return  id +
                "," + createdBy +
                "," + createdDate +
                "," + typeRequest;
    }
}
