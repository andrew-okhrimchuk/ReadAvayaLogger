package main.servlet;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import main.nonBlockingEchoServer.server.NonBlockingEchoServer_NEW;
import main.nonBlockingEchoServer.timer.MyShutdownHook;
import main.service.ServiceCalls;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static main.servlet.web.ThymeleafListener.engine;

@Slf4j
@WebServlet("")
public class ReportServlet extends HttpServlet {
  ServiceCalls serviceCalls = new ServiceCalls();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start doGet of '/servlets'");
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale(),
                ImmutableMap.of("calls", serviceCalls.buildReport(req)));
        webContext.setVariable("start_def", serviceCalls.get_date("start", req));  //  "start", req.getParameter("start")
        webContext.setVariable("end_def", serviceCalls.get_date("end", req));
        webContext.setVariable("list", serviceCalls.list(req));
        webContext.setVariable("subject", "");
        webContext.setVariable("num", serviceCalls.get_numberInt(req));
        engine.process("calls", webContext, resp.getWriter());
    }


}
