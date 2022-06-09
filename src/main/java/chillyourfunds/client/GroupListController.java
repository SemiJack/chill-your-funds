package chillyourfunds.client;

import chillyourfunds.logic.Logic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupListController implements Initializable {
    CYFApplication cyfApplication=new CYFApplication();
    SceneController sceneController=new SceneController();

    @FXML
    Button returnButton=new Button();
    @FXML
    public ListView<String> listView = new ListView<String>();
    ObservableList<String> items = FXCollections.observableArrayList("Szefy","Szafy","Szlaufy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(items);
    }
    public void returnToMain(ActionEvent e) throws IOException {
        sceneController.switchToMain(e);
    }
}
