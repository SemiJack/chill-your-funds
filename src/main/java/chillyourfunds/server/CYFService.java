package chillyourfunds.server;

import chillyourfunds.logic.*;

import java.io.*;
import java.net.Socket;

/**
 * @author Jacek Pelczar
 */
public class CYFService implements Runnable {

    private int id;
    private final CYFServer server;
    private Socket clientSocket;
    private UserAccount _userAccount;

    private Group _currGroup;

    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    /**
     * Konstruktor klasy CYFService
     * @param clientSocket
     * @param server
     */
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
            clientSocket = null;
        }
    }

    public void run() {
        while (true) {
            Messenger message = receive();
            CYFProtocol command = message.command;
            switch (command) {
                case LOGIN:
                    String[] credentials = (String[]) message.data;
                    _userAccount = server._database.getUserByCredentials(credentials[0], credentials[1]);
                    if (_userAccount == null) {
                        //send(CYFProtocol.COMMENT, "Wrong login or password!");
                        send(CYFProtocol.LOGINDENY);
                    } else {
                        send(CYFProtocol.LOGGEDIN, _userAccount.memberOfGroups);
                        //sendSnapshot();
                    }
                    break;
                case REGISTER:
                    String[] newcredentials = (String[]) message.data;
                    if (server._database.addUser(newcredentials[0], newcredentials[1], newcredentials[2], newcredentials[3])) {
                        send(CYFProtocol.REGISTERED);
                    } else {
                        send(CYFProtocol.COMMENT, "User with this username already exists. Choose other username.");
                    }
                    break;
                case CREATEGROUP:
                    _currGroup = server._database.addGroup((String) message.data, _userAccount);
                    if (_currGroup != null) {
                        System.out.println(_currGroup.getPeople());
                        send(CYFProtocol.GROUPCREATED, _currGroup);
                    } else {
                        send(CYFProtocol.COMMENT, "Error while creating group!");
                    }
                    break;
                case CHOOSEGROUP:
                    _currGroup = server._database.getGroup((Integer) message.data);
                    if (_currGroup != null) {
                        send(CYFProtocol.GROUPCHOOSED, _currGroup);
                    } else {
                        send(CYFProtocol.COMMENT, "This group doesn't exist!");
                    }
                    //sendSnapshot();
                    break;
                case ADDEXPENSE:     // TODO
                    Object[] params = (Object[]) message.data;
                    String[] debtors = (String[]) params[2];
                    Expense newExpense = new EqualExpense((int) params[1], _currGroup, _userAccount.memberOfGroups);
                    for (String debtorUsername : debtors) {
                        newExpense.addDebtor(server._database.getPersonByUsername(debtorUsername).getId());
                    }
                    if (params[0].equals(CYFProtocol.EQUALSPLIT)) {

                    } else if (params[0].equals(CYFProtocol.PERCENTSPLIT)) {

                    } else if (params[0].equals(CYFProtocol.EXACTSPLIT)) {

                    } else {
                        //send(CYFProtocol.COMMENT, "Bad type of expense!");
                    }
                    //send(CYFProtocol.COMMENT, "Created expense");
                    //sendSnapshot();
                    break;
                case ADDPERSON:
                    String usernameToAdd = (String) message.data;
                    System.out.println("Adding Username: " + usernameToAdd);
                    Person personToAdd = server._database.getPersonByUsername(usernameToAdd);
                    if (personToAdd != null) {
                        System.out.println("People In Group Before: " + _currGroup.getPeople());
                        System.out.println("Username/Person Exists, Adding Person To Current Group");
                        _currGroup.addPerson(personToAdd);
                        System.out.println("Added Person To Group");
                        System.out.println("People In Group After: " + _currGroup.getPeople());
                        Group newGroup = _currGroup;
                        send(CYFProtocol.PERSONADDED, newGroup);
                    } else {
                        System.out.println("Username/Person Does Not Exist");
                        send(CYFProtocol.COMMENT, "User with this username doesn't exist!");
                    }
                    //sendSnapshot();
                    break;
                case PERSON:
                    send(CYFProtocol.PERSON, _userAccount.memberOfGroups);
                    break;
                case REMOVEPERSON:
                    String usernameToRemove = (String) message.data;
                    Person personToRemove = server._database.getPersonByUsername(usernameToRemove);
                    if (personToRemove != null && _currGroup.removePerson(personToRemove)) {
                        send(CYFProtocol.PERSONREMOVED);
                    } else {
                        send(CYFProtocol.COMMENT, "User with this username doesn't exist!");
                    }
                    //sendSnapshot();
                    break;
                case SIMPlify:
                    break;
                case UPDATE:
                    if (_userAccount != null) {
                        send(CYFProtocol.SNAPSHOT, new Object[]{_currGroup, _userAccount.memberOfGroups});
                    } else {
                        send(CYFProtocol.SNAPSHOT,new Object[]{ null, null});
                    }
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
            try {
                refreshConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void sendSnapshot() {
        if (_userAccount != null) {
            send(CYFProtocol.SNAPSHOT, new Object[]{_currGroup, _userAccount.memberOfGroups});
        } else {
            System.out.println("sdas");
        }
    }

    void refreshConnection() throws IOException {
        objectOut.reset();
        //objectOut = null;
        //objectIn = null;
        //objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        //objectIn = new ObjectInputStream(clientSocket.getInputStream());
    }


    void send(CYFProtocol command, Object data) {
        try {
            if(objectOut!=null){
                objectOut.writeUnshared(new Messenger(command, data));
                objectOut.flush();
            }
            //objectOut.writeObject(new Messenger(command, data));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to client!");
        }
    }

    void send(CYFProtocol command) {
        try {
            if (objectOut != null) {
                objectOut.writeUnshared(new Messenger(command));
                objectOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to client!");
        }
    }

    private Messenger receive() {
        try {
            return (Messenger) objectIn.readUnshared();
        } catch (IOException | ClassNotFoundException ioe) {
            System.err.println("Error reading client (" + id + "), " + ioe);
            return new Messenger(CYFProtocol.NULLCOMMAND);
        }
    }


}