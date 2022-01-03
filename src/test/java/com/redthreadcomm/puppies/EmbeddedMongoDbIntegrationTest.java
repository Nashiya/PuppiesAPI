package com.redthreadcomm.puppies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = PuppiesApplication.class)
//@DataMongoTest(classes = PuppiesApplication.class)
public class EmbeddedMongoDbIntegrationTest {
    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;
    @After
    void clean() {
        mongodExecutable.stop();
    }
    @Test
    void contextLoads() {
    }
    @Before
    void setup() throws Exception {
        String ip = "localhost";
        int port = 27017;
        //MongoConfig.builder(ip,port);
        IMongodConfig  mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://localhost:27017"),
                "puppies");
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

