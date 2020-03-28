package main.web.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.service_web.ServiceCalls;
import main.web.TO.TO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    protected ModelAndView  doGet(@ModelAttribute @NonNull TO to, ModelAndView modelAndView) {
        log.info("Start doGet of '/servlets'");

        modelAndView.getModelMap().addAttribute("calls_list", serviceCalls.buildReport(to));
        modelAndView.getModelMap().addAttribute("start", serviceCalls.get_date_start(to));
        modelAndView.getModelMap().addAttribute("end", serviceCalls.get_date_end(to));
        modelAndView.getModelMap().addAttribute("all_out_in", serviceCalls.list(to));
        modelAndView.getModelMap().addAttribute("answer_all_out_in", "");
        modelAndView.getModelMap().addAttribute("num", serviceCalls.CallStringToCallInt(to));
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
      //  secondaryTemplateResolver.setOrder(1);
        secondaryTemplateResolver.setCheckExistence(true);

        return secondaryTemplateResolver;
    }


}
