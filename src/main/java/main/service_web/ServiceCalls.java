package main.service_web;

import main.entity.CallsNew;
import main.mongo.callsNew.CallsNewRepository;
import main.web.TO.TO;
import main.web.TO.TOServiceToBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Component()
public class ServiceCalls {
    private final CallsNewRepository callsRepository;

    @Autowired
    public ServiceCalls(CallsNewRepository repo) {
        callsRepository = repo;
    }


    public static List listAll = new ArrayList<>(Arrays.asList("all","out","in"));
  //  public static List listOut = new ArrayList<>(Arrays.asList("out","all","in"));
 //   public static List listIn = new ArrayList<>(Arrays.asList("in","out","all"));

    public Page<CallsNew> buildReport(TO to){
        to.setAll_out_in(listAll); ;
      //  TOServiceToBase TOSTB = new TOServiceToBase ();
       // get_date_start(to, TOSTB);


        return callsRepository.findBeetwDateAndWay(new TOServiceToBase(to));
    }



}
