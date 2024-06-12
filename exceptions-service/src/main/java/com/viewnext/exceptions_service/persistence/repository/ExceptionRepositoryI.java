package com.viewnext.exceptions_service.persistence.repository;

import com.viewnext.exceptions_service.persistence.model.ExceptionHandler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionRepositoryI extends JpaRepository<ExceptionHandler, Long> {
}
