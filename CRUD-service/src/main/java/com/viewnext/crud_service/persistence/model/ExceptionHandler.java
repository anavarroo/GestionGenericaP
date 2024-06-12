package com.viewnext.crud_service.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionHandler implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Author")
    private String createdBy;

    @Column(name = "Created_Date")
    @CreationTimestamp
    private LocalDate createdDate;

    @Column(name = "EndPoint")
    private String typeRequest;

    @Column(name = "Message")
    private String message;

    @Override
    public String toString() {
        return  id +
                "," + createdBy +
                "," + createdDate +
                "," + typeRequest +
                "," + message;
    }
}
