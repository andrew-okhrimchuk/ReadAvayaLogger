package main.nonBlockingEchoServer.server;

import com.typesafe.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.entity.Calls;
import main.mongo.CallsRepository;
import main.mongo.ServiceCallsMongoDB;
import main.entity.ToCalls;
import main.nonBlockingEchoServer.util.UtilText;
import main.nonBlockingEchoServer.config.Configs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import static main.nonBlockingEchoServer.server.NonBlockingEchoServer_NEW.countTextTest;

@Slf4j
@Component ("saveData")

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveData extends Thread {
    public static Config path_to_save_files = Configs.getConfig("common.config", "path_to_save_files");
    @Setter
    @Getter
    private StringBuilder sb;

    private final CallsRepository callsRepository;

    @Autowired
    public SaveData(CallsRepository repo) {
        callsRepository = repo;
    }

    public void run1() {
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
//        log.info("Result: " + data);
        log.info("Result: Long text is = " + data.length());
        countTextTest.addAndGet(data.length());
    }

    public void run(){
        String text = sb.toString();
        sbToLog(text);
        log.info("Try save to DB.");
        UtilText ut = new UtilText();
        List<Calls> toSave = ut.StringToListToCalls(sb.toString());
        int result = 0;
        if (toSave.size() > 0){
            Collection<Calls> resultCalls = callsRepository.insertManyDocuments(toSave);
            if (!resultCalls.isEmpty()){
                result = resultCalls.size();
            }
        }
        else {log.info("Date is empty! Not save.");}

        log.info("Saved " + result + " items, from " + toSave.size()+ " items.");
    }
}
