package main.mongo;
import com.mongodb.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class CallsRepositoryImpl implements CallsRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void insertOneDocument(@NonNull Calls calls) {
        mongoTemplate.insert(calls);
    }

    @Override
    public void insertManyDocuments(@NonNull List<Calls> doc) {
        mongoTemplate.insertAll(doc);
    }


    @Override
    public List<Calls> findBeetwDateAndWay(@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num) {
        log.info("start findBeetwDateAndWay");
        int day1 = start.getDayOfMonth();
        int day2 = end.getDayOfMonth();
        int month1 = start.getMonth().getValue();
        int month2 = end.getMonth().getValue();
        int years1 = start.getYear();
        int years2 = end.getYear();

//       query.with(new Sort(Sort.Direction.ASC, "age"));
        Query query = new Query();
        query.addCriteria(Criteria.where("day").lt(day2).gt(day1));
        query.addCriteria(Criteria.where("month").lt(month2).gt(month1));
        query.addCriteria(Criteria.where("years").lt(years2).gt(years1));
        if (way !=0 ){query.addCriteria(Criteria.where("cond_code").is("way"));}
        if (num != null ){query.addCriteria(Criteria.where("calling_num").is("num"));}

        return mongoTemplate.find(query, Calls.class);
    }
    @Override
    public List<Calls> findAll() {
        return mongoTemplate.findAll(Calls.class);
    }

    @Override
    public void deleteCollection(@NonNull String collection) {

    }
}