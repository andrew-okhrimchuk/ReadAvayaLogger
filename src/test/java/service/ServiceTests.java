package service;

import main.nonBlockingEchoServer.util.ToCalls;
import main.nonBlockingEchoServer.util.UtilText;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import static org.hamcrest.core.Is.is;


public class ServiceTests extends AdstractServiceTests {
    public static void after (){
        serviceCallsMongoDB.deleteCollection("calls_test");
    }
    @Test
    public void simple ()  {

    }
    @Test
    public void insertOneDocument () {
        UtilText util = new UtilText();
        ToCalls doc = util.strToCalls("flslgksdmfgd");

        serviceCallsMongoDB.insertOneDocument(doc);
        List<ToCalls> docs = serviceCallsMongoDB.findAll();
        System.out.println(docs);
        Assert.assertThat(docs.size() , is(1));

        serviceCallsMongoDB.deleteOneDocument(doc);
        List<ToCalls> docs2 = serviceCallsMongoDB.findAll();
        Assert.assertThat(docs2.size() , is(0));
    }
    @Test
    public void findDocuments () {
        UtilText util = new UtilText();
        List<ToCalls> doc = util.StringToListToCalls("fasfsdfasdfas");
        serviceCallsMongoDB.insertManyDocuments(doc);
        List<ToCalls> docs2 = serviceCallsMongoDB.findBeetwDate(LocalDateTime.MIN, LocalDateTime.MAX);
        System.out.println(docs2);
    }
}
