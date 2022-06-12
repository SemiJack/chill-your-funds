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

/**
 * Ta klasa realizuje zadanie serwera. Jest odpowiedzialna za akceptację nawiązania połączenia z użytkownikami i utrzymywanie go.
 * Może obsługiwać wiele użytkowników naraz.
 */
public class CYFServer extends Frame implements Runnable {
    private ServerSocket _serverSocket;

    protected CYFData _database;

    private  final List<CYFService> _clients = Collections.synchronizedList(new ArrayList<>());

    private final Properties _props;


    private boolean _isRunning = true;

    /**
     * Konstruktor klasy CYFServer.
     * @param p Obiekt klasy properties z konfiguracją servera.
     * @param title String będący nazwą wyświetlanego okna aplikacji serwera.
     */
    public CYFServer(Properties p, String title) {
        super(title);
        _props = p;
        int port = Integer.parseInt(_props.getProperty("port"));
        try {
            _serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Blad podczas uruchamiania CYFServer.");
            return;
        }
        Button b = new Button("zatrzymaj i wyjdź");
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
                    System.err.println("Wystapil blad podczas akceptowania polaczenia. Klient nie moze byc obsluzony...");
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

    /**
     * Metoda zapisująca stan obiektu klasy CYFDatabase, czyli "bazy danych" w której zapisywane są dane niezbędne do działania serwera.
     * @param filepath Ścieżka do pliku ".json", w którym ma znaleźć się zapisany stan "bazy danych".
     */
    public void saveDatabase(String filepath) {

        GsonBuilder gbuilder = new GsonBuilder();
        gbuilder.setPrettyPrinting();
        gbuilder.disableHtmlEscaping(); // wyłączenie automatycznej zamiany znaków specjalnych
        Gson gson = gbuilder.create();
        try (FileWriter pw = new FileWriter(filepath)) {
            gson.toJson(_database, pw);
            System.out.println("Baza danych zapisana");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Metoda wczytuje stan obiektu klasy CYFDatabase, czyli "bazy danych" w której zapisywane są dane niezbędne do działania serwera.
     * @param filepath Ścieżka do pliku ".json", w którym znajduje się zapisany stan "bazy danych".
     */
    public CYFData loadDatabase(String filepath) {
        try {
            Gson gson = new Gson();
            FileReader fr = new FileReader(filepath);
            return gson.fromJson(fr, new CYFData() {
            }.getClass().getGenericSuperclass());
        } catch (FileNotFoundException e) {
            System.out.println("Plik z baza danych nie istnieje. Utworzono nowy...");
            return new CYFData();
        }

    }

    /**
     * Ta metoda tworzy obiekt klasy CYFService, który obsługuje żądania jednego użytkownika.
     * @param clientSocket Obiekt klasy Socket, który ma połączenie z użytkownikiem.
     * @throws IOException
     */
    synchronized void createAndStartClientService(Socket clientSocket) throws IOException {
        CYFService clientService = new CYFService(clientSocket, this);
        clientService.init();
        new Thread(clientService).start();
        _clients.add(clientService);
        System.out.println("Klient dodany. Aktualna ilosc klientow: " + _clients.size());
    }

    /**
     * Ta metoda zamyka połączenie z użytkownikiem.
     * @param clientService Obiekt klasy Socket, który ma połączenie z użytkownikiem.
     */
    synchronized void removeClientService(CYFService clientService) {
        _clients.remove(clientService);
        clientService.close();
        System.out.println("Klient usuniety. Pozostalo : " + _clients.size());
    }

    /**
     * Wysyła do wszystkich połączonych użytkowników odpowiednią wiadomość.
     * @param proto Wiadomość zgodna z protokołem CYFProtocol, która ma zostać wysłana.
     */
    synchronized void send(CYFProtocol proto) {
        for (CYFService s : _clients) { // roześlij do wszystkich klientów
            s.send(proto);
        }
    }

    private int $lastID = -1;

    synchronized int nextID() {
        return ++$lastID;
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
        CYFServer server = new CYFServer(props, "Server");
        server.run();
        server.cleanup();
    }
}