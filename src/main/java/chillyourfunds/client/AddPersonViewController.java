package chillyourfunds.client;

import chillyourfunds.logic.Logic;
import chillyourfunds.logic.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddPersonViewController {
    SceneController sceneController=new SceneController();
    CYFApplication cyfApplication=new CYFApplication();
    @FXML
    TextField personNameTextField=new TextField();
    @FXML
    TextField personIdTextField=new TextField();
    @FXML
    Button addPersonButton=new Button();

    String name;
    int id;
    public void addPerson(ActionEvent event) throws IOException {
        name=personNameTextField.getText();
        try{
            id=Integer.parseInt(personIdTextField.getText());
            Logic.groups.get(0).addPerson(new Person(id,name));
            sceneController.switchToGroupView(event);
        }catch(Exception e){
            addPerson(event);
        }


    }
}
