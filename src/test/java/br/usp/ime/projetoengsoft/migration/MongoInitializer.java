package br.usp.ime.projetoengsoft.migration;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static MongoDBContainer instance;

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        List<String> addedProperties = Collections.singletonList(
                "spring.data.mongodb.uri=" + getContainer().getReplicaSetUrl()
        );
        TestPropertyValues.of(addedProperties).applyTo(context.getEnvironment());
    }

    private MongoDBContainer getContainer() {
        if (instance == null) {
            instance = new MongoDBContainer(DockerImageName.parse("mongo:8.0"))
                    .withReuse(true);
            instance.start();
        }
        return instance;
    }
}
