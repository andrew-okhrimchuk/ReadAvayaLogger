package main.nonBlockingServer.server;

import lombok.extern.slf4j.Slf4j;
import main.entity.CallsNew;
import main.mongo.CallsRepository.CallsNewRepositoryImpl;
import main.nonBlockingServer.util.UtilTextToCalls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Slf4j
@Service
public class SaveToBase {
    private final CallsNewRepositoryImpl callsRepository;
    private final UtilTextToCalls utilTextToCalls;

    @Autowired
    public SaveToBase(CallsNewRepositoryImpl repo, UtilTextToCalls utilTextToCalls1) {
        callsRepository = repo;
        utilTextToCalls = utilTextToCalls1;
    }
    @Async
    public void sbToLog(String data){
        log.info("Result: Long text is = " + data.length());
    }
    @Async
    public void saveCalls(StringBuilder sb){
        String text = sb.toString();
        sbToLog(text);
        log.info("Try save to DB.");
        List<CallsNew> toSave = utilTextToCalls.StringToListToCalls(sb.toString());

        int result = 0;
        if (toSave.size() > 0){
            Collection<CallsNew> resultCalls = callsRepository.insertManyDocuments(toSave);
            if (!resultCalls.isEmpty()){
                result = resultCalls.size();
            }
        }
        else {log.info("Date is empty! Not save.");}

        log.info("Saved " + result + " items, from " + toSave.size()+ " items.");
    }
}
