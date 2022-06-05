package chillyourfunds.client;

import chillyourfunds.logic.ExactExpense;
import chillyourfunds.logic.Group;
import chillyourfunds.logic.PercentExpense;
import chillyourfunds.logic.Person;
import chillyourfunds.server.CYFProtocol;
import chillyourfunds.server.Messenger;
import chillyourfunds.server.User;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class CYFClientController implements Runnable {
    private final Socket socket;
    public User me;
    private  Group currGroup;
    private final ObjectInputStream objectIn;
    private final ObjectOutputStream objectOut;


    public CYFClientController(String host, String port) throws Exception {
        //view = new CYFClientView(this, model, host + ":" + port);
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
            throw new Exception("Can not get input/output connection stream.");
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
            } catch (IOException | ClassNotFoundException ignore ) {
            }
        }
    }

    private boolean handleCommand() throws IOException, ClassNotFoundException {
        Messenger message = (Messenger) objectIn.readObject();
        CYFProtocol  command = message.command;
        switch (command) {
            case LOGGEDIN:
                me = (User) message.data;
                System.out.println("Log in successful");
                break;
            case REGISTERED:
                System.out.println("Register successful");
                break;
            case GROUPCHOOSED:
                currGroup = (Group) message.data;
                System.out.println("Choosed group: " + currGroup.getGroupName());
                break;
//            case 3:
//                // a co jak się nie zmieści do jednego pakietu?
//                break;
//            case 4:
//               // System.out.println(st.nextToken());
//                break;
            case COMMENT:
                System.out.println((String) message.data);
                break;
            case STOP:
                send(CYFProtocol.STOPPED); // no break! - false must be returned
            case LOGGEDOUT:
                return false; // stop the communication
        }
        return true;
    }

//    void createExpense(Integer groudId, Integer expensetype, Integer amount) {
//        if(expensetype==0){
//            PercentExpense percent = new PercentExpense(amount,currGroup,me.getMeInGroup());
//            send(CYFProtocol.ADDEXPENSE,percent);
//        }else if(expensetype ==1){
//            ExactExpense exact = new ExactExpense(amount,currGroup,me.getMeInGroup());
//        }else {
//
//        }
//        send(CYFProtocol.ADDEXPENSE,expensetype,new Integer[]{amount,groudId});
//    }

    ArrayList<Integer> getGroups(){
        return me.getGroupsId();
    }

    void login(String username, String password) {
        send(CYFProtocol.LOGIN, new String[]{username,password});
    }

    void register(String login, String password, String firstname, String lastname){
        send(CYFProtocol.REGISTER, new String[]{login, password, firstname, lastname});
    }

     void createGroup(String groupName) {
       send(CYFProtocol.CREATEGROUP, groupName);
    }

    void chooseGroup(int groupId) {
        send(CYFProtocol.CHOOSEGROUP, groupId);
    }


    void send(CYFProtocol command, Object data) {
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(command, data));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to server!");
        }
    }
    void send(CYFProtocol command) {
        try {
            if (objectOut != null)
                objectOut.writeObject(new Messenger(command));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot send data to server!");
        }
    }

    void forceLogout() {
        if (socket != null) {
            send(CYFProtocol.LOGOUT);
        }
    }
}
