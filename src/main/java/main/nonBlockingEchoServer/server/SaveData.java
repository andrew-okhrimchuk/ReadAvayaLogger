package main.nonBlockingEchoServer.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import main.mongo.CallsRepository;
import main.nonBlockingEchoServer.util.UtilText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


@Slf4j
@Component ("saveData")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveData extends Thread {
    @Setter
    @Getter
    private StringBuilder sb;
    private final CallsRepository callsRepository;
    private final UtilText utilText;

    @Autowired
    public SaveData(CallsRepository repo, UtilText utilText1) {
        callsRepository = repo;
        utilText = utilText1;
    }

    private void sbToLog(String data){
        log.info("Result: Long text is = " + data.length());
    }

    public void run(){
        String text = sb.toString();
        sbToLog(text);
        log.info("Try save to DB.");
        List<Calls> toSave = utilText.StringToListToCalls(sb.toString());

        int result = 0;
        if (toSave.size() > 0){
            Collection<Calls> resultCalls = callsRepository.insertManyDocuments(toSave);
            if (!resultCalls.isEmpty()){
                result = resultCalls.size();
            }
        }
        else {log.info("Date is empty! Not save.");}

        log.info("Saved " + result + " items, from " + toSave.size()+ " items.");
    }
}
