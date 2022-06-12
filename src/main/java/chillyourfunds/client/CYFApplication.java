package chillyourfunds.client;

import chillyourfunds.server.CYFProtocol;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CYFApplication extends javafx.application.Application {
    protected CYFClientController client;


    void initialize() {
        String host = "localhost";
        String port = "40000";
        try {
            this.client = new CYFClientController(host, port);
            talkWithServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void talkWithServer() {
        System.out.println("Rejestrowanie...");
        client.register("admin", "1234", "Jacek", "Pelczar");

        System.out.println("Logowanie...");
        client.login("admin", "1234");

        System.out.println("Tworzenie grupy");
        client.createGroup("testowaGrupa");

        client.getPerson("admin");

        System.out.println("Wybieranie grupy");
        System.out.println(client._me.getParticipateGroupsId());
        client.chooseGroup(client._me.getParticipateGroupsId().get(0));

        System.out.println("Dodawanie nowej osoby: jack");
        client.addPersonToGroup("jack");
        System.out.println(client._currGroup.getPeople());

        System.out.println("Dodawanie nowej osoby: jacke");
        client.addPersonToGroup("jacke");
        System.out.println(client._currGroup.getPeople());

        System.out.println("Tworzenie nowego wydatku. Typ EqualExpense. Podziel na osoby: admin, jacke i jack");
        client.addExpense(CYFProtocol.EQUALSPLIT,360,new String[]{"admin", "jack", "jacke"});
        System.out.println(client._currGroup.getExpenses());
    }

    void destroy() {
        client.forceLogout();
    }

    private void cleanHouse() {
        client.isDisconnected();
    }

    public Stage primaryStage=new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {

        initialize();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.primaryStage=primaryStage;
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(
                getClass().getResource(fxml));

        Scene scene = new Scene(pane);
        primaryStage.close();
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}