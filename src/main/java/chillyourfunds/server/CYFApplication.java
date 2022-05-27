package chillyourfunds.server;

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