package server;
//: c15:JabberServer.java
// Очень простой сервер, который просто отсылает
// назад все, что посылает клиент.
// {RunByHand}

import com.typesafe.config.Config;
import readAvayaLogger.config.Configs;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
@Data
@AllArgsConstructor
@Slf4j
public class JabberServerDataTest extends Thread {
    private Config date;
    private int length;
    // Выбираем порт вне пределов 1-1024:

    public void run()   {

        log.info("Run Thread -> JabberServerDataTest");
        try (ServerSocket s = new ServerSocket(Integer.parseInt(date.getString("port")))) {
            System.out.println("Started: " + s);
            try {
                System.out.println("getLocalSocketAddress = " + s.getLocalSocketAddress());
                System.out.println("getInetAddress = " + s.getInetAddress());
                // Блокирует до тех пор, пока не возникнет соединение:
                Socket socket = s.accept();

                int count = 0;
                try {
                    System.out.println("Connection accepted: " + socket);
                    DataOutputStream out = new DataOutputStream((socket.getOutputStream()));
                    while (count < 1000) {
                        out.writeUTF("Ну очень нужная информация, count = " + count);
                        out.flush();
                        log.info("JabberServerDataTest, count =  " + count);
                        Thread.sleep(10);
                        count++;
                    }

                    // Всегда закрываем два сокета...
                    out.close();
                    socket.close();
                } catch (InterruptedException e) {
                    log.error("catch InterruptedException in JabberServerDataTest" + e.getMessage());
                    e.printStackTrace();
                } finally {
                    System.out.println("closing...");
                    socket.close();
                    log.info("In finally closed socket");

                }
            } finally {
                s.close();
                log.info("In finally closed ServerSocket");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("catch IOException in JabberServerDataTest" + e.getMessage());
        }
    }
    public static void main(String[] args) {
         Config date = Configs.getConfig("common.config","work_date");
         int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");

        JabberServerDataTest jt = new JabberServerDataTest(date, length);
        jt.start();
    }
} // /:~