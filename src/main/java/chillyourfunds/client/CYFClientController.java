package chillyourfunds.client;

import chillyourfunds.logic.*;
import chillyourfunds.server.CYFProtocol;
import chillyourfunds.server.Messenger;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Klasa odpowiedzialna za komunikację z serwerem.
 * @author Jacek Pelczar
 */
public class CYFClientController implements Runnable {
    private final Socket _socket;
    public Person _me;
    public Group _currGroup;
    private  ObjectInputStream _objectIn;
    private  ObjectOutputStream _objectOut;

    public CYFClientController(String host, String port) throws Exception {
        try {
            _socket = new Socket(host, Integer.parseInt(port));
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Nieznany host.");
            throw new Exception("Nieznany host.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO exception podczas łącznia z serwerem.");
            throw new Exception("IO exception podczas łącznia z serwerem.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Wartosc portu musi byc numerem!");
            throw new Exception("Wartosc portu musi byc numerem!");
        }
        try {
            _objectOut = new ObjectOutputStream(_socket.getOutputStream());
            _objectIn = new ObjectInputStream(_socket.getInputStream());
        } catch (IOException ex) {
            throw new Exception("Nie mozna uzyskac strumienia wejscia/wyjscia ");
        }
        new Thread(this).start();
    }

    boolean isDisconnected() {
        return _socket == null;
    }

    @Override
    public void run() {
        while (true) {
        }
    }

    private boolean handleCommand() throws IOException, ClassNotFoundException {

        Object data = _objectIn.readUnshared();
        Messenger message = (Messenger) data;
        CYFProtocol command = message.command;
        switch (command) {
            case LOGGEDIN:
                _me = (Person) message.data;
                System.out.println("Logowanie pomyslne");
                break;
            case REGISTERED:
                System.out.println("Rejestracja pomyslna");
                break;
            case GROUPCHOOSED:
                _currGroup = (Group) message.data;
                System.out.println("Wybrana grupa: " + _currGroup.getGroupName());
                break;
            case GROUPCREATED:
                _currGroup = (Group) message.data;
                System.out.println("Stworzono nowa grupe");
                break;
            case PERSONADDED:
                _currGroup = (Group) message.data;
                System.out.println("Dodano nowa osobe do grupy");
                break;
            case EXPENSEADDED:
                Object[] dataarr = (Object[]) message.data;
                _currGroup = (Group) dataarr[0];
                _me = (Person) dataarr[1];
                System.out.println("Dodano nowy wydatek");
            case PERSON:
                _me = (Person) message.data;
                System.out.println("Zaktualizowano uzytkownika");
                break;
            case COMMENT:
                System.out.println((String) message.data);
                break;
            case SNAPSHOT:
                System.out.println(message.data);
                Object[] dataarr2 = (Object[]) message.data;
                _currGroup = (Group) dataarr2[0];
                System.out.println(_currGroup);
                _me = (Person) dataarr2[1];
                System.out.println("Synchronizacja zakonczona");
                break;
            case STOP:
                send(CYFProtocol.STOPPED);
            case LOGGEDOUT:
                return false;
        }
        refreshConnection();
        return true;
    }

    ArrayList<Integer> getGroups() {
        return _me.getParticipateGroupsId();
    }

    void login(String username, String password) {
        send(CYFProtocol.LOGIN, new String[]{username, password});
    }

    void register(String login, String password, String firstname, String lastname) {
        send(CYFProtocol.REGISTER, new String[]{login, password, firstname, lastname});
    }

    void createGroup(String groupName) {
        send(CYFProtocol.CREATEGROUP, groupName);
    }
    void getPerson(String username) {
        send(CYFProtocol.PERSON, username);
    }

    void chooseGroup(int groupId) {
        send(CYFProtocol.CHOOSEGROUP, groupId);
    }

    void addPersonToGroup(String username) {
        send(CYFProtocol.ADDPERSON, username);
    }

    void removePersonFromGroup(String username) {
        send(CYFProtocol.REMOVEPERSON, username);
    }

    void addExpense(CYFProtocol expenseType, int amount, String[] debtors) {
        send(CYFProtocol.ADDEXPENSE, new Object[]{expenseType, amount, debtors});
    }

    void refreshConnection() throws IOException {
        _objectOut.reset();
    }


    void send(CYFProtocol command, Object data) {
        try {
            if (_objectOut != null) {
                _objectOut.writeUnshared(new Messenger(command, data));
                _objectOut.flush();
                handleCommand();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Nie mozna wyslac danych do serwera!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void send(CYFProtocol command) {
        try {
            if (_objectOut != null) {
                _objectOut.writeUnshared(new Messenger(command));
                _objectOut.flush();
                handleCommand();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Nie mozna wyslac danych do serwera!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    void forceLogout() {
        if (_socket != null) {
            send(CYFProtocol.LOGOUT);
        }
    }
}
