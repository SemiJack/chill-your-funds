package chillyourfunds.client;

import chillyourfunds.logic.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class ExactExpenseViewController {

    public TextField debtorID=new TextField();
    public TextField debtorAmountTextField=new TextField();
    public Button addButton=new Button();
    public TextField amountTextField=new TextField();
    public TextField payerTextField=new TextField();
    public Button confirmButton=new Button();
    public Button exactSplitButton=new Button();


    int amount;
    Person payer;
    int debtorsID;
    int debtorAmount;
    public ExactExpense lastExactExpense;
    Map<Integer,Integer> map=new HashMap<>();

    public void confirm(ActionEvent event) {
        amount=Integer.parseInt(amountTextField.getText());
        payer= Logic.lastGroup.getPersonByName(payerTextField.getText());
        lastExactExpense=new ExactExpense(amount,Logic.lastGroup,payer);
        Logic.lastGroup.getExpenses().add(Logic.lastExpense);
    }

    public void add(ActionEvent event) {
        debtorsID=Integer.parseInt(debtorID.getText());
        lastExactExpense.addDebtor(debtorsID);
        debtorAmount=Integer.parseInt(debtorAmountTextField.getText());
        map.put(debtorsID,debtorAmount);
        lastExactExpense.mapOfAmounts.put(Logic.lastGroup.getPersonById(debtorsID),debtorAmount);
        System.out.println("Lista debtorów"+lastExactExpense.debtors.toString());

    }
    public void exactSplit(ActionEvent event) throws WrongAmountException {
        lastExactExpense.exactSplit(map);
        for (int i = 0; i < lastExactExpense.debtors.size(); i++) {
            System.out.println(lastExactExpense.debtors.get(0).getBalance(Logic.lastGroup));
        }
    }
}
