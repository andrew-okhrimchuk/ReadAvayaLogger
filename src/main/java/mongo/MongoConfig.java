package mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoConfig {
    private static MongoClient instance;

    private MongoConfig() {
    }

    //static block initialization for exception handling
    static {
        try {
            instance = init();
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static MongoClient getInstance() {
        return instance;
    }

    private static MongoClient init() {
        // log.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        log.info("run method MongoClient init");
        log.warn("not run method MongoClient init");
        String connectionString = System.getProperty("mongodb.uri");
        return MongoClients.create(connectionString);
    }
}
