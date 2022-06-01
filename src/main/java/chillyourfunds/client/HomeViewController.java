package chillyourfunds.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    @FXML
    private CYFClientController client;
    @FXML
    public ListView<String> grouplist;

    @FXML
    public javafx.scene.control.Button addgroupButton;
    ObservableList<String> groups = FXCollections.observableArrayList("Koksy", "Gracze", "Malepieski");

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
        client.createGroup("Koty",1);
  }

  @FXML
    private void login(){
        client.login("jacko","1234");
  }

}
