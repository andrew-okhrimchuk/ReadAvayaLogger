package other.jabber;
//: c15:JabberServer.java
// Очень простой сервер, который просто отсылает
// назад все, что посылает клиент.
// {RunByHand}
import java.io.*;
import java.net.*;

public class JabberServer {
    // Выбираем порт вне пределов 1-1024:
    public static final int PORT = 6666;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Started: " + s);
        try {
            // Блокирует до тех пор, пока не возникнет соединение:
            Socket socket = s.accept();
            int count = 0;
            try {
                System.out.println("Connection accepted: " + socket);
                DataOutputStream out = new DataOutputStream((socket.getOutputStream()));
                while (true) {
                    out.writeUTF("Start " + count);
                    out.flush();
                    System.out.println("Start " + count);
                    Thread.sleep(1000);
                    count++;
                }
                // Всегда закрываем два сокета...
            }
            finally {
                System.out.println("closing...");
                socket.close();
            }
        }
        finally {
            s.close();
        }
    }
} // /:~