package chillyourfunds.client;

import chillyourfunds.logic.Logic;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GroupListController implements Initializable {
    CYFApplication cyfApplication=new CYFApplication();
    SceneController sceneController=new SceneController();
    List<String> group = new ArrayList<>();
    @FXML
    Button returnButton=new Button();
    @FXML
    public ListView<String> listView = new ListView<String>();

    public List addItems() {
        for(int i = 0; i < Logic.groups.size(); i++) {
            group.add(Logic.groups.get(i).getGroupName());

        }
        return group;
    }

    ObservableList<String> items = FXCollections.observableArrayList();
    String string;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addItems();
        listView.getItems().addAll(group);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                string = listView.getSelectionModel().getSelectedItem();
            }
        });

    }
    public void returnToMain(ActionEvent e) throws IOException {
        sceneController.switchToMain(e);
    }
}
