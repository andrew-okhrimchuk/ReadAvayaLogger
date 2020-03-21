package mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoConfig_simple {
    public static MongoClient instance;
    public static MongoDatabase sampleTrainingDB;

    {
        log.info("run MongoClient init");
        try (MongoClient mongoClient = MongoClients.create(System.getProperty("mongodb.uri"))) {
            instance = mongoClient;
             sampleTrainingDB = mongoClient.getDatabase("sample_training");
            //  MongoCollection<Document> gradesCollection = sampleTrainingDB.getCollection("grades");
        }
    }
}
