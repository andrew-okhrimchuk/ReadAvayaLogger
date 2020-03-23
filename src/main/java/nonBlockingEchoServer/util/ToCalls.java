package nonBlockingEchoServer.util;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Slf4j
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ToCalls {

    @BsonProperty("_id") public ObjectId id;
    @BsonProperty("cond_code") public  int cond_code;
    @BsonProperty("day") public  int day;
    @BsonProperty("month")public  int month;
    @BsonProperty("years")public  int years;
    @BsonProperty("time")public  int time;
    @BsonProperty("sec_dur")public  String sec_dur;
    @BsonProperty("code_dial") public  String code_dial;
    @BsonProperty("code_used") public  String code_used;
    @BsonProperty("dialed_num") public  String dialed_num;
    @BsonProperty("calling_num") public  String calling_num;
    @BsonProperty("acct_code") public  String acct_code;
    @BsonProperty("out_crt_id") public  String out_crt_id;
    @BsonProperty("in_crt_idd") public  String in_crt_id;
    @BsonProperty("in_trk_code") public  String in_trk_code;

    @BsonCreator
    public ToCalls(
            @BsonProperty("_id") ObjectId id,
            @BsonProperty("cond_code") int cond_code,
            @BsonProperty("day") int day,
            @BsonProperty("month") int month,
            @BsonProperty("years") int years,
            @BsonProperty("time") int time,
            @BsonProperty("sec_dur") String sec_dur,
            @BsonProperty("code_dial") String code_dial,
            @BsonProperty("code_used") String code_used,
            @BsonProperty("dialed_num") String dialed_num,
            @BsonProperty("calling_num") String calling_num,
            @BsonProperty("acct_code") String acct_code,
            @BsonProperty("out_crt_id") String out_crt_id,
            @BsonProperty("in_crt_idd") String in_crt_id,
            @BsonProperty("in_trk_code") String in_trk_code
    ) {
        this.id = id;
        this.cond_code = cond_code;
        this.day = day;
        this.month = month;
        this.years = years;
        this.time = time;
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
/*    @BsonCreator
    public ToCalls(
            @BsonProperty("cond-code") int cond_code,
            @BsonProperty("day") int day,
            @BsonProperty("month") int month,
            @BsonProperty("years") int years,
            @BsonProperty("time") int time,
            @BsonProperty("sec-dur") String sec_dur,
            @BsonProperty("code-dial") String code_dial,
            @BsonProperty("code-used") String code_used,
            @BsonProperty("dialed-num") String dialed_num,
            @BsonProperty("calling-num") String calling_num,
            @BsonProperty("acct-code") String acct_code,
            @BsonProperty("out-crt-id") String out_crt_id,
            @BsonProperty("in-crt-idd") String in_crt_id,
            @BsonProperty("in-trk-code") String in_trk_code
    ) {
        this.id = 0;
        this.cond_code = cond_code;
        this.day = day;
        this.month = month;
        this.years = years;
        this.time = time;
        this.sec_dur = sec_dur;
        this.code_dial = code_dial;
        this.code_used = code_used;
        this.dialed_num = dialed_num;
        this.calling_num = calling_num;
        this.acct_code = acct_code;
        this.out_crt_id = out_crt_id;
        this.in_crt_id = in_crt_id;
        this.in_trk_code = in_trk_code;
    }*/
}