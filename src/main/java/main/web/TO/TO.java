package main.web.TO;

import lombok.Getter;
import lombok.Setter;
import main.entity.CallsNew;
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
public class TO {
    String start;
    String end;
    List<String> all_out_in = new ArrayList<>(Arrays.asList("all","out","in"));
    String answer_all_out_in;
    String num;
    String numD;
    String page;
    Page<CallsNew> callsPage;
}
