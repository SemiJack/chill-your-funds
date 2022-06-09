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
 * author: Jacek Pelczar
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
            JOptionPane.showMessageDialog(null, "Unknown host.");
            throw new Exception("Unknown host.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO exception while connecting to the server.");
            throw new Exception("IO exception while connecting to the server.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Port value must be a number.");
            throw new Exception("Port value must be a number.");
        }
        try {
            _objectOut = new ObjectOutputStream(_socket.getOutputStream());
            _objectIn = new ObjectInputStream(_socket.getInputStream());
        } catch (IOException ex) {
            throw new Exception("Can't get input/output connection stream.");
        }
        new Thread(this).start();
    }

    boolean isDisconnected() {
        return _socket == null;
    }

    @Override
    public void run() {
        while (true) {
//            try {
////                if (!handleCommand()) {
////                    _objectOut.close();
////                    _objectIn.close();
////                    _socket.close();
////                    break;
////                }
//
//            } catch (IOException | ClassNotFoundException ignore) {
//            }
        }
    }

    private boolean handleCommand() throws IOException, ClassNotFoundException {

        Object data = _objectIn.readUnshared();
        Messenger message = (Messenger) data;
        CYFProtocol command = message.command;
        switch (command) {
            case LOGGEDIN:
                _me = (Person) message.data;
                System.out.println("Log in successful");
                break;
            case REGISTERED:
                System.out.println("Register successful");
                break;
            case GROUPCHOOSED:
                _currGroup = (Group) message.data;
                System.out.println("Chosen group: " + _currGroup.getGroupName());
                break;
            case GROUPCREATED:
                _currGroup = (Group) message.data;
                System.out.println("Created new group");
                break;
            case PERSONADDED:
                _currGroup = (Group) message.data;
                System.out.println("Added new person to group");
                break;
            case PERSON:
                _me = (Person) message.data;
                System.out.println("Updated person");
                break;
            case COMMENT:
                System.out.println((String) message.data);
                break;
            case SNAPSHOT:
                System.out.println(message.data);
                Object[] dataarr = (Object[]) message.data;

                _currGroup = (Group) dataarr[0];
                System.out.println(_currGroup);
                _me = (Person) dataarr[1];
                System.out.println("Up to date");
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
        System.out.println("send login req");
    }

    void register(String login, String password, String firstname, String lastname) {
        send(CYFProtocol.REGISTER, new String[]{login, password, firstname, lastname});
        System.out.println("send register req");
    }

    void createGroup(String groupName) {
        send(CYFProtocol.CREATEGROUP, groupName);
        System.out.println("send create group req");
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
        //_objectOut = null;
        //_objectIn = null;
        //_objectOut = new ObjectOutputStream(_socket.getOutputStream());
        //_objectIn = new ObjectInputStream(_socket.getInputStream());
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
            System.err.println("Cannot send data to server!");
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
//            if (_objectOut != null) {
//                _objectOut.writeObject(new Messenger(CYFProtocol.UPDATE));
//                _objectOut.flush();
//                handleCommand();
//            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to server!");
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
