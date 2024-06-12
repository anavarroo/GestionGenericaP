package com.viewnext.exceptions_service.services;


import com.viewnext.exceptions_service.persistence.model.ExceptionHandler;
import com.viewnext.exceptions_service.persistence.repository.ExceptionRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionServiceI {

    private final ExceptionRepositoryI exceptionRepo;

    @Autowired
    public ExceptionServiceImpl(ExceptionRepositoryI exceptionRepo) {
        this.exceptionRepo = exceptionRepo;
    }

    @Override
    public ExceptionHandler saveException(ExceptionHandler exception) {
        return exceptionRepo.save(exception);
    }


}
