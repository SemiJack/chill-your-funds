package chillyourfunds.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class CYFApplication extends javafx.application.Application {
    protected CYFClientController client;


    void initialize() {
        String host = "localhost";
        String port = "40000";
        try {
            this.client = new CYFClientController(host, port);
            //new Thread(this::talkWithServer).start();
            talkWithServer();
        } catch (Exception e) {
            //Platform.exit();
            e.printStackTrace();
        }
//        talkWithServer();
    }

    private void talkWithServer() {
        ///client.update();
        System.out.println("Registering");
        client.register("admin", "1234", "Jacek", "Pelczar");

        System.out.println("Logging In");
        client.login("admin", "1234");


        System.out.println("Creating group");
        client.createGroup("testowaGrupa");

        client.getPerson("admin");

        System.out.println("Choosing group");
        System.out.println(client._me.getParticipateGroupsId());
        client.chooseGroup(client._me.getParticipateGroupsId().get(0));

        System.out.println(client._currGroup.getPeople());
        client.addPersonToGroup("jack");
        System.out.println(client._currGroup.getPeople());

        //System.out.println(client.currGroup.getPeople());
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Choose an option: ");
//        while (true) {
//            int myint = keyboard.nextInt();
//
//            switch (myint) {
//                case 0:
//                    // wyloguj i zamknij apkę
//                    client.forceLogout();
//                    System.exit(0);     // exit app
//                    break;
//                case 1:
//                    // grupy w których jestem
//                    System.out.println(client.me.getParticipateGroupsId());
//                    break;
//                case 2:
//                    // wybierz grupę nr.0
//                    client.chooseGroup(client.me.getParticipateGroupsId().get(0));
//                    break;
//                case 3:
//                    // dodaj jack do grupy
//                    client.addPersonToGroup("jack");
//                    break;
//                case 4:
//                    // usuń osobę z grupy
//                    client.removePersonFromGroup("jack");
//                    break;
//                case 5:
//                    // wyświetl osoby należące do grupy
//                    System.out.println(client.currGroup.getPeople());
//                    break;
//                case 6:
//                    // synchronizacja danych
//                    client.update();
//                    break;
//            }
//        }
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
//        MainController mainController = loader.getController();
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