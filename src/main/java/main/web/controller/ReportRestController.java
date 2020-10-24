package main.web.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import main.web.service_web.ExcelFileExporter;
import main.web.service_web.ServiceCalls;
import main.web.DTO.DTO;
import main.web.DTO.DTO_Padding;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


@Slf4j
@Controller
public class ReportRestController  {
    @Autowired
    ServiceCalls serviceCalls;
    @Autowired
    DTO_Padding DTO_padding;



    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    protected ModelAndView  mainReport(@ModelAttribute @NonNull DTO dto, ModelAndView modelAndView) {
        log.info("Start mainReport of '/servlets'");
        Page<Calls> callsPage = serviceCalls.buildReport(dto);
        modelAndView.getModelMap().addAttribute("TO", dto);
        modelAndView.getModelMap().addAttribute("TO_Padding", serviceCalls.getTO_Padding(callsPage, DTO_padding));
        modelAndView.setViewName("calls-2");
      // log.info(dto.toString());
      //  log.info(to_padding.toString());
        return modelAndView;
    }

    @RequestMapping(value = { "/consolidated_report" }, method = RequestMethod.GET)
    @ResponseBody
    protected ModelAndView  consolidatedReport(@ModelAttribute @NonNull DTO dto, ModelAndView modelAndView) {
        log.info("Start consolidatedReport of '/servlets'");
        dto.setShortReport(true);
        Page<Calls> callsPage = serviceCalls.buildReport(dto);
        modelAndView.getModelMap().addAttribute("TO", dto);
        modelAndView.getModelMap().addAttribute("TO_Padding", serviceCalls.getTO_Padding(callsPage, DTO_padding));
        modelAndView.setViewName("consolidated_report");
        return modelAndView;
    }
    @GetMapping("/download/calls.xlsx")
    public void downloadCsv(HttpServletResponse response, @ModelAttribute @NonNull DTO dto) throws IOException {
        log.info("Start downloadCsv of '/servlets'");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");
        ByteArrayInputStream stream = ExcelFileExporter.callsListToExcelFile(serviceCalls.buildExcel(dto));
        IOUtils.copy(stream, response.getOutputStream());
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
