package main.web.TO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
public class TO {
    String start;
    String end;
    String all_out_in;
    String answer_all_out_in;
    String num;
}
