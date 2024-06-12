package com.viewnext.exceptions_service.services;

import com.viewnext.exceptions_service.persistence.model.ExceptionHandler;

public interface ExceptionServiceI {

    public ExceptionHandler saveException(ExceptionHandler exception);

}
