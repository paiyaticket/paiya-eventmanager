package events.paiya.eventmanager.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("events.paiya.eventmanager.repositories")
@EnableMongoAuditing
public class MongoDbConfig {
}
