package main.mongo.CallsRepository;

import com.mongodb.lang.NonNull;
import main.entity.CallsNew;
import main.web.TO.TOServiceToBase;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface CallsNewRepositoryCustom {
    CallsNew insertOneDocument(@NonNull CallsNew calls);
    Collection<CallsNew> insertManyDocuments(@NonNull List<CallsNew> doc);
    Page<CallsNew> findBeetwDateAndWay(TOServiceToBase tos);
    List<CallsNew> findAll();
    void deleteCollection(String collection);
}
