package chillyourfunds.server;

import chillyourfunds.logic.*;
import com.sun.javafx.font.coretext.CTFactory;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class CYFService implements Runnable {

    private int id;

    private int color;
    private final CYFServer server;
    private Socket clientSocket;

    private User user;

    private Group currGroup;

    private BufferedReader input;
    private PrintWriter output;

    private CYFData database;

    public CYFService(Socket clientSocket, CYFServer server) {
        this.server = server;
        this.clientSocket = clientSocket;
        //this.database =
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
    //user odbiera:
    // historię rozliczeń dla danej osoby
    // kto ma jaki balance
    // kto mu ile wisi
    // kto komu ile wisi??

    public void run() {
        while (true) {
            String protocolSentence = receive();
            StringTokenizer st = new StringTokenizer(protocolSentence);
            CYFProtocol command = CYFProtocol.valueOf(st.nextToken()); // w srodku bylo jeszcze .Draw.
            switch (command) {
                case LOGIN:
                    send(CYFProtocol.LOGGEDIN + " " + (id = server.nextID()) + " "
                            + (color = server.currentColor()) + " "
                            + server.boardWidth() + " " + server.boardHeight());
                    break;
                case CHOOSEGROUP:
                    currGroup = server.database.database.get(Integer.parseInt(st.nextToken()));
                    break;
                case HISTORY:
                    //server.send(CYFProtocol.CYF + " " + /*jakiś stream z danymi*/, this);
                    break;
                case ADDEXPENSE:
                    Expense expense;
                    String expenseType = st.nextToken();
                    Integer amount = Integer.parseInt(st.nextToken());
                    switch (expenseType){
                        case "percent":
                            expense = new PercentExpense(amount,currGroup,currGroup.getPersonById(user.getUUID()));
                            break;
                        case "exact":
                            expense = new ExactExpense(amount,currGroup,currGroup.getPersonById(user.getUUID()));
                            break;
                        default:
                            expense = new EqualExpense(amount,currGroup,currGroup.getPersonById(user.getUUID()));
                            break;
                    }
                    expense.createExpense(expense);
                    // wybieramy rodzaj
                    // tworzymy obiekt
                    // dodajemy osoby
                    // createExpense -> zapisuje w Grupie
                    break;


//                case MOUSERELEASED:
//                    int currentMouseX = Integer.parseInt(st.nextToken());
//                    int currentMouseY = Integer.parseInt(st.nextToken());
//                    server.send(CYFProtocol.DRAW + " " + color + " " + lastMouseX + " " + lastMouseY
//                            + " " + currentMouseX + " " + currentMouseY, this);
//                    lastMouseX = currentMouseX;
//                    lastMouseY = currentMouseY;
//                    break;
                case LOGOUT:
                    send(CYFProtocol.LOGGEDOUT.name()); // no break!
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
            return CYFProtocol.NULLCOMMAND.name();
        }
    }
}