package nonBlockingEchoServer.server;

import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mongo.ServiceCallsMongoDB;
import nonBlockingEchoServer.config.Configs;
import nonBlockingEchoServer.util.ToCalls;
import nonBlockingEchoServer.util.UtilText;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static nonBlockingEchoServer.server.NonBlockingEchoServer_NEW.countTextTest;

@Slf4j
@AllArgsConstructor
public class SaveData extends Thread {
    public static Config path_to_save_files = Configs.getConfig("common.config", "path_to_save_files");
    private StringBuilder sb;

    public void run() {
        String text = sb.toString();
        sbToLog(text);

        log.info("Start method saveFileToFileWriter");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss.SSSSSS");
        String folder_name = path_to_save_files.getString("folder_name");

        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = null;
        try {
            testFile1 = Files.createFile(Paths.get(folder_name + ".\\" + LocalDateTime.now().format(formatter) + start_name_files + currentThread().getName() + ".txt"));
            FileWriter fw = new FileWriter(testFile1.toString());
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ERROR in read text in File" + e.toString());
        }
        log.info("End successful method saveFile");
    }

    private void sbToLog(String data){
        log.info("Result: " + data);
        log.info("Result: Long text is = " + data.length());
        countTextTest.addAndGet(data.length());
    }

    public void run1(){
        ServiceCallsMongoDB mongoDB = new ServiceCallsMongoDB();
        UtilText ut = new UtilText();
        List<ToCalls> toCalls = ut.StringToListToCalls(sb.toString());
        mongoDB.insertManyDocuments(toCalls);
    }
}
