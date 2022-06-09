package chillyourfunds.client;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddExpenseViewController {
    AddGroupViewController addGroupViewController=new AddGroupViewController();

    @FXML
    TextField amountTextField=new TextField();
    @FXML
    TextField payerTextField=new TextField();
    @FXML
    Button addExpenseBut=new Button();
int amount;
String payerName;
    public void addExpense(){
        amount=Integer.parseInt(amountTextField.getText());
//        Group group=addGroupViewController.groups.get(0)
    }
}
