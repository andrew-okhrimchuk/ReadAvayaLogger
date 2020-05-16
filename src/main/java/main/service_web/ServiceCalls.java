package main.service_web;

import main.entity.CallsNew;
import main.mongo.callsNew.CallsNewRepository;
import main.web.TO.TO;
import main.web.TO.TOServiceToBase;
import main.web.TO.TO_Padding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component()
public class ServiceCalls {
    private final CallsNewRepository callsRepository;
    @Autowired
    public ServiceCalls(CallsNewRepository repo) {
        callsRepository = repo;
    }


    public Page<CallsNew> buildReport(TO to){
        Page<CallsNew> callsPage = callsRepository.findBeetwDateAndWay(new TOServiceToBase(to));
        to.setCallsPage(callsPage);
        return callsPage;
    }

    public TO_Padding getTO_Padding(Page<CallsNew> callsPage, TO_Padding to_padding){
        to_padding.init(callsPage);
        return to_padding;
    }

}
