package chillyourfunds.server;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

// To-Do
//  Pozmieniać wszystko żeby działało xd to jest tak naprawdę obsługa pakietów, więc ważniejszy jest CYFService

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
            send(CYFProtocol.STOP.name());
            new Thread(() -> {
                while (clients.size() != 0) {
                    try {
                        TimeUnit.of(ChronoUnit.MILLIS).sleep(500);
                    } catch (InterruptedException ignore) {
                        break;
                    }
                }
                System.exit(0);
            }).start();
        });
        add(b);
        pack();
        (serverThread = new Thread(this)).start();
        EventQueue.invokeLater(() -> setVisible(true));
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
        database.saveJSON("database.json");
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

    synchronized void send(String msg) {
        for (CYFService s : clients) { // roześlij do wszystkich klientów
            s.send(msg);
        }
    }

    synchronized void send(String msg, CYFService skip) {
        for (CYFService s : clients) { // roześlij do wszystkich klientów
            if (s != skip) { // oprócz jednego, którego trzeba pominąć...
                s.send(msg);
            }
        }
    }


    private int $lastID = -1;

    synchronized int nextID() {
        return ++$lastID;
    }

    int currentColor() {
        return $lastID % CYFProtocol.colors.length;
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