package chillyourfunds.client;

import chillyourfunds.logic.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddGroupViewController {
    SceneController sceneController=new SceneController();
    List<Group> groups = new ArrayList<>();
@FXML
    TextField groupnametextfield = new TextField();
@FXML
    TextField groupidtextfield = new TextField();
@FXML
    Button addgroupbutton = new Button();

String groupName;
int groupId;

public void addGroup(ActionEvent event) {
        groupName = groupnametextfield.getText();
        groupId = Integer.parseInt(groupidtextfield.getText());
        groups.add(new Group(groupName,groupId));
    try {
        sceneController.switchToGroupView(event);
    } catch (IOException e) {
        e.printStackTrace();
    }


}
}
