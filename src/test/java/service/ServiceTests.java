package service;

import nonBlockingEchoServer.util.TextsOne;
import nonBlockingEchoServer.util.ToCalls;
import nonBlockingEchoServer.util.UtilText;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;

public class ServiceTests extends AdstractServiceTests {

    @Test
    public void simple ()  {

    }
    @Test
    public void notSimple () {
        UtilText util = new UtilText();
        ToCalls doc = util.strToCalls(TextsOne.oneText);

        serviceCallsMD.insertOneDocument(doc);
        List<ToCalls> docs = serviceCallsMD.findAll();
        System.out.println(docs);
        Assert.assertThat(docs.size() , is(1));

        serviceCallsMD.deleteOneDocument(doc);
        List<ToCalls> docs2 = serviceCallsMD.findAll();
        Assert.assertThat(docs2.size() , is(0));
    }
}
