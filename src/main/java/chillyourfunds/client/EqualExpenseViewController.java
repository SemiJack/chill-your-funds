package chillyourfunds.client;

import chillyourfunds.logic.EqualExpense;
import chillyourfunds.logic.Logic;
import chillyourfunds.logic.Person;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EqualExpenseViewController {

    public TextField amountTextField=new TextField();
    public TextField payerTextField=new TextField();
    public Button confirmButton=new Button();
    public Button addButton=new Button();
    public TextField debtorID=new TextField();

    int amount;
    Person payer;
    int debtorsID;

    public void confirm(ActionEvent event) {
        amount=Integer.parseInt(amountTextField.getText());
        payer=Logic.lastGroup.getPersonByName(payerTextField.getText());
        Logic.lastExpense=new EqualExpense(amount,Logic.lastGroup,payer);
        Logic.lastGroup.getExpenses().add(Logic.lastExpense);
    }

    public void add(ActionEvent event) {
        debtorsID=Integer.parseInt(debtorID.getText());
        Logic.lastExpense.addDebtor(debtorsID);
        System.out.println("Lista debtorów"+Logic.lastExpense.debtors.toString());

    }
}
