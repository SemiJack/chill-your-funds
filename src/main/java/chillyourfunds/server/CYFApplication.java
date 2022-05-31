package chillyourfunds.server;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CYFApplication {
    private final List<CYFClientController> clients = new ArrayList<>();

    CYFApplication() {
        String host = "localhost";
        String port = "40000";
        try {
            clients.add(new CYFClientController(host, port));
        } catch (Exception e) {
            e.printStackTrace();
        }
        clients.get(0).singin("jacko","1234", "Jacek","Pelczar");
        clients.get(0).createGroup("Akolici_Cyerbezpieczenstwa", 1);
        //clients.get(0).createExpense();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("enter an integer");
        int myint = keyboard.nextInt();
        switch (myint){
            case 0:
                clients.get(0).forceLogout();
                break;
            case 1 :
               // clients.get(0).createExpense();
                break;
        }


        //clients.get(0).getGroup();
      //  clients.get(0).createExpense(new Integer[]{1,2,3},12,"exact",200);

    }

    void destroy() {
        clients.forEach(CYFClientController::forceLogout);
    }

    private void cleanHouse() {
        clients.removeIf(CYFClientController::isDisconnected);
    }

    public static void main(String[] args) {
        new CYFApplication();
    }
}