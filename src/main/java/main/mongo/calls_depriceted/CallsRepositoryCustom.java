package main.mongo.calls_depriceted;

import com.mongodb.lang.NonNull;
import main.entity.Calls;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@Deprecated
public interface CallsRepositoryCustom {
    Calls insertOneDocument (@NonNull Calls calls);
    Collection<Calls> insertManyDocuments (@NonNull List<Calls> doc);
    Page<Calls> findBeetwDateAndWay (@NonNull LocalDateTime start, @NonNull LocalDateTime end, int way, String num, int page);
    List<Calls> findAll ();
    void deleteCollection (String collection);
}
