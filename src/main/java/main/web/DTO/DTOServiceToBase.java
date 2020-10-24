package main.web.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Getter
@Setter
@NoArgsConstructor
public class DTOServiceToBase {
    private static String cityMatch = "^(0342.*)";
    private static String mobilMatch = "^((0)((39)|(67)|(68)|(96)|(97)|(98)|(50)|(66)|(95)|(99)|(63)|(91)|(92)|(93)|(94)).*)$";
    private static String countyMatch = "^(00.*)";
    private static String emptyMatch = "^(?![\\s\\S])";

    LocalDateTime start,  end;
    int way, page;
    String numD, num;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    boolean isShortReport;

    String city; // null и on
    String mobil; // null и on
    String city_other; // null и on
    String county; // null и on


    public DTOServiceToBase(DTO dto) {
        this.start = get_date_start(dto);
        this.end = get_date_end(dto);
        this.way = get_selected_all_out_in_to_Int(dto);
        this.page = check_Page(dto);
        this.numD = check_phone_numberD(dto);;
        this.num = check_phone_number(dto);
        this.isShortReport = dto.isShortReport;
        this.city = check_CheckboxCity(dto.city);
        this.mobil = check_CheckboxMobil(dto.mobil);
        this.city_other = check_CheckboxCity_other(dto.city_other);
        this.county = check_CheckboxCounty(dto.county);

    }

    public LocalDateTime get_date_start(DTO DTO){
        LocalDateTime start;
        if (DTO.getStart()==null || DTO.getStart().equals("")){
            start = LocalDateTime.now().with(firstDayOfMonth()).minusMonths(1).withHour(0).withMinute(0).truncatedTo(ChronoUnit.MINUTES);
            DTO.setStart(start.format(formatter));
        } else {
            start = LocalDateTime.of(LocalDate.parse(DTO.getStart()), LocalTime.of(00, 00));
            DTO.setStart(start.format(formatter));
        }
        return start;
    }

    public LocalDateTime get_date_end(DTO DTO){
        LocalDateTime end;
        if (DTO.getEnd()==null || DTO.getEnd().equals("")){
            end = LocalDateTime.now().with(lastDayOfMonth()).minusMonths(1).withHour(23).withMinute(59).truncatedTo(ChronoUnit.MINUTES);
            DTO.setEnd(end.format(formatter));
        } else {
            end = LocalDateTime.of(LocalDate.parse(DTO.getEnd()), LocalTime.of(23, 59));
            DTO.setEnd(end.format(formatter));
        }
        return end;
    }

    public int get_selected_all_out_in_to_Int(DTO dto){
        String answer_all_out_in = dto.getAnswer_all_out_in();
        if(answer_all_out_in==null ){return 0;}
        else if (answer_all_out_in.equals("out")) {return 7;}
        else if (answer_all_out_in.equals("in"))  {return 9;}
        return 0;
    }

    public String check_phone_number(DTO dto){
        String number = dto.getNum();
        if(number==null || number.length() >10 || number.equals("")|| number.equals("0") ){return null;}
        return number;
    }

    public String check_phone_numberD(DTO dto){
        String number = dto.getNumD();
        if(number==null || number.length() >10 || number.equals("")|| number.equals("0") ){return null;}
        return number;
    }

    public int check_Page(DTO dto){
        if(dto == null || dto.getPage() == null || dto.getPage().equals("") || dto.getPage().equals("0")){return 0;}
        else return (Integer.valueOf(dto.getPage())-1);
    }


    public String check_CheckboxCity(String dto){
        if(dto == null ){return emptyMatch;}
        else return (cityMatch);
    }
    public String check_CheckboxCounty(String dto){
        if(dto == null ){return emptyMatch;}
        else return (countyMatch);
    }
    public String check_CheckboxMobil(String dto){
        if(dto == null ){return emptyMatch;}
        else return (mobilMatch);
    }
    //https://www.regextester.com/15
    public String check_CheckboxCity_other(String dto){
        if(dto == null ){return emptyMatch;}
        else return "^((?!((00)|(0342)|((0)((39)|(67)|(68)|(96)|(97)|(98)|(50)|(66)|(95)|(99)|(63)|(91)|(92)|(93)|(94))))).)*$";
    }
}
