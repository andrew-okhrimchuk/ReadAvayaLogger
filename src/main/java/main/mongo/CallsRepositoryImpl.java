package main.mongo;
import com.mongodb.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CallsRepositoryImpl implements CallsRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Calls insertOneDocument(@NonNull Calls calls) {
        return mongoTemplate.insert(calls);
    }

    @Override
    public Collection<Calls> insertManyDocuments(@NonNull List<Calls> doc) {
        return mongoTemplate.insertAll(doc);
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
        query.addCriteria(Criteria.where("day").lte(day2).gte(day1)).with(Sort.by(Sort.Direction.ASC, "day"));
        query.addCriteria(Criteria.where("month").lte(month2).gte(month1)).with(Sort.by(Sort.Direction.ASC, "month"));
        query.addCriteria(Criteria.where("years").lte(years2).gte(years1)).with(Sort.by(Sort.Direction.ASC, "years"));
        if (way !=0 ){query.addCriteria(Criteria.where("cond_code").is(way));}
        if (num != null ){query.addCriteria(Criteria.where("calling_num").is(num));}


        int result = 0;
        List<Calls> resultCalls = mongoTemplate.find(query, Calls.class);
            if (!resultCalls.isEmpty()){
                result = resultCalls.size();
            }
        log.info("Fined " + result + " items.");
        return resultCalls;
    }
    @Override
    public List<Calls> findAll() {
        return mongoTemplate.findAll(Calls.class);
    }

    @Override
    public void deleteCollection(@NonNull String collection) {

    }
}