package server;

import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.nonBlockingEchoServer.config.Configs;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class ListenerAvayaClientTest_2 extends Thread{
    private Config date;
    private int length;

    public void run()  {
        log.info("run method startConnection");
// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента

        String host = date.getString("host");
        int port = Integer.parseInt(date.getString("port"));
        log.info("host = " + host);
        log.info("port = " + port);
        System.out.println("host = " + host);
        System.out.println("port = " + port);

        try(Socket socket = new Socket(host, port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()))
        {
            log.info("Client connected to socket.");
            log.info("Client writing channel = oos & reading channel = ois initialized.");

            int count = 0;
            log.info("Socket = " + socket);

            while(count < 3  ){
                out.writeUTF("longText");
                out.writeUTF("longText");
                out.flush();
                count++;
            }
            log.info("count = " + count);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        catch (ConnectException | EOFException | UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error(e.toString());
            e.printStackTrace();


        }
        catch (SocketException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error("Server is get out" + e.toString() + ")");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error("catch IOException in startConnection " + e.toString());
        }
    }


    public static void main(String[] args) {
        Config date = Configs.getConfig("common.config","test_date");
        int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");

        ListenerAvayaClientTest_2 jt = new ListenerAvayaClientTest_2(date, length);
        jt.start();
    }
}
