package chillyourfunds.client;

import chillyourfunds.logic.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.stage.Stage;


import java.io.IOException;

public class GroupViewController {
    CYFApplication cyfApplication=new CYFApplication();
    SceneController sceneController=new SceneController();

    @FXML
    Button addExpenseButton=new Button();
    @FXML
    Button simplifyButton=new Button();
    @FXML
    Button returnDebtButton=new Button();
    @FXML
    Button addPersonButton=new Button();


    public void addExpense(){
    }
    public void addPerson(ActionEvent e) throws IOException {
        sceneController.switchToAddPersonView(e);
    }
    public void simplify(){

    }
    public void returnDebt(){

    }

}
