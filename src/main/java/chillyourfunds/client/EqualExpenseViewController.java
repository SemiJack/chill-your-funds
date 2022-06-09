package chillyourfunds.client;

import chillyourfunds.logic.EqualExpense;
import chillyourfunds.logic.Expense;
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
    public Button equalsplit = new Button();

    int amount;
    Person payer;
    int debtorsID;
    public EqualExpense equalExpense;

    public void confirm(ActionEvent event) {
        amount=Integer.parseInt(amountTextField.getText());
        payer=Logic.lastGroup.getPersonByName(payerTextField.getText());
        equalExpense = new EqualExpense(amount,Logic.lastGroup,payer);
        Logic.lastGroup.getExpenses().add(equalExpense);
    }

    public void add(ActionEvent event) {
        debtorsID=Integer.parseInt(debtorID.getText());
        equalExpense.addDebtor(debtorsID);
        System.out.println("Lista debtorów"+equalExpense.debtors.toString());

    }

    public void equalSplit(ActionEvent event) {
        equalExpense.equalSplit();
        for(int i = 0; i < equalExpense.debtors.size();i++) {
            System.out.println(equalExpense.debtors.get(i).getBalance(Logic.lastGroup));
        }


    }
}
