package com.viewnext.crud_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.viewnext.crud_service.persistence.repository.UserRepositoryI;
import com.viewnext.crud_service.services.UserServiceImp;

@SpringBootTest
class CrudServiceApplicationTests {

	@Mock
    private UserRepositoryI userRepository;
    @InjectMocks
    private UserServiceImp userService;



    @Test
    void contextLoads() {

		assert true;
    }   

}
