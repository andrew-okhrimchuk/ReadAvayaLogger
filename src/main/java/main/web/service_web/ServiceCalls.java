package main.web.service_web;

import main.entity.Calls;
import main.mongo.CallsRepository.CallsRepository;
import main.web.DTO.DTO;
import main.web.DTO.DTOServiceToBase;
import main.web.DTO.DTO_Padding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component()
public class ServiceCalls {
    private final CallsRepository callsRepository;
    @Autowired
    public ServiceCalls(CallsRepository repo) {
        callsRepository = repo;
    }


    public Page<Calls> buildReport(DTO dto){
        DTOServiceToBase tos = new DTOServiceToBase(dto);
        Page<Calls> callsPage = callsRepository.findBeetwDateAndWay(cookingQuery(tos), tos.getPage());
        dto.setCallsPage(callsPage);
        return callsPage;
    }

    public List<Calls> buildExcel(DTO dto){
        DTOServiceToBase tos = new DTOServiceToBase(dto);
        return callsRepository.findByQuery(cookingQuery(tos));

    }


    public DTO_Padding getTO_Padding(Page<Calls> callsPage, DTO_Padding DTO_padding){
        DTO_padding.init(callsPage);
        return DTO_padding;
    }

    private Query cookingQuery (DTOServiceToBase tos){
        Query query = new Query();
        query.addCriteria(Criteria.where("localDateTime").lte(tos.getEnd()).gte(tos.getStart())).with(Sort.by(Sort.Direction.ASC, "localDateTime"));
        if (tos.getWay() != 0 ){query.addCriteria(Criteria.where("cond_code").is(tos.getWay()));}
        if (tos.isShortReport()) {
            query.addCriteria(Criteria.where("code_dial").regex("^[0]{1}$"));
        }
        if (tos.isShortReport()) {
            query
                    .addCriteria(
                            Criteria
                                    .where("dialed_num")
                                    .exists(true)
                                    .orOperator(
                                            Criteria
                                                    .where("dialed_num")
                                                    .regex(tos.getCity()),
                                            Criteria
                                                    .where("dialed_num")
                                                    .regex(tos.getCounty()),
                                            Criteria
                                                    .where("dialed_num")
                                                    .regex(tos.getCity_other()),
                                            Criteria
                                                    .where("dialed_num")
                                                    .regex(tos.getMobil()),
                                            Criteria
                                                    .where("dialed_num")
                                                    .is(tos.getNumD())
                                    ));
        }
        else if (tos.getNumD() != null ){query.addCriteria(Criteria.where("dialed_num").is(tos.getNumD()));}


        //  query.addCriteria(Criteria.where("calling_num").regex("^[0-9]{4,8}$"));

        if (tos.getNum() != null ){query.addCriteria(Criteria.where("calling_num").is(tos.getNum()));}

        return query;
    }

}
