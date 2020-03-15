package readAvayaLogger.server;

import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import readAvayaLogger.config.Configs;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
//@AllArgsConstructor
@Slf4j
public class ListenerAvayaServer extends Thread{
    private final static Config date = Configs.getConfig("common.config","work_date");
    private final static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    private final static int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");
    private final Socket socket;
    private StringBuilder sb = new StringBuilder();

    public void run()  {
        log.info("run method startReading");

        try(InputStream input = socket.getInputStream();)
        { log.info("Crated socket.getInputStream()");

            if (input.available() > 0) {
                log.info("Input.available() > 0. Input = " + input.available());
                readFromInputStream(input);
            }
            else { log.info("Input.available() = 0. Can't save anything.");}


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
        }
    }

    private void readFromInputStream(InputStream input) throws IOException {
        log.info("Start method readFromInputStream");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss.SSSSSS");
        String folder_name = path_to_save_files.getString("folder_name");
        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = Files.createFile(Paths.get(folder_name + ".\\" + LocalDateTime.now().format(formatter) + start_name_files + currentThread().getName() + "V(7).txt"));
        FileOutputStream outputStream = new FileOutputStream(testFile1.toString());

            while(input.available() > 0) {
                int data = input.read();
                sb.append(Integer.valueOf(data));
                sb.append(" , ");
                outputStream.write(data);
            }
            log.info("when sb.length() > 0, than = " + sb.toString());

        outputStream.flush();
        outputStream.close();
        log.info("End method readFromInputStream");
    }
}
