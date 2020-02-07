package client;

import com.typesafe.config.Config;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Slf4j
public class ListenerAvayaClient extends Thread{
    private Config date;
    private Config path_to_save_files;
    private int length;

    public void run()  {
        log.info("run method startConnection");
// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try(Socket socket = new Socket(date.getString("host"), Integer.parseInt(date.getString("port")));
            DataInputStream in = new DataInputStream(socket.getInputStream()); )
        {
            log.info("Client connected to socket.");
            log.info("Client writing channel = oos & reading channel = ois initialized.");

            StringBuilder sb = new StringBuilder();
            int count = 0;
            log.info("Socket = " + socket);

            while(!socket.isOutputShutdown()  ){
                String str = in.readUTF();
                System.out.println(" ");
                log.info("Echoing = " + str);

                if(count == length){
                    saveFile(sb.toString());
                    sb =  new StringBuilder();
                    count = 0;
                }
                count++;
                sb.append(str).append(System.getProperty("line.separator"));
            }

        }
        catch (ConnectException | EOFException | UnknownHostException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
            log.error("catch ConnectException | EOFException | UnknownHostException in startConnection. Exception = " + e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("catch IOException in startConnection " + e.getMessage());
        }
    }

    private void saveFile(String clientCommand) throws IOException {
        log.info("Start method saveFile" );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss.SSSSSS");
        String folder_name = path_to_save_files.getString("folder_name");

        Path filePath = Paths.get(".\\" + folder_name);
        if(!Files.exists(Paths.get(filePath.toString()))){
            Files.createDirectory(filePath);
            log.info("createDirectory: "  + filePath.toString());
        }

        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = Files.createFile(Paths.get(folder_name +".\\" + LocalDateTime.now().format(formatter) + start_name_files + ".txt"));
        FileWriter fw = new FileWriter(testFile1.toString());
        fw.write(clientCommand);
        fw.flush();
        //fw.close();
        log.info("writed in: "  + testFile1.toString());
        log.info("End successful method saveFile" );

    }

}
