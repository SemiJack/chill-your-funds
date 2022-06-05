package chillyourfunds.server;

import chillyourfunds.logic.*;

import java.io.*;
import java.net.Socket;

public class CYFService implements Runnable {

    private int id;
    private final CYFServer server;
    private Socket clientSocket;
    private User user;

    private Group currGroup;
    private BufferedReader messageIn;
    private PrintWriter messageOut;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;


    public CYFService(Socket clientSocket, CYFServer server) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    void init() throws IOException {
        objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        objectIn = new ObjectInputStream(clientSocket.getInputStream());
    }

    void close() {
        try {
            objectIn.close();
            objectOut.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client (" + id + "), " + e);
        } finally {
            messageOut = null;
            messageIn = null;
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
            Messenger message = receive();
            CYFProtocol command = message.command;
            switch (command) {
                case LOGIN:
                    String[] credentials = (String[]) message.data;
                    user = server.database.getUserByCredentials(credentials[0], credentials[1]);
                    if (user == null) {
                        send(CYFProtocol.COMMENT, "Wrong login or password!");
                    } else send(CYFProtocol.LOGGEDIN, user);
                    break;
                case REGISTER:
                    String[] newcredentials = (String[]) message.data;
                    if (server.database.addUser(newcredentials[0], newcredentials[1], newcredentials[2], newcredentials[3])) {
                        send(CYFProtocol.REGISTERED);
                    } else send(CYFProtocol.COMMENT, "User with this username already exists. Choose other username.");
                    break;
                case CREATEGROUP:
                    if (server.database.addGroup((String) message.data, user)) {
                        send(CYFProtocol.GROUPCREATED);
                    } else send(CYFProtocol.COMMENT, "Error while creating group!");

                    break;
                case CHOOSEGROUP:
                    currGroup = server.database.getGroup((Integer) message.data);
                    if (currGroup != null) {
                        send(CYFProtocol.GROUPCHOOSED, currGroup);
                    } else send(CYFProtocol.COMMENT, "This group doesn't exist!");
                    break;
                case ADDEXPENSE:

                    break;
                case ADDPERSON:
                    break;
                case REMOVEPERSON:
                    break;
                case SIMPlify:
                    break;

                case LOGOUT:
                    send(CYFProtocol.LOGGEDOUT); // no break!
                case STOPPED:
                    server.removeClientService(this); // no break!
                case NULLCOMMAND:
                    return;
                default:
                    System.out.println("Error");
//                case SINGIN:
//                    String newLogin = st.nextToken();
//                    String newPassword = st.nextToken();
//                    server.database.addUser( newLogin, newPassword, st.nextToken(),st.nextToken());
//                    user = server.database.getUserByCredentials(newLogin,newPassword);
//                    send(CYFProtocol.SINGEDIN+" ");
//                    send(CYFProtocol.LOGGEDIN + " " + (id = server.nextID()) + " " + server.boardWidth() + " " + server.boardHeight());
//                    break;
//                case CREATEGROUP:
//                    String groupName = st.nextToken();
//                    server.database.addGroup(groupName,user);
//                    send(CYFProtocol.COMMENT + " " + "Grupa_pomyślnie_utworzona");
//                    break;
//                case CHOOSEGROUP:
//                    currGroup = server.database.getGroup(Integer.parseInt(st.nextToken()));
//                    send(CYFProtocol.COMMENT + " " + "Grupa_pomyślnie_wybrana");
//                    break;
////                case HISTORY:
////                    server.send(CYFProtocol.HISTORY + );
////                    //server.send(CYFProtocol.CYF + " " + /*jakiś stream z danymi*/, this);
////                    break;
//                case ADDEXPENSE:
//                    try {
//                    Expense expense;
//                    String expenseType = st.nextToken();
//                    Integer amount = Integer.parseInt(st.nextToken());
//                    switch (expenseType) {
//                        case "percent":
//                            expense = new PercentExpense(amount, currGroup, currGroup.getPersonById(user.getUUID()));
//                            break;
//                        case "exact":
//                            expense = new ExactExpense(amount, currGroup, currGroup.getPersonById(user.getUUID()));
//                            break;
//                        default:
//                            expense = new EqualExpense(amount, currGroup, currGroup.getPersonById(user.getUUID()));
//                            break;
//                    }
//                    ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//                    Integer[] debtors = (Integer[]) objectInputStream.readObject();
//                    for(Integer pId : debtors){
//                       expense.addDebtor(pId);
//                    }
//                    expense.createExpense(expense);
//
//                    } catch (IOException | ClassNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                    // wybieramy rodzaj
//                    // tworzymy obiekt
//                    // dodajemy osoby
//                    // createExpense -> zapisuje w Grupie
//                    break;

            }
        }
    }


    void send(CYFProtocol command, Object data) {
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(command, data));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to client!");
        }
    }

    void send(CYFProtocol command) {
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(command));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to client!");
        }
    }

    void broadcast(CYFProtocol command) {
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(command));
        } catch (IOException e) {
        }
    }

    private Messenger receive() {
        try {
            return (Messenger) objectIn.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            System.err.println("Error reading client (" + id + "), " + ioe);
            return new Messenger(CYFProtocol.NULLCOMMAND);
        }
    }

}