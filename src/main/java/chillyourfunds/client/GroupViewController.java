package chillyourfunds.client;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Logic;
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
    @FXML
    Button returnButton=new Button();


    public void addExpense(ActionEvent e) throws IOException {
        sceneController.switchToAddExpenseView(e);
    }
    public void addPerson(ActionEvent e) throws IOException {
        sceneController.switchToAddPersonView(e);
    }
    public void simplify(){
        Logic.groups.get(Logic.groups.size()-1).simplifyGroupExpenses(Logic.groups.get(Logic.groups.size()-1));
    }
    public void showPeople(){
        System.out.println(Logic.lastGroup.getPeople().toString());
    }
    public void returnDebt(){
    }
    public void returnToMain(ActionEvent e) throws IOException {
        sceneController.switchToMain(e);
    }
}
