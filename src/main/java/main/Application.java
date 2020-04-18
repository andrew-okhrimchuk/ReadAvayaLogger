package main;

import lombok.extern.slf4j.Slf4j;
import main.config.Constants;
import main.nonBlockingEchoServer.server.NonBlockingEchoServer_NEW;
import main.move_base.MoveBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;


@SpringBootApplication
@Slf4j
public class Application {
    @Inject
    private Environment env;

    /**
     * Spring profiles can be configured with program arguments --spring.profiles.active=your-active-profile
     */

    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);

        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

        // Check if the selected profile has been set as argument.
        // If not, the development profile will be used.
        addDefaultProfile(app, source);
        ApplicationContext apx = app.run(args);
        Environment env = apx.getEnvironment();
        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

        NonBlockingEchoServer_NEW nonBlockingEchoServer_new = (NonBlockingEchoServer_NEW) apx.getBean("nonBlockingEchoServer_NEW");
        nonBlockingEchoServer_new.start();


       // @Deprecated
      //  MoveBase moveBase = (MoveBase) app.getBean("MoveBase");
      //  moveBase.moveCallsAtherBase();
    }


    /**
     * Set a default profile if it has not been set
     */
    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active")) {
            app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
        }
    }
}