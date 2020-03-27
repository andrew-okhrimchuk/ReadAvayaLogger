package main.service;

import main.mongo.ServiceCallsMongoDB;
import main.nonBlockingEchoServer.util.ToCalls;

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
        LocalDateTime start = get_date("start", req);
        LocalDateTime end = get_date("end", req);
        int way = get_subjec(req);
        String num = get_number(req);
        return mongoDB.findBeetwDateAndWay(start, end, way, num);
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
    public int get_subjec(HttpServletRequest req){
        String way = req.getParameter("subject");
        if(way==null ){return 0;}
        else if (way.equals("out")) {return 7;}
        else if (way.equals("in"))  {return 9;}
        return 0;
    }
    public String get_number(HttpServletRequest req){
        String number = req.getParameter("num");
        if(number==null || number.length() >10 || number.equals("")|| number.equals("0")){return null;}
        return number;
    }
    public int get_numberInt(HttpServletRequest req){
        String number = req.getParameter("num");
        if(number==null || number.equals("0") || number.equals("")){return 0;}
        return Integer.parseInt(number);
    }
}
