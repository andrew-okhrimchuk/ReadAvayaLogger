package service;

import mongo.ServiceCallsMongoDB;
import nonBlockingEchoServer.util.ToCalls;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class ServiceCalls {
    static ServiceCallsMongoDB mongoDB = new ServiceCallsMongoDB();
    public static List listAll = new ArrayList<>(Arrays.asList("all","out","in"));
    public static List listOut = new ArrayList<>(Arrays.asList("out","all","in"));
    public static List listIn = new ArrayList<>(Arrays.asList("in","out","all"));

    public List<ToCalls> buildReport(HttpServletRequest req){
        System.out.println(req.getAttributeNames().toString());

        System.out.println(req.getParameterNames());
        LocalDateTime start = get_date("start", req);
        LocalDateTime end = get_date("end", req);
        String way = req.getParameter("subject");
        if(way==null || way.equals("all")){
            return mongoDB.findBeetwDate(start, end);
        }else { int wayI = 7;
            if (way.equals("in")){return mongoDB.findBeetwDateAndWay(start, end, 9);}
            return mongoDB.findBeetwDateAndWay(start, end, wayI);
        }
    }


    public List list (HttpServletRequest req){
        String way = req.getParameter("subject");
        if(way==null || way.equals("all")){
            return listAll;
        }
        if(way.equals("out")){
            return listOut;
        }
        if(way.equals("in")){
            return listIn;
        }
        return listAll;
    }

    public LocalDateTime get_date(String start_end, HttpServletRequest req ){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        if (req.getParameter("start")==null){
            start = LocalDateTime.now().with(firstDayOfYear()).withHour(0).withMinute(0).withSecond(0);
        } else if (req.getParameter("start")!=null){
            start = LocalDateTime.parse(req.getParameter("start"));
        }

        if (req.getParameter("end")==null){
            end = LocalDateTime.now().with(lastDayOfYear()).withHour(23).withMinute(59).withSecond(59);
        } else if(req.getParameter("end")!=null){
            end = LocalDateTime.parse(req.getParameter("end"));
        }

        return start_end.equals("start") ? start: end;
    }


}
