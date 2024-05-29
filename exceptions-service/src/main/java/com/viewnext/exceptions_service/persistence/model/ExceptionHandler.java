package com.viewnext.exceptions_service.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Exceptions")
public class ExceptionHandler extends RuntimeException implements Serializable {

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
}
