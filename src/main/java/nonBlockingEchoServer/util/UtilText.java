package nonBlockingEchoServer.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static nonBlockingEchoServer.util.Texts.longText;

@Data
@Slf4j
public class UtilText {

    public List<ToCalls> StringToListToCalls(String str){
        return Stream.of(str.split("\n"))
                .filter(s -> (s.startsWith("7") | s.startsWith("9")))
                .map(this::strToCalls)
                .collect(toList());
    }

    public ToCalls strToCalls (String str){
        // DBCollection table = db.getCollection("user");
      //  String str = filter(str);
        ToCalls calls = new ToCalls (
                null,
        Integer.parseInt(str.substring(0,1).trim()),
        Integer.parseInt(str.substring(2,4)),
        Integer.parseInt(str.substring(4,6)),
        Integer.parseInt( "20" + str.substring(6,8)),
        Integer.parseInt(str.substring(9,13).trim()),
        str.substring(14,19).trim(),
        str.substring(20,24).trim(),
        str.substring(24,28).trim(),
        str.substring(29,46).trim(),
        str.substring(47,57).trim(),
        str.substring(58,68).trim(),
        str.substring(69,71).trim(),
        str.substring(72,75).trim(),
        str.substring(76,80).trim());
  //     document.put("return", str.substring(81,82).trim());
  //      document.put("line-feed", str.substring(82,83).trim());
        return calls;
    }
/*
    public List<ToCalls> DocumentsToListToCalls(List<Document> doc){
        return doc.stream()
                .map(this::DocumentToString)
                .collect(toList());
    }

    public ToCalls  DocumentToString (Document doc){
        // DBCollection table = db.getCollection("user");
        //  String str = filter(str);
        ToCalls document = new ToCalls (;
        Integer.parseInt(str.substring(0,1).trim()),
        document.put("day",   Integer.parseInt(str.substring(2,4)));
        document.put("month", Integer.parseInt(str.substring(4,6)));
        document.put("years", Integer.parseInt( "20" + str.substring(6,8)));
        document.put("time", Integer.parseInt(str.substring(9,13).trim()));
        document.put("sec-dur", str.substring(14,19).trim());
        document.put("code-dial", str.substring(20,24).trim());
        document.put("code-used", str.substring(24,28).trim());
        document.put("dialed-num", str.substring(29,46).trim());
        document.put("calling-num", str.substring(47,57).trim());
        document.put("acct-code", str.substring(58,68).trim());
        document.put("out-crt-id", str.substring(69,71).trim());
        document.put("in-crt-id", str.substring(72,75).trim());
        document.put("in-trk-code", str.substring(76,80).trim());
        //     document.put("return", str.substring(81,82).trim());
        //      document.put("line-feed", str.substring(82,83).trim());
        return document;
    }
*/


    public static void main(String args[]) {
        UtilText ss = new UtilText();
        List<ToCalls> xx = ss.StringToListToCalls (longText);
        System.out.println(xx);
    }
}
