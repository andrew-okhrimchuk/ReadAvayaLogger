package main.mongo.calls;
import main.entity.Calls;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.annotation.PostConstruct;

public interface CallsRepository extends MongoRepository<Calls, ObjectId>, CallsRepositoryCustom  {


    // https://stackoverflow.com/questions/47055743/spring-data-mongodb-where-to-create-an-index-programmatically-for-a-mongo-coll
    @Configuration
    @DependsOn("mongoTemplate")
    public class CollectionsConfig {

        @Autowired
        private MongoTemplate mongoTemplate;

        @PostConstruct
        public void initIndexes() {
            mongoTemplate
                    .indexOps("calls") // collection name string or .class
                    .ensureIndex(new Index().on("years", Sort.Direction.ASC));
        }
    }
}