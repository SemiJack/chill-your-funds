package chillyourfunds.client;

import chillyourfunds.logic.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PercentExpenseViewController {

    public TextField debtorID=new TextField();
    public TextField debtorAmountTextField=new TextField();
    public Button addButton=new Button();
    public TextField amountTextField=new TextField();
    public TextField payerTextField=new TextField();
    public Button confirmButton=new Button();
    public Button percentSplitButton=new Button();
    public Button back = new Button();
    SceneController sceneController = new SceneController();


    int amount;
    Person payer;
    int debtorsID;
    int debtorAmount;
    public PercentExpense lastPercentExpense;
    Map<Integer,Integer> map=new HashMap<>();

    public void confirm(ActionEvent event) {
        amount=Integer.parseInt(amountTextField.getText());
        payer= Logic.lastGroup.getPersonByName(payerTextField.getText());
        lastPercentExpense =new PercentExpense(amount,Logic.lastGroup,payer);
        Logic.lastGroup.getExpenses().add(Logic.lastExpense);
    }

    public void add(ActionEvent event) {
        debtorsID=Integer.parseInt(debtorID.getText());
        lastPercentExpense.addDebtor(debtorsID);
        debtorAmount=Integer.parseInt(debtorAmountTextField.getText());
        map.put(debtorsID,debtorAmount);
        lastPercentExpense.mapOfPercents.put(Logic.lastGroup.getPersonById(debtorsID),debtorAmount);
        System.out.println("Lista debtorów"+ lastPercentExpense.debtors.toString());

    }
    public void percentSplit(ActionEvent event) throws WrongAmountException, WrongPercentException {
        lastPercentExpense.percentSplit(map);
        for (int i = 0; i < lastPercentExpense.debtors.size(); i++) {
            System.out.println(lastPercentExpense.debtors.get(i).getBalance(Logic.lastGroup));
        }
    }

    public void back(ActionEvent event) throws IOException {
        sceneController.switchToGroupView(event);
    }
}
