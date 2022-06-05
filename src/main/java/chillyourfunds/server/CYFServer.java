package chillyourfunds.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CYFServer extends Frame implements Runnable {
    private ServerSocket serverSocket;

    protected CYFData database;

    private final List<CYFService> clients = new ArrayList<>();

    private final Properties props;


    private Thread serverThread;

    public CYFServer(Properties p, String title) {
        super(title);
        props = p;
        int port = Integer.parseInt(props.getProperty("port"));
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Error starting IBServer.");
            return;
        }
        Button b = new Button("stop and exit");
        b.addActionListener((actionEvent) -> {
            send(CYFProtocol.STOP);
            new Thread(() -> {
                while (clients.size() != 0) {
                    try {
                        TimeUnit.of(ChronoUnit.MILLIS).sleep(500);
                    } catch (InterruptedException ignore) {
                        break;
                    }
                }
                saveDatabase("database.json");
                System.exit(0);
            }).start();
        });
        add(b);
        pack();
        (serverThread = new Thread(this)).start();
        EventQueue.invokeLater(() -> setVisible(true));
        database = loadDatabase("database.json");
    }

    public void run() {
        while (serverThread == Thread.currentThread()) {
            try {
                Socket clientSocket = serverSocket.accept();
                createAndStartClientService(clientSocket);
            } catch (IOException e) {
                System.err.println("Error accepting connection. Client will not be served...");
            }
        }
    }

    public void stopRunning() {
        serverThread = null;
        saveDatabase("database.json");
    }

    public void saveDatabase(String filename) {

        GsonBuilder gbuilder = new GsonBuilder();
        gbuilder.setPrettyPrinting();
        gbuilder.disableHtmlEscaping(); // for disable auto replacing special characters
        Gson gson = gbuilder.create();
        try (FileWriter pw = new FileWriter(filename)) {
            gson.toJson(database, pw);
            System.out.println("Database saved");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public CYFData loadDatabase(String filepath){
        try {
            Gson gson = new Gson();
            FileReader fr = new  FileReader(filepath);
            return gson.fromJson(fr, new CYFData() {
            }.getClass().getGenericSuperclass());
        }catch (FileNotFoundException e){
            System.out.println("Database file doesnt exist. Create new one ");
            return new CYFData();
        }

    }

    synchronized void createAndStartClientService(Socket clientSocket) throws IOException {
        CYFService clientService = new CYFService(clientSocket, this);
        clientService.init();
        new Thread(clientService).start();
        clients.add(clientService);
        System.out.println("Client added. Number of clients: " + clients.size());
    }

    synchronized void removeClientService(CYFService clientService) {
        clients.remove(clientService);
        clientService.close();
        System.out.println("Client removed. Number of clients: " + clients.size());
    }

    synchronized void send(CYFProtocol proto) {
        for (CYFService s : clients) { // roześlij do wszystkich klientów
            s.broadcast(proto);
        }
    }


//    synchronized void send(String msg, CYFService skip) {
//        for (CYFService s : clients) { // roześlij do wszystkich klientów
//            if (s != skip) { // oprócz jednego, którego trzeba pominąć...
//                s.send(msg);
//            }
//        }
//    }


    private int $lastID = -1;

    synchronized int nextID() {
        return ++$lastID;
    }


    int boardWidth() {
        return Integer.parseInt(props.getProperty("width"));
    }

    int boardHeight() {
        return Integer.parseInt(props.getProperty("height"));
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        String pName = "CYFServer.properties";
        try {
            props.load(new FileInputStream(pName));
        } catch (Exception e) {
            props.put("port", "40000");
            props.put("width", "250");
            props.put("height", "250");
        }
        try {
            props.store(new FileOutputStream(pName), null);
        } catch (Exception ignore) {
        }
        new CYFServer(props, "Internet Board Server");
    }
}