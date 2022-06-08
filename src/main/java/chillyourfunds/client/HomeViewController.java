package chillyourfunds.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

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
    private void login(){
        client.login("jacko","1234");
  }

    public void addExpense(ActionEvent e) {
        System.out.println("Expense added");
    }

}
