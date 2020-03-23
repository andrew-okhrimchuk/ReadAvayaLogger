package service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongo.MongoConfig;
import mongo.ServiceCallsMD;
import nonBlockingEchoServer.util.TextsOne;
import nonBlockingEchoServer.util.ToCalls;
import nonBlockingEchoServer.util.UtilText;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;


//import static nonBlockingEchoServer.server.NonBlockingEchoServer.countTextTest;


public abstract class AdstractServiceTests {
    static ServiceCallsMD serviceCallsMD = new ServiceCallsMD();

    @BeforeClass
    public static void before (){
        MongoClient mongoClient = MongoConfig.instance;
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("calls_documents");
        ServiceCallsMD.gradesCollection = sampleTrainingDB.getCollection("calls_test");
    }
    @AfterClass
    public static void after (){
        serviceCallsMD.deleteCollection("calls_test");
    }
}
