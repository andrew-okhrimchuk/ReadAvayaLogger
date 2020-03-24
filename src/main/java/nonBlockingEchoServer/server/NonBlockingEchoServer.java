package nonBlockingEchoServer.server;
//https://github.com/teocci/NioSocketCodeSample/blob/master/src/com/github/teocci/nio/socket/nio/NonBlockingEchoServer.java

import com.typesafe.config.Config;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.config.Configs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
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

@Data
@Setter
@RequiredArgsConstructor
@Slf4j
public class NonBlockingEchoServer extends Thread
{
    public static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    private static Config date = Configs.getConfig("common.config","work_date");
    private static boolean stoping = true;

    private InetSocketAddress listenAddress = new InetSocketAddress(Integer.parseInt(date.getString("port")));
    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(4, 30, 20L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));

    private Selector selector;
    private Map<SocketChannel, Long> myConcurrentSet = new ConcurrentHashMap<>();
//    public static AtomicInteger countTextTest = new AtomicInteger();// for tests

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

            cycle(serverChannel);

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

    private void cycle(ServerSocketChannel serverChannel) throws IOException{
        log.info("Start cycle in NonBlockingEchoServer" + "\n");
        while (true) {
            // Wait for events
            int readyCount = selector.select(500L);
            if (readyCount == 0) {
       //         System.out.println("readyCount == 0");
                closeAllChannelsByTimeOut();
                continue;
            }
            // Process selected keys...
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                if (!key.isValid()) continue;
                if (key.isConnectable())
                {
                    ((SocketChannel)key.channel()).finishConnect();
                    log.info("key.isConnectable");
                }
                else if (key.isAcceptable()) {
                    this.accept(key);
                    log.info("key.isAcceptable");
                }
                else if (key.isReadable()) {
                    log.info("key.isReadable");
                    read( key);
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
        //channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
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
    private void read(SelectionKey key)  {
        log.info("Start method readData" );

        SocketChannel channel = (SocketChannel) key.channel();
        String channelName = channel.socket().getRemoteSocketAddress().toString();
        StringBuilder sb = new StringBuilder();

        ByteBuffer buffer = ByteBuffer.allocate(4*1024);
        buffer.clear();
        int count = -1;

        while (true) {
            try {
                if (!((count = channel.read(buffer)) > 0)) break;
            } catch (IOException e) {
                e.printStackTrace();
                log.error("End error channel.read(buffer), "  + e.toString());
            }
            // TODO - in the future pass this to a "listener" which will do something useful with this buffer
            byte[] data = new byte[count];
            System.arraycopy(buffer.array(), 0, data, 0, count);
            buffer.clear();
            sb.append(new String(data));
        }

        if (count == 0) {
            log.info("Non-blocking IO can read 0 bytes, this data should be discarded manually");
            closeChannel(channel);
        }

        if (count < 0) {
            closeChannel(channel);
            log.info("Client closed the link, " + channel.toString());
        }
        log.info("End successful method readData" );

        executorService.execute(new ListenerAvayaReadNIO_OLD(sb, channelName));
        key.cancel();
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
        new NonBlockingEchoServer().start();
       // OneTimeIn5Min.push();
       // OneTimeIn15Min.push();

    }
}
