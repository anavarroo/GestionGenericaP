package com.viewnext.CRUD_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.viewnext.CRUD_service.persistence.model.Role;
import com.viewnext.CRUD_service.persistence.model.User;
import com.viewnext.CRUD_service.persistence.repository.UserRepositoryI;
import com.viewnext.CRUD_service.services.UserServiceImp;
import java.util.Collection;

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
