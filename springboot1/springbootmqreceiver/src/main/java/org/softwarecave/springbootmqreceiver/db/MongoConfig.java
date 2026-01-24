package org.softwarecave.springbootmqreceiver.db;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${app.mongodb.uri}")
    private String mongoURI;

    @Value("${app.mongodb.database}")
    private String database;

    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setConnectionString(new ConnectionString(mongoURI));
        return mongo;
    }

    @Bean
    public MongoOperations mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, database);
    }
}
