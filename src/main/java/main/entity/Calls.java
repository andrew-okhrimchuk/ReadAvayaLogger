package main.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@ToString
@Document(collection = "CallsRepository")
@Data
@NoArgsConstructor
@EqualsAndHashCode

public class Calls {

    @Id
    public ObjectId id;
    public  int cond_code;
    @DateTimeFormat(pattern = "MM.dd.yyyy. HH:mm")
    public LocalDateTime localDateTime;
    public  String sec_dur;
    public  String code_dial;
    public  String code_used;
    public  String dialed_num;
    public  String calling_num;
    public  String acct_code;
    public  String out_crt_id;
    public  String in_crt_id;
    public  String in_trk_code;

    public Calls(
            ObjectId id,
            int cond_code,
            LocalDateTime localDateTime,
            String sec_dur,
            String code_dial,
            String code_used,
            String dialed_num,
            String calling_num,
            String acct_code,
            String out_crt_id,
            String in_crt_id,
            String in_trk_code
    ) {
        this.id = id;
        this.cond_code = cond_code;
        this.localDateTime = localDateTime;
        this.sec_dur = sec_dur;
        this.code_dial = code_dial;
        this.code_used = code_used;
        this.dialed_num = dialed_num;
        this.calling_num = calling_num;
        this.acct_code = acct_code;
        this.out_crt_id = out_crt_id;
        this.in_crt_id = in_crt_id;
        this.in_trk_code = in_trk_code;
    }


}