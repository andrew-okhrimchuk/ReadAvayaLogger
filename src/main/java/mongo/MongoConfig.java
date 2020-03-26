package mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

@Slf4j
@NoArgsConstructor
public class MongoConfig {
    @Getter
    public static MongoClient instance;
    //static block initialization for exception handling
    static {
        try {
            instance = init();
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance " + e.toString());
        }
    }

    private static MongoClient init() {
        log.info("run method MongoClient init");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        log.warn("not run method MongoClient init");
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                .build();

        return MongoClients.create(settings);
    }
}
