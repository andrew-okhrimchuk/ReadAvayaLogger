package nonBlockingEchoServer.server;
//https://github.com/teocci/NioSocketCodeSample/blob/master/src/com/github/teocci/nio/socket/nio/NonBlockingEchoServer.java

import com.typesafe.config.Config;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.config.Configs;
import nonBlockingEchoServer.tester.OneTimeIn5Min;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Setter
@RequiredArgsConstructor
@Slf4j
public class NonBlockingEchoServer_NEW extends Thread
{
    public static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    private static Config date = Configs.getConfig("common.config","work_date");
    private static boolean stoping = true;

    private InetSocketAddress listenAddress = new InetSocketAddress(Integer.parseInt(date.getString("port")));
   // private ExecutorService executorService = Executors.newFixedThreadPool(30);
    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 5, 20L,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
    private Selector selector;
    private Map<SocketChannel, Long> myConcurrentSet = new ConcurrentHashMap<>();
    public static AtomicInteger countTextTest = new AtomicInteger();// for tests

    public void run() {
        crateFolder();
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(listenAddress);
            serverChannel.configureBlocking(false);


            int ops = serverChannel.validOps();
            this.selector = Selector.open();
            serverChannel.register(this.selector, ops, null);
            log.info("Server started on port >> " + listenAddress.getPort());

            cycle();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("catch IOException in run " + e.toString());
            try {
                serverChannel.close();
            } catch (IOException | NullPointerException ex) {
                ex.printStackTrace();
                log.error("catch IOException in serverChannel.close() " + ex.toString());

            }
        }
        finally {
            try {
                serverChannel.close();
            } catch (IOException | NullPointerException ex) {
                ex.printStackTrace();
                log.error("catch IOException in finally serverChannel.close() " + ex.toString());

            }
        }
    }

    private void cycle() throws IOException{
        log.info("Start cycle in NonBlockingEchoServer" + "\n");
        while (true) {
            // Wait for events
            int readyCount = selector.select(24*60*60*1000L);
            if (readyCount == 0) {
                log.info("readyCount == 0" );

                closeAllChannelsByTimeOut();
                continue;
            }
            // Process selected keys...
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();

                if (key.isConnectable())
                {
                    ((SocketChannel)key.channel()).finishConnect();
                    log.info("key.isConnectable");
                }

                else if (key.isAcceptable()) {
                    // Accept client connections
                    this.accept(key);
                    log.info("key.isAcceptable");

                }
                else if (key.isReadable()) {
                    log.info("key.isReadable");
                    SocketChannel channel = (SocketChannel) key.channel();
                    //String channelName = channel.socket().getRemoteSocketAddress().toString();
                    //StringBuilder sb = new StringBuilder();

                    executorService.execute(new ListenerAvayaReadNIO_NEW(channel));
                        key.cancel();
                }
                else {
                    log.info("key.is NOT Readable()");
                }
                iterator.remove();
            }
            selector.selectedKeys().clear();
        }
    }

    private void accept(SelectionKey key) throws IOException
    {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverSocketChannel.accept();

        if (channel != null) {
        channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
        channel.configureBlocking(false);
        myConcurrentSet.put(channel,  System.currentTimeMillis());
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        log.info("Connected to: " + remoteAddr);

        channel.register(selector, SelectionKey.OP_READ);
        } else {
            key.cancel();
        }
    }

    private static void crateFolder(){
        String folder_name = path_to_save_files.getString("folder_name");

        Path filePath = Paths.get(".\\" + folder_name);
        if(!Files.exists(Paths.get(filePath.toString()))){
            try {
                Files.createDirectory(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("createDirectory: "  + filePath.toString());
        }
    }
    public static void stoping(){
        stoping = false;
    }
    public void closeAllChannelsByTimeOut(){
        Set<Map.Entry<SocketChannel, Long>> entrySet = myConcurrentSet.entrySet();
        Iterator<Map.Entry<SocketChannel, Long>> itr = entrySet.iterator();
        int seconds = 2;

        while (itr.hasNext())
        {
            Map.Entry<SocketChannel, Long> entry = itr.next();
            SocketChannel channel = entry.getKey();
            Long value = entry.getValue();

            if (checkTimeout(value, seconds)) {
                if (channel.isOpen()) {
                    log.info("Try close channel by TIMEOUT." + channel.toString() );
                    closeChannel(channel);
                }
                itr.remove();
            }
            log.info("End method closeAllChannelsByTimeOut." );
        }

    }
    private boolean checkTimeout(Long value, int seconds ){
        long diffrents = System.currentTimeMillis() - value;
        log.info("Timeout is "+seconds+" seconds. Idle = " + (double)diffrents/1000 + " Seconds." );
        return diffrents > (seconds*1000);
    }
    public void closeChannel(SocketChannel channel){
        boolean check = false;
        String socketName = null;
        try {
            socketName = channel.socket().getRemoteSocketAddress().toString();
            if (channel.isOpen()) {
                channel.close();
                check = true;
                if(!channel.isOpen()){log.info("closeChannel: socket close " + socketName);}
            }
        } catch (NullPointerException | IOException e) {
            log.error("Catch IOException in closeChannel" + e.toString() );
        }
        if (!check){log.info("The " + socketName + " can't close. It is not open." );}

    }


    public static void main(String[] args)
    {
        new NonBlockingEchoServer_NEW().start();
      //  OneTimeIn5Min.push();
       // OneTimeIn15Min.push();

    }
}
