package service;

import mongo.ServiceCallsMongoDB;
import nonBlockingEchoServer.util.ToCalls;
import servlet.To.Report;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class ServiceCalls {
    static ServiceCallsMongoDB mongoDB = new ServiceCallsMongoDB();

    public List<ToCalls> buildReport(HttpServletRequest req){
        LocalDateTime start = get_date("start", req);
        LocalDateTime end = get_date("end", req);
        String way = req.getParameter("calls_way");
        if(way==null || way.equals("all")){
            return mongoDB.findBeetwDate(start, end);
        }else { int wayI = 7;
            if (way.equals("in")){return mongoDB.findBeetwDateAndWay(start, end, 9);}
            return mongoDB.findBeetwDateAndWay(start, end, wayI);
        }
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

    /*public Way get_calls_way(){
        Way calls_way = new Way();
        List<String> sss = new ArrayList<>();
        sss.add("all");
        sss.add("out");
        sss.add("in");
        calls_way.setWayisone(sss);
        return calls_way;
    }*/
    public String get_param(String calls_way, HttpServletRequest req ){
        String result = "";
        if (req.getParameter("calls_way")==null){
            result = "all";
        } else {result = req.getParameter(calls_way);}

        return result;
    }

   /* public List<ToCalls> buildReport(HttpServletRequest req){
        LocalDateTime start = get_date("start", req);
        LocalDateTime end = get_date("end", req);
        String way = req.getParameter("calls_way");
        if(way==null || way.equals("all")){
            return mongoDB.findBeetwDate(start, end);
        }else { int wayI = 7;
            if (way.equals("in")){return mongoDB.findBeetwDateAndWay(start, end, 9);}
            return mongoDB.findBeetwDateAndWay(start, end, wayI);
        }
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

    public List <Way> get_calls_way(){
        List<Way> calls_way = new ArrayList<>();
        calls_way.add(new Way("All Calls"));
        calls_way.add(new Way("Out"));
        calls_way.add(new Way("In"));
        return calls_way;
    }
    public String get_param(String calls_way, HttpServletRequest req ){
        String result = "";
        if (req.getParameter("calls_way")==null){
            result = "all";
        } else {result = req.getParameter(calls_way);}

        return result;
    }*/
}
