package other.jabber;

//: c15:JabberClient.java
// Очень простой клиент, который просто посылает
// строки на сервер и читает строки,
// посылаемые сервером.
// {RunByHand}
import java.net.*;

import java.io.*;

public class JabberClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Передаем null в getByName(), получая
        // специальный IP адрес "локальной заглушки"
        // для тестирования на машине без сети:
        InetAddress addr = InetAddress.getByName(null);
        // Альтернативно, вы можете использовать
        // адрес или имя:
        // InetAddress addr =
        // InetAddress.getByName("127.0.0.1");
        // InetAddress addr =
        // InetAddress.getByName("localhost");
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, JabberServer.PORT);
        // Помещаем все в блок try-finally, чтобы
        // быть уверенным, что сокет закроется:
        try {
            System.out.println("socket = " + socket);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            while (true) {
                  String str = in.readUTF();
                  System.out.println("Echoing: ");
                  System.out.println("Echoing: " + str);
            }
        }
        finally {
            System.out.println("closing...");
            socket.close();
        }
    }
} // /:~