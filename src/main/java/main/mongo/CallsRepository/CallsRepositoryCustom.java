package main.mongo.CallsRepository;

import com.mongodb.lang.NonNull;
import main.entity.Calls;
import main.web.DTO.DTOServiceToBase;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;

public interface CallsRepositoryCustom {
    Calls insertOneDocument(@NonNull Calls calls);
    Collection<Calls> insertManyDocuments(@NonNull List<Calls> doc);
    Page<Calls> findBeetwDateAndWay(Query query, int page);
    List<Calls> findAll();
    List<Calls> findByQuery(Query query);
    void deleteCollection(String collection);
}
