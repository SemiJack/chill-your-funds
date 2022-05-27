package chillyourfunds.server;

import com.sun.javafx.font.coretext.CTFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class CYFService implements Runnable {

    private int id;

    private final CYFServer server;
    private Socket clientSocket;

    private BufferedReader input;
    private PrintWriter output;

    public CYFService(Socket clientSocket, CYFServer server) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    void init() throws IOException {
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    void close() {
        try {
            input.close();
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client (" + id + "), " + e);
        } finally {
            output = null;
            input = null;
            clientSocket = null;
        }
    }
    // To-Do
    // określić jakie dane będzie przesyłał server
    // określić kiedy je będzie przesyłał
    // określić syntax protokołu
    //

    public void run() {
        while (true) {
            String protocolSentence = receive();
            StringTokenizer st = new StringTokenizer(protocolSentence);
            CYFProtocol command = CYFProtocol.DRAW.valueOf(st.nextToken());
            switch (command) {
                case LOGIN:
                    send(IBProtocol.LOGGEDIN + " " + (id = server.nextID()) + " "
                            + (color = server.currentColor()) + " "
                            + server.boardWidth() + " " + server.boardHeight());
                    break;
                case MOUSEPRESSED:
                    lastMouseX = Integer.parseInt(st.nextToken());
                    lastMouseY = Integer.parseInt(st.nextToken());
                    break;
                case MOUSEDRAGGED:
                case MOUSERELEASED:
                    int currentMouseX = Integer.parseInt(st.nextToken());
                    int currentMouseY = Integer.parseInt(st.nextToken());
                    server.send(IBProtocol.DRAW + " " + color + " " + lastMouseX + " " + lastMouseY
                            + " " + currentMouseX + " " + currentMouseY, this);
                    lastMouseX = currentMouseX;
                    lastMouseY = currentMouseY;
                    break;
                case LOGOUT:
                    send(IBProtocol.LOGGEDOUT.name()); // no break!
                case STOPPED:
                    server.removeClientService(this); // no break!
                case NULLCOMMAND:
                    return;
            }
        }
    }

    void send(String command) {
        output.println(command);
    }

    private String receive() {
        try {
            return input.readLine();
        } catch (IOException ioe) {
            System.err.println("Error reading client (" + id + "), " + ioe);
            return IBProtocol.NULLCOMMAND.name();
        }
    }
}