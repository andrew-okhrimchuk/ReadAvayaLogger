package main.mongo;


import com.mongodb.client.*;
import com.mongodb.client.model.InsertManyOptions;
import static java.util.stream.Collectors.toList;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.lang.NonNull;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.nonBlockingEchoServer.util.ToCalls;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@Slf4j
@NoArgsConstructor
public class ServiceCallsMongoDB {
    private static MongoClient mongoClient = MongoConfig.instance;
    private static MongoDatabase sampleTrainingDB = mongoClient.getDatabase("calls_documents");
    public static MongoCollection<Document> gradesCollection = sampleTrainingDB.getCollection("calls");

    public void insertOneDocument (@NonNull ToCalls calls) {
        log.info("start insertOneDocument");
        Gson gson = new Gson();
        gradesCollection.insertOne(Document.parse(gson.toJson(calls)));
    }
    public void insertManyDocuments (@NonNull List<ToCalls> doc) {
        log.info("start insertManyDocuments");

        gradesCollection.insertMany(DocumentsToCalls(doc), new InsertManyOptions().ordered(false));
    }
    private List<Document> DocumentsToCalls(@NonNull List<ToCalls> calls){
        Gson gson = new Gson();
        return calls.stream()
                .map(doc -> Document.parse(gson.toJson(doc)) )
                .collect(toList());
    }
    public List<ToCalls> findBeetwDate (@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        log.info("start findBeetwDate");
        int day1 = start.getDayOfMonth();
        int day2 = end.getDayOfMonth();
        int month1 = start.getMonth().getValue();
        int month2 = end.getMonth().getValue();
        int years1 = start.getYear();
        int years2 = end.getYear();

        Document  query = new Document ();
        Map<String, Document > andQuery = new HashMap<>();  //BasicDBObject instead  Document
        andQuery.put("day", new Document ("$gte", day1).append("$lte", day2));
        andQuery.put("month", new Document ("$gte", month1).append("$lte", month2));
        andQuery.put("years", new Document ("$gte", years1).append("$lte", years2));
        query.putAll(andQuery);

        return gradesCollection.find(query, ToCalls.class)
                .into(new ArrayList<>());

    }
    public List<ToCalls> findBeetwDateAndWay (@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num) {
        log.info("start findBeetwDate");
        int day1 = start.getDayOfMonth();
        int day2 = end.getDayOfMonth();
        int month1 = start.getMonth().getValue();
        int month2 = end.getMonth().getValue();
        int years1 = start.getYear();
        int years2 = end.getYear();

        Document  query = new Document ();
        Map<String, Document > andQuery = new HashMap<>();  //BasicDBObject instead  Document
        andQuery.put("day", new Document ("$gte", day1).append("$lte", day2));
        andQuery.put("month", new Document ("$gte", month1).append("$lte", month2));
        andQuery.put("years", new Document ("$gte", years1).append("$lte", years2));
        if (way !=0 ){andQuery.put("cond_code", new Document ("$eq", way));}
        if (num != null ){andQuery.put("calling_num", new Document ("$eq", num));}

        query.putAll(andQuery);

        FindIterable<ToCalls> docs = gradesCollection.find(query, ToCalls.class);

        return gradesCollection.find(query, ToCalls.class)
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
}
