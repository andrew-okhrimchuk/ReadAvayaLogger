package main.mongo.CallsRepository;
import com.mongodb.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import main.web.DTO.DTOServiceToBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.Collection;
import java.util.List;

@Slf4j
public class CallsRepositoryImpl implements CallsRepositoryCustom {
    private final int size_oF_page = 1000;

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
   // public Page<Calls> findBeetwDateAndWay(@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num, int page) {
    public Page<Calls> findBeetwDateAndWay(Query query, int page) {
        log.info("start findBeetwDateAndWay");
        log.info("start query = " + query);
        long total = mongoTemplate.count(query, Calls.class);
        final Pageable pageableRequest = PageRequest.of(page, size_oF_page);
        query.with(pageableRequest);

        List<Calls> resultCalls = mongoTemplate.find(query, Calls.class);

        Page<Calls> patientPage = PageableExecutionUtils.getPage(
                resultCalls,
                pageableRequest,
                () -> total );
        long result = patientPage.getTotalElements();

        log.info("Fined " + result + " items.");

        return patientPage;
    }
    @Override
    public List<Calls> findAll() {
        return mongoTemplate.findAll(Calls.class);
    }


    @Override
    public List<Calls> findByQuery(Query query) {
        log.info("start query = " + query);
        List<Calls> result = mongoTemplate.find(query, Calls.class);
        log.info("Fined " + result + " items.");
        return result;
    }
    @Override
    public void deleteCollection(@NonNull String collection) {

    }
}

