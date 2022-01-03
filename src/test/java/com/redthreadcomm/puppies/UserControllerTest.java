package com.redthreadcomm.puppies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest

public class UserControllerTest {

    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    @Test
    public void authenticateUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(new File("src/test/resources/authUser.json"),User.class);
        String s  = userController.authenticateUser(user);
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.verify(userRepository,Mockito.times(1)).findById(Mockito.any());

    }

    @Test
    public void createNewUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(new File("src/test/resources/user.json"),User.class);
        userController.createNewUser(user);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any());
        Assertions.assertNotNull(user);
    }
}
