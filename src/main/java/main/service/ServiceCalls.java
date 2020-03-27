package main.service;

import main.mongo.ServiceCallsMongoDB;
import main.nonBlockingEchoServer.util.ToCalls;
import main.web.TO.TO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Component()
public class ServiceCalls {
    static ServiceCallsMongoDB mongoDB = new ServiceCallsMongoDB();
    public static List listAll = new ArrayList<>(Arrays.asList("all","out","in"));
    public static List listOut = new ArrayList<>(Arrays.asList("out","all","in"));
    public static List listIn = new ArrayList<>(Arrays.asList("in","out","all"));

    public List<ToCalls> buildReport(TO to){
        return mongoDB.findBeetwDateAndWay(get_date_start(to), get_date_end(to), get_selected_all_out_in_to_Int(to),  check_phone_number(to.getNum()));
    }

    public List list (TO to){
        String all_out_in = to.getAll_out_in();
        if(all_out_in==null || all_out_in.equals("all")){
            return listAll;
        }
        if(all_out_in.equals("out")){
            return listOut;
        }
        if(all_out_in.equals("in")){
            return listIn;
        }
        return listAll;
    }
    public LocalDateTime get_date_start(TO to){
        LocalDateTime start;
        if (to.getStart()==null || to.getStart().equals("")){
            start = LocalDateTime.now().with(firstDayOfYear()).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
        } else {
            start = LocalDateTime.parse(to.getStart());
        }
        return start;
    }

    public LocalDateTime get_date_end(TO to){
        LocalDateTime end;
        if (to.getEnd()==null || to.getStart().equals("")){
            end = LocalDateTime.now().with(lastDayOfYear()).withHour(23).withMinute(59).truncatedTo(ChronoUnit.MINUTES);
        } else {
            end = LocalDateTime.parse(to.getEnd());
        }
        return end;
    }

    public int get_selected_all_out_in_to_Int(TO to){
        String answer_all_out_in = to.getAnswer_all_out_in();
        if(answer_all_out_in==null ){return 0;}
        else if (answer_all_out_in.equals("out")) {return 7;}
        else if (answer_all_out_in.equals("in"))  {return 9;}
        return 0;
    }
    public String check_phone_number(String number){
        if(number==null || number.length() >10 || number.equals("")|| number.equals("0")){return null;}
        return number;
    }
    public String CallStringToCallInt(TO to){
        String number = to.getAnswer_all_out_in();
        if(number==null || number.equals("0") || number.equals("")){return "0";}
        return number;
    }
}
