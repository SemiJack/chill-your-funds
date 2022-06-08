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
    private CYFClientController client=new CYFClientController("localhost","40000");
    @FXML
    public ListView<String> grouplist;

    @FXML
    public javafx.scene.control.Button addgroupButton;
    ObservableList<String> groups = FXCollections.observableArrayList("Koksy", "Gracze", "Malepieski");

    public HomeViewController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grouplist.setItems(groups);
    }

    public void initData(CYFClientController client){
        this.client = client;
    }

    public void groupsChanged(ActionEvent event){
        grouplist.getItems().addAll("Kokok");
    }

    @FXML
    private void addgroupButtonClick(ActionEvent event){
        client.createGroup("Koty");
  }

  @FXML
    private void logout(ActionEvent e) throws IOException {
        client.forceLogout();
        sceneController.switchToLoginScene(e);

  }

    public void addExpense(ActionEvent e) {
        System.out.println("Expense added");
    }

}
