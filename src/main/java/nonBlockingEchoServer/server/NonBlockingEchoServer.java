package nonBlockingEchoServer.server;
//https://github.com/teocci/NioSocketCodeSample/blob/master/src/com/github/teocci/nio/socket/nio/NonBlockingEchoServer.java

import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.config.Configs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Slf4j
public class NonBlockingEchoServer extends Thread
{
    private static boolean stoping = true;
    public  static Config date = Configs.getConfig("common.config","work_date");
    private ExecutorService executorService = Executors.newFixedThreadPool(30);
    private final static int PORT = Integer.parseInt(date.getString("port"));
    private Selector selector;
    private InetSocketAddress listenAddress = new InetSocketAddress(PORT);

    public void run() {
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(listenAddress);
            serverChannel.configureBlocking(false);

            this.selector = Selector.open();
            serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            log.info("Server started on port >> " + PORT);

            cycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cycle() throws IOException{
        log.info("Start cycle in NonBlockingEchoServer");
        while (stoping) {
            // Wait for events
            int readyCount = selector.select();
            if (readyCount == 0) {
                continue;
            }

            // Process selected keys...
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();

                // Remove key from set so we don't process it twice
                iterator.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    // Accept client connections
                    this.accept(key);
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();

                    if(channel.isOpen()){
                        log.info("Read from client");
                        executorService.execute(new ListenerAvayaReadNIO(channel));
                        key.cancel();
                    }
                }
                else {log.info("key.is NOT Readable()");}
            }
        }
    }

    private void accept(SelectionKey key) throws IOException
    {
        // Accept client connection
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connected to: " + remoteAddr);

        channel.register(this.selector, SelectionKey.OP_READ);
    }
    public static void stoping(){
        stoping = false;
    }

    public static void main(String[] args)
    {
        new NonBlockingEchoServer().start();

    }
}
