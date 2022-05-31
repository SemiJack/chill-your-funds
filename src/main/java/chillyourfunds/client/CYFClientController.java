package chillyourfunds.client;

import chillyourfunds.server.CYFProtocol;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class CYFClientController implements Runnable {
    private final Socket socket;

    private final BufferedReader input;
    private final PrintWriter output;


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
            input = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
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
                String protocolSentence = receive();
                if (!handleCommand(protocolSentence)) {
                    output.close();
                    input.close();
                    socket.close();
                    break;
                }
            } catch (IOException ignore) {
            }
        }
    }

    private boolean handleCommand(String protocolSentence) {
        StringTokenizer st = new StringTokenizer(protocolSentence);
        CYFProtocol command = CYFProtocol.valueOf(st.nextToken());
        switch (command) {
            case LOGGEDIN:
                System.out.println("Logowanie pomyślne");
                break;
            case SINGEDIN:
                System.out.println("Rejestracja pomyślna");
                break;
            case HISTORY:
                // a co jak się nie zmieści do jednego pakietu?
                break;
            case COMMENT:
                System.out.println(st.nextToken());
                break;
            case STOP:
                send(CYFProtocol.STOPPED.name()); // no break! - false must be returned
            case LOGGEDOUT:
                return false;// stop the communication
        }
        return true;
    }

    void createExpense(Integer[] personsId, Integer groudId, String expensetype, Integer amount) {
        try {
            send(CYFProtocol.ADDEXPENSE + expensetype + " " + amount);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(personsId);
            oos.flush();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void login(String login, String password) {
        send(CYFProtocol.LOGIN + " " +login+ " " + password);
    }

    void singin(String login, String password, String firstname, String lastname) {
        send(CYFProtocol.SINGIN + " " +login+ " " + password + " " + firstname + " " + lastname);
    }

    void createGroup(String groupName, Integer personId) {
        send(CYFProtocol.CREATEGROUP + " " + groupName + " " + personId.toString());
    }

    void getGroup(Integer groupId) {
        send(CYFProtocol.CHOOSEGROUP + " " + groupId);
    }

    void createAccount(String login, String password, String firstname, String lastname){
        send(CYFProtocol.SINGIN + " " + login + " " + password + " " + firstname + " " + lastname);
    }

    void send(String command) {
        if (output != null)
            output.println(command);
    }

    String receive() throws IOException {
        return input.readLine();
    }

    void forceLogout() {
        if (socket != null) {
            send(CYFProtocol.LOGOUT.name());
        }
    }
}
