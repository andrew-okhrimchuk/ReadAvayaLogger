package main.mongo.calls;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Deprecated
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
    public Page<Calls> findBeetwDateAndWay(@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num, int page) {
        log.info("start findBeetwDateAndWay");
        int day1 = start.getDayOfMonth();
        int day2 = end.getDayOfMonth();
        int month1 = start.getMonth().getValue();
        int month2 = end.getMonth().getValue();
        int years1 = start.getYear();
        int years2 = end.getYear();

        Query query = new Query();
        query.addCriteria(Criteria.where("day").lte(day2).gte(day1)).with(Sort.by(Sort.Direction.ASC, "day"));
        query.addCriteria(Criteria.where("month").lte(month2).gte(month1)).with(Sort.by(Sort.Direction.ASC, "month"));
        query.addCriteria(Criteria.where("years").lte(years2).gte(years1)).with(Sort.by(Sort.Direction.ASC, "years"));
        if (way !=0 ){query.addCriteria(Criteria.where("cond_code").is(way));}
        if (num != null ){query.addCriteria(Criteria.where("calling_num").is(num));}

        long total = mongoTemplate.count(query, Calls.class);

        final Pageable pageableRequest = PageRequest.of(page, 10000);
        query.with(pageableRequest);


        long result = 0;
        List<Calls> resultCalls = mongoTemplate.find(query, Calls.class);

        Page<Calls> patientPage = PageableExecutionUtils.getPage(
                resultCalls,
                pageableRequest,
                () -> total );
        result = patientPage.getTotalElements();

        log.info("Fined " + result + " items.");

        return patientPage;
    }
    @Override
    public List<Calls> findAll() {
        return mongoTemplate.findAll(Calls.class);
    }

    @Override
    public void deleteCollection(@NonNull String collection) {

    }

    public List<Calls> findAll_Limit() {
        log.info("start findAll_Limit 500 items");
        Query query = new Query();
        final Pageable pageableRequest = PageRequest.of(0, 500);
        query.with(pageableRequest);
        List<Calls> resultCalls = mongoTemplate.find(query, Calls.class);
        long result = resultCalls.size();
        log.info("Fined in findAll_Limit " + result + " items.");

        return resultCalls;
    }

    public void delite() {
        log.info("start delite");
        Query query = new Query();
        final Pageable pageableRequest = PageRequest.of(0, 500);
        query.with(pageableRequest);
        DeleteResult ds = mongoTemplate.remove(query, Calls.class);
        log.info("Fined in delite " + ds.getDeletedCount() + " items.");
    }



}

