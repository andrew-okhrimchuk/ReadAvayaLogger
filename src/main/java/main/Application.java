package main;

import main.nonBlockingEchoServer.server.NonBlockingEchoServer_NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        NonBlockingEchoServer_NEW nonBlockingEchoServer_new = (NonBlockingEchoServer_NEW) ctx.getBean("nonBlockingEchoServer_NEW");
        nonBlockingEchoServer_new.start();

    }

}