package chillyourfunds.client;

import javafx.stage.Stage;

import java.util.Scanner;

public class CYFApplication extends javafx.application.Application {
    protected CYFClientController client;

    void initialize() {
        String host = "localhost";
        String port = "40000";
        try {
            this.client = new CYFClientController(host, port);
        } catch (Exception e) {
            //Platform.exit();
            e.printStackTrace();
        }
        talkWithServer();
    }

    private void talkWithServer() {
        client.register("admin", "1234", "Jacek", "Pelczar");
        client.login("admin", "1234");
        client.createGroup("testowaGrupa");

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Choose an option: ");
        while (true) {
            int myint = keyboard.nextInt();

            switch (myint) {
                case 0:
                    // wyloguj i zamknij apkę
                    client.forceLogout();
                    System.exit(0);     // exit app
                    break;
                case 1:
                    // grupy w których jestem
                    System.out.println(client.me.getParticipateGroupsId());
                    break;
                case 2:
                    // wybierz grupę nr.0
                    client.chooseGroup(client.me.getParticipateGroupsId().get(0));
                    break;
                case 3:
                    // dodaj jack do grupy
                    client.addPersonToGroup("jack");
                    break;
                case 4:
                    // usuń osobę z grupy
                    client.removePersonFromGroup("jack");
                    break;
                case 5:
                    // wyświetl osoby należące do grupy
                    System.out.println(client.currGroup.getPeople());
                    break;
            }
        }
    }

    void destroy() {
        client.forceLogout();
    }

    private void cleanHouse() {
        client.isDisconnected();
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