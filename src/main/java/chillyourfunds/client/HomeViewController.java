package chillyourfunds.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {
    SceneController sceneController=new SceneController();


    @FXML
    public ListView<String> grouplist;

    @FXML
    public javafx.scene.control.Button addgroupButton;


    public HomeViewController() throws Exception {
    }

    @FXML
    private void addgroupButtonClick(ActionEvent event) throws IOException {
        sceneController.switchToAddGroup(event);
    }

  @FXML
    private void logout(ActionEvent e) throws IOException {

        sceneController.switchToLoginScene(e);

  }

    public void addExpense(ActionEvent e) {
        System.out.println("Expense added");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
