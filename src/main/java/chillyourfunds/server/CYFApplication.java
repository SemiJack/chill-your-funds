package chillyourfunds.server;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


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
        clients.get(0).createGroup("Akolici_Cyberbezpieczenstwa");
        System.out.println("jkj");
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