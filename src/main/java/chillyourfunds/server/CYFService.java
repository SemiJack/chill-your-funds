package chillyourfunds.server;

import chillyourfunds.logic.*;

import java.io.*;
import java.net.Socket;

/**
 * author: Jacek Pelczar
 */
public class CYFService implements Runnable {

    private int id;
    private final CYFServer server;
    private Socket clientSocket;
    private UserAccount userAccount;

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
                    userAccount = server.database.getUserByCredentials(credentials[0], credentials[1]);
                    if (userAccount == null) {
                        send(CYFProtocol.COMMENT, "Wrong login or password!");
                    } else send(CYFProtocol.LOGGEDIN, userAccount.memberOfGroups);
                    break;
                case REGISTER:
                    String[] newcredentials = (String[]) message.data;
                    if (server.database.addUser(newcredentials[0], newcredentials[1], newcredentials[2], newcredentials[3])) {
                        send(CYFProtocol.REGISTERED);
                    } else send(CYFProtocol.COMMENT, "User with this username already exists. Choose other username.");
                    break;
                case CREATEGROUP:
                    if (server.database.addGroup((String) message.data, userAccount)) {

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
                    Object[] params = (Object[]) message.data;
                    String[] debtors =(String[]) params[2];
                    Expense newExpense = new EqualExpense((int) params[1],currGroup,userAccount.memberOfGroups);
                    for (String debtorUsername: debtors){
                        newExpense.addDebtor(server.database.getPersonByUsername(debtorUsername).getId());
                    }
                    if(params[0].equals(CYFProtocol.EQUALSPLIT)){

                    }else if(params[0].equals(CYFProtocol.PERCENTSPLIT)){

                    }else if(params[0].equals(CYFProtocol.EXACTSPLIT)){

                    }else send(CYFProtocol.COMMENT, "Bad type of expense!");
                    send(CYFProtocol.COMMENT, "Created expense");
                    break;
                case ADDPERSON:
                    String usernameToAdd = (String) message.data;
                    Person personToAdd = server.database.getPersonByUsername(usernameToAdd);
                    if(personToAdd!=null){
                        currGroup.addPerson(personToAdd);
                        send(CYFProtocol.PERSONADDED);
                    }else send(CYFProtocol.COMMENT, "User with this username doesn't exist!");
                    break;
                case REMOVEPERSON:
                    break;
                case SIMPlify:
                    break;
                case UPDATE:
                    update();
                    break;
                case LOGOUT:
                    send(CYFProtocol.LOGGEDOUT); // no break!
                case STOPPED:
                    server.removeClientService(this); // no break!
                case NULLCOMMAND:
                    return;
                default:
                    System.out.println("Error");
            }
            update();
        }
    }

    void update(){
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(CYFProtocol.UPDATE, new Object[]{currGroup,userAccount.memberOfGroups}));
        } catch (NullPointerException npe){
            System.out.println("Not yet logged in");
        } catch (IOException e) {
            System.out.println("Cannot send data to client!");
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