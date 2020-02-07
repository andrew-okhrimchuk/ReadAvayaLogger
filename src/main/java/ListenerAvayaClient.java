import com.typesafe.config.Config;
import config.Configs;
import jabber.JabberServer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListenerAvayaClient {
    private Config work_date = Configs.getConfig("config.conf","work_date");
    private static Config path_to_save_files = Configs.getConfig("config.conf","path_to_save_files");

    public void startConnection() throws InterruptedException {

// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try(Socket socket = new Socket(work_date.getString("host"), Integer.parseInt(work_date.getString("port")));
            DataInputStream in = new DataInputStream(socket.getInputStream()); )
        {
            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

// проверяем живой ли канал и работаем если живой
            System.out.println("socket = " + socket);
            while (true) {
                String str = in.readUTF();
                System.out.println("Echoing: ");
                System.out.println("Echoing: " + str);
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void saveFile(String clientCommand) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        String folder_name = path_to_save_files.getString("folder_name");
        Path filePath = Paths.get(".\\" + folder_name);
        if(!Files.exists(Paths.get(filePath.toString()))){Files.createDirectory(filePath);}
        String start_name_files = path_to_save_files.getString("start_name_files");
        Path testFile1 = Files.createFile(Paths.get(folder_name +".\\" + LocalDateTime.now().format(formatter) + start_name_files + ".txt"));
        FileWriter fw = new FileWriter(testFile1.toString());
        fw.write(clientCommand);
    }
    public static void main(String[] args) throws InterruptedException, IOException {
     //   JabberServer.main(null);
     //   Thread.sleep(4000);
        ListenerAvayaClient lac = new ListenerAvayaClient();
        lac.startConnection();
    }
}
