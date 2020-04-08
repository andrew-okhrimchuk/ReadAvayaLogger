package main.mongo.callsNew;
import com.mongodb.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.entity.CallsNew;
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

@Slf4j
public class CallsNewRepositoryImpl implements CallsNewRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public CallsNew insertOneDocument(@NonNull CallsNew calls) {
        return mongoTemplate.insert(calls);
    }

    @Override
    public Collection<CallsNew> insertManyDocuments(@NonNull List<CallsNew> doc) {
        return mongoTemplate.insertAll(doc);
    }


    @Override
    public Page<CallsNew> findBeetwDateAndWay(@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num, int page) {
        log.info("start findBeetwDateAndWay");
        int day1 = start.getDayOfMonth();
        int day2 = end.getDayOfMonth();
        int month1 = start.getMonth().getValue();
        int month2 = end.getMonth().getValue();
        int years1 = start.getYear();
        int years2 = end.getYear();

        Query query = new Query();
        query.addCriteria(Criteria.where("localDateTime").lte(end).gte(start)).with(Sort.by(Sort.Direction.ASC, "localDateTime"));
 //       query.addCriteria(Criteria.where("month").lte(month2).gte(month1)).with(Sort.by(Sort.Direction.ASC, "month"));
 //       query.addCriteria(Criteria.where("years").lte(years2).gte(years1)).with(Sort.by(Sort.Direction.ASC, "years"));
        if (way !=0 ){query.addCriteria(Criteria.where("cond_code").is(way));}
        if (num != null ){query.addCriteria(Criteria.where("calling_num").is(num));}

        long total = mongoTemplate.count(query, CallsNew.class);

        final Pageable pageableRequest = PageRequest.of(page, 10000);
        query.with(pageableRequest);


        long result = 0;
        List<CallsNew> resultCalls = mongoTemplate.find(query, CallsNew.class);
           /* if (!resultCalls.isEmpty()){
                result = resultCalls.size();
            }*/

        Page<CallsNew> patientPage = PageableExecutionUtils.getPage(
                resultCalls,
                pageableRequest,
                () -> total );
        result = patientPage.getTotalElements();

        log.info("Fined " + result + " items.");

        return patientPage;
    }
    @Override
    public List<CallsNew> findAll() {
        return mongoTemplate.findAll(CallsNew.class);
    }

    @Override
    public void deleteCollection(@NonNull String collection) {

    }
}

