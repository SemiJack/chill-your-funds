package chillyourfunds.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;


import java.io.IOException;

public class GroupViewController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private MainController mainController;

    public void injectMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    public void switchToHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Main.fxml"));
        root = loader.load();
        System.out.println("click");
        this.mainController = loader.getController();
        this.mainController.switchToHistory(event);
    }

}
