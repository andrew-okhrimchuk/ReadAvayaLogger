package servlet;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.server.NonBlockingEchoServer_NEW;
import nonBlockingEchoServer.timer.MyShutdownHook;
import org.thymeleaf.context.WebContext;
import service.ServiceCalls;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static servlet.web.ThymeleafListener.engine;

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

    static {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new NonBlockingEchoServer_NEW());
        Runtime.getRuntime().addShutdownHook(new MyShutdownHook(executor));
    }
}
