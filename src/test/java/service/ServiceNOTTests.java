package service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import main.mongo.MongoConfig;
import main.mongo.ServiceCallsMongoDB;
import main.nonBlockingEchoServer.util.UtilText;

public class ServiceNOTTests extends AdstractServiceTests {

  //  @Test
    public void after1 (){
        serviceCallsMongoDB.deleteCollection("calls");
    }

 //   @Test
   /* public void notSimple () {
        MongoClient mongoClient = MongoConfig.instance;
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("calls_documents");
        ServiceCallsMongoDB.gradesCollection = sampleTrainingDB.getCollection("calls");

        UtilText util = new UtilText();
        serviceCallsMongoDB.insertManyDocuments(util.StringToListToCalls("gwgwerggeg"));
    }*/
}
