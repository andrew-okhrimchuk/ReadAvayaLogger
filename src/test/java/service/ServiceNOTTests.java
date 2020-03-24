package service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import mongo.MongoConfig;
import mongo.ServiceCallsMongoDB;
import nonBlockingEchoServer.util.TextsOne;
import nonBlockingEchoServer.util.ToCalls;
import nonBlockingEchoServer.util.UtilText;
import org.junit.Assert;
import org.junit.Test;
import server.TextsData;

import java.util.List;

import static org.hamcrest.core.Is.is;

public class ServiceNOTTests extends AdstractServiceTests {

  //  @Test
    public void after1 (){
        serviceCallsMongoDB.deleteCollection("calls");
    }

 //   @Test
    public void notSimple () {
        MongoClient mongoClient = MongoConfig.instance;
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("calls_documents");
        ServiceCallsMongoDB.gradesCollection = sampleTrainingDB.getCollection("calls");

        UtilText util = new UtilText();
        serviceCallsMongoDB.insertManyDocuments(util.StringToListToCalls(TextsData.longText));
    }
}
