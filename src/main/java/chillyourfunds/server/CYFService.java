package chillyourfunds.server;

import chillyourfunds.logic.*;

import java.io.*;
import java.net.Socket;

/**
 * Klasa obsługująca pojedynczego użytkownika od strony servera.
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
     * Konstruktor klasy CYFService.
     * @param clientSocket Obiekt klasy Socket, który obsługuje połączenie z użykownikiem.
     * @param server Okiekt klasy CYFServer, który odpowiada za działanie servera.
     */
    public CYFService(Socket clientSocket, CYFServer server) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    /**
     * Inicjalizacja obiektów odpowiedzialnych za przesyłanie pomiędzy użytkownikiem a serwerem.
     * @throws IOException
     */
    void init() throws IOException {
        objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        objectIn = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Zamknięcie kanałów przesyłu danych.
     */
    void close() {
        try {
            objectIn.close();
            objectOut.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Blad podczas zamykania polaczenia z kilentem (" + id + "), " + e);
        } finally {
            clientSocket = null;
        }
    }

    /**
     * Ta metoda wykonuje główne zadanie klasy CYFService, mianowicie odbiera od uzytkownika żądania i realizuje odpowiednie działania niezbędne do działania
     * usługi Chillyourfunds - dzielenia wspólnych wydatków.
     */
    public void run() {
        while (true) {
            Messenger message = receive();
            CYFProtocol command = message.command;
            switch (command) {
                case LOGIN:
                    String[] credentials = (String[]) message.data;
                    _userAccount = server._database.getUserByCredentials(credentials[0], credentials[1]);
                    if (_userAccount == null) {
                        send(CYFProtocol.LOGINDENY);
                    } else {
                        send(CYFProtocol.LOGGEDIN, _userAccount.memberOfGroups);
                    }
                    break;
                case REGISTER:
                    String[] newcredentials = (String[]) message.data;
                    if (server._database.addUser(newcredentials[0], newcredentials[1], newcredentials[2], newcredentials[3])) {
                        send(CYFProtocol.REGISTERED);
                    } else {
                        send(CYFProtocol.COMMENT, "Uzytkownik o podanej nazwie juz istnieje. Wybierz inna nazwe.");
                    }
                    break;
                case CREATEGROUP:
                    _currGroup = server._database.addGroup((String) message.data, _userAccount);
                    if (_currGroup != null) {
                        System.out.println(_currGroup.getPeople());
                        send(CYFProtocol.GROUPCREATED, _currGroup);
                    } else {
                        send(CYFProtocol.COMMENT, "Wystapil blad podczas tworzenia grupy!");
                    }
                    break;
                case CHOOSEGROUP:
                    _currGroup = server._database.getGroup((Integer) message.data);
                    if (_currGroup != null) {
                        send(CYFProtocol.GROUPCHOOSED, _currGroup);
                    } else {
                        send(CYFProtocol.COMMENT, "Grupa o takiej nazwie nie istnieje!");
                    }
                    break;
                case ADDEXPENSE:
                    Object[] params = (Object[]) message.data;
                    String[] debtors = (String[]) params[2];
                    Expense newExpense;
                    if (params[0].equals(CYFProtocol.EQUALSPLIT)) {
                        newExpense =  new EqualExpense((int) params[1], _currGroup, _userAccount.memberOfGroups);
                        for(String debtor: debtors){
                            newExpense.addDebtor(server._database.getPersonByUsername(debtor).getId());
                        }
                        newExpense.createExpense(newExpense);
                    } else if (params[0].equals(CYFProtocol.PERCENTSPLIT)) {

                    } else if (params[0].equals(CYFProtocol.EXACTSPLIT)) {

                    } else {
                        send(CYFProtocol.COMMENT, "Zly typ wydatku!");
                    }
                    send(CYFProtocol.EXPENSEADDED,new Object[]{_currGroup, _userAccount.memberOfGroups});
                    break;
                case ADDPERSON:
                    String usernameToAdd = (String) message.data;
                    Person personToAdd = server._database.getPersonByUsername(usernameToAdd);
                    if (personToAdd != null) {
                        _currGroup.addPerson(personToAdd);
                        Group newGroup = _currGroup;
                        send(CYFProtocol.PERSONADDED, newGroup);
                    } else {
                        send(CYFProtocol.COMMENT, "Uzytkownik o podanej nazwie nie istnieje!");
                    }
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
                        send(CYFProtocol.COMMENT, "Uzytkownik o podanej nazwie nie istnieje!");
                    }
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
                    send(CYFProtocol.LOGGEDOUT);
                case STOPPED:
                    server.removeClientService(this);
                case NULLCOMMAND:
                    return;
                default:
                    System.out.println("Blad");
            }
            try {
                refreshConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void refreshConnection() throws IOException {
        objectOut.reset();
    }


    void send(CYFProtocol command, Object data) {
        try {
            if(objectOut!=null){
                objectOut.writeUnshared(new Messenger(command, data));
                objectOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Nie mozna wyslac danych do klienta!");
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
            System.err.println("Nie mozna wyslac danych do klienta!");
        }
    }

    private Messenger receive() {
        try {
            return (Messenger) objectIn.readUnshared();
        } catch (IOException | ClassNotFoundException ioe) {
            System.err.println("Blad podczas odbierania danych od klienta (" + id + "), " + ioe);
            return new Messenger(CYFProtocol.NULLCOMMAND);
        }
    }


}