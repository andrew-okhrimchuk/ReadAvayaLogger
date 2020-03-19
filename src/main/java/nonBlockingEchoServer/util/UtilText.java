package nonBlockingEchoServer.util;

import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class UtilText {

    public synchronized static List<String> split(String str){
        return Stream.of(str.split("\n"))
                .map (String::new)
                .collect(toList());
    }

    public synchronized static Document strToDocument (String str){
        // DBCollection table = db.getCollection("user");
        Document document = new Document ();
        document.put("typeOutOrInCall", str.substring(0,1).trim());
        document.put("date",  LocalDate.of(Integer.parseInt( "20" + str.substring(6,8)), Integer.parseInt(str.substring(4,6)), Integer.parseInt(str.substring(2,4))));
        document.put("type1", str.substring(9,13).trim());
        document.put("type2", str.substring(13,19).trim());
        document.put("type3", str.substring(19,28).trim());
        document.put("tell", str.substring(29,47).trim());
        document.put("tell-2", str.substring(47,58).trim());
        document.put("hz", str.substring(59,72).trim());
        document.put("hz1", str.substring(72,76).trim());
        document.put("discr", str.substring(76,80).trim());

        //  table.insert(document);
        System.out.println(document.toString());
        return document;
    }

    public static void main(String args[]) {
        List<String> Str = split("longText");
        List<String> l7 = new ArrayList<>();
        List<String> l9 = new ArrayList<>();
        //  System.out.println(split(longText));

        for (String retval : Str) {
            String first = retval.substring(0,1);
            if (first.equals("7")) {
                l7.add(retval);
            }
            else if (first.equals("9")) {
                l9.add(retval);
            }
        }
        for (String retval : l7) {
            strToDocument(retval);
            System.out.println(retval);
        }
        for (String retval : l9) {
            strToDocument(retval);
            System.out.println(retval);
        }

    }


}
