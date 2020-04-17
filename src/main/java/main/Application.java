package main;

import main.nonBlockingEchoServer.server.NonBlockingEchoServer_NEW;
import main.move_base.MoveBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        NonBlockingEchoServer_NEW nonBlockingEchoServer_new = (NonBlockingEchoServer_NEW) ctx.getBean("nonBlockingEchoServer_NEW");
        nonBlockingEchoServer_new.start();


        @Deprecated
        MoveBase moveBase = (MoveBase) ctx.getBean("MoveBase");
        moveBase.moveCallsAtherBase();
    }
}