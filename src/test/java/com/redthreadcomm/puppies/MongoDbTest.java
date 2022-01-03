package com.redthreadcomm.puppies;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.Socket;

@Testcontainers
@SpringBootTest
public class MongoDbTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:4.4.11"));

    @BeforeAll
    static void initAll(){
        container.start();
    }

    @Test
    void containerStartsAndPublicPortIsAvailable(){
        assertThatPortIsAvailable(container);
    }

    private void assertThatPortIsAvailable(MongoDBContainer container) {
        try{
            new Socket(container.getContainerIpAddress(),container.getFirstMappedPort());
        }catch(IOException e){
            throw new AssertionError("The expected port "+container.getFirstMappedPort()+" is not available");
        }
    }


}
