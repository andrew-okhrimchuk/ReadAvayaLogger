package main.move_base;

import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import main.entity.CallsNew;
import main.mongo.calls.CallsRepositoryImpl;
import main.mongo.callsNew.CallsNewRepositoryImpl;
import main.nonBlockingEchoServer.util.UtilText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Slf4j
@Service("MoveBase")
@Deprecated
public class MoveBase {

    private final CallsNewRepositoryImpl callsNewRepository;
    private final CallsRepositoryImpl callsRepository;
    private final UtilText utilText;

    @Autowired
    public MoveBase(CallsNewRepositoryImpl callsNewRepository, CallsRepositoryImpl callsRepository, UtilText utilText) {
        this.callsNewRepository = callsNewRepository;
        this.callsRepository = callsRepository;
        this.utilText = utilText;

    }


    @Async("newSingleThreadExecutor")
    public void moveCallsAtherBase(){
        log.info("Try save to AtherBase.");
        boolean start = true;
        int result = 0;
        int oldresult = 0;


        while (start) {
            List<Calls> getOldCalls = callsRepository.findAll_Limit();
            if (getOldCalls.size() <= 0){
                start = false;
            }
            callsRepository.delite();

            Collection<CallsNew> calls = callsNewRepository.insertManyDocuments(utilText.oldToNew(getOldCalls));
            result = calls.size();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                log.error("InterruptedException = " + e.toString());
            }

        }




        log.info("Saved news " + result + " items, from " + oldresult + " items.");
    }
}
