package chillyourfunds.client;

import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;



public class CYFApplication extends javafx.application.Application{
    protected final List<CYFClientController> clients = new ArrayList<>();
    void initialize() {
        String host = "localhost";
        String port = "40000";
        try {
            clients.add(new CYFClientController(host, port));
        } catch (Exception e) {
            //Platform.exit();
            e.printStackTrace();
        }
//        clients.get(0).singin("jacko","1234", "Jacek","Pelczar");
//        clients.get(0).createGroup("Akolici_Cyerbezpieczenstwa", 1);
//        //clients.get(0).createExpense();
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("enter an integer");
//        int myint = keyboard.nextInt();
//        switch (myint){
//            case 0:
//                clients.get(0).forceLogout();
//                break;
//            case 1 :
//               // clients.get(0).createExpense();
//                break;
 //       }


        //clients.get(0).getGroup();
      //  clients.get(0).createExpense(new Integer[]{1,2,3},12,"exact",200);

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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/Main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.initData(clients.get(0));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}