package mongo;


import com.mongodb.client.*;
import com.mongodb.client.model.InsertManyOptions;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import static java.util.stream.Collectors.toList;

import com.mongodb.client.result.DeleteResult;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.util.ToCalls;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@Slf4j
@NoArgsConstructor
public class ServiceCallsMD {
    private static MongoClient mongoClient = MongoConfig.instance;
    private static MongoDatabase sampleTrainingDB = mongoClient.getDatabase("calls_documents");
    public static MongoCollection<Document> gradesCollection = sampleTrainingDB.getCollection("calls");

    public void insertOneDocument (ToCalls calls) {
        log.info("start insertOneDocument");
        Gson gson = new Gson();
        gradesCollection.insertOne(Document.parse(gson.toJson(calls)));
    }
    public void insertManyDocuments (List<ToCalls> doc) {
        log.info("start insertManyDocuments");

        gradesCollection.insertMany(DocumentsToCalls(doc), new InsertManyOptions().ordered(false));
    }
    private List<Document> DocumentsToCalls(List<ToCalls> calls){
        Gson gson = new Gson();
        return calls.stream()
                .map(doc -> Document.parse(gson.toJson(doc)) )
                .collect(toList());
    }
    public List<ToCalls> findBeetwDate (LocalDateTime start, LocalDateTime end) {
        log.info("start findBeetwDate");
        int day1 = start.getDayOfMonth();
        int day2 = end.getDayOfMonth();
        int month1 = start.getMonth().getValue();
        int month2 = end.getMonth().getValue();
        int years1 = start.getDayOfYear();
        int years2 = end.getDayOfYear();

        Document  query = new Document ();
        Map<String, Document > andQuery = new HashMap<>();  //BasicDBObject instead  Document
        andQuery.put("day", new Document ("$gte", day1).append("$lte", day2));
        andQuery.put("month", new Document ("$gte", month1).append("$lte", month2));
        andQuery.put("years", new Document ("$gte", years1).append("$lte", years2));
        query.putAll(andQuery);

        FindIterable<ToCalls> docs = gradesCollection.find(query, ToCalls.class);
 //       query.put("quantity", new BasicDBObject("$gte", day1));

 //       List<BasicDBObject> andQuery1 = new ArrayList<BasicDBObject>();
 //       andQuery1.add(new BasicDBObject("manufacturer", manufacturer));
 //       query.put("price",new BasicDBObject("$gte", lowPrice).append("$lte", highPrice));

 //       query.put("$and", andQuery);

        return gradesCollection.find(query, ToCalls.class)
             //   .projection(fields(excludeId(), include("class_id", "student_id")))
             //   .sort(descending("class_id"))
                //   .skip(2)
                //   .limit(2)
                .into(new ArrayList<>());

    }
    public List<ToCalls> findAll (){
        return gradesCollection.find(ToCalls.class).into(new ArrayList<>());
    }
    public DeleteResult deleteOneDocument (ToCalls calls){
        Gson gson = new Gson();
        return gradesCollection.deleteMany(Document.parse(gson.toJson(calls)));
    }
    public void deleteCollection (String collection){
        sampleTrainingDB.getCollection(collection).drop();
    }


   /* @Getter
    public static ServiceCallsMD insTanceS;
    static {
        try {
            insTanceS = new ServiceCallsMD();
            log.info("run static method init insTanceS ServiceCallsMD");
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance " + e.toString());
        }
    }*/

}
