package main.mongo;

import com.mongodb.lang.NonNull;
import main.entity.Calls;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface CallsRepositoryCustom {
    Calls insertOneDocument (@NonNull Calls calls);
    Collection<Calls> insertManyDocuments (@NonNull List<Calls> doc);
    List<Calls> findBeetwDateAndWay (@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num);
    List<Calls> findAll ();
    void deleteCollection (String collection);
}
