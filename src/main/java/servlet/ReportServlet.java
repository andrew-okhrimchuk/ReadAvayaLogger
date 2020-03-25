package servlet;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import mongo.ServiceCallsMongoDB;
import nonBlockingEchoServer.server.NonBlockingEchoServer_NEW;
import nonBlockingEchoServer.timer.MyShutdownHook;
import nonBlockingEchoServer.util.ToCalls;
import org.thymeleaf.context.WebContext;
import service.ServiceCalls;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;


import static servlet.web.ThymeleafListener.engine;

@Slf4j
@WebServlet("")
public class ReportServlet extends HttpServlet {
  //  private UserDao userDao = DBIProvider.getDao(UserDao.class);
  ServiceCalls serviceCalls = new ServiceCalls();


   /* @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime start = stringToLocalDateTime(req.getParameter("start"));
        LocalDateTime end = stringToLocalDateTime(req.getParameter("end"));

        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale(),
                ImmutableMap.of("calls", insTanceS.findBeetwDate(start, end)));
        engine.process("upload", webContext, resp.getWriter());
    }

    private LocalDateTime stringToLocalDateTime (String str) {
       //  = "2016-03-04 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start doGet of '/servlets'");
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale(),
                ImmutableMap.of("calls", serviceCalls.buildReport(req)));
        webContext.setVariable("start_def", serviceCalls.get_date("start", req));  //  "start", req.getParameter("start")
        webContext.setVariable("end_def", serviceCalls.get_date("end", req));
        webContext.setVariable("ways", serviceCalls.ways(req));

        engine.process("calls", webContext, resp.getWriter());
    }

  /*  private List<ToCalls> buildReport(HttpServletRequest req){
        LocalDateTime start = check_date("start", req);
        LocalDateTime end = check_date("end", req);

      *//*  if (req.getParameter("start")==null){
            start = LocalDateTime.now().with(firstDayOfYear());
        } else if (req.getParameter("start")!=null){
            start = LocalDateTime.parse(req.getParameter("start"));
        }

        if (req.getParameter("end")==null){
            end = LocalDateTime.now().with(lastDayOfYear());
        } else if(req.getParameter("end")!=null){
            end = LocalDateTime.parse(req.getParameter("end"));
        }*//*
        return serviceCalls.findBeetwDate(start, end);
    }*/

    private LocalDateTime check_date (String start_end, HttpServletRequest req ){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        if (req.getParameter("start")==null){
            start = LocalDateTime.now().with(firstDayOfYear()).withHour(0).withMinute(0).withSecond(0);
        } else if (req.getParameter("start")!=null){
            start = LocalDateTime.parse(req.getParameter("start"));
        }

        if (req.getParameter("end")==null){
            end = LocalDateTime.now().with(lastDayOfYear()).withHour(23).withMinute(59).withSecond(59);
        } else if(req.getParameter("end")!=null){
            end = LocalDateTime.parse(req.getParameter("end"));
        }

        return start_end.equals("start") ? start: end;
    }

  //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    //  LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
  //  LocalDateTime date = LocalDateTime.(req.getParameter("end"));


    static {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new NonBlockingEchoServer_NEW());
        Runtime.getRuntime().addShutdownHook(new MyShutdownHook(executor));
    }
}
