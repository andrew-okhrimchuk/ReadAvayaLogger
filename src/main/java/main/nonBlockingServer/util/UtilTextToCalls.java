package main.nonBlockingServer.util;

import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import main.entity.CallsNew;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UtilTextToCalls {

    public List<CallsNew> StringToListToCalls(String str){
        return Stream.of(str.split("\n"))
                .filter(s -> (s.startsWith("7") | s.startsWith("9")))
                .map(this::strToCalls)
                .collect(toList());
    }

    public CallsNew strToCalls (String str){

        LocalDate localDate = LocalDate.of(Integer.parseInt("20" + str.substring(6,8)),
                Integer.parseInt(str.substring(4,6)),
                Integer.parseInt( str.substring(2,4)));
        LocalTime localTime = LocalTime.of(Integer.parseInt(str.substring(9,11).trim()), Integer.parseInt(str.substring(11,13).trim())) ;
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        CallsNew calls = new CallsNew (
                null,
        Integer.parseInt(str.substring(0,1).trim()),
                localDateTime,
        str.substring(14,19).trim(),
        str.substring(20,24).trim(),
        str.substring(24,28).trim(),
        str.substring(29,46).trim(),
        str.substring(47,57).trim(),
        str.substring(58,68).trim(),
        str.substring(69,71).trim(),
        str.substring(72,75).trim(),
        str.substring(76,80).trim());
        return calls;
    }


    @Deprecated
    public List<CallsNew> oldToNew(List<Calls> list) {
        return list
                .stream()
                .map(this::oldToNew)
                .collect(toList());
    }

    @Deprecated
    public CallsNew oldToNew(Calls item){
        LocalDate localDate = LocalDate.of(
                item.getYears(),
                item.getMonth(),
                item.getDay());
        LocalTime localTime = LocalTime.of(
                firstDigit(item.getTime()),
                lastDigit(item.getTime()));
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        CallsNew callsNew = new CallsNew();
        callsNew.setCond_code(item.getCond_code());
        callsNew.setLocalDateTime(localDateTime);
        callsNew.setSec_dur(item.getSec_dur());
        callsNew.setCode_dial(item.getCode_dial());
        callsNew.setCode_used(item.getCode_used());
        callsNew.setDialed_num(item.getDialed_num());
        callsNew.setCalling_num(item.getCalling_num());
        callsNew.setAcct_code(item.getAcct_code());
        callsNew.setOut_crt_id(item.getOut_crt_id());
        callsNew.setIn_crt_id(item.getIn_crt_id());
        callsNew.setIn_trk_code(item.getIn_trk_code());
        return callsNew;
    }

    @Deprecated
    // Find the first digit
    private static int firstDigit(int n)
    {
        // Remove last digit from number
        // till only one digit is left
        while (n >= 100)
            n /= 100;

        // return the first digit
        return n;
    }

    @Deprecated
    // Find the last digit
    private static int lastDigit(int n)
    {
        // return the last digit
        return (n % 100);
    }


}
