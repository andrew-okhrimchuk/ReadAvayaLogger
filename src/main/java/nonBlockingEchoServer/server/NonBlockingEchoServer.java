package nonBlockingEchoServer.server;
//https://github.com/teocci/NioSocketCodeSample/blob/master/src/com/github/teocci/nio/socket/nio/NonBlockingEchoServer.java

import com.typesafe.config.Config;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.config.Configs;
import nonBlockingEchoServer.tester.OneTimeIn15Min;
import nonBlockingEchoServer.tester.OneTimeIn5Min;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private ExecutorService executorService = Executors.newFixedThreadPool(30);
    private Selector selector;
    private Set<SocketChannel> myConcurrentSet = ConcurrentHashMap.newKeySet();
    public static Set<Integer> countText = ConcurrentHashMap.newKeySet();

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
            int readyCount = selector.select(1000);
            if (readyCount == 0) {
                closeAllChannels();
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
                    String channelName = channel.socket().getRemoteSocketAddress().toString();
                    StringBuilder sb = new StringBuilder();
                    try {
                        log.info("Read from client.");
                        sb = readData(channel);
                    } catch (Exception e) {
                       log.error("What i catch? " + e.toString());
                        key.cancel();
                    }
                    executorService.execute(new ListenerAvayaReadNIO_2(sb, channelName));
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
        myConcurrentSet.add(channel);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        log.info("Connected to: " + remoteAddr);

        channel.register(selector, SelectionKey.OP_READ);
        } else {
            key.cancel();
        }
    }
    private StringBuilder readData(SocketChannel channel) throws Exception {
        log.info("Start method readData" );
        StringBuilder sb = new StringBuilder();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count = -1;


        while ((count = channel.read(buffer)) > 0) {
            // TODO - in the future pass this to a "listener" which will do something useful with this buffer
            byte[] data = new byte[count];
            System.arraycopy(buffer.array(), 0, data, 0, count);
            buffer.clear();
            sb.append(new String(data));
        }

        if (count < 0) {
            Socket socket = channel.socket();
            SocketAddress remoteAddress = socket.getRemoteSocketAddress();
            log.info("Connection closed by client: " + remoteAddress);
            socket.close();//может убрать????? по документации канал закрывает сокет. Оставлю на всякий случ.
            log.info("socket.close() " +  socket.isClosed() );
            channel.close();
            log.info("channel.isOpen() " +  channel.isOpen());
        }
        log.info("End successful method readData" );

        return sb;
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
    public void closeAllChannels(){
        Iterator<SocketChannel> iterator = myConcurrentSet.iterator();
        boolean check = false;
        while (iterator.hasNext())
        {
            try {
                SocketChannel channel = iterator.next();
                SocketAddress socketName = channel.socket().getRemoteSocketAddress();
                if (channel.isOpen()) {
                    channel.close();
                    if(!check){check = true;}
                    if(!channel.isOpen()){log.info("CloseAllChannels: socket close by timeout " + socketName.toString());}
                }


            } catch (IOException e) {
                log.error("Catch IOException in iterator.next().close()" + e.toString() );
            }
            iterator.remove();

        }
        if (check){log.info("End successful CloseAllChannels." );}
    }


    public static void main(String[] args)
    {
        new NonBlockingEchoServer().start();
      //  OneTimeIn5Min.push();
       // OneTimeIn15Min.push();

    }
}
