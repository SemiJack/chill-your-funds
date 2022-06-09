package chillyourfunds.client;

import chillyourfunds.logic.Group;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CYFApplication extends javafx.application.Application {




    public Stage primaryStage=new Stage();
    List<Group> groups=new ArrayList<>();
    @Override
    public void start(Stage primaryStage) throws Exception {



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