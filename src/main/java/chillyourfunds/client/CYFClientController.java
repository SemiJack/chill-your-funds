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
    private final Socket socket;
    public Person me;
    public Group currGroup;
    private final ObjectInputStream objectIn;
    private final ObjectOutputStream objectOut;


    public CYFClientController(String host, String port) throws Exception {
        try {
            socket = new Socket(host, Integer.parseInt(port));
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
            objectIn = new ObjectInputStream(socket.getInputStream());
            objectOut = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            throw new Exception("Can't get input/output connection stream.");
        }
        new Thread(this).start();
    }

    boolean isDisconnected() {
        return socket == null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!handleCommand()) {
                    objectOut.close();
                    objectIn.close();
                    socket.close();
                    break;
                }

            } catch (IOException | ClassNotFoundException ignore) {
            }
        }
    }

    private boolean handleCommand() throws IOException, ClassNotFoundException {
        Messenger message = (Messenger) objectIn.readObject();
        CYFProtocol command = message.command;
        switch (command) {
            case LOGGEDIN:
                me = (Person) message.data;
                System.out.println("Log in successful");
                break;
            case REGISTERED:
                System.out.println("Register successful");
                break;
            case GROUPCHOOSED:
                currGroup = (Group) message.data;
                System.out.println("Chosen group: " + currGroup.getGroupName());
                break;
            case GROUPCREATED:
                System.out.println("Created new group");
                break;
            case PERSONADDED:
                System.out.println("Added new person to group");
                break;
            case COMMENT:
                System.out.println((String) message.data);
                break;
            case UPDATEDONE:
                Object[] dataarr = (Object[]) message.data;
                currGroup = (Group) dataarr[0];
                me = (Person) dataarr[1];
                System.out.println("Up to date");
                break;
            case STOP:
                send(CYFProtocol.STOPPED);
            case LOGGEDOUT:
                return false;
        }
        return true;
    }

    ArrayList<Integer> getGroups() {
        return me.getParticipateGroupsId();
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

   void update() {
        if (objectOut != null) {
            try {
                objectOut.writeObject(new Messenger(CYFProtocol.UPDATE));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    void send(CYFProtocol command, Object data) {
        update();
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(command, data));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to server!");
        }
    }

    void send(CYFProtocol command) {
        update();
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(command));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to server!");
        }
    }

    void forceUpdate() {
        send(CYFProtocol.UPDATE);
    }

    void forceLogout() {
        if (socket != null) {
            send(CYFProtocol.LOGOUT);
        }
    }
}
