package main.mongo;
import main.entity.Calls;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CallsRepository extends MongoRepository<Calls, ObjectId>, CallsRepositoryCustom  {

}