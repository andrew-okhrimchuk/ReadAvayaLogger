package nonBlockingEchoServer.server;

import com.typesafe.config.Config;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.config.Configs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RequiredArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor
@Slf4j
//@Основной цикл прослушки порта и запуска исполнителя чтения
public class ReverseServer extends Thread {
    private static boolean stoping = true;
    public  static Config date = Configs.getConfig("common.config","work_date");
    public static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    private ExecutorService executorService = Executors.newFixedThreadPool(35);

    public void run() {
        crateFolder();
        log.info("run method startConnection");
        log.info("Get port = " + Integer.parseInt(date.getString("port")));


         try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(date.getString("port")))) {
        log.info("Server is listening on port  = " + Integer.parseInt(date.getString("port")));


            while (stoping) {
                log.info("Start of cycle");
                Socket socket = serverSocket.accept();
                log.info("New client connected");

                if (socket!=null){
                    executorService.execute(new ListenerAvayaServer(socket));                }
                else {
                    log.info("socket = null!!!");
                    break;
                }
            }

        } catch (IOException ex) {
            log.error("Server exception: " + ex.toString());
            ex.printStackTrace();
             executorService.shutdown();
        }
         finally {
             executorService.shutdown();
         }
        executorService.shutdownNow();
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
}
