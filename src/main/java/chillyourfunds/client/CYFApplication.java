package chillyourfunds.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CYFApplication extends javafx.application.Application {
    protected final List<CYFClientController> clients = new ArrayList<>();

    void initialize() throws Exception {
        String host = "localhost";
        String port = "40000";
        try {
            clients.add(new CYFClientController(host, port));
        } catch (Exception e) {
            //Platform.exit();
            e.printStackTrace();
        }
        talkWithServer();
    }

    private void talkWithServer() throws Exception {
        clients.get(0).register("jacko","1234", "Jacek","Pelczar");
        clients.get(0).login("jacko", "1234");
        //clients.get(0).createGroup("Akolici_Cyerbezpieczenstwa", 1);
        //clients.get(0).createExpense();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Choose an option: ");
        int myint = keyboard.nextInt();
        switch (myint) {
            case 0:
                clients.get(0).forceLogout();
                System.exit(0);     // exit app
                break;
            case 1:
                // clients.get(0).createExpense();
                break;

            //clients.get(0).createExpense(new Integer[]{1, 2, 3}, 12, "exact", 200);

        }
    }

    void destroy() {
        clients.forEach(CYFClientController::forceLogout);
    }

    private void cleanHouse() {
        clients.removeIf(CYFClientController::isDisconnected);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("fxml/Main.fxml"));
//        Parent root = loader.load();
//        MainController mainController = loader.getController();
//        mainController.initData(clients.get(0));
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}