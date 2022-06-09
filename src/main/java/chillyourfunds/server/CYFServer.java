package chillyourfunds.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class CYFServer extends Frame implements Runnable {
    private ServerSocket _serverSocket;

    protected CYFData _database;

    private  final List<CYFService> _clients = Collections.synchronizedList(new ArrayList<CYFService>());

    private final Properties _props;


    private boolean _isRunning = true;

    public CYFServer(Properties p, String title) {
        super(title);
        _props = p;
        int port = Integer.parseInt(_props.getProperty("port"));
        try {
            _serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Error starting IBServer.");
            return;
        }
        Button b = new Button("stop and exit");
        b.addActionListener((actionEvent) -> {
            _isRunning = false;
            try {
                _serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        add(b);
        pack();
        _database = loadDatabase("database.json");
        EventQueue.invokeLater(() -> setVisible(true));
    }

    public void run() {
        while (_isRunning) {
            try {
                Socket clientSocket = _serverSocket.accept();
                createAndStartClientService(clientSocket);
            } catch (IOException e) {
                if(_isRunning){
                    System.err.println("Error accepting connection. Client will not be served...");
                }
                _isRunning = false;
            }
        }
    }

    public void cleanup(){
        send(CYFProtocol.STOP);
        for (CYFService client : _clients) {
            client.close();
        }
        saveDatabase("database.json");
        System.exit(0);
    }


    public void saveDatabase(String filename) {

        GsonBuilder gbuilder = new GsonBuilder();
        gbuilder.setPrettyPrinting();
        gbuilder.disableHtmlEscaping(); // for disable auto replacing special characters
        Gson gson = gbuilder.create();
        try (FileWriter pw = new FileWriter(filename)) {
            gson.toJson(_database, pw);
            System.out.println("Database saved");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public CYFData loadDatabase(String filepath) {
        try {
            Gson gson = new Gson();
            FileReader fr = new FileReader(filepath);
            return gson.fromJson(fr, new CYFData() {
            }.getClass().getGenericSuperclass());
        } catch (FileNotFoundException e) {
            System.out.println("Database file doesnt exist. Create new one ");
            return new CYFData();
        }

    }

    synchronized void createAndStartClientService(Socket clientSocket) throws IOException {
        CYFService clientService = new CYFService(clientSocket, this);
        clientService.init();
        new Thread(clientService).start();
        _clients.add(clientService);
        System.out.println("Client added. Number of clients: " + _clients.size());
    }

    synchronized void removeClientService(CYFService clientService) {
        _clients.remove(clientService);
        clientService.close();
        System.out.println("Client removed. Number of clients: " + _clients.size());
    }

    synchronized void send(CYFProtocol proto) {
        for (CYFService s : _clients) { // roześlij do wszystkich klientów
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
        return Integer.parseInt(_props.getProperty("width"));
    }

    int boardHeight() {
        return Integer.parseInt(_props.getProperty("height"));
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
        CYFServer server = new CYFServer(props, "Internet Board Server");
        server.run();
        server.cleanup();
    }
}