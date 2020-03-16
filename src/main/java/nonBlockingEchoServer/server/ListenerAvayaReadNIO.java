package nonBlockingEchoServer.server;

import com.typesafe.config.Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.config.Configs;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RequiredArgsConstructor
@Slf4j
public class ListenerAvayaReadNIO extends Thread{
    public static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    public final SelectionKey key;
    private StringBuilder sb = new StringBuilder();

    public void run()  {
        SocketChannel channel = (SocketChannel) key.channel();

        log.info("run method startReading");

        try {
            readData(channel);
            sbToLog(sb.toString());
            saveFileToFileWriter(new String(sb));
        }
        catch (ConnectException | EOFException | UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error(e.toString());
            e.printStackTrace();
        }
        catch (SocketException e) {
            // TODO Auto-generated catch block
            log.error("Сервер отвалился. (" + e.toString() + ")");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("catch IOException in startConnection " + e.toString());
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("catch Exception in startConnection " + e.toString());

        }
    }

    private void saveFileToFileWriter(String text) throws IOException {
        log.info("Start method saveFileToFileWriter" );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss.SSSSSS");
        String folder_name = path_to_save_files.getString("folder_name");

        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = Files.createFile(Paths.get(folder_name +".\\" + LocalDateTime.now().format(formatter) + start_name_files + currentThread().getName()+ ".txt"));
        FileWriter fw = new FileWriter(testFile1.toString());
        fw.write(text);
        fw.flush();
        fw.close();
        log.info("End successful method saveFile" );
    }

    private void readData(SocketChannel channel) throws Exception {
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
            channel.close();
            key.cancel();
        }
    }

    private void sbToLog(String data){
        log.info("Got: " + data);
        log.info("Got: data.length() = " + data.length());
    }
}

