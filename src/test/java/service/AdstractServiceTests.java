package service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import main.mongo.MongoConfig;
import main.mongo.ServiceCallsMongoDB;
import org.junit.AfterClass;
import org.junit.BeforeClass;



public abstract class AdstractServiceTests {
    static ServiceCallsMongoDB serviceCallsMongoDB = new ServiceCallsMongoDB();

  /*  @BeforeClass
    public static void before (){
        MongoClient mongoClient = MongoConfig.instance;
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("calls_documents");
        ServiceCallsMongoDB.gradesCollection = sampleTrainingDB.getCollection("calls_test");
    }
    @AfterClass
    public static void after (){
        serviceCallsMongoDB.deleteCollection("calls_test");
    }*/
}
