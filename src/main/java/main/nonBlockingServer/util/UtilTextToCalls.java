package main.nonBlockingServer.util;

import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UtilTextToCalls {

    public List<Calls> StringToListToCalls(String str){
        return Stream.of(str.split("\n"))
                .filter(s -> (s.startsWith("7") | s.startsWith("9")))
                .filter(s -> (Objects.equals(s.substring(20, 24).trim(), "0")))
                .map(this::strToCalls)
                .collect(toList());
    }

    public Calls strToCalls (String str){

        LocalDate localDate = LocalDate.of(Integer.parseInt("20" + str.substring(6,8)),
                Integer.parseInt(str.substring(4,6)),
                Integer.parseInt( str.substring(2,4)));
        LocalTime localTime = LocalTime.of(Integer.parseInt(str.substring(9,11).trim()), Integer.parseInt(str.substring(11,13).trim())) ;
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        Calls calls = new Calls(
                null,
        Integer.parseInt(str.substring(0,1).trim()),
                localDateTime,
        str.substring(14,19).trim(),//sec_dur
        str.substring(20,24).trim(),//code_dial
        str.substring(24,28).trim(),
        str.substring(29,46).trim(),
        str.substring(47,57).trim(),
        str.substring(58,68).trim(),
        str.substring(69,71).trim(),
        str.substring(72,75).trim(),
        str.substring(76,80).trim());
        return calls;
    }




}
