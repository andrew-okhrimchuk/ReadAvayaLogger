package mongo;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class ServiceMongo {
    private static MongoClient mongoClient = MongoConfig_simple.instance;

    MongoDatabase sampleTrainingDB = mongoClient.getDatabase("calls_documents");
    MongoCollection<Document> gradesCollection = sampleTrainingDB.getCollection("calls");

    public void insertOneDocument (Document doc) {
        log.info("start insertOneDocument");
        gradesCollection.insertOne(doc);
    }

    public void insertManyDocuments (List<Document> doc) {
        log.info("start insertManyDocuments");
        gradesCollection.insertMany(doc, new InsertManyOptions().ordered(false));
    }

    public void findBeetwDate (LocalDateTime start, LocalDateTime end) {
        log.info("start findBeetwDate");
        int day1 = start.getDayOfMonth();
        int day2 = end.getDayOfMonth();
        int month1 = start.getMonth().getValue();
        int month2 = end.getMonth().getValue();
        int years1 = start.getDayOfYear();
        int years2 = end.getDayOfYear();

        BasicDBObject query = new BasicDBObject();
        Map<String, BasicDBObject> andQuery = new HashMap<>();
        andQuery.put("day", new BasicDBObject("$gte", day1).append("$lte", day2));
        andQuery.put("month", new BasicDBObject("$gte", month1).append("$lte", month2));
        andQuery.put("years", new BasicDBObject("$gte", years1).append("$lte", years2));
        query.putAll(andQuery);
        FindIterable<Document> docs = gradesCollection.find(query);
 //       query.put("quantity", new BasicDBObject("$gte", day1));

 //       List<BasicDBObject> andQuery1 = new ArrayList<BasicDBObject>();
 //       andQuery1.add(new BasicDBObject("manufacturer", manufacturer));
 //       query.put("price",new BasicDBObject("$gte", lowPrice).append("$lte", highPrice));

 //       query.put("$and", andQuery);

        List<Document> docs2 = gradesCollection.find(query)
                .projection(fields(excludeId(), include("class_id", "student_id")))
                .sort(descending("class_id"))
                //   .skip(2)
                //   .limit(2)
                .into(new ArrayList<>());

    }
}
