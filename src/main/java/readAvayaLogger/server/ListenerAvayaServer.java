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

        try(//DataInputStream in = new DataInputStream(socket.getInputStream());
            InputStream input = socket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
           // BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        )
        {
            log.info("Crated new DataInputStream");

            readFromInputStream(bufferedInputStream);

        }
        catch (ConnectException | EOFException | UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error(e.toString());
            e.printStackTrace();

            if(sb.length() > 0){
                try {
                    saveFile(sb.toString());
                    log.error("saveFile in catch when (ConnectException | EOFException | UnknownHostException e)");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    log.error(ex.toString());
                }
            }

        }
        catch (SocketException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error("Сервер отвалился. (" + e.toString() + ")");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error("catch IOException in startConnection " + e.toString());
        }
    }

    private void saveFile(String text) throws IOException {
        log.info("Start method saveFile" );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss.SSSSSS");
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

        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = Files.createFile(Paths.get(folder_name +".\\" + LocalDateTime.now().format(formatter) + start_name_files + ".txt"));
        FileWriter fw = new FileWriter(testFile1.toString());
        fw.write(text);
        fw.flush();
        fw.close();
        log.info("writed in: "  + testFile1.toString());
        log.info("End successful method saveFile" );
    }
    private void readFromInputStream(InputStream input) throws IOException {
        log.info("Start method readFromInputStream");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss.SSSSSS");
        String folder_name = path_to_save_files.getString("folder_name");
        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = Files.createFile(Paths.get(folder_name + ".\\" + LocalDateTime.now().format(formatter) + start_name_files + currentThread().getName() + "V(7).txt"));
        FileOutputStream outputStream = new FileOutputStream(testFile1.toString());

        log.info("input.available = " + input.available());

        while(input.available() > 0) {
            int data = input.read();
            sb.append(Integer.valueOf(data));
            sb.append(" , ");
            outputStream.write(data);
        }
        if(sb.length() > 0){
            log.info("when sb.length() > 0, than = " + sb.toString());
        }
        outputStream.flush();
        outputStream.close();
        log.info("End method readFromInputStream");
    }
}
