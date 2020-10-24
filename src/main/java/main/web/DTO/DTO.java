package main.web.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.entity.Calls;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Getter
@Setter
@RequestScope
public class DTO {
    String start;
    String end;
    List<String> all_out_in = new ArrayList<>(Arrays.asList("all","out","in"));
    String answer_all_out_in;
    String num;
    String numD;
    String page;
    Page<Calls> callsPage;
    boolean isShortReport;
    String city; // null и on
    String mobil; // null и on
    String city_other; // null и on
    String county; // null и on

}
