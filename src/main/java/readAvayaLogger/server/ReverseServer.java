package readAvayaLogger.server;

import com.typesafe.config.Config;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import readAvayaLogger.config.Configs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Data
@RequiredArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor
@Slf4j
//@Основной цикл прослушки порта и запуска исполнителя чтения
public class ReverseServer extends Thread {
    private final static Config date = Configs.getConfig("common.config","work_date");

    public void run() {
        log.info("run method startConnection");
        log.info("Get port = " + Integer.parseInt(date.getString("port")));
       /* String number = "10A";
        val result = Integer.parseInt(number);*/

        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(date.getString("port")))) {
        log.info("Server is listening on port  = " + Integer.parseInt(date.getString("port")));


            while (true) {
                System.out.println("Start of cycle");
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ListenerAvayaServer(socket).start();
            }

        } catch (IOException ex) {
            log.error("Server exception: " + ex.toString());
            ex.printStackTrace();
        }
    }
}
