package main.web.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.entity.CallsNew;
import main.service_web.ServiceCalls;
import main.web.TO.TO;
import main.web.TO.TO_Padding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Slf4j
@Controller
public class ReportRestController  {
    @Autowired
    ServiceCalls serviceCalls;
    @Autowired
    TO_Padding to_padding;



    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    protected ModelAndView  doGet(@ModelAttribute @NonNull TO to, ModelAndView modelAndView) {
        log.info("Start doGet of '/servlets'");
        Page<CallsNew> callsPage = serviceCalls.buildReport(to);
        modelAndView.getModelMap().addAttribute("TO", to);
        modelAndView.getModelMap().addAttribute("TO_Padding", serviceCalls.getTO_Padding(callsPage, to_padding));
        modelAndView.setViewName("calls-2");

        return modelAndView;
    }

    @Bean
    public ClassLoaderTemplateResolver secondaryTemplateResolver() {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("templates/");
        secondaryTemplateResolver.setSuffix(".html");
        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
        secondaryTemplateResolver.setCheckExistence(true);
        return secondaryTemplateResolver;
    }


}
