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
    private final int size_oF_page = 1000;

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

        Query query = new Query();
        query.addCriteria(Criteria.where("localDateTime").lte(end).gte(start)).with(Sort.by(Sort.Direction.ASC, "localDateTime"));
        if (way !=0 ){query.addCriteria(Criteria.where("cond_code").is(way));}
        if (num != null ){query.addCriteria(Criteria.where("calling_num").is(num));}

        long total = mongoTemplate.count(query, CallsNew.class);

        final Pageable pageableRequest = PageRequest.of(page, size_oF_page);
        query.with(pageableRequest);


        List<CallsNew> resultCalls = mongoTemplate.find(query, CallsNew.class);

        Page<CallsNew> patientPage = PageableExecutionUtils.getPage(
                resultCalls,
                pageableRequest,
                () -> total );
        long result = patientPage.getTotalElements();

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

