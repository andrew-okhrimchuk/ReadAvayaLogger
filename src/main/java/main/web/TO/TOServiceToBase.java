package main.web.TO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Getter
@Setter
public class TOServiceToBase {
    LocalDateTime start,  end;
    int way, page;
    String numD, num;

    public TOServiceToBase(TO to) {
        this.start = get_date_start(to);
        this.end = get_date_end(to);
        this.way = get_selected_all_out_in_to_Int( to);
        this.page = check_Page(to);
        this.numD = check_phone_numberD(to);;
        this.num = check_phone_number(to);
    }

    public LocalDateTime get_date_start(TO to){
        LocalDateTime start;
        if (to.getStart()==null || to.getStart().equals("")){
            start = LocalDateTime.now().with(firstDayOfMonth()).minusMonths(1).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
        } else {
            //start = LocalDateTime.parse(to.getStart());
            start = LocalDateTime.of(LocalDate.parse(to.getStart()), LocalTime.of(00, 00));
        }
        return start;
    }

    public LocalDateTime get_date_end(TO to){
        LocalDateTime end;
        if (to.getEnd()==null || to.getEnd().equals("")){
            end = LocalDateTime.now().with(lastDayOfMonth()).minusMonths(1).withHour(23).withMinute(59).truncatedTo(ChronoUnit.MINUTES);
        } else {
            //end = LocalDateTime.parse(to.getEnd());
            end = LocalDateTime.of(LocalDate.parse(to.getEnd()), LocalTime.of(23, 59));
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

    public String check_phone_number(TO to){
        String number = to.getNum();
        if(number==null || number.length() >10 || number.equals("")|| number.equals("0")){return null;}
        return number;
    }

    public String check_phone_numberD(TO to){
        String number = to.getNumD();
        if(number==null || number.length() >10 || number.equals("")|| number.equals("0")){return null;}
        return number;
    }

    public int check_Page(TO to){
        if(to == null || to.getPage() == null || to.getPage().equals("") || to.getPage().equals("0")){return 0;}
        else return (Integer.valueOf(to.getPage())-1);
    }
}
