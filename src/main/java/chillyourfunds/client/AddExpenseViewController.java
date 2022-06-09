package chillyourfunds.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddExpenseViewController {
    AddGroupViewController addGroupViewController=new AddGroupViewController();
    SceneController sceneController = new SceneController();

    @FXML
    TextField amountTextField=new TextField();
    @FXML
    TextField payerTextField=new TextField();
    @FXML
    Button percentsplit = new Button();
    @FXML
    Button exactsplit = new Button();
    @FXML
    Button equalsplit = new Button();




    public void addExpenseEqual(ActionEvent event) throws IOException {
        sceneController.switchToEqualSplitView(event);
    }

    public void addExpensePercent(ActionEvent event) throws IOException {
        sceneController.switchToPercentSplitView(event);
    }

    public void addExpenseExact(ActionEvent event) throws IOException {
        sceneController.switchToExactSplitView(event);
    }




}
