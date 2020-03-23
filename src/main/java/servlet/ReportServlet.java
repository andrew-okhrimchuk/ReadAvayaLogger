package servlet;

import lombok.extern.slf4j.Slf4j;
import mongo.ServiceCallsMD;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import com.google.common.collect.ImmutableMap;
import servlet.To.Report;

import static servlet.web.ThymeleafListener.engine;

@Slf4j
@WebServlet("")
public class ReportServlet extends HttpServlet {
  //  private UserDao userDao = DBIProvider.getDao(UserDao.class);
    ServiceCallsMD insTanceS = new ServiceCallsMD();

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
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        webContext.setVariable("report", buildReport(req));
        webContext.setVariable("default_start", req.getParameter("start"));
        webContext.setVariable("default_end", req.getParameter("end"));

        engine.process("result", webContext, resp.getWriter());
    }

    private Report buildReport(HttpServletRequest req){
        Report report = new Report();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        if (req.getParameter("start")==null){
            start = LocalDateTime.now().with(firstDayOfYear());
        } else if (req.getParameter("start")!=null){
            start = LocalDateTime.parse(req.getParameter("start"));
        }

        if (req.getParameter("end")==null){
            end = LocalDateTime.now().with(lastDayOfYear());
        } else if(req.getParameter("end")!=null){
            end = LocalDateTime.parse(req.getParameter("end"));
        }

        report.setStart(start);
        report.setEnd(end);
        report.setRequest(insTanceS.findBeetwDate(start, end));
        return report;
    }
}
