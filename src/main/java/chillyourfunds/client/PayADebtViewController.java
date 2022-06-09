package chillyourfunds.client;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Logic;
import chillyourfunds.logic.Person;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PayADebtViewController {

    SceneController sceneController = new SceneController();


    public TextField payerFieldName = new TextField();
    public TextField amountFieldName = new TextField();
    public TextField debtorsname = new TextField();
    public Button back = new Button();
    public Button payADebt = new Button();
    int amount;
    Person payer;
    Person debtor;
    public void payADebt(ActionEvent event) {
        amount = Integer.parseInt(amountFieldName.getText());
        payer = Logic.lastGroup.getPersonByName(payerFieldName.getText());
        debtor = Logic.lastGroup.getPersonByName(debtorsname.getText());
        debtor.payADebt(payer,Logic.lastGroup,amount);
    }


    public void back(ActionEvent event) throws IOException {
        sceneController.switchToGroupView(event);

    }
}
