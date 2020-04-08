package main.mongo.callsNew;

import com.mongodb.lang.NonNull;
import main.entity.CallsNew;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface CallsNewRepositoryCustom {
    CallsNew insertOneDocument(@NonNull CallsNew calls);
    Collection<CallsNew> insertManyDocuments(@NonNull List<CallsNew> doc);
    Page<CallsNew> findBeetwDateAndWay(@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num, int page);
    List<CallsNew> findAll();
    void deleteCollection(String collection);
}
