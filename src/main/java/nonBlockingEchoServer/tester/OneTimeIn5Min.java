package nonBlockingEchoServer.tester;

import com.typesafe.config.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.config.Configs;
import nonBlockingEchoServer.timer.Start_of_the_fallen_thread_NIO;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class OneTimeIn5Min extends Thread {
    private final static long delay  = 1000L;
    private final static long time  = 5 * 1000 * 60;

    private static Config date = Configs.getConfig("common.config","work_date");
    private final InetSocketAddress listenAddress = new InetSocketAddress(Integer.parseInt(date.getString("port")));

    public void run()  {
        log.info("run method startConnection");

// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента

        log.info("host = " + listenAddress.getHostString());
        log.info("port = " + listenAddress.getPort());

        try(Socket socket = new Socket(listenAddress.getHostString(), listenAddress.getPort());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()))
        {
            log.info("Client connected to socket.");
            log.info("Client writing channel = oos & reading channel = ois initialized.");
            log.info("Socket = " + socket);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.writeUTF("@ TEST MESSAGE! One time in 5 minuts");
            log.info("Successful out writeUTF" +"\n");

        }
        catch (ConnectException | EOFException | UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error(e.toString());
            e.printStackTrace();


        }
        catch (SocketException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error("Сервер отвалился. (" + e.toString() + ")");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error("catch IOException in startConnection " + e.toString());
        }
    }


    public static void push() {
        log.info("Start OneTimeIn5Min");
        OneTimeIn5Min oneTimeIn5Min = new OneTimeIn5Min();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(oneTimeIn5Min, delay, time, TimeUnit.MILLISECONDS);
        log.info("End OneTimeIn5Min");
    }
}
