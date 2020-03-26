package nonBlockingEchoServer.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Slf4j
public class ListenerAvayaReadNIO_NEW extends Thread{
    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(4, 8, 20L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
    private StringBuilder sb;
    private final SocketChannel channel;

    public void run()  {
        log.info("Start method run" );
        try {
            readData(channel);
        }
        catch (ConnectException | EOFException | UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error(e.toString());
            closeChannel(channel);
            log.error("Catch exception. = " + e.toString() + ")");

        }
        catch (SocketException e) {
            // TODO Auto-generated catch block
            closeChannel(channel);
            log.error("Сервер отвалился. (" + e.toString() + ")");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("catch IOException in startConnection " + e.toString());
            closeChannel(channel);
        }
        catch (Exception e) {
            closeChannel(channel);
            log.error("catch Exception in startConnection " + e.toString());
        }

        log.info("End successful method run" + "\n");

    }
    private void readData(SocketChannel channel) throws Exception {
        log.info("Start method readData" );
        sb = new StringBuilder();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count = -1;

        while (channel.isOpen() && channel.isConnected() ) {
            while ((count = channel.read(buffer)) > 0) {
                // TODO - in the future pass this to a "listener" which will do something useful with this buffer
                byte[] data = new byte[count];
                System.arraycopy(buffer.array(), 0, data, 0, count);
                buffer.clear();
                sb.append(new String(data));
            }

            if (count <= 0) {
                if (sb.length() > 0) {
                    executorService.execute(new SaveData(sb));
                    sb = new StringBuilder();
                    log.info("Shannel.read(buffer) <= 0. Save to DB. " +"\n"+ channel.toString());
                }
            }
        }
        closeChannel(channel);
        log.info("End successful method readData" );
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
}

