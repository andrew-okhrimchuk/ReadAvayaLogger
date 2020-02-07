import com.typesafe.config.Config;
import config.Configs;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Test2 {
    private static Config path_to_save_files = Configs.getConfig("config.conf","path_to_save_files");
    public static void main(String[] args) throws IOException {
        //Path testFilePath = Paths.get("./Test");

        //Пример строки пути для запуска в Windows 
/*
        Path testFilePath = Paths.get(".\\Test" + ".\\test.txt");


        System.out.println("The file name is: " + testFilePath.getFileName());
        System.out.println("It's URI is: " + testFilePath.toUri());
        System.out.println("It's absolute path is: "
                + testFilePath.toAbsolutePath());
        System.out.println("It's normalized path is: "
                + testFilePath.normalize());

        //Получение другого объекта строки по нормализованному относительному пути 
        Path testPathNormalized = Paths
                .get(testFilePath.normalize().toString());
        System.out.println("It's normalized absolute path is: "
                + testPathNormalized.toAbsolutePath());
*/

        saveFile(null);
   /*     System.out.println("It's normalized real path is: "
                + testFilePath.toRealPath(LinkOption.NOFOLLOW_LINKS));*/
    }
    private static void saveFile(String clientCommand) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        String folder_name = path_to_save_files.getString("folder_name");
        Path filePath = Paths.get(".\\" + folder_name);
        if(!Files.exists(Paths.get(filePath.toString()))){Files.createDirectory(filePath);}
        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = Files.createFile(Paths.get(folder_name +".\\" + LocalDateTime.now().format(formatter) + start_name_files + ".txt"));


        //Path filePath = Paths.get(".\\SaveLoggers");
    }
} 